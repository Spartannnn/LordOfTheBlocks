package lotb.registries;

import lotb.LotbMod;
import lotb.client.armour.AnimalArmour;
import lotb.items.EnchantedItem;
import lotb.items.KnifeItem;
import lotb.items.ModeledArmourItem;
import lotb.registries.materials.ModArmourTiers;
import lotb.registries.materials.ModFoods;
import lotb.registries.materials.ModToolTiers;
import net.minecraft.block.Block;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ModItems {
    public static final Item.Properties DEFAULT_PROP = new Item.Properties().group(LotbMod.LOTB_GROUP);

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, LotbMod.MODID);

    //mithril tools
    public static final RegistryObject<Item> MITHRIL_INGOT = registerItem("mithril_ingot");
    public static final RegistryObject<Item> MITHRIL_NUGGET = registerItem("mithril_nugget");
    public static final RegistryObject<Item> MITHRIL_SWORD = registerSword("mithril_sword", ModToolTiers.MITHRIL, 3, -2.3F);
    public static final RegistryObject<Item> MITHRIL_AXE = registerAxe("mithril_axe", ModToolTiers.MITHRIL, 1.5f, -2.8f);
    public static final RegistryObject<Item> MITHRIL_PICK = registerPickaxe("mithril_pickaxe", ModToolTiers.MITHRIL, 1, -2.6f);
    public static final RegistryObject<Item> MITHRIL_SHOVEL = registerShovel("mithril_shovel", ModToolTiers.MITHRIL, 5.0F, -3.0F);
    public static final RegistryObject<Item> MITHRIL_HOE = registerHoe("mithril_hoe", ModToolTiers.MITHRIL, 1.0F);
    public static final RegistryObject<Item> MITHRIL_HELMET = registerArmor("mithril_helmet", ModArmourTiers.MITHRIL, EquipmentSlotType.HEAD);
    public static final RegistryObject<Item> MITHRIL_CHESTPLATE = registerArmor("mithril_chestplate", ModArmourTiers.MITHRIL, EquipmentSlotType.CHEST);
    public static final RegistryObject<Item> MITHRIL_LEGGINGS = registerArmor("mithril_leggings", ModArmourTiers.MITHRIL, EquipmentSlotType.LEGS);
    public static final RegistryObject<Item> MITHRIL_BOOTS = registerArmor("mithril_boots", ModArmourTiers.MITHRIL, EquipmentSlotType.FEET);
    public static final RegistryObject<Item> MITHRIL_CHAIN_HELMET = registerArmor("mithril_chain_helmet", ModArmourTiers.MITHRILCHAIN, EquipmentSlotType.HEAD);
    public static final RegistryObject<Item> MITHRIL_CHAIN_CHESTPLATE = registerArmor("mithril_chain_chestplate", ModArmourTiers.MITHRILCHAIN, EquipmentSlotType.CHEST);
    public static final RegistryObject<Item> MITHRIL_CHAIN_LEGGINGS = registerArmor("mithril_chain_leggings", ModArmourTiers.MITHRILCHAIN, EquipmentSlotType.LEGS);
    public static final RegistryObject<Item> MITHRIL_CHAIN_BOOTS = registerArmor("mithril_chain_boots", ModArmourTiers.MITHRILCHAIN, EquipmentSlotType.FEET);
    public static final RegistryObject<Item> MITHRIL_HORSE_ARMOR = registerOther("mithril_horse_armor", () -> new HorseArmorItem(15, "mithril", new Item.Properties().group(LotbMod.LOTB_GROUP).maxStackSize(1)));
    //furs
    public static final RegistryObject<Item> FOX_HELMET = registerArmor("fox_fur_helmet", ModArmourTiers.FOX, EquipmentSlotType.HEAD);
    public static final RegistryObject<Item> FOX_CHESTPLATE =registerArmor("fox_fur_chestplate", ModArmourTiers.FOX, EquipmentSlotType.CHEST);
    public static final RegistryObject<Item> FOX_LEGGINGS =registerArmor("fox_fur_leggings", ModArmourTiers.FOX, EquipmentSlotType.LEGS);
    public static final RegistryObject<Item> FOX_BOOTS = registerArmor("fox_fur_boots", ModArmourTiers.FOX, EquipmentSlotType.FEET);
    public static final RegistryObject<Item> WOLF_HELMET = registerArmor("wolf_fur_helmet", ModArmourTiers.WOLF, EquipmentSlotType.HEAD);
    public static final RegistryObject<Item> WOLF_CHESTPLATE =registerArmor("wolf_fur_chestplate", ModArmourTiers.WOLF, EquipmentSlotType.CHEST);
    public static final RegistryObject<Item> WOLF_LEGGINGS =registerArmor("wolf_fur_leggings", ModArmourTiers.WOLF, EquipmentSlotType.LEGS);
    public static final RegistryObject<Item> WOLF_BOOTS = registerArmor("wolf_fur_boots", ModArmourTiers.WOLF, EquipmentSlotType.FEET);
    public static final RegistryObject<Item> BADGER_HELMET = registerArmor("badger_fur_helmet", ModArmourTiers.BADGER, EquipmentSlotType.HEAD);
    public static final RegistryObject<Item> BADGER_CHESTPLATE =registerArmor("badger_fur_chestplate", ModArmourTiers.BADGER, EquipmentSlotType.CHEST);
    public static final RegistryObject<Item> BADGER_LEGGINGS =registerArmor("badger_fur_leggings", ModArmourTiers.BADGER, EquipmentSlotType.LEGS);
    public static final RegistryObject<Item> BADGER_BOOTS = registerArmor("badger_fur_boots", ModArmourTiers.BADGER, EquipmentSlotType.FEET);

    //faction armour+weapons
    public static final RegistryObject<Item> GONDOR_HELMET = registerArmor("gondor_helmet", ModArmourTiers.GONDOR, EquipmentSlotType.HEAD);
    public static final RegistryObject<Item> GONDOR_CHESTPLATE =registerArmor("gondor_chestplate", ModArmourTiers.GONDOR, EquipmentSlotType.CHEST);
    public static final RegistryObject<Item> GONDOR_LEGGINGS =registerArmor("gondor_leggings", ModArmourTiers.GONDOR, EquipmentSlotType.LEGS);
    public static final RegistryObject<Item> GONDOR_BOOTS = registerArmor("gondor_boots", ModArmourTiers.GONDOR, EquipmentSlotType.FEET);
    public static final RegistryObject<Item> ROHAN_HELMET = registerArmor("rohan_helmet", ModArmourTiers.ROHAN, EquipmentSlotType.HEAD);
    public static final RegistryObject<Item> ROHAN_CHESTPLATE =registerArmor("rohan_chestplate", ModArmourTiers.ROHAN, EquipmentSlotType.CHEST);
    public static final RegistryObject<Item> ROHAN_LEGGINGS =registerArmor("rohan_leggings", ModArmourTiers.ROHAN, EquipmentSlotType.LEGS);
    public static final RegistryObject<Item> ROHAN_BOOTS = registerArmor("rohan_boots", ModArmourTiers.ROHAN, EquipmentSlotType.FEET);
    public static final RegistryObject<Item> MORDOR_HELMET = registerArmor("mordor_helmet", ModArmourTiers.MORDOR, EquipmentSlotType.HEAD);
    public static final RegistryObject<Item> MORDOR_CHESTPLATE =registerArmor("mordor_chestplate", ModArmourTiers.MORDOR, EquipmentSlotType.CHEST);
    public static final RegistryObject<Item> MORDOR_LEGGINGS =registerArmor("mordor_leggings", ModArmourTiers.MORDOR, EquipmentSlotType.LEGS);
    public static final RegistryObject<Item> MORDOR_BOOTS = registerArmor("mordor_boots", ModArmourTiers.MORDOR, EquipmentSlotType.FEET);
    public static final RegistryObject<Item> URUK_HELMET = registerArmor("uruk_helmet", ModArmourTiers.URUK, EquipmentSlotType.HEAD);
    public static final RegistryObject<Item> URUK_CHESTPLATE =registerArmor("uruk_chestplate", ModArmourTiers.URUK, EquipmentSlotType.CHEST);
    public static final RegistryObject<Item> URUK_LEGGINGS =registerArmor("uruk_leggings", ModArmourTiers.URUK, EquipmentSlotType.LEGS);
    public static final RegistryObject<Item> URUK_BOOTS = registerArmor("uruk_boots", ModArmourTiers.URUK, EquipmentSlotType.FEET);

    //misc materials
    public static final RegistryObject<Item> FOX_PELT = registerItem("fox_pelt");
    public static final RegistryObject<Item> WOLF_PELT = registerItem("wolf_pelt");
    public static final RegistryObject<Item> BADGER_FUR = registerItem("badger_fur");
    public static final RegistryObject<Item> COIN = registerItem("coin");

    //clay and bricks
    public static final RegistryObject<Item> CLAY_BALL_WHITE = registerItem("white_clay_ball");
    public static final RegistryObject<Item> CLAY_BALL_ORANGE = registerItem("orange_clay_ball");
    public static final RegistryObject<Item> CLAY_BALL_MAGENTA = registerItem("magenta_clay_ball");
    public static final RegistryObject<Item> CLAY_BALL_LIGHT_BLUE = registerItem("light_blue_clay_ball");
    public static final RegistryObject<Item> CLAY_BALL_YELLOW = registerItem("yellow_clay_ball");
    public static final RegistryObject<Item> CLAY_BALL_LIME = registerItem("lime_clay_ball");
    public static final RegistryObject<Item> CLAY_BALL_PINK = registerItem("pink_clay_ball");
    public static final RegistryObject<Item> CLAY_BALL_GREY = registerItem("grey_clay_ball");
    public static final RegistryObject<Item> CLAY_BALL_SILVER = registerItem("silver_clay_ball");
    public static final RegistryObject<Item> CLAY_BALL_CYAN = registerItem("cyan_clay_ball");
    public static final RegistryObject<Item> CLAY_BALL_PURPLE = registerItem("purple_clay_ball");
    public static final RegistryObject<Item> CLAY_BALL_BLUE = registerItem("blue_clay_ball");
    public static final RegistryObject<Item> CLAY_BALL_BROWN = registerItem("brown_clay_ball");
    public static final RegistryObject<Item> CLAY_BALL_GREEN = registerItem("green_clay_ball");
    public static final RegistryObject<Item> CLAY_BALL_RED = registerItem("red_clay_ball");
    public static final RegistryObject<Item> CLAY_BALL_BLACK = registerItem("black_clay_ball");
    public static final RegistryObject<Item> BRICK_WHITE = registerItem("white_brick");
    public static final RegistryObject<Item> BRICK_ORANGE = registerItem("orange_brick");
    public static final RegistryObject<Item> BRICK_MAGENTA = registerItem("magenta_brick");
    public static final RegistryObject<Item> BRICK_LIGHT_BLUE = registerItem("light_blue_brick");
    public static final RegistryObject<Item> BRICK_YELLOW = registerItem("yellow_brick");
    public static final RegistryObject<Item> BRICK_LIME = registerItem("lime_brick");
    public static final RegistryObject<Item> BRICK_PINK = registerItem("pink_brick");
    public static final RegistryObject<Item> BRICK_GREY = registerItem("grey_brick");
    public static final RegistryObject<Item> BRICK_SILVER = registerItem("silver_brick");
    public static final RegistryObject<Item> BRICK_CYAN = registerItem("cyan_brick");
    public static final RegistryObject<Item> BRICK_PURPLE = registerItem("purple_brick");
    public static final RegistryObject<Item> BRICK_BLUE = registerItem("blue_brick");
    public static final RegistryObject<Item> BRICK_BROWN = registerItem("brown_brick");
    public static final RegistryObject<Item> BRICK_GREEN = registerItem("green_brick");
    public static final RegistryObject<Item> BRICK_RED = registerItem("red_brick");
    public static final RegistryObject<Item> BRICK_BLACK = registerItem("black_brick");

    //boats
    public static final RegistryObject<Item> OAK_BOAT = registerOther("shire_oak_boat", () -> new BoatItem(BoatEntity.Type.OAK, new Item.Properties().maxStackSize(1).group(LotbMod.LOTB_GROUP)));

    //foods
    public static final RegistryObject<Item> VENISON = registerFood("venison", ModFoods.VENISON);
    public static final RegistryObject<Item> COOKED_VENISON = registerFood("cooked_venison", ModFoods.COOKED_VENISON);
    public static final RegistryObject<Item> SILVER_APPLE = registerFood("silver_apple", ModFoods.SILVER_APPLE);
    public static final RegistryObject<Item> ENCHANTED_SILVER_APPLE = registerFood("enchanted_silver_apple", ModFoods.ENCHANTED_SILVER_APPLE);
    public static final RegistryObject<Item> HARD_TACK = registerFood("hard_tack", ModFoods.HARD_TACK);
    public static final RegistryObject<Item> LEMBAS = registerFood("lembas", ModFoods.LEMBAS);
    public static final RegistryObject<Item> CRAM = registerFood("cram", ModFoods.CRAM);

    //knifes
    public static final RegistryObject<Item> WOODEN_KNIFE = registerKnife("wooden_knife", ItemTier.WOOD);
    public static final RegistryObject<Item> STONE_KNIFE = registerKnife("stone_knife", ItemTier.STONE);
    public static final RegistryObject<Item> IRON_KNIFE = registerKnife("iron_knife", ItemTier.IRON);
    public static final RegistryObject<Item> GOLD_KNIFE = registerKnife("gold_knife", ItemTier.GOLD);
    public static final RegistryObject<Item> DIAMOND_KNIFE = registerKnife("diamond_knife", ItemTier.DIAMOND);
    public static final RegistryObject<Item> MITHRIL_KNIFE = registerKnife("mithril_knife", ModToolTiers.MITHRIL);

    public static final RegistryObject<Item> DEER_SKULL = registerSkull("deer_skull", ModBlocks.DEER_SKULL, ModBlocks.DEER_SKULL_WALL);
    public static final RegistryObject<Item> HORNED_DEER_SKULL = registerSkull("horned_deer_skull", ModBlocks.HORN_DEER_SKULL, ModBlocks.HORN_DEER_SKULL_WALL);


    private static RegistryObject<Item> registerSkull(String name, RegistryObject<Block> skull, RegistryObject<Block> wand) {
        return registerOther(name, () -> new WallOrFloorItem(skull.get(), wand.get(), DEFAULT_PROP));
    }

    private static RegistryObject<Item> registerFood(String name, Food food) {
        return registerOther(name, () -> new Item(new Item.Properties().group(LotbMod.LOTB_GROUP).food(food)));
    }

    private static RegistryObject<Item> registerOther(String name, Supplier<? extends Item> supplier) {
        return ITEMS.register(name, supplier);
    }

    private static RegistryObject<Item> registerItem(String name, @Nullable Item.Properties properties) {
        return ITEMS.register(name, () -> new Item(properties == null ? DEFAULT_PROP : properties));
    }

    private static RegistryObject<Item> registerItem(String name) {
        return registerItem(name, null);
    }

    private static RegistryObject<Item> registerKnife(String name, IItemTier tier) {
        return registerOther(name, () -> new KnifeItem(tier, DEFAULT_PROP));
    }

    private static RegistryObject<Item> registerSword(String name, IItemTier tier, int damage, float attackSpeed) {
        return ITEMS.register(name, () -> new SwordItem(tier, damage, attackSpeed, DEFAULT_PROP));
    }

    private static RegistryObject<Item> registerAxe(String name, IItemTier tier, float damage, float speed) {
        return ITEMS.register(name, () -> new AxeItem(tier, damage, speed, DEFAULT_PROP));
    }

    private static RegistryObject<Item> registerPickaxe(String name, IItemTier tier, int damage, float speed) {
        return ITEMS.register(name, () -> new PickaxeItem(tier, damage, speed, DEFAULT_PROP));
    }

    private static RegistryObject<Item> registerShovel(String name, IItemTier tier, float damage, float speed) {
        return ITEMS.register(name, () -> new ShovelItem(tier, damage, speed, DEFAULT_PROP));
    }

    private static RegistryObject<Item> registerHoe(String name, IItemTier tier, float speed) {
        return ITEMS.register(name, () -> new HoeItem(tier, speed, DEFAULT_PROP));
    }

    private static RegistryObject<Item> registerArmor(String name, IArmorMaterial armor, EquipmentSlotType slot) {
        return ITEMS.register(name, () -> new ArmorItem(armor, slot, DEFAULT_PROP));
    }

}
