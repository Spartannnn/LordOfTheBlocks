package lotb.client;

import lotb.client.entity.render.BadgerRenderer;
import lotb.client.entity.render.DeerRenderer;
import lotb.client.entity.render.DwarfRenderer;
import lotb.client.entity.render.ElfRenderer;
import lotb.client.entity.render.HedgeHogRenderer;
import lotb.client.entity.render.HobbitRenderer;
import lotb.client.entity.render.HumanRenderer;
import lotb.client.entity.render.MouseRenderer;
import lotb.client.entity.render.OrcRenderer;
import lotb.client.entity.render.SquirrelRenderer;
import lotb.client.entity.render.WolfRenderer;
import lotb.client.gui.WorkBenchScreen;
import lotb.entities.item.KnifeEntity;
import lotb.registries.ModBlocks;
import lotb.registries.ModContainers;
import lotb.registries.ModEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class RenderSetupManager {

	public static void initMobRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.BADGER, BadgerRenderer::new);
    	RenderingRegistry.registerEntityRenderingHandler(ModEntities.DEER, DeerRenderer::new);
    	RenderingRegistry.registerEntityRenderingHandler(ModEntities.HEDGEHOG, HedgeHogRenderer::new);
    	RenderingRegistry.registerEntityRenderingHandler(ModEntities.MOUSE, MouseRenderer::new);
    	RenderingRegistry.registerEntityRenderingHandler(ModEntities.SQUIRREL, SquirrelRenderer::new);
    	RenderingRegistry.registerEntityRenderingHandler(ModEntities.DWARF, DwarfRenderer::new);
    	RenderingRegistry.registerEntityRenderingHandler(ModEntities.ELF, ElfRenderer::new);
    	RenderingRegistry.registerEntityRenderingHandler(ModEntities.HOBBIT, HobbitRenderer::new);
    	RenderingRegistry.registerEntityRenderingHandler(ModEntities.HUMAN, HumanRenderer::new);
    	RenderingRegistry.registerEntityRenderingHandler(ModEntities.ORC, OrcRenderer::new);
    	
    	//overriding
    	RenderingRegistry.registerEntityRenderingHandler(EntityType.WOLF, WolfRenderer::new);
    	//knife rendering
    	RenderingRegistry.registerEntityRenderingHandler(ModEntities.KNIFE, renderManager -> {
			return new SpriteRenderer<KnifeEntity>(renderManager, Minecraft.getInstance().getItemRenderer());
		});
    	/*IRenderFactory<WolfEntity> wolf_factory = new IRenderFactory<WolfEntity>() {
            @Override public EntityRenderer<? super WolfEntity> createRenderFor(EntityRendererManager manager) {
                return (EntityRenderer<? super WolfEntity>) new WolfRenderer(manager);
        }};*/
	}
	
	public static void initBlockTransparancy() {
        RenderTypeLookup.setRenderLayer(ModBlocks.LEBETHRON_LEAVES.get(),	RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.MITHRIL_BARS.get(),		RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.IRON_BAR_DOOR.get(),		RenderType.getCutoutMipped());
        
        RenderTypeLookup.setRenderLayer(ModBlocks.OAK_SAPLING.get(), 		RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.BIRCH_SAPLING.get(),	RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.SPRUCE_SAPLING.get(), 	RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.JUNGLE_SAPLING.get(), 	RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.ACACIA_SAPLING.get(), 	RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.DARK_OAK_SAPLING.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.LEBETHRON_SAPLING.get(),RenderType.getCutout());
	}

	public static void initGuis() {
    	ScreenManager.registerFactory(ModContainers.ROHAN_WORKBENCH,WorkBenchScreen::new);
    }

}
