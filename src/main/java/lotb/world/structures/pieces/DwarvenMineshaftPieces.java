package lotb.world.structures.pieces;

import java.util.List;
import java.util.Random;

import lotb.tools.ModMathFunctions;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class DwarvenMineshaftPieces {
	static final IStructurePieceType DMSCORRIDOR  	= IStructurePieceType.register(Corridor::new,"DMSC");
	static final IStructurePieceType DMSBASE 		= IStructurePieceType.register(Room::new, 	 "DMSR");

	/**=========================================================================================================================
	 *-------------------------------------------------------ROOM---------------------------------------------------------------
	 *=========================================================================================================================*/
	public static class Room extends StructurePiece {
		
		public Room(int id, Random rand, int x, int z) {
			super(DMSBASE, id);
			boundingBox = new MutableBoundingBox(x, 50, z, x+7+rand.nextInt(6), 54+rand.nextInt(6), z+7+rand.nextInt(6));
		}
		/*---------------------------------------------data-------------------------------------------------*/
		public Room(TemplateManager manager, CompoundNBT nbt) {
			super(DMSBASE, nbt);
		}
		@Override protected void readAdditional(CompoundNBT tagCompound) {} 
		
		/*-------------------------------------------generation----------------------------------------------*/
		@Override public boolean create(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox box, ChunkPos chunkPos){
			if (this.isLiquidInStructureBoundingBox(world, box))
	            return false;
        	fillWithBlocks(world, box, boundingBox.minX, boundingBox.minY  , boundingBox.minZ, boundingBox.maxX, boundingBox.minY, boundingBox.maxZ, Blocks.DIRT.getDefaultState(), CAVE_AIR, true);
        	fillWithBlocks(world, box, boundingBox.minX, boundingBox.minY+1, boundingBox.minZ, boundingBox.maxX, boundingBox.maxY,boundingBox.maxZ, CAVE_AIR, CAVE_AIR, false);
        	setBlockState(world, Blocks.RED_WOOL.getDefaultState() , 	boundingBox.minX, boundingBox.minY, boundingBox.minZ, box);
        	setBlockState(world, Blocks.GREEN_WOOL.getDefaultState() ,  boundingBox.minX, boundingBox.minY, boundingBox.maxZ, box);
        	setBlockState(world, Blocks.YELLOW_WOOL.getDefaultState() , boundingBox.maxX, boundingBox.minY, boundingBox.maxZ, box);
        	setBlockState(world, Blocks.BLUE_WOOL.getDefaultState() , 	boundingBox.maxX, boundingBox.minY, boundingBox.minZ, box);
        	BlockPos west	= ModMathFunctions.getAdgacentPosInDir(box, Direction.WEST);
        	BlockPos east	= ModMathFunctions.getAdgacentPosInDir(box, Direction.EAST);
        	BlockPos south	= ModMathFunctions.getAdgacentPosInDir(box, Direction.SOUTH);
        	BlockPos north	= ModMathFunctions.getAdgacentPosInDir(box, Direction.NORTH);
        	setBlockState(world, Blocks.RED_CONCRETE.getDefaultState() , 	north.getX(), north.getY()+1, north.getZ(), box);
        	setBlockState(world, Blocks.GREEN_CONCRETE.getDefaultState() ,  west.getX(), west.getX()+1, west.getX(), box);
        	setBlockState(world, Blocks.YELLOW_CONCRETE.getDefaultState() , south.getX(), south.getX()+1, south.getX(), box);
        	setBlockState(world, Blocks.BLUE_CONCRETE.getDefaultState() , 	east.getX(), east.getX()+1, east.getX(), box);
        	BlockPos centre = new BlockPos(boundingBox.minX+5,boundingBox.minY+2,boundingBox.minZ+5);
        	world.setBlockState(centre, Blocks.IRON_BLOCK.getDefaultState(), 1);
        	world.setBlockState(ModMathFunctions.getRelativePosInDir(centre, Direction.EAST, 5,1,2), Blocks.BLUE_STAINED_GLASS.getDefaultState(), 1);
        	world.setBlockState(ModMathFunctions.getRelativePosInDir(centre, Direction.WEST, 5,1,2), Blocks.YELLOW_STAINED_GLASS.getDefaultState(), 1);
        	world.setBlockState(ModMathFunctions.getRelativePosInDir(centre, Direction.NORTH, 5,1,2), Blocks.RED_STAINED_GLASS.getDefaultState(),1);
        	world.setBlockState(ModMathFunctions.getRelativePosInDir(centre, Direction.SOUTH, 5,1,2), Blocks.GREEN_STAINED_GLASS.getDefaultState(),1);
        	//randomlyRareFillWithBlocks(world, box, boundingBox.minX, boundingBox.minY, boundingBox.minZ, boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ, Blocks.COBWEB.getDefaultState(), false);
        	return true;
        }
        public List<StructurePiece> buildComponent (List<StructurePiece> components, Random rand) {
			for (int r = 0; r < 4; r++)
            	//if (rand.nextBoolean()){
				if(true) {
            		Direction shaftDirection = Direction.byIndex(r+2);
            		//for(int i =0; i < rand.nextInt(8);i++) {
            			BlockPos startPos  = ModMathFunctions.getAdgacentPosInDir(boundingBox, shaftDirection);
            			//Corridor corridor = new Corridor(ModMathFunctions.getPosInDir(startPos,shaftDirection,i*8), shaftDirection, 8);
            			Corridor corridor = new Corridor(startPos, shaftDirection, 8);
            			components.add(corridor);
            		//}
            	}
			return components;
		}
	}
	/**==========================================================================================================================
	 *-------------------------------------------------------SHAFT---------------------------------------------------------------
	 *=========================================================================================================================*/
	public static class Corridor extends StructurePiece{
		int length;
    	
    	public Corridor(BlockPos pos, Direction _dir, int _length) {
			super(DMSCORRIDOR, 0);
			setCoordBaseMode(_dir);
			boundingBox = ModMathFunctions.getDirectedBox(pos.getX(),pos.getY(),pos.getZ(),_dir,4,4,_length);
			length = _length;
		}
		/*---------------------------------------------data-------------------------------------------------*/
		public Corridor(TemplateManager manager, CompoundNBT nbt) {
			super(DMSCORRIDOR, nbt);
		}
		@Override protected void readAdditional(CompoundNBT compound) {}
		/*-------------------------------------------generation----------------------------------------------*/
        @SuppressWarnings("incomplete-switch")
		@Override public boolean create(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox box, ChunkPos chunkPos){
        	//if (this.isLiquidInStructureBoundingBox(world, box))
	        //    return false;
        	BlockState bricks = Blocks.ACACIA_PLANKS.getDefaultState();; 
        	switch (getCoordBaseMode()) {
        	case NORTH:
        		bricks = Blocks.STONE_BRICKS.getDefaultState(); break;
        	case SOUTH:
        		bricks = Blocks.CHISELED_STONE_BRICKS.getDefaultState(); break;
        	case EAST:
        		bricks = Blocks.MOSSY_STONE_BRICKS.getDefaultState(); break;
        	case WEST:
        		bricks = Blocks.CRACKED_STONE_BRICKS.getDefaultState(); break;
        	}
        	fillWithBlocks(world, box, 0, 0, 0, 4, 0, length, bricks, CAVE_AIR, true);
        	fillWithAir(world, box, 0, 1, 0, 4, 4, length);
        	setBlockState(world, Blocks.RED_TERRACOTTA.getDefaultState() , 	  0, 0, 0, box);
        	setBlockState(world, Blocks.GREEN_TERRACOTTA.getDefaultState() ,  0, 0, 4, box);
        	setBlockState(world, Blocks.YELLOW_TERRACOTTA.getDefaultState() , 4, 0, 4, box);
        	setBlockState(world, Blocks.BLUE_TERRACOTTA.getDefaultState() ,   4, 0, 0, box);
        	
        	//randomlyRareFillWithBlocks(world, box, 0, 1, 0, 4, 4, boundingBox.maxZ, Blocks.COBWEB.getDefaultState(), false);
        	return true;
        }
	}
}
