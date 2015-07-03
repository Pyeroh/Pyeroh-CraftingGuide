package model.interfaces;

import java.util.ArrayList;
import java.util.List;

import model.enums.ECraftingType;
import model.impl.Item;
import model.impl.ItemWithQuantity;
import model.impl.Recipe;

/**
 * Interface pour les recettes
 *
 * @author Pyeroh
 *
 */
public interface IRecipe extends IMod {

	/**
	 * Liste des recettes gérées par l'appli
	 */
	List<Recipe> recipeList = new ArrayList<>();

	/**
	 * @return l'item donné par la recette
	 */
	ItemWithQuantity getItem();

	/**
	 * @return les extras éventuels de la recette (des seaux vides pour un cake de Minecraft par exemple)
	 */
	List<ItemWithQuantity> getExtras();

	/**
	 * @return le pattern de craft de l'item. Selon le type de la recette, il y aura plus ou moins d'items dans le
	 *         tableau
	 */
	Item[] getPattern();

	/**
	 * @return le type de recette
	 */
	ECraftingType getType();

}
