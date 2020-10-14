package lotb.client.renderer.entity.render;


/*import lotb.LotbMod;
import lotb.entities.npc.ElfNpcEntity;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.ResourceLocation;

public class ElfRenderer extends BipedRenderer<ElfNpcEntity, BipedModel<ElfNpcEntity>> {
	protected static final ResourceLocation TEXTURE = new ResourceLocation(LotbMod.MODID,"textures/entity/elf.png");
	
	public ElfRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, newModel(0f), 1.0f);
		this.addLayer(new BipedArmorLayer<>(this, newModel(0.5f), newModel(1f)));
	}
    private static BipedModel<ElfNpcEntity> newModel(float size){
        return new BipedModel<ElfNpcEntity>(RenderType::getEntityCutoutNoCull, size, 0.0F, 64, 64);
    }

	@Override
	public ResourceLocation getEntityTexture(ElfNpcEntity entity) {
		return TEXTURE;
	}
}
*/