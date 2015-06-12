/**
 *
 */
package view.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;

import model.impl.Item;
import model.impl.Recipe;
import view.Launch;
import view.components.cells.CellListCaracs;

/**
 * Panel qui affiche le pattern d'un craft
 *
 * @author Pyeroh
 *
 */
public class CraftingPanel extends JLayeredPane {

	/**
	 *
	 */
	private static final long serialVersionUID = 5199141989858605885L;

	private Recipe recipe;

	private JLabel img_background;

	private MCImage img_craft1;

	private MCImage img_craft2;

	private MCImage img_craft3;

	private MCImage img_craft4;

	private MCImage img_craft5;

	private MCImage img_craft6;

	private MCImage img_craft7;

	private MCImage img_craft8;

	private MCImage img_craft9;

	private MCImage img_craftRes;

	private ShadowLabel lib_craftQuantity;

	private MCImage img_four1;

	private MCImage img_fourRes;

	private ShadowLabel lib_fourQuantity;

	private MCImage img_potion1;

	private MCImage img_potion2;

	private MCImage img_potionRes;

	public CraftingPanel(Recipe recipe) {
		super();
		this.recipe = recipe;

		setSize(528, 258);
		setLayout(null);

		MouseAdapter imgAdapter = new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {

				MCImage source = (MCImage) e.getSource();
				if (source.getItem() != null) {
					source.getParent().dispatchEvent(e);
				}

			}
		};

		img_background = new JLabel();
		setLayer(img_background, 0);
		img_background.setBounds(0, 0, 528, 258);
		img_background.setIcon(new ImageIcon(CellListCaracs.scaleImage(recipe.getType().getInterfaceImage(), img_background)));
		add(img_background);

		img_craft1 = new MCImage(this);
		img_craft1.addMouseListener(imgAdapter);
		setLayer(img_craft1, 1);
		img_craft1.setBounds(90, 51, 48, 48);
		add(img_craft1);

		img_craft2 = new MCImage(this);
		img_craft2.addMouseListener(imgAdapter);
		setLayer(img_craft2, 1);
		img_craft2.setBounds(144, 51, 48, 48);
		add(img_craft2);

		img_craft3 = new MCImage(this);
		img_craft3.addMouseListener(imgAdapter);
		setLayer(img_craft3, 1);
		img_craft3.setBounds(197, 51, 48, 48);
		add(img_craft3);

		img_craft4 = new MCImage(this);
		img_craft4.addMouseListener(imgAdapter);
		setLayer(img_craft4, 1);
		img_craft4.setBounds(90, 105, 48, 48);
		add(img_craft4);

		img_craft5 = new MCImage(this);
		img_craft5.addMouseListener(imgAdapter);
		setLayer(img_craft5, 1);
		img_craft5.setBounds(144, 105, 48, 48);
		add(img_craft5);

		img_craft6 = new MCImage(this);
		img_craft6.addMouseListener(imgAdapter);
		setLayer(img_craft6, 1);
		img_craft6.setBounds(197, 105, 48, 48);
		add(img_craft6);

		img_craft7 = new MCImage(this);
		img_craft7.addMouseListener(imgAdapter);
		setLayer(img_craft7, 1);
		img_craft7.setBounds(90, 158, 48, 48);
		add(img_craft7);

		img_craft8 = new MCImage(this);
		img_craft8.addMouseListener(imgAdapter);
		setLayer(img_craft8, 1);
		img_craft8.setBounds(144, 158, 48, 48);
		add(img_craft8);

		img_craft9 = new MCImage(this);
		img_craft9.addMouseListener(imgAdapter);
		setLayer(img_craft9, 1);
		img_craft9.setBounds(197, 158, 48, 48);
		add(img_craft9);

		img_craftRes = new MCImage(this);
		img_craftRes.addMouseListener(imgAdapter);
		setLayer(img_craftRes, 1);
		img_craftRes.setBounds(372, 106, 48, 48);
		add(img_craftRes);

		lib_craftQuantity = new ShadowLabel();
		lib_craftQuantity.setFont(Launch.getMinecraftia().deriveFont(16f));
		lib_craftQuantity.setVerticalAlignment(SwingConstants.BOTTOM);
		lib_craftQuantity.setHorizontalAlignment(SwingConstants.RIGHT);
		setLayer(lib_craftQuantity, 1);
		lib_craftQuantity.setBounds(377, 118, 42, 36);
		lib_craftQuantity.setBackground(new Color(63, 63, 63));
		lib_craftQuantity.setForeground(Color.white);
		add(lib_craftQuantity);

		img_four1 = new MCImage(this);
		img_four1.addMouseListener(imgAdapter);
		setLayer(img_four1, 1);
		img_four1.setBounds(167, 50, 48, 48);
		add(img_four1);

		img_fourRes = new MCImage(this);
		img_fourRes.addMouseListener(imgAdapter);
		setLayer(img_fourRes, 1);
		img_fourRes.setBounds(347, 105, 48, 48);
		add(img_fourRes);

		lib_fourQuantity = new ShadowLabel();
		lib_fourQuantity.setFont(Launch.getMinecraftia().deriveFont(16f));
		lib_fourQuantity.setVerticalAlignment(SwingConstants.BOTTOM);
		lib_fourQuantity.setHorizontalAlignment(SwingConstants.RIGHT);
		setLayer(lib_fourQuantity, 1);
		lib_fourQuantity.setBounds(352, 117, 42, 36);
		lib_fourQuantity.setBackground(new Color(63, 63, 63));
		lib_fourQuantity.setForeground(Color.white);
		add(lib_fourQuantity);

		img_potion1 = new MCImage(this);
		img_potion1.addMouseListener(imgAdapter);
		setLayer(img_potion1, 1);
		img_potion1.setBounds(236, 50, 48, 48);
		add(img_potion1);

		img_potion2 = new MCImage(this);
		img_potion2.addMouseListener(imgAdapter);
		setLayer(img_potion2, 1);
		img_potion2.setBounds(167, 137, 48, 48);
		add(img_potion2);

		img_potionRes = new MCImage(this);
		img_potionRes.addMouseListener(imgAdapter);
		setLayer(img_potionRes, 1);
		img_potionRes.setBounds(306, 137, 48, 48);
		add(img_potionRes);

		loadRecipe();
	}

	private void loadRecipe() {
		Item[] pattern = recipe.getPattern();
		Item result = recipe.getItem();
		int quantity = recipe.getQuantity();
		switch (recipe.getType()) {
		case CRAFT:
			img_craft1.setItem(pattern[0]);
			img_craft2.setItem(pattern[1]);
			img_craft3.setItem(pattern[2]);
			img_craft4.setItem(pattern[3]);
			img_craft5.setItem(pattern[4]);
			img_craft6.setItem(pattern[5]);
			img_craft7.setItem(pattern[6]);
			img_craft8.setItem(pattern[7]);
			img_craft9.setItem(pattern[8]);

			img_craftRes.setItem(result);
			if (quantity > 1) {
				lib_craftQuantity.setText(quantity + "");
			}

			setLayer(img_craft1, 2);
			setLayer(img_craft2, 2);
			setLayer(img_craft3, 2);
			setLayer(img_craft4, 2);
			setLayer(img_craft5, 2);
			setLayer(img_craft6, 2);
			setLayer(img_craft7, 2);
			setLayer(img_craft8, 2);
			setLayer(img_craft9, 2);
			setLayer(img_craftRes, 2);
			setLayer(lib_craftQuantity, 3);

			break;
		case FOUR:
			img_four1.setItem(pattern[0]);

			img_fourRes.setItem(result);
			if (quantity > 1) {
				lib_fourQuantity.setText(quantity + "");
			}

			setLayer(img_four1, 2);
			setLayer(img_fourRes, 2);
			setLayer(lib_fourQuantity, 3);

			break;
		case POTION:
			Item ingredient = null;
			Item potion = null;
			if (pattern[0].getId().contains("potion")) {
				ingredient = pattern[1];
				potion = pattern[0];
			} else {
				ingredient = pattern[0];
				potion = pattern[1];
			}

			img_potion1.setItem(ingredient);
			img_potion2.setItem(potion);
			img_potionRes.setItem(result);

			setLayer(img_potion1, 2);
			setLayer(img_potion2, 2);
			setLayer(img_potionRes, 2);
			break;
		default:
			break;
		}
	}

	/**
	 * @return the recipe
	 */
	public Recipe getRecipe() {
		return recipe;
	}

	@Override
	public void setSize(Dimension d) {
		setSize(0, 0);
	}

	@Override
	public void setSize(int width, int height) {
		super.setSize(528, 258);
	}

	@Override
	public void setBounds(int x, int y, int width, int heigth) {
		super.setBounds(x, y, 528, 258);
	}

	@Override
	public void setBounds(Rectangle r) {
		setBounds(r.x, r.y, 528, 258);
	}
}
