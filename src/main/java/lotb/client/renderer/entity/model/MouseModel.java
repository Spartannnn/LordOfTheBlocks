package lotb.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

// Made with Blockbench 3.5.3
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


public class MouseModel<T extends Entity> extends EntityModel<T> {
	private final ModelRenderer body;
	private final ModelRenderer head;
	private final ModelRenderer tail;

	public MouseModel() {
		textureWidth = 32;
		textureHeight = 32;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 24.0F, 0.0F);
		body.setTextureOffset(0, 0).addBox(-7.0F, -4.0F, 0.0F, 7.0F, 4.0F, 4.0F, 0.0F, false);
		body.setTextureOffset(8, 14).addBox(-8.0F, -0.75F, -0.25F, 3.0F, 1.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(4, 16).addBox(-3.0F, -0.75F, -0.25F, 3.0F, 1.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(0, 14).addBox(-8.0F, -0.75F, 3.25F, 3.0F, 1.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(6, 18).addBox(-3.0F, -0.75F, 3.25F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 24.0F, 0.0F);
		head.setTextureOffset(0, 8).addBox(-10.0F, -3.5F, 0.5F, 3.0F, 3.0F, 3.0F, 0.0F, false);
		head.setTextureOffset(0, 16).addBox(-8.0F, -5.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
		head.setTextureOffset(14, 14).addBox(-8.0F, -5.0F, 3.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
		head.setTextureOffset(0, 0).addBox(-11.0F, -2.5F, 1.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, 24.0F, 0.0F);
		tail.setTextureOffset(12, 12).addBox(0.0F, -2.5F, 1.5F, 4.0F, 1.0F, 1.0F, 0.0F, false);
		tail.setTextureOffset(9, 9).addBox(4.0F, -2.5F, 1.5F, 4.0F, 1.0F, 1.0F, 0.0F, false);
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
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}