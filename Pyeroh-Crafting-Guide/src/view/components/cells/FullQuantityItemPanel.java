/**
 *
 */
package view.components.cells;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import model.impl.Item;
import view.components.cells.CellListEditQuantity.ButtonType;

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

	public FullQuantityItemPanel() {
		super();
		setLayout(null);
		setBackground(Color.white);
		reloadCells();
	}

	private void reloadCells() {

		setSize(0, 0);
		if (!itemList.isEmpty()) {
			for (Item item : itemList) {
				CellListEditQuantity cleq = new CellListEditQuantity(item, ButtonType.ANNULER);
				setSize(0, getHeight() + cleq.getHeight());
				cleq.setLocation(0, getHeight() - cleq.getHeight());
				add(cleq);
			}
		}
	}

	@Override
	public void setBounds(Rectangle r) {
		setBounds(r.x, r.y, 0, r.height);
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, 500, height);
	}

	@Override
	public void setSize(Dimension d) {
		setSize(0, d.height);
	}

	@Override
	public void setSize(int width, int height) {
		super.setSize(500, height);

		// A laisser pour le JScrollPane
		setMinimumSize(getSize());
		setMaximumSize(getSize());
		setPreferredSize(getSize());
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
		reloadCells();
		return b;
	}

	public boolean remove(Item item) {
		boolean b = itemList.remove(item);
		reloadCells();
		return b;
	}

	public boolean containsAll(Collection<?> paramCollection) {
		return itemList.containsAll(paramCollection);
	}

	public boolean addAll(Collection<? extends Item> paramCollection) {
		boolean b = itemList.addAll(paramCollection);
		reloadCells();
		return b;
	}

	public boolean addAll(int paramInt, Collection<? extends Item> paramCollection) {
		boolean b = itemList.addAll(paramInt, paramCollection);
		reloadCells();
		return b;
	}

	public boolean removeAll(Collection<?> paramCollection) {
		boolean b = itemList.removeAll(paramCollection);
		reloadCells();
		return b;
	}

	public boolean retainAll(Collection<?> paramCollection) {
		boolean b = itemList.retainAll(paramCollection);
		reloadCells();
		return b;
	}

	public void clear() {
		itemList.clear();
		reloadCells();
	}

	public Item get(int paramInt) {
		return itemList.get(paramInt);
	}

	public Item set(int paramInt, Item paramE) {
		Item set = itemList.set(paramInt, paramE);
		reloadCells();
		return set;
	}

	public void add(int paramInt, Item paramE) {
		itemList.add(paramInt, paramE);
		reloadCells();
	}

	public Item itemListremove(int paramInt) {
		Item remove = itemList.remove(paramInt);
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
