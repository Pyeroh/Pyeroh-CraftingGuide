package model.interfaces;

import java.util.ArrayList;
import java.util.List;

import model.enums.ECategory;

/**
 * Interface pour les items
 *
 * @author Pyeroh
 *
 */
public interface IItem extends IMod {

	/**
	 * Liste des objets g�r�s par l'appli
	 */
	List<AbstractItem> itemList = new ArrayList<>();

	/**
	 * @return l'ID en jeu
	 */
	String getId();

	/**
	 * @return la m�tadonn�e de l'objet
	 */
	int getMeta();

	/**
	 * @return le nom � afficher
	 */
	String getDisplayName();

	/**
	 * @return le chemin de l'ic�ne de l'objet
	 */
	String getIconName();

	/**
	 * @return la cat�gorie de l'objet
	 */
	ECategory getCategory();

}
