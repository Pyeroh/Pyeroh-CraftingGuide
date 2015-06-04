package model;

import model.enums.EMod;
import model.interfaces.AbstractItem;

/**
 * Objet inconnu et qui ne sera pas impl�ment�
 *
 * @author Pyeroh
 *
 */
public class UnknownItem extends AbstractItem {

	public UnknownItem(String displayName) {
		super("", 0, "question.png", displayName, null, EMod.UNKNOWN);
	}

}
