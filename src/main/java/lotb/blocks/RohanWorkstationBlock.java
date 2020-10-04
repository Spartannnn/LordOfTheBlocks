package lotb.blocks;

import javax.annotation.Nullable;

import lotb.inventory.RohanWorkBenchContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class RohanWorkstationBlock extends Block {
	private static final TranslationTextComponent GUI_NAME = new TranslationTextComponent("container.rohan_work_table");

	public RohanWorkstationBlock (Block.Properties properties) {
		super(properties);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult result) {
		if (!worldIn.isRemote) {
			player.openContainer(state.getContainer(worldIn, pos));
		}
		return ActionResultType.SUCCESS;
	}

	@Override @Nullable
	public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
		return new SimpleNamedContainerProvider((id, playerInvent, player) -> {
			return new RohanWorkBenchContainer(id, playerInvent, IWorldPosCallable.of(worldIn, pos));
		}, GUI_NAME);
	}
}
