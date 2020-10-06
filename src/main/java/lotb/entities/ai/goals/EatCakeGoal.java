package lotb.entities.ai.goals;

import lotb.entities.Mouse;
import lotb.registries.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CakeBlock;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class EatCakeGoal extends MoveToBlockGoal {

    private final Mouse mouse;
    private int val;
    private int val2;
    private int hunger;
    private int eatingTimer;

    public EatCakeGoal(Mouse mouse) {
        super(mouse, 1, 10);
        this.mouse = mouse;
        this.hunger = 40; //Change this value to the max value
    }

    @Override
    public boolean shouldMove() {
        return this.timeoutCounter % 100 == 0 && this.hunger <= 0;
    }

    public double getTargetDistanceSq() {
        return 2.0D;
    }

    @Override
    protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
        BlockState state = worldIn.getBlockState(pos);
        if (this.hunger <= 0)
            return isCake(state.getBlock());
        return false;
    }

    public boolean isCake(Block block) {
        return (block == Blocks.CAKE || block == ModBlocks.BEEF_PIE.get() || block == ModBlocks.PORK_PIE.get() || block == ModBlocks.CHICKEN_PIE.get() || block == ModBlocks.MUTTON_PIE.get() || block == ModBlocks.RABBIT_PIE.get() || block == ModBlocks.VENISON_PIE.get() || block == ModBlocks.SALMON_PIE.get() || block == ModBlocks.FISH_PIE.get());
    }

    @Override
    public void startExecuting() {
        this.eatingTimer = 40; //Eating timer
        super.startExecuting();
    }

    @Override
    public void tick() {
        if (this.getIsAboveDestination()) {
            if (this.val >= 40) { //Don't change these values
                this.eatCake();
            } else {
                ++this.val;
            }
            if (val2 > 100 /*Change this value for the time when the hunger should be removed in ticks (100=5sec)*/ && this.hunger > 0) {
                this.hunger -= 5; //Change this value for the decrease amount
                this.val2 = 0;
            } else {
                ++this.val2;
            }
        }

        super.tick();
    }

    private void eatCake() {
        if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.mouse.world, mouse)) {
            BlockState state = this.mouse.world.getBlockState(this.destinationBlock);
            if (isCake(state.getBlock())) {
                eatingTimer = Math.max(0, eatingTimer - 1);
                if (eatingTimer == 4) {
                    int cakeSlice = state.get(CakeBlock.BITES);
                    if (cakeSlice < 6)
                        mouse.world.setBlockState(destinationBlock, state.with(CakeBlock.BITES, cakeSlice + 1), 3);
                    else
                        mouse.world.removeBlock(destinationBlock, false);
                    this.hunger += 20; //Change this value for the increase amount
                }
                mouse.playSound(SoundEvents.ENTITY_GENERIC_EAT, 1, 1);
                if (mouse.world.isRemote)
                    mouse.world.addParticle(ParticleTypes.BUBBLE, destinationBlock.getX(), destinationBlock.getY(), destinationBlock.getZ(), 0.0D, 0.0D, 0.0D);
                this.val = 0;
            }
        }
    }
}
