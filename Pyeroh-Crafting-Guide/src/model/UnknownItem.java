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

	public UnknownItem(String displayName) {
		super("unknown", 0, "unknown.png", displayName, null, EMod.UNKNOWN);
	}

}
