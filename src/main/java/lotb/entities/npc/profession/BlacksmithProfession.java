package lotb.entities.npc.profession;

import java.util.Random;

import lotb.entities.npc.AbstractNpcEntity;
import lotb.tools.ModInventoryUtils;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffers;

public class BlacksmithProfession extends AbstractMerchant {

	public BlacksmithProfession(AbstractNpcEntity _entity , Random _rand) {
		super(_entity,_rand);
		ModInventoryUtils.addItem(entity.inventory,new ItemStack(Items.IRON_INGOT, 5));
		ModInventoryUtils.addItem(entity.inventory,new ItemStack(Items.BREAD, 5));
	}

	@Override
	public void populateTradeData() {
		MerchantOffers offers = this.getOffers();
		addTrades(offers,VillagerTrades.field_221240_b.get(1),VillagerTrades.field_221240_b.get(1).length/2);
	}

	@Override
	public Brain<AbstractNpcEntity> registerActivitiesOntoBrain (Brain<AbstractNpcEntity> brain){
		return brain;
	}

}
