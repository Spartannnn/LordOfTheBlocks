package lotb.client.renderer.armour;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

public abstract class ModeledArmour extends BipedModel<LivingEntity> {
	protected final ModelRenderer Head;
	protected final ModelRenderer Body;
	protected final ModelRenderer RightArm;
	protected final ModelRenderer LeftArm;
	protected final ModelRenderer RightLeg;
	protected final ModelRenderer LeftLeg;
	
	public EquipmentSlotType slot;
	
	public ModeledArmour() {
		super(1f);
		Head = new ModelRenderer(this);
		Body = new ModelRenderer(this);
		RightArm = new ModelRenderer(this);
		LeftArm = new ModelRenderer(this);
		RightLeg = new ModelRenderer(this);
		LeftLeg = new ModelRenderer(this);
	}
	public void setSlot(EquipmentSlotType _slot) {
		slot=_slot;
	}
	
	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		switch(slot) {
		case HEAD:
			Head.render(matrixStack, buffer, packedLight, packedOverlay);
			break;
		case CHEST:
			Body.render(matrixStack, buffer, packedLight, packedOverlay);
			RightArm.render(matrixStack, buffer, packedLight, packedOverlay);
			LeftArm.render(matrixStack, buffer, packedLight, packedOverlay);
			break;
		default:
			RightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
			LeftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
		}
	}
	/*@Override
	public void setRotationAngles(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
	    	
		Head.copyModelAngles(this.bipedHead);

		Body.copyModelAngles(this.bipedBody);
		LeftArm.copyModelAngles(this.bipedLeftArm);
		RightArm.copyModelAngles(this.bipedRightArm);
	    	
		RightLeg.copyModelAngles(this.bipedLeftLeg);
		LeftLeg.copyModelAngles(this.bipedRightLeg);
	}*/
}
