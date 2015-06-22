/**
 *
 */
package view.components.infos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Messages;
import model.impl.ItemWithQuantity;
import model.impl.Recipe;
import view.Launch;
import view.components.cells.CellListQuantityItem;
import view.components.core.JHoverList;

/**
 * Panel pour afficher une recette avec tous ses ingrédients et les extras
 *
 * @author Pyeroh
 *
 */
public class RecipePanel extends JPanel {

	private static final long serialVersionUID = 3283211122651561287L;

	private CraftingPanel craftingPan;

	private Recipe recipe;

	private JScrollPane scrpan_ingredients;

	private JHoverList<CellListQuantityItem> list_ingredients;

	private JLabel lib_ingredients;

	private JScrollPane scrpan_extras;

	private JLabel lib_extras;

	private JHoverList<CellListQuantityItem> list_extras;

	public RecipePanel(Recipe recipe) {
		super();
		setBackground(Color.white);
		this.recipe = recipe;

		setLayout(null);
		setSize(770, 280);

		craftingPan = new CraftingPanel(recipe);
		craftingPan.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				RecipePanel.this.dispatchEvent(e);
			}

		});
		craftingPan.setBounds(10, 11, 528, 258);
		add(craftingPan);

		scrpan_ingredients = new JScrollPane();
		scrpan_ingredients.setBounds(548, 11, 212, 165);
		add(scrpan_ingredients);

		MouseAdapter eventDispatcher = new MouseAdapter() {

			@SuppressWarnings("rawtypes")
			@Override
			public void mouseReleased(MouseEvent e) {
				if (!((JHoverList) e.getSource()).isSelectionEmpty() && e.getClickCount() == 2) {
					RecipePanel.this.dispatchEvent(e);
				}
			}
		};

		list_ingredients = new JHoverList<>();
		list_ingredients.addMouseListener(eventDispatcher);
		scrpan_ingredients.setViewportView(list_ingredients);

		lib_ingredients = new JLabel(Messages.getString("FullRecipePanel.lib_ingredients.text")); //$NON-NLS-1$
		scrpan_ingredients.setColumnHeaderView(lib_ingredients);
		lib_ingredients.setFont(Launch.getMinecraftia().deriveFont(12f));
		lib_ingredients.setBackground(new Color(255, 255, 255));

		scrpan_extras = new JScrollPane();
		scrpan_extras.setBounds(548, 187, 212, 82);
		add(scrpan_extras);

		list_extras = new JHoverList<>();
		list_extras.addMouseListener(eventDispatcher);
		scrpan_extras.setViewportView(list_extras);

		lib_extras = new JLabel(Messages.getString("FullRecipePanel.lib_extras.text")); //$NON-NLS-1$
		lib_extras.setFont(Launch.getMinecraftia().deriveFont(12f));
		scrpan_extras.setColumnHeaderView(lib_extras);

		loadRecipe();
	}

	private void loadRecipe() {
		List<ItemWithQuantity> extras = recipe.getExtras();
		List<ItemWithQuantity> ingredients = recipe.getIngredients();

		DefaultListModel<CellListQuantityItem> modelIngredients = new DefaultListModel<CellListQuantityItem>();
		for (ItemWithQuantity ingredient : ingredients) {
			modelIngredients.addElement(new CellListQuantityItem(ingredient));
		}
		list_ingredients.setModel(modelIngredients);

		DefaultListModel<CellListQuantityItem> modelExtras = new DefaultListModel<CellListQuantityItem>();
		for (ItemWithQuantity extra : extras) {
			modelExtras.addElement(new CellListQuantityItem(extra));
		}
		list_extras.setModel(modelExtras);

		if (extras.isEmpty()) {
			scrpan_extras.setVisible(false);
			scrpan_ingredients.setSize(212, 258);
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
		super.setSize(770, 280);
	}

	@Override
	public void setBounds(int x, int y, int width, int heigth) {
		super.setBounds(x, y, 770, 280);
	}

	@Override
	public void setBounds(Rectangle r) {
		setBounds(r.x, r.y, 528, 258);
	}
}
