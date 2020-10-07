package lotb.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class HedgehogModel<T extends Entity> extends EntityModel<T> {
	private final ModelRenderer body;
	private final ModelRenderer head;

	public HedgehogModel() {
		textureWidth = 32;
		textureHeight = 32;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 24.0F, 0.0F);
		body.setTextureOffset(0, 0).addBox(-7.0F, -4.0F, 0.0F, 7.0F, 4.0F, 5.0F, 0.0F, false);
		body.setTextureOffset(13, 13).addBox(-6.75F, -1.5F, -0.25F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(10, 11).addBox(-1.25F, -1.5F, -0.25F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(7, 9).addBox(-6.75F, -1.5F, 4.25F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(0, 0).addBox(-1.25F, -1.5F, 4.25F, 1.0F, 2.0F, 1.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 24.0F, 0.0F);
		head.setTextureOffset(0, 9).addBox(-9.0F, -3.5F, 1.0F, 2.0F, 3.0F, 3.0F, 0.0F, false);
		head.setTextureOffset(0, 3).addBox(-10.0F, -1.5F, 2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		head.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}