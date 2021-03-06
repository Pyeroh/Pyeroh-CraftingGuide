package view.components.craft;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import model.impl.Item;
import model.impl.ItemWithQuantity;
import net.miginfocom.swing.MigLayout;
import view.components.cells.CellListCaracs;

public class CellEditQuantity extends JPanel {

	private static final long serialVersionUID = -489871563289609199L;

	private ButtonType buttonType;

	private ItemWithQuantity item;

	private JLabel lib_desc;

	private JLabel image;

	private JButton button;

	private JSpinner spinner;

	public CellEditQuantity(ItemWithQuantity item, ButtonType buttonType) {
		super();
		this.item = item;
		this.buttonType = buttonType;

		setSize(433, 45);
		setBackground(Color.white);
		setLayout(new MigLayout("", "[32px:32px:32px][286.00px,grow][70.00px:70.00px][32px:32px:32px]",
				"[32px:32px:32px]"));

		lib_desc = new JLabel(item.getItem().getDisplayName());
		lib_desc.setFont(new Font("Tahoma", Font.PLAIN, 13));
		add(lib_desc, "cell 1 0,grow");

		image = new JLabel();
		if (this.item != null) {
			image.setIcon(new ImageIcon(CellListCaracs
					.scaleImage(this.item.getItem().getImage(), new Dimension(32, 32))));
		}
		add(image, "cell 0 0");

		spinner = new JSpinner();
		((DefaultFormatter) ((JFormattedTextField) spinner.getEditor().getComponent(0)).getFormatter())
				.setCommitsOnValidEdit(true);
		spinner.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				setQuantity((int) spinner.getValue());

				Container container = CellEditQuantity.this.getParent();
				if (container != null && container instanceof FullQuantityItemPanel) {
					((FullQuantityItemPanel) container).getItemSet().add(CellEditQuantity.this.item);
				}
			}
		});
		for (Component iterable_element : spinner.getComponents()) {
			if (iterable_element instanceof JButton) {
				iterable_element.addMouseListener(new MouseAdapter() {

					@Override
					public void mouseReleased(MouseEvent e) {
						e.setSource(spinner);
						CellEditQuantity.this.dispatchEvent(e);
					}

				});
			}
		}
		spinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinner.setEnabled(buttonType != ButtonType.AJOUTER);
		spinner.setValue(item.getQuantity());
		add(spinner, "cell 2 0,grow");

		button = new JButton();
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setRolloverEnabled(true);
		button.setIcon(buttonType.getBaseIcon());
		button.setRolloverIcon(buttonType.getHoverIcon());
		button.setBorder(new LineBorder(Color.white));
		button.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// e.setSource(CellEditQuantity.this);
				CellEditQuantity.this.dispatchEvent(e);
			}
		});
		add(button, "cell 3 0,alignx right,growy");

	}

	/**
	 * @return {@link buttonType}
	 */
	public ButtonType getButtonType() {
		return buttonType;
	}

	/**
	 * @return {@link item}
	 */
	public Item getItem() {
		return item.getItem();
	}

	/**
	 * @return {@link quantity}
	 */
	public int getQuantity() {
		return item.getQuantity();
	}

	/**
	 * @param quantity
	 *            {@link quantity}
	 */
	public void setQuantity(int quantity) {
		item.setQuantity(quantity);
		spinner.setValue(quantity);
	}

	/**
	 * Type de bouton � afficher
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
			baseIcon = new ImageIcon(ButtonType.class.getResource(String.format("/gui/%s.png", this.name()
					.toLowerCase())));
			hoverIcon = new ImageIcon(ButtonType.class.getResource(String.format("/gui/%s_hover.png", this.name()
					.toLowerCase())));
		}

		public Icon getBaseIcon() {
			return baseIcon;
		}

		public Icon getHoverIcon() {
			return hoverIcon;
		}

	}

}