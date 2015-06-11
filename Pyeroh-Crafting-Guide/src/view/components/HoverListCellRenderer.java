package view.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;

import model.impl.Item;
import view.components.cells.CellListItem;

public class HoverListCellRenderer extends DefaultListCellRenderer {

	private static final long serialVersionUID = 9037091771053473873L;
	private static final Color HOVER_COLOR = new Color(208,233,253);
	private static final Color SELECTED_COLOR = new Color(247,212,170);
	private int hoverIndex = -1;
	private MouseAdapter handler;
	private JList<?> list;

	public HoverListCellRenderer(JList<?> list) {
		super();
		this.list = list;
	}

	@SuppressWarnings("rawtypes")
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

		/*JList thisvalue = (JList) value;
		thisvalue.setPreferredSize(new Dimension(list.getWidth()-20, thisvalue.getHeight()));
		thisvalue.setMinimumSize(thisvalue.getPreferredSize());
		thisvalue.setMaximumSize(thisvalue.getPreferredSize());*/

		if (value != null) {
			if (value instanceof Item) {
				Item item = (Item) value;
				setIcon(new ImageIcon(item.getImage()));
				setText(item.getDisplayName());

				if (!isSelected) {
					setBackground(index == hoverIndex ? HOVER_COLOR : (list.getBackground().getClass().equals(Color.class) ? list.getBackground() : Color.white));
				}
				else {
					setBackground(SELECTED_COLOR);
				}
				setForeground(Color.black);

				return this;
			} else if (value instanceof CellListItem) {
				CellListItem cell = (CellListItem) value;

				cell.setPreferredSize(new Dimension(list.getWidth()-20, cell.getHeight()));
				cell.setMinimumSize(cell.getPreferredSize());
				cell.setMaximumSize(cell.getPreferredSize());

				if (!isSelected) {
					cell.setBackground(index == hoverIndex ? HOVER_COLOR : (list.getBackground().getClass().equals(Color.class) ? list.getBackground() : Color.white));
				}
				else {
					cell.setBackground(SELECTED_COLOR);
				}
				cell.setForeground(Color.black);

				return cell;
			}
		}

		return this;

	}

	public MouseAdapter getHandler() {
		if (handler == null) {
			handler = new HoverMouseHandler(list);
		}
		return handler;
	}

	/**
	 * Donne l'index survolé par la souris
	 * @return -1 s'il n'y a pas de survol, l'index survolé sinon
	 */
	public int getHoverIndex() {
		return hoverIndex;
	}

	class HoverMouseHandler extends MouseAdapter {

		private final JList<?> flist;

		public HoverMouseHandler(JList<?> list) {
			this.flist = list;
		}

		public void mouseExited(MouseEvent e) {
			setHoverIndex(-1);
		}

		public void mouseMoved(MouseEvent e) {
			if (flist.getModel().getSize()!=0) {
				int index = flist.locationToIndex(e.getPoint());
				setHoverIndex(flist.getCellBounds(index, index).contains(
						e.getPoint()) ? index : -1);
			}
		}

		private void setHoverIndex(int index) {
			if (hoverIndex == index) return;
			hoverIndex = index;
			flist.repaint();
		}
	}
}