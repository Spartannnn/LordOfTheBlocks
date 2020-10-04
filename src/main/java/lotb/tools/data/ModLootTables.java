package lotb.tools.data;

import java.util.Set;

import com.google.common.collect.Sets;

import lotb.LotbMod;
import net.minecraft.util.ResourceLocation;

public class ModLootTables {
	private static final Set<ResourceLocation> MOD_LOOT_TABLES = Sets.newHashSet();

	//MISC & BARRELS
	public static final ResourceLocation MISC_COALSHED = register("chests/misc_coalshed");
	public static final ResourceLocation MISC_CARAVAN = register("chests/misc_caravan");
	public static final ResourceLocation BARREL_APPLE = register("chests/barrel_apple");
	public static final ResourceLocation BARREL_WHEAT = register("chests/barrel_wheat");
	public static final ResourceLocation BARREL_CARROT = register("chests/barrel_carrot");
	public static final ResourceLocation BARREL_POTATO = register("chests/barrel_potato");
	public static final ResourceLocation BARREL_BEET = register("chests/barrel_beetruit");
	public static final ResourceLocation BARREL_GUNPOWDER = register("chests/barrel_gunpowder");
	public static final ResourceLocation BARREL_ARROW = register("chests/barrel_arrow");
	//DWARF
	public static final ResourceLocation DWARF_MINESHAFT_HALL 	 = register("chests/dwarf_mineshaft_hall");
	public static final ResourceLocation DWARF_MINESHAFT_ROOM 	 = register("chests/dwarf_mineshaft_room");
	public static final ResourceLocation DWARF_MINESHAFT_TRESURE = register("chests/dwarf_mineshaft_tresure");
	public static final ResourceLocation DWARF_TOMB_HALL 	= register("chests/dwarf_tomb_hall");
	public static final ResourceLocation DWARF_TOMB_GRAVE 	= register("chests/dwarf_tomb_grave");
	public static final ResourceLocation DWARF_TOMB_TRESURE = register("chests/dwarf_tomb_tresure");
	public static final ResourceLocation DWARF_TOMB_LIBRARY = register("chests/dwarf_tomb_library");
	//HOBBIT
	public static final ResourceLocation HOBBIT_HOLE_KITCHEN = register("chests/hobbit_hole_kitchen");
	public static final ResourceLocation HOBBIT_HOLE_STUDY 	 = register("chests/hobbit_hole_study");
	//ELF
	public static final ResourceLocation ELF_PARTY_FOOD 	= register("chests/elf_party_food");
	public static final ResourceLocation ELF_PARTY_ITEMS 	= register("chests/elf_party_items");
	public static final ResourceLocation ELF_RUIN_HALL 	  	= register("chests/elf_ruin_hall");
	public static final ResourceLocation ELF_RUIN_LIBRARY 	= register("chests/elf_ruin_library");
	public static final ResourceLocation ELF_RUIN_TRESURE 	= register("chests/elf_ruin_tresure");
	//ROHAN
	public static final ResourceLocation ROHAN_CAMP_TENT 	= register("chests/rohan_camp_tent");
	public static final ResourceLocation ROHAN_CAMP_FOOD 	= register("chests/rohan_camp_food");
	public static final ResourceLocation ROHAN_CAMP_SMITHY 	= register("chests/rohan_camp_smithy");
	public static final ResourceLocation ROHAN_VILLAGE_SMITHY 	= register("chests/rohan_village_smithy");
	public static final ResourceLocation ROHAN_VILLAGE_STABLES 	= register("chests/rohan_village_stables");
	public static final ResourceLocation ROHAN_MEADHALL_FOOD 	= register("chests/rohan_meadhall_food");
	public static final ResourceLocation ROHAN_MEADHALL_ARMOURY = register("chests/rohan_meadhall_armoury");
	public static final ResourceLocation ROHAN_OUTPOST_ARMOURY 	= register("chests/rohan_outpost_armoury");
	//GONDOR
	public static final ResourceLocation GONDOR_OUTPOST_ARMOURY = register("chests/gondor_outpost_armoury");
	public static final ResourceLocation GONDOR_RUIN_HALL 		= register("chests/gondor_ruin_hall");
	public static final ResourceLocation GONDOR_RUIN_TRESURE 	= register("chests/gondor_ruin_tresure");
	//MORDOR
	public static final ResourceLocation MORDOR_OUTPOST_ARMOURY = register("chests/mordor_outpost_armoury");
	public static final ResourceLocation MORDOR_OUTPOST_FOOD 	= register("chests/mordor_outpost_food");
	public static final ResourceLocation MORDOR_OUTPOST_ITEMS 	= register("chests/mordor_outpost_items");
	public static final ResourceLocation MORDOR_RUIN_HALL 	 = register("chests/mordor_ruin_hall");
	public static final ResourceLocation MORDOR_RUIN_TRESURE = register("chests/mordor_ruin_tresure");
	//GOBLIN
	public static final ResourceLocation GOBLIN_OUTPOST_ARMOURY = register("chests/mordor_outpost_armoury");
	public static final ResourceLocation GOBLIN_OUTPOST_FOOD 	= register("chests/mordor_outpost_food");
	public static final ResourceLocation GOBLIN_OUTPOST_ITEMS 	= register("chests/mordor_outpost_items");
	//URUK
	public static final ResourceLocation URUK_CAMP_CHESTS = register("chests/uruk_camp_chests");
	
	
	private static ResourceLocation register(String id) {
		ResourceLocation table = new ResourceLocation(LotbMod.MODID,id);
		if (MOD_LOOT_TABLES.add(table)) {
			return table;
		}
		throw new IllegalArgumentException(id + " is already a registered built-in loot table");
	}
}
