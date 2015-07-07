/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

import model.impl.Item;
import model.impl.ItemWithQuantity;

/**
 * @author Pyeroh
 *
 */
public class ItemWQSet extends LinkedHashSet<ItemWithQuantity> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4465636615463026872L;

	public ItemWQSet() {
		super();
	}

	public ItemWQSet(Collection<? extends ItemWithQuantity> c) {
		super(c);
	}

	@Override
	public ItemWQSet clone() {
		ItemWQSet i = (ItemWQSet) super.clone();
		i.clear();

		for (ItemWithQuantity item : this) {
			i.add(item.clone());
		}

		return i;
	}

	public ItemWithQuantity get(Item item) {

		nettoyage();

		ItemWithQuantity itemWQ = new ItemWithQuantity(item);

		if (this.contains(itemWQ)) {
			int index = new ArrayList<>(this).indexOf(itemWQ);
			return get(index);
		}
		else {
			return null;
		}

	}

	@Override
	public boolean add(ItemWithQuantity item) {
		if (this.contains(item)) {

			ItemWithQuantity itemWQ = get(item.getItem());
			remove(itemWQ);
			itemWQ.setQuantity(item.getQuantity());

			return super.add(itemWQ);
		}
		else {
			return super.add(item);
		}
	}

	/**
	 * "Nettoie" le set des �l�ments dont la quantit� est 0
	 */
	private void nettoyage() {

		ItemWQSet toClear = new ItemWQSet();
		for (ItemWithQuantity itemResult : this) {
			if (itemResult.getQuantity() == 0) {
				toClear.add(itemResult);
			}
		}
		for (ItemWithQuantity itemToClear : toClear) {
			remove(itemToClear);
		}

	}

	public ItemWithQuantity get(int index) {

		nettoyage();

		ArrayList<ItemWithQuantity> i = new ArrayList<>(this);
		return i.get(index);

	}

	public int indexOf(ItemWithQuantity item) {
		return new ArrayList<>(this).indexOf(item);
	}

	/**
	 * Ajoute tous les �l�ments des listes list1 et list2, en les ajoutant entre
	 * eux si besoin
	 *
	 * @param itemWQSet
	 * @return
	 */
	public ItemWQSet addAll(ItemWQSet itemWQSet) {

		ItemWQSet resultList = new ItemWQSet();

		// Ajoute tous les �l�ments de la liste 1, en cherchant les �l�ments qui
		// sont pr�sents dans la liste 2, pour additionner les quantit�s
		for (ItemWithQuantity itemFromList1 : itemWQSet) {
			if (this.contains(itemFromList1)) {
				int index = indexOf(itemFromList1);
				resultList.add(itemFromList1.add(get(index).getQuantity()));
			}
			else {
				resultList.add(itemFromList1);
			}
		}
		// Ajoute tous les �lements restants dans la liste 2
		for (ItemWithQuantity itemFromList2 : this) {
			if (!resultList.contains(itemFromList2)) {
				resultList.add(itemFromList2);
			}
		}

		return resultList;
	}

	/**
	 * Retire la quantit� de chaque �l�ment de la liste pass�e en param�tre, et
	 * retire les �l�ments dont la quantit� a atteint z�ro
	 * 
	 * @param itemWQSet
	 * @return
	 */
	public ItemWQSet removeAll(ItemWQSet itemWQSet) {

		ItemWQSet resultList = new ItemWQSet();

		// Ajoute tous les �l�ments de la liste 1, en cherchant les �l�ments qui
		// sont pr�sents dans la liste 2, pour additionner les quantit�s
		for (ItemWithQuantity itemFromRemove : this) {
			if (itemWQSet.contains(itemFromRemove)) {
				int index = itemWQSet.indexOf(itemFromRemove);
				resultList.add(itemFromRemove.substract(Math.min(itemWQSet.get(index).getQuantity(),
						itemFromRemove.getQuantity())));
			}
			else {
				resultList.add(itemFromRemove);
			}
		}

		nettoyage();

		return resultList;
	}

}
