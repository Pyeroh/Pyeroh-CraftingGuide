/**
 *
 */
package model.minecraft;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import model.enums.ECraftingType;
import model.enums.EMod;
import model.interfaces.AbstractItem;
import model.interfaces.AbstractRecipe;

/**
 * Recette provenant de Minecraft
 *
 * @author Pyeroh
 *
 */
public class MCRecipe extends AbstractRecipe {

	public MCRecipe(AbstractItem item, int quantity, List<AbstractItem> extras, AbstractItem[] pattern, ECraftingType type) {
		super(item, quantity, extras, pattern, type, EMod.MINECRAFT);
	}

	public static void initialize() throws IOException, URISyntaxException {

	}

}
