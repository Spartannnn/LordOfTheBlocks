package lotb.client.entity.render;

import lotb.LotbMod;
import lotb.client.entity.model.HedgehogModel;
import lotb.entities.HedgeHog;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class HedgeHogRenderer extends MobRenderer<HedgeHog, HedgehogModel<HedgeHog>> {
	protected static final ResourceLocation TEXTURE = new ResourceLocation(LotbMod.MODID,"textures/entity/hedgehog.png");

	public HedgeHogRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn,new HedgehogModel<HedgeHog>(),0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(HedgeHog entity) {
		return TEXTURE;
	}

}
