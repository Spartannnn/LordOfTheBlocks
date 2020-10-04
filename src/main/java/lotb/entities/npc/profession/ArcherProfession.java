package lotb.entities.npc.profession;

import lotb.entities.npc.AbstractNpcEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;

public class ArcherProfession extends AbstractSoldier {
	public ArcherProfession(AbstractNpcEntity _entity) {
		super(_entity);
	}

	@Override public boolean onRightClicked(PlayerEntity player, Hand hand) {
		player.sendMessage(new StringTextComponent("Hello!"));
		return true;
	}

	@Override public boolean onAttacked() {
		return false;
	}

	@Override public void tick() {}
}
