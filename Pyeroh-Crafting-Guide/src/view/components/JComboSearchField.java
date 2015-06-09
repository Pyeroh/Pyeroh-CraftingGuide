package view.components;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JScrollPane;

import model.impl.Item;
import model.impl.Item.ItemData;

import org.jdesktop.swingx.JXSearchField;

import sun.font.FontDesignMetrics;
import view.components.event.SearchedItemChangeListener;

public class JComboSearchField extends JXSearchField {

	private static final long serialVersionUID = 5987013368749228268L;

	@SuppressWarnings("unused")
	private JDropDownList list;

	private Item item;

	public JComboSearchField() {

		setInstantSearchDelay(300);
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!e.getActionCommand().isEmpty()) {

					Item finalItem = Item.getBy(e.getActionCommand(), ItemData.NAME);
					if (finalItem == null) {
						List<Item> searches = Item.searchBy(e.getActionCommand(), ItemData.NAME);
						if (searches.size() > 1) {
							list = new JDropDownList(JComboSearchField.this, searches);
						}
						else {
							if (searches.size() == 1) {
								finalItem = searches.get(0);
							}
							if (finalItem != null) {
								JComboSearchField.this.setItem(finalItem);
								JComboSearchField.this.setText(finalItem.getDisplayName());
							}
						}
					}
					else {
						JComboSearchField.this.setItem(finalItem);
					}

				}
			}
		});
	}

	public Item getItem() {
		return item;
	}

	private void setItem(Item item) {
		this.item = item;
		fireSearchedItemChanged(item);
	}

	public void addSearchedItemChangeListener(SearchedItemChangeListener listener) {
		listenerList.add(SearchedItemChangeListener.class, listener);
	}

	public void removeSearchedItemChangeListener(SearchedItemChangeListener listener) {
		listenerList.remove(SearchedItemChangeListener.class, listener);
	}

	public SearchedItemChangeListener[] getSearchedItemChangeListeners() {
		return listenerList.getListeners(SearchedItemChangeListener.class);
	}

	protected void fireSearchedItemChanged(Item item) {
		for (SearchedItemChangeListener listener : getSearchedItemChangeListeners()) {
			listener.searchedItemChanged(item);
		}
	}

}

class JDropDownList extends JDialog {

	private static final long serialVersionUID = 4346096598210553609L;

	private JComboSearchField parent;

	private JScrollPane scroll;

	private JHoverList<Item> list;

	public int getSelectedIndex() {
		return list.getSelectedIndex();
	}

	public JDropDownList(JComboSearchField parent, List<Item> results) {
		addWindowFocusListener(new WindowFocusListener() {

			@Override
			public void windowLostFocus(WindowEvent e) {
				dispose();
			}

			@Override
			public void windowGainedFocus(WindowEvent e) {
			}
		});
		setModalityType(ModalityType.MODELESS);
		setType(Type.POPUP);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setUndecorated(true);
		setResizable(false);

		this.parent = parent;
		scroll = new JScrollPane();
		getContentPane().add(scroll, BorderLayout.CENTER);

		list = new JHoverList<>();
		scroll.setViewportView(list);

		DefaultListModel<Item> model = new DefaultListModel<>();
		int mlength = 0;
		int max = this.parent.getWidth() + 80;
		for (Item result : results) {
			model.addElement(result);
			int length = FontDesignMetrics.getMetrics(this.parent.getFont()).stringWidth(result.getDisplayName());
			mlength = Math.max(length, mlength);
		}
		if (mlength > max)
			mlength = max;
		mlength = Math.max(mlength, max) + 50;
		list.setModel(model);

		list.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				selectItem();
			}
		});

		list.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					selectItem();
				}
			}
		});

		setSize(mlength + 70, 150);
		setLocation(this.parent.getLocationOnScreen().x, this.parent.getLocationOnScreen().y + this.parent.getHeight() + 5);
		setVisible(true);
	}

	private void selectItem() {
		JDropDownList.this.parent.setText(list.getSelectedValue().getDisplayName());
		dispose();
	}

}
