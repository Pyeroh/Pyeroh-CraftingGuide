/**
 *
 */
package view.components;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JPanel;

import model.impl.Item;
import model.impl.Recipe;
import model.impl.Recipe.RecipeData;

/**
 * Panel pour afficher toutes les recettes d'un item
 *
 * @author Pyeroh
 *
 */
public class FullRecipePanel extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = 3283211122651561287L;

	private Item item;

	public FullRecipePanel(Item item) {
		super();
		this.item = item;

		setLayout(null);
		setSize(770, 0);

		loadItem();
	}

	private void loadItem() {
		List<Recipe> recipes = Recipe.searchBy(item, RecipeData.ITEM);

		if (!recipes.isEmpty()) {
			for (Recipe recipe : recipes) {
				RecipePanel rp = new RecipePanel(recipe);
				rp.addMouseListener(new MouseAdapter() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						FullRecipePanel.this.dispatchEvent(e);
					}
					
				});
				setSize(0, getHeight() + rp.getHeight());
				rp.setLocation(0, getHeight() - rp.getHeight());
				add(rp);
			}
		}
	}

	/**
	 * @return {@link item}
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * @param item
	 *            {@link item}
	 */
	public void setItem(Item item) {
		this.item = item;
		removeAll();
		loadItem();
	}

	@Override
	public void setBounds(Rectangle r) {
		setBounds(r.x, r.y, 0, r.height);
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, 770, height);
	}

	@Override
	public void setSize(Dimension d) {
		setSize(0, d.height);
	}

	@Override
	public void setSize(int width, int height) {
		super.setSize(770, height);

		// A laisser pour le JScrollPane
		setMinimumSize(getSize());
		setMaximumSize(getSize());
		setPreferredSize(getSize());
	}

}
