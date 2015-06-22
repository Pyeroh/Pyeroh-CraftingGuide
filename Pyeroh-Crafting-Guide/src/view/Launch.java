package view;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.UIManager;

import model.impl.Item;
import model.impl.Item.ItemData;
import model.impl.Recipe;

// TODO calcul des ingrédients nécessaires pour une recette, avec le moins d'étapes possibles (pour les moyens multiples d'obtention)
public class Launch {

	private static Font MINECRAFTIA;

	public static void main(String... args) throws Exception {
		// File mcItems = new
		// File("C:\\Users\\DENANTEUILQ\\Documents\\WS-eclipse-perso\\Pyeroh-Crafting-Guide\\src\\gui\\items\\mc");
		// File[] files = mcItems.listFiles();
		//
		// for (File file : files) {
		// File[] icons = file.listFiles();
		// int i = 0;
		// if (icons.length > 1) {
		// if (!icons[i].getName().contains("icon")) {
		// i++;
		// }
		// }
		// Files.move(
		// icons[i].toPath(),
		// Paths.get("C:\\Users\\DENANTEUILQ\\Documents\\WS-eclipse-perso\\Pyeroh-Crafting-Guide\\src\\gui\\items\\mc",
		// file.getName() + ".png"), StandardCopyOption.ATOMIC_MOVE);
		// }

		MINECRAFTIA = Font.createFont(Font.TRUETYPE_FONT, Launch.class.getResourceAsStream("/gui/Minecraftia.ttf"));
		GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(MINECRAFTIA);

		UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

		Item.initialize();
		Recipe.initialize();

		Recipe.getIngredientsNeeded(Item.getBy("minecraft:piston", ItemData.ID_AND_META), 1, null);

		// new TestFrame();

		new MenuPrincipal();

	}

	public static Font getMinecraftia() {
		return MINECRAFTIA;
	}

}
