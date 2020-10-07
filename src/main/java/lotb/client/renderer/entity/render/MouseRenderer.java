package lotb.client.renderer.entity.render;

import lotb.LotbMod;
import lotb.client.renderer.entity.model.MouseModel;
import lotb.entities.Mouse;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class MouseRenderer extends MobRenderer<Mouse, MouseModel<Mouse>> {
	protected static final ResourceLocation TEXTURE = new ResourceLocation(LotbMod.MODID,"textures/entity/mouse.png");

	public MouseRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn,new MouseModel<Mouse>(),0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(Mouse entity) {
		return TEXTURE;
	}

}
