package lotb.entities.item;

import lotb.LotbMod;
import lotb.registries.ModEntities;
import lotb.registries.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
public class KnifeEntity extends AbstractArrowEntity implements IRendersAsItem{
	public final Item representedKnife;
	
	public KnifeEntity(LivingEntity shooter, World worldIn, Item _item) {
		super(ModEntities.KNIFE, shooter, worldIn);
		representedKnife = _item;
	}
	public KnifeEntity(EntityType<KnifeEntity> _type,World worldIn) {
		super(_type,worldIn);
		representedKnife = ModItems.IRON_KNIFE.get();
	}


	@Override
	protected ItemStack getArrowStack() {
		return getItem();
	}
	/*@Override
	protected void arrowHit(LivingEntity entity) {
		super.arrowHit(entity);
		entity.setArrowCountInEntity(entity.getArrowCountInEntity() - 1);
	}*/
	@Override
	@OnlyIn(Dist.CLIENT)
	public ItemStack getItem() {
		LotbMod.LOGGER.error("trying to render an entity");
		return new ItemStack(representedKnife);
	}
}
