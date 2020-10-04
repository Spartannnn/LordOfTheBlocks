package lotb.registries;

import lotb.LotbMod;
import lotb.client.armour.AnimalArmour;
import lotb.items.EnchantedItem;
import lotb.items.KnifeItem;
import lotb.items.ModeledArmourItem;
import lotb.registries.materials.ModArmourTiers;
import lotb.registries.materials.ModFoods;
import lotb.registries.materials.ModToolTiers;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BoatItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.Rarity;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SkullItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.WallOrFloorItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("unused")
public class ModItems {
	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS,LotbMod.MODID);
	
	//mithril tools
	public static final Item MITHRIL_INGOT 		= new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item MITHRIL_NUGGET 	= new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item MITHRIL_SWORD 	= new SwordItem(  ModToolTiers.MITHRIL, 3	,-2.3f,	new Item.Properties().group(ItemGroup.COMBAT));
	public static final Item MITHRIL_AXE 	= new AxeItem(	  ModToolTiers.MITHRIL, 1.5f,-2.8f, new Item.Properties().group(ItemGroup.TOOLS));
	public static final Item MITHRIL_PICK	= new PickaxeItem(ModToolTiers.MITHRIL, 1	,-2.6f, new Item.Properties().group(ItemGroup.TOOLS));
	public static final Item MITHRIL_SHOVEL	= new ShovelItem( ModToolTiers.MITHRIL, 5.0f,-3.0f, new Item.Properties().group(ItemGroup.TOOLS));
	public static final Item MITHRIL_HOE	= new HoeItem(	  ModToolTiers.MITHRIL, 1.0f,		new Item.Properties().group(ItemGroup.TOOLS));
	public static final Item MITHRIL_HELMET		= reg( new ArmorItem(ModArmourTiers.MITHRIL,EquipmentSlotType.HEAD,new Item.Properties().group(ItemGroup.COMBAT)), "mithril_chain_helmet");
	public static final Item MITHRIL_CHESTPLATE	= reg( new ArmorItem(ModArmourTiers.MITHRIL,EquipmentSlotType.CHEST,new Item.Properties().group(ItemGroup.COMBAT)), "mithril_chain_chestplate");
	public static final Item MITHRIL_LEGGINGS	= reg( new ArmorItem(ModArmourTiers.MITHRIL,EquipmentSlotType.LEGS,new Item.Properties().group(ItemGroup.COMBAT)), "mithril_chain_leggings");
	public static final Item MITHRIL_BOOTS		= reg( new ArmorItem(ModArmourTiers.MITHRIL,EquipmentSlotType.FEET,new Item.Properties().group(ItemGroup.COMBAT)), "mithril_chain_boots");
	public static final Item MITHRIL_CHAIN_HELMET		= reg( new ArmorItem(ModArmourTiers.MITHRILCHAIN,EquipmentSlotType.HEAD,new Item.Properties().group(ItemGroup.COMBAT)), "mithril_chain_helmet");
	public static final Item MITHRIL_CHAIN_CHESTPLATE	= reg( new ArmorItem(ModArmourTiers.MITHRILCHAIN,EquipmentSlotType.CHEST,new Item.Properties().group(ItemGroup.COMBAT)),"mithril_chain_chestplate");
	public static final Item MITHRIL_CHAIN_LEGGINGS		= reg( new ArmorItem(ModArmourTiers.MITHRILCHAIN,EquipmentSlotType.LEGS,new Item.Properties().group(ItemGroup.COMBAT)), "mithril_chain_leggings");
	public static final Item MITHRIL_CHAIN_BOOTS		= reg( new ArmorItem(ModArmourTiers.MITHRILCHAIN,EquipmentSlotType.FEET,new Item.Properties().group(ItemGroup.COMBAT)), "mithril_chain_boots");
	public static final Item MITHRIL_HORSE_ARMOR = reg(new HorseArmorItem(15, "mithril", (new Item.Properties()).maxStackSize(1).group(ItemGroup.MISC)),"mithril_horse_armor");
	//furs
	public static final Item FOX_HELMET		= reg( new ModeledArmourItem(ModArmourTiers.FOX,EquipmentSlotType.HEAD,new AnimalArmour(),new Item.Properties().group(ItemGroup.COMBAT)), "fox_fur_helmet");
	public static final Item FOX_CHESTPLATE = reg( new ModeledArmourItem(ModArmourTiers.FOX,EquipmentSlotType.CHEST,new AnimalArmour(),new Item.Properties().group(ItemGroup.COMBAT)),"fox_fur_chestplate");
	public static final Item FOX_LEGGINGS	= reg( new ModeledArmourItem(ModArmourTiers.FOX,EquipmentSlotType.LEGS,new AnimalArmour(),new Item.Properties().group(ItemGroup.COMBAT)), "fox_fur_leggings");
	public static final Item FOX_BOOTS		= reg( new ModeledArmourItem(ModArmourTiers.FOX,EquipmentSlotType.FEET,new AnimalArmour(),new Item.Properties().group(ItemGroup.COMBAT)), "fox_fur_boots");
	public static final Item WOLF_HELMET	= reg( new ModeledArmourItem(ModArmourTiers.WOLF,EquipmentSlotType.HEAD,new AnimalArmour(),new Item.Properties().group(ItemGroup.COMBAT)), "wolf_fur_helmet");
	public static final Item WOLF_CHESTPLATE= reg( new ModeledArmourItem(ModArmourTiers.WOLF,EquipmentSlotType.CHEST,new AnimalArmour(),new Item.Properties().group(ItemGroup.COMBAT)),"wolf_fur_chestplate");
	public static final Item WOLF_LEGGINGS	= reg( new ModeledArmourItem(ModArmourTiers.WOLF,EquipmentSlotType.LEGS,new AnimalArmour(),new Item.Properties().group(ItemGroup.COMBAT)), "wolf_fur_leggings");
	public static final Item WOLF_BOOTS		= reg( new ModeledArmourItem(ModArmourTiers.WOLF,EquipmentSlotType.FEET,new AnimalArmour(),new Item.Properties().group(ItemGroup.COMBAT)), "wolf_fur_boots");
	public static final Item BADGER_HELMET		= reg( new ModeledArmourItem(ModArmourTiers.BADGER,EquipmentSlotType.HEAD,new AnimalArmour(),new Item.Properties().group(ItemGroup.COMBAT)), "badger_fur_helmet");
	public static final Item BADGER_CHESTPLATE 	= reg( new ModeledArmourItem(ModArmourTiers.BADGER,EquipmentSlotType.CHEST,new AnimalArmour(),new Item.Properties().group(ItemGroup.COMBAT)),"badger_fur_chestplate");
	public static final Item BADGER_LEGGINGS	= reg( new ModeledArmourItem(ModArmourTiers.BADGER,EquipmentSlotType.LEGS,new AnimalArmour(),new Item.Properties().group(ItemGroup.COMBAT)), "badger_fur_leggings");
	public static final Item BADGER_BOOTS		= reg( new ModeledArmourItem(ModArmourTiers.BADGER,EquipmentSlotType.FEET,new AnimalArmour(),new Item.Properties().group(ItemGroup.COMBAT)), "badger_fur_boots");
	//faction armour+weapons
	public static final Item GONDOR_HELMET		= reg( new ArmorItem(ModArmourTiers.GONDOR,EquipmentSlotType.HEAD,new Item.Properties().group(ItemGroup.COMBAT)) ,"gondor_helmet");
	public static final Item GONDOR_CHESTPLATE	= reg( new ArmorItem(ModArmourTiers.GONDOR,EquipmentSlotType.CHEST,new Item.Properties().group(ItemGroup.COMBAT)),"gondor_chestplate");
	public static final Item GONDOR_LEGGINGS	= reg( new ArmorItem(ModArmourTiers.GONDOR,EquipmentSlotType.LEGS,new Item.Properties().group(ItemGroup.COMBAT)) ,"gondor_leggings");
	public static final Item GONDOR_BOOTS		= reg( new ArmorItem(ModArmourTiers.GONDOR,EquipmentSlotType.FEET,new Item.Properties().group(ItemGroup.COMBAT)) ,"gondor_boots");
	public static final Item ROHAN_HELMET		= reg( new ArmorItem(ModArmourTiers.ROHAN,EquipmentSlotType.HEAD,new Item.Properties().group(ItemGroup.COMBAT)) ,"rohan_helmet");
	public static final Item ROHAN_CHESTPLATE	= reg( new ArmorItem(ModArmourTiers.ROHAN,EquipmentSlotType.CHEST,new Item.Properties().group(ItemGroup.COMBAT)),"rohan_chestplate");
	public static final Item ROHAN_LEGGINGS		= reg( new ArmorItem(ModArmourTiers.ROHAN,EquipmentSlotType.LEGS,new Item.Properties().group(ItemGroup.COMBAT)) ,"rohan_leggings");
	public static final Item ROHAN_BOOTS		= reg( new ArmorItem(ModArmourTiers.ROHAN,EquipmentSlotType.FEET,new Item.Properties().group(ItemGroup.COMBAT)) ,"rohan_boots");
	public static final Item MORDOR_HELMET		= reg( new ArmorItem(ModArmourTiers.MORDOR,EquipmentSlotType.HEAD,new Item.Properties().group(ItemGroup.COMBAT)) ,"mordor_helmet");
	public static final Item MORDOR_CHESTPLATE	= reg( new ArmorItem(ModArmourTiers.MORDOR,EquipmentSlotType.CHEST,new Item.Properties().group(ItemGroup.COMBAT)),"mordor_chestplate");
	public static final Item MORDOR_LEGGINGS	= reg( new ArmorItem(ModArmourTiers.MORDOR,EquipmentSlotType.LEGS,new Item.Properties().group(ItemGroup.COMBAT)) ,"mordor_leggings");
	public static final Item MORDOR_BOOTS		= reg( new ArmorItem(ModArmourTiers.MORDOR,EquipmentSlotType.FEET,new Item.Properties().group(ItemGroup.COMBAT)) ,"mordor_boots");
	public static final Item URUK_HELMET		= reg( new ArmorItem(ModArmourTiers.URUK,EquipmentSlotType.HEAD,new Item.Properties().group(ItemGroup.COMBAT)) ,"uruk_helmet");
	public static final Item URUK_CHESTPLATE	= reg( new ArmorItem(ModArmourTiers.URUK,EquipmentSlotType.CHEST,new Item.Properties().group(ItemGroup.COMBAT)),"uruk_chestplate");
	public static final Item URUK_LEGGINGS		= reg( new ArmorItem(ModArmourTiers.URUK,EquipmentSlotType.LEGS,new Item.Properties().group(ItemGroup.COMBAT)) ,"uruk_leggings");
	public static final Item URUK_BOOTS			= reg( new ArmorItem(ModArmourTiers.URUK,EquipmentSlotType.FEET,new Item.Properties().group(ItemGroup.COMBAT)) ,"uruk_boots");
	//misc materials
	public static final Item FOX_PELT	= reg( new Item(new Item.Properties().group(ItemGroup.MISC)),"fox_pelt");
	public static final Item WOLF_PELT	= reg( new Item(new Item.Properties().group(ItemGroup.MISC)),"wolf_pelt");
	public static final Item BADGER_FUR	= reg( new Item(new Item.Properties().group(ItemGroup.MISC)),"badger_fur");
	public static final Item COIN 		= reg( new Item(new Item.Properties().group(ItemGroup.MISC)),"gold_coin");
	//clay and bricks
	public static final Item CLAY_BALL_WHITE 	= new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item CLAY_BALL_ORANGE 	= new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item CLAY_BALL_MAGENTA 	= new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item CLAY_BALL_LIGHT_BLUE=new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item CLAY_BALL_YELLOW 	= new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item CLAY_BALL_LIME  	= new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item CLAY_BALL_PINK  	= new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item CLAY_BALL_GREY  	= new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item CLAY_BALL_SILVER  	= new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item CLAY_BALL_CYAN  	= new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item CLAY_BALL_PURPLE  	= new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item CLAY_BALL_BLUE  	= new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item CLAY_BALL_BROWN  	= new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item CLAY_BALL_GREEN  	= new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item CLAY_BALL_RED  	= new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item CLAY_BALL_BLACK  	= new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item BRICK_WHITE  		= new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item BRICK_ORANGE 	 	= new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item BRICK_MAGENTA  	= new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item BRICK_LIGHT_BLUE  	= new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item BRICK_YELLOW  		= new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item BRICK_LIME  		= new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item BRICK_PINK  		= new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item BRICK_GREY  		= new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item BRICK_SILVER  		= new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item BRICK_CYAN  		= new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item BRICK_PURPLE  		= new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item BRICK_BLUE 	 	= new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item BRICK_BROWN	  	= new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item BRICK_GREEN	  	= new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item BRICK_RED  		= new Item(new Item.Properties().group(ItemGroup.MISC));
	public static final Item BRICK_BLACK  		= new Item(new Item.Properties().group(ItemGroup.MISC));
	//boats
	public static final Item OAK_BOAT 			= reg(new BoatItem(BoatEntity.Type.OAK, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)),"shire_oak_boat");
	//foods
	public static final Item VENISON		= new Item(new Item.Properties().group(ItemGroup.FOOD).food(ModFoods.VENISON));
	public static final Item COOKED_VENISON	= new Item(new Item.Properties().group(ItemGroup.FOOD).food(ModFoods.COOKED_VENISON));
	public static final Item SILVER_APPLE	= new Item(new Item.Properties().group(ItemGroup.FOOD).food(ModFoods.SILVER_APPLE));
	public static final Item ENCHANTED_SILVER_APPLE = new EnchantedItem(new Item.Properties().group(ItemGroup.FOOD).food(ModFoods.ENCHANTED_SILVER_APPLE));
	public static final Item HARD_TACK	= reg(new Item(new Item.Properties().group(ItemGroup.FOOD).food(ModFoods.HARD_TACK)), "hard_tack");
	public static final Item LEMBAS		= reg(new Item(new Item.Properties().group(ItemGroup.FOOD).food(ModFoods.LEMBAS)), "lembas");
	public static final Item CRAM		= reg(new Item(new Item.Properties().group(ItemGroup.FOOD).food(ModFoods.CRAM)), "cram");
	//knifes
	public static final Item WOODEN_KNIFE	= reg( new KnifeItem(ItemTier.WOOD,new Item.Properties().group(ItemGroup.COMBAT)),	"wooden_knife");
	public static final Item STONE_KNIFE	= reg( new KnifeItem(ItemTier.STONE,new Item.Properties().group(ItemGroup.COMBAT)),	"stone_knife");
	public static final Item IRON_KNIFE		= reg( new KnifeItem(ItemTier.IRON,new Item.Properties().group(ItemGroup.COMBAT)),	"iron_knife");
	public static final Item GOLD_KNIFE		= reg( new KnifeItem(ItemTier.GOLD,new Item.Properties().group(ItemGroup.COMBAT)),	"gold_knife");
	public static final Item DIAMOND_KNIFE	= reg( new KnifeItem(ItemTier.DIAMOND,new Item.Properties().group(ItemGroup.COMBAT)),	"diamond_knife");
	public static final Item MITHRIL_KNIFE	= reg( new KnifeItem(ModToolTiers.MITHRIL,new Item.Properties().group(ItemGroup.COMBAT)),"mithril_knife" );
	
	public static final Item DEER_SKULL 		= reg(new WallOrFloorItem(ModBlocks.DEER_SKULL, ModBlocks.DEER_SKULL_WALL, (new Item.Properties()).group(ItemGroup.DECORATIONS)),"deer_skull");
	public static final Item HORNED_DEER_SKULL  = reg(new WallOrFloorItem(ModBlocks.HORN_DEER_SKULL, ModBlocks.HORN_DEER_SKULL_WALL, (new Item.Properties()).group(ItemGroup.DECORATIONS)),"horned_deer_skull");
	   
	public static Item reg(Item item,String name) {
		ITEMS.register(name,() -> item);
		return item;
	}
	
	public static void registerItems() {
		ITEMS.register("mithril_ingot",	 	() -> MITHRIL_INGOT);
		ITEMS.register("mithril_nugget", 	() -> MITHRIL_NUGGET);
		ITEMS.register("mithril_sword",  	() -> MITHRIL_SWORD);
		ITEMS.register("mithril_axe", 	 	() -> MITHRIL_AXE);
		ITEMS.register("mithril_pickaxe",	() -> MITHRIL_PICK);
		ITEMS.register("mithril_shovel", 	() -> MITHRIL_SHOVEL);
		ITEMS.register("mithril_hoe", 	 	() -> MITHRIL_HOE);
		ITEMS.register("white_clay_ball",	() -> CLAY_BALL_WHITE);
		ITEMS.register("orange_clay_ball",	() -> CLAY_BALL_ORANGE);
		ITEMS.register("magenta_clay_ball",	() -> CLAY_BALL_MAGENTA);
		ITEMS.register("light_blue_clay_ball",()->CLAY_BALL_LIGHT_BLUE);
		ITEMS.register("yellow_clay_ball",	() -> CLAY_BALL_YELLOW);
		ITEMS.register("lime_clay_ball",	() -> CLAY_BALL_LIME);
		ITEMS.register("pink_clay_ball",	() -> CLAY_BALL_PINK);
		ITEMS.register("grey_clay_ball",	() -> CLAY_BALL_GREY);
		ITEMS.register("silver_clay_ball",	() -> CLAY_BALL_SILVER);
		ITEMS.register("cyan_clay_ball",	() -> CLAY_BALL_CYAN);
		ITEMS.register("purple_clay_ball",	() -> CLAY_BALL_PURPLE);
		ITEMS.register("blue_clay_ball",	() -> CLAY_BALL_BLUE);
		ITEMS.register("brown_clay_ball",	() -> CLAY_BALL_BROWN);
		ITEMS.register("green_clay_ball",	() -> CLAY_BALL_GREEN);
		ITEMS.register("red_clay_ball",		() -> CLAY_BALL_RED);
		ITEMS.register("black_clay_ball",	() -> CLAY_BALL_BLACK);
		ITEMS.register("white_brick",		() -> BRICK_WHITE);
		ITEMS.register("orange_brick",		() -> BRICK_ORANGE);
		ITEMS.register("magenta_brick",		() -> BRICK_MAGENTA);
		ITEMS.register("light_blue_brick",	() -> BRICK_LIGHT_BLUE);
		ITEMS.register("yellow_brick",		() -> BRICK_YELLOW);
		ITEMS.register("lime_brick",		() -> BRICK_LIME);
		ITEMS.register("pink_brick",		() -> BRICK_PINK);
		ITEMS.register("grey_brick",		() -> BRICK_GREY);
		ITEMS.register("silver_brick",		() -> BRICK_SILVER);
		ITEMS.register("cyan_brick",		() -> BRICK_CYAN);
		ITEMS.register("purple_brick",		() -> BRICK_PURPLE);
		ITEMS.register("blue_brick",		() -> BRICK_BLUE);
		ITEMS.register("brown_brick",		() -> BRICK_BROWN);
		ITEMS.register("green_brick",		() -> BRICK_GREEN);
		ITEMS.register("red_brick",			() -> BRICK_RED);
		ITEMS.register("black_brick",		() -> BRICK_BLACK);
		ITEMS.register("venison",			() -> VENISON);
		ITEMS.register("cooked_venison",	() -> COOKED_VENISON);
		ITEMS.register("silver_apple",		() -> SILVER_APPLE);
		ITEMS.register("enchanted_silver_apple",()->ENCHANTED_SILVER_APPLE);
	}
}
