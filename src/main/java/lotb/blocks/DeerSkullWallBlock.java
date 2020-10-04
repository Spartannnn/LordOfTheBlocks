package lotb.blocks;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
public class DeerSkullWallBlock extends Block {
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(ImmutableMap.of(
			Direction.NORTH, Block.makeCuboidShape(4.0D, 4.0D, 8.0D, 12.0D, 12.0D, 16.0D), 
			Direction.SOUTH, Block.makeCuboidShape(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 8.0D), 
			Direction.EAST, Block.makeCuboidShape(0.0D, 4.0D, 4.0D, 8.0D, 12.0D, 12.0D), 
			Direction.WEST, Block.makeCuboidShape(8.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D)));
	public DeerSkullWallBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
	}

	/*-------------------------INIT-STATE-------------------------*/
	@Override protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
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
				BlockState blockstate = getDefaultState().with(FACING, direction.getOpposite());
				if (!context.getWorld().getBlockState(context.getPos().offset(direction)).isReplaceable(context))
					return blockstate;
			}
		return null;
	}
}
