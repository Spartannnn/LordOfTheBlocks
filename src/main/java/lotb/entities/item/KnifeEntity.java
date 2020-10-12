package lotb.entities.item;

import com.google.common.collect.Multimap;
import lotb.items.KnifeItem;
import lotb.registries.ModEntities;
import lotb.registries.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class KnifeEntity extends ThrowableToolEntity {
    public KnifeEntity(EntityType<? extends ThrowableToolEntity> _type, World _world) {
        super(_type, _world);
    }

    public KnifeEntity(World _world, double x, double y, double z, ItemStack item) {
        super(ModEntities.KNIFE.get(), x, y, z, _world);
        if (item.getItem() instanceof KnifeItem)
            this.setItem(item);
        else
            LOGGER.error("tried to use an invalid item");
    }

    public void onCollideWithPlayer(PlayerEntity entityIn) {
        if (!this.world.isRemote && (this.inGround) ){
            if( entityIn.inventory.addItemStackToInventory(this.getItemStack())) {
                entityIn.onItemPickup(this, 1);
                this.remove();
            }
        }
    }

    @Override protected float getAttackDamage() {
        return ((KnifeItem)this.getItemStack().getItem()).getAttackDamage();
    }
    @Override protected Item getDefaultItem() {
        return ModItems.IRON_KNIFE.get();
    }
    @Override public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
