package lotb.entities.npc.profesion;

import com.google.common.collect.Sets;
import lotb.LotbMod;
import lotb.entities.npc.AbstractNPCEntity;
import lotb.entities.npc.NPCFoodManager;
import lotb.entities.npc.NPCInventory;
import lotb.util.ModInventoryUtils;
import lotb.util.TCHelper;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;

public class KnightProfession extends SoldierProfession {

    public static final Set<Item> BEDS = Sets.newHashSet();
    public static final Set<Item> WEAPONS = Sets.newHashSet();

    static {
        Collections.addAll(BEDS, Items.WHITE_BED);
        Collections.addAll(WEAPONS, Items.IRON_SWORD);
    }


    private int currentTick;
    public ProffesionActivity currentState;
    @Nullable
    public BlockPos bedPos;
    public boolean needsBed = false;
    public boolean needsFood = false;
    public boolean needsWeapon = false;

    public KnightProfession() {
        this.currentState = ProffesionActivity.FREETIME;
        this.currentTick = 2000;
        this.bedPos = null;
    }

    @Override
    public boolean onRightClick(AbstractNPCEntity npc, ItemStack stack, PlayerEntity player, World world) {
        if (currentState != ProffesionActivity.SLEEPING)
            player.sendMessage(TCHelper.string("DEBUG: Not sleeping"));
        return true;
    }

    @Override
    public void onAttacked(AbstractNPCEntity npc, PlayerEntity player, World world) {

    }

    @Override
    public void tick(AbstractNPCEntity npc, World world) {
        currentTick -= 1;
        NPCFoodManager foodManager = npc.getFoodManager();
        this.needsFood = foodManager.needFood();
        if (currentTick == 0) {
            switch (currentState) {
                case SLEEPING:
                case PATROL:
                    startFreeTime();
                case FREETIME:
                    if (world.isNightTime())
                        if (!needsBed)
                            startSleeping();
                        else if (!needsWeapon)
                            startPatrolling();
            }
        } else
            switch (currentState) {
                case PATROL:
                    tickPatrolling();
                case FREETIME:
                    tickFreeTime(npc, world);
                case SLEEPING:
                    tickSleeping(npc, world);
            }
    }

    @Override
    public void kill(AbstractNPCEntity npc, World world) {
        
    }

    public void tickPatrolling() {
        if (needsWeapon)
            lookForWeapon();
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
            lookForWeapon();
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
                    if (world.getBlockState(pos).isAir()) {
                        if (!world.getBlockState(pos.add(0, -1, 0)).isAir())
                            bedPos = pos;
                    }
                });
                if (bedPos != null) {
                    world.setBlockState(bedPos, Blocks.WHITE_BED.getDefaultState(), 1);
                    LotbMod.LOGGER.info("bed placed");
                }
            }
        }
    }

    public void lookForBed(AbstractNPCEntity npc) {
        ModInventoryUtils.addItem(npc.getNpcInventory(), new ItemStack(Items.WHITE_BED));
    }

    public void lookForFood() {

    }

    public void lookForWeapon() {

    }


    public void startPatrolling() {
        currentState = ProffesionActivity.PATROL;
        currentTick = 6000;
    }

    public void startSleeping() {
        currentState = ProffesionActivity.SLEEPING;
        currentTick = 6000;
    }

    public void startFreeTime() {
        currentState = ProffesionActivity.FREETIME;
        currentTick = 2000;
    }


    public enum ProffesionActivity {
        SLEEPING,
        PATROL,
        FREETIME;
    }
}
