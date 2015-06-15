package view.components.cells;

import java.awt.Color;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;

import model.enums.ECategory;
import model.enums.EMod;
import model.impl.Item;

public class CellListEditQuantity extends CellListItem {

	private static final long serialVersionUID = -489871563289609199L;

	private ButtonType buttonType;

	private JButton button;

	private JSpinner spinner;

	public CellListEditQuantity(Item item, ButtonType buttonType) {
		super(new Item("bed", 0, "bidule", "bed.png", ECategory.DECORATION, EMod.MINECRAFT));
		setBorder(new LineBorder(new Color(0, 0, 0)));
		this.buttonType = buttonType;

		setSize(500, 32);
		setBackground(Color.white);
		setLayout(null);

		lib_desc.setBounds(44, 0, 355, 32);

		image.setBounds(0, 0, 32, 32);

		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinner.setBounds(409, 0, 50, 32);
		add(spinner);

		button = new JButton();
		button.setBounds(469, 0, 32, 32);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setRolloverEnabled(true);
		button.setIcon(buttonType.getBaseIcon());
		button.setRolloverIcon(buttonType.getHoverIcon());
		add(button);

	}

	/**
	 * @return {@link buttonType}
	 */
	public ButtonType getButtonType() {
		return buttonType;
	}

	/**
	 * Type de bouton à afficher
	 *
	 * @author Pyeroh
	 *
	 */
	public static enum ButtonType {

		ANNULER,

		AJOUTER,

		RETIRER;

		private Icon baseIcon;

		private Icon hoverIcon;

		private ButtonType() {
			baseIcon = new ImageIcon(ButtonType.class.getResource(String.format("/gui/%s.png", this.name().toLowerCase())));
			hoverIcon = new ImageIcon(ButtonType.class.getResource(String.format("/gui/%s_hover.png", this.name().toLowerCase())));
		}

		public Icon getBaseIcon() {
			return baseIcon;
		}

		public Icon getHoverIcon() {
			return hoverIcon;
		}

	}

}