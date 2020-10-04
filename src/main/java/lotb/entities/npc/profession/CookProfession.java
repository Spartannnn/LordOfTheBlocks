package lotb.entities.npc.profession;

import java.util.Random;

import lotb.entities.npc.AbstractNpcEntity;
import net.minecraft.entity.ai.brain.Brain;

public class CookProfession extends AbstractMerchant {

	public CookProfession(AbstractNpcEntity _entity , Random _rand) {
		super(_entity,_rand);
	}

	@Override
	public void populateTradeData() {
	}
	@Override
	public Brain<AbstractNpcEntity> registerActivitiesOntoBrain (Brain<AbstractNpcEntity> brain){
		return brain;
	}

}
