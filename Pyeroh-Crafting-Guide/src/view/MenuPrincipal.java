/**
 *
 */
package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import model.Messages;
import model.enums.ECategory;
import model.enums.EMod;
import model.impl.Item;
import model.impl.Item.ItemData;

import org.jdesktop.swingx.JXTree;

import view.components.cells.CellListCaracs;
import view.components.core.JComboSearchField;
import view.components.core.MCImage;
import view.components.craft.CellEditQuantity;
import view.components.craft.CellEditQuantity.ButtonType;
import view.components.craft.FullQuantityItemPanel;
import view.components.event.SearchedItemChangeListener;
import view.components.infos.ItemDialog;

/**
 * Fenêtre principale pour l'affichage
 *
 * @author Pyeroh
 *
 */
public class MenuPrincipal extends JFrame {

	private static final Font MINECRAFTIA = Launch.getMinecraftia().deriveFont(12f);

	/**
	 *
	 */
	private static final long serialVersionUID = 7504976316824014595L;

	private JTabbedPane tabpan_mainContainer;

	private JPanel pan_configure;

	private JPanel pan_browse;

	private JPanel pan_craft;

	private JCheckBox chk_enderStorage;

	private JCheckBox chk_appliedEnergistics;

	private JButton btn_brMinecraft;

	private JXTree tree_browsed;

	private JLabel lib_modName;

	private JLabel lib_modAuthor;

	private JScrollPane scrpan_browsed;

	private ItemDialog itemDialog;

	private JPanel pan_itemsToMake;

	private JScrollPane scrpan_itemsToMake;

	private JComboSearchField search_itemsToMake;

	private JPanel pan_ingredients;

	private JScrollPane scrpan_steps;

	private JScrollPane scrpan_ingredientsPresent;

	private JComboSearchField search_ingredientsPresent;
	private JScrollPane scrpan_ingredientsNeeded;
	private JComboSearchField search_item;

	public MenuPrincipal() {
		super();
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(MenuPrincipal.class.getResource("/gui/items/minecraft/icons/crafting_table.png")));
		setTitle("Pyeroh Crafting Guide v1");

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(818, 700);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		MouseAdapter browseEvent = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				JButton button = (JButton) e.getSource();

				lib_modAuthor.setVisible(true);
				lib_modName.setVisible(true);
				scrpan_browsed.setVisible(true);

				if (button == btn_brMinecraft) {
					DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
					List<ECategory> categories = ECategory.getCategoriesByMod(EMod.MINECRAFT);
					for (ECategory cat : categories) {
						List<Item> items = Item.searchBy(cat.name(), ItemData.CATEGORY);
						DefaultMutableTreeNode node = new DefaultMutableTreeNode(cat.getDisplayName());
						for (Item item : items) {
							node.add(new DefaultMutableTreeNode(item));
						}
						root.add(node);
					}
					tree_browsed.setModel(new DefaultTreeModel(root));

					lib_modAuthor
							.setText(String.format(Messages.getString("MenuPrincipal.lib_modAuthor.text"), EMod.MINECRAFT.getCreator()));
					lib_modName.setText(EMod.MINECRAFT.getModName());

				} else {
					throw new UnsupportedOperationException(String.format("Bouton de navigation non géré : %s", button.getName()));
				}

			}

		};

		search_item = new JComboSearchField();
		search_item.setBounds(625, 3, 181, 28);
		search_item.setPrompt(Messages.getString("MenuPrincipal.search_itemsToMake.prompt"));
		getContentPane().add(search_item);
		search_item.setColumns(10);

		tabpan_mainContainer = new JTabbedPane(JTabbedPane.TOP);
		tabpan_mainContainer.setBounds(0, 6, 812, 666);
		getContentPane().add(tabpan_mainContainer);

		pan_configure = new JPanel();
		tabpan_mainContainer.addTab(Messages.getString("MenuPrincipal.pan_configure.title"), null, pan_configure, null); //$NON-NLS-1$
		pan_configure.setLayout(null);

		chk_enderStorage = new JCheckBox(Messages.getString("MenuPrincipal.chk_enderStorage.text")); //$NON-NLS-1$
		chk_enderStorage.setBounds(6, 6, 104, 18);
		pan_configure.add(chk_enderStorage);

		chk_appliedEnergistics = new JCheckBox(Messages.getString("MenuPrincipal.chk_appliedEnergistics.text"));
		chk_appliedEnergistics.setBounds(6, 36, 138, 18);
		pan_configure.add(chk_appliedEnergistics);

		pan_browse = new JPanel();
		tabpan_mainContainer.addTab(Messages.getString("MenuPrincipal.pan_browse.title"), null, pan_browse, null); //$NON-NLS-1$
		pan_browse.setLayout(null);

		btn_brMinecraft = new JButton(Messages.getString("MenuPrincipal.btn_brMinecraft.text")); //$NON-NLS-1$
		btn_brMinecraft.addMouseListener(browseEvent);
		btn_brMinecraft.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/gui/items/minecraft/icons/grass_block.png")));
		btn_brMinecraft.setBounds(6, 6, 169, 100);
		btn_brMinecraft.setName("btn_brMinecraft");
		pan_browse.add(btn_brMinecraft);

		lib_modName = new JLabel(Messages.getString("MenuPrincipal.lib_modName.text"));
		lib_modName.setVisible(false);
		lib_modName.setFont(Launch.getMinecraftia().deriveFont(14f));
		lib_modName.setBounds(543, 6, 263, 16);
		pan_browse.add(lib_modName);

		lib_modAuthor = new JLabel(); //$NON-NLS-1$
		lib_modAuthor.setVisible(false);
		lib_modAuthor.setFont(new Font("SansSerif", Font.ITALIC, 12));
		lib_modAuthor.setBounds(543, 32, 138, 16);
		pan_browse.add(lib_modAuthor);

		scrpan_browsed = new JScrollPane();
		scrpan_browsed.setVisible(false);
		scrpan_browsed.setBounds(543, 60, 263, 495);
		pan_browse.add(scrpan_browsed);

		tree_browsed = new JXTree();
		tree_browsed.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				TreePath tp = tree_browsed.getPathForLocation(e.getX(), e.getY());
				if (e.getClickCount() == 2) {
					if (tp != null) {
						DefaultMutableTreeNode node = (DefaultMutableTreeNode) tp.getLastPathComponent();
						if (node.getUserObject() instanceof Item) {
							Item selectedItem = (Item) node.getUserObject();
							itemDialog = new ItemDialog(selectedItem, MenuPrincipal.this);
							itemDialog.setVisible(true);
						}
					}
				}
			}
		});
		tree_browsed.setRootVisible(false);
		tree_browsed.setCellRenderer(new DefaultTreeCellRenderer() {

			private static final long serialVersionUID = 1L;

			@Override
			public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row,
					boolean hasFocus) {

				DefaultTreeCellRenderer render = (DefaultTreeCellRenderer) super.getTreeCellRendererComponent(tree, value, sel, expanded,
						leaf, row, hasFocus);
				Object obj = ((DefaultMutableTreeNode) value).getUserObject();

				if (obj instanceof Item) {
					Item item = (Item) obj;

					render.setIcon(new ImageIcon(CellListCaracs.scaleImage(item.getImage(), new Dimension(24, 24))));
					render.setText(item.getDisplayName());
				} else {
					render.setIcon(null);
				}

				return render;
			}

		});
		scrpan_browsed.setViewportView(tree_browsed);

		pan_craft = new JPanel();
		tabpan_mainContainer.addTab(Messages.getString("MenuPrincipal.pan_craft.title"), null, pan_craft, null); //$NON-NLS-1$
		pan_craft.setLayout(null);

		pan_itemsToMake = new JPanel();
		pan_itemsToMake.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), Messages
				.getString("MenuPrincipal.pan_itemsToMake.borderTitle"), TitledBorder.LEADING, TitledBorder.TOP, MINECRAFTIA, null)); //$NON-NLS-1$
		pan_itemsToMake.setBounds(6, 6, 395, 307);
		pan_craft.add(pan_itemsToMake);
		pan_itemsToMake.setLayout(null);

		scrpan_itemsToMake = new JScrollPane();
		scrpan_itemsToMake.setBackground(Color.WHITE);
		scrpan_itemsToMake.getViewport().setBackground(Color.white);
		scrpan_itemsToMake.setBounds(6, 25, 383, 236);
		scrpan_itemsToMake.getVerticalScrollBar().setUnitIncrement(45);
		pan_itemsToMake.add(scrpan_itemsToMake);

		final FullQuantityItemPanel fqip_itemsToMake = new FullQuantityItemPanel(ButtonType.ANNULER);
		scrpan_itemsToMake.setViewportView(fqip_itemsToMake);
		fqip_itemsToMake.setSize(scrpan_itemsToMake.getViewport().getWidth(), fqip_itemsToMake.getHeight());

		search_itemsToMake = new JComboSearchField();
		search_itemsToMake.addSearchedItemChangeListener(new SearchedItemChangeListener() {

			@Override
			public void searchedItemChanged(Item item) {
				if (item != null) {
					if (fqip_itemsToMake.contains(item)) {
						CellEditQuantity ceq = fqip_itemsToMake.getItemsToCellsMap().get(item);
						ceq.setQuantity(ceq.getQuantity() + 1);
					} else {
						fqip_itemsToMake.add(item);
					}
					search_itemsToMake.setItem(null);
				}
			}
		});
		search_itemsToMake.setPrompt(Messages.getString("MenuPrincipal.search_itemsToMake.prompt")); //$NON-NLS-1$
		search_itemsToMake.setBounds(6, 273, 383, 28);
		pan_itemsToMake.add(search_itemsToMake);
		search_itemsToMake.setColumns(10);

		pan_ingredients = new JPanel();
		pan_ingredients.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), Messages.getString("FullRecipePanel.lib_ingredients.text"), TitledBorder.LEADING, TitledBorder.TOP, MINECRAFTIA, null));
		pan_ingredients.setBounds(413, 6, 389, 307);
		pan_craft.add(pan_ingredients);
		pan_ingredients.setLayout(null);

		scrpan_ingredientsPresent = new JScrollPane();
		scrpan_ingredientsPresent.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Already in inventory",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrpan_ingredientsPresent.setBounds(6, 25, 377, 111);
		pan_ingredients.add(scrpan_ingredientsPresent);

		final FullQuantityItemPanel fqip_ingredientsPresent = new FullQuantityItemPanel(ButtonType.RETIRER);
		scrpan_ingredientsPresent.setViewportView(fqip_ingredientsPresent);
		fqip_ingredientsPresent.setSize(scrpan_ingredientsPresent.getViewport().getWidth(), fqip_ingredientsPresent.getHeight());

		search_ingredientsPresent = new JComboSearchField();
		search_ingredientsPresent.setBounds(6, 148, 377, 28);
		search_ingredientsPresent.setPrompt(Messages.getString("MenuPrincipal.search_itemsToMake.prompt"));
		pan_ingredients.add(search_ingredientsPresent);
		search_ingredientsPresent.setColumns(10);

		scrpan_ingredientsNeeded = new JScrollPane();
		scrpan_ingredientsNeeded.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Need to gather", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrpan_ingredientsNeeded.setBounds(6, 188, 377, 113);
		pan_ingredients.add(scrpan_ingredientsNeeded);

		final FullQuantityItemPanel fqip_ingredientsNeeded = new FullQuantityItemPanel(ButtonType.AJOUTER);
		scrpan_ingredientsNeeded.setViewportView(fqip_ingredientsNeeded);
		fqip_ingredientsNeeded.setSize(scrpan_ingredientsNeeded.getViewport().getWidth(), fqip_ingredientsNeeded.getHeight());

		scrpan_steps = new JScrollPane();
		scrpan_steps.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Steps", TitledBorder.LEADING,
				TitledBorder.TOP, MINECRAFTIA, null));
		scrpan_steps.setBounds(6, 325, 796, 305);
		pan_craft.add(scrpan_steps);

		getContentPane().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getSource() instanceof MCImage) {
					itemDialog.setVisible(false);
					itemDialog = null;
					// TODO Gestion du crafting plan
					System.out.println(((MCImage) e.getSource()).getItem());
				}
			}

		});

		setVisible(true);
	}
}