package lotb.items;

import com.google.common.collect.Multimap;
import lotb.LotbMod;
import lotb.entities.item.KnifeEntity;
import lotb.registries.materials.ModToolTiers;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.TieredItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class KnifeItem extends TieredItem {
    private final float attackDamage;

    public KnifeItem(IItemTier _tier, Properties properties) {
        super(_tier, properties);
        attackDamage=_tier.getAttackDamage()+3f;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return this.getTier().getMaxUses();
    }
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity attacker, Hand handIn) {
        ItemStack knifeitem = attacker.getHeldItem(handIn);
        knifeitem.damageItem(1,attacker, (entity) -> {  entity.sendBreakAnimation(EquipmentSlotType.MAINHAND); });
        KnifeEntity knife = new KnifeEntity(worldIn, attacker.getPosX(), attacker.getPosYEye(), attacker.getPosZ(), knifeitem.copy());
        knife.shoot(attacker, attacker.rotationPitch, attacker.rotationYaw, 0.0F, 1.5F, 1.0F);
        worldIn.addEntity(knife);
        knifeitem.shrink(1);
        return ActionResult.resultSuccess(knifeitem);
    }
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot);
        if (equipmentSlot == EquipmentSlotType.MAINHAND)
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage/2, AttributeModifier.Operation.ADDITION));
        return multimap;
    }
}
