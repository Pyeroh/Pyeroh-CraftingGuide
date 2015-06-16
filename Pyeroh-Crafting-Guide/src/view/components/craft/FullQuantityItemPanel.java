/**
 *
 */
package view.components.craft;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import model.impl.Item;
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

	private List<Item> itemList = new ArrayList<>();

	private Map<Item, Integer> itemsQuantity = new LinkedHashMap<>();

	private Map<Item, CellEditQuantity> itemsToCells = new LinkedHashMap<>();

	private ButtonType buttonType;

	public FullQuantityItemPanel(ButtonType buttonType) {
		super();
		this.buttonType = buttonType;
		setLayout(null);
		setBackground(Color.white);
		reloadCells();
	}

	private void reloadCells() {

		for (Component component : getComponents()) {
			CellEditQuantity ceq = (CellEditQuantity) component;
			itemsQuantity.put(ceq.getItem(), ceq.getQuantity());
		}

		setSize(0, 0);
		removeAll();
		itemsToCells.clear();
		if (!itemList.isEmpty()) {
			for (Item item : itemList) {
				CellEditQuantity ceq = new CellEditQuantity(item, buttonType);
				ceq.addMouseListener(new MouseAdapter() {

					@Override
					public void mouseReleased(MouseEvent e) {
						CellEditQuantity ceq = (CellEditQuantity) e.getSource();
						remove(ceq.getItem());
					}
				});
				ceq.setQuantity(itemsQuantity.get(item));
				itemsToCells.put(item, ceq);
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

	public Map<Item, CellEditQuantity> getItemsToCellsMap() {
		return Collections.unmodifiableMap(itemsToCells);
	}

	protected Map<Item, Integer> getItemsQuantityMap() {
		return itemsQuantity;
	}

	public int itemListSize() {
		return itemList.size();
	}

	public boolean isEmpty() {
		return itemList.isEmpty();
	}

	public boolean contains(Item item) {
		return itemList.contains(item);
	}

	public Iterator<Item> iterator() {
		return itemList.iterator();
	}

	public boolean add(Item item) {
		boolean b = itemList.add(item);
		itemsQuantity.put(item, 1);
		reloadCells();
		return b;
	}

	public boolean remove(Item item) {
		boolean b = itemList.remove(item);
		itemsQuantity.remove(item);
		reloadCells();
		return b;
	}

	public boolean containsAll(Collection<?> paramCollection) {
		return itemList.containsAll(paramCollection);
	}

	public boolean addAll(Collection<? extends Item> paramCollection) {
		boolean b = itemList.addAll(paramCollection);
		for (Item item : paramCollection) {
			itemsQuantity.put(item, 1);
		}
		reloadCells();
		return b;
	}

	public boolean addAll(int paramInt, Collection<? extends Item> paramCollection) {
		boolean b = itemList.addAll(paramInt, paramCollection);
		for (Item item : paramCollection) {
			itemsQuantity.put(item, 1);
		}
		reloadCells();
		return b;
	}

	public boolean removeAll(Collection<? extends Item> paramCollection) {
		boolean b = itemList.removeAll(paramCollection);
		for (Item item : paramCollection) {
			itemsQuantity.remove(item);
		}
		reloadCells();
		return b;
	}

	public boolean retainAll(Collection<? extends Item> paramCollection) {
		boolean b = itemList.retainAll(paramCollection);
		for (Item item : itemsQuantity.keySet()) {
			if (!paramCollection.contains(item)) {
				itemsQuantity.remove(item);
			}
		}
		reloadCells();
		return b;
	}

	public void clear() {
		itemList.clear();
		itemsQuantity.clear();
		reloadCells();
	}

	public Item get(int paramInt) {
		return itemList.get(paramInt);
	}

	public Item set(int index, Item item) {
		Item set = itemList.set(index, item);
		int q = itemsQuantity.remove(set);
		itemsQuantity.put(item, q);
		reloadCells();
		return set;
	}

	public void add(int index, Item item) {
		itemList.add(index, item);
		itemsQuantity.put(item, 1);
		reloadCells();
	}

	public Item itemListremove(int paramInt) {
		Item remove = itemList.remove(paramInt);
		itemsQuantity.remove(remove);
		reloadCells();
		return remove;
	}

	public int indexOf(Item item) {
		return itemList.indexOf(item);
	}

	public int lastIndexOf(Item item) {
		return itemList.lastIndexOf(item);
	}

	public List<Item> subList(int paramInt1, int paramInt2) {
		return itemList.subList(paramInt1, paramInt2);
	}

}
