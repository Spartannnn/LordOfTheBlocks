package lotb.client.renderer;

import lotb.entities.item.ThrowableToolEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.entity.projectile.ProjectileItemEntity;

public class ProjectileItemRenderer extends SpriteRenderer<ThrowableToolEntity> {

    public ProjectileItemRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, Minecraft.getInstance().getItemRenderer());
    }
}
