package view.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import model.Messages;
import model.impl.Item;
import model.impl.Item.ItemData;
import model.impl.Recipe;
import model.impl.Recipe.RecipeData;
import view.Launch;
import view.components.cells.CellListItem;
import view.components.cells.CellListQuantityItem;

/**
 * Un panel pour afficher les informations d'un item
 *
 * @author Pyeroh
 *
 */
public class ItemDialog extends JDialog {

	/**
	 *
	 */
	private static final long serialVersionUID = 8397804417232569776L;

	private final Font MINECRAFTIA = Launch.getMinecraftia().deriveFont(12f);

	private String title;

	private Item item;

	private MCImage img_mainImg;

	private JLabel lib_item;

	private JButton btn_craftingPlan;

	private JScrollPane scrpan_recipes;

	private JScrollPane scrpan_usage;

	private JScrollPane scrpan_fromCategory;

	private JHoverList<CellListItem> list_usage;

	private JHoverList<CellListItem> list_fromCategory;

	public ItemDialog(Item item, final JFrame frame) {
		super();
		this.item = item;
		this.title = frame.getTitle();

		setIconImage(frame.getIconImage());
		setSize(820, 660);
		setMinimumSize(getSize());
		setMaximumSize(getSize());
		setPreferredSize(getSize());
		getContentPane().setLayout(null);
		setLocationRelativeTo(frame);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);

		img_mainImg = new MCImage((JPanel) this.getContentPane());
		img_mainImg.setBounds(6, 6, 64, 64);
		img_mainImg.toggleHoverable();
		getContentPane().add(img_mainImg);

		lib_item = new JLabel();
		lib_item.setBounds(82, 6, 480, 22);
		lib_item.setFont(MINECRAFTIA);
		getContentPane().add(lib_item);

		btn_craftingPlan = new JButton(Messages.getString("ItemPane.btn_craftingPlan.text")); //$NON-NLS-1$
		btn_craftingPlan.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				e.setSource(img_mainImg);
				frame.getContentPane().dispatchEvent(e);
			}

		});
		btn_craftingPlan.setBounds(80, 39, 166, 31);
		getContentPane().add(btn_craftingPlan);

		scrpan_recipes = new JScrollPane();
		scrpan_recipes.setBounds(6, 82, 796, 305);
		scrpan_recipes.getViewport().setSize(770, 280);
		scrpan_recipes.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), Messages
				.getString("ItemPane.scrpan_recipes.borderTitle"), TitledBorder.LEADING, TitledBorder.TOP, MINECRAFTIA, null)); //$NON-NLS-1$
		scrpan_recipes.getVerticalScrollBar().setUnitIncrement(260);
		getContentPane().add(scrpan_recipes);

		scrpan_usage = new JScrollPane();
		scrpan_usage.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), Messages
				.getString("ItemPane.scrpan_usage.borderTitle"), TitledBorder.LEADING, TitledBorder.TOP, MINECRAFTIA, null)); //$NON-NLS-1$
		scrpan_usage.setBounds(6, 399, 392, 220);
		getContentPane().add(scrpan_usage);

		MouseAdapter changeItem = new MouseAdapter() {

			@SuppressWarnings("unchecked")
			@Override
			public void mouseReleased(MouseEvent e) {

				JHoverList<CellListItem> list = (JHoverList<CellListItem>) e.getSource();
				if (!list.isSelectionEmpty() && e.getClickCount() == 2) {
					reloadItem(list.getSelectedValue().getItem());
				}

			}
		};

		list_usage = new JHoverList<>();
		list_usage.addMouseListener(changeItem);
		scrpan_usage.setViewportView(list_usage);

		scrpan_fromCategory = new JScrollPane();
		scrpan_fromCategory.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), null, TitledBorder.LEADING,
				TitledBorder.TOP, MINECRAFTIA, null));
		scrpan_fromCategory.setBounds(410, 399, 392, 220);
		getContentPane().add(scrpan_fromCategory);

		list_fromCategory = new JHoverList<>();
		list_fromCategory.addMouseListener(changeItem);
		scrpan_fromCategory.setViewportView(list_fromCategory);

		loadItem();

	}

	private void loadItem() {
		img_mainImg.setItem(item);
		lib_item.setText(item.getDisplayName());

		FullRecipePanel frp = new FullRecipePanel(item);
		frp.addMouseListener(new MouseAdapter() {

			@SuppressWarnings("unchecked")
			@Override
			public void mouseReleased(MouseEvent e) {
				Object source = e.getSource();
				Item item = null;

				if (source instanceof MCImage) {
					MCImage mcImage = (MCImage) source;
					item = mcImage.getItem();
				}
				else if (source instanceof JHoverList) {
					JHoverList<CellListQuantityItem> list = (JHoverList<CellListQuantityItem>) source;
					item = list.getSelectedValue().getItem();
				}
				else {
					return;
				}


				reloadItem(item);
			}

		});
		scrpan_recipes.setViewportView(frp);

		((TitledBorder) scrpan_fromCategory.getBorder()).setTitle(String.format(
				Messages.getString("ItemPane.scrpan_fromCategory.borderTitle"), item.getCategory().getDisplayName().toLowerCase()));

		List<Recipe> usedToMake = Recipe.searchBy(item, RecipeData.CONTENT);
		Set<Item> usedToMakeItemSet = new LinkedHashSet<>();
		for (Recipe recipe : usedToMake) {
			usedToMakeItemSet.add(recipe.getItem());
		}
		DefaultListModel<CellListItem> model = new DefaultListModel<>();
		for (Item item : usedToMakeItemSet) {
			model.addElement(new CellListItem(item));
		}
		list_usage.setModel(model);

		List<Item> fromCategory = Item.searchBy(item.getCategory().name(), ItemData.CATEGORY);
		model = new DefaultListModel<>();
		for (Item itemFromCat : fromCategory) {
			model.addElement(new CellListItem(itemFromCat));
		}
		list_fromCategory.setModel(model);

		setTitle(String.format("%s - %s", title, item.getDisplayName()));

		repaint();

	}

	private void reloadItem(Item item) {
		this.item = item;
		loadItem();
	}

}
