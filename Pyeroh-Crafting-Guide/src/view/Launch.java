package view;

import model.minecraft.MCItem;


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
		
		MCItem.initialize();

	}

}
