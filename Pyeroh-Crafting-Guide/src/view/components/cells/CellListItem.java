package view.components.cells;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import model.impl.Item;

public class CellListItem extends CellListCaracs {

	private static final long serialVersionUID = -489871563289609199L;

	protected JLabel image = new JLabel();

	private Item item;

	public CellListItem(Item item) {
		super(item.getDisplayName());
		this.item = item;

		lib_desc.setBounds(44, 0, 256, 30);

		image.setBounds(3, 3, 24, 24);
		if (this.item != null) {
			image.setIcon(new ImageIcon(scaleImage(this.item.getImage(), image)));
		}
		add(image);

		setSize(300, 30);
	}

	public Item getItem() {
		return item;
	}

}