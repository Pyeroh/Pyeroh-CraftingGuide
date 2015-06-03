/**
 *
 */
package model.minecraft;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.Properties;

import model.enums.ECategory;
import model.enums.EMod;
import model.interfaces.AbstractItem;
import model.interfaces.IItem;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Item provenant de Minecraft
 *
 * @author Pyeroh
 *
 */
public class MCItem extends AbstractItem {

	public MCItem(String id, int meta, String iconName, ECategory category) {
		super(id, meta, iconName, category, EMod.MINECRAFT);
	}

	@Override
	public String toString() {
		return String.format("%s.%s", getMod().name().toLowerCase(), getId()) + (getMeta() == 0 ? "" : "." + getMeta());
	}

	/**
	 * Permet d'initialiser la liste des items disponibles (sans image, avec la langue)
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static void initialize() throws IOException, URISyntaxException {

		List<String> source = Files.readAllLines(new File(MCItem.class.getResource(EMod.MINECRAFT.getPath() + "infos.json").toURI()).toPath(),
				Charset.defaultCharset());
		StringBuffer buf = new StringBuffer();
		for (String string : source) {
			buf.append(string);
		}
		JSONArray jsonArray = new JSONArray(buf.toString());
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			itemList.add(new MCItem(obj.getString("id"), obj.getInt("meta"), obj.getString("iconName"), ECategory.valueOf(obj.getString("cat"))));
		}

		Properties props = new Properties();
		for (IItem item : itemList) {
			if (item.getMod() == EMod.MINECRAFT) {
				props.put(item.toString(), "");
			}
		}
		props.store(new FileWriter(new File("C:\\Users\\DENANTEUILQ\\Documents\\WS-eclipse-perso\\git\\Pyeroh-CraftingGuide\\Pyeroh-Crafting-Guide\\src\\gui\\items\\minecraft\\lang.properties")), null);

	}

}
