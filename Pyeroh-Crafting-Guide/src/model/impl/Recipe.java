/**
 *
 */
package model.impl;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import model.UnknownItem;
import model.enums.ECraftingType;
import model.enums.EMod;
import model.impl.Item.ItemData;
import model.interfaces.IItem;
import model.interfaces.IRecipe;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Définition des recettes
 *
 * @author Pyeroh
 *
 */
public class Recipe implements IRecipe {

	private Item item;

	private int quantity;

	private Map<Item, Integer> extras;

	private Item[] pattern;

	private ECraftingType type;

	private EMod mod;

	public Recipe(Item item, int quantity, Map<Item, Integer> extras, Item[] pattern, ECraftingType type, EMod mod) {
		this.item = item;
		this.quantity = quantity;
		this.extras = extras;
		this.pattern = pattern;
		this.type = type;
		this.mod = mod;
	}

	@Override
	public String toString() {
		Map<Item, Integer> items = new LinkedHashMap<>();
		for (Item item : getPattern()) {
			if (items.containsKey(item)) {
				items.put(item, items.get(item) + 1);
			} else {
				items.put(item, 1);
			}
		}
		items.remove(null);
		return String.format("%s : %s -> %d %s", getType().name(), items.toString(), getQuantity(), getItem().toString());
	}

	/**
	 * Permet d'initialiser la liste des recettes disponibles
	 *
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static void initialize() throws IOException, URISyntaxException {

		Properties unknownLang = new Properties();
		unknownLang.load(Recipe.class.getResourceAsStream(EMod.UNKNOWN.getPath() + String.format("lang_%s.properties", Locale.getDefault().getLanguage())));

		for (EMod mod : EMod.values()) {

			switch (mod) {
			case UNKNOWN:
				break;
			default:

				List<String> source = Files.readAllLines(new File(Item.class.getResource(mod.getPath() + "recipes.json").toURI()).toPath(),
						Charset.defaultCharset());
				StringBuffer buf = new StringBuffer();
				for (String string : source) {
					buf.append(string);
				}

				JSONArray jsonArray = new JSONArray(buf.toString());
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject obj = jsonArray.getJSONObject(i);

					Item item = Item.getBy(obj.getString("item"), ItemData.ID_AND_META);
					JSONArray recipes = obj.getJSONArray("recipes");

					for (int j = 0; j < recipes.length(); j++) {

						JSONObject recipe = recipes.getJSONObject(j);
						String pattern = recipe.getString("pattern");
						ECraftingType craftType = ECraftingType.valueOf(recipe.getString("craftType"));
						int quantity = recipe.optInt("quantity", 1);
						String extras = recipe.optString("extras");

						// Traitement des extras
						Map<Item, Integer> recipeExtras = new Hashtable<Item, Integer>();
						if (!extras.isEmpty()) {
							String[] extrass = extras.split(",");
							for (String extra : extrass) {
								String[] parts = extra.split("::");
								recipeExtras.put(Item.getBy(parts[1], ItemData.ID_AND_META), Integer.parseInt(parts[0]));
							}
						}

						// Traitement du pattern de craft
						String[] patternParts = pattern.split(",");
						Item[] recipePattern = new Item[patternParts.length];
						for (int k = 0; k < patternParts.length; k++) {
							Item patElement = Item.getBy(patternParts[k], ItemData.ID_AND_META);
							if (patElement == null && !patternParts[k].equals(".")) {
								patElement = new UnknownItem(patternParts[k], unknownLang.getProperty(patternParts[k]));
								IItem.itemList.add(patElement);
							}
							recipePattern[k] = patElement;
						}

						IRecipe.recipeList.add(new Recipe(item, quantity, recipeExtras, recipePattern, craftType, mod));

					}
				}

				break;
			}

			Set<Item> il = new LinkedHashSet<>(IItem.itemList);
			IItem.itemList.clear();
			IItem.itemList.addAll(il);

		}

	}

	/**
	 * Recherche basique de recette entre une donnée et un critère de comparaison
	 * @param data Doit correspondre au type indiqué par les valeurs de {@link RecipeData}
	 * @param compare
	 * @return
	 */
	public static List<Recipe> searchBy(Object data, RecipeData compare) {

		List<Recipe> recipes = new ArrayList<>();

		Item item = null;
		ECraftingType type = null;
		EMod mod = null;

		switch (compare) {
		case ITEM:
		case CONTENT:
			item = (Item) data;
			break;
		case TYPE:
			type = (ECraftingType) data;
			break;
		case MOD:
			mod = (EMod) data;
			break;
		default:
			throw new UnsupportedOperationException("Recipe.searchBy() : pas d'implémentation pour " + compare);
		}

		for (Recipe recipe : IRecipe.recipeList) {
			if (recipe.getMod().isActivated()) {
				switch (compare) {
				case ITEM:
					if (recipe.getItem() == item) {
						recipes.add(recipe);
					}
					break;
				case CONTENT:
					if (Arrays.asList(recipe.getPattern()).contains(item)) {
						recipes.add(recipe);
					}
					break;
				case TYPE:
					if (recipe.getType() == type) {
						recipes.add(recipe);
					}
					break;
				case MOD:
					if (recipe.getMod() == mod) {
						recipes.add(recipe);
					}
					break;
				}
			}
		}

		return recipes;
	}

	public static Map<Item, Integer> getIngredientsNeeded(Item item, int quantity, Map<Item, Integer> owned) {

		List<Recipe> recipes = Recipe.searchBy(item, RecipeData.ITEM);

		if (!item.isPrimary()) {
			// TODO
		}

		System.out.println(recipes);

		return null;
	}

	/**
	 * @return {@link #item}
	 */
	public final Item getItem() {
		return item;
	}

	/**
	 * @return {@link #quantity}
	 */
	public final int getQuantity() {
		return quantity;
	}

	/**
	 * @return {@link #extras}
	 */
	public final Map<Item, Integer> getExtras() {
		return Collections.unmodifiableMap(extras);
	}

	/**
	 * @return {@link #pattern}
	 */
	public final Item[] getPattern() {
		return pattern;
	}

	/**
	 * @return {@link #type}
	 */
	public final ECraftingType getType() {
		return type;
	}

	/**
	 * @return {@link #mod}
	 */
	public final EMod getMod() {
		return mod;
	}

	/**
	 * Enum pour la comparaison de valeurs, pour la recherche d'éléments dans la
	 * liste des recettes
	 *
	 * @author Pyeroh
	 *
	 */
	public static enum RecipeData {

		/**
		 * Recherche par résultat de la recette, un {@link Item}
		 */
		ITEM,

		/**
		 * Recherche par contenu de la recette, un {@link Item}
		 */
		CONTENT,

		/**
		 * Recherche par {@link ECraftingType}
		 */
		TYPE,

		/**
		 * Recherche par {@link EMod}
		 */
		MOD;

	}

}
