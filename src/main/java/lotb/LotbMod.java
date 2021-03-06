package lotb;

import lotb.client.RenderSetupManager;
import lotb.entities.ai.VanillaAddGoals;
import lotb.entities.ai.ModActivities;
import lotb.entities.ai.ModMemories;
import lotb.entities.ai.ModSchedules;
import lotb.entities.ai.ModSensors;
import lotb.registries.*;
import lotb.util.FoilageColorizer;
import lotb.util.data.DataManager;
import lotb.world.capabilities.CapabilityRegistering;
import lotb.world.middleearth.MiddleEarth;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(LotbMod.MODID)
@Mod.EventBusSubscriber(modid = LotbMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LotbMod {
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

        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModEntities.ENTITIES.register(modEventBus);
        ModContainers.CONTAINERS.register(modEventBus);
        ModStructures.STRUCTURES.register(modEventBus);
        ModMemories.MEMORIES.register(modEventBus);
        ModActivities.ACTIVITIES.register(modEventBus);
        ModBiomes.BIOMES.register(modEventBus);


        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(VanillaAddGoals.class);
        MinecraftForge.EVENT_BUS.register(DataManager.class);
    }

    public static ResourceLocation newLocation(String name) {
        return new ResourceLocation(MODID, name);
    }
//========================================================================================================================


    private void setup(final FMLCommonSetupEvent event) {
        CapabilityRegistering.register();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        FoilageColorizer.register();
        RenderSetupManager.initMobRenderers();
        RenderSetupManager.initBlockTransparancy();
        RenderSetupManager.initGuis();
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
    }

    private void processIMC(final InterModProcessEvent event) {
    }

    public static final ItemGroup LOTB_GROUP = new ItemGroup(ItemGroup.GROUPS.length, "lotb") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.GOLD_INGOT);
        }
    };

    @SubscribeEvent
    public static void onBlockItemRegistry(RegistryEvent.Register<Item> items) {
        IForgeRegistry<Item> registry = items.getRegistry();

        ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
            Item.Properties properties = new Item.Properties().group(LOTB_GROUP);
            BlockItem item = new BlockItem(block, properties);
            item.setRegistryName(Objects.requireNonNull(block.getRegistryName()));
            registry.register(item);
        });
    }

    @SubscribeEvent
    public static void onBiomeRegistry(RegistryEvent.Register<Biome> event) {
        ModBiomes.registerBiomes();
    }

    @SubscribeEvent
    public static void loadCompleteEvent(FMLLoadCompleteEvent event) {
    }
    //ROHAN		- green, yellow per fess, red inverted chevron, black indented base, white thing, white roundel
    //MORDOR	- black, yellow roundel, red flower charge
    //GONDOR	- black, white saltire, black base, blue base indented, white cross, black bordure indented
    //ISENGAURD	- black, white flower charge, black bordure, white lozenge, black chevron
    //MIRKWOOD	- green, brown saltire, green base, yellow flower charge, brown cross, green bordure indented, red bordure 
    //MORIA		- grey, black per fess, white flower charge, black lozenge, light blue roundel, light grey chevron, black base indented
    //SHIRE		- lime, brown saltire, lime base, yellow flower charge, brown cross, lime bordure indented
    //BLUE MT	- grey, light blue per fess, light blue lozenge, red roundel, light grey chevron, light blue base indented
}
