package helper;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.enums.ECraftingType;
import model.enums.EMod;
import model.interfaces.AbstractItem;
import model.interfaces.IItem;
import model.interfaces.IRecipe;
import model.minecraft.MCItem;
import model.minecraft.MCRecipe;

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

		List<String> source = Files.readAllLines(new File(MCItem.class.getResource(EMod.MINECRAFT.getPath() + "recipes.json").toURI()).toPath(),
				Charset.defaultCharset());
		StringBuffer buf = new StringBuffer();
		for (String string : source) {
			buf.append(string);
		}

		Map<String, AbstractItem> itemList = new LinkedHashMap<>();
		for (AbstractItem item : IItem.itemList) {
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
						AbstractItem finalItem = itemList.get(item);
						System.out.println(finalItem);
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
							AbstractItem[] inputItems = new AbstractItem[sInputItems.length];
							for (int i = 0; i < sInputItems.length; i++) {
								inputItems[i] = itemList.get(sInputItems[i]);
							}

							AbstractItem[] recipePattern = new AbstractItem[9];
							String[] patternElements = pattern.replaceAll(" ", "").split("");
							for (int i = 1; i < patternElements.length; i++) {
								if (patternElements[i].equals(".")) {
									recipePattern[i - 1] = null;
								}
								else {
									recipePattern[i - 1] = inputItems[Integer.parseInt(patternElements[i])];
								}
							}

							List<AbstractItem> recipeExtras = new ArrayList<>();
							if (!extras.equals("")) {
								// TODO extras
							}

							IRecipe.recipeList.add(new MCRecipe(finalItem, quantity, recipeExtras, recipePattern, craftingType));

						}

						// new MCRecipe(item, quantity, extras, pattern, type)

					}
				}
			}
		}

	}

	public static void formatItems() throws Exception {

	}

}
