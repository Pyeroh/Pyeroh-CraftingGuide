/**
 *
 */
package model.impl;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.swing.ImageIcon;

import model.enums.ECategory;
import model.enums.EMod;
import model.interfaces.IItem;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Définition des items
 *
 * @author Pyeroh
 *
 */
public class Item implements IItem {

	private String id;

	private int meta;

	private String displayName;

	private String iconName;

	private ECategory category;

	private EMod mod;

	public Item(String id, int meta, String displayName, String iconName, ECategory category, EMod mod) {
		this.id = id;
		this.meta = meta;
		this.displayName = displayName;
		this.iconName = iconName;
		this.category = category;
		this.mod = mod;
	}

	@Override
	public String toString() {
		return String.format("%s:%s", getMod().name().toLowerCase(), getId()) + (getMeta() == 0 ? "" : "." + getMeta());
	}

	/**
	 * Permet d'initialiser la liste des items disponibles (sans image, avec la langue)
	 *
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static void initialize() throws IOException, URISyntaxException {

		for (EMod mod : EMod.values()) {

			switch (mod) {
			case UNKNOWN:
				break;
			default:
				List<String> source = Files.readAllLines(new File(Item.class.getResource(mod.getPath() + "infos.json").toURI()).toPath(),
						Charset.defaultCharset());
				StringBuffer buf = new StringBuffer();
				for (String string : source) {
					buf.append(string);
				}

				Properties lang = new Properties();
				lang.load(Item.class.getResourceAsStream(mod.getPath() + String.format("lang_%s.properties", Locale.getDefault().getLanguage())));

				JSONArray jsonArray = new JSONArray(buf.toString());
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject obj = jsonArray.getJSONObject(i);
					String id = obj.getString("id");
					int meta = obj.getInt("meta");
					String iconName = obj.getString("iconName");
					String category = obj.getString("cat");
					itemList.add(new Item(id, meta, lang.getProperty(mod.name().toLowerCase() + "." + id + (meta == 0 ? "" : "." + meta), ""),
							iconName, ECategory.valueOf(category), mod));
				}
				break;
			}

		}

	}

	/**
	 * Méthode de base pour la récupération d'un item
	 *
	 * @param data
	 * @param compare
	 * @return
	 */
	public static Item getBy(String data, ItemData compare) {

		Item item = null;
		int i = 0;
		do {

			if (IItem.itemList.get(i).getMod() != EMod.UNKNOWN) {
				switch (compare) {
				case NAME:
					if (IItem.itemList.get(i).getDisplayName().equalsIgnoreCase(data)) {
						item = IItem.itemList.get(i);
					}
					break;
				case ID_AND_META:
					if (IItem.itemList.get(i).toString().equalsIgnoreCase(data)) {
						item = IItem.itemList.get(i);
					}
					break;
				default:
					throw new UnsupportedOperationException("Item.getBy() : pas d'implémentation pour " + compare);
				}
			}
			i++;

		} while (item == null && i < IItem.itemList.size());

		return item;

	}

	/**
	 * Méthode de base pour la récupération d'une liste d'items
	 *
	 * @param data
	 * @param compare
	 * @return
	 */
	public static List<Item> searchBy(String data, ItemData compare) {

		List<Item> items = new ArrayList<>();

		ECategory catData = compare == ItemData.CATEGORY ? ECategory.valueOf(data) : null;
		data = data.toLowerCase();

		for (Item item : IItem.itemList) {
			if (item.getMod() != EMod.UNKNOWN) {
				switch (compare) {
				case ID_AND_META:
					if (item.toString().toLowerCase().contains(data)) {
						items.add(item);
					}
					break;
				case NAME:
					if (item.getDisplayName().toLowerCase().contains(data)) {
						items.add(item);
					}
					break;
				case CATEGORY:
					if (item.getCategory() == catData) {
						items.add(item);
					}
					break;
				default:
					throw new UnsupportedOperationException("Item.searchBy() : pas d'implémentation pour " + compare);
				}
			}
		}

		return items;

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

	public Image getImage() {
		Image img = new ImageIcon(Item.class.getResource(getIconName())).getImage();
		return img;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((iconName == null) ? 0 : iconName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + meta;
		result = prime * result + ((mod == null) ? 0 : mod.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Item)) {
			return false;
		}
		Item other = (Item) obj;
		if (category != other.category) {
			return false;
		}
		if (iconName == null) {
			if (other.iconName != null) {
				return false;
			}
		}
		else if (!iconName.equals(other.iconName)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else if (!id.equals(other.id)) {
			return false;
		}
		if (meta != other.meta) {
			return false;
		}
		if (mod != other.mod) {
			return false;
		}
		return true;
	}

	/**
	 * Enum pour la comparaison de valeurs, pour la recherche d'éléments dans la liste des items
	 *
	 * @author Pyeroh
	 *
	 */
	public static enum ItemData {

		/**
		 * Recherche par l'ID. La donnée doit être au format <b>{@literal <nomDuMod>:<id>.[meta]}</b> avec la meta si
		 * elle existe.
		 */
		ID_AND_META,

		/**
		 * Recherche par le nom d'affichage
		 */
		NAME,

		/**
		 * Recherche par la catégorie
		 */
		CATEGORY,

		/**
		 * Recherche par le mod
		 */
		MOD;
	}

}
