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

import model.enums.ECategory;
import model.enums.EMod;
import model.interfaces.IItem;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Item provenant de Minecraft
 *
 * @author Pyeroh
 *
 */
public class MCItem implements IItem {

	private String id;

	private int meta;

	private String iconName;

	private ECategory category;

	private final EMod mod = EMod.MINECRAFT;

	public MCItem(String id, int meta, String iconName) {
		this.id = id;
		this.meta = meta;
		this.iconName = iconName;
	}

	@Override
	public String toString() {
		return String.format("%s:%s", mod.name().toLowerCase(), id) + (meta == 0 ? "" : ":" + meta);
	}

	/**
	 * @return {@link #id}
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * @return {@link #meta}
	 */
	@Override
	public int getMeta() {
		return meta;
	}

	/**
	 * @return {@link #iconName}
	 */
	@Override
	public String getIconName() {
		return iconName;
	}

	/**
	 * @return {@link #iconName}
	 */
	@Override
	public ECategory getCategory() {
		return category;
	}

	/**
	 * @return {@link #mod}
	 */
	@Override
	public EMod getMod() {
		return mod;
	}

	public static void initialize() throws URISyntaxException, IOException {

		List<String> source = Files.readAllLines(new File(MCItem.class.getResource(EMod.MINECRAFT.getPath() + "infos.json").toURI()).toPath(),
				Charset.defaultCharset());
		StringBuffer buf = new StringBuffer();
		for (String string : source) {
			buf.append(string);
		}
		JSONArray jsonArray = new JSONArray(buf.toString());
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			itemList.add(new MCItem(obj.getString("id"), obj.getInt("meta"), obj.getString("iconName")));
		}

		jsonArray = new JSONArray();
		for (IItem item : itemList) {
			if (item.getMod() == EMod.MINECRAFT) {
				MCItem mcItem = (MCItem) item;

				JSONObject obj = new JSONObject();
				obj.put("id", mcItem.getId());
				obj.put("meta", mcItem.getMeta());
				obj.put("iconName", mcItem.getIconName());
				obj.put("cat", "");
				obj.put("mod", mcItem.getMod());
				jsonArray.put(obj);
			}
		}

		FileWriter writer = new FileWriter(new File("C:\\Users\\DENANTEUILQ\\Documents\\WS-eclipse-perso\\git\\Pyeroh-CraftingGuide\\Pyeroh-Crafting-Guide\\src\\gui\\items\\minecraft\\infos.json"));
		writer.write(jsonArray.toString());
		writer.close();

	}

}
