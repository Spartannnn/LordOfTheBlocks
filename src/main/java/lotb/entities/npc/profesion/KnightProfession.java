package lotb.entities.npc.profesion;

import com.google.common.collect.Sets;
import lotb.LotbMod;
import lotb.entities.npc.AbstractNPCEntity;
import lotb.entities.npc.NPCFoodManager;
import lotb.entities.npc.NPCInventory;
import lotb.util.IndexedQueue;
import net.minecraft.block.Blocks;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;

public class KnightProfession extends AbstractProfession {

    public static final Set<Item> BEDS = Sets.newHashSet();
    public static final Set<Item> WEAPONS = Sets.newHashSet();

    static {
        Collections.addAll(BEDS, Items.WHITE_BED);
        Collections.addAll(WEAPONS, Items.IRON_SWORD);
    }

    private final IndexedQueue<BlockPos> patrollingPoses = new IndexedQueue<>(4);
    private BlockPos prevPatrollingPos;
    private BlockPos currentPatrollingPos;
    @Nullable
    public BlockPos bedPos;
    public boolean needsBed = false;
    public boolean needsFood = false;
    public boolean needsWeapon = false;

    public KnightProfession() {
        super(2000);
        //this.currentActivity = ProfessionWorldTimeManager.Activity.ACTION;
        this.bedPos = null;
        this.prevPatrollingPos = null;
    }

    @Override
    public void registerGoals(GoalSelector goalSelector, GoalSelector targetSelector) {

    }

    @Override
    public boolean onRightClick(AbstractNPCEntity npc, ItemStack stack, PlayerEntity player, World world) {
       /* if (this.patrollingPoses.isEmpty()) {
           /* BlockPos currentPos = npc.getPosition();
            this.patrollingPoses.add(currentPos.add(5, 0, 0));
            this.patrollingPoses.add(currentPos.add(5, 0, 5));
            this.patrollingPoses.add(currentPos.add(0, 0, 5));
            this.patrollingPoses.add(currentPos.add(-5, 0, 5));
            this.currentPatrollingPos = this.patrollingPoses.peek();
            this.currentActivity = Activity.ACTION;
            player.sendMessage(TCHelper.string("Block positions set"));
        }
/*        if (currentState != ProffesionActivity.SLEEPING)
            player.sendMessage(TCHelper.string("DEBUG: Not sleeping"));*/
        if (player.isSneaking()) {
            this.currentActivity = Activity.ACTION;
            LotbMod.LOGGER.debug("set activity to action");
        } else {
            NPCInventory inventory = npc.getNpcInventory();
            LotbMod.LOGGER.debug("Inventory contains bed? {}", inventory.contains(ItemTags.BEDS));
            LotbMod.LOGGER.debug("Inventory bed: {}", inventory.lookFor(ItemTags.BEDS));
        }
        return true;
    }

    @Override
    public void onAttacked(AbstractNPCEntity npc, PlayerEntity player, World world) {

    }

    @Override
    public void _tick(AbstractNPCEntity npc, World world) {
        NPCFoodManager foodManager = npc.getFoodManager();
        this.needsFood = foodManager.needFood();
        if (this.currentActivity == Activity.ACTION) {
            //this.patrol(npc);
            this.placeBed(npc, world);
        }
    }

    private boolean hasBed(AbstractNPCEntity npc) {
        return npc.getNpcInventory().contains(ItemTags.BEDS);
    }

    private void placeBed(AbstractNPCEntity npc, World world) {
        NPCInventory inventory = npc.getNpcInventory();
        ItemStack bedStack = inventory.lookFor(ItemTags.BEDS);
        if (bedStack.isEmpty()) {
            LotbMod.LOGGER.debug("Tried to place a bed, but the inventory has no bed item");
            return;
        }
        bedStack.shrink(1);
        BlockPos.getAllInBox(npc.getPosition().add(-10, -10, -10), npc.getPosition().add(10, 10, 10))
                .forEach(blockPos -> {
                    if (world.isAirBlock(blockPos) && bedPos == null) {
                        if (lookAroundForAir(world, blockPos)) {
                            bedPos = blockPos;
                        }
                    }
                });
        if (bedPos != null) {
            world.setBlockState(bedPos, Blocks.WHITE_BED.getDefaultState(), 1);
        } else {
            LotbMod.LOGGER.debug("Tried to place bed, but no valid position found");
        }

    }

    private boolean lookAroundForAir(World world, BlockPos pos) {
        return world.isAirBlock(pos.add(1, 0, 0)) || world.isAirBlock(pos.add(-1, 0, 0))
                || world.isAirBlock(pos.add(0, 0, 1)) || world.isAirBlock(pos.add(0, 0, -1));
    }

    private void patrol(AbstractNPCEntity npc) {
        if (this.prevPatrollingPos != this.currentPatrollingPos && this.currentPatrollingPos != null) {
            if (npc.getDistanceSq(this.currentPatrollingPos.getX(), this.currentPatrollingPos.getY(), this.currentPatrollingPos.getZ()) < 4) {
                this.prevPatrollingPos = this.currentPatrollingPos;
                this.currentPatrollingPos = this.patrollingPoses.peek();
                LotbMod.LOGGER.debug("change patrol position");
            } else {
                npc.getMoveHelper().setMoveTo(this.currentPatrollingPos.getX(), this.currentPatrollingPos.getY(), this.currentPatrollingPos.getZ(), npc.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue());
            }
        }
    }

    @Override
    public void kill(AbstractNPCEntity npc, World world) {

    }


    /*public void tickPatrolling(AbstractNPCEntity npc) {
        if (needsWeapon) {
            lookForWeapon(npc);
        } else {

        }
    }

    public void tickFreeTime(AbstractNPCEntity npc, World world) {
        if (bedPos == null)
            if (needsBed)
                lookForBed(npc);
            else
                placeBed(npc, world);
        else if (needsFood)
            lookForFood();
        else if (needsWeapon)
            lookForWeapon(npc);
    }

    public void tickSleeping(AbstractNPCEntity npc, World world) {
        if (bedPos != null) {
            if (world.getBlockState(bedPos).getBlock() == Blocks.WHITE_BED) {
                if (bedPos != npc.getPosition())
                    npc.getNavigator().tryMoveToXYZ(bedPos.getX() + 0.5, bedPos.getY(), bedPos.getZ() + 0.5, 1);
            } else {
                bedPos = null;
                if (ModInventoryUtils.hasAny(npc.getNpcInventory(), BEDS))
                    needsBed = true;
            }
        } else {
            if (needsBed)
                lookForBed(npc);
            else
                placeBed(npc, world);
        }
    }

    public void placeBed(AbstractNPCEntity npc, World world) {
        NPCInventory npcInventory = npc.getNpcInventory();
        for (int i = 0; i < npcInventory.getSlots(); i++) {
            ItemStack stack = npcInventory.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() == Items.WHITE_BED) {
                stack.shrink(1);
                BlockPos.getAllInBox(npc.getPosition().add(-10, -10, -10), npc.getPosition().add(10, 10, 10)).forEach(pos -> {
                    if (world.isAirBlock(pos)) {
                        if (!world.isAirBlock(pos.add(0, -1, 0)))
                            bedPos = pos;
                    }
                });
                if (bedPos != null) {
                    world.setBlockState(bedPos, Blocks.WHITE_BED.getDefaultState(), 1);
                    LotbMod.LOGGER.info("bed placed");
                }
            }
        }
    }*/

}
