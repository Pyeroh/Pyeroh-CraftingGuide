package model.interfaces;

import model.enums.EMod;

/**
 * Interface générique pour ce qui est lié à un mod
 * 
 * @author Pyeroh
 *
 */
public interface IMod {

	/**
	 * @return le mod d'où vient l'item
	 */
	EMod getMod();

}
