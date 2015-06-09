/**
 *
 */
package view;

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
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import model.Messages;
import model.enums.ECategory;
import model.enums.EMod;
import model.impl.Item;
import model.impl.Item.ItemData;

import org.jdesktop.swingx.JXTree;

import view.components.cells.CellListCaracs;

import javax.swing.tree.DefaultTreeModel;

/**
 * Fenêtre principale pour l'affichage
 *
 * @author Pyeroh
 *
 */
public class MenuPrincipal extends JFrame {

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

	public MenuPrincipal() {
		super();
		setIconImage(Toolkit.getDefaultToolkit().getImage(MenuPrincipal.class.getResource("/gui/items/minecraft/icons/crafting_table.png")));
		setTitle("Pyeroh Crafting Guide v1");

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(796, 471);
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

					lib_modAuthor.setText(String.format(Messages.getString("MenuPrincipal.lib_modAuthor.text"), EMod.MINECRAFT.getCreator()));
					lib_modName.setText(EMod.MINECRAFT.getModName());

				}
				else {
					throw new UnsupportedOperationException(String.format("Bouton de navigation non géré : %s", button.getName()));
				}

			}

		};

		tabpan_mainContainer = new JTabbedPane(JTabbedPane.TOP);
		tabpan_mainContainer.setBounds(0, 6, 780, 427);
		getContentPane().add(tabpan_mainContainer);

		pan_configure = new JPanel();
		tabpan_mainContainer.addTab(Messages.getString("MenuPrincipal.pan_configure.title"), null, pan_configure, null); //$NON-NLS-1$
		pan_configure.setLayout(null);

		chk_enderStorage = new JCheckBox(Messages.getString("MenuPrincipal.chk_enderStorage.text")); //$NON-NLS-1$
		chk_enderStorage.setBounds(6, 6, 104, 18);
		pan_configure.add(chk_enderStorage);

		chk_appliedEnergistics = new JCheckBox(Messages.getString("MenuPrincipal.chk_appliedEnergistics.text")); //$NON-NLS-1$
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

		lib_modName = new JLabel(Messages.getString("MenuPrincipal.lib_modName.text")); //$NON-NLS-1$
		lib_modName.setVisible(false);
		lib_modName.setFont(Launch.getMinecraftia().deriveFont(14f));
		lib_modName.setBounds(511, 6, 263, 16);
		pan_browse.add(lib_modName);

		lib_modAuthor = new JLabel(); //$NON-NLS-1$
		lib_modAuthor.setVisible(false);
		lib_modAuthor.setFont(new Font("SansSerif", Font.ITALIC, 12));
		lib_modAuthor.setBounds(511, 32, 138, 16);
		pan_browse.add(lib_modAuthor);

		scrpan_browsed = new JScrollPane();
		scrpan_browsed.setVisible(false);
		scrpan_browsed.setBounds(511, 60, 263, 331);
		pan_browse.add(scrpan_browsed);

		tree_browsed = new JXTree();
		tree_browsed.setRootVisible(false);
		tree_browsed.setCellRenderer(new DefaultTreeCellRenderer() {

			private static final long serialVersionUID = 1L;

			@Override
			public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row,
					boolean hasFocus) {

				DefaultTreeCellRenderer render = (DefaultTreeCellRenderer) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row,
						hasFocus);
				Object obj = ((DefaultMutableTreeNode) value).getUserObject();

				if (obj instanceof Item) {
					Item item = (Item) obj;

					render.setIcon(new ImageIcon(CellListCaracs.scaleImage(item.getImage(), new Dimension(24, 24))));
					render.setText(item.getDisplayName());
				}
				else {
					render.setIcon(null);
				}

				return render;
			}

		});
		scrpan_browsed.setViewportView(tree_browsed);

		pan_craft = new JPanel();
		tabpan_mainContainer.addTab(Messages.getString("MenuPrincipal.pan_craft.title"), null, pan_craft, null); //$NON-NLS-1$
		pan_craft.setLayout(null);

		setVisible(true);
	}
}
