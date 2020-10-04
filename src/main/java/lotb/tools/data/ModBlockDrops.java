package lotb.tools.data;

import lotb.registries.ModBlocks;
import lotb.registries.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.world.storage.loot.ConstantRange;
import net.minecraft.world.storage.loot.ItemLootEntry;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.functions.ApplyBonus;
import net.minecraft.world.storage.loot.functions.SetCount;

public class ModBlockDrops extends BlockLootTables {
	private static final float[] DEFAULT_SAPLING_DROP_RATES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};

	@Override
	public void addTables() {
		for (Block block : ModBlocks.DROPSSELF)
			registerDropSelfLootTable(block);
		for (Block block : ModBlocks.SLAB)
			registerLootTable(block, BlockLootTables::droppingSlab);
		
		registerLootTable(ModBlocks.QUARTZ_ORE, 	(b) -> { return droppingItemWithFortune(b, Items.QUARTZ); });
		registerLootTable(ModBlocks.PRISMARINE_ORE, (b) -> { return droppingItemWithFortune(b, Items.PRISMARINE_SHARD); });
		registerLootTable(ModBlocks.GLOWSTONE_ORE, (b) -> {
			return droppingWithSilkTouch(b, withExplosionDecay(b, ItemLootEntry.builder(Items.GLOWSTONE_DUST).acceptFunction(SetCount.builder(RandomValueRange.of(2.0F, 4.0F))).acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE))));
		});
		
		registerLootTableWithNumber(ModBlocks.CLAY_WHITE,	ModItems.CLAY_BALL_WHITE,4);
		registerLootTableWithNumber(ModBlocks.CLAY_ORANGE,	ModItems.CLAY_BALL_ORANGE,4);
		registerLootTableWithNumber(ModBlocks.CLAY_MAGENTA,	ModItems.CLAY_BALL_MAGENTA,4);
		registerLootTableWithNumber(ModBlocks.CLAY_LIGHT_BLUE,ModItems.CLAY_BALL_LIGHT_BLUE,4);
		registerLootTableWithNumber(ModBlocks.CLAY_YELLOW,	ModItems.CLAY_BALL_YELLOW,4);
		registerLootTableWithNumber(ModBlocks.CLAY_LIME,	ModItems.CLAY_BALL_LIME,4);
		registerLootTableWithNumber(ModBlocks.CLAY_PINK,	ModItems.CLAY_BALL_PINK,4);
		registerLootTableWithNumber(ModBlocks.CLAY_GREY,	ModItems.CLAY_BALL_GREY,4);
		registerLootTableWithNumber(ModBlocks.CLAY_SILVER,	ModItems.CLAY_BALL_SILVER,4);
		registerLootTableWithNumber(ModBlocks.CLAY_CYAN,	ModItems.CLAY_BALL_CYAN,4);
		registerLootTableWithNumber(ModBlocks.CLAY_PURPLE,	ModItems.CLAY_BALL_PURPLE,4);
		registerLootTableWithNumber(ModBlocks.CLAY_BLUE,	ModItems.CLAY_BALL_BLUE,4);
		registerLootTableWithNumber(ModBlocks.CLAY_BROWN,	ModItems.CLAY_BALL_BROWN,4);
		registerLootTableWithNumber(ModBlocks.CLAY_GREEN,	ModItems.CLAY_BALL_GREEN,4);
		registerLootTableWithNumber(ModBlocks.CLAY_RED,		ModItems.CLAY_BALL_RED,4);
		registerLootTableWithNumber(ModBlocks.CLAY_BLACK,	ModItems.CLAY_BALL_BLACK,4);
		
		registerTreeLeavesDrops(Blocks.OAK_LEAVES			,ModBlocks.OAK_SAPLING);
		registerTreeLeavesDrops(Blocks.BIRCH_LEAVES			,ModBlocks.BIRCH_SAPLING);
		registerTreeLeavesDrops(Blocks.SPRUCE_LEAVES		,ModBlocks.SPRUCE_SAPLING);
		registerTreeLeavesDrops(Blocks.JUNGLE_LEAVES		,ModBlocks.JUNGLE_SAPLING);
		registerTreeLeavesDrops(Blocks.ACACIA_LEAVES		,ModBlocks.ACACIA_SAPLING);
		registerTreeLeavesDrops(Blocks.DARK_OAK_LEAVES		,ModBlocks.DARK_OAK_SAPLING);
		registerTreeLeavesDrops(ModBlocks.LEBETHRON_LEAVES	,ModBlocks.LEBETHRON_SAPLING);
	}
	protected void registerTreeLeavesDrops(Block block,Block item) {
		registerLootTable(block, (b) -> { return droppingWithChancesAndSticks(b, item, DEFAULT_SAPLING_DROP_RATES);});
	}
	protected void registerLootTableWithNumber(Block block,Item item,int number) {
		registerLootTable(block,(b)->{ return droppingWithSilkTouchOrRandomly(b, item, ConstantRange.of(number));});
	}

}
