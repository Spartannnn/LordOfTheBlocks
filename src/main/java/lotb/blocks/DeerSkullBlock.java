package lotb.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class DeerSkullBlock extends Block {
	public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_0_15;
	private static final VoxelShape SHAPE = Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D);

	public DeerSkullBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(ROTATION, Integer.valueOf(0)));
	}

	/*-------------------------INIT-STATE-------------------------*/
	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(ROTATION);
	}
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	/*-------------------------ROTATION-------------------------*/
	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(ROTATION, Integer.valueOf(rot.rotate(state.get(ROTATION), 16)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.with(ROTATION, Integer.valueOf(mirrorIn.mirrorRotation(state.get(ROTATION), 16)));
	}

	/*----------------------------PLACEMENT-------------------------*/
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(ROTATION, Integer.valueOf(MathHelper.floor((double)(context.getPlacementYaw() * 16.0F / 360.0F) + 0.5D) & 15));
	}
}
