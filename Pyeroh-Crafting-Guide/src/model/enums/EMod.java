package model.enums;

/**
 * Mods gérés par l'appli
 * 
 * @author Pyeroh
 *
 */
public enum EMod {

	/**
	 * Minecraft
	 */
	MINECRAFT("minecraft");

	private String path;

	EMod(String path) {
		String format = "/gui/items/%s/";
		this.path = String.format(format, path);
	}

	/**
	 * @return {@link #path}
	 */
	public String getPath() {
		return path;
	}

}
