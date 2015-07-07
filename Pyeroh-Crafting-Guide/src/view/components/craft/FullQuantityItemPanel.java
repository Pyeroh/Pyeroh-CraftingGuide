/**
 *
 */
package view.components.craft;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import model.ItemWQSet;
import model.impl.ItemWithQuantity;
import view.components.craft.CellEditQuantity.ButtonType;

/**
 * JPanel fait pour afficher tous les CellListEditQuantity qu'il contient
 *
 * @author Pyeroh
 *
 */
public class FullQuantityItemPanel extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = 8446652883704121959L;

	private ItemWQSet itemSet = new ItemWQSet();

	private ButtonType buttonType;

	public FullQuantityItemPanel(ButtonType buttonType) {
		super();
		this.buttonType = buttonType;
		setLayout(null);
		setBackground(Color.white);
		reloadCells();
	}

	private void reloadCells() {

		// On retire tous les CEQ
		// On recharge les CEQ avec le itemSet

		setSize(0, 0);
		removeAll();
		if (!itemSet.isEmpty()) {
			for (ItemWithQuantity item : itemSet) {
				CellEditQuantity ceq = new CellEditQuantity(item, buttonType);
				ceq.addMouseListener(new MouseAdapter() {

					@Override
					public void mouseReleased(MouseEvent e) {
						if (e.getSource() instanceof JButton) {
							JButton source = (JButton) e.getSource();
							CellEditQuantity ceq = (CellEditQuantity) source.getParent();
							remove(new ItemWithQuantity(ceq.getItem()));
							FullQuantityItemPanel.this.dispatchEvent(e);
						}
						else if (e.getSource() instanceof JSpinner) {
							FullQuantityItemPanel.this.dispatchEvent(e);
						}
					}
				});
				setSize(0, getHeight() + ceq.getHeight());
				ceq.setLocation(0, getHeight() - ceq.getHeight());
				add(ceq);
			}
		}

	}

	@Override
	public void setSize(Dimension d) {
		setSize(d.width, d.height);
	}

	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		setMinimumSize(getSize());
		setMaximumSize(getSize());
		setPreferredSize(getSize());

		for (Component component : getComponents()) {
			component.setSize(getWidth(), component.getHeight());
		}
	}

	public ItemWQSet getItemSet() {
		return itemSet;
	}

	public void clear() {
		itemSet.clear();
		reloadCells();
	}

	public void add(ItemWithQuantity item) {
		itemSet.add(item);

		reloadCells();
	}
	
	public void remove(ItemWithQuantity item) {
		itemSet.remove(item);
		
		reloadCells();
	}
	
}
