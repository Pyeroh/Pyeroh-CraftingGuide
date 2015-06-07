package view;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.UIManager;

import model.impl.Item;
import model.impl.Recipe;

// TODO Ajouter une description sur les items (ex: Il faut utiliser un seau sur une vache pour avoir du lait/ ex2: comment obtenir le Kikoku d'ExtraUtils...)
public class Launch {

	public static void main(String[] args) throws Exception {
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
		
		// 528,258
		
		Font minecraftia = Font.createFont(Font.TRUETYPE_FONT, Launch.class.getResourceAsStream("/gui/Minecraftia.ttf"));
		GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(minecraftia);
		
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

		Item.initialize();
		Recipe.initialize();
		
		new MenuPrincipal();

	}

}
