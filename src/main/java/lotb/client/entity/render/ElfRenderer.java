package lotb.client.entity.render;


import lotb.LotbMod;
import lotb.entities.npc.Elf;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.ResourceLocation;

public class ElfRenderer extends BipedRenderer<Elf, BipedModel<Elf>> {
	protected static final ResourceLocation TEXTURE = new ResourceLocation(LotbMod.MODID,"textures/entity/elf.png");
	
	public ElfRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new BipedModel<Elf>(0.0f), 1.0f);
		this.addLayer(new BipedArmorLayer<>(this, new BipedModel<Elf>(0.5f), new BipedModel<Elf>(1.0f)));
		//this.addLayer(new ArrowLayer<>(this));
		//this.addLayer(new BeeStingerLayer<>(this));
	}

	@Override
	public ResourceLocation getEntityTexture(Elf entity) {
		return TEXTURE;
	}
}
