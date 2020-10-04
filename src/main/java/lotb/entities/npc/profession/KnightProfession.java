package lotb.entities.npc.profession;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;

import lotb.entities.npc.AbstractNpcEntity;
import lotb.tools.ModInventoryUtils;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;

public class KnightProfession extends AbstractProfession {
	public static final Set<Item> BEDS		= new HashSet<>();
	public static final Set<Item> WEAPONS	= new HashSet<>();
	static {
		Collections.addAll(BEDS, Items.WHITE_BED);
		Collections.addAll(WEAPONS, Items.IRON_SWORD);
	}
	//MY STUFF
	private int currentTick;
	public ProffesionActivity currentState;
	@Nullable BlockPos bedPos;
	public boolean needsBed		= false;
	public boolean needsFood	= false;
	public boolean needsWeapon	= false;

	public KnightProfession(AbstractNpcEntity _entity) {
		super(_entity);
		currentState = ProffesionActivity.FREETIME;
		currentTick=2000;
		bedPos = null;
		entity.sendMessage(new StringTextComponent("Hello!"));
	}

	//overrides
	@Override public boolean onRightClicked(PlayerEntity player, Hand hand) {
		if (!(currentState == ProffesionActivity.SLEEPING))
			player.sendMessage(new StringTextComponent("Hello!"));
		return true;
	}
	@Override public boolean onAttacked() {
		return false;
	}
	@Override public void tick() {
		currentTick -= 1;
		//if (entity.hunger > 6000)
		//	needsFood=true;
		if (currentTick == 0)
			switch (currentState) {
			case SLEEPING:
				startFreeTime();
			case PATROL:
				startFreeTime();
			case FREETIME:
				if (world.isNightTime())
					if (!needsBed)
						startSleeping();
				else
					if (!needsWeapon)
						startPatrolling();
			}
		else
			switch (currentState) {
			case PATROL:
				tickPatrolling();
			case FREETIME:
				tickFreeTime();
			case SLEEPING:
				tickSleeping();
			}
	}
	/*======================================TICK MAIN EVENTS======================================*/
	public void tickPatrolling() {
		if(needsWeapon) {
			lookForWeapon();
		}else {
			
		}
	}
	public void tickFreeTime() {
		if (bedPos == null) {
			if(needsBed)
				lookForBed();
			else
				placeBed();
		}else if(needsFood) {
			lookForFood();
		}else if(needsWeapon) {
			lookForWeapon();
		}else {
			
		}
	}
	public void tickSleeping() {
		if (bedPos != null)
			if (world.getBlockState(bedPos).getBlock() == Blocks.WHITE_BED) {
				if (bedPos != entity.getPosition())
					entity.getNavigator().tryMoveToXYZ(bedPos.getX() + 0.5, bedPos.getY(), bedPos.getZ() + 0.5, 1);
			}else {
				bedPos=null;
				if (ModInventoryUtils.hasAny(entity.inventory,BEDS))
					needsBed=true;
		}else {
			if (needsBed)
				lookForBed();
			else
				placeBed();
		}
	}
	//state activation
	public void startPatrolling() {
		//LOGGER.error("time to work!");
		currentState = ProffesionActivity.PATROL;
		currentTick = 6000;
	}
	public void startSleeping() {
		//LOGGER.error("zzzzz!");
		currentState = ProffesionActivity.SLEEPING;
		currentTick = 6000;
	}
	public void startFreeTime() {
		//LOGGER.error("time off!");
		currentState = ProffesionActivity.FREETIME;
		currentTick = 2000;
	}
	//state deactivation
	
	/*======================================ACTIVITIES======================================*/
	@SuppressWarnings("deprecation")
	public void placeBed() {
		//LOGGER.error("lets place this bed"+bedPos);
		for (int slot=0;slot < entity.inventory.getSlots();slot++) {
			ItemStack stack=entity.inventory.getStackInSlot(slot);
			if (stack.getItem() == Items.WHITE_BED) {
				//LOGGER.error("bed in inventory");
				stack.setCount(stack.getCount()-1);
				BlockPos.getAllInBox(entity.getPosition().add(-10, -10, -10), entity.getPosition().add(10, 10, 10)).forEach(pos ->{
		            if (world.getBlockState(pos).isAir())
		            	if (!world.getBlockState(pos.add(0,-1,0)).isAir())
		            		bedPos=pos;
		        });
				world.setBlockState(bedPos,Blocks.WHITE_BED.getDefaultState(), 1);
			}
		}
	}
	
	public void lookForBed() {
		//LOGGER.error("lets look for a bed");
		ModInventoryUtils.addItem(entity.inventory,new ItemStack(Items.WHITE_BED, 1));
	}
	
	public void lookForFood() {
		//LOGGER.error("hungee");
		//entity.hunger = 0;
	}
	
	public void lookForWeapon() {
		//LOGGER.error("lets look for a weapon");
		ModInventoryUtils.addItem(entity.inventory,new ItemStack(Items.IRON_SWORD, 1));
	}
	
	//states
	public enum ProffesionActivity {
		SLEEPING,
		PATROL,
		FREETIME;
	}

	@Override
	public Brain<AbstractNpcEntity> registerActivitiesOntoBrain(Brain<AbstractNpcEntity> brain) {
		return brain;
	}
}