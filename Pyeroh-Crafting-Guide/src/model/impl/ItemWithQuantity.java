/**
 *
 */
package model.impl;


/**
 * Un item avec une quantit�
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
	 * Multiplie la quantit� de l'objet par la quantit� pass�e en param�tre
	 *
	 * @param quantity
	 * @return
	 */
	public ItemWithQuantity multiply(int quantity) {
		return new ItemWithQuantity(item, this.quantity * quantity);
	}

	/**
	 * Divise la quantit� de l'objet par la quantit� pass�e en param�tre
	 *
	 * @param quantity
	 * @return
	 */
	public ItemWithQuantity divide(int quantity) {
		return new ItemWithQuantity(item, (int) Math.ceil(this.quantity / Integer.valueOf(quantity).doubleValue()));
	}

	/**
	 * Ajoute � la quantit� de l'objet la quantit� pass�e en param�tre
	 *
	 * @param quantity
	 * @return
	 */
	public ItemWithQuantity add(int quantity) {
		return new ItemWithQuantity(item, this.quantity + quantity);
	}

	/**
	 * Soustrait � la quantit� de l'objet la quantit� pass�e en param�tre
	 *
	 * @param quantity
	 * @return
	 */
	public ItemWithQuantity substract(int quantity) {
		return new ItemWithQuantity(item, this.quantity - quantity);
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
	public ItemWithQuantity clone() {
		return new ItemWithQuantity(item, quantity);
	}

	@Override
	public String toString() {
		return quantity + "x " + item.toString();
	}

}
