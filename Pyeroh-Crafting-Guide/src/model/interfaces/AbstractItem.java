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

	private String displayName;

	private String iconName;

	private ECategory category;

	private EMod mod;

	public AbstractItem(String id, int meta, String displayName, String iconName, ECategory category, EMod mod) {
		this.id = id;
		this.meta = meta;
		this.displayName = displayName;
		this.iconName = iconName;
		this.category = category;
		this.mod = mod;
	}

	/**
	 * @return {@link #id}
	 */
	@Override
	public final String getId() {
		return id;
	}

	/**
	 * @return {@link #meta}
	 */
	@Override
	public final int getMeta() {
		return meta;
	}

	/**
	 * @return {@link #displayName}
	 */
	@Override
	public final String getDisplayName() {
		return displayName;
	}

	/**
	 * @return {@link #iconName}
	 */
	@Override
	public final String getIconName() {
		return String.format("%sicons/%s", mod.getPath(), iconName);
	}

	/**
	 * @return {@link #category}
	 */
	@Override
	public final ECategory getCategory() {
		return category;
	}

	/**
	 * @return {@link #mod}
	 */
	@Override
	public final EMod getMod() {
		return mod;
	}

}
