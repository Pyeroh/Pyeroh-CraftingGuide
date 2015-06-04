/**
 *
 */
package model.minecraft;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.Properties;

import model.enums.ECategory;
import model.enums.EMod;
import model.interfaces.AbstractItem;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Item provenant de Minecraft
 *
 * @author Pyeroh
 *
 */
public class MCItem extends AbstractItem {

	public MCItem(String id, int meta, String displayName, String iconName, ECategory category) {
		super(id, meta, displayName, iconName, category, EMod.MINECRAFT);
	}

	@Override
	public String toString() {
		return String.format("%s:%s", getMod().name().toLowerCase(), getId()) + (getMeta() == 0 ? "" : "." + getMeta());
	}

	/**
	 * Permet d'initialiser la liste des items disponibles (sans image, avec la langue)
	 * TODO déporter la méthode sur la classe abstraite, et rajouter un objet "inconnu" pouvant prendre n'importe quel nom
	 *
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static void initialize() throws IOException, URISyntaxException {

		EMod mod = EMod.MINECRAFT;

		List<String> source = Files.readAllLines(new File(MCItem.class.getResource(mod.getPath() + "infos.json").toURI()).toPath(),
				Charset.defaultCharset());
		StringBuffer buf = new StringBuffer();
		for (String string : source) {
			buf.append(string);
		}

		Properties lang = new Properties();
		lang.load(MCItem.class.getResourceAsStream(mod.getPath() + "lang.properties"));

		JSONArray jsonArray = new JSONArray(buf.toString());
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			String id = obj.getString("id");
			int meta = obj.getInt("meta");
			String iconName = obj.getString("iconName");
			String category = obj.getString("cat");
			itemList.add(new MCItem(id, meta, lang.getProperty(mod.name().toLowerCase() + "." + id + (meta == 0 ? "" : "." + meta), ""), iconName,
					ECategory.valueOf(category)));
		}

	}

}
