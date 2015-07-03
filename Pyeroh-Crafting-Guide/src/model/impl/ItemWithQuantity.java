/**
 *
 */
package model.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * Un item avec une quantité
 *
 * @author Pyeroh
 *
 */
public class ItemWithQuantity {

	private Item item;

	private int quantity;

	public ItemWithQuantity(Item item, int quantity) {
		this.item = item;
		this.quantity = quantity;
	}

	public ItemWithQuantity(Item item) {
		this(item, 1);
	}

	/**
	 * Multiplie la quantité de l'objet par la quantité passée en paramètre
	 *
	 * @param quantity
	 * @return
	 */
	public ItemWithQuantity multiply(int quantity) {
		this.quantity *= quantity;
		return new ItemWithQuantity(item, this.quantity);
	}

	/**
	 * Divise la quantité de l'objet par la quantité passée en paramètre
	 *
	 * @param quantity
	 * @return
	 */
	public ItemWithQuantity divide(int quantity) {
		this.quantity = (int) Math.ceil(this.quantity / Integer.valueOf(quantity).doubleValue());
		return new ItemWithQuantity(item, this.quantity);
	}

	/**
	 * Ajoute à la quantité de l'objet la quantité passée en paramètre
	 *
	 * @param quantity
	 * @return
	 */
	public ItemWithQuantity add(int quantity) {
		this.quantity += quantity;
		return new ItemWithQuantity(item, this.quantity);
	}

	/**
	 * Soustrait à la quantité de l'objet la quantité passée en paramètre
	 *
	 * @param quantity
	 * @return
	 */
	public ItemWithQuantity substract(int quantity) {
		this.quantity -= quantity;
		return new ItemWithQuantity(item, this.quantity);
	}

	/**
	 * Ajoute tous les éléments des listes list1 et list2, en les ajoutant entre
	 * eux si besoin
	 *
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static List<ItemWithQuantity> addAll(List<ItemWithQuantity> list1, List<ItemWithQuantity> list2) {

		List<ItemWithQuantity> resultList = new ArrayList<>();

		// Ajoute tous les éléments de la liste 1, en cherchant les éléments qui
		// sont présents dans la liste 2, pour additionner les quantités
		for (ItemWithQuantity itemFromList1 : list1) {
			if (list2.contains(itemFromList1)) {
				int index = list2.indexOf(itemFromList1);
				resultList.add(itemFromList1.add(list2.get(index).getQuantity()));
			}
			else {
				resultList.add(itemFromList1);
			}
		}
		// Ajoute tous les élements restants dans la liste 2
		for (ItemWithQuantity itemFromList2 : list2) {
			if (!resultList.contains(itemFromList2)) {
				resultList.add(itemFromList2);
			}
		}

		return resultList;
	}

	public static List<ItemWithQuantity> removeAll(List<ItemWithQuantity> removeFrom, List<ItemWithQuantity> toRemove) {

		List<ItemWithQuantity> resultList = new ArrayList<>();

		// Ajoute tous les éléments de la liste 1, en cherchant les éléments qui
		// sont présents dans la liste 2, pour additionner les quantités
		for (ItemWithQuantity itemFromRemove : removeFrom) {
			if (toRemove.contains(itemFromRemove)) {
				int index = toRemove.indexOf(itemFromRemove);
				resultList.add(itemFromRemove.substract(Math.min(toRemove.get(index).getQuantity(), itemFromRemove.getQuantity())));
			}
			else {
				resultList.add(itemFromRemove);
			}
		}

		List<ItemWithQuantity> toClear = new ArrayList<>();
		for (ItemWithQuantity itemResult : resultList) {
			if (itemResult.getQuantity() == 0) {
				toClear.add(itemResult);
			}
		}
		for (ItemWithQuantity itemToClear : toClear) {
			resultList.remove(itemToClear);
		}

		return resultList;
	}

	/**
	 * @return {@link item}
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * @return {@link quantity}
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            {@link quantity}
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof Item) {
			Item other = (Item) obj;
			if (item == null) {
				return false;
			}
			else {
				return item.equals(other);
			}
		}
		if (!(obj instanceof ItemWithQuantity)) {
			return false;
		}
		ItemWithQuantity other = (ItemWithQuantity) obj;
		if (item == null) {
			if (other.item != null) {
				return false;
			}
		}
		else if (!item.equals(other.item)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return quantity + "x " + item.toString();
	}

}
