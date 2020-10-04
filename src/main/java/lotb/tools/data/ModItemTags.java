package lotb.tools.data;

import lotb.LotbMod;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class ModItemTags extends ItemTags {
	public static final Tag<Item> WEAPONS = makeWrapperTag("weapons");
	public static final Tag<Item> MELEE_WEAPONS = makeWrapperTag("melee_weapons");

	
	//====================registration====================
	private static Tag<Item> makeWrapperTag(String key) {
		return new ItemTags.Wrapper(new ResourceLocation(LotbMod.MODID,key));
	}
}
