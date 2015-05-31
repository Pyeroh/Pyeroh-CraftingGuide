package model.enums;

/**
 * D�crit les types de craft possibles
 * 
 * @author Pyeroh
 *
 */
public enum ECraftingType {

	CRAFT(EMod.MINECRAFT), 
	FOUR(EMod.MINECRAFT), 
	POTION(EMod.MINECRAFT);

	/**
	 * Le mod d'origine de la m�thode de craft
	 */
	private EMod mod;

	ECraftingType(EMod mod) {
		this.mod = mod;
	}

	/**
	 * @return {@link #mod}
	 */
	public EMod getMod() {
		return mod;
	}

}
