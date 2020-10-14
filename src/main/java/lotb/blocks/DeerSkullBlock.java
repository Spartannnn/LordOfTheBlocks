package lotb.blocks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import java.util.Map;

public class DeerSkullBlock extends Block {
	//public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_0_15;
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(ImmutableMap.of(
			Direction.NORTH, Block.makeCuboidShape(6.0D, 0.0D, 1.0D, 10.0D, 4.0D, 16.0D),
			Direction.SOUTH, Block.makeCuboidShape(6.0D, 0.0D, 0.0D, 10.0D, 4.0D, 15.0D),
			Direction.EAST, Block.makeCuboidShape(0.0D, 0.0D, 6.0D, 15.0D, 4.0D, 10.0D),
			Direction.WEST, Block.makeCuboidShape(1.0D, 0.0D, 6.0D, 16.0D, 4.0D, 10.0D)));

	public DeerSkullBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
	}

	/*-------------------------INIT-STATE-------------------------*/
	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPES.get(state.get(FACING));
	}

	/*-------------------------ROTATION-------------------------*/
	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(FACING, rot.rotate(state.get(FACING)));
	}
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.toRotation(state.get(FACING)));
	}

	/*----------------------------PLACEMENT-------------------------*/
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		Direction[] adirection = context.getNearestLookingDirections();
		for(Direction direction : adirection)
			if (direction.getAxis().isHorizontal()) {
				return getDefaultState().with(FACING, direction.getOpposite());
			}
		return null;
	}
}
