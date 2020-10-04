package lotb.entities.npc.profession;

import lotb.entities.npc.AbstractNpcEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

public class AristocratProfession extends AbstractProfession {

	public AristocratProfession(AbstractNpcEntity _entity) {
		super(_entity);
	}

	@Override
	public boolean onRightClicked(PlayerEntity player, Hand hand) {
		return false;
	}

	@Override
	public boolean onAttacked() {
		return false;
	}
	@Override public void tick() {
	}
	@Override
	public Brain<AbstractNpcEntity> registerActivitiesOntoBrain (Brain<AbstractNpcEntity> brain){
		return brain;
	}
}
