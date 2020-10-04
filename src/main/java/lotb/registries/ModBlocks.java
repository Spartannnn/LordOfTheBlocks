package lotb.registries;

import java.util.LinkedList;
import java.util.List;


import lotb.LotbMod;
import lotb.blocks.BarsBlock;
import lotb.blocks.DeerSkullBlock;
import lotb.blocks.DeerSkullWallBlock;
import lotb.blocks.ModDoorBlock;
import lotb.blocks.ModPressurePlateBlock;
import lotb.blocks.ModSeedBlock;
import lotb.blocks.ModTrapDoorBlock;
import lotb.blocks.ModWoodButtonBlock;
import lotb.blocks.PieBlock;
import lotb.blocks.RohanWorkstationBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.LogBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.WoodType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {
	public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS,LotbMod.MODID);
	
	/* DATAGROUPS */
	/** used for cycling through important lists*/
	public static final List<Block> ALLBLOCKS = new LinkedList<Block>();
	public static final List<Block> DROPSSELF = new LinkedList<Block>();
	public static final List<Block> SLAB = new LinkedList<Block>();
	
	//ores
	public static final Block PRISMARINE_ORE 	 	= reg("prismarine_ore", 	new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 3.0F)), ItemGroup.BUILDING_BLOCKS);
	public static final Block QUARTZ_ORE  	 	 	= reg("quartz_ore", 	 	new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 3.0F)), ItemGroup.BUILDING_BLOCKS);
	public static final Block GLOWSTONE_ORE  	 	= reg("glowstone_ore",  	new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 3.0F).lightValue(6)), ItemGroup.BUILDING_BLOCKS);
	public static final Block MITHRIL_ORE 	 	 	= reg("mithril_ore",	 	new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 3.0F)), ItemGroup.BUILDING_BLOCKS								, DROPSSELF);
	public static final Block MITHRIL_BLOCK  	 	= reg("mithril_block",	 	new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(10.0F,25.0F)),ItemGroup.BUILDING_BLOCKS								, DROPSSELF);
	public static final Block MITHRIL_BARS  	 	= reg("mithril_bars",	 	new BarsBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(10.0F,25.0F)),ItemGroup.BUILDING_BLOCKS							, DROPSSELF);
	//building
	public static final Block STICK_BLOCK		 	= reg("stick_block",	 	new RotatedPillarBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(0.5F).sound(SoundType.BAMBOO_SAPLING)),ItemGroup.BUILDING_BLOCKS, DROPSSELF);
	public static final Block BLAZE_BLOCK	 	 	= reg("blaze_block",	 	new RotatedPillarBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(0.5F).sound(SoundType.BAMBOO_SAPLING)),ItemGroup.BUILDING_BLOCKS, DROPSSELF);
	public static final Block THATCH_BLOCK 	 	= reg("thatch",		 		new Block(Block.Properties.create(Material.ORGANIC).hardnessAndResistance(0.5F).sound(SoundType.GROUND)),ItemGroup.BUILDING_BLOCKS					, DROPSSELF);
	public static final Block THATCH_STAIRS  	= reg("thatch_stairs",	 	new StairsBlock(()->THATCH_BLOCK.getDefaultState(),Block.Properties.from(THATCH_BLOCK)),ItemGroup.BUILDING_BLOCKS								, DROPSSELF);
	public static final Block THATCH_SLAB	 	= reg("thatch_slab",	 	new SlabBlock(Block.Properties.from(THATCH_BLOCK)),ItemGroup.BUILDING_BLOCKS																	, SLAB);
	public static final Block THATCH_WALL  	 	= reg("thatch_wall",	 	new WallBlock(Block.Properties.from(THATCH_BLOCK)),ItemGroup.BUILDING_BLOCKS																	, DROPSSELF);
	public static final Block CRACKED_STONE_BRICK_STAIR = reg("cracked_brick_stairs",new StairsBlock(()->Blocks.CRACKED_STONE_BRICKS.getDefaultState(),Block.Properties.from(Blocks.CRACKED_STONE_BRICKS)),ItemGroup.BUILDING_BLOCKS, DROPSSELF);
	public static final Block CRACKED_STONE_BRICK_SLAB 	= reg("cracked_brick_slab",new SlabBlock(			Block.Properties.create(Material.ROCK).hardnessAndResistance(2F, 6.0F)),ItemGroup.BUILDING_BLOCKS				, SLAB);
	public static final Block CRACKED_STONE_BRICK_WALL 	= reg("cracked_brick_wall",new WallBlock(			Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5F, 6.0F)),ItemGroup.BUILDING_BLOCKS				, DROPSSELF);
	public static final Block STONE_BRICK_PILLAR 		= reg("stone_brick_pillar",new RotatedPillarBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5F, 6.0F)),ItemGroup.BUILDING_BLOCKS				, DROPSSELF);
	//temp mordor rock
	public static final Block MORDOR_ROCK				= reg("mordor_rock",		new Block(				Block.Properties.create(Material.ROCK).hardnessAndResistance(1.75F, 6.0F)),ItemGroup.BUILDING_BLOCKS			, DROPSSELF);
	public static final Block MORDOR_ROCK_POLISHED		= reg("polished_mordor_rock",new Block(				Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5F, 6.0F)),ItemGroup.BUILDING_BLOCKS				, DROPSSELF);
	public static final Block MORDOR_STAIR				= reg("mordor_rock_stairs",	new StairsBlock(() -> MORDOR_ROCK.getDefaultState(),Block.Properties.from(MORDOR_ROCK)),ItemGroup.BUILDING_BLOCKS						, DROPSSELF);
	public static final Block MORDOR_SLAB				= reg("mordor_rock_slab",	new SlabBlock(			Block.Properties.create(Material.ROCK).hardnessAndResistance(2F, 6.0F)),ItemGroup.BUILDING_BLOCKS				, SLAB);
	public static final Block MORDOR_WALL				= reg("mordor_rock_wall",	new WallBlock(			Block.Properties.create(Material.ROCK).hardnessAndResistance(1.75F, 6.0F)),ItemGroup.BUILDING_BLOCKS			, DROPSSELF);
	public static final Block MORDOR_BRICK				= reg("mordor_brick",					 new Block(	Block.Properties.create(Material.ROCK).hardnessAndResistance(1.75F, 6.0F)),ItemGroup.BUILDING_BLOCKS			, DROPSSELF);
	public static final Block MORDOR_BRICK_STAIR		= reg("mordor_brick_stairs",new StairsBlock(() -> MORDOR_BRICK.getDefaultState(),Block.Properties.from(MORDOR_BRICK)),ItemGroup.BUILDING_BLOCKS						, DROPSSELF);
	public static final Block MORDOR_BRICK_SLAB			= reg("mordor_brick_slab",	new SlabBlock(			Block.Properties.create(Material.ROCK).hardnessAndResistance(2F, 6.0F)),ItemGroup.BUILDING_BLOCKS				, SLAB);
	public static final Block MORDOR_BRICK_WALL			= reg("mordor_brick_wall",	new WallBlock(			Block.Properties.create(Material.ROCK).hardnessAndResistance(1.75F, 6.0F)),ItemGroup.BUILDING_BLOCKS			, DROPSSELF);
	public static final Block MORDOR_BRICK_MOSSY		= reg("mossy_mordor_brick", 			 new Block(	Block.Properties.create(Material.ROCK).hardnessAndResistance(1.75F, 6.0F)),ItemGroup.BUILDING_BLOCKS			, DROPSSELF);
	public static final Block MORDOR_BRICK_MOSSY_STAIR	= reg("mossy_mordor_brick_stairs",new StairsBlock(() -> MORDOR_BRICK_MOSSY.getDefaultState(),Block.Properties.from(MORDOR_BRICK)),ItemGroup.BUILDING_BLOCKS			, DROPSSELF);
	public static final Block MORDOR_BRICK_MOSSY_SLAB	= reg("mossy_mordor_brick_slab",new SlabBlock(		Block.Properties.create(Material.ROCK).hardnessAndResistance(2F, 6.0F)),ItemGroup.BUILDING_BLOCKS				, SLAB);
	public static final Block MORDOR_BRICK_MOSSY_WALL	= reg("mossy_mordor_brick_wall",new WallBlock(		Block.Properties.create(Material.ROCK).hardnessAndResistance(1.75F, 6.0F)),ItemGroup.BUILDING_BLOCKS			, DROPSSELF);
	public static final Block MORDOR_BRICK_CRACKED		= reg("cracked_mordor_brick", 			 new Block(	Block.Properties.create(Material.ROCK).hardnessAndResistance(1.75F, 6.0F)), ItemGroup.BUILDING_BLOCKS			, DROPSSELF);
	public static final Block MORDOR_BRICK_CRACKED_STAIR= reg("cracked_mordor_brick_stairs", new StairsBlock(() -> MORDOR_BRICK_CRACKED.getDefaultState(),Block.Properties.from(MORDOR_BRICK)), ItemGroup.BUILDING_BLOCKS	, DROPSSELF);
	public static final Block MORDOR_BRICK_CRACKED_SLAB = reg("cracked_mordor_brick_slab", new SlabBlock(	Block.Properties.create(Material.ROCK).hardnessAndResistance(2F, 6.0F)), ItemGroup.BUILDING_BLOCKS				, SLAB);
	public static final Block MORDOR_BRICK_CRACKED_WALL = reg("cracked_mordor_brick_wall", new WallBlock(	Block.Properties.create(Material.ROCK).hardnessAndResistance(1.75F, 6.0F)), ItemGroup.BUILDING_BLOCKS			, DROPSSELF);
	public static final Block CHISELED_MORDOR_BRICK		= reg("chiseled_mordor_brick", 			 new Block(	Block.Properties.create(Material.ROCK).hardnessAndResistance(1.75F, 6.0F)),ItemGroup.BUILDING_BLOCKS			, DROPSSELF);
	public static final Block MORDOR_PILLAR				= reg("mordor_pillar",		new RotatedPillarBlock(	Block.Properties.create(Material.ROCK).hardnessAndResistance(1.75F, 6.0F)),ItemGroup.BUILDING_BLOCKS			, DROPSSELF);
	//bricks
	public static final Block CLAY_WHITE	= reg("white_clay", new Block(Block.Properties.from(Blocks.CLAY)),ItemGroup.BUILDING_BLOCKS);
	public static final Block CLAY_ORANGE	= reg("orange_clay",new Block(Block.Properties.from(Blocks.CLAY)),ItemGroup.BUILDING_BLOCKS);
	public static final Block CLAY_MAGENTA	= reg("magenta_clay",new Block(Block.Properties.from(Blocks.CLAY)),ItemGroup.BUILDING_BLOCKS);
	public static final Block CLAY_LIGHT_BLUE=reg("light_blue_clay",new Block(Block.Properties.from(Blocks.CLAY)),ItemGroup.BUILDING_BLOCKS);
	public static final Block CLAY_YELLOW	= reg("yellow_clay",new Block(Block.Properties.from(Blocks.CLAY)),ItemGroup.BUILDING_BLOCKS);
	public static final Block CLAY_LIME		= reg("lime_clay",	new Block(Block.Properties.from(Blocks.CLAY)),ItemGroup.BUILDING_BLOCKS);
	public static final Block CLAY_PINK		= reg("pink_clay",	new Block(Block.Properties.from(Blocks.CLAY)),ItemGroup.BUILDING_BLOCKS);
	public static final Block CLAY_GREY		= reg("grey_clay",	new Block(Block.Properties.from(Blocks.CLAY)),ItemGroup.BUILDING_BLOCKS);
	public static final Block CLAY_SILVER	= reg("silver_clay",new Block(Block.Properties.from(Blocks.CLAY)),ItemGroup.BUILDING_BLOCKS);
	public static final Block CLAY_CYAN		= reg("cyan_clay", 	new Block(Block.Properties.from(Blocks.CLAY)),ItemGroup.BUILDING_BLOCKS);
	public static final Block CLAY_PURPLE	= reg("purple_clay",new Block(Block.Properties.from(Blocks.CLAY)),ItemGroup.BUILDING_BLOCKS);
	public static final Block CLAY_BLUE		= reg("blue_clay", 	new Block(Block.Properties.from(Blocks.CLAY)),ItemGroup.BUILDING_BLOCKS);
	public static final Block CLAY_BROWN	= reg("brown_clay", new Block(Block.Properties.from(Blocks.CLAY)),ItemGroup.BUILDING_BLOCKS);
	public static final Block CLAY_GREEN	= reg("green_clay", new Block(Block.Properties.from(Blocks.CLAY)),ItemGroup.BUILDING_BLOCKS);
	public static final Block CLAY_RED		= reg("red_clay", 	new Block(Block.Properties.from(Blocks.CLAY)),ItemGroup.BUILDING_BLOCKS);
	public static final Block CLAY_BLACK	= reg("black_clay", new Block(Block.Properties.from(Blocks.CLAY)),ItemGroup.BUILDING_BLOCKS);
	public static final Block BRICKS_WHITE		= reg("white_bricks",  	new Block(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS																	, DROPSSELF);
	public static final Block BRICKS_ORANGE		= reg("orange_bricks", 	new Block(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS																	, DROPSSELF);
	public static final Block BRICKS_MAGENTA	= reg("magenta_bricks",	new Block(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS																	, DROPSSELF);
	public static final Block BRICKS_LIGHT_BLUE = reg("light_blue_bricks",new Block(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS																	, DROPSSELF);
	public static final Block BRICKS_YELLOW		= reg("yellow_bricks", 	new Block(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS																	, DROPSSELF);
	public static final Block BRICKS_LIME		= reg("lime_bricks",		new Block(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS																, DROPSSELF);
	public static final Block BRICKS_PINK		= reg("pink_bricks",		new Block(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS																, DROPSSELF);
	public static final Block BRICKS_GREY		= reg("grey_bricks", 	new Block(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS																	, DROPSSELF);
	public static final Block BRICKS_SILVER		= reg("silver_bricks", 	new Block(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS																	, DROPSSELF);
	public static final Block BRICKS_CYAN		= reg("cyan_bricks", 	new Block(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS																	, DROPSSELF);
	public static final Block BRICKS_PURPLE		= reg("purple_bricks", 	new Block(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS																	, DROPSSELF);
	public static final Block BRICKS_BLUE		= reg("blue_bricks", 	new Block(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS																	, DROPSSELF);
	public static final Block BRICKS_BROWN		= reg("brown_bricks", 	new Block(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS																	, DROPSSELF);
	public static final Block BRICKS_GREEN		= reg("green_bricks", 	new Block(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS																	, DROPSSELF);
	public static final Block BRICKS_RED		= reg("red_bricks", 		new Block(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS																, DROPSSELF);
	public static final Block BRICKS_BLACK		= reg("black_bricks", 	new Block(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS																	, DROPSSELF);
	public static final Block BRICKS_STAIRS_WHITE		= reg("white_bricks_stairs", 	new StairsBlock(() -> BRICKS_WHITE.getDefaultState(),Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS		, DROPSSELF);
	public static final Block BRICKS_STAIRS_ORANGE		= reg("orange_bricks_stairs", 	new StairsBlock(() -> BRICKS_ORANGE.getDefaultState(),Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS		, DROPSSELF);
	public static final Block BRICKS_STAIRS_MAGENTA		= reg("magenta_bricks_stairs", 	new StairsBlock(() -> BRICKS_MAGENTA.getDefaultState(),Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS		, DROPSSELF);
	public static final Block BRICKS_STAIRS_LIGHT_BLUE 	= reg("light_blue_bricks_stairs",new StairsBlock(()-> BRICKS_LIGHT_BLUE.getDefaultState(),Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS	, DROPSSELF);
	public static final Block BRICKS_STAIRS_YELLOW		= reg("yellow_bricks_stairs", 	new StairsBlock(() -> BRICKS_YELLOW.getDefaultState(),Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS		, DROPSSELF);
	public static final Block BRICKS_STAIRS_LIME		= reg("lime_bricks_stairs", 	new StairsBlock(() -> BRICKS_LIME.getDefaultState(),Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS			, DROPSSELF);
	public static final Block BRICKS_STAIRS_PINK		= reg("pink_bricks_stairs", 	new StairsBlock(() -> BRICKS_PINK.getDefaultState(),Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS			, DROPSSELF);
	public static final Block BRICKS_STAIRS_GREY		= reg("grey_bricks_stairs", 	new StairsBlock(() -> BRICKS_GREY.getDefaultState(),Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS			, DROPSSELF);
	public static final Block BRICKS_STAIRS_SILVER		= reg("silver_bricks_stairs", 	new StairsBlock(() -> BRICKS_SILVER.getDefaultState(),Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS		, DROPSSELF);
	public static final Block BRICKS_STAIRS_CYAN		= reg("cyan_bricks_stairs", 	new StairsBlock(() -> BRICKS_CYAN.getDefaultState(),Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS			, DROPSSELF);
	public static final Block BRICKS_STAIRS_PURPLE		= reg("purple_bricks_stairs", 	new StairsBlock(() -> BRICKS_PURPLE.getDefaultState(),Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS		, DROPSSELF);
	public static final Block BRICKS_STAIRS_BLUE		= reg("blue_bricks_stairs", 	new StairsBlock(() -> BRICKS_BLUE.getDefaultState(),Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS			, DROPSSELF);
	public static final Block BRICKS_STAIRS_BROWN		= reg("brown_bricks_stairs", 	new StairsBlock(() -> BRICKS_BROWN.getDefaultState(),Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS		, DROPSSELF);
	public static final Block BRICKS_STAIRS_GREEN		= reg("green_bricks_stairs", 	new StairsBlock(() -> BRICKS_GREEN.getDefaultState(),Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS		, DROPSSELF);
	public static final Block BRICKS_STAIRS_RED			= reg("red_bricks_stairs", 		new StairsBlock(() -> BRICKS_RED.getDefaultState(),Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS			, DROPSSELF);
	public static final Block BRICKS_STAIRS_BLACK		= reg("black_bricks_stairs", 	new StairsBlock(() -> BRICKS_BLACK.getDefaultState(),Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS		, DROPSSELF);
	public static final Block BRICKS_SLAB_WHITE		= reg("white_bricks_slab", 	new SlabBlock(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS														, SLAB);
	public static final Block BRICKS_SLAB_ORANGE	= reg("orange_bricks_slab", new SlabBlock(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS														, SLAB);
	public static final Block BRICKS_SLAB_MAGENTA	= reg("magenta_bricks_slab",new SlabBlock(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS														, SLAB);
	public static final Block BRICKS_SLAB_LIGHT_BLUE= reg("light_blue_bricks_slab",new SlabBlock(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS													, SLAB);
	public static final Block BRICKS_SLAB_YELLOW	= reg("yellow_bricks_slab", new SlabBlock(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS														, SLAB);
	public static final Block BRICKS_SLAB_LIME		= reg("lime_bricks_slab", 	new SlabBlock(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS														, SLAB);
	public static final Block BRICKS_SLAB_PINK		= reg("pink_bricks_slab", 	new SlabBlock(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS														, SLAB);
	public static final Block BRICKS_SLAB_GREY		= reg("grey_bricks_slab", 	new SlabBlock(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS														, SLAB);
	public static final Block BRICKS_SLAB_SILVER	= reg("silver_bricks_slab", new SlabBlock(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS														, SLAB);
	public static final Block BRICKS_SLAB_CYAN		= reg("cyan_bricks_slab", 	new SlabBlock(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS														, SLAB);
	public static final Block BRICKS_SLAB_PURPLE	= reg("purple_bricks_slab", new SlabBlock(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS														, SLAB);
	public static final Block BRICKS_SLAB_BLUE		= reg("blue_bricks_slab", 	new SlabBlock(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS														, SLAB);
	public static final Block BRICKS_SLAB_BROWN		= reg("brown_bricks_slab", 	new SlabBlock(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS														, SLAB);
	public static final Block BRICKS_SLAB_GREEN		= reg("green_bricks_slab", 	new SlabBlock(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS														, SLAB);
	public static final Block BRICKS_SLAB_RED		= reg("red_bricks_slab", 	new SlabBlock(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS														, SLAB);
	public static final Block BRICKS_SLAB_BLACK		= reg("black_bricks_slab", 	new SlabBlock(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS														, SLAB);
	public static final Block BRICKS_WALL_WHITE		= reg("white_bricks_wall", 	new WallBlock(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS														, DROPSSELF);
	public static final Block BRICKS_WALL_ORANGE	= reg("orange_bricks_wall", new WallBlock(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS														, DROPSSELF);
	public static final Block BRICKS_WALL_MAGENTA	= reg("magenta_bricks_wall",new WallBlock(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS														, DROPSSELF);
	public static final Block BRICKS_WALL_LIGHT_BLUE= reg("light_blue_bricks_wall",new WallBlock(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS													, DROPSSELF);
	public static final Block BRICKS_WALL_YELLOW	= reg("yellow_bricks_wall", new WallBlock(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS														, DROPSSELF);
	public static final Block BRICKS_WALL_LIME		= reg("lime_bricks_wall", 	new WallBlock(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS														, DROPSSELF);
	public static final Block BRICKS_WALL_PINK		= reg("pink_bricks_wall",	new WallBlock(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS														, DROPSSELF);
	public static final Block BRICKS_WALL_GREY		= reg("grey_bricks_wall",	new WallBlock(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS														, DROPSSELF);
	public static final Block BRICKS_WALL_SILVER	= reg("silver_bricks_wall", new WallBlock(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS														, DROPSSELF);
	public static final Block BRICKS_WALL_CYAN		= reg("cyan_bricks_wall",	new WallBlock(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS														, DROPSSELF);
	public static final Block BRICKS_WALL_PURPLE	= reg("purple_bricks_wall", new WallBlock(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS														, DROPSSELF);
	public static final Block BRICKS_WALL_BLUE		= reg("blue_bricks_wall",	new WallBlock(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS														, DROPSSELF);
	public static final Block BRICKS_WALL_BROWN		= reg("brown_bricks_wall",	new WallBlock(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS														, DROPSSELF);
	public static final Block BRICKS_WALL_GREEN		= reg("green_bricks_wall",	new WallBlock(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS														, DROPSSELF);
	public static final Block BRICKS_WALL_RED		= reg("red_bricks_wall",	new WallBlock(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS														, DROPSSELF);
	public static final Block BRICKS_WALL_BLACK		= reg("black_bricks_wall",	new WallBlock(Block.Properties.from(Blocks.BRICKS)),ItemGroup.BUILDING_BLOCKS														, DROPSSELF);
	//saplings
	public static final Block OAK_SAPLING		= reg("oak_seed",		new ModSeedBlock(), ItemGroup.MISC);
	public static final Block SPRUCE_SAPLING	= reg("spruce_seed",	new ModSeedBlock(), ItemGroup.MISC);
	public static final Block BIRCH_SAPLING		= reg("birch_seed",		new ModSeedBlock(), ItemGroup.MISC);
	public static final Block JUNGLE_SAPLING	= reg("jungle_seed",	new ModSeedBlock(), ItemGroup.MISC);
	public static final Block ACACIA_SAPLING	= reg("acacia_seed",	new ModSeedBlock(), ItemGroup.MISC);
	public static final Block DARK_OAK_SAPLING	= reg("dark_oak_seed",	new ModSeedBlock(), ItemGroup.MISC);
	public static final Block LEBETHRON_SAPLING	= reg("shire_oak_seed",	new ModSeedBlock(), ItemGroup.MISC);
	//woodtypes
	public static final Block LEBETHRON_LOG			= reg("shire_oak_log",			new LogBlock(MaterialColor.WOOD,Block.Properties.from(Blocks.OAK_LOG)), ItemGroup.BUILDING_BLOCKS								, DROPSSELF);
	public static final Block LEBETHRON_LOG_STRIPPED= reg("stripped_shire_oak_log",	new LogBlock(MaterialColor.WOOD,Block.Properties.from(Blocks.STRIPPED_OAK_LOG)), ItemGroup.BUILDING_BLOCKS						, DROPSSELF);
	public static final Block LEBETHRON_WOOD		= reg("shire_oak_wood",			new RotatedPillarBlock(			Block.Properties.from(Blocks.OAK_WOOD)), ItemGroup.BUILDING_BLOCKS								, DROPSSELF);
	public static final Block LEBETHRON_WOOD_STRIPPED=reg("stripped_shire_oak_wood",new RotatedPillarBlock(			Block.Properties.from(Blocks.STRIPPED_OAK_WOOD)), ItemGroup.BUILDING_BLOCKS						, DROPSSELF);
	public static final Block LEBETHRON_LEAVES		= reg("shire_oak_leaves",	new LeavesBlock(		Block.Properties.from(Blocks.OAK_LEAVES)),ItemGroup.DECORATIONS);
	public static final Block LEBETHRON_PLANKS		= reg("shire_oak_planks",	new Block(				Block.Properties.from(Blocks.OAK_PLANKS)),ItemGroup.BUILDING_BLOCKS											, DROPSSELF);
	public static final Block LEBETHRON_SLAB		= reg("shire_oak_slab",		new SlabBlock(			Block.Properties.from(Blocks.OAK_SLAB)),ItemGroup.BUILDING_BLOCKS											, SLAB);
	public static final Block LEBETHRON_STAIRS		= reg("shire_oak_stairs",	new StairsBlock(() -> LEBETHRON_PLANKS.getDefaultState(),	Block.Properties.from(Blocks.OAK_STAIRS)),ItemGroup.BUILDING_BLOCKS		, DROPSSELF);
	public static final Block LEBETHRON_FENCE		= reg("shire_oak_fence",	new FenceBlock(			Block.Properties.from(Blocks.OAK_FENCE)),ItemGroup.DECORATIONS												, DROPSSELF);
	public static final Block LEBETHRON_GATE		= reg("shire_oak_gate",		new FenceGateBlock(		Block.Properties.from(Blocks.OAK_FENCE_GATE)),ItemGroup.REDSTONE											, DROPSSELF);
	public static final Block LEBETHRON_TRAPDOOR	= reg("shire_oak_trapdoor",	new ModTrapDoorBlock(	Block.Properties.from(Blocks.OAK_TRAPDOOR)),ItemGroup.REDSTONE												, DROPSSELF);
	public static final Block LEBETHRON_BUTTON		= reg("shire_oak_button",	new ModWoodButtonBlock(	Block.Properties.from(Blocks.OAK_BUTTON)),ItemGroup.REDSTONE												, DROPSSELF);
	public static final Block LEBETHRON_PRESSURE	= reg("shire_oak_pressure_plate",new ModPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.from(Blocks.OAK_PRESSURE_PLATE)),ItemGroup.REDSTONE, DROPSSELF);
	public static final Block LEBETHRON_SIGN 		= reg("shire_oak_sign", 	new StandingSignBlock(	Block.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.WOOD), WoodType.OAK),ItemGroup.DECORATIONS, DROPSSELF);
	//utilities
	public static final Block ROHAN_WORKBENCH 	= reg("rohan_workbench", new RohanWorkstationBlock(Block.Properties.from(Blocks.CRAFTING_TABLE)),ItemGroup.TRANSPORTATION , DROPSSELF);
	//public static final Block DWARVEN_CHEST	= reg("dwarven_chest",new ModChestBlock());
	public static final Block MITHRIL_DOOR 		= reg("mithril_door",	 new ModDoorBlock(Block.Properties.from(ModBlocks.MITHRIL_BARS)),ItemGroup.REDSTONE, DROPSSELF);
	public static final Block IRON_BAR_DOOR 	= reg("iron_bar_door",	 new ModDoorBlock(Block.Properties.from(Blocks.IRON_BARS)),ItemGroup.REDSTONE, DROPSSELF);
	public static final Block MITHRIL_BAR_DOOR 	= reg("mithril_bar_door",new ModDoorBlock(Block.Properties.from(ModBlocks.MITHRIL_BARS)),ItemGroup.REDSTONE, DROPSSELF);
	public static final Block DWARVEN_DOOR  	= reg("dwarven_door", 	 new ModDoorBlock(Block.Properties.from(Blocks.STONE)),ItemGroup.REDSTONE, DROPSSELF);
	//public static final Block HOBBIT_DOOR		= reg("shire_door", new ShireDoorBlock(Block.Properties.create(Material.WOOD)),ItemGroup.REDSTONE, DROPSSELF);
	//decorations
	//public static final Block DWARVEN_GRAFFITI	= reg("dwarven_graffiti", new Block(Block.Properties.from(Blocks.STONE)),ItemGroup.DECORATIONS)
	public static final Block DEER_SKULL			= reg("deer_skull", 	  new DeerSkullBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1.0F)));
	public static final Block DEER_SKULL_WALL		= reg("deer_skull_wall", 	  new DeerSkullWallBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1.0F)));
	public static final Block HORN_DEER_SKULL		= reg("horned_deer_skull",	  new DeerSkullBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1.0F)));
	public static final Block HORN_DEER_SKULL_WALL	= reg("horned_deer_skull_wall",new DeerSkullWallBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1.0F)));
	//pies
	public static final Block PORK_PIE 		= reg("pork_pie",	new PieBlock(),ItemGroup.FOOD);
	public static final Block BEEF_PIE 		= reg("beef_pie",	new PieBlock(),ItemGroup.FOOD);
	public static final Block MUTTON_PIE 	= reg("mutton_pie",	new PieBlock(),ItemGroup.FOOD);
	public static final Block CHICKEN_PIE	= reg("chicken_pie",new PieBlock(),ItemGroup.FOOD);
	public static final Block FISH_PIE		= reg("fish_pie",	new PieBlock(),ItemGroup.FOOD);
	public static final Block SALMON_PIE	= reg("salmon_pie",	new PieBlock(),ItemGroup.FOOD);
	public static final Block RABBIT_PIE	= reg("rabbit_pie",	new PieBlock(),ItemGroup.FOOD);
	public static final Block VENISON_PIE	= reg("venison_pie",new PieBlock(),ItemGroup.FOOD);
	
	
	/**-----------------funcs-----------------------*/
	//register blocks
	@SafeVarargs
	public static Block reg(String id, Block block,List<Block>... datagroups) {
		BLOCKS.register(id, () -> block);
		for (List<Block> datagroup : datagroups) 
			datagroup.add(block);
		ALLBLOCKS.add(block);
		return block;
	}
	//register block and standard blockitem
	@SafeVarargs
	public static Block reg(String id, Block block, ItemGroup group,List<Block>... datagroups) {
		reg(id,block,datagroups);
		if (group != null)
			ModItems.ITEMS.register(id, () -> new BlockItem(block, new Item.Properties().group(group)));
		else
			ModItems.ITEMS.register(id, () -> new BlockItem(block, new Item.Properties()));
		return block;
	}
}

