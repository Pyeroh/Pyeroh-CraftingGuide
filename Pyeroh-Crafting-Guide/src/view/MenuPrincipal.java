/**
 *
 */
package view;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import model.Messages;

import org.jdesktop.swingx.JXTree;

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



			}

		};

		tabpan_mainContainer = new JTabbedPane(JTabbedPane.TOP);
		tabpan_mainContainer.setBounds(0, 6, 780, 427);
		getContentPane().add(tabpan_mainContainer);

		pan_configure = new JPanel();
		tabpan_mainContainer.addTab(Messages.getString("MenuPrincipal.pan_configure.title"), null, pan_configure, null);
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

		btn_brMinecraft = new JButton(Messages.getString("MenuPrincipal.btn_brMinecraft.text"));
		btn_brMinecraft.addMouseListener(browseEvent);
		btn_brMinecraft.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/gui/items/minecraft/icons/grass_block.png")));
		btn_brMinecraft.setBounds(6, 6, 169, 100);
		pan_browse.add(btn_brMinecraft);

		tree_browsed = new JXTree();
		tree_browsed.setBounds(511, 112, 263, 279);
		pan_browse.add(tree_browsed);

		lib_modName = new JLabel(Messages.getString("MenuPrincipal.lib_modName.text")); //$NON-NLS-1$
		lib_modName.setFont(Launch.getMinecraftia().deriveFont(14f));
		lib_modName.setBounds(511, 6, 100, 16);
		pan_browse.add(lib_modName);

		pan_craft = new JPanel();
		tabpan_mainContainer.addTab(Messages.getString("MenuPrincipal.pan_craft.title"), null, pan_craft, null);
		pan_craft.setLayout(null);

		setVisible(true);
	}
}
