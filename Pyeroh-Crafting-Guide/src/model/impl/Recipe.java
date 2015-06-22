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

	private List<ItemWithQuantity> extras;

	private Item[] pattern;

	private ECraftingType type;

	private EMod mod;

	public Recipe(Item item, int quantity, List<ItemWithQuantity> extras, Item[] pattern, ECraftingType type, EMod mod) {
		this.item = item;
		this.quantity = quantity;
		this.extras = extras;
		this.pattern = pattern;
		this.type = type;
		this.mod = mod;
	}

	@Override
	public String toString() {
		return String.format("%s : %s -> %d %s", getType().name(), getIngredients().toString(), getQuantity(), getItem().toString());
	}

	/**
	 * Permet d'initialiser la liste des recettes disponibles
	 *
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static void initialize() throws IOException, URISyntaxException {

		Properties unknownLang = new Properties();
		unknownLang.load(Recipe.class.getResourceAsStream(EMod.UNKNOWN.getPath()
				+ String.format("lang_%s.properties", Locale.getDefault().getLanguage())));

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
						List<ItemWithQuantity> recipeExtras = new ArrayList<>();
						if (!extras.isEmpty()) {
							String[] extrass = extras.split(",");
							for (String extra : extrass) {
								String[] parts = extra.split("::");
								Item extraItem = Item.getBy(parts[1], ItemData.ID_AND_META);
								if (extraItem != null) {
									recipeExtras.add(new ItemWithQuantity(extraItem, Integer.parseInt(parts[0])));
								}
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

		}
		
		Set<Item> il = new LinkedHashSet<>(IItem.itemList);
		IItem.itemList.clear();
		IItem.itemList.addAll(il);
		
		for (Item item : IItem.itemList) {
			if (searchBy(item, RecipeData.ITEM).isEmpty()) {
				item.setPrimary(true);
			}
		}

	}

	/**
	 * Recherche basique de recette entre une donnée et un critère de
	 * comparaison
	 * 
	 * @param data
	 *            Doit correspondre au type indiqué par les valeurs de
	 *            {@link RecipeData}
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

	/**
	 * Renvoie la liste des ingrédients nécessaires pour un item
	 * @param item
	 * @param quantity
	 * @param owned
	 * @return
	 */
	public static List<ItemWithQuantity> getIngredientsNeeded(Item item, int quantity, List<ItemWithQuantity> owned) {
		
		Map<List<ItemWithQuantity>,Integer> ingredientsNeeded = getIngredientsNeededImpl(item, quantity, owned);
		
		return new ArrayList<>(ingredientsNeeded.keySet()).get(0);
	}
	
	/**
	 * Renvoie la liste des ingrédients nécessaires pour un item, avec la quantité d'étapes nécessaires
	 * @param item l'item à créer
	 * @param quantity la quantité à créer
	 * @param owned la liste des items déjà en stock
	 * @return Un couple entre la liste des ingrédients nécessaires, et la quantité d'étapes
	 */
	private static Map<List<ItemWithQuantity>, Integer> getIngredientsNeededImpl(Item item, int quantity, List<ItemWithQuantity> owned) {
		
		List<Recipe> recipes = Recipe.searchBy(item, RecipeData.ITEM);
		Map<List<ItemWithQuantity>, Integer> res = new LinkedHashMap<>();

		if (!item.isPrimary()) {
			for (Recipe recipe : recipes) {
				
			}
		}
		else {
			List<ItemWithQuantity> list = new ArrayList<>();
			list.add(new ItemWithQuantity(item, quantity));
			res.put(list, 1);
			
			if (owned.contains(list.get(0))) {
				// TODO
				ItemWithQuantity itemWithQuantity = owned.get(owned.indexOf(list.get(0)));
				itemWithQuantity.setQuantity(quantity);
			}
		}

		System.out.println(recipes);

		return res;
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
	public final List<ItemWithQuantity> getExtras() {
		return Collections.unmodifiableList(extras);
	}
	
	public List<ItemWithQuantity> getIngredients() {
		
		List<ItemWithQuantity> ingredients = new ArrayList<>();
		for (Item item : pattern) {
			if (item != null) {
				ItemWithQuantity itemWithQuantity = new ItemWithQuantity(item);
				if (ingredients.contains(itemWithQuantity)) {
					int index = ingredients.indexOf(itemWithQuantity);
					itemWithQuantity = ingredients.get(index);
					itemWithQuantity.setQuantity(itemWithQuantity.getQuantity() + 1);
				}
				else {
					ingredients.add(new ItemWithQuantity(item, 1));
				}
			}
		}
		ingredients.remove(new ItemWithQuantity(null));
		
		return Collections.unmodifiableList(ingredients);
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
