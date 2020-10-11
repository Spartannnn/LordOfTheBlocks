package lotb.util;

import lotb.registries.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.BlockItem;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.biome.BiomeColors;

public class FoilageColorizer {
	static ItemColors ITEMCOLOURS;
	static BlockColors BLOCKCOLOURS;
	
	public static void register() {
		ITEMCOLOURS = Minecraft.getInstance().getItemColors();
		BLOCKCOLOURS= Minecraft.getInstance().getBlockColors();
		registerBlockColours();
		registerItemColours();
	}
	
	private static void registerBlockColours() {
		final IBlockColor grassColourHandler = (state, blockAccess, pos, tintIndex) -> {
			if (blockAccess != null && pos != null) {
				return BiomeColors.getFoliageColor(blockAccess, pos);
			}
			return FoliageColors.get(0.5D, 0.5D);
		};
		BLOCKCOLOURS.register(grassColourHandler, ModBlocks.LEBETHRON_LEAVES.get());
	}
	
	private static void registerItemColours() {
		final IItemColor itemBlockColourHandler = (stack, tintIndex) -> {
			final BlockState state = ((BlockItem) stack.getItem()).getBlock().getDefaultState();
			return BLOCKCOLOURS.getColor(state, null, null, tintIndex);
		};
		ITEMCOLOURS.register(itemBlockColourHandler,ModBlocks.LEBETHRON_LEAVES.get());
	}
}
