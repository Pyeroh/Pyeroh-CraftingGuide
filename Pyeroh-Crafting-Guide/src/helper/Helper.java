package helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import model.enums.ECraftingType;
import model.enums.EMod;
import model.impl.Item;
import model.impl.Recipe;
import model.impl.Item.ItemData;
import model.interfaces.IItem;
import model.interfaces.IRecipe;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Helper {

	/**
	 * <p>
	 * Au préalable, le fichier mod-version.cg doit être parsable en YML :
	 * <ul>
	 * <li>chaque item "group" et "item" doit prendre la valeur qui se situe devant ses deux points
	 * ("group: Agriculture" devient "Agriculture:", idem pour les "item")</li>
	 * <li>chaque item "recipe" en doublon doit avoir une valeur incrémentée (le premier doublon devient "recipe2", le
	 * suivant "recipe3", etc.)</li>
	 * </ul>
	 * </p>
	 * <p>
	 * Une fois parsé en YML, ce fichier doit être converti en JSON, et cette méthode doit être exécutée. Elle va
	 * boucler sur les éléments pour extraire les recettes et les re-créer au format de l'application. Attention à
	 * ajouter la gestion des valeurs de type de craft, ainsi que la gestion pour de nouvelles clés (autre que input,
	 * pattern, quantity, tools, extras).
	 * </p>
	 * <p>
	 * Attention c'est une méthode de développement, elle n'est pas destinée à être laissée dans l'application finale
	 * (quelque soit la version).
	 * </p>
	 */
	public static void formatRecipes() throws Exception {

		List<String> source = Files.readAllLines(new File(Helper.class.getResource(EMod.MINECRAFT.getPath() + "recipes.json").toURI()).toPath(),
				Charset.defaultCharset());
		StringBuffer buf = new StringBuffer();
		for (String string : source) {
			buf.append(string);
		}

		Map<String, Item> itemList = new LinkedHashMap<>();
		for (Item item : IItem.itemList) {
			itemList.put(item.getDisplayName(), item);
		}

		JSONObject jsonObject = new JSONObject(buf.toString());
		for (String category : jsonObject.keySet()) {
			JSONObject items = jsonObject.getJSONObject(category);
			for (String item : items.keySet()) {
				JSONObject recipes = items.getJSONObject(item);
				for (String recipeObj : recipes.keySet()) {
					if (recipeObj.contains("recipe")) {
						JSONObject recipe = recipes.getJSONObject(recipeObj);

						String tools = recipe.optString("tools", "Crafting Table");
						String input = recipe.getString("input");
						int quantity = recipe.optInt("quantity", 1);
						String pattern = recipe.getString("pattern");
						String extras = recipe.optString("extras", "");

						// On récupère l'item par son nom d'affichage
						Item finalItem = itemList.get(item);
						ECraftingType craftingType;

						switch (tools) {
						case "Crafting Table":
							craftingType = ECraftingType.CRAFT;
							break;
						case "Furnace":
							craftingType = ECraftingType.FOUR;
							break;
						case "Brewing Stand":
							craftingType = ECraftingType.POTION;
							break;
						default:
							craftingType = null;
							break;
						}

						if (craftingType != null) {

							String[] sInputItems = input.split(", ");
							Item[] inputItems = new Item[sInputItems.length];
							for (int i = 0; i < sInputItems.length; i++) {
								if (itemList.containsKey(sInputItems[i])) {
									inputItems[i] = itemList.get(sInputItems[i]);
								}
							}

							Item[] recipePattern = null;

							switch (craftingType) {
							case CRAFT:
								recipePattern = new Item[9];
								String[] patternElements = pattern.replaceAll(" ", "").split("");
								for (int i = 1; i < patternElements.length; i++) {
									if (patternElements[i].equals(".")) {
										recipePattern[i - 1] = null;
									}
									else {
										recipePattern[i - 1] = inputItems[Integer.parseInt(patternElements[i])];
									}
								}
								break;
							case FOUR:
							case POTION:
								List<Item> listInputItems = new ArrayList<>(Arrays.asList(inputItems));
								listInputItems.removeAll(Collections.singletonList(null));
								recipePattern = listInputItems.toArray(new Item[0]);
								break;
							default:
								break;
							}

							Map<Item, Integer> recipeExtras = new LinkedHashMap<>();
							if (!extras.isEmpty()) {
								String[] extrass = extras.split(", ");
								for (String extra : extrass) {
									String extraItem = extra.split("^\\d+ ")[1];
									int q = Integer.parseInt(extra.replaceAll(extraItem, "").trim());
									recipeExtras.put(itemList.get(extraItem), q);
								}
							}

							IRecipe.recipeList.add(new Recipe(finalItem, quantity, recipeExtras, recipePattern, craftingType, EMod.MINECRAFT));

						}

					}
				}
			}
		}

		JSONArray jsonArray = new JSONArray();
		for (Item item : IItem.itemList) {
			jsonObject = new JSONObject();
			List<Recipe> recipes = Recipe.getRecipesByItem(item);
			if (!recipes.isEmpty()) {
				jsonObject.put("item", item.toString());
				JSONArray recipesArray = new JSONArray();
				for (Recipe recipe : recipes) {
					JSONObject jsonRecipe = new JSONObject();
					jsonRecipe.put("craftType", recipe.getType().name());

					buf = new StringBuffer();
					for (Item patternItem : recipe.getPattern()) {
						if (patternItem != null) {
							buf.append(patternItem.toString());

						}
						else {
							buf.append(".");
						}
						buf.append(",");
					}

					jsonRecipe.put("pattern", buf.toString().substring(0, buf.length() - 1));
					if (recipe.getQuantity() > 1) {
						jsonRecipe.put("quantity", recipe.getQuantity());
					}
					if (!recipe.getExtras().isEmpty()) {
						buf = new StringBuffer();
						for (Item extraKey : recipe.getExtras().keySet()) {
							buf.append(recipe.getExtras().get(extraKey));
							buf.append("::");
							buf.append(extraKey.toString());
							buf.append(",");
						}
						jsonRecipe.put("extras", buf.toString().substring(0, buf.length() - 1));
					}

					recipesArray.put(jsonRecipe);
				}
				jsonObject.put("recipes", recipesArray);
				jsonArray.put(jsonObject);
			}
		}

		FileWriter w = new FileWriter(new File(
				"C:\\Users\\DENANTEUILQ\\Documents\\WS-eclipse-perso\\git\\Pyeroh-CraftingGuide\\Pyeroh-Crafting-Guide\\src\\gui\\recipes2.json"));
		w.write(jsonArray.toString());
		w.close();

	}

	public static void formatItems() throws Exception {

	}

	/**
	 * <p>Cette méthode extrait, à partir d'un dump des objets d'un mod (en CSV) le fichier lang.properties.</p>
	 * <p>Elle doit être exécutée après que les items aient été générés.</p>
	 * @throws Exception
	 */
	public static void extractLang() throws Exception {

		List<String> source = Files.readAllLines(new File(Helper.class.getResource(EMod.MINECRAFT.getPath() + "itempanel.csv").toURI()).toPath(),
				Charset.defaultCharset());

		Properties lang = new Properties();

		for (int i = 1; i < source.size(); i++) {
			String[] parts = source.get(i).split(",");

			String id = parts[0] + (parts[2].equals("0") ? "" : "." + parts[2]);
			String langID = id.replaceFirst(":", ".");

			if (Item.getBy(id, ItemData.ID_AND_META) != null) {
				lang.put(langID, parts[4]);
			}

			lang.store(new FileOutputStream("D:\\Workspace Eclipse Luna\\git\\Pyeroh-CraftingGuide\\Pyeroh-Crafting-Guide\\src\\gui\\items\\minecraft\\lang_fr.properties"), null);

		}

	}

}
