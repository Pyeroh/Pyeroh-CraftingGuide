package model.enums;

/**
 * Mods gérés par l'appli
 *
 * @author Pyeroh
 *
 */
public enum EMod {

	/**
	 * Objets inconnus
	 */
	UNKNOWN("custom", null, null),
	/**
	 * Minecraft
	 */
	MINECRAFT("minecraft", "Minecraft", "Mojang");

	/**
	 * Chemin d'accès aux données du mod
	 */
	private String path;

	/**
	 * Nom du mod
	 */
	private String modName;

	/**
	 * Créateur du mod
	 */
	private String creator;

	EMod(String path, String modName, String creator) {
		String format = "/gui/items/%s/";
		this.path = String.format(format, path);
		this.modName = modName;
		this.creator = creator;
	}

	/**
	 * @return {@link #path}
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @return {@link #modName}
	 */
	public String getModName() {
		return modName;
	}

	/**
	 * @return {@link #creator}
	 */
	public String getCreator() {
		return creator;
	}

}
