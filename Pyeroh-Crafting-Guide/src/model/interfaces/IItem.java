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
	 * Liste des objets gérés par l'appli
	 */
	List<IItem> itemList = new ArrayList<>();

	/**
	 * @return l'ID en jeu
	 */
	String getId();

	/**
	 * @return la métadonnée de l'objet
	 */
	int getMeta();

	/**
	 * @return le chemin de l'icône de l'objet
	 */
	String getIconName();

	/**
	 * @return la catégorie de l'objet
	 */
	ECategory getCategory();

}
