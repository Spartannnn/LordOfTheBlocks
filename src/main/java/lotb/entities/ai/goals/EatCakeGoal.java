package lotb.entities.ai.goals;

import java.util.EnumSet;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lotb.registries.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CakeBlock;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EatCakeGoal extends Goal {
	protected static final Logger LOGGER = LogManager.getLogger();
	private final MobEntity eaterEntity;
	private final World entityWorld;
	private int eatingtimer;
	private int hunger;
	private boolean contExcec;
	private BlockPos cakePos;
	
	//constructer and cake utilitie
	public EatCakeGoal(MobEntity grassEaterEntityIn) {
		eaterEntity = grassEaterEntityIn;
		entityWorld = grassEaterEntityIn.world;
		setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
	}
	
	public boolean isCake(Block block) {
		return(block == Blocks.CAKE || block == ModBlocks.BEEF_PIE.get() || block == ModBlocks.PORK_PIE.get() || block == ModBlocks.CHICKEN_PIE.get() || block == ModBlocks.MUTTON_PIE.get() || block == ModBlocks.RABBIT_PIE.get() || block == ModBlocks.VENISON_PIE.get() || block == ModBlocks.SALMON_PIE.get() || block == ModBlocks.FISH_PIE.get());
	}
	
	public boolean findCake() {
		BlockPos entitypos = new BlockPos(eaterEntity);
		/*for (BlockPos pos :BlockPos.getAllInBoxMutable(entitypos.add(-40, -40, -40), entitypos.add(40, 40, 40))){
            if( isCake(entityWorld.getBlockState(pos).getBlock()) ){
                cakePos = new BlockPos(pos.getX(),pos.getY(),pos.getZ()).toImmutable();break;}}*/
		BlockPos.getAllInBox(entitypos.add(-10, -10, -10), entitypos.add(10, 10, 10)).forEach(pos ->
        {
            if(isCake(entityWorld.getBlockState(pos).getBlock()))
            {
                cakePos=pos.toImmutable();
            }
        });
		LOGGER.debug("cake found"+cakePos);
		return cakePos == null;
	}
	/**-----------------------EXCECUTION FUNCTIONS-----------------------*/
	@Override
	public boolean shouldExecute() {
		hunger = Math.min(0, hunger--);
		if (hunger == 0) {
			return findCake();
		}
		return false;
	}
	
	@Override
	public void startExecuting() {
		eatingtimer = 40;
		contExcec = true;
		entityWorld.setEntityState(eaterEntity, (byte)10);
		eaterEntity.getNavigator().clearPath();
	}
	
	@Override 
	public void tick() {
		LOGGER.debug("cake? "+cakePos+(cakePos==null));
		if (cakePos != null) {
			LOGGER.debug("cake found?");
			BlockState potentialCake = entityWorld.getBlockState(cakePos);
			if(!cakePos.withinDistance(eaterEntity.getPositionVec(), 1)){
				eaterEntity.getNavigator().tryMoveToXYZ(cakePos.getX() + 0.5, cakePos.getY(), cakePos.getZ() + 0.5, 1);
			}else if( isCake(potentialCake.getBlock())) {
				eatingtimer = Math.max(0, eatingtimer - 1);
				if (eatingtimer == 4) {
					if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(entityWorld, eaterEntity)) {
						int cakeSlices = potentialCake.get(CakeBlock.BITES);
						if (cakeSlices <6)
							entityWorld.setBlockState(cakePos, potentialCake.with(CakeBlock.BITES, Integer.valueOf(cakeSlices + 1)), 3);
						else
							entityWorld.removeBlock(cakePos, false);
					}
					eaterEntity.playSound(SoundEvents.ENTITY_GENERIC_EAT, 1, 1);
					entityWorld.addParticle(ParticleTypes.BUBBLE, (double)cakePos.getX(), (double)cakePos.getY(), (double)cakePos.getZ(), 0.0D, 0.0D, 0.0D);
					hunger += 20;
				}
			}
		}else {
			LOGGER.debug("cake gone?");
			contExcec = false;
		}
	}
	
	
	
	
	/**-----------------------TIMER STUFF-----------------------*/
	@Override
	public boolean shouldContinueExecuting() {
		return eatingtimer > 0 && contExcec;
	}
	public int getEatingGrassTimer() {
		return eatingtimer;
	}
	@Override
	public void resetTask() {
		eatingtimer = 0;
	}
}
