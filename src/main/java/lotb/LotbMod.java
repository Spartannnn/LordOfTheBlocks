package lotb;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lotb.lore.CharacterData;
import lotb.client.RenderSetupManager;
import lotb.entities.ai.VanillaAddGoals;
import lotb.entities.ai.brain.ModActivities;
import lotb.entities.ai.brain.ModMemories;
import lotb.entities.ai.brain.ModSchedules;
import lotb.entities.ai.brain.ModSensors;
import lotb.registries.ModBiomes;
import lotb.registries.ModBlocks;
import lotb.registries.ModContainers;
import lotb.registries.ModEntities;
import lotb.registries.ModItems;
import lotb.registries.ModStructures;
import lotb.tools.FoilageColorizer;
import lotb.tools.data.DataManager;
import lotb.world.middleearth.MiddleEarth;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


// The value here should match an entry in the META-INF/mods.toml file
@Mod("lotb")
public class LotbMod
{
    public static final String MODID = "lotb";
    public static final WorldType MiddleEarthWorld = new MiddleEarth();
    
    
    //@SuppressWarnings("unused")
	public static final Logger LOGGER = LogManager.getLogger(MODID);

    public LotbMod() {
        // Register the setup method for modloading
    	final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    	modEventBus.addListener(this::setup);
    	modEventBus.addListener(this::enqueueIMC);
    	modEventBus.addListener(this::processIMC);
    	modEventBus.addListener(this::doClientStuff);
    	
    	ModSchedules.init();
    	ModSensors.init();
    	
    	ModBiomes.RegisterBiomes();
    	ModItems.ITEMS.register(modEventBus);
    	ModBlocks.BLOCKS.register(modEventBus);
    	ModEntities.ENTITIES.register(modEventBus);
    	ModContainers.CONTAINERS.register(modEventBus);
    	ModStructures.STRUCTURES.register(modEventBus);
    	ModBiomes.BIOMES.register(modEventBus);
    	ModMemories.MEMORIES.register(modEventBus);
    	ModActivities.ACTIVITIES.register(modEventBus);
    	

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(VanillaAddGoals.class);
        MinecraftForge.EVENT_BUS.register(DataManager.class);
        MinecraftForge.EVENT_BUS.register(CharacterData.class);
    }
    public static ResourceLocation newLocation(String name) {
    	return new ResourceLocation(MODID,name);
    }
//========================================================================================================================


    private void setup(final FMLCommonSetupEvent event){}

    private void doClientStuff(final FMLClientSetupEvent event) {
    	FoilageColorizer.register();
    	RenderSetupManager.initMobRenderers();
    	RenderSetupManager.initBlockTransparancy();
    	RenderSetupManager.initGuis();
    }

    private void enqueueIMC(final InterModEnqueueEvent event){}
    private void processIMC(final InterModProcessEvent event) {}

    public static final ItemGroup LOTB_GROUP = new ItemGroup(ItemGroup.GROUPS.length, "lotb") {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(Items.GOLD_INGOT);
		}
	};
    
    @SubscribeEvent
    public static void loadCompleteEvent(FMLLoadCompleteEvent event) {}
    //ROHAN		- green, yellow per fess, red inverted chevron, black indented base, white thing, white roundel
    //MORDOR	- black, yellow roundel, red flower charge
    //GONDOR	- black, white saltire, black base, blue base indented, white cross, black bordure indented
    //ISENGAURD	- black, white flower charge, black bordure, white lozenge, black chevron
    //MIRKWOOD	- green, brown saltire, green base, yellow flower charge, brown cross, green bordure indented, red bordure 
    //MORIA		- grey, black per fess, white flower charge, black lozenge, light blue roundel, light grey chevron, black base indented
    //SHIRE		- lime, brown saltire, lime base, yellow flower charge, brown cross, lime bordure indented
    //BLUE MT	- grey, light blue per fess, light blue lozenge, red roundel, light grey chevron, light blue base indented
}
