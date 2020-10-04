package lotb.items;

import com.google.common.collect.Multimap;

import lotb.entities.item.KnifeEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BowItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TieredItem;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class KnifeItem extends TieredItem {

	public KnifeItem(IItemTier _tier,Properties properties) {
		super(_tier, properties);
	}
	
	//shooty shooty
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		boolean flag = !playerIn.findAmmo(itemstack).isEmpty();

		ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, flag);
		if (ret != null) return ret;

		if (!playerIn.abilities.isCreativeMode && !flag)
			return ActionResult.resultFail(itemstack);
		
		playerIn.setActiveHand(handIn);
		return ActionResult.resultConsume(itemstack);
	}
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof PlayerEntity) {
	        PlayerEntity playerentity = (PlayerEntity)entityLiving;

	        int i = getUseDuration(stack) - timeLeft;
	        i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, playerentity, i, true);
	        if (i < 0) return;

	        float f = BowItem.getArrowVelocity(i);
	        if (!(f < 0.1f)) {
	        	if (!worldIn.isRemote) {
	        		stack.damageItem(1, playerentity, (p_220009_1_) -> {
	        			p_220009_1_.sendBreakAnimation(playerentity.getActiveHand());
	        		});
	        		KnifeEntity thrownKnife = new KnifeEntity(entityLiving,worldIn,stack.getItem());
	        		thrownKnife.shoot(playerentity, playerentity.rotationPitch, playerentity.rotationYaw, 0.0F, f * 3.0F, 1.0F);
	        		if (f == 1.0F) 
	        			thrownKnife.setIsCritical(true);
	        		/*int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
	        		if (j > 0) {
	        			thrownKnife.setDamage(thrownKnife.getDamage() + (double)j * 0.5D + 0.5D);
	        		}
	        		int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
	        		if (k > 0) {
	        			thrownKnife.setKnockbackStrength(k);
	        		}if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
	        			thrownKnife.setFire(100);
	        		}*/
	        		worldIn.addEntity(thrownKnife);
	        	
	        	}
	        	worldIn.playSound((PlayerEntity)null, playerentity.getPosX(), playerentity.getPosY(), playerentity.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
	        	playerentity.addStat(Stats.ITEM_USED.get(this));
			}
		}
	}
	//stats
	@Override
	public int getUseDuration(ItemStack stack) {
		return 72000;
	}
	@Override @SuppressWarnings("deprecation")
	public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot) {
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot);
		if (slot == EquipmentSlotType.MAINHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
					new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "ranged_item_damage", (double) 4, AttributeModifier.Operation.ADDITION));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
					new AttributeModifier(ATTACK_SPEED_MODIFIER, "ranged_item_attack_speed", -2.4, AttributeModifier.Operation.ADDITION));
		}
		return multimap;
	}
}
