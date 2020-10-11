package lotb.client.renderer.entity.render;

/*import com.mojang.blaze3d.matrix.MatrixStack;

import lotb.LotbMod;
import lotb.entities.npc.HobbitNpcEntity;
import lotb.entities.profession.BlacksmithProfession;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.ResourceLocation;

public class HobbitRenderer  extends BipedRenderer<HobbitNpcEntity, BipedModel<HobbitNpcEntity>> {
	protected static final ResourceLocation BLACKSMITH_TEXTURE = new ResourceLocation(LotbMod.MODID,"textures/entity/mouse.png");
	protected static final ResourceLocation OTHER_TEXTURE = new ResourceLocation(LotbMod.MODID,"textures/entity/hobbit.png");

	public HobbitRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new BipedModel<HobbitNpcEntity>(0.0f), 1.0f);
		this.addLayer(new BipedArmorLayer<>(this, new BipedModel<HobbitNpcEntity>(0.5f), new BipedModel<HobbitNpcEntity>(1.0f)));
	}
	@Override
	protected void preRenderCallback(HobbitNpcEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
		matrixStackIn.scale(0.8f, 0.8f, 0.8f);
	}

	@Override
	public ResourceLocation getEntityTexture(HobbitNpcEntity entity) {
		if (entity.getProfession() instanceof BlacksmithProfession)
			return BLACKSMITH_TEXTURE;
		return OTHER_TEXTURE;
	}

}*/
