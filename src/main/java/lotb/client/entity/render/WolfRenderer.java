package lotb.client.entity.render;

import lotb.LotbMod;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.SquidModel;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.ResourceLocation;

public class WolfRenderer extends MobRenderer<WolfEntity, SquidModel<WolfEntity>> {
	protected static final ResourceLocation TEXTURE = new ResourceLocation(LotbMod.MODID,"textures/entity/badger.png");
 
 	public WolfRenderer(EntityRendererManager renderManager) {
	 	super(renderManager,new SquidModel<WolfEntity>(),0.5f);
 	}
 	@Override
	public ResourceLocation getEntityTexture(WolfEntity entity) {
 		return TEXTURE;
 	}
}
