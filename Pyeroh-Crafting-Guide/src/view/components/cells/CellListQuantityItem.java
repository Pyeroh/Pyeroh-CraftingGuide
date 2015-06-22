package view.components.cells;

import model.impl.ItemWithQuantity;

public class CellListQuantityItem extends CellListItem {

	private static final long serialVersionUID = 5471797674503281211L;

	private int quantity;

	public CellListQuantityItem(ItemWithQuantity itemWithQuantity) {
		super(itemWithQuantity.getItem());
		this.quantity = itemWithQuantity.getQuantity();

		lib_desc.setText(getItem().getDisplayName()
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