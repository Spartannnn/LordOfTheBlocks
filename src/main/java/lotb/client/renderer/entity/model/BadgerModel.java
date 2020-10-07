package lotb.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


public class BadgerModel<T extends Entity> extends EntityModel<T> {
	private final ModelRenderer body;
	private final ModelRenderer head;
	private final ModelRenderer tail;
	private final ModelRenderer legFR;
	private final ModelRenderer legFL;
	private final ModelRenderer legBR;
	private final ModelRenderer legBL;

	public BadgerModel() {
		textureWidth = 64;
		textureHeight = 64;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 24.0F, 0.0F);
		body.setTextureOffset(0, 0).addBox(-4.0F, -7.0F, -5.0F, 8.0F, 5.0F, 14.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 24.0F, 0.0F);
		head.setTextureOffset(0, 19).addBox(-3.0F, -6.0F, -9.0F, 6.0F, 4.0F, 4.0F, 0.0F, false);
		head.setTextureOffset(0, 0).addBox(-2.0F, -4.0F, -11.0F, 4.0F, 2.0F, 2.0F, 0.0F, false);
		head.setTextureOffset(0, 12).addBox(-3.0F, -7.0F, -7.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		head.setTextureOffset(6, 4).addBox(1.0F, -7.0F, -7.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, 20.0F, 8.0F);
		setRotationAngle(tail, 0.4363F, 0.0F, 0.0F);
		tail.setTextureOffset(18, 19).addBox(-1.0F, -0.8689F, 1.1444F, 2.0F, 5.0F, 1.0F, 0.0F, false);

		legFR = new ModelRenderer(this);
		legFR.setRotationPoint(0.0F, 24.0F, 0.0F);
		legFR.setTextureOffset(6, 10).addBox(-4.0F, -2.0F, -5.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

		legFL = new ModelRenderer(this);
		legFL.setRotationPoint(0.0F, 24.0F, 0.0F);
		legFL.setTextureOffset(0, 8).addBox(2.0F, -2.0F, -5.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

		legBR = new ModelRenderer(this);
		legBR.setRotationPoint(0.0F, 24.0F, 0.0F);
		legBR.setTextureOffset(6, 6).addBox(2.0F, -2.0F, 7.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

		legBL = new ModelRenderer(this);
		legBL.setRotationPoint(0.0F, 24.0F, 0.0F);
		legBL.setTextureOffset(0, 4).addBox(-4.0F, -2.0F, 7.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		head.render(matrixStack, buffer, packedLight, packedOverlay);
		tail.render(matrixStack, buffer, packedLight, packedOverlay);
		legFR.render(matrixStack, buffer, packedLight, packedOverlay);
		legFL.render(matrixStack, buffer, packedLight, packedOverlay);
		legBR.render(matrixStack, buffer, packedLight, packedOverlay);
		legBL.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}