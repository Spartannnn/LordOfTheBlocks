package lotb.client.renderer.entity.render;


import lotb.LotbMod;
import lotb.entities.npc.Human;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.ResourceLocation;

public class HumanRenderer extends BipedRenderer<Human, BipedModel<Human>> {
	protected static final ResourceLocation TEXTURE = new ResourceLocation(LotbMod.MODID,"textures/entity/human_gondor.png");
	
	public HumanRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new BipedModel<Human>(0.0f), 1.0f);
		this.addLayer(new BipedArmorLayer<>(this, new BipedModel<Human>(0.5f), new BipedModel<Human>(1.0f)));
	}

	@Override
	public ResourceLocation getEntityTexture(Human entity) {
		return TEXTURE;
	}
}
