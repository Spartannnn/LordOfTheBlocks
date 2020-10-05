package lotb.registries;

import lotb.LotbMod;
import lotb.blocks.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.LinkedList;
import java.util.List;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, LotbMod.MODID);

    /* DATAGROUPS */
    /**
     * used for cycling through important lists
     */
    public static final List<Block> ALLBLOCKS = new LinkedList<Block>();
    public static final List<Block> DROPSSELF = new LinkedList<Block>();
    public static final List<Block> SLAB = new LinkedList<Block>();

    //ores
    public static final RegistryObject<Block> PRISMARINE_ORE = reg("prismarine_ore", new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 3.0F)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> QUARTZ_ORE = reg("quartz_ore", new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 3.0F)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> GLOWSTONE_ORE = reg("glowstone_ore", new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 3.0F).lightValue(6)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> MITHRIL_ORE = reg("mithril_ore", new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 3.0F)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> MITHRIL_BLOCK = reg("mithril_block", new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(10.0F, 25.0F)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> MITHRIL_BARS = reg("mithril_bars", new BarsBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(10.0F, 25.0F)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    //building
    public static final RegistryObject<Block> STICK_BLOCK = reg("stick_block", new RotatedPillarBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(0.5F).sound(SoundType.BAMBOO_SAPLING)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BLAZE_BLOCK = reg("blaze_block", new RotatedPillarBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(0.5F).sound(SoundType.BAMBOO_SAPLING)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> THATCH_BLOCK = reg("thatch", new Block(Block.Properties.create(Material.ORGANIC).hardnessAndResistance(0.5F).sound(SoundType.GROUND)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> THATCH_STAIRS = reg("thatch_stairs", new StairsBlock(() -> THATCH_BLOCK.get().getDefaultState(), Block.Properties.from(THATCH_BLOCK.get())), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> THATCH_SLAB = reg("thatch_slab", new SlabBlock(Block.Properties.from(THATCH_BLOCK.get())), ItemGroup.BUILDING_BLOCKS, SLAB);
    public static final RegistryObject<Block> THATCH_WALL = reg("thatch_wall", new WallBlock(Block.Properties.from(THATCH_BLOCK.get())), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> CRACKED_STONE_BRICK_STAIR = reg("cracked_brick_stairs", new StairsBlock(() -> Blocks.CRACKED_STONE_BRICKS.getDefaultState(), Block.Properties.from(Blocks.CRACKED_STONE_BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> CRACKED_STONE_BRICK_SLAB = reg("cracked_brick_slab", new SlabBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2F, 6.0F)), ItemGroup.BUILDING_BLOCKS, SLAB);
    public static final RegistryObject<Block> CRACKED_STONE_BRICK_WALL = reg("cracked_brick_wall", new WallBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5F, 6.0F)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> STONE_BRICK_PILLAR = reg("stone_brick_pillar", new RotatedPillarBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5F, 6.0F)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    //temp mordor rock
    public static final RegistryObject<Block> MORDOR_ROCK = reg("mordor_rock", new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.75F, 6.0F)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> MORDOR_ROCK_POLISHED = reg("polished_mordor_rock", new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5F, 6.0F)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> MORDOR_STAIR = reg("mordor_rock_stairs", new StairsBlock(() -> MORDOR_ROCK.get().getDefaultState(), Block.Properties.from(MORDOR_ROCK.get())), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> MORDOR_SLAB = reg("mordor_rock_slab", new SlabBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2F, 6.0F)), ItemGroup.BUILDING_BLOCKS, SLAB);
    public static final RegistryObject<Block> MORDOR_WALL = reg("mordor_rock_wall", new WallBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.75F, 6.0F)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> MORDOR_BRICK = reg("mordor_brick", new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.75F, 6.0F)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> MORDOR_BRICK_STAIR = reg("mordor_brick_stairs", new StairsBlock(() -> MORDOR_BRICK.get().getDefaultState(), Block.Properties.from(MORDOR_BRICK.get())), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> MORDOR_BRICK_SLAB = reg("mordor_brick_slab", new SlabBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2F, 6.0F)), ItemGroup.BUILDING_BLOCKS, SLAB);
    public static final RegistryObject<Block> MORDOR_BRICK_WALL = reg("mordor_brick_wall", new WallBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.75F, 6.0F)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> MORDOR_BRICK_MOSSY = reg("mossy_mordor_brick", new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.75F, 6.0F)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> MORDOR_BRICK_MOSSY_STAIR = reg("mossy_mordor_brick_stairs", new StairsBlock(() -> MORDOR_BRICK_MOSSY.get().getDefaultState(), Block.Properties.from(MORDOR_BRICK.get())), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> MORDOR_BRICK_MOSSY_SLAB = reg("mossy_mordor_brick_slab", new SlabBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2F, 6.0F)), ItemGroup.BUILDING_BLOCKS, SLAB);
    public static final RegistryObject<Block> MORDOR_BRICK_MOSSY_WALL = reg("mossy_mordor_brick_wall", new WallBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.75F, 6.0F)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> MORDOR_BRICK_CRACKED = reg("cracked_mordor_brick", new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.75F, 6.0F)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> MORDOR_BRICK_CRACKED_STAIR = reg("cracked_mordor_brick_stairs", new StairsBlock(() -> MORDOR_BRICK_CRACKED.get().getDefaultState(), Block.Properties.from(MORDOR_BRICK.get())), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> MORDOR_BRICK_CRACKED_SLAB = reg("cracked_mordor_brick_slab", new SlabBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2F, 6.0F)), ItemGroup.BUILDING_BLOCKS, SLAB);
    public static final RegistryObject<Block> MORDOR_BRICK_CRACKED_WALL = reg("cracked_mordor_brick_wall", new WallBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.75F, 6.0F)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> CHISELED_MORDOR_BRICK = reg("chiseled_mordor_brick", new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.75F, 6.0F)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> MORDOR_PILLAR = reg("mordor_pillar", new RotatedPillarBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.75F, 6.0F)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    //bricks
    public static final RegistryObject<Block> CLAY_WHITE = reg("white_clay", new Block(Block.Properties.from(Blocks.CLAY)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CLAY_ORANGE = reg("orange_clay", new Block(Block.Properties.from(Blocks.CLAY)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CLAY_MAGENTA = reg("magenta_clay", new Block(Block.Properties.from(Blocks.CLAY)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CLAY_LIGHT_BLUE = reg("light_blue_clay", new Block(Block.Properties.from(Blocks.CLAY)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CLAY_YELLOW = reg("yellow_clay", new Block(Block.Properties.from(Blocks.CLAY)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CLAY_LIME = reg("lime_clay", new Block(Block.Properties.from(Blocks.CLAY)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CLAY_PINK = reg("pink_clay", new Block(Block.Properties.from(Blocks.CLAY)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CLAY_GREY = reg("grey_clay", new Block(Block.Properties.from(Blocks.CLAY)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CLAY_SILVER = reg("silver_clay", new Block(Block.Properties.from(Blocks.CLAY)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CLAY_CYAN = reg("cyan_clay", new Block(Block.Properties.from(Blocks.CLAY)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CLAY_PURPLE = reg("purple_clay", new Block(Block.Properties.from(Blocks.CLAY)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CLAY_BLUE = reg("blue_clay", new Block(Block.Properties.from(Blocks.CLAY)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CLAY_BROWN = reg("brown_clay", new Block(Block.Properties.from(Blocks.CLAY)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CLAY_GREEN = reg("green_clay", new Block(Block.Properties.from(Blocks.CLAY)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CLAY_RED = reg("red_clay", new Block(Block.Properties.from(Blocks.CLAY)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CLAY_BLACK = reg("black_clay", new Block(Block.Properties.from(Blocks.CLAY)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> BRICKS_WHITE = reg("white_bricks", new Block(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_ORANGE = reg("orange_bricks", new Block(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_MAGENTA = reg("magenta_bricks", new Block(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_LIGHT_BLUE = reg("light_blue_bricks", new Block(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_YELLOW = reg("yellow_bricks", new Block(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_LIME = reg("lime_bricks", new Block(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_PINK = reg("pink_bricks", new Block(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_GREY = reg("grey_bricks", new Block(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_SILVER = reg("silver_bricks", new Block(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_CYAN = reg("cyan_bricks", new Block(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_PURPLE = reg("purple_bricks", new Block(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_BLUE = reg("blue_bricks", new Block(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_BROWN = reg("brown_bricks", new Block(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_GREEN = reg("green_bricks", new Block(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_RED = reg("red_bricks", new Block(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_BLACK = reg("black_bricks", new Block(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_STAIRS_WHITE = reg("white_bricks_stairs", new StairsBlock(() -> BRICKS_WHITE.get().getDefaultState(), Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_STAIRS_ORANGE = reg("orange_bricks_stairs", new StairsBlock(() -> BRICKS_ORANGE.get().getDefaultState(), Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_STAIRS_MAGENTA = reg("magenta_bricks_stairs", new StairsBlock(() -> BRICKS_MAGENTA.get().getDefaultState(), Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_STAIRS_LIGHT_BLUE = reg("light_blue_bricks_stairs", new StairsBlock(() -> BRICKS_LIGHT_BLUE.get().getDefaultState(), Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_STAIRS_YELLOW = reg("yellow_bricks_stairs", new StairsBlock(() -> BRICKS_YELLOW.get().getDefaultState(), Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_STAIRS_LIME = reg("lime_bricks_stairs", new StairsBlock(() -> BRICKS_LIME.get().getDefaultState(), Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_STAIRS_PINK = reg("pink_bricks_stairs", new StairsBlock(() -> BRICKS_PINK.get().getDefaultState(), Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_STAIRS_GREY = reg("grey_bricks_stairs", new StairsBlock(() -> BRICKS_GREY.get().getDefaultState(), Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_STAIRS_SILVER = reg("silver_bricks_stairs", new StairsBlock(() -> BRICKS_SILVER.get().getDefaultState(), Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_STAIRS_CYAN = reg("cyan_bricks_stairs", new StairsBlock(() -> BRICKS_CYAN.get().getDefaultState(), Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_STAIRS_PURPLE = reg("purple_bricks_stairs", new StairsBlock(() -> BRICKS_PURPLE.get().getDefaultState(), Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_STAIRS_BLUE = reg("blue_bricks_stairs", new StairsBlock(() -> BRICKS_BLUE.get().getDefaultState(), Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_STAIRS_BROWN = reg("brown_bricks_stairs", new StairsBlock(() -> BRICKS_BROWN.get().getDefaultState(), Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_STAIRS_GREEN = reg("green_bricks_stairs", new StairsBlock(() -> BRICKS_GREEN.get().getDefaultState(), Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_STAIRS_RED = reg("red_bricks_stairs", new StairsBlock(() -> BRICKS_RED.get().getDefaultState(), Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_STAIRS_BLACK = reg("black_bricks_stairs", new StairsBlock(() -> BRICKS_BLACK.get().getDefaultState(), Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_SLAB_WHITE = reg("white_bricks_slab", new SlabBlock(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, SLAB);
    public static final RegistryObject<Block> BRICKS_SLAB_ORANGE = reg("orange_bricks_slab", new SlabBlock(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, SLAB);
    public static final RegistryObject<Block> BRICKS_SLAB_MAGENTA = reg("magenta_bricks_slab", new SlabBlock(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, SLAB);
    public static final RegistryObject<Block> BRICKS_SLAB_LIGHT_BLUE = reg("light_blue_bricks_slab", new SlabBlock(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, SLAB);
    public static final RegistryObject<Block> BRICKS_SLAB_YELLOW = reg("yellow_bricks_slab", new SlabBlock(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, SLAB);
    public static final RegistryObject<Block> BRICKS_SLAB_LIME = reg("lime_bricks_slab", new SlabBlock(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, SLAB);
    public static final RegistryObject<Block> BRICKS_SLAB_PINK = reg("pink_bricks_slab", new SlabBlock(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, SLAB);
    public static final RegistryObject<Block> BRICKS_SLAB_GREY = reg("grey_bricks_slab", new SlabBlock(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, SLAB);
    public static final RegistryObject<Block> BRICKS_SLAB_SILVER = reg("silver_bricks_slab", new SlabBlock(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, SLAB);
    public static final RegistryObject<Block> BRICKS_SLAB_CYAN = reg("cyan_bricks_slab", new SlabBlock(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, SLAB);
    public static final RegistryObject<Block> BRICKS_SLAB_PURPLE = reg("purple_bricks_slab", new SlabBlock(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, SLAB);
    public static final RegistryObject<Block> BRICKS_SLAB_BLUE = reg("blue_bricks_slab", new SlabBlock(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, SLAB);
    public static final RegistryObject<Block> BRICKS_SLAB_BROWN = reg("brown_bricks_slab", new SlabBlock(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, SLAB);
    public static final RegistryObject<Block> BRICKS_SLAB_GREEN = reg("green_bricks_slab", new SlabBlock(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, SLAB);
    public static final RegistryObject<Block> BRICKS_SLAB_RED = reg("red_bricks_slab", new SlabBlock(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, SLAB);
    public static final RegistryObject<Block> BRICKS_SLAB_BLACK = reg("black_bricks_slab", new SlabBlock(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, SLAB);
    public static final RegistryObject<Block> BRICKS_WALL_WHITE = reg("white_bricks_wall", new WallBlock(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_WALL_ORANGE = reg("orange_bricks_wall", new WallBlock(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_WALL_MAGENTA = reg("magenta_bricks_wall", new WallBlock(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_WALL_LIGHT_BLUE = reg("light_blue_bricks_wall", new WallBlock(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_WALL_YELLOW = reg("yellow_bricks_wall", new WallBlock(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_WALL_LIME = reg("lime_bricks_wall", new WallBlock(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_WALL_PINK = reg("pink_bricks_wall", new WallBlock(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_WALL_GREY = reg("grey_bricks_wall", new WallBlock(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_WALL_SILVER = reg("silver_bricks_wall", new WallBlock(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_WALL_CYAN = reg("cyan_bricks_wall", new WallBlock(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_WALL_PURPLE = reg("purple_bricks_wall", new WallBlock(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_WALL_BLUE = reg("blue_bricks_wall", new WallBlock(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_WALL_BROWN = reg("brown_bricks_wall", new WallBlock(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_WALL_GREEN = reg("green_bricks_wall", new WallBlock(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_WALL_RED = reg("red_bricks_wall", new WallBlock(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> BRICKS_WALL_BLACK = reg("black_bricks_wall", new WallBlock(Block.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    //saplings
    public static final RegistryObject<Block> OAK_SAPLING = reg("oak_seed", new ModSeedBlock(), ItemGroup.MISC);
    public static final RegistryObject<Block> SPRUCE_SAPLING = reg("spruce_seed", new ModSeedBlock(), ItemGroup.MISC);
    public static final RegistryObject<Block> BIRCH_SAPLING = reg("birch_seed", new ModSeedBlock(), ItemGroup.MISC);
    public static final RegistryObject<Block> JUNGLE_SAPLING = reg("jungle_seed", new ModSeedBlock(), ItemGroup.MISC);
    public static final RegistryObject<Block> ACACIA_SAPLING = reg("acacia_seed", new ModSeedBlock(), ItemGroup.MISC);
    public static final RegistryObject<Block> DARK_OAK_SAPLING = reg("dark_oak_seed", new ModSeedBlock(), ItemGroup.MISC);
    public static final RegistryObject<Block> LEBETHRON_SAPLING = reg("shire_oak_seed", new ModSeedBlock(), ItemGroup.MISC);
    //woodtypes
    public static final RegistryObject<Block> LEBETHRON_LOG = reg("shire_oak_log", new LogBlock(MaterialColor.WOOD, Block.Properties.from(Blocks.OAK_LOG)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> LEBETHRON_LOG_STRIPPED = reg("stripped_shire_oak_log", new LogBlock(MaterialColor.WOOD, Block.Properties.from(Blocks.STRIPPED_OAK_LOG)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> LEBETHRON_WOOD = reg("shire_oak_wood", new RotatedPillarBlock(Block.Properties.from(Blocks.OAK_WOOD)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> LEBETHRON_WOOD_STRIPPED = reg("stripped_shire_oak_wood", new RotatedPillarBlock(Block.Properties.from(Blocks.STRIPPED_OAK_WOOD)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> LEBETHRON_LEAVES = reg("shire_oak_leaves", new LeavesBlock(Block.Properties.from(Blocks.OAK_LEAVES)), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> LEBETHRON_PLANKS = reg("shire_oak_planks", new Block(Block.Properties.from(Blocks.OAK_PLANKS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> LEBETHRON_SLAB = reg("shire_oak_slab", new SlabBlock(Block.Properties.from(Blocks.OAK_SLAB)), ItemGroup.BUILDING_BLOCKS, SLAB);
    public static final RegistryObject<Block> LEBETHRON_STAIRS = reg("shire_oak_stairs", new StairsBlock(() -> LEBETHRON_PLANKS.get().getDefaultState(), Block.Properties.from(Blocks.OAK_STAIRS)), ItemGroup.BUILDING_BLOCKS, DROPSSELF);
    public static final RegistryObject<Block> LEBETHRON_FENCE = reg("shire_oak_fence", new FenceBlock(Block.Properties.from(Blocks.OAK_FENCE)), ItemGroup.DECORATIONS, DROPSSELF);
    public static final RegistryObject<Block> LEBETHRON_GATE = reg("shire_oak_gate", new FenceGateBlock(Block.Properties.from(Blocks.OAK_FENCE_GATE)), ItemGroup.REDSTONE, DROPSSELF);
    public static final RegistryObject<Block> LEBETHRON_TRAPDOOR = reg("shire_oak_trapdoor", new ModTrapDoorBlock(Block.Properties.from(Blocks.OAK_TRAPDOOR)), ItemGroup.REDSTONE, DROPSSELF);
    public static final RegistryObject<Block> LEBETHRON_BUTTON = reg("shire_oak_button", new ModWoodButtonBlock(Block.Properties.from(Blocks.OAK_BUTTON)), ItemGroup.REDSTONE, DROPSSELF);
    public static final RegistryObject<Block> LEBETHRON_PRESSURE = reg("shire_oak_pressure_plate", new ModPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.from(Blocks.OAK_PRESSURE_PLATE)), ItemGroup.REDSTONE, DROPSSELF);
    public static final RegistryObject<Block> LEBETHRON_SIGN = reg("shire_oak_sign", new StandingSignBlock(Block.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.WOOD), WoodType.OAK), ItemGroup.DECORATIONS, DROPSSELF);
    //utilities
    public static final RegistryObject<Block> ROHAN_WORKBENCH = reg("rohan_workbench", new RohanWorkstationBlock(Block.Properties.from(Blocks.CRAFTING_TABLE)), ItemGroup.TRANSPORTATION, DROPSSELF);
    //public static final RegistryObject<Block> DWARVEN_CHEST	= reg("dwarven_chest",new ModChestBlock());
    public static final RegistryObject<Block> MITHRIL_DOOR = reg("mithril_door", new ModDoorBlock(Block.Properties.from(ModBlocks.MITHRIL_BARS.get())), ItemGroup.REDSTONE, DROPSSELF);
    public static final RegistryObject<Block> IRON_BAR_DOOR = reg("iron_bar_door", new ModDoorBlock(Block.Properties.from(Blocks.IRON_BARS)), ItemGroup.REDSTONE, DROPSSELF);
    public static final RegistryObject<Block> MITHRIL_BAR_DOOR = reg("mithril_bar_door", new ModDoorBlock(Block.Properties.from(ModBlocks.MITHRIL_BARS.get())), ItemGroup.REDSTONE, DROPSSELF);
    public static final RegistryObject<Block> DWARVEN_DOOR = reg("dwarven_door", new ModDoorBlock(Block.Properties.from(Blocks.STONE)), ItemGroup.REDSTONE, DROPSSELF);
    //public static final RegistryObject<Block> HOBBIT_DOOR		= reg("shire_door", new ShireDoorBlock(Block.Properties.create(Material.WOOD)),ItemGroup.REDSTONE, DROPSSELF);
    //decorations
    //public static final RegistryObject<Block> DWARVEN_GRAFFITI	= reg("dwarven_graffiti", new Block(Block.Properties.from(Blocks.STONE)),ItemGroup.DECORATIONS)
    public static final RegistryObject<Block> DEER_SKULL = reg("deer_skull", new DeerSkullBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1.0F)));
    public static final RegistryObject<Block> DEER_SKULL_WALL = reg("deer_skull_wall", new DeerSkullWallBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1.0F)));
    public static final RegistryObject<Block> HORN_DEER_SKULL = reg("horned_deer_skull", new DeerSkullBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1.0F)));
    public static final RegistryObject<Block> HORN_DEER_SKULL_WALL = reg("horned_deer_skull_wall", new DeerSkullWallBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1.0F)));
    //pies
    public static final RegistryObject<Block> PORK_PIE = reg("pork_pie", new PieBlock(), ItemGroup.FOOD);
    public static final RegistryObject<Block> BEEF_PIE = reg("beef_pie", new PieBlock(), ItemGroup.FOOD);
    public static final RegistryObject<Block> MUTTON_PIE = reg("mutton_pie", new PieBlock(), ItemGroup.FOOD);
    public static final RegistryObject<Block> CHICKEN_PIE = reg("chicken_pie", new PieBlock(), ItemGroup.FOOD);
    public static final RegistryObject<Block> FISH_PIE = reg("fish_pie", new PieBlock(), ItemGroup.FOOD);
    public static final RegistryObject<Block> SALMON_PIE = reg("salmon_pie", new PieBlock(), ItemGroup.FOOD);
    public static final RegistryObject<Block> RABBIT_PIE = reg("rabbit_pie", new PieBlock(), ItemGroup.FOOD);
    public static final RegistryObject<Block> VENISON_PIE = reg("venison_pie", new PieBlock(), ItemGroup.FOOD);


    /**
     * -----------------funcs-----------------------
     */
    //register blocks
    @SafeVarargs
    public static RegistryObject<Block> reg(String id, Block block, List<Block>... datagroups) {
        RegistryObject<Block> ro = BLOCKS.register(id, () -> block);
        for (List<Block> datagroup : datagroups)
            datagroup.add(block);
        ALLBLOCKS.add(block);
        return ro;
    }

    //register RegistryObject<Block> and standard blockitem
    @SafeVarargs
    public static RegistryObject<Block> reg(String id, Block block, ItemGroup group, List<Block>... datagroups) {
        RegistryObject<Block> ro = reg(id, block, datagroups);
        if (group != null)
            ModItems.ITEMS.register(id, () -> new BlockItem(block, new Item.Properties().group(group)));
        else
            ModItems.ITEMS.register(id, () -> new BlockItem(block, new Item.Properties()));
        return ro;
    }
}

