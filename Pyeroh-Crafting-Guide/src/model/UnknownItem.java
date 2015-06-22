package model;

import model.enums.EMod;
import model.impl.Item;

/**
 * Objet inconnu et qui ne sera pas implémenté
 *
 * @author Pyeroh
 *
 */
public class UnknownItem extends Item {

	public UnknownItem(String ID, String displayName) {
		super(ID, 0, displayName, "", "unknown.png", null, EMod.UNKNOWN, true);
	}

}
