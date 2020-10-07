package lotb.client.renderer.entity.render;

import lotb.LotbMod;
import lotb.entities.Deer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.util.ResourceLocation;

public class DeerRenderer extends MobRenderer<Deer, CowModel<Deer>> {
	protected static final ResourceLocation TEXTURE = new ResourceLocation(LotbMod.MODID,"textures/entity/horse/horse_chestnut.png");

	public DeerRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new CowModel<Deer>(), 0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(Deer entity) {
		return TEXTURE;
	}

}
