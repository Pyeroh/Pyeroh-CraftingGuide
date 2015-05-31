/**
 * 
 */
package model.minecraft;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

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

	private final EMod mod = EMod.MINECRAFT;

	public MCItem(String id, int meta, String iconName) {
		this.id = id;
		this.meta = meta;
		this.iconName = iconName;
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
	 * @return {@link #mod}
	 */
	@Override
	public EMod getMod() {
		return mod;
	}

	public static void initialize() throws URISyntaxException, IOException {
		File[] icons = new File(MCItem.class.getResource(EMod.MINECRAFT.getPath() + "icons")
				.toURI()).listFiles();

		for (File icon : icons) {
			itemList.add(new MCItem("",0,icon.getName()));
		}
		
		JSONArray jsonArray = new JSONArray();
		for (IItem item : itemList) {
			if (item.getMod() == EMod.MINECRAFT) {
				MCItem mcItem = (MCItem) item;
				
				JSONObject obj = new JSONObject();
				obj.put("id", mcItem.getId());
				obj.put("meta", mcItem.getMeta());
				obj.put("iconName", mcItem.getIconName());
				obj.put("mod", mcItem.getMod());
				jsonArray.put(obj);
			}
		}
		
		FileWriter writer = new FileWriter(new File("D:\\Workspace Eclipse Luna\\git\\Pyeroh-CraftingGuide\\Pyeroh-Crafting-Guide\\src\\infos.json"));
		writer.write(jsonArray.toString());
		writer.close();

	}

}
