package lotb.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;

import lotb.LotbMod;
import lotb.entities.npc.Dwarf;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.ResourceLocation;

public class DwarfRenderer extends BipedRenderer<Dwarf, BipedModel<Dwarf>> {
	protected static final ResourceLocation TEXTURE = new ResourceLocation(LotbMod.MODID,"textures/entity/dwarf.png");
	
	public DwarfRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new BipedModel<Dwarf>(0.0f), 1.0f);
		this.addLayer(new BipedArmorLayer<>(this, new BipedModel<Dwarf>(0.5f), new BipedModel<Dwarf>(1.0f)));
	}
	@Override
	protected void preRenderCallback(Dwarf entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
		matrixStackIn.scale(0.9f, 0.9f, 0.9f);
	}
	@Override
	public ResourceLocation getEntityTexture(Dwarf entity) {
		return TEXTURE;
	}
}
