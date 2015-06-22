/**
 * 
 */
package model.impl;


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

}
