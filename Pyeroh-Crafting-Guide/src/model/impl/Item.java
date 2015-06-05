/**
 *
 */
package model.impl;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.Properties;

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
	 * Permet d'initialiser la liste des items disponibles (sans image, avec la langue) TODO rajouter un objet "inconnu"
	 * pouvant prendre n'importe quel nom
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
				lang.load(Item.class.getResourceAsStream(mod.getPath() + "lang.properties"));

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
	 * Renvoie un item à partir de son ID
	 *
	 * @param id
	 *            l'ID complet (<b>{@literal <nomDuMod>:<id>.[meta]}</b> avec la meta si elle existe)
	 * @return l'item, ou null si non trouvé
	 */
	public static Item getById(String id) {

		Item item = null;
		int i = 0;
		do {

			if (IItem.itemList.get(i).toString().equals(id)) {
				item = IItem.itemList.get(i);
			}
			i++;

		} while (item == null && i < IItem.itemList.size());

		return item;
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
