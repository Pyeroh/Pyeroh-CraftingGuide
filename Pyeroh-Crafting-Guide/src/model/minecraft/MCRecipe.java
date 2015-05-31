/**
 * 
 */
package model.minecraft;

import model.enums.EMod;
import model.interfaces.IRecipe;

/**
 * Recette provenant de Minecraft
 * 
 * @author Pyeroh
 *
 */
public class MCRecipe implements IRecipe {

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IMod#getMod()
	 */
	@Override
	public EMod getMod() {
		return EMod.MINECRAFT;
	}

}
