package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import model.enums.ECraftingType;
import model.impl.Item;
import view.components.JHoverList;
import view.components.cells.CellListCaracs;
import view.components.cells.CellListQuantityItem;

// TODO Il faut créer un panel custom pour les crafting type (pour avoir directement la recette renseignée sans se casser la tête avec les labels...)
// TODO Il faut aussi un panel custom pour le couple CraftingPanel/IngredientsListe pour pouvoir le "dupliquer" comme une liste
public class ItemPane extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = 8397804417232569776L;

	private JLabel img_mainImg;

	private JLabel lib_item;

	private JPanel pan_recipe;

	private JLabel img_craftBackground;

	private JHoverList<CellListQuantityItem> list_ingredients;

	private JLabel lib_ingredients;

	private JScrollPane scrpan_ingredients;

	public ItemPane(Item item) {
		super();

		setLayout(null);
		setSize(965, 467);

		img_mainImg = new JLabel();
		img_mainImg.setBounds(6, 6, 64, 64);
		add(img_mainImg);

		lib_item = new JLabel("");
		lib_item.setFont(Launch.getMinecraftia().deriveFont(Font.PLAIN).deriveFont(14f));
		lib_item.setBounds(82, 6, 310, 22);
		add(lib_item);

		pan_recipe = new JPanel();
		pan_recipe.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Recipe", TitledBorder.LEADING, TitledBorder.TOP, Launch
				.getMinecraftia().deriveFont(12f), null));
		pan_recipe.setBounds(82, 40, 787, 301);
		add(pan_recipe);
		pan_recipe.setLayout(null);

		img_craftBackground = new JLabel();
		img_craftBackground.setBounds(21, 26, 528, 258);
		img_craftBackground.setIcon(new ImageIcon(CellListCaracs.scaleImage(ECraftingType.CRAFT.getInterfaceImage(), img_craftBackground)));
		pan_recipe.add(img_craftBackground);

		scrpan_ingredients = new JScrollPane();
		scrpan_ingredients.setBounds(561, 26, 208, 258);
		scrpan_ingredients.setBackground(getBackground());
		pan_recipe.add(scrpan_ingredients);

		lib_ingredients = new JLabel("Ingredients");
		lib_ingredients.setFont(Launch.getMinecraftia().deriveFont(12f));
		scrpan_ingredients.setColumnHeaderView(lib_ingredients);

		list_ingredients = new JHoverList<CellListQuantityItem>();
		list_ingredients.setBackground(new Color(214, 217, 223));
		list_ingredients.toggleHoverable();
		scrpan_ingredients.setViewportView(list_ingredients);

	}
}
