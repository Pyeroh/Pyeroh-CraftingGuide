package model.enums;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

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

	/**
	 * Renvoie vrai si le mod est activé, faux sinon. Dépend du fichier
	 * activatedMods.properties, qui doit se trouver au même endroit que le jar.
	 * Renvoie vrai par défaut.
	 *
	 * @return
	 */
	public boolean isActivated() {
		if (this != MINECRAFT && this != UNKNOWN) {
			Properties activatedMods = new Properties();
			File f = new File(System.getProperty("user.dir"), "activatedMods.properties");
			try {
				if (!f.isFile()) {
					f.createNewFile();
				}
				activatedMods.load(new FileInputStream(f));

				if (!activatedMods.containsKey(this.name())) {
					activatedMods.put(this.name(), Boolean.TRUE.toString());
					activatedMods.store(new FileWriter(f), null);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return Boolean.parseBoolean((String) activatedMods.get(this.name()));
		}
		else {
			return true;
		}
	}

	/**
	 * Active ou désactive un mod
	 * @param activated
	 * @throws Exception
	 */
	public void setActivated(boolean activated) throws Exception {
		if (this != MINECRAFT && this != UNKNOWN) {
			Properties activatedMods = new Properties();
			File f = new File(System.getProperty("user.dir"), "activatedMods.properties");
			isActivated();

			activatedMods.load(new FileInputStream(f));
			activatedMods.put(this.name(), Boolean.toString(activated));
			activatedMods.store(new FileWriter(f), null);
		}
	}

}
