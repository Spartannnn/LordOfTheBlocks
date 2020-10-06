package lotb.registries;

import lotb.LotbMod;
import lotb.blocks.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, LotbMod.MODID);

    private static final Block.Properties DEFAULT_PROP = Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 3.0F);

    //ores
    public static final RegistryObject<Block> PRISMARINE_ORE = _registerBlock("prismarine_ore", Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 3.0F));
    public static final RegistryObject<Block> QUARTZ_ORE = _registerBlock("quartz_ore", Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 3.0F));
    public static final RegistryObject<Block> GLOWSTONE_ORE = _registerBlock("glowstone_ore", Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 3.0F).lightValue(6));
    public static final RegistryObject<Block> MITHRIL_ORE = _registerBlock("mithril_ore", Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 3.0F));
    public static final RegistryObject<Block> MITHRIL_BLOCK = _registerBlock("mithril_block", Block.Properties.create(Material.IRON).hardnessAndResistance(10.0F, 25.0F));
    public static final RegistryObject<Block> MITHRIL_BARS = registerOther("mithril_bars", () -> new BarsBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(10.0F, 25.0F)));

    //building
    public static final RegistryObject<Block> STICK_BLOCK = registerOther("stick_block", () -> new RotatedPillarBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(0.5F).sound(SoundType.BAMBOO_SAPLING)));
    public static final RegistryObject<Block> BLAZE_BLOCK = registerOther("blaze_block", () -> new RotatedPillarBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(0.5F).sound(SoundType.BAMBOO_SAPLING)));
    public static final RegistryObject<Block> THATCH_BLOCK = _registerBlock("thatch", Block.Properties.create(Material.ORGANIC).hardnessAndResistance(0.5F).sound(SoundType.GROUND));
    public static final RegistryObject<Block> THATCH_STAIRS = registerOther("thatch_stairs", () -> new StairsBlock(() -> THATCH_BLOCK.get().getDefaultState(), Block.Properties.from(THATCH_BLOCK.get())));
    public static final RegistryObject<Block> THATCH_SLAB = registerOther("thatch_slab", () -> new SlabBlock(Block.Properties.from(THATCH_BLOCK.get())));
    public static final RegistryObject<Block> THATCH_WALL = registerOther("thatch_wall", () -> new WallBlock(Block.Properties.from(THATCH_BLOCK.get())));
    public static final RegistryObject<Block> CRACKED_STONE_BRICK_STAIR = registerOther("cracked_brick_stairs", () -> new StairsBlock(Blocks.CRACKED_STONE_BRICKS::getDefaultState, Block.Properties.from(Blocks.CRACKED_STONE_BRICKS)));
    public static final RegistryObject<Block> CRACKED_STONE_BRICK_SLAB = registerOther("cracked_brick_slab", () -> new SlabBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2F, 6.0F)));
    public static final RegistryObject<Block> CRACKED_STONE_BRICK_WALL = registerOther("cracked_brick_wall", () -> new WallBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5F, 6.0F)));
    public static final RegistryObject<Block> STONE_BRICK_PILLAR = registerOther("stone_brick_pillar", () -> new RotatedPillarBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5F, 6.0F)));

    //temp mordor rock
    public static final RegistryObject<Block> MORDOR_ROCK = _registerBlock("mordor_rock", Block.Properties.create(Material.ROCK).hardnessAndResistance(1.75F, 6.0F));
    public static final RegistryObject<Block> MORDOR_ROCK_POLISHED = _registerBlock("polished_mordor_rock", Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5F, 6.0F));
    public static final RegistryObject<Block> MORDOR_STAIR = registerOther("mordor_rock_stairs", () -> new StairsBlock(() -> MORDOR_ROCK.get().getDefaultState(), Block.Properties.from(MORDOR_ROCK.get())));
    public static final RegistryObject<Block> MORDOR_SLAB = registerOther("mordor_rock_slab", () -> new SlabBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2F, 6.0F)));
    public static final RegistryObject<Block> MORDOR_WALL = registerOther("mordor_rock_wall", () -> new WallBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.75F, 6.0F)));
    public static final RegistryObject<Block> MORDOR_BRICK = _registerBlock("mordor_brick", Block.Properties.create(Material.ROCK).hardnessAndResistance(1.75F, 6.0F));
    public static final RegistryObject<Block> MORDOR_BRICK_STAIR = registerOther("mordor_brick_stairs", () -> new StairsBlock(() -> MORDOR_BRICK.get().getDefaultState(), Block.Properties.from(MORDOR_BRICK.get())));
    public static final RegistryObject<Block> MORDOR_BRICK_SLAB = registerOther("mordor_brick_slab", () -> new SlabBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2F, 6.0F)));
    public static final RegistryObject<Block> MORDOR_BRICK_WALL = registerOther("mordor_brick_wall", () -> new WallBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.75F, 6.0F)));
    public static final RegistryObject<Block> MORDOR_BRICK_MOSSY = _registerBlock("mossy_mordor_brick", Block.Properties.create(Material.ROCK).hardnessAndResistance(1.75F, 6.0F));
    public static final RegistryObject<Block> MORDOR_BRICK_MOSSY_STAIR = registerOther("mossy_mordor_brick_stairs", () -> new StairsBlock(() -> MORDOR_BRICK_MOSSY.get().getDefaultState(), Block.Properties.from(MORDOR_BRICK.get())));
    public static final RegistryObject<Block> MORDOR_BRICK_MOSSY_SLAB = registerOther("mossy_mordor_brick_slab", () -> new SlabBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2F, 6.0F)));
    public static final RegistryObject<Block> MORDOR_BRICK_MOSSY_WALL = registerOther("mossy_mordor_brick_wall", () -> new WallBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.75F, 6.0F)));
    public static final RegistryObject<Block> MORDOR_BRICK_CRACKED = _registerBlock("cracked_mordor_brick", Block.Properties.create(Material.ROCK).hardnessAndResistance(1.75F, 6.0F));
    public static final RegistryObject<Block> MORDOR_BRICK_CRACKED_STAIR = registerOther("cracked_mordor_brick_stairs", () -> new StairsBlock(() -> MORDOR_BRICK_CRACKED.get().getDefaultState(), Block.Properties.from(MORDOR_BRICK.get())));
    public static final RegistryObject<Block> MORDOR_BRICK_CRACKED_SLAB = registerOther("cracked_mordor_brick_slab", () -> new SlabBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2F, 6.0F)));
    public static final RegistryObject<Block> MORDOR_BRICK_CRACKED_WALL = registerOther("cracked_mordor_brick_wall", () -> new WallBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.75F, 6.0F)));
    public static final RegistryObject<Block> CHISELED_MORDOR_BRICK = _registerBlock("chiseled_mordor_brick", Block.Properties.create(Material.ROCK).hardnessAndResistance(1.75F, 6.0F));
    public static final RegistryObject<Block> MORDOR_PILLAR = registerOther("mordor_pillar", () -> new RotatedPillarBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.75F, 6.0F)));

    //bricks
    public static final RegistryObject<Block> CLAY_WHITE = registerBlock("white_clay", Blocks.CLAY);
    public static final RegistryObject<Block> CLAY_ORANGE = registerBlock("orange_clay", Blocks.CLAY);
    public static final RegistryObject<Block> CLAY_MAGENTA = registerBlock("magenta_clay", Blocks.CLAY);
    public static final RegistryObject<Block> CLAY_LIGHT_BLUE = registerBlock("light_blue_clay", Blocks.CLAY);
    public static final RegistryObject<Block> CLAY_YELLOW = registerBlock("yellow_clay", Blocks.CLAY);
    public static final RegistryObject<Block> CLAY_LIME = registerBlock("lime_clay", Blocks.CLAY);
    public static final RegistryObject<Block> CLAY_PINK = registerBlock("pink_clay", Blocks.CLAY);
    public static final RegistryObject<Block> CLAY_GREY = registerBlock("grey_clay", Blocks.CLAY);
    public static final RegistryObject<Block> CLAY_SILVER = registerBlock("silver_clay", Blocks.CLAY);
    public static final RegistryObject<Block> CLAY_CYAN = registerBlock("cyan_clay", Blocks.CLAY);
    public static final RegistryObject<Block> CLAY_PURPLE = registerBlock("purple_clay", Blocks.CLAY);
    public static final RegistryObject<Block> CLAY_BLUE = registerBlock("blue_clay", Blocks.CLAY);
    public static final RegistryObject<Block> CLAY_BROWN = registerBlock("brown_clay", Blocks.CLAY);
    public static final RegistryObject<Block> CLAY_GREEN = registerBlock("green_clay", Blocks.CLAY);
    public static final RegistryObject<Block> CLAY_RED = registerBlock("red_clay", Blocks.CLAY);
    public static final RegistryObject<Block> CLAY_BLACK = registerBlock("black_clay", Blocks.CLAY);
    public static final RegistryObject<Block> BRICKS_WHITE = registerBlock("white_bricks", Blocks.BRICKS);
    public static final RegistryObject<Block> BRICKS_ORANGE = registerBlock("orange_bricks", Blocks.BRICKS);
    public static final RegistryObject<Block> BRICKS_MAGENTA = registerBlock("magenta_bricks", Blocks.BRICKS);
    public static final RegistryObject<Block> BRICKS_LIGHT_BLUE = registerBlock("light_blue_bricks", Blocks.BRICKS);
    public static final RegistryObject<Block> BRICKS_YELLOW = registerBlock("yellow_bricks", Blocks.BRICKS);
    public static final RegistryObject<Block> BRICKS_LIME = registerBlock("lime_bricks", Blocks.BRICKS);
    public static final RegistryObject<Block> BRICKS_PINK = registerBlock("pink_bricks", Blocks.BRICKS);
    public static final RegistryObject<Block> BRICKS_GREY = registerBlock("grey_bricks", Blocks.BRICKS);
    public static final RegistryObject<Block> BRICKS_SILVER = registerBlock("silver_bricks", Blocks.BRICKS);
    public static final RegistryObject<Block> BRICKS_CYAN = registerBlock("cyan_bricks", Blocks.BRICKS);
    public static final RegistryObject<Block> BRICKS_PURPLE = registerBlock("purple_bricks", Blocks.BRICKS);
    public static final RegistryObject<Block> BRICKS_BLUE = registerBlock("blue_bricks", Blocks.BRICKS);
    public static final RegistryObject<Block> BRICKS_BROWN = registerBlock("brown_bricks", Blocks.BRICKS);
    public static final RegistryObject<Block> BRICKS_GREEN = registerBlock("green_bricks", Blocks.BRICKS);
    public static final RegistryObject<Block> BRICKS_RED = registerBlock("red_bricks", Blocks.BRICKS);
    public static final RegistryObject<Block> BRICKS_BLACK = registerBlock("black_bricks", Blocks.BRICKS);
    public static final RegistryObject<Block> BRICKS_STAIRS_WHITE = registerBrickStairs("white", BRICKS_WHITE);
    public static final RegistryObject<Block> BRICKS_STAIRS_ORANGE = registerBrickStairs("orange", BRICKS_ORANGE);
    public static final RegistryObject<Block> BRICKS_STAIRS_MAGENTA = registerBrickStairs("magenta", BRICKS_MAGENTA);
    public static final RegistryObject<Block> BRICKS_STAIRS_LIGHT_BLUE = registerBrickStairs("light_blue", BRICKS_LIGHT_BLUE);
    public static final RegistryObject<Block> BRICKS_STAIRS_YELLOW = registerBrickStairs("yellow", BRICKS_YELLOW);
    public static final RegistryObject<Block> BRICKS_STAIRS_LIME = registerBrickStairs("lime", BRICKS_LIME);
    public static final RegistryObject<Block> BRICKS_STAIRS_PINK = registerBrickStairs("pink", BRICKS_PINK);
    public static final RegistryObject<Block> BRICKS_STAIRS_GREY = registerBrickStairs("grey", BRICKS_GREY);
    public static final RegistryObject<Block> BRICKS_STAIRS_SILVER = registerBrickStairs("silver", BRICKS_SILVER);
    public static final RegistryObject<Block> BRICKS_STAIRS_CYAN = registerBrickStairs("cyan", BRICKS_CYAN);
    public static final RegistryObject<Block> BRICKS_STAIRS_PURPLE = registerBrickStairs("purple", BRICKS_PURPLE);
    public static final RegistryObject<Block> BRICKS_STAIRS_BLUE = registerBrickStairs("blue", BRICKS_BLUE);
    public static final RegistryObject<Block> BRICKS_STAIRS_BROWN = registerBrickStairs("brown", BRICKS_BROWN);
    public static final RegistryObject<Block> BRICKS_STAIRS_GREEN = registerBrickStairs("green", BRICKS_GREEN);
    public static final RegistryObject<Block> BRICKS_STAIRS_RED = registerBrickStairs("red", BRICKS_RED);
    public static final RegistryObject<Block> BRICKS_STAIRS_BLACK = registerBrickStairs("black", BRICKS_BLACK);
    public static final RegistryObject<Block> BRICKS_SLAB_WHITE = registerBrickSlab("white");
    public static final RegistryObject<Block> BRICKS_SLAB_ORANGE = registerBrickSlab("orange");
    public static final RegistryObject<Block> BRICKS_SLAB_MAGENTA = registerBrickSlab("magenta");
    public static final RegistryObject<Block> BRICKS_SLAB_LIGHT_BLUE = registerBrickSlab("light_blue");
    public static final RegistryObject<Block> BRICKS_SLAB_YELLOW = registerBrickSlab("yellow");
    public static final RegistryObject<Block> BRICKS_SLAB_LIME = registerBrickSlab("lime");
    public static final RegistryObject<Block> BRICKS_SLAB_PINK = registerBrickSlab("pink");
    public static final RegistryObject<Block> BRICKS_SLAB_GREY = registerBrickSlab("grey");
    public static final RegistryObject<Block> BRICKS_SLAB_SILVER = registerBrickSlab("silver");
    public static final RegistryObject<Block> BRICKS_SLAB_CYAN = registerBrickSlab("cyan");
    public static final RegistryObject<Block> BRICKS_SLAB_PURPLE = registerBrickSlab("purple");
    public static final RegistryObject<Block> BRICKS_SLAB_BLUE = registerBrickSlab("blue");
    public static final RegistryObject<Block> BRICKS_SLAB_BROWN = registerBrickSlab("brown");
    public static final RegistryObject<Block> BRICKS_SLAB_GREEN = registerBrickSlab("green");
    public static final RegistryObject<Block> BRICKS_SLAB_RED = registerBrickSlab("red");
    public static final RegistryObject<Block> BRICKS_SLAB_BLACK = registerBrickSlab("black");
    public static final RegistryObject<Block> BRICKS_WALL_WHITE = registerBrickWall("white");
    public static final RegistryObject<Block> BRICKS_WALL_ORANGE = registerBrickWall("orange");
    public static final RegistryObject<Block> BRICKS_WALL_MAGENTA = registerBrickWall("magenta");
    public static final RegistryObject<Block> BRICKS_WALL_LIGHT_BLUE = registerBrickWall("light_blue");
    public static final RegistryObject<Block> BRICKS_WALL_YELLOW = registerBrickWall("yellow");
    public static final RegistryObject<Block> BRICKS_WALL_LIME = registerBrickWall("lime");
    public static final RegistryObject<Block> BRICKS_WALL_PINK = registerBrickWall("pink");
    public static final RegistryObject<Block> BRICKS_WALL_GREY = registerBrickWall("grey");
    public static final RegistryObject<Block> BRICKS_WALL_SILVER = registerBrickWall("silver");
    public static final RegistryObject<Block> BRICKS_WALL_CYAN = registerBrickWall("cyan");
    public static final RegistryObject<Block> BRICKS_WALL_PURPLE = registerBrickWall("purple");
    public static final RegistryObject<Block> BRICKS_WALL_BLUE = registerBrickWall("blue");
    public static final RegistryObject<Block> BRICKS_WALL_BROWN = registerBrickWall("brown");
    public static final RegistryObject<Block> BRICKS_WALL_GREEN = registerBrickWall("green");
    public static final RegistryObject<Block> BRICKS_WALL_RED = registerBrickWall("red");
    public static final RegistryObject<Block> BRICKS_WALL_BLACK = registerBrickWall("black");

    //saplings
    public static final RegistryObject<Block> OAK_SAPLING = registerSeed("oak_seed");
    public static final RegistryObject<Block> SPRUCE_SAPLING = registerSeed("spruce_seed");
    public static final RegistryObject<Block> BIRCH_SAPLING = registerSeed("birch_seed");
    public static final RegistryObject<Block> JUNGLE_SAPLING = registerSeed("jungle_seed");
    public static final RegistryObject<Block> ACACIA_SAPLING = registerSeed("acacia_seed");
    public static final RegistryObject<Block> DARK_OAK_SAPLING = registerSeed("dark_oak_seed");
    public static final RegistryObject<Block> LEBETHRON_SAPLING = registerSeed("shire_oak_seed");

    //woodtypes
    public static final RegistryObject<Block> LEBETHRON_LOG = registerOther("shire_oak_log", () -> new LogBlock(MaterialColor.WOOD, Block.Properties.from(Blocks.OAK_LOG)));
    public static final RegistryObject<Block> LEBETHRON_LOG_STRIPPED = registerOther("stripped_shire_oak_log", () -> new LogBlock(MaterialColor.WOOD, Block.Properties.from(Blocks.STRIPPED_OAK_LOG)));
    public static final RegistryObject<Block> LEBETHRON_WOOD = registerOther("shire_oak_wood", () -> new RotatedPillarBlock(Block.Properties.from(Blocks.OAK_WOOD)));
    public static final RegistryObject<Block> LEBETHRON_WOOD_STRIPPED = registerOther("stripped_hire_oak_wood", () -> new RotatedPillarBlock(Block.Properties.from(Blocks.STRIPPED_OAK_WOOD)));
    public static final RegistryObject<Block> LEBETHRON_LEAVES = registerOther("shire_oak_leaves", () -> new LeavesBlock(Block.Properties.from(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> LEBETHRON_PLANKS = registerBlock("shire_oak_planks", Blocks.OAK_PLANKS);
    public static final RegistryObject<Block> LEBETHRON_SLAB = registerOther("shire_oak_slab", () -> new SlabBlock(Block.Properties.from(Blocks.OAK_SLAB)));
    public static final RegistryObject<Block> LEBETHRON_STAIRS = registerOther("shire_oak_stairs", () -> new StairsBlock(() -> ModBlocks.LEBETHRON_PLANKS.get().getDefaultState(), Block.Properties.from(Blocks.OAK_STAIRS)));
    public static final RegistryObject<Block> LEBETHRON_FENCE = registerOther("shire_oak_fence", () -> new FenceBlock(Block.Properties.from(Blocks.OAK_FENCE)));
    public static final RegistryObject<Block> LEBETHRON_GATE = registerOther("shire_oak_gate", () -> new FenceGateBlock(Block.Properties.from(Blocks.OAK_FENCE_GATE)));
    public static final RegistryObject<Block> LEBETHRON_TRAPDOOR = registerOther("shire_oak_trapdoor", () -> new ModTrapDoorBlock(Block.Properties.from(Blocks.OAK_TRAPDOOR)));
    public static final RegistryObject<Block> LEBETHRON_BUTTON = registerOther("shire_oak_button", () -> new ModWoodButtonBlock(Block.Properties.from(Blocks.OAK_BUTTON)));
    public static final RegistryObject<Block> LEBETHRON_PRESSURE = registerOther("shire_oak_pressure_plate", () -> new ModPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.from(Blocks.OAK_PRESSURE_PLATE)));
    public static final RegistryObject<Block> LEBETHRON_SIGN = registerOther("shire_oak_sign", () -> new StandingSignBlock(Block.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.WOOD), WoodType.OAK));

    //utilities
    //public static final RegistryObject<Block> DWARVEN_CHEST	= reg("dwarven_chest",new ModChestBlock());
    //public static final RegistryObject<Block> HOBBIT_DOOR		= reg("shire_door", new ShireDoorBlock(Block.Properties.create(Material.WOOD)),ItemGroup.REDSTONE, DROPSSELF);
    public static final RegistryObject<Block> ROHAN_WORKBENCH = registerOther("rohan_workbench", () -> new RohanWorkstationBlock(Block.Properties.from(Blocks.CRAFTING_TABLE)));
    public static final RegistryObject<Block> MITHRIL_DOOR = registerOther("mithril_door", () -> new ModDoorBlock(Block.Properties.from(ModBlocks.MITHRIL_BARS.get())));
    public static final RegistryObject<Block> IRON_BAR_DOOR = registerOther("iron_bar_door", () -> new ModDoorBlock(Block.Properties.from(Blocks.IRON_BARS)));
    public static final RegistryObject<Block> MITHRIL_BAR_DOOR = registerOther("mithril_bar_door", () -> new ModDoorBlock(Block.Properties.from(ModBlocks.MITHRIL_BARS.get())));
    public static final RegistryObject<Block> DWARVEN_DOOR = registerOther("dwarven_door", () -> new ModDoorBlock(Block.Properties.from(Blocks.STONE)));

    //decorations
    //public static final RegistryObject<Block> DWARVEN_GRAFFITI	= reg("dwarven_graffiti", new Block(Block.Properties.from(Blocks.STONE)),ItemGroup.DECORATIONS)
    public static final RegistryObject<Block> DEER_SKULL = registerOther("deer_skull", () -> new DeerSkullBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1.0F)));
    public static final RegistryObject<Block> DEER_SKULL_WALL = registerOther("deer_skull_wall", () -> new DeerSkullWallBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1.0F)));
    public static final RegistryObject<Block> HORN_DEER_SKULL = registerOther("horned_deer_skull", () -> new DeerSkullBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1.0F)));
    public static final RegistryObject<Block> HORN_DEER_SKULL_WALL = registerOther("horned_deer_skull_wall", () -> new DeerSkullWallBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1.0F)));

    //pies
    public static final RegistryObject<Block> PORK_PIE = registerCake("prok_pie");
    public static final RegistryObject<Block> BEEF_PIE = registerCake("beef_pie");
    public static final RegistryObject<Block> MUTTON_PIE = registerCake("mutton_pie");
    public static final RegistryObject<Block> CHICKEN_PIE = registerCake("chicken_pie");
    public static final RegistryObject<Block> FISH_PIE = registerCake("fish_pie");
    public static final RegistryObject<Block> SALMON_PIE = registerCake("salmon_pie");
    public static final RegistryObject<Block> RABBIT_PIE = registerCake("rabbit_pie");
    public static final RegistryObject<Block> VENISON_PIE = registerCake("venison_pie");


    private static RegistryObject<Block> _registerBlock(String name, @Nullable Block.Properties properties) {
        return registerOther(name, () -> new Block(properties == null ? DEFAULT_PROP : properties));
    }

    private static RegistryObject<Block> registerBlock(String name) {
        return registerBlock(name, null);
    }

    private static RegistryObject<Block> registerBlock(String name, Block other) {
        return _registerBlock(name, Block.Properties.from(other));
    }


    private static RegistryObject<Block> registerOther(String name, Supplier<? extends Block> block) {
        return BLOCKS.register(name, block);
    }

    private static RegistryObject<Block> registerCake(String name) {
        return registerOther(name, PieBlock::new);
    }

    private static RegistryObject<Block> registerSeed(String name) {
        return registerOther(name, ModSeedBlock::new);
    }

    private static RegistryObject<Block> registerBrickWall(String color) {
        return registerOther(color + "_bricks_wall", () -> new WallBlock(Block.Properties.from(Blocks.BRICKS)));
    }

    private static RegistryObject<Block> registerBrickSlab(String color) {
        return registerOther(color + "_bricks_slab", () -> new SlabBlock(Block.Properties.from(Blocks.BRICKS)));
    }


    private static RegistryObject<Block> registerBrickStairs(String color, Supplier<Block> block) {
        return registerOther(color + "_bricks_stairs", () -> new StairsBlock(() -> block.get().getDefaultState(), Block.Properties.from(Blocks.BRICKS)));
    }

}

