package model.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Décrit les catégories d'un mod
 *
 * @author Pyeroh
 *
 */
public enum ECategory {

	AGRICULTURE(EMod.MINECRAFT),
	CARPET(EMod.MINECRAFT),
	CLAY(EMod.MINECRAFT),
	DECORATION(EMod.MINECRAFT),
	DYES(EMod.MINECRAFT),
	EARTH_MATERIALS(EMod.MINECRAFT),
	ENCHANTING(EMod.MINECRAFT),
	FUNCTIONAL_BLOCKS(EMod.MINECRAFT),
	GLASS(EMod.MINECRAFT),
	HUSBANDRY(EMod.MINECRAFT),
	HUSBANDRY_WOOL(EMod.MINECRAFT),
	LIQUIDS(EMod.MINECRAFT),
	MINERALS(EMod.MINECRAFT),
	MOB_DROPS(EMod.MINECRAFT),
	PLANTS(EMod.MINECRAFT),
	POTIONS_BREWING(EMod.MINECRAFT),
	POTIONS_NORMAL(EMod.MINECRAFT),
	POTIONS_SPLASH(EMod.MINECRAFT),
	REDSTONE_MACHINES(EMod.MINECRAFT),
	SPAWN_EGGS(EMod.MINECRAFT),
	TOOLS(EMod.MINECRAFT),
	TOOLS_ARMOR(EMod.MINECRAFT),
	TOOLS_WEAPONS(EMod.MINECRAFT),
	TRANSPORTATION(EMod.MINECRAFT),
	WOOD(EMod.MINECRAFT);

	/**
	 * Le mod associé à la catégorie
	 */
	private List<EMod> mods;

	private ECategory(EMod mod, EMod... mods) {
		List<EMod> modList = new ArrayList<>(Arrays.asList(mods));
		if (modList.size() == 0) {
			modList.add(mod);
		}
		else {
			modList.add(0, mod);
		}
		this.mods = modList;
	}

	/**
	 * @return {@link #mod}
	 */
	public List<EMod> getMods() {
		return mods;
	}

	/**
	 * Renvoie une liste des catégories associées au mod passé en paramètre
	 *
	 * @param mod
	 * @return
	 */
	public static List<ECategory> getCategoriesByMod(EMod mod) {
		ECategory[] categories = values();
		List<ECategory> retCats = new ArrayList<>();

		for (ECategory eCategory : categories) {
			if (eCategory.getMods().contains(mod)) {
				retCats.add(eCategory);
			}
		}

		return retCats;
	}

}
