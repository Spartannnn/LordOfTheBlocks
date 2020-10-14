package lotb.client.renderer.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import lotb.LotbMod;
import lotb.entities.npc.DwarfNPCEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.ResourceLocation;

public class DwarfRenderer extends BipedRenderer<DwarfNPCEntity, BipedModel<DwarfNPCEntity>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(LotbMod.MODID, "textures/entity/dwarf.png");

    public DwarfRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, newModel(0.0f), 1.0f);
        this.addLayer(new BipedArmorLayer<>(this, newModel(0.5f), newModel(1f)));
    }
    private static BipedModel<DwarfNPCEntity> newModel(float size){
        return new BipedModel<DwarfNPCEntity>(RenderType::getEntityCutoutNoCull, size, 0.0F, 64, 64);
    }

    @Override
    protected void preRenderCallback(DwarfNPCEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        matrixStackIn.scale(0.9f, 0.9f, 0.9f);
    }

    @Override
    public ResourceLocation getEntityTexture(DwarfNPCEntity entity) {
        return TEXTURE;
    }
}
