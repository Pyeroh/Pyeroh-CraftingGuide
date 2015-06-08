package model.enums;

/**
 * Mods g�r�s par l'appli
 *
 * @author Pyeroh
 *
 */
public enum EMod {

	/**
	 * Objets inconnus
	 */
	UNKNOWN("custom", null),
	/**
	 * Minecraft
	 */
	MINECRAFT("minecraft", "Mojang");

	private String path;

	private String creator;

	EMod(String path, String creator) {
		String format = "/gui/items/%s/";
		this.path = String.format(format, path);
	}

	/**
	 * @return {@link #path}
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @return {@link #creator}
	 */
	public String getCreator() {
		return creator;
	}

}
