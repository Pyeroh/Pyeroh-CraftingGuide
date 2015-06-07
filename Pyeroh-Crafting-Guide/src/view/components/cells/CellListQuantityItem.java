package view.components.cells;

import model.impl.Item;

public class CellListQuantityItem extends CellListItem {

	private static final long serialVersionUID = 5471797674503281211L;

	private int quantity;

	public CellListQuantityItem(Item item, int quantity) {
		super(item);
		this.quantity = quantity;

		lib_desc.setText(item.getDisplayName()
				+ (getQuantity() != 1 ? " x" + getQuantity() : ""));

	}

	/**
	 * @return {@link #serialversionuid}
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return {@link #quantity}
	 */
	public int getQuantity() {
		return quantity;
	}

}