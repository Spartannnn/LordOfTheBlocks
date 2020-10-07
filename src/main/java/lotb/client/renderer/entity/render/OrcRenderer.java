package lotb.client.renderer.entity.render;


import lotb.LotbMod;
import lotb.entities.npc.Orc;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.ResourceLocation;

public class OrcRenderer extends BipedRenderer<Orc, BipedModel<Orc>>{
	protected static final ResourceLocation TEXTURE = new ResourceLocation(LotbMod.MODID,"textures/entity/decorated_uruk_hai.png");

	public OrcRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new BipedModel<Orc>(0.0f), 1.0f);
		this.addLayer(new BipedArmorLayer<>(this, new BipedModel<Orc>(0.5f), new BipedModel<Orc>(1.0f)));
	}

	@Override
	public ResourceLocation getEntityTexture(Orc entity) {
		return TEXTURE;
	}
}
