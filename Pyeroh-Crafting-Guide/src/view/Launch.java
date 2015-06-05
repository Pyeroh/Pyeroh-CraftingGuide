package view;

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

		Item.initialize();
		Recipe.initialize();

	}

}
