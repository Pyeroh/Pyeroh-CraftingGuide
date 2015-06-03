/**
 *
 */
package model.interfaces;

import model.enums.ECategory;
import model.enums.EMod;

/**
 * Superclasse de type {@link IItem} pour la définition des classes d'item
 *
 * @author Pyeroh
 *
 */
public abstract class AbstractItem implements IItem {

	private String id;

	private int meta;

	private String iconName;

	private ECategory category;

	private EMod mod;

	public AbstractItem(String id, int meta, String iconName, ECategory category, EMod mod) {
		this.id = id;
		this.meta = meta;
		this.iconName = iconName;
		this.category = category;
		this.mod = mod;
	}

	/**
	 * @return {@link #id}
	 */
	public final String getId() {
		return id;
	}

	/**
	 * @return {@link #meta}
	 */
	public final int getMeta() {
		return meta;
	}

	/**
	 * @return {@link #iconName}
	 */
	public final String getIconName() {
		return iconName;
	}

	/**
	 * @return {@link #category}
	 */
	public final ECategory getCategory() {
		return category;
	}

	/**
	 * @return {@link #mod}
	 */
	public final EMod getMod() {
		return mod;
	}

}
