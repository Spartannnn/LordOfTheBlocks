package lotb.client.entity.render;

import lotb.LotbMod;
import lotb.client.entity.model.BadgerModel;
import lotb.entities.Squirrel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class SquirrelRenderer extends MobRenderer<Squirrel, BadgerModel<Squirrel>> {
	protected static final ResourceLocation TEXTURE = new ResourceLocation(LotbMod.MODID,"textures/entity/mouse.png");

	public SquirrelRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn,new BadgerModel<Squirrel>(),0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(Squirrel entity) {
		return TEXTURE;
	}


}
