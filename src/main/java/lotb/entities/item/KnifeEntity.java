package lotb.entities.item;

import lotb.items.KnifeItem;
import lotb.registries.ModEntities;
import lotb.registries.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class KnifeEntity extends ProjectileItemEntity {

    public static final float DAMAGE = 3.0F; //Change the damage here

    private KnifeItem.KnifeTier tier = KnifeItem.KnifeTier.DIAMOND; //Default diamond

    private ItemStack knifeStack;

    public KnifeEntity(EntityType<? extends ProjectileItemEntity> p_i50173_1_, World p_i50173_2_) {
        super(p_i50173_1_, p_i50173_2_);
        this.knifeStack = ItemStack.EMPTY;
    }

    public KnifeEntity(World world, double x, double y, double z, KnifeItem.KnifeTier tier, ItemStack knifeStack) {
        super(ModEntities.KNIFE.get(), x, y, z, world);
        this.tier = tier;
        this.knifeStack = knifeStack;
    }

    @Override
    protected Item getDefaultItem() {
        switch (tier) {
            case WOOD:
                return ModItems.WOODEN_KNIFE.get();
            case STONE:
                return ModItems.STONE_KNIFE.get();
            case GOLD:
                return ModItems.GOLD_KNIFE.get();
            case IRON:
                return ModItems.IRON_KNIFE.get();
            case DIAMOND:
                return ModItems.DIAMOND_KNIFE.get();
            case MITHRIL:
                return ModItems.MITHRIL_KNIFE.get();
            default:
                throw new IllegalStateException("Knife tier not found");
        }
    }


    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.getType() == RayTraceResult.Type.ENTITY) {
            EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult) result;
            Entity entity = entityRayTraceResult.getEntity();
            if (entity instanceof LivingEntity) {
                entity.attackEntityFrom(DamageSource.GENERIC, DAMAGE);
            }
            if(knifeStack.isEmpty())
                return;
            ItemEntity itemEntity = new ItemEntity(entity.world, result.getHitVec().x, result.getHitVec().y, result.getHitVec().z, knifeStack);
            entity.world.addEntity(itemEntity);
        }
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }


}
