package lotb.tools.data;

import lotb.LotbMod;
import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class ModBlockTags extends BlockTags{
	public static final Tag<Block> SHIRE_OAK_LOGS = makeWrapperTag("shire_oak_logs");


	//====================registration====================
	private static Tag<Block> makeWrapperTag(String key) {
		return new BlockTags.Wrapper(new ResourceLocation(LotbMod.MODID,key));
	}
}
