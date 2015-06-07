package model.enums;

import java.awt.Image;

import javax.swing.ImageIcon;

import model.interfaces.IMod;

/**
 * Décrit les types de craft possibles
 * 
 * @author Pyeroh
 *
 */
public enum ECraftingType implements IMod {

	CRAFT(EMod.MINECRAFT, "craft"), 
	FOUR(EMod.MINECRAFT, "four"), 
	POTION(EMod.MINECRAFT, "potion");

	/**
	 * Le mod d'origine de la méthode de craft
	 */
	private EMod mod;
	
	/**
	 * Chemin de l'image pour l'affichage de l'interface
	 */
	private String interfacePath;

	ECraftingType(EMod mod, String interfacePath) {
		this.mod = mod;
		this.interfacePath = String.format("/gui/craftingType/%s.png", interfacePath);
	}

	/**
	 * @return {@link #mod}
	 */
	public EMod getMod() {
		return mod;
	}

	/**
	 * @return {@link #interfacePath}
	 */
	public String getInterfacePath() {
		return interfacePath;
	}
	
	/**
	 * @return l'image associée au {@link #interfacePath}
	 */
	public Image getInterfaceImage() {
		return new ImageIcon(ECraftingType.class.getResource(interfacePath)).getImage();
	}
}
