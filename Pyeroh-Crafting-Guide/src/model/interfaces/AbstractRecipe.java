/**
 *
 */
package model.interfaces;

import java.util.Collections;
import java.util.List;

import model.enums.ECraftingType;
import model.enums.EMod;

/**
 * Superclasse de type {@link IRecipe} pour les définition des recettes
 *
 * @author Pyeroh
 *
 */
public abstract class AbstractRecipe implements IRecipe {

	private AbstractItem item;

	private int quantity;

	private List<AbstractItem> extras;

	private AbstractItem[] pattern;

	private ECraftingType type;

	private EMod mod;

	public AbstractRecipe(AbstractItem item, int quantity, List<AbstractItem> extras, AbstractItem[] pattern, ECraftingType type, EMod mod) {
		this.item = item;
		this.quantity = quantity;
		this.extras = extras;
		this.pattern = pattern;
		this.type = type;
		this.mod = mod;
	}

	/**
	 * @return {@link #item}
	 */
	public final AbstractItem getItem() {
		return item;
	}

	/**
	 * @return {@link #quantity}
	 */
	public final int getQuantity() {
		return quantity;
	}

	/**
	 * @return {@link #extras}
	 */
	public final List<AbstractItem> getExtras() {
		return Collections.unmodifiableList(extras);
	}

	/**
	 * @return {@link #pattern}
	 */
	public final AbstractItem[] getPattern() {
		return pattern;
	}

	/**
	 * @return {@link #type}
	 */
	public final ECraftingType getType() {
		return type;
	}

	/**
	 * @return {@link #mod}
	 */
	public final EMod getMod() {
		return mod;
	}

}
