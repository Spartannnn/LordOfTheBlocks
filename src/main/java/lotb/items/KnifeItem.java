package lotb.items;

import lotb.LotbMod;
import lotb.entities.item.KnifeEntity;
import lotb.registries.materials.ModToolTiers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.TieredItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class KnifeItem extends TieredItem {

    public KnifeItem(IItemTier _tier, Properties properties) {
        super(_tier, properties);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return this.getTier().getMaxUses();
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        KnifeTier tier = null;
        IItemTier itemTier = this.getTier();
        if (itemTier instanceof ItemTier) {
            ItemTier it0 = (ItemTier) itemTier;
            switch (it0) {
                case WOOD:
                    tier = KnifeTier.WOOD;
                    break;
                case STONE:
                    tier = KnifeTier.STONE;
                    break;
                case GOLD:
                    tier = KnifeTier.GOLD;
                    break;
                case IRON:
                    tier = KnifeTier.IRON;
                    break;
                case DIAMOND:
                    tier = KnifeTier.DIAMOND;
                    break;
            }
        } else if (itemTier instanceof ModToolTiers) {
            tier = KnifeTier.MITHRIL;
        }

        if (tier == null) {
            LotbMod.LOGGER.error("Knife tier is null, can not find a knife tier for this item tier: {}", itemTier.toString());
            return ActionResult.resultFail(itemstack);
        }

        KnifeEntity knife = new KnifeEntity(worldIn, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), tier);
        knife.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
        worldIn.addEntity(knife);

        return ActionResult.resultSuccess(itemstack);
    }

    public enum KnifeTier {

        WOOD, STONE, IRON, GOLD, DIAMOND, MITHRIL

    }
}
