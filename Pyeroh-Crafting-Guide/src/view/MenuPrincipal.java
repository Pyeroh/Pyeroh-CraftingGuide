/**
 * 
 */
package view;

import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import model.enums.ECraftingType;
import view.components.cells.CellListCaracs;

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
	
	private JLabel img_craftingBackground;
	private JTabbedPane tabpan_mainContainer;
	private JPanel pan_configure;

	public MenuPrincipal() {
		super();
		setIconImage(Toolkit.getDefaultToolkit().getImage(MenuPrincipal.class.getResource("/gui/items/minecraft/icons/crafting_table.png")));
		setTitle("Pyeroh Crafting Guide v1");

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(711, 405);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		img_craftingBackground = new JLabel("");
		img_craftingBackground.setBounds(89, 53, 528, 258);
		img_craftingBackground.setIcon(new ImageIcon(CellListCaracs.scaleImage(ECraftingType.CRAFT.getInterfaceImage(), img_craftingBackground)));
		getContentPane().add(img_craftingBackground);
		
		tabpan_mainContainer = new JTabbedPane(JTabbedPane.TOP);
		tabpan_mainContainer.setBounds(6, 6, 683, 355);
		getContentPane().add(tabpan_mainContainer);
		
		pan_configure = new JPanel();
		tabpan_mainContainer.addTab("Configure", null, pan_configure, null);
		
		setVisible(true);
	}
}
