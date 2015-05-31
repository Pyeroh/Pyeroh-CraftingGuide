package model.interfaces;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface pour les recettes
 * 
 * @author Pyeroh
 *
 */
public interface IRecipe extends IMod {

	/**
	 * Liste des recettes g�r�es par l'appli
	 */
	List<IRecipe> recipeList = new ArrayList<>();
	
}
