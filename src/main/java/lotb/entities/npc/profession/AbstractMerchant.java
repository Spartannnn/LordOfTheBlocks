package lotb.entities.npc.profession;

import java.util.Random;

import javax.annotation.Nullable;

import lotb.entities.npc.AbstractNpcEntity;
import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class AbstractMerchant extends AbstractProfession implements IMerchant {
	//may be supered
	@Nullable private PlayerEntity customer;
	@Nullable protected MerchantOffers offers;
	public final AbstractNpcEntity entity;
	protected final Random rand;
	
	public AbstractMerchant(AbstractNpcEntity _entity , Random _rand) {
		super(_entity);
		entity=_entity;
		rand=_rand;
	}
	
	//overrides
	
	@Override
	public boolean onRightClicked(PlayerEntity player, Hand hand) {
		this.setCustomer(player);
		this.openMerchantContainer(player, entity.getDisplayName(), 1);
		return true;
	}
	@Override public boolean onAttacked() {
		return false;
	}
	@Override public void tick() {
	}
	
	//overidable
	public abstract void populateTradeData();


	//trading
	/** ============== MAKE SURE THIS DOES A THING ============== */
	@Override
	public void onTrade(MerchantOffer offer) { 
		offer.increaseUses();
	    //onVillagerTrade(offer);
	    if (this.customer instanceof ServerPlayerEntity) {
	         //CriteriaTriggers.VILLAGER_TRADE.func_215114_a((ServerPlayerEntity)this.customer, this, offer.getSellingStack());
	    }
	}

	
	@Override public World getWorld() {
		return world;
	}
	//offer management
	@Override public MerchantOffers getOffers() {
		if (offers == null) {
			offers = new MerchantOffers();
			populateTradeData();
		}return offers; } 
	@OnlyIn(Dist.CLIENT) 
	@Override public void setClientSideOffers(MerchantOffers offers) {}
	
	public void addTrades(MerchantOffers givenMerchantOffers, VillagerTrades.ITrade[] newTrades, int maxNumbers) {
		int numberOfTrades = Math.min(maxNumbers,newTrades.length);
		int[] set = new int[numberOfTrades];
		for(int i = 0; i < numberOfTrades; ++i) {
			set[i] = i;
			MerchantOffer merchantoffer = newTrades[i].getOffer(entity, rand);
			if (merchantoffer != null)
				givenMerchantOffers.add(merchantoffer);
		}
	}
	
	/**----------getters & setters----------*/
	//customer management
	@Override public void setCustomer(@Nullable PlayerEntity player){ customer=player; } 
	@Override public PlayerEntity getCustomer() 					{ return customer; }
	//xp management
	@Override public int getXp() { return 0; }
	@Override public void setXP (int xpIn) 	{}
	//some sound stuff
	@Override public void verifySellingItem(ItemStack stack) { }
	@Override public SoundEvent getYesSound() { return entity.getTradeYesSound(); }
	
	
	//who even fucking knows
	@Override public boolean func_213705_dZ() {
		return true;
	}

}
