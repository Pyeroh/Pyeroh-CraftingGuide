package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import model.impl.Item;
import view.components.MCImage;

/**
 * Un panel pour afficher les informations d'un item
 *
 * @author Pyeroh
 *
 */
// TODO Ajouter la possibilité de désactiver des mods pour sélectionner
// seulement certains objets en fonction des mods actifs
public class ItemPane extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = 8397804417232569776L;

	private Item item;

	private MCImage img_mainImg;

	private JLabel lib_item;

	private JButton btn_craftingPlan;

	private JScrollPane scrpan_recipes;

	private JScrollPane scrpan_usage;

	private JScrollPane scrpan_fromCategory;

	public ItemPane(Item item) {
		super();
		this.item = item;
		setSize(810, 700);
		setLayout(null);

		img_mainImg = new MCImage(this);
		img_mainImg.setBounds(6, 6, 64, 64);
		img_mainImg.toggleHoverable();
		add(img_mainImg);

		lib_item = new JLabel();
		lib_item.setBounds(82, 6, 480, 22);
		lib_item.setFont(Launch.getMinecraftia().deriveFont(Font.PLAIN).deriveFont(14f));
		add(lib_item);

		btn_craftingPlan = new JButton("See Crafting Plan");
		btn_craftingPlan.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("Pressed");
			}
		});
		btn_craftingPlan.setBounds(80, 39, 131, 31);
		add(btn_craftingPlan);

		scrpan_recipes = new JScrollPane(new FullRecipePanel(item));
		scrpan_recipes.setBounds(6, 82, 796, 305);
		scrpan_recipes.getViewport().setSize(770, 280);
		scrpan_recipes.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Recipe", TitledBorder.LEADING,
				TitledBorder.TOP, Launch.getMinecraftia().deriveFont(12f), null));
		add(scrpan_recipes);

		scrpan_usage = new JScrollPane();
		scrpan_usage.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Used to make", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		scrpan_usage.setBounds(6, 399, 392, 295);
		add(scrpan_usage);

		scrpan_fromCategory = new JScrollPane();
		scrpan_fromCategory.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Other %s", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		scrpan_fromCategory.setBounds(410, 399, 392, 295);
		add(scrpan_fromCategory);

		loadItem();

	}

	private void loadItem() {
		img_mainImg.setItem(item);
		lib_item.setText(item.getDisplayName());

	}

	/**
	 * @return {@link item}
	 */
	public Item getItem() {
		return item;
	}
}
