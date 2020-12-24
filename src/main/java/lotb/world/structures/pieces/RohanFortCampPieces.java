package lotb.world.structures.pieces;

import java.util.List;
import java.util.Random;

import com.mojang.datafixers.util.Pair;

import lotb.lore.Faction;
import lotb.registries.ModBlocks;
import lotb.registries.ModItems;
import lotb.util.ModMathFunctions;
import lotb.world.structures.ModStructurePiece;
import lotb.world.structures.gen.RohanFortCamp;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BedPart;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.RailShape;
import net.minecraft.state.properties.SlabType;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTables;

public class RohanFortCampPieces {
	public static final BlockState AIR = Blocks.AIR.getDefaultState();
	public static final BlockState DIRT = Blocks.COARSE_DIRT.getDefaultState();
	public static final BlockState WATER = Blocks.WATER.getDefaultState();
	public static final BlockState FENCE = Blocks.OAK_FENCE.getDefaultState();
	public static final BlockState GATE = Blocks.OAK_FENCE_GATE.getDefaultState();
	public static final BlockState LOG = Blocks.STRIPPED_OAK_LOG.getDefaultState();
	public static final BlockState LOGX = LOG.with(LogBlock.AXIS, Direction.Axis.X);
	public static final BlockState LOGZ = LOG.with(LogBlock.AXIS, Direction.Axis.Z);
	public static final BlockState ROOF = Blocks.SPRUCE_PLANKS.getDefaultState();
	public static final BlockState ROOF_SLAB = Blocks.SPRUCE_SLAB.getDefaultState();
	public static final BlockState ROOF_STOP = ROOF_SLAB.with(SlabBlock.TYPE,SlabType.TOP);
	public static final BlockState COBBLE_SLAB = Blocks.COBBLESTONE_SLAB.getDefaultState();
	public static final BlockState LANTERN = Blocks.LANTERN.getDefaultState();

	public static final StructurePiece.BlockSelector PATH = new ModStructurePiece.NoisySelector(3,
			Pair.of(3, DIRT),
			Pair.of(2, Blocks.GRAVEL.getDefaultState()),
			Pair.of(4, Blocks.PODZOL.getDefaultState()),
			Pair.of(4, Blocks.GRASS_BLOCK.getDefaultState()),
			Pair.of(1, Blocks.COBBLESTONE.getDefaultState()),
			Pair.of(1, Blocks.MOSSY_COBBLESTONE.getDefaultState()),
			Pair.of(5, Blocks.GRASS_PATH.getDefaultState()));
	public static final StructurePiece.BlockSelector WALL = new ModStructurePiece.RandomSelector(
			Pair.of(6, Blocks.OAK_LOG.getDefaultState()),
			Pair.of(5, Blocks.DARK_OAK_LOG.getDefaultState()),
			Pair.of(4, Blocks.SPRUCE_LOG.getDefaultState()),
			Pair.of(3, ModBlocks.LEBETHRON_LOG.get().getDefaultState()),
			Pair.of(2, Blocks.ACACIA_LOG.getDefaultState()),
			Pair.of(1, Blocks.BIRCH_LOG.getDefaultState()));
	public static final StructurePiece.BlockSelector CROP = new ModStructurePiece.RandomSelector(
			Pair.of(5, Blocks.WHEAT.getDefaultState().with(CropsBlock.AGE, 7)),
			Pair.of(2, Blocks.CARROTS.getDefaultState().with(CropsBlock.AGE, 7)),
			Pair.of(4, Blocks.POTATOES.getDefaultState().with(CropsBlock.AGE, 7)),
			Pair.of(1, Blocks.BEETROOTS.getDefaultState().with(BeetrootBlock.BEETROOT_AGE,3)));
	/*---------------------------------------------functions-------------------------------------------------*/
	public static StructurePiece getSingleNonTentPiece(BlockPos pos, Direction dir, Random rand, RohanFortCamp.Data data, ChunkGenerator<?> _gen, List<StructurePiece> components) {
		while (true) {
			int i =rand.nextInt(73);
			if 		 (i<10 && !data.utilityPlaced) {
				data.utilityPlaced = true;
				return new UtilitiesTent(pos,dir,_gen);
			}else if (i<20 && !data.planTentPlaced) {
				data.planTentPlaced = true;
				return new PlanningTent(pos,dir,_gen);
			}else if (i<30 && !data.kitchenPlaced) {
				data.kitchenPlaced = true;
				return new KitchenTent(pos,dir,_gen);
			}else if (i<40 && !data.infirmaryPlaced) {
				data.infirmaryPlaced = true;
				return new Infirmary(pos,dir,_gen);
			}else if (i<50 && !data.blacksmithPlaced) {
				data.blacksmithPlaced = true;
				return new Smithy(pos,dir,_gen);
			}else if (i<60 && !data.meleeAreaPlaced) {
				data.meleeAreaPlaced = true;
				return new MeleeArena(pos,dir,_gen);
			}else if (i<70 && !data.firePlaced) {
				data.firePlaced = true;
				return new CampFire(pos,dir,_gen);
			}else if (i==70) {
				return new StoreTent(pos,dir,_gen);
			}else {
				return new AnimalPen(pos,dir,_gen);
			}
		}
	}
	public static void addSinglePiece(BlockPos pos, Direction dir, Random rand, RohanFortCamp.Data data, ChunkGenerator<?> _gen, List<StructurePiece> components) {
		StructurePiece piece;
		int i = rand.nextInt(7);
		if (i==0 || (i<3 && !data.requiredMet())) 
			piece = getSingleNonTentPiece(pos,dir,rand,data,_gen,components);
		else if (i==3) {
			piece = new Stables(pos,dir,_gen);
			data.numberofstables++;
		}else{
			piece = new Tent(pos,dir,_gen);
			data.numberoftents++;
		}
		components.add(piece);
	}
	public static void addDoublePiece(BlockPos pos, Direction dir, Random rand, RohanFortCamp.Data data, ChunkGenerator<?> _gen, List<StructurePiece> components) {
		int i = rand.nextInt(3);
		if 		(i==0 && !data.archeryAreaPlaced) {
			components.add( new ArcheryRange(pos, dir,_gen) );
			data.archeryAreaPlaced = true;
		}else if(i==1 && !data.cavalryAreaPlaced) {
			components.add( new CavallaryArena(pos, dir,_gen) );
			data.cavalryAreaPlaced = true;
		}else if(i==2 && !data.eatingAreaPlaced) {
			components.add( new EatingArea(pos, dir,_gen) );
			data.eatingAreaPlaced = true;
		}else {
			addSinglePiece(pos,dir,rand,data,_gen,components);
			BlockPos pos2 = ModMathFunctions.getRelativePosInDir(pos, dir, 0, 0, 8);
			addSinglePiece(pos2,dir,rand,data,_gen,components);
		}
	}
	public static void addSmallPiece( BlockPos pos, Direction dir, Random rand, RohanFortCamp.Data data, ChunkGenerator<?> _gen, List<StructurePiece> components) {
		int i = rand.nextInt(6);
		if (i==0) {
			data.wellPlaced=true;
			components.add( new Well(pos,dir,_gen) );
		}else if (i==1){
			components.add( new Farm(pos,dir,_gen) );
		}else if (i==2){
			components.add( new HayBale(pos,dir,_gen) );
		}else if (i==3){
			components.add( new Crates(pos,dir,_gen) );
		}else if (i==4){
			components.add( new LogPile(pos,dir,_gen) );
		} else {
			components.add( new WatchTower(pos,dir,_gen,rand) );
		}
	}
	public static void addEndPiece(	  BlockPos pos, Direction dir, Random rand, RohanFortCamp.Data data, ChunkGenerator<?> _gen, List<StructurePiece> components) {
		if (!data.captainPlaced) {
			data.captainPlaced = true;
			components.add( new CaptainsTent(pos,dir,_gen) );
		}else if (rand.nextBoolean())
			addSmallPiece(ModMathFunctions.getRelativePosInDir(pos, dir, 0, 0, 2),dir,rand,data,_gen, components);
		else
			components.add( getSingleNonTentPiece(pos,dir,rand,data,_gen,components) );
	}

	/**=========================================================================================================================
	|*--------------------------------------------------------WALL---------------------------------------------------------------
	|*=========================================================================================================================*/
	static final IStructurePieceType PIECES_GATE = IStructurePieceType.register(Gate::new,"RoFCG");
	static final IStructurePieceType PIECES_WALL = IStructurePieceType.register(Wall::new,"RoFCW");
	static final IStructurePieceType PIECES_CORNER = IStructurePieceType.register(WallCorner::new,"RoFCC");

	public static class Gate extends ModStructurePiece {
		BlockPos origin;
		public Gate(int id, Random rand, int x, int y, int z) {
			super(PIECES_GATE, id);
			Direction dir = Direction.Plane.HORIZONTAL.random(rand);
			setCoordBaseMode(dir);
			origin = ModMathFunctions.getRelativePosInDir(new BlockPos(x,y,z), dir, 0, 0, 8);
			boundingBox = ModMathFunctions.getDirectedBox(x, y, z, dir, 18, 4, 0);
		}
		/*---------------------------------------------data-------------------------------------------------*/
		public Gate(TemplateManager manager, CompoundNBT nbt) {
			super(PIECES_GATE, nbt);
		}
		@Override protected void readAdditional(CompoundNBT tagCompound) {} 
		
		
		/*-------------------------------------------generation----------------------------------------------*/
		@Override public boolean create(IWorld world, ChunkGenerator<?> _gen, Random rand, MutableBoundingBox box, ChunkPos chunkPos){
			for (int i=0;i<=7;i++) {
				int depth = getDepthAt(_gen,i,0);
				int dept1 = getDepthAt(_gen,i+11,0);
				WALL.selectBlocks(rand, i,depth,0, false);
				for (int y =0;y<rand.nextInt(3)+3;y++)
					setBlockState(world, WALL.getBlockState(), i, depth+y, 0, box);
				for (int y =0;y<rand.nextInt(3)+3;y++)
					setBlockState(world, WALL.getBlockState(),i+11,dept1+y,0, box);
			}return true;
        }
        public void buildComponent (List<StructurePiece> components, Random rand, RohanFortCamp.Data data, ChunkGenerator<?> generator) {
			Direction pathDirection = getCoordBaseMode();
    		BlockPos startPos  = ModMathFunctions.getRelativePosInDir(origin, pathDirection, 1,0,0);
			Path path  = new Path(startPos, pathDirection,rand);
			components.add(path);
    		//placing front tents
    		Direction lTentDir = Rotation.COUNTERCLOCKWISE_90.rotate(pathDirection);
        	Direction rTentDir = Rotation.CLOCKWISE_90.rotate(		 pathDirection);
    		BlockPos lTentPos  = ModMathFunctions.getRelativePosInDir(origin, pathDirection, 1,0,-1);
    		BlockPos rTentPos  = ModMathFunctions.getRelativePosInDir(origin, pathDirection, 8,0,3);
    		addSinglePiece(lTentPos,lTentDir,rand,data,generator,components);
    		addSinglePiece(rTentPos,rTentDir,rand,data,generator,components);
    		//walls
    		BlockPos lWallPos = ModMathFunctions.getRelativePosInDir(origin, pathDirection, 1,0,-9);
    		BlockPos rWallPos = ModMathFunctions.getRelativePosInDir(origin, pathDirection, 1,0,11);
    		components.add(new Wall(lWallPos,lTentDir,9));
    		components.add(new Wall(rWallPos,lTentDir,9));
    		//building
			path.buildComponent(components, rand, data, generator, (byte)0, (byte)0);
		}
	}
	public static class Wall extends ModStructurePiece {
		int walllength;
		public Wall(BlockPos pos, Direction _dir,  int length) {
			super(PIECES_WALL, 0);
			setCoordBaseMode(_dir);
			walllength=length;
			boundingBox = ModMathFunctions.getDirectedBox(pos.getX(), pos.getY(), pos.getZ(), _dir, length, 4, 0);
		}
		/*---------------------------------------------data-------------------------------------------------*/
		public Wall(TemplateManager manager, CompoundNBT nbt) {
			super(PIECES_WALL, nbt);
			walllength = nbt.getInt("len");
		}
		@Override protected void readAdditional(CompoundNBT nbt) {
			nbt.putInt("len",walllength);
		} 
		
		/*-------------------------------------------generation----------------------------------------------*/
		@Override public boolean create(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox box, ChunkPos chunkPos){
			for (int i=0;i<=walllength;i++) {
				int depth = ( generator.func_222529_a(getXWithOffset(i,0), getZWithOffset(i,0), Heightmap.Type.OCEAN_FLOOR_WG)-1)-boundingBox.minY;
				WALL.selectBlocks(rand, i,depth,0, false);
				for (int y =0;y<rand.nextInt(3)+3;y++)
					setBlockState(world, WALL.getBlockState(), i, depth+y, 0, box);
			}return true;
        }
	}
	public static class WallCorner extends ModStructurePiece {
		public WallCorner(BlockPos pos, Direction _dir) {
			super(PIECES_CORNER, 0);
			setCoordBaseMode(_dir);
			boundingBox = ModMathFunctions.getDirectedBox(pos.getX(), pos.getY(), pos.getZ(), _dir, 7, 4, 7);
		}
		/*---------------------------------------------data-------------------------------------------------*/
		public WallCorner(TemplateManager manager, CompoundNBT nbt) {
			super(PIECES_CORNER, nbt);
		}
		@Override protected void readAdditional(CompoundNBT tagCompound) {} 
		
		/*-----------------------------------------mirror changes--------------------------------------------*/
		@Override protected int getXWithOffset(int x, int z) {
			Direction direction = this.getCoordBaseMode();
			if (direction == null) {
				return x;
			}
			switch(direction) {
			case NORTH:
				return this.boundingBox.minX + x;
			case SOUTH:
				return this.boundingBox.maxX - x;
			case WEST:
				return this.boundingBox.maxX - z;
			case EAST:
				return this.boundingBox.minX + z;
			default:
				return x;
			}
		}
		@Override protected int getZWithOffset(int x, int z) {
			Direction direction = this.getCoordBaseMode();
			if (direction == null) {
				return z;
			}
			switch(direction) {
			case NORTH:
				return this.boundingBox.maxZ - z;
			case SOUTH:
				return this.boundingBox.minZ + z;
			case WEST:
				return this.boundingBox.maxZ - x;
			case EAST:
				return this.boundingBox.minZ + x;
			default:
				return z;
			}
		}
		/*-------------------------------------------generation----------------------------------------------*/
		@Override public boolean create(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox box, ChunkPos chunkPos){
			for (int i=0;i<7;i++) {
				int depth = ( generator.func_222529_a(getXWithOffset(i,7-i), getZWithOffset(i,7-i), Heightmap.Type.OCEAN_FLOOR_WG)-1)-boundingBox.minY;
				WALL.selectBlocks(rand, i,depth,0, false);
				for (int y =0;y<rand.nextInt(3)+3;y++)
					setBlockState(world, WALL.getBlockState(), i, depth+y, 7-i, box);
			}
        	return true;
        }
	}
	
	/**==========================================================================================================================
	|*-------------------------------------------------------PATH----------------------------------------------------------------
	|*=========================================================================================================================*/
	static final IStructurePieceType PIECES_PATH = IStructurePieceType.register(Path::new,"RoFCP");
	static final IStructurePieceType PIECES_TURN = IStructurePieceType.register(PathTurn::new,"RoFCX");
	static final IStructurePieceType PIECES_END = IStructurePieceType.register(PathEnd::new,"RoFCE");

	public static class Path extends ModStructurePiece{
		public final int length;
		public final int blockLength;
		public BlockPos origin;
    	
    	public Path(BlockPos pos, Direction _dir, Random rand) {
			super(PIECES_PATH, 0);
			origin = pos;
			length = rand.nextInt(3)+3;
			blockLength = length * 8;
			setCoordBaseMode(_dir);
			boundingBox = ModMathFunctions.getDirectedBox(pos.getX(),pos.getY(),pos.getZ(),_dir, 2, 1, blockLength+1);
		}
		/*---------------------------------------------data-------------------------------------------------*/
		public Path(TemplateManager manager, CompoundNBT nbt) {
			super(PIECES_PATH, nbt);
			length = nbt.getInt("Len");
			blockLength = length * 8;
			origin = new BlockPos( nbt.getInt("Xop") , nbt.getInt("Yop") , nbt.getInt("Zop") );
		}
		@Override protected void readAdditional(CompoundNBT nbt) {
			nbt.putInt("Len", length);
			nbt.putInt("Xop", origin.getX());
			nbt.putInt("Yop", origin.getY());
			nbt.putInt("Zop", origin.getZ());
		}
		/*-------------------------------------------generation----------------------------------------------*/
        
		@Override public boolean create(IWorld world, ChunkGenerator<?> _gen, Random rand, MutableBoundingBox box, ChunkPos chunkPos){
			for (int x = 0; x <= 2; x++)
				for (int z = 0; z <= blockLength; z++) {
					int depth = getDepthAt(_gen,x,z) - 1;
					PATH.selectBlocks(rand, x, depth, z, false);
					setBlockState(world, PATH.getBlockState(), x, depth, z, box);
					setBlockState(world, AIR, x, depth+1, z, box);
					setBlockState(world, AIR, x, depth+2, z, box);
				}
			return true;
        }	
        
		public void buildComponent(List<StructurePiece> components, Random rand, RohanFortCamp.Data data, ChunkGenerator<?> generator, byte turnCountEast, byte turnCountWest) {
        	Direction pathDirection = getCoordBaseMode();
        	Direction lTentDir = Rotation.COUNTERCLOCKWISE_90.rotate(pathDirection);
        	Direction rTentDir = Rotation.CLOCKWISE_90.rotate(		 pathDirection);
        	
        	//------------tents------------
        	boolean doublenextL = false;
        	boolean doublenextR = false;
        	for (int i = 1; i < length; i++) {
        		if (doublenextL)
        			doublenextL = false;
        		else {
        			BlockPos lTentPos  = ModMathFunctions.getRelativePosInDir(origin, pathDirection, 0+(i*8),0,-1);
        			if (rand.nextInt(3)==0 && i != length-1){
        				addDoublePiece(lTentPos,lTentDir,rand,data,generator,components);
        				doublenextL=true;
        			}else 
        				addSinglePiece(lTentPos,lTentDir,rand,data,generator,components);
        		}
        		if (rand.nextInt(3)==0 && i != length-1)
        			doublenextR = true;
        		else {
        			BlockPos rTentPos  = ModMathFunctions.getRelativePosInDir(origin, pathDirection, 7+(i*8),0,3);
        			if (doublenextR){
        				addDoublePiece(rTentPos,rTentDir,rand,data,generator,components);
        				doublenextR=false;
        			}else 
        				addSinglePiece(rTentPos,rTentDir,rand,data,generator,components);
        		}
        	}

        	//------------the end of the path------------
        	BlockPos endposition = ModMathFunctions.getRelativePosInDir(origin, pathDirection, blockLength+1, 0, 0);
        	StructurePiece end;
        	if (data.requiredMet())//(rand.nextInt(15)!=0 && data.requiredMet())
        		end = new PathEnd( endposition, pathDirection, data, generator);
        	else
        		end = new PathTurn(endposition, pathDirection, rand, data, generator, turnCountEast, turnCountWest);
        	components.add(end);
        	end.buildComponent(end, components, rand);
        	
        	//------------walls------------
        	int wallLength = blockLength-18;
        	if (wallLength>0) {
        		components.add( new Wall( ModMathFunctions.getRelativePosInDir(origin, pathDirection, 9, 0, -9), lTentDir, wallLength));
        		components.add( new Wall( ModMathFunctions.getRelativePosInDir(origin, pathDirection, 9, 0, 11), lTentDir, wallLength));
        	}
		}
	}
	public static class PathTurn extends ModStructurePiece{
		ChunkGenerator<?> generator;
		RohanFortCamp.Data structureData;
		BlockPos origin;
		byte pathends;
		byte turnCountRight;
		byte turnCountLeft;
		boolean leftIsMoreThan3;
		boolean rigIsMoreThan3;
    	
    	public PathTurn(BlockPos pos, Direction _dir, Random rand, RohanFortCamp.Data _structureData, ChunkGenerator<?> _gen, byte _tcr, byte _tcl) {
			super(PIECES_TURN, 0);
			origin = pos;
			generator =_gen;
			structureData = _structureData;
			turnCountRight = _tcr;
			turnCountLeft = _tcl;
			setCoordBaseMode(_dir);
			boundingBox = ModMathFunctions.getDirectedBox(pos.getX(),pos.getY(),pos.getZ(), _dir, 2,1,2);
			pathends = (byte)rand.nextInt(6);
		}
    	
		/*---------------------------------------------data-------------------------------------------------*/
		public PathTurn(TemplateManager manager, CompoundNBT nbt) {
			super(PIECES_TURN, nbt);
			pathends = nbt.getByte("End");
		}
		@Override protected void readAdditional(CompoundNBT nbt) {
			nbt.putByte("End",pathends);
		}
		
		/*-------------------------------------------generation----------------------------------------------*/
        @Override public boolean create(IWorld world, ChunkGenerator<?> _gen, Random rand, MutableBoundingBox box, ChunkPos chunkPos){
        	for (int x = 0; x <= 2; x++)
				for (int z = 0; z <= 2; z++) {
					int depth = getDepthAt(_gen,x,z) - 1;
					PATH.selectBlocks(rand, x, depth, z, false);
					setBlockState(world, PATH.getBlockState(), x, depth, z, box);
					setBlockState(world, AIR, x, depth+1, z, box);
					setBlockState(world, AIR, x, depth+2, z, box);
				}
			return true;
        }       
        @Override public void buildComponent (StructurePiece piece, List<StructurePiece> components, Random rand) {
        	//init directions and checkbools
    		Direction pathDirection = getCoordBaseMode();
    		Direction pathDirectionL = Rotation.COUNTERCLOCKWISE_90.rotate(pathDirection);
    		Direction pathDirectionR = Rotation.CLOCKWISE_90.rotate(pathDirection);
    		BlockPos startblockL = ModMathFunctions.getAdgacentPosInDir(boundingBox, pathDirectionL);
    		BlockPos startblockR = ModMathFunctions.getAdgacentPosInDir(boundingBox, pathDirectionR);
    		boolean hasLeftPath	= pathends <= 3 && turnCountLeft < 2;
    		boolean hasRightPath= pathends >= 2 && turnCountRight < 2;
    		boolean hasForPath = pathends == 1 || pathends == 2 || pathends == 4;
    		//left and right path
        	if (hasLeftPath) {
        		Path path = new Path(startblockL,pathDirectionL,rand);
            	components.add(path);
            	path.buildComponent(components, rand, structureData, generator, (byte)Math.max(turnCountRight-(hasForPath?1:0),0), (byte)(turnCountLeft+1));
        	}else{
        		int wallLength = hasForPath?8:0;
            	addSmallPiece(startblockL,pathDirectionL,rand,structureData,generator,components);
            	components.add(new Wall( ModMathFunctions.getRelativePosInDir(origin, pathDirection, -9,0,-9), pathDirectionL , 12+wallLength));
            }
    		if (hasRightPath) {
    			Path path = new Path(startblockR,pathDirectionR,rand);
            	components.add(path);
            	path.buildComponent(components, rand, structureData, generator, (byte)(turnCountRight+1), (byte)Math.max(turnCountLeft-(hasForPath?1:0),0));
        	}else{
        		addSmallPiece(startblockR,pathDirectionR,rand,structureData,generator,components);
        		int wallLength = hasForPath?8:0;
            	components.add(new Wall( ModMathFunctions.getRelativePosInDir(origin, pathDirection, 3+wallLength, 0,11), pathDirectionR , 12+wallLength));
        	}
    		//forward path
    		BlockPos startblock = ModMathFunctions.getAdgacentPosInDir(boundingBox, pathDirection);
    		if (hasForPath) {
        		//placing front tents
        		BlockPos lTentPos  = ModMathFunctions.getRelativePosInDir(origin, pathDirection, 3,0,-1);
        		BlockPos rTentPos  = ModMathFunctions.getRelativePosInDir(origin, pathDirection, 9,0,3);
        		addSinglePiece(lTentPos,pathDirectionL,rand,structureData,generator,components);
        		addSinglePiece(rTentPos,pathDirectionR,rand,structureData,generator,components);
        		//placing forward path
    			Path path = new Path(startblock,pathDirection,rand);
            	components.add(path);
            	path.buildComponent(components, rand, structureData, generator, (byte)(turnCountRight-1), (byte)(turnCountLeft-1));
        	}else{
        		addSmallPiece(startblock,pathDirection,rand,structureData,generator, components);
        		//if forward, check for sidepaths and add if they exist (if they don't we'll add corners in a mo)
        		int lwalllength=0;int rwalllength=0;
            	if (hasLeftPath) { 
            		BlockPos tentPos  = ModMathFunctions.getRelativePosInDir(origin, pathDirection, 3,0,-7);
            		addSinglePiece(tentPos,pathDirection,rand,structureData,generator, components);
            		lwalllength=8;
            	}else
            		components.add(new WallCorner( ModMathFunctions.getRelativePosInDir(origin, pathDirection, 4,0,-1), pathDirectionL));
            	if (hasRightPath) {
            		BlockPos tentPos  = ModMathFunctions.getRelativePosInDir(origin, pathDirection, 3,0,3);
            		addSinglePiece(tentPos,pathDirection,rand,structureData,generator, components);
            		rwalllength=8;
            	}else
            		components.add(new WallCorner( ModMathFunctions.getRelativePosInDir(origin, pathDirection, 3,0, 4), pathDirection));
            	components.add(new Wall( ModMathFunctions.getRelativePosInDir(origin, pathDirection, 11,0,-1-lwalllength), pathDirection  , 4+rwalllength+lwalllength));
        	}
		}
	}
	public static class PathEnd extends ModStructurePiece{
		BlockPos origin;
		RohanFortCamp.Data structureData;
		ChunkGenerator<?> generator;
		
    	public PathEnd(BlockPos pos, Direction _dir, RohanFortCamp.Data _data,ChunkGenerator<?> _gen) {
			super(PIECES_END, 0);
			origin = pos;
			generator = _gen;
			structureData = _data;
			setCoordBaseMode(_dir);
			boundingBox = ModMathFunctions.getDirectedBox(pos.getX(),pos.getY(),pos.getZ(),_dir,2,1,2);
		}
		/*---------------------------------------------data-------------------------------------------------*/
		public PathEnd(TemplateManager manager, CompoundNBT nbt) {
			super(PIECES_END, nbt);
		}
		@Override protected void readAdditional(CompoundNBT compound) {}
		/*-------------------------------------------generation----------------------------------------------*/
        @Override public boolean create(IWorld world, ChunkGenerator<?> _gen, Random rand, MutableBoundingBox box, ChunkPos chunkPos){
        	for (int x = 0; x <= 2; x++)
				for (int z = 0; z <= 2; z++) {
					int depth = getDepthAt(_gen,x,z)-1;
					PATH.selectBlocks(rand, x, depth, z, false);
					setBlockState(world, PATH.getBlockState(), x, depth, z, box);
					setBlockState(world, AIR, x, depth+1, z, box);
					setBlockState(world, AIR, x, depth+2, z, box);
				}
        	return true;
        }
        @Override public void buildComponent (StructurePiece piece,List<StructurePiece> components, Random rand) {
    		Direction direction	 = getCoordBaseMode();
    		Direction directionL = Rotation.COUNTERCLOCKWISE_90.rotate(direction);
    		Direction directionR = Rotation.CLOCKWISE_90.rotate(direction);
    		BlockPos startblock  = ModMathFunctions.getRelativePosInDir(origin, direction, 3,0,-2);
    		BlockPos startblockL = ModMathFunctions.getAdgacentPosInDir(boundingBox, directionL);
    		BlockPos startblockR = ModMathFunctions.getAdgacentPosInDir(boundingBox, directionR);
    		addEndPiece(startblock,direction,rand,structureData,generator, components);
        	addSmallPiece(startblockR,directionR,rand,structureData,generator, components);
        	addSmallPiece(startblockL,directionL,rand,structureData,generator, components);
        	components.add(new Wall( ModMathFunctions.getRelativePosInDir(origin, direction, -9,0,-9), directionL , 12));
        	components.add(new Wall( ModMathFunctions.getRelativePosInDir(origin, direction, 11,0,-1), direction  , 4));
        	components.add(new Wall( ModMathFunctions.getRelativePosInDir(origin, direction, 3, 0,11), directionR , 12));
        	components.add(new WallCorner( ModMathFunctions.getRelativePosInDir(origin, direction, 4,0,-1), directionL));
        	components.add(new WallCorner( ModMathFunctions.getRelativePosInDir(origin, direction, 3,0, 4), direction));
    	}
	}
	
	/**==========================================================================================================================
	|*--------------------------------------------------------PIECES--------------------------------------------------------------
	|*=========================================================================================================================*/
	static final IStructurePieceType PIECE_TENT = IStructurePieceType.register(Tent::new,"RoFCT");
	static final IStructurePieceType PIECE_CAPTAIN = IStructurePieceType.register(CaptainsTent::new,"RoFCTC");
	static final IStructurePieceType PIECE_UTILITY = IStructurePieceType.register(UtilitiesTent::new,"RoFCTU");
	static final IStructurePieceType PIECE_STORE = IStructurePieceType.register(StoreTent::new,"RoFCTS");
	static final IStructurePieceType PIECE_PLAN = IStructurePieceType.register(PlanningTent::new,"RoFCTP");
	static final IStructurePieceType PIECE_KITCHEN = IStructurePieceType.register(KitchenTent::new,"RoFCTK");
	static final IStructurePieceType PIECE_MEDIC = IStructurePieceType.register(Infirmary::new,"RoFCTM");

	public static abstract class AbstractTent extends ModStructurePiece{
		public AbstractTent(IStructurePieceType type, BlockPos pos, Direction _dir, ChunkGenerator<?> _gen) {
			super(type, 0);
			int depth = _gen.func_222529_a(pos.getX()+1, pos.getZ()+1, Heightmap.Type.OCEAN_FLOOR_WG)-1;
			setCoordBaseMode(_dir);
			boundingBox = ModMathFunctions.getDirectedBox(pos.getX(), depth, pos.getZ(), _dir, 7, 7, 7);
		}
		public AbstractTent(IStructurePieceType type, CompoundNBT nbt) {
			super(type, nbt);
		}
		@Override protected void readAdditional(CompoundNBT compound) {}
		/*-------------------------------------------generation----------------------------------------------*/
		public void buildTent(IWorld world, MutableBoundingBox box, BlockState wool, Random rand){
			BlockState fence= Blocks.OAK_FENCE.getDefaultState();
			for (int x=1;x<=5;x++) for (int z=1;z<=5;z++) replaceAirAndLiquidDownwards(world, DIRT, x, 0, z, box);
			fillWithAir(world, box, 2, 1, 2, 4, 2, 4);
			//wool walls
			fillWithBlocks(world, box, 1, 1, 2, 1, 2, 4, wool, wool, false);
			setBlockState(world, wool, 1, 3, 3, box);
			fillWithBlocks(world, box, 5, 1, 2, 5, 2, 4, wool, wool, false);
			setBlockState(world, wool, 5, 3, 3, box);
			fillWithBlocks(world, box, 2, 1, 5, 4, 2, 5, wool, wool, false);
			setBlockState(world, wool, 3, 3, 5, box);
			//door bit
			setBlockState(world, AIR, 3, 1, 1, box);
			setBlockState(world, AIR, 3, 2, 1, box);
			setBlockState(world, wool, 2, 1, 1, box);
			setBlockState(world, wool, 2, 2, 1, box);
			setBlockState(world, wool, 4, 1, 1, box);
			setBlockState(world, wool, 4, 2, 1, box);
			setBlockState(world, wool, 3, 3, 1, box);
			//top bit
			setBlockState(world, wool, 2, 3, 2, box);//layer 1
			setBlockState(world, wool, 2, 3, 4, box);
			setBlockState(world, wool, 4, 3, 2, box);
			setBlockState(world, wool, 4, 3, 4, box);
			setBlockState(world, wool, 2, 4, 2, box);
			setBlockState(world, wool, 2, 4, 4, box);
			setBlockState(world, wool, 4, 4, 2, box);
			setBlockState(world, wool, 4, 4, 4, box);//layer 2
			setBlockState(world, wool, 2, 4, 3, box);
			setBlockState(world, wool, 4, 4, 3, box);
			setBlockState(world, wool, 3, 4, 2, box);
			setBlockState(world, wool, 3, 4, 4, box);
			setBlockState(world, wool, 2, 5, 3, box);
			setBlockState(world, wool, 4, 5, 3, box);
			setBlockState(world, wool, 3, 5, 2, box);
			setBlockState(world, wool, 3, 5, 4, box);
			//main pole
			fillWithBlocks(world, box, 3, 1, 3, 3, 4, 3, fence,fence, false);
			setBlockState(world, wool, 3, 5, 3, box);
			setBlockState(world, wool, 3, 6, 3, box);
			setBlockState(world, fence, 3, 7, 3, box);
			setBlockState(world, fence, 6, 1, 3, box);//guide ropes
			setBlockState(world, fence, 0, 1, 3, box);
			setBlockState(world, fence, 3, 1, 6, box);
		}
		public void buildUtilTent(IWorld world, MutableBoundingBox box, BlockState wool, BlockState cross, Random rand) {
			
			BlockState fence= Blocks.OAK_FENCE.getDefaultState();
			for (int x=1;x<=5;x++) for (int z=1;z<=5;z++) replaceAirAndLiquidDownwards(world, DIRT, x, 0, z, box);
			fillWithAir(world, box, 2, 1, 2, 4, 6, 4);
			//wool walls
			fillWithBlocks(world, box, 1, 1, 1, 1, 2, 4, wool, wool, false);//left wall
			setBlockState(world, wool, 1, 3, 2, box);
			setBlockState(world, cross,1, 3, 3, box);
			setBlockState(world, wool, 1, 3, 4, box);
			fillWithBlocks(world, box, 5, 1, 1, 5, 2, 4, wool, wool, false);//right wall
			setBlockState(world, wool, 5, 3, 2, box);
			setBlockState(world, cross,5, 3, 3, box);
			setBlockState(world, wool, 5, 3, 4, box);
			setBlockState(world, wool, 2, 1, 5, box);//backwall
			setBlockState(world, wool, 2, 2, 5, box);
			setBlockState(world, wool, 2, 3, 5, box);
			setBlockState(world, cross,3, 1, 6, box);
			setBlockState(world, cross,3, 2, 6, box);
			setBlockState(world, cross,3, 3, 6, box);
			setBlockState(world, wool, 4, 1, 5, box);
			setBlockState(world, wool, 4, 2, 5, box);
			setBlockState(world, wool, 4, 3, 5, box);
			//door
			setBlockState(world, wool, 2, 3, 1, box);
			setBlockState(world, wool, 4, 3, 1, box);
			//top bit
			setBlockState(world, cross,1, 4, 3, box);//layer 1
			setBlockState(world, cross,5, 4, 3, box);
			setBlockState(world, cross,3, 4, 5, box);
			setBlockState(world, cross,3, 4, 1, box);
			setBlockState(world, wool, 2, 4, 2, box);
			setBlockState(world, wool, 2, 4, 4, box);
			setBlockState(world, wool, 4, 4, 2, box);
			setBlockState(world, wool, 4, 4, 4, box);
			setBlockState(world, cross,2, 5, 3, box);//layer 2
			setBlockState(world, cross,3, 5, 2, box);
			setBlockState(world, cross,3, 5, 3, box);
			setBlockState(world, cross,3, 5, 4, box);
			setBlockState(world, cross,4, 5, 3, box);
			//main pole
			setBlockState(world, fence, 3, 1, 1, box);//door
			setBlockState(world, fence, 3, 2, 1, box);
			setBlockState(world, fence, 3, 3, 1, box);
			setBlockState(world, fence, 3, 3, 5, box);//top frame
			setBlockState(world, fence, 2, 4, 3, box);
			setBlockState(world, fence, 3, 4, 2, box);
			setBlockState(world, fence, 3, 4, 3, box);
			setBlockState(world, fence, 3, 4, 4, box);
			setBlockState(world, fence, 4, 4, 3, box);
			setBlockState(world, fence, 6, 1, 3, box);//guide ropes
			setBlockState(world, fence, 6, 2, 3, box);
			setBlockState(world, fence, 0, 1, 3, box);
			setBlockState(world, fence, 0, 2, 3, box);
			setBlockState(world, fence, 2, 1, 6, box);
			setBlockState(world, fence, 2, 2, 6, box);
			setBlockState(world, fence, 4, 1, 6, box);
			setBlockState(world, fence, 4, 2, 6, box);
		}
	}

	public static class Tent extends AbstractTent{
    	public Tent(BlockPos pos, Direction _dir, ChunkGenerator<?> _gen) {
			super(PIECE_TENT,pos,_dir,_gen);
		}
    	public Tent(TemplateManager manager, CompoundNBT nbt) {
			super(PIECE_TENT, nbt);
		}
		/*-------------------------------------------generation----------------------------------------------*/
        @Override public boolean create(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox box, ChunkPos chunkPos){
        	buildTent(world,box,Blocks.GREEN_WOOL.getDefaultState(),rand);
        	//inside
        	BlockState bedFoot = Blocks.BROWN_BED.getDefaultState().with(BedBlock.PART, BedPart.FOOT);
        	BlockState bedHead = Blocks.BROWN_BED.getDefaultState().with(BedBlock.PART, BedPart.HEAD);
        	setBlockState(world, bedFoot, 2, 1, 3, box);
        	setBlockState(world, bedHead, 2, 1, 4, box);
        	setBlockState(world, bedFoot, 4, 1, 3, box);
        	setBlockState(world, bedHead, 4, 1, 4, box);
        	generateChest(world, box, rand, 3, 1, 4, LootTables.CHESTS_PILLAGER_OUTPOST);
        	return true;
        }
	}
	public static class CaptainsTent extends AbstractTent{
		public CaptainsTent(BlockPos pos, Direction _dir, ChunkGenerator<?> _gen) {
			super(PIECE_CAPTAIN,pos,_dir,_gen);
		}
    	public CaptainsTent(TemplateManager manager, CompoundNBT nbt) {
			super(PIECE_CAPTAIN, nbt);
		}
		/*-------------------------------------------generation----------------------------------------------*/
        @Override public boolean create(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox box, ChunkPos chunkPos){
        	buildTent(world,box,Blocks.LIGHT_GRAY_WOOL.getDefaultState(),rand);
        	setBlockState(world, Blocks.WHITE_BED.getDefaultState().with(BedBlock.PART, BedPart.FOOT), 2, 1, 3, box);
        	setBlockState(world, Blocks.WHITE_BED.getDefaultState().with(BedBlock.PART, BedPart.HEAD), 2, 1, 4, box);
        	setBlockState(world, Blocks.OAK_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.SOUTH), 4, 1, 3, box);
        	setBlockState(world, Blocks.OAK_STAIRS.getDefaultState().with(StairsBlock.HALF, Half.TOP), 4, 1, 4, box);
        	setBlockState(world, LANTERN, 4, 2, 4, box);
        	generateChest(world, box, rand, 3, 1, 4, LootTables.CHESTS_PILLAGER_OUTPOST);
        	return true;
        }
	}
	public static class UtilitiesTent extends AbstractTent{
		public UtilitiesTent(BlockPos pos, Direction _dir, ChunkGenerator<?> _gen) {
			super(PIECE_UTILITY,pos,_dir,_gen);
		}
    	public UtilitiesTent(TemplateManager manager, CompoundNBT nbt) {
			super(PIECE_UTILITY, nbt);
		}
		/*-------------------------------------------generation----------------------------------------------*/
        @Override public boolean create(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox box, ChunkPos chunkPos){
        	buildTent(world,box,Blocks.GRAY_WOOL.getDefaultState(),rand);
        	//inside
        	BlockState bedFoot = Blocks.BROWN_BED.getDefaultState().with(BedBlock.PART, BedPart.FOOT);
        	BlockState bedHead = Blocks.BROWN_BED.getDefaultState().with(BedBlock.PART, BedPart.HEAD);
        	setBlockState(world, bedFoot, 2, 1, 3, box);
        	setBlockState(world, bedHead, 2, 1, 4, box);
        	setBlockState(world, bedFoot, 4, 1, 3, box);
        	setBlockState(world, bedHead, 4, 1, 4, box);
        	generateChest(world, box, rand, 3, 1, 4, LootTables.CHESTS_PILLAGER_OUTPOST);
        	return true;
        }
	}
	public static class StoreTent extends AbstractTent{
		public StoreTent(BlockPos pos, Direction _dir, ChunkGenerator<?> _gen) {
			super(PIECE_STORE,pos,_dir,_gen);
		}
    	public StoreTent(TemplateManager manager, CompoundNBT nbt) {
			super(PIECE_STORE, nbt);
		}
		/*-------------------------------------------generation----------------------------------------------*/
		private ResourceLocation randTable(Random rand){
			switch (rand.nextInt(5)){
				case 0: return LootTables.CHESTS_VILLAGE_VILLAGE_FLETCHER;
				case 1: return LootTables.CHESTS_VILLAGE_VILLAGE_FISHER;
				case 2: return LootTables.CHESTS_VILLAGE_VILLAGE_PLAINS_HOUSE;
				case 3: return LootTables.CHESTS_VILLAGE_VILLAGE_TEMPLE;
				default: return LootTables.CHESTS_VILLAGE_VILLAGE_CARTOGRAPHER;
			}
		}
		private void generateRandomChest(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox box, int x, int y, int z){
			for (int i =1;i<=3;i++){
				switch(rand.nextInt(4)){
					case 0:
					case 1: generateBarrel(world,box,rand,2,1,4,randTable(rand),Direction.random(rand)); break;
					case 2: generateChest(world, box, rand, 3, 1, 4, LootTables.CHESTS_IGLOO_CHEST);
					default: return;
				}
			}
		}
        @Override public boolean create(IWorld world, ChunkGenerator<?> gen, Random rand, MutableBoundingBox box, ChunkPos chunkPos){
        	buildUtilTent(world,box,Blocks.LIGHT_GRAY_WOOL.getDefaultState(),Blocks.LIGHT_GRAY_WOOL.getDefaultState(),rand);
			generateRandomChest(world,gen,rand,box,2,1,2);
			generateRandomChest(world,gen,rand,box,2,1,3);
			generateRandomChest(world,gen,rand,box,2,1,4);
			generateRandomChest(world,gen,rand,box,3,1,5);
			generateRandomChest(world,gen,rand,box,4,1,4);
			generateRandomChest(world,gen,rand,box,4,1,3);
        	return true;
        }
	}
	public static class PlanningTent extends AbstractTent{
		public PlanningTent(BlockPos pos, Direction _dir, ChunkGenerator<?> _gen) {
			super(PIECE_PLAN,pos,_dir,_gen);
		}
    	public PlanningTent(TemplateManager manager, CompoundNBT nbt) {
			super(PIECE_PLAN, nbt);
		}
		/*-------------------------------------------generation----------------------------------------------*/
        @Override public boolean create(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox box, ChunkPos chunkPos){
        	buildUtilTent(world,box,Blocks.RED_WOOL.getDefaultState(),Blocks.YELLOW_WOOL.getDefaultState(),rand);
        	setBlockState(world, Blocks.OAK_STAIRS.getDefaultState().with(StairsBlock.HALF, Half.TOP), 3, 1, 5, box);
        	setBlockState(world, LANTERN, 3, 2, 5, box);
        	setBlockState(world, Blocks.CARTOGRAPHY_TABLE.getDefaultState(), 3, 1, 3, box);
        	placeWallFactionBanner(world,box,4, 3, 3, Direction.WEST, Faction.ROHAN);
        	return true;
        }
	}
	public static class KitchenTent extends AbstractTent{
		public KitchenTent(BlockPos pos, Direction _dir, ChunkGenerator<?> _gen) {
			super(PIECE_KITCHEN,pos,_dir,_gen);
		}
    	public KitchenTent(TemplateManager manager, CompoundNBT nbt) {
			super(PIECE_KITCHEN, nbt);
		}
		/*-------------------------------------------generation----------------------------------------------*/
        @Override public boolean create(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox box, ChunkPos chunkPos){
        	BlockState stairs = Blocks.OAK_STAIRS.getDefaultState().with(StairsBlock.HALF, Half.TOP).with(StairsBlock.FACING, Direction.WEST);
        	buildUtilTent(world,box,Blocks.WHITE_WOOL.getDefaultState(),Blocks.WHITE_WOOL.getDefaultState(),rand);
        	setBlockState(world, stairs, 2, 1, 2, box);
        	setBlockState(world, stairs, 2, 1, 3, box);
        	setBlockState(world, stairs, 2, 1, 4, box);
        	placeRandomCake(world, box, rand, 2, 2, 2);
        	setBlockState(world, Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE.getDefaultState(), 2, 2, 3, box);
        	setBlockState(world, LANTERN, 2, 2, 4, box);
        	setBlockState(world, Blocks.CRAFTING_TABLE.getDefaultState(), 3, 1, 5, box);
        	setBlockState(world, Blocks.CAULDRON.getDefaultState().with(CauldronBlock.LEVEL, Integer.valueOf(2)), 4, 1, 4, box);
        	generateChest(world, box, rand, 4, 1, 3, LootTables.CHESTS_SHIPWRECK_SUPPLY);
        	generateItemFrame(world, 4, 2, 3, Direction.WEST, ModItems.IRON_KNIFE.get());
        	return true;
        }
	}
	public static class Infirmary extends AbstractTent{
		public Infirmary(BlockPos pos, Direction _dir, ChunkGenerator<?> _gen) {
			super(PIECE_MEDIC,pos,_dir,_gen);
		}
    	public Infirmary(TemplateManager manager, CompoundNBT nbt) {
			super(PIECE_MEDIC, nbt);
		}
		/*-------------------------------------------generation----------------------------------------------*/
        @Override public boolean create(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox box, ChunkPos chunkPos){
        	BlockState stairs = Blocks.OAK_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.WEST);
        	buildUtilTent(world,box,Blocks.WHITE_WOOL.getDefaultState(),Blocks.RED_WOOL.getDefaultState(),rand);
        	setBlockState(world, Blocks.CRAFTING_TABLE.getDefaultState(), 2, 1, 2, box);
        	setBlockState(world, Blocks.BREWING_STAND.getDefaultState(),  2, 2, 2, box);
        	setBlockState(world, stairs, 2, 1, 3, box);
        	setBlockState(world, Blocks.FLOWER_POT.getDefaultState(), 2, 2, 4, box);
        	setBlockState(world, stairs.with(StairsBlock.HALF, Half.TOP), 2, 1, 4, box);
        	setBlockState(world, Blocks.CAULDRON.getDefaultState().with(CauldronBlock.LEVEL, Integer.valueOf(1)), 3, 1, 5, box);
        	setBlockState(world, Blocks.WHITE_BED.getDefaultState().with(BedBlock.PART, BedPart.FOOT), 4, 1, 3, box);
        	setBlockState(world, Blocks.WHITE_BED.getDefaultState().with(BedBlock.PART, BedPart.HEAD), 4, 1, 4, box);
        	setBlockState(world, LANTERN.with(LanternBlock.HANGING, Boolean.valueOf(true)), 3, 3, 3, box);
        	generateChest(world, box, rand, 2, 1, 1, LootTables.CHESTS_IGLOO_CHEST);
        	return true;
        }
	}
	
	/*---------------------------------------------SINGLES-------------------------------------------------*/
	static final IStructurePieceType PIECE_SMITHY = IStructurePieceType.register(Smithy::new,"RoFCSM");
	static final IStructurePieceType PIECE_MELEE = IStructurePieceType.register(MeleeArena::new,"RoFCAM");
	static final IStructurePieceType PIECE_PEN = IStructurePieceType.register(AnimalPen::new,"RoFCPN");
	static final IStructurePieceType PIECE_STABLE = IStructurePieceType.register(Stables::new,"RoFCS");
	static final IStructurePieceType PIECE_FIRE = IStructurePieceType.register(CampFire::new,"RoFCF");

	public static class Smithy extends AbstractTent {
    	public Smithy(BlockPos pos, Direction _dir, ChunkGenerator<?> _gen) {
			super(PIECE_SMITHY,pos,_dir,_gen);
		}
    	public Smithy(TemplateManager manager, CompoundNBT nbt) {
			super(PIECE_SMITHY, nbt);
		}
		/*-------------------------------------------generation----------------------------------------------*/
		BlockState COBBLE_WALL = Blocks.COBBLESTONE_WALL.getDefaultState();
		@Override public boolean create(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox box, ChunkPos chunkPos){
        	BlockState anvil =Blocks.ANVIL.getDefaultState().with(AnvilBlock.FACING, Direction.EAST);
        	for (int x=1;x<=5;x++) for (int z=1;z<=5;z++) replaceAirAndLiquidDownwards(world, DIRT, x, 0, z, box);
        	for (int x=0;x<6;x++) for (int z=0;z<6;z++) setBlockState(world, AIR, x, 1, z, box);
        	//tools
        	if (rand.nextBoolean()) 
        		setBlockState(world, anvil, 2, 1, 1, box);
        	else
        		setBlockState(world, anvil, 3, 1, 3, box);
        	setBlockState(world, Blocks.CAULDRON.getDefaultState().with(CauldronBlock.LEVEL, 3), 1, 1, 2, box);
        	setBlockState(world, Blocks.BIRCH_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.EAST).with(StairsBlock.HALF, Half.TOP), 6, 1, 2, box);
        	setBlockState(world, Blocks.FLETCHING_TABLE.getDefaultState(),6, 1, 1, box);
        	//furnace
        	setBlockState(world, COBBLE_SLAB, 1, 2, 4, box);
        	setBlockState(world, COBBLE_WALL, 1, 1, 3, box);
        	setBlockState(world, COBBLE_WALL, 1, 1, 5, box);
        	setBlockState(world, COBBLE_WALL, 0, 1, 4, box);
        	setBlockState(world, Blocks.BLAST_FURNACE.getDefaultState().with(BlastFurnaceBlock.FACING, Direction.EAST), 1, 1, 4, box);
        	//table
        	setBlockState(world, Blocks.SMITHING_TABLE.getDefaultState(), 2, 1, 6, box);
        	setBlockState(world, Blocks.CRAFTING_TABLE.getDefaultState(), 3, 1, 6, box);
        	setBlockState(world, ModBlocks.ROHAN_WORKBENCH.get().getDefaultState(), 4, 1, 6, box);
        	setBlockState(world, Blocks.BRICKS.getDefaultState(), 5, 1, 6, box);
        	setBlockState(world, LANTERN, 5, 2, 6, box);
        	setBlockState(world, Blocks.GRINDSTONE.getDefaultState().with(GrindstoneBlock.HORIZONTAL_FACING, Direction.SOUTH).with(GrindstoneBlock.FACE, AttachFace.WALL), 5, 1, 5, box);
        	return true;
        }
	}
	public static class MeleeArena extends AbstractTent{
    	public MeleeArena(BlockPos pos, Direction _dir, ChunkGenerator<?> _gen) {
			super(PIECE_MELEE,pos,_dir,_gen);
		}
    	public MeleeArena(TemplateManager manager, CompoundNBT nbt) {
			super(PIECE_MELEE, nbt);
		}
		/*-------------------------------------------generation----------------------------------------------*/
		@Override public boolean create(IWorld world, ChunkGenerator<?> _gen, Random rand, MutableBoundingBox box, ChunkPos chunkPos){
        	for (int x=1;x<=5;x++) for (int z=3;z<=6;z++) replaceAirAndLiquidDownwards(world, DIRT, x, 0, z, box);
        	generateArmourStand(world,0,getDepthAt(_gen,0,2),2);
        	generateArmourStand(world,1,getDepthAt(_gen,1,1),1);
        	generateChest(world, box, rand, 5, getDepthAt(_gen,5,2), 2, LootTables.CHESTS_VILLAGE_VILLAGE_WEAPONSMITH);
        	setBlockState(world, Blocks.OAK_FENCE_GATE.getDefaultState(), 3, 1, 3, box);
        	fillWithBlocks(world,AIR, 1,0,4, 5,0,5, box);
			//fence
        	setBlockState(world, FENCE, 2, 1, 3, box);
        	setBlockState(world, FENCE, 1, 1, 3, box);
        	setBlockState(world, FENCE, 1, 1, 4, box);
        	setBlockState(world, FENCE, 1, 1, 5, box);
        	setBlockState(world, FENCE, 1, 1, 6, box);
        	setBlockState(world, FENCE, 2, 1, 6, box);
        	setBlockState(world, FENCE, 3, 1, 6, box);
        	setBlockState(world, FENCE, 4, 1, 6, box);
        	setBlockState(world, FENCE, 5, 1, 6, box);
        	setBlockState(world, FENCE, 5, 1, 5, box);
        	setBlockState(world, FENCE, 5, 1, 4, box);
        	setBlockState(world, FENCE, 5, 1, 3, box);
        	setBlockState(world, FENCE, 4, 1, 3, box);
        	return true;
        }
	}
	public static class AnimalPen extends AbstractTent{
    	public AnimalPen(BlockPos pos, Direction _dir, ChunkGenerator<?> _gen) {
			super(PIECE_PEN,pos,_dir,_gen);
		}
    	public AnimalPen(TemplateManager manager, CompoundNBT nbt) {
			super(PIECE_PEN, nbt);
		}
    	static EntityType<? extends AnimalEntity> getRandomAnimalType(Random rand) {
    		switch(rand.nextInt(3)) {
    		case 0:
    			return EntityType.PIG;
    		case 1:
    			return EntityType.COW;
    		case 2:
    			return EntityType.CHICKEN;
    		default:
    			return EntityType.PIG;
    		}
    	}
		/*-------------------------------------------generation----------------------------------------------*/
		@Override public boolean create(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox box, ChunkPos chunkPos){
        	
        	setBlockState(world, GATE, 3, 1, 0, box);
			fillWithBlocks(world,AIR, 1,1,1, 5,1,5, box);
			
        	setBlockState(world, FENCE, 2, 1, 0, box);
			setBlockState(world, FENCE, 1, 1, 0, box);
			setBlockState(world, FENCE, 0, 1, 0, box);// post1
			setBlockState(world, FENCE, 0, 1, 1, box);
			setBlockState(world, FENCE, 0, 1, 2, box);
			setBlockState(world, FENCE, 0, 1, 3, box);
			setBlockState(world, FENCE, 0, 1, 4, box);
			setBlockState(world, FENCE, 0, 1, 5, box);
			setBlockState(world, FENCE, 0, 1, 6, box);// post2
			setBlockState(world, FENCE, 1, 1, 6, box);
			setBlockState(world, FENCE, 2, 1, 6, box);
			setBlockState(world, FENCE, 3, 1, 6, box);
			setBlockState(world, FENCE, 4, 1, 6, box);
			setBlockState(world, FENCE, 5, 1, 6, box);
			setBlockState(world, FENCE, 6, 1, 6, box);// post3
			setBlockState(world, FENCE, 6, 1, 5, box);
			setBlockState(world, FENCE, 6, 1, 4, box);
			setBlockState(world, FENCE, 6, 1, 3, box);
			setBlockState(world, FENCE, 6, 1, 2, box);
			setBlockState(world, FENCE, 6, 1, 1, box);
			setBlockState(world, FENCE, 6, 1, 0, box);// post4
			setBlockState(world, FENCE, 5, 1, 0, box);
			setBlockState(world, FENCE, 4, 1, 0, box);
        	spawnEntity(world,box,getRandomAnimalType(rand),2,1,2);
        	return true;
        }
	}
	public static class Stables extends AbstractTent{
    	public Stables(BlockPos pos, Direction _dir, ChunkGenerator<?> _gen) {
			super(PIECE_STABLE,pos,_dir,_gen);
		}
    	public Stables(TemplateManager manager, CompoundNBT nbt) {
			super(PIECE_STABLE, nbt);
		}
		/*-------------------------------------------generation----------------------------------------------*/
		@Override public boolean create(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox box, ChunkPos chunkPos){
        	//build
        	for (int x=1;x<=5;x++) for (int z=1;z<=6;z++) replaceAirAndLiquidDownwards(world, DIRT, x, 0, z, box);
        	setBlockState(world, Blocks.HAY_BLOCK.getDefaultState().with(HayBlock.AXIS, Direction.Axis.random(rand)), 1, 1, 4, box);
        	setBlockState(world, Blocks.CAULDRON.getDefaultState().with(CauldronBlock.LEVEL, Integer.valueOf(rand.nextInt(2)+1)), 2, 1, 4, box);
        	setBlockState(world, Blocks.CAULDRON.getDefaultState().with(CauldronBlock.LEVEL, Integer.valueOf(rand.nextInt(2)+1)), 4, 1, 4, box);
        	setBlockState(world, Blocks.HAY_BLOCK.getDefaultState().with(HayBlock.AXIS, Direction.Axis.random(rand)), 5, 1, 4, box);
        	setBlockState(world, GATE, 1, 1, 1, box);
        	setBlockState(world, GATE, 2, 1, 1, box);
        	setBlockState(world, GATE, 4, 1, 1, box);
        	setBlockState(world, GATE, 5, 1, 1, box);
        	//air
        	setBlockState(world, AIR, 1, 1, 0, box);
        	setBlockState(world, AIR, 2, 1, 0, box);
        	setBlockState(world, AIR, 4, 1, 0, box);
        	setBlockState(world, AIR, 5, 1, 0, box);
        	setBlockState(world, AIR, 1, 1, 2, box);
        	setBlockState(world, AIR, 2, 1, 2, box);
        	setBlockState(world, AIR, 1, 1, 3, box);
        	setBlockState(world, AIR, 2, 1, 3, box);
        	setBlockState(world, AIR, 4, 1, 2, box);
        	setBlockState(world, AIR, 5, 1, 2, box);
        	setBlockState(world, AIR, 4, 1, 3, box);
        	setBlockState(world, AIR, 5, 1, 3, box);
        	setBlockState(world, AIR, 1, 2, 2, box);
        	setBlockState(world, AIR, 2, 2, 2, box);
        	setBlockState(world, AIR, 1, 2, 3, box);
        	setBlockState(world, AIR, 2, 2, 3, box);
        	setBlockState(world, AIR, 4, 2, 2, box);
        	setBlockState(world, AIR, 5, 2, 2, box);
        	setBlockState(world, AIR, 4, 2, 3, box);
        	setBlockState(world, AIR, 5, 2, 3, box);
        	//wall
        	setBlockState(world, FENCE, 0, 1, 2, box);//side
        	setBlockState(world, FENCE, 0, 1, 3, box);
        	setBlockState(world, FENCE, 0, 1, 4, box);
        	setBlockState(world, FENCE, 3, 1, 2, box);
        	setBlockState(world, FENCE, 3, 1, 3, box);
        	setBlockState(world, FENCE, 3, 1, 4, box);
        	setBlockState(world, FENCE, 6, 1, 2, box);
        	setBlockState(world, FENCE, 6, 1, 3, box);
        	setBlockState(world, FENCE, 6, 1, 4, box);
        	setBlockState(world, FENCE, 1, 1, 5, box);//back
        	setBlockState(world, FENCE, 2, 1, 5, box);
        	setBlockState(world, FENCE, 4, 1, 5, box);
        	setBlockState(world, FENCE, 5, 1, 5, box);
        	//posts
        	setBlockState(world, FENCE, 0, 1, 1, box);//front
        	setBlockState(world, FENCE, 0, 2, 1, box);
        	setBlockState(world, FENCE, 0, 3, 1, box);
        	setBlockState(world, FENCE, 3, 1, 1, box);
        	setBlockState(world, FENCE, 3, 2, 1, box);
        	setBlockState(world, FENCE, 3, 3, 1, box);
        	setBlockState(world, FENCE, 6, 1, 1, box);
        	setBlockState(world, FENCE, 6, 2, 1, box);
        	setBlockState(world, FENCE, 6, 3, 1, box);
        	setBlockState(world, FENCE, 0, 1, 5, box);//back
        	setBlockState(world, FENCE, 0, 2, 5, box);
        	setBlockState(world, FENCE, 0, 3, 5, box);
        	setBlockState(world, FENCE, 3, 1, 5, box);
        	setBlockState(world, FENCE, 3, 2, 5, box);
        	setBlockState(world, FENCE, 3, 3, 5, box);
        	setBlockState(world, FENCE, 6, 1, 5, box);
        	setBlockState(world, FENCE, 6, 2, 5, box);
        	setBlockState(world, FENCE, 6, 3, 5, box);
			//roof
        	setBlockState(world, ROOF_SLAB, 0, 4, 1, box);
        	setBlockState(world, ROOF_STOP, 1, 3, 1, box);
        	setBlockState(world, ROOF_STOP, 2, 3, 1, box);
        	setBlockState(world, ROOF_SLAB, 3, 4, 1, box);
        	setBlockState(world, ROOF_STOP, 4, 3, 1, box);
        	setBlockState(world, ROOF_STOP, 5, 3, 1, box);
        	setBlockState(world, ROOF_SLAB, 6, 4, 1, box);
        	setBlockState(world, ROOF_STOP, 0, 3, 2, box);
        	setBlockState(world, ROOF_SLAB, 1, 4, 2, box);
        	setBlockState(world, ROOF_SLAB, 2, 4, 2, box);
        	setBlockState(world, ROOF_STOP, 3, 4, 2, box);
        	setBlockState(world, ROOF_SLAB, 4, 4, 2, box);
        	setBlockState(world, ROOF_SLAB, 5, 4, 2, box);
        	setBlockState(world, ROOF_STOP, 6, 3, 2, box);
        	setBlockState(world, ROOF_STOP, 0, 3, 3, box);
        	setBlockState(world, ROOF_SLAB, 1, 4, 3, box);
        	setBlockState(world, ROOF_STOP, 2, 4, 3, box);
        	setBlockState(world, ROOF_STOP, 3, 4, 3, box);
        	setBlockState(world, ROOF_STOP, 4, 4, 3, box);
        	setBlockState(world, ROOF_SLAB, 5, 4, 3, box);
        	setBlockState(world, ROOF_STOP, 6, 3, 3, box);
        	setBlockState(world, ROOF_STOP, 0, 3, 4, box);
        	setBlockState(world, ROOF_SLAB, 1, 4, 4, box);
        	setBlockState(world, ROOF_SLAB, 2, 4, 4, box);
        	setBlockState(world, ROOF_STOP, 3, 4, 4, box);
        	setBlockState(world, ROOF_SLAB, 4, 4, 4, box);
        	setBlockState(world, ROOF_SLAB, 5, 4, 4, box);
        	setBlockState(world, ROOF_STOP, 6, 3, 4, box);
        	setBlockState(world, ROOF_SLAB, 0, 4, 5, box);
        	setBlockState(world, ROOF_STOP, 1, 3, 5, box);
        	setBlockState(world, ROOF_STOP, 2, 3, 5, box);
        	setBlockState(world, ROOF_SLAB, 3, 4, 5, box);
        	setBlockState(world, ROOF_STOP, 4, 3, 5, box);
        	setBlockState(world, ROOF_STOP, 5, 3, 5, box);
        	setBlockState(world, ROOF_SLAB, 6, 4, 5, box);
        	//two horseys
        	return true;
        }
	}
	public static class CampFire extends AbstractTent{
    	public CampFire(BlockPos pos, Direction _dir, ChunkGenerator<?> _gen) {
			super(PIECE_FIRE,pos,_dir,_gen);
		}
    	public CampFire(TemplateManager manager, CompoundNBT nbt) {
			super(PIECE_FIRE, nbt);
		}
		/*-------------------------------------------generation----------------------------------------------*/
		BlockState BARS = Blocks.IRON_BARS.getDefaultState();
		BlockState FIRE = Blocks.CAMPFIRE.getDefaultState();
		BlockState STICKS = ModBlocks.STICK_BLOCK.get().getDefaultState();
		@Override public boolean create(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox box, ChunkPos chunkPos){
        	if(rand.nextBoolean()) {
        		for (int x=1;x<=5;x++) for (int z=1;z<=5;z++) replaceAirAndLiquidDownwards(world, DIRT, x, 0, z, box);
            	setBlockState(world, STICKS.with(LogBlock.AXIS, Direction.Axis.random(rand)), 6, 1, 6, box);
            	if (rand.nextBoolean())setBlockState(world, STICKS.with(RotatedPillarBlock.AXIS, Direction.Axis.random(rand)), 7, 1, 6, box);
            	if (rand.nextBoolean())setBlockState(world, STICKS.with(RotatedPillarBlock.AXIS, Direction.Axis.random(rand)), 6, 1, 7, box);
            	if (rand.nextBoolean())setBlockState(world, STICKS.with(RotatedPillarBlock.AXIS, Direction.Axis.random(rand)), 6, 2, 6, box);
        		//fire
        		setBlockState(world, Blocks.COBBLESTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.WEST), 2, 1, 3, box);
        		setBlockState(world, Blocks.COBBLESTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.EAST), 4, 1, 3, box);
        		setBlockState(world, FIRE, 3, 1, 3, box);
        		setBlockState(world, COBBLE_SLAB, 3, 1, 2, box);
        		setBlockState(world, COBBLE_SLAB, 3, 1, 4, box);
        		setBlockState(world, Blocks.CAULDRON.getDefaultState().with(CauldronBlock.LEVEL, Integer.valueOf(rand.nextInt(3))), 3, 2, 3, box);
        		setBlockState(world, BARS, 2, 2, 3, box);
        		setBlockState(world, BARS, 4, 2, 3, box);
        		//logs
				WALL.selectBlocks(rand, 0, 1, 2, false);;
				BlockState log0 = WALL.getBlockState().with(LogBlock.AXIS, Direction.Axis.Z);
				WALL.selectBlocks(rand, 6, 1, 2, false);;
				BlockState log1 = WALL.getBlockState().with(LogBlock.AXIS, Direction.Axis.Z);
				WALL.selectBlocks(rand, 2, 1, 6, false);;
				BlockState log2 = WALL.getBlockState().with(LogBlock.AXIS, Direction.Axis.X);
        		setBlockState(world, log0, 0, 1, 2, box);
            	setBlockState(world, log0, 0, 1, 3, box);
            	setBlockState(world, log0, 0, 1, 4, box);
        		setBlockState(world, log1, 6, 1, 2, box);
            	setBlockState(world, log1, 6, 1, 3, box);
            	setBlockState(world, log1, 6, 1, 4, box);
        		setBlockState(world, log2, 2, 1, 6, box);
            	setBlockState(world, log2, 3, 1, 6, box);
            	setBlockState(world, log2, 4, 1, 6, box);
        	}else {
        		fillWithBlocks(world, box, 0, 0, 0, 5, 0, 5, DIRT,DIRT, false);
            	setBlockState(world, STICKS.with(LogBlock.AXIS, Direction.Axis.random(rand)), 6, 1, 6, box);
            	if (rand.nextBoolean())setBlockState(world, STICKS.with(RotatedPillarBlock.AXIS, Direction.Axis.random(rand)), 7, 1, 6, box);
            	if (rand.nextBoolean())setBlockState(world, STICKS.with(RotatedPillarBlock.AXIS, Direction.Axis.random(rand)), 6, 1, 7, box);
            	if (rand.nextBoolean())setBlockState(world, STICKS.with(RotatedPillarBlock.AXIS, Direction.Axis.random(rand)), 6, 2, 6, box);
        		//fire
        		setBlockState(world, Blocks.COBBLESTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.WEST), 1, 1, 3, box);
        		setBlockState(world, Blocks.COBBLESTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.EAST), 4, 1, 3, box);
        		setBlockState(world, COBBLE_SLAB, 1, 1, 2, box);
        		setBlockState(world, COBBLE_SLAB, 4, 1, 2, box);
        		setBlockState(world, FIRE, 2, 1, 2, box);
        		setBlockState(world, FIRE, 3, 1, 2, box);
        		setBlockState(world, FIRE, 2, 1, 3, box);
        		setBlockState(world, FIRE, 3, 1, 3, box);
        		setBlockState(world, COBBLE_SLAB, 2, 1, 1, box);
        		setBlockState(world, COBBLE_SLAB, 3, 1, 1, box);
        		setBlockState(world, COBBLE_SLAB, 2, 1, 4, box);
        		setBlockState(world, COBBLE_SLAB, 3, 1, 4, box);
        		setBlockState(world, Blocks.CAULDRON.getDefaultState().with(CauldronBlock.LEVEL, Integer.valueOf(rand.nextInt(3))), 2, 2, 3, box);
        		setBlockState(world, BARS, 1, 2, 3, box);
        		setBlockState(world, BARS, 3, 2, 3, box);
        		setBlockState(world, BARS, 4, 2, 3, box);
        		//logs
				WALL.selectBlocks(rand, 0, 1, 2, false);;
				BlockState log0 = WALL.getBlockState().with(LogBlock.AXIS, Direction.Axis.Z);
				WALL.selectBlocks(rand, 6, 1, 2, false);;
				BlockState log1 = WALL.getBlockState().with(LogBlock.AXIS, Direction.Axis.Z);
        		setBlockState(world, log0, 6, 1, 1, box);
        		setBlockState(world, log0, 6, 1, 2, box);
            	setBlockState(world, log0, 6, 1, 3, box);
            	setBlockState(world, log0, 6, 1, 4, box);
        		setBlockState(world, log1, 1, 1, 6, box);
        		setBlockState(world, log1, 2, 1, 6, box);
            	setBlockState(world, log1, 3, 1, 6, box);
            	setBlockState(world, log1, 4, 1, 6, box);
				WALL.selectBlocks(rand, -1, 1, 2, false);
        		setBlockState(world, WALL.getBlockState(), -1, 1, 2, box);
				WALL.selectBlocks(rand, -1, 1, 2, false);
            	setBlockState(world, WALL.getBlockState(), -1, 1, 4, box);
        	}return true;
        }
	}
	
	/*---------------------------------------------DOUBLES-------------------------------------------------*/
	static final IStructurePieceType PIECE_CAVALLARY = IStructurePieceType.register(CavallaryArena::new,"RoFCAC");
	static final IStructurePieceType PIECE_ARCHERY = IStructurePieceType.register(ArcheryRange::new,"RoFCAA");
	static final IStructurePieceType PIECE_MESS_AREA = IStructurePieceType.register(EatingArea::new,"RoFCEA");

	public static class CavallaryArena extends ModStructurePiece{
    	public CavallaryArena(BlockPos pos, Direction _dir, ChunkGenerator<?> _gen) {
			super(PIECE_CAVALLARY, 0);
			int depth = _gen.func_222529_a(pos.getX()+1, pos.getZ()+1, Heightmap.Type.OCEAN_FLOOR_WG)-1;
			boundingBox = ModMathFunctions.getDirectedBox(pos.getX(), depth, pos.getZ(), _dir, 15, 7, 7);
			setCoordBaseMode(_dir);
		}
		/*---------------------------------------------data-------------------------------------------------*/
		public CavallaryArena(TemplateManager manager, CompoundNBT nbt) {
			super(PIECE_CAVALLARY, nbt);
		}
		@Override protected void readAdditional(CompoundNBT compound) {}
		
		/*-------------------------------------------generation----------------------------------------------*/
		@Override public boolean create(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox box, ChunkPos chunkPos){
        	for (int x=1;x<=5;x++) for (int z=14;z<=5;z++) replaceAirAndLiquidDownwards(world, DIRT, x, 0, z, box);
        	for (int i = 4;i<=11;i++) setBlockState(world, FENCE, i, 1, 3, box);
        	for (int i = 1;i<=14;i++) setBlockState(world, FENCE, i, 1, 6, box);
        	for (int i = 1;i<=5 ;i++) { 
        		setBlockState(world, FENCE, 0, 1, i, box);
        		setBlockState(world, FENCE, 15,1, i, box);
        		setBlockState(world, FENCE, i, 1, 0, box);
        		setBlockState(world, FENCE,i+9,1, 0, box);
        	}
    		setBlockState(world, FENCE, 1, 1, 1, box);
    		setBlockState(world, FENCE, 1, 1, 5, box);
    		setBlockState(world, FENCE, 14,1, 1, box);
    		setBlockState(world, FENCE, 14,1, 5, box);
        	return true;
        }
	}
	public static class ArcheryRange extends ModStructurePiece{
    	public ArcheryRange(BlockPos pos, Direction _dir, ChunkGenerator<?> _gen) {
			super(PIECE_ARCHERY, 0);
			int depth = _gen.func_222529_a(pos.getX()+1, pos.getZ()+1, Heightmap.Type.OCEAN_FLOOR_WG)-1;
			boundingBox = ModMathFunctions.getDirectedBox(pos.getX(), depth, pos.getZ(), _dir, 15, 7, 7);
			setCoordBaseMode(_dir);
		}
		/*---------------------------------------------data-------------------------------------------------*/
		public ArcheryRange(TemplateManager manager, CompoundNBT nbt) {
			super(PIECE_ARCHERY, nbt);
		}
		@Override protected void readAdditional(CompoundNBT compound) {}
		
		/*-------------------------------------------generation----------------------------------------------*/
		BlockState TARGET = Blocks.WHITE_WOOL.getDefaultState();
		BlockState MARK = Blocks.WHITE_CARPET.getDefaultState();
		@Override public boolean create(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox box, ChunkPos chunkPos){
        	for (int x=0;x<=15;x++) for (int z=0;z<=7;z++) replaceAirAndLiquidDownwards(world, DIRT, x, 0, z, box);
        	//fences
        	for (int i = 0;i<=13;i++) setBlockState(world, FENCE, i, 1, 6, box);
        	for (int i = 1;i<=5 ;i++) setBlockState(world, FENCE, 0, 1, i, box);
        	for (int i = 3;i<=13;i++) { 
        		setBlockState(world, FENCE, i, 1, 2, box);
        		setBlockState(world, FENCE, i, 1, 4, box);
        		setBlockState(world, AIR, i, 1, 1, box);
        		setBlockState(world, AIR, i, 1, 3, box);
        		setBlockState(world, AIR, i, 1, 5, box);
        		setBlockState(world, AIR, i, 2, 1, box);
        		setBlockState(world, AIR, i, 2, 3, box);
        		setBlockState(world, AIR, i, 2, 5, box);
        	}
        	setBlockState(world, FENCE, 1, 1, 0, box);
        	setBlockState(world, FENCE, 1, 1, 1, box);
        	//posts
        	setBlockState(world, MARK, 2, 1, 1, box);
        	setBlockState(world, MARK, 2, 1, 3, box);
        	setBlockState(world, MARK, 2, 1, 5, box);
        	setBlockState(world, FENCE,14, 1, 1, box);
        	setBlockState(world, FENCE,14, 1, 3, box);
        	setBlockState(world, FENCE,14, 1, 5, box);
        	setBlockState(world, TARGET, 14, 2, 1, box);
        	setBlockState(world, TARGET, 14, 2, 3, box);
        	setBlockState(world, TARGET, 14, 2, 5, box);
        	//wall
        	for (int i = 3;i<=14;i++) {
        		WALL.selectBlocks(rand, i,0,0, false);
        		BlockState log =  WALL.getBlockState();
				setBlockState(world, log, i, 0, 0, box);
				setBlockState(world, log, i, 1, 0, box);
            	setBlockState(world, log, i, 2, 0, box);
            	if (i%2==1) setBlockState(world, log, i, 3, 0, box);
        	}; for (int i = 0;i<=6 ;i++) {
        		WALL.selectBlocks(rand, i,0,0, false);
        		BlockState log =  WALL.getBlockState();
				setBlockState(world, log, 15, 0, i, box);
				setBlockState(world, log, 15, 1, i, box);
            	setBlockState(world, log, 15, 2, i, box);
            	setBlockState(world, log, 15, 3, i, box);
            	if (i%2==1) setBlockState(world, log, 15, 4, i, box);
        	};
        	return true;
        }
	}
	public static class EatingArea extends ModStructurePiece{
		public EatingArea(BlockPos pos, Direction _dir, ChunkGenerator<?> _gen) {
			super(PIECE_MESS_AREA, 0);
			int depth = _gen.func_222529_a(pos.getX()+1, pos.getZ()+1, Heightmap.Type.OCEAN_FLOOR_WG)-1;
			boundingBox = ModMathFunctions.getDirectedBox(pos.getX(), depth, pos.getZ(), _dir, 15, 7, 7);
			setCoordBaseMode(_dir);
		}
		/*---------------------------------------------data-------------------------------------------------*/
		public EatingArea(TemplateManager manager, CompoundNBT nbt) {
			super(PIECE_MESS_AREA, nbt);
		}
		@Override protected void readAdditional(CompoundNBT compound) {}
		
		/*-------------------------------------------generation----------------------------------------------*/
		@Override public void placeRandomCake(IWorld world, MutableBoundingBox box, Random rand, int x, int y, int z) {
	    	Block cake;
	    	switch (rand.nextInt(7)) {
	    	case 0:
	    		cake = ModBlocks.BEEF_PIE.get(); break;
	    	case 1:
	    		cake = ModBlocks.PORK_PIE.get(); break;
	    	case 2:
	    		cake = ModBlocks.MUTTON_PIE.get(); break;
	    	case 3:
	    		cake = ModBlocks.RABBIT_PIE.get(); break;
	    	case 4:
	    		cake = ModBlocks.FISH_PIE.get(); break;
	    	case 5:
	    		cake = ModBlocks.SALMON_PIE.get(); break;
	    	case 6:
	    		cake = ModBlocks.CHICKEN_PIE.get(); break;
	    	case 7:
	    		cake = ModBlocks.VENISON_PIE.get(); break;
	    	default:
	    		cake = Blocks.CAKE;
	    	}
	    	setBlockState(world, cake.getDefaultState().with(CakeBlock.BITES, rand.nextInt(6)),x, y, z, box);
        }

        static final BlockState CHAIRN = Blocks.OAK_STAIRS.getDefaultState();
		static final BlockState CHAIRS = Blocks.OAK_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.SOUTH);
		static final BlockState TABLEN = Blocks.OAK_STAIRS.getDefaultState().with(StairsBlock.HALF, Half.TOP);
		static final BlockState TABLES = Blocks.OAK_STAIRS.getDefaultState().with(StairsBlock.HALF, Half.TOP).with(StairsBlock.FACING, Direction.SOUTH);
		static final BlockState TABLE_END1 = Blocks.OAK_STAIRS.getDefaultState().with(StairsBlock.HALF, Half.TOP).with(StairsBlock.FACING, Direction.WEST).with(StairsBlock.SHAPE, StairsShape.OUTER_LEFT);
		static final BlockState TABLE_END2 = Blocks.OAK_STAIRS.getDefaultState().with(StairsBlock.HALF, Half.TOP).with(StairsBlock.FACING, Direction.WEST).with(StairsBlock.SHAPE, StairsShape.OUTER_RIGHT);
		static final BlockState TABLE_END3 = Blocks.OAK_STAIRS.getDefaultState().with(StairsBlock.HALF, Half.TOP).with(StairsBlock.FACING, Direction.EAST).with(StairsBlock.SHAPE, StairsShape.OUTER_LEFT);
		static final BlockState TABLE_END4 = Blocks.OAK_STAIRS.getDefaultState().with(StairsBlock.HALF, Half.TOP).with(StairsBlock.FACING, Direction.EAST).with(StairsBlock.SHAPE, StairsShape.OUTER_RIGHT);
		static final BlockState MUG = Blocks.FLOWER_POT.getDefaultState();
		static final BlockState PLATE = Blocks.SPRUCE_PRESSURE_PLATE.getDefaultState();
		static final BlockState TAP = Blocks.TRIPWIRE_HOOK.getDefaultState().with(TripWireHookBlock.FACING, Direction.SOUTH);
		@Override public boolean create(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox box, ChunkPos chunkPos){
        	for (int x=1;x<=15;x++) for (int z=1;z<=7;z++) replaceAirAndLiquidDownwards(world, DIRT, x, 0, z, box);
        	//main table
    		setBlockState(world, TABLE_END1, 2, 1, 1, box);
    		setBlockState(world, TABLE_END2, 2, 1, 2, box);
    		setBlockState(world, TABLEN, 3, 1, 1, box);
    		setBlockState(world, TABLEN, 4, 1, 1, box);
    		setBlockState(world, TABLEN, 5, 1, 1, box);
    		setBlockState(world, TABLEN, 6, 1, 1, box);
    		setBlockState(world, TABLEN, 7, 1, 1, box);
    		setBlockState(world, TABLES, 3, 1, 2, box);
    		setBlockState(world, TABLES, 4, 1, 2, box);
    		setBlockState(world, TABLES, 5, 1, 2, box);
    		setBlockState(world, TABLES, 6, 1, 2, box);
    		setBlockState(world, TABLES, 7, 1, 2, box);
    		setBlockState(world, TABLEN, 11, 1, 1, box);
    		setBlockState(world, TABLEN, 12, 1, 1, box);
    		setBlockState(world, TABLEN, 13, 1, 1, box);
    		setBlockState(world, TABLES, 11, 1, 2, box);
    		setBlockState(world, TABLES, 12, 1, 2, box);
    		setBlockState(world, TABLES, 13, 1, 2, box);
    		setBlockState(world, TABLE_END3, 14, 1, 2, box);
    		setBlockState(world, TABLE_END4, 14, 1, 1, box);
        	//main chairs
    		setBlockState(world, CHAIRS, 3, 1, 0, box);
    		setBlockState(world, CHAIRS, 5, 1, 0, box);
    		setBlockState(world, CHAIRS, 7, 1, 0, box);
    		setBlockState(world, CHAIRS, 11, 1, 0, box);
    		setBlockState(world, CHAIRS, 13, 1, 0, box);
    		setBlockState(world, CHAIRN, 3, 1, 3, box);
    		setBlockState(world, CHAIRN, 5, 1, 3, box);
    		setBlockState(world, CHAIRN, 7, 1, 3, box);
    		setBlockState(world, CHAIRN, 11, 1, 3, box);
    		setBlockState(world, CHAIRN, 13, 1, 3, box);
    		//table top
    		setBlockState(world, MUG, 2, 2, 2, box);
    		setBlockState(world, PLATE, 3, 2, 1, box);
    		setBlockState(world, PLATE, 3, 2, 2, box);
    		setBlockState(world, MUG, 4, 2, 1, box);
    		setBlockState(world, LANTERN, 4, 2, 2, box);
    		setBlockState(world, PLATE, 5, 2, 1, box);
    		setBlockState(world, PLATE, 5, 2, 2, box);
    		setBlockState(world, MUG, 6, 2, 2, box);
    		setBlockState(world, PLATE, 7, 2, 1, box);
    		setBlockState(world, PLATE, 7, 2, 2, box);
    		setBlockState(world, PLATE, 11, 2, 1, box);
    		setBlockState(world, PLATE, 11, 2, 2, box);
    		setBlockState(world, MUG, 12, 2, 1, box);
    		setBlockState(world, MUG, 12, 2, 2, box);
    		setBlockState(world, PLATE, 13, 2, 1, box);
    		setBlockState(world, PLATE, 13, 2, 2, box);
    		setBlockState(world, MUG, 14, 2, 1, box);
        	//main gap
    		if (rand.nextBoolean()) {
        		setBlockState(world, TABLEN, 8, 1, 1, box);
        		setBlockState(world, TABLEN, 9, 1, 1, box);
        		setBlockState(world, TABLEN, 10,1, 1, box);
        		setBlockState(world, TABLES, 8, 1, 2, box);
        		setBlockState(world, TABLES, 9, 1, 2, box);
        		setBlockState(world, TABLES, 10,1, 2, box);
        		setBlockState(world, CHAIRS, 9, 1, 0, box);
        		setBlockState(world, CHAIRN, 9, 1, 3, box);
        		setBlockState(world, MUG, 	8, 2, 1, box);
        		setBlockState(world, MUG, 	8, 2, 2, box);
        		setBlockState(world, PLATE, 9, 2, 1, box);
        		setBlockState(world, PLATE, 9, 2, 2, box);
        		setBlockState(world, LANTERN,10,2, 1, box);
        		setBlockState(world, MUG, 	10,2, 2, box);
    		}else {
        		setBlockState(world, TABLE_END1, 8, 1, 1, box);
        		setBlockState(world, TABLE_END2, 8, 1, 2, box);
        		setBlockState(world, TABLE_END3, 10,1, 2, box);
        		setBlockState(world, TABLE_END4, 10,1, 1, box);
        		setBlockState(world, MUG, 	8 ,2, 2, box);
        		setBlockState(world, LANTERN,10,2, 1, box);
    		}
        	//second table
    		setBlockState(world, Blocks.CAULDRON.getDefaultState().with(CauldronBlock.LEVEL,Integer.valueOf(3)), 2, 1, 7, box);
    		setBlockState(world, TABLE_END1, 3, 1, 6, box);
    		setBlockState(world, TABLE_END2, 3, 1, 7, box);
    		setBlockState(world, TABLEN, 4, 1, 6, box);
    		setBlockState(world, TABLEN, 5, 1, 6, box);
    		setBlockState(world, TABLEN, 6, 1, 6, box);
    		setBlockState(world, TABLES, 4, 1, 7, box);
    		setBlockState(world, TABLES, 5, 1, 7, box);
    		setBlockState(world, TABLES, 6, 1, 7, box);
    		setBlockState(world, TABLEN, 10, 1, 6, box);
    		setBlockState(world, TABLEN, 11, 1, 6, box);
    		setBlockState(world, TABLEN, 12, 1, 6, box);
    		setBlockState(world, TABLEN, 13, 1, 6, box);
    		setBlockState(world, TABLES, 10, 1, 7, box);
    		setBlockState(world, TABLES, 11, 1, 7, box);
    		setBlockState(world, TABLES, 12, 1, 7, box);
    		setBlockState(world, TABLES, 13, 1, 7, box);
    		setBlockState(world, TABLE_END3, 14, 1, 7, box);
    		setBlockState(world, TABLE_END4, 14, 1, 6, box);
        	//second chairs
    		if (rand.nextInt(3)==1) {
    			setBlockState(world, CHAIRS, 4, 1, 5, box);
    			setBlockState(world, PLATE, 4, 2, 6, box);
    		}
    		if (rand.nextInt(3)==1) {
    			setBlockState(world, CHAIRS, 6, 1, 5, box);
    			setBlockState(world, PLATE, 6, 2, 6, box);
    		}else
    			setBlockState(world, MUG, 6, 2, 6, box);
    		if (rand.nextInt(3)==1) {
    			setBlockState(world, CHAIRS, 12, 1, 5, box);
    		}
    		if (rand.nextInt(5)==1) {
    			setBlockState(world, CHAIRS, 14, 1, 5, box);
    			setBlockState(world, PLATE, 14, 2, 6, box);
    		}
    		//second top
    		setBlockState(world, MUG, 3, 2, 6, box);
    		placeRandomCake(world,box,rand,4, 2, 7);
    		setBlockState(world, TAP, 5, 2, 6, box);
    		generateBarrel(world,box,rand,5, 2, 7,LootTables.CHESTS_VILLAGE_VILLAGE_TANNERY,Direction.SOUTH);
    		setBlockState(world, MUG, 6, 2, 6, box);
    		placeRandomCake(world,box,rand,6, 2, 7);
    		setBlockState(world, TAP, 10, 2, 6, box);
    		generateBarrel(world,box,rand,10, 2, 7,LootTables.CHESTS_VILLAGE_VILLAGE_TANNERY,Direction.SOUTH);
    		setBlockState(world, TAP, 11, 2, 6, box);
    		generateBarrel(world,box,rand,11, 2, 7,LootTables.CHESTS_VILLAGE_VILLAGE_TANNERY,Direction.SOUTH);
    		placeRandomCake(world,box,rand,12, 2, 7);
			setBlockState(world, PLATE, 12, 2, 6, box);
    		setBlockState(world, MUG, 13, 2, 6, box);
    		placeRandomCake(world,box,rand, 13, 2, 7);
    		setBlockState(world, MUG, 14, 2, 7, box);
        	//second gap
    		if (rand.nextBoolean()) {
    			setBlockState(world, TABLEN, 7, 1, 6, box);
    			setBlockState(world, TABLEN, 8, 1, 6, box);
    			setBlockState(world, TABLEN, 9, 1, 6, box);
    			setBlockState(world, TABLES, 7, 1, 7, box);
    			setBlockState(world, TABLES, 8, 1, 7, box);
    			setBlockState(world, TABLES, 9, 1, 7, box);
    			setBlockState(world, LANTERN,7, 2, 6, box);
        		generateBarrel(world,box,rand,8, 2, 7,LootTables.CHESTS_VILLAGE_VILLAGE_TANNERY,Direction.SOUTH);
    			setBlockState(world, TAP,	 8, 2, 6, box);
    			placeRandomCake(world,box,rand,9, 2, 6);
    			placeRandomCake(world,box,rand,7, 2, 7);
    			placeRandomCake(world,box,rand,9, 2, 7);
    		}else{
    			setBlockState(world, TABLE_END1, 7, 1, 6, box);
    			setBlockState(world, TABLE_END2, 7, 1, 7, box);
    			setBlockState(world, TABLE_END4, 9, 1, 6, box);
    			setBlockState(world, TABLE_END3, 9, 1, 7, box);
    			setBlockState(world, LANTERN,	7, 2, 7, box);
    			setBlockState(world, MUG,	 	9, 2, 6, box);
    			placeRandomCake(world,box,rand,9, 2, 7);
    		}return true;
        }
	}
	
	/*---------------------------------------------SMALL-------------------------------------------------*/
	static final IStructurePieceType PIECE_TOWER = IStructurePieceType.register(WatchTower::new,"RoFCWT");
	static final IStructurePieceType PIECE_WELL = IStructurePieceType.register(Well::new,"RoFCWL");
	static final IStructurePieceType PIECE_HAYBALE = IStructurePieceType.register(HayBale::new,"RoFCHB");
	static final IStructurePieceType PIECE_CRATE = IStructurePieceType.register(Crates::new,"RoFCCR");
	static final IStructurePieceType PIECE_FARM = IStructurePieceType.register(Farm::new,"RoFCFR");
	static final IStructurePieceType PIECE_LOGS = IStructurePieceType.register(LogPile::new,"RoFCLS");

	public static class WatchTower extends ModStructurePiece {
		int height;

		public WatchTower(BlockPos pos, Direction _dir, ChunkGenerator<?> _gen, Random rand) {
			super(PIECE_TOWER, 0);
			height = rand.nextInt(4)+7;
			setCoordBaseMode(_dir);
			int depth = _gen.func_222529_a(pos.getX()+1, pos.getZ()+1, Heightmap.Type.OCEAN_FLOOR_WG)-1;
			boundingBox = ModMathFunctions.getDirectedBox(pos.getX(), depth, pos.getZ(), _dir, 2, height+3, 6);
		}

		/*---------------------------------------------data-------------------------------------------------*/
		public WatchTower(TemplateManager manager, CompoundNBT nbt) {
			super(PIECE_TOWER, nbt);
			height=nbt.getInt("Hte");
		}
		@Override protected void readAdditional(CompoundNBT nbt) {
			nbt.putInt("Hte",height);
		}
		
		/*-------------------------------------------generation----------------------------------------------*/
		static final BlockState OAK_SLAB = Blocks.OAK_SLAB.getDefaultState().with(SlabBlock.TYPE,SlabType.TOP);
		static final BlockState LADDER = Blocks.LADDER.getDefaultState().with(LadderBlock.FACING, Direction.SOUTH);
		@Override public boolean create(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox box, ChunkPos chunkPos){
			setBlockState(world, AIR, 1, 1, 0, box);
			setBlockState(world, AIR, 1, 1, 1, box);
			setBlockState(world, AIR, 1, 1, 2, box);
			setBlockState(world, AIR, 1, 1, 3, box);
			setBlockState(world, AIR, 1, 2, 0, box);
			setBlockState(world, AIR, 1, 2, 1, box);
			setBlockState(world, AIR, 1, 2, 2, box);
			setBlockState(world, AIR, 1, 2, 3, box);
			//blocks
        	for (int x=0;x<=2;x++) for (int z=2;z<=5;z++) replaceAirAndLiquidDownwards(world, DIRT, x, 0, z, box);
        	int y  = height-1;
    		int y0 = height+1;
    		int y1 = height+2;
    		int y2 = height+3;
        	//pole
			setBlockState(world, LOG,1, 0, 5, box);
			setBlockState(world, LOG,1, -1, 5, box);
			setBlockState(world, LOG,1, -2, 5, box);
        	for (int i = 1; i <= height; i++) {
        		setBlockState(world, LOG,	 1, i, 5, box);
        		setBlockState(world, LADDER, 1, i, 4, box);
        	}
    		setBlockState(world, LOG, 1, y0, 5, box);
    		setBlockState(world, LOG, 1, y1, 5, box);
    		setBlockState(world, LOGZ, 1, y1, 4, box);
    		setBlockState(world, LOGX, 0, y1, 5, box);
    		setBlockState(world, LOGX, 2, y1, 5, box);
        	//cabin
    		setBlockState(world, LOGZ, 1, y, 6, box);
    		setBlockState(world, LOGX, 0, y, 5, box);
    		setBlockState(world, LOGX, 2, y, 5, box);
    		setBlockState(world, OAK_SLAB, 0, y, 4, box);
    		setBlockState(world, OAK_SLAB, 2, y, 4, box);
    		setBlockState(world, OAK_SLAB, 0, y, 6, box);
    		setBlockState(world, OAK_SLAB, 2, y, 6, box);
    		setBlockState(world, FENCE,-1, y0, 5, box);//posts
    		setBlockState(world, FENCE, 3, y0, 5, box);
    		setBlockState(world, FENCE, 1, y0, 3, box);
    		setBlockState(world, FENCE,-1, y, 5, box);
    		setBlockState(world, FENCE, 3, y, 5, box);
    		setBlockState(world, FENCE, 1, y, 7, box);
        	//roof
    		setBlockState(world, ROOF_SLAB,-1, y1, 4, box);
    		setBlockState(world, ROOF,-1, y1, 5, box);
    		setBlockState(world, ROOF_SLAB,-1, y1, 6, box);
    		setBlockState(world, ROOF_SLAB, 0, y1, 3, box);
    		setBlockState(world, ROOF_STOP, 0, y1, 4, box);
    		setBlockState(world, ROOF_SLAB, 0, y2, 5, box);
    		setBlockState(world, ROOF_STOP, 0, y1, 6, box);
    		setBlockState(world, ROOF_SLAB, 0, y1, 7, box);
    		setBlockState(world, ROOF, 1, y1, 3, box);
    		setBlockState(world, ROOF_SLAB, 1, y2, 4, box);
    		setBlockState(world, ROOF_SLAB, 1, y2, 5, box);
    		setBlockState(world, ROOF_SLAB, 1, y2, 6, box);
    		setBlockState(world, ROOF_STOP, 1, y1, 7, box);
    		setBlockState(world, ROOF_SLAB, 2, y1, 3, box);
    		setBlockState(world, ROOF_STOP, 2, y1, 4, box);
    		setBlockState(world, ROOF_SLAB, 2, y2, 5, box);
    		setBlockState(world, ROOF_STOP, 2, y1, 6, box);
    		setBlockState(world, ROOF_SLAB, 2, y1, 7, box);
    		setBlockState(world, ROOF_SLAB, 3, y1, 4, box);
    		setBlockState(world, ROOF, 3, y1, 5, box);
    		setBlockState(world, ROOF_SLAB, 3, y1, 6, box);
        	//wall
    		setBlockState(world, FENCE,-1, height, 3, box);//p
    		setBlockState(world, FENCE, 0, height, 3, box);
    		setBlockState(world, FENCE, 1, height, 3, box);
    		setBlockState(world, FENCE, 2, height, 3, box);
    		setBlockState(world, FENCE, 3, height, 3, box);//p
    		setBlockState(world, FENCE, 3, height, 4, box);
    		setBlockState(world, FENCE, 3, height, 5, box);
    		setBlockState(world, FENCE, 3, height, 6, box);
    		setBlockState(world, FENCE, 3, height, 7, box);//p
    		setBlockState(world, FENCE, 2, height, 7, box);
    		setBlockState(world, FENCE, 1, height, 7, box);
    		setBlockState(world, FENCE, 0, height, 7, box);
    		setBlockState(world, FENCE,-1, height, 7, box);//p
    		setBlockState(world, FENCE,-1, height, 6, box);
    		setBlockState(world, FENCE,-1, height, 5, box);
    		setBlockState(world, FENCE,-1, height, 4, box);
        	//misc
    		setBlockState(world, FENCE, 0, 1, 5, box);
    		setBlockState(world, FENCE, 2, 1, 5, box);
    		setBlockState(world, FENCE, 1, 1, 6, box);
    		setBlockState(world, Blocks.WALL_TORCH.getDefaultState(), 1, y0, 6, box);
    		setBlockState(world, Blocks.OAK_FENCE_GATE.getDefaultState().with(FenceGateBlock.OPEN, true), 1, y1, 6, box);
    		generateBarrel(world,box,rand,2,1,4,LootTables.CHESTS_VILLAGE_VILLAGE_FLETCHER,Direction.random(rand));
        	return true;
        }
	}
	public static class Well extends ModStructurePiece {
		public Well(BlockPos pos, Direction _dir, ChunkGenerator<?> _gen) {
			super(PIECE_WELL, 0);
			int depth = _gen.func_222529_a(pos.getX()+1, pos.getZ()+1, Heightmap.Type.OCEAN_FLOOR_WG)-1;
			boundingBox = ModMathFunctions.getDirectedBox(pos.getX(), depth, pos.getZ(), _dir, 2, 7, 6);
			setCoordBaseMode(_dir);
		}
		/*---------------------------------------------data-------------------------------------------------*/
		public Well(TemplateManager manager, CompoundNBT nbt) {
			super(PIECE_WELL, nbt);
		}
		@Override protected void readAdditional(CompoundNBT compound) {}
		
		/*-------------------------------------------generation----------------------------------------------*/
		static final BlockState COBBLE = Blocks.COBBLESTONE.getDefaultState();
		@Override public boolean create(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox box, ChunkPos chunkPos){
        	//base
        	setBlockState(world, DIRT, 0, 0, 2, box);
        	setBlockState(world, COBBLE, 1, 0, 2, box);
        	setBlockState(world, DIRT, 2, 0, 2, box);
        	setBlockState(world, COBBLE, 0, 0, 3, box);
        	setBlockState(world, COBBLE, 2, 0, 3, box);
        	setBlockState(world, DIRT, 0, 0, 4, box);
        	setBlockState(world, COBBLE, 1, 0, 4, box);
        	setBlockState(world, DIRT, 2, 0, 4, box);
        	setBlockState(world, COBBLE_SLAB, 1, 1, 2, box);
        	setBlockState(world, COBBLE_SLAB, 0, 1, 3, box);
        	setBlockState(world, COBBLE_SLAB, 2, 1, 3, box);
        	setBlockState(world, COBBLE_SLAB, 1, 1, 4, box);
        	//posts
        	setBlockState(world, FENCE, 0, 1, 2, box);
        	setBlockState(world, FENCE, 0, 2, 2, box);
        	setBlockState(world, FENCE, 2, 1, 2, box);
        	setBlockState(world, FENCE, 2, 2, 2, box);
        	setBlockState(world, FENCE, 0, 1, 4, box);
        	setBlockState(world, FENCE, 0, 2, 4, box);
        	setBlockState(world, FENCE, 2, 1, 4, box);
        	setBlockState(world, FENCE, 2, 2, 4, box);
        	//roof
        	setBlockState(world, ROOF_SLAB, 0, 3, 2, box);
        	setBlockState(world, ROOF_SLAB, 1, 3, 2, box);
        	setBlockState(world, ROOF_SLAB, 2, 3, 2, box);
        	setBlockState(world, ROOF_SLAB, 0, 3, 3, box);
        	setBlockState(world, ROOF_SLAB.with(SlabBlock.TYPE, SlabType.TOP), 1, 3, 3, box);
        	setBlockState(world, ROOF_SLAB, 2, 3, 3, box);
        	setBlockState(world, ROOF_SLAB, 0, 3, 4, box);
        	setBlockState(world, ROOF_SLAB, 1, 3, 4, box);
        	setBlockState(world, ROOF_SLAB, 2, 3, 4, box);
        	for (int i=0;i>=-5;i--){
            	setBlockState(world, WATER, 1, i, 3, box);
				setBlockState(world, COBBLE, 1, i, 3, box);
				setBlockState(world, COBBLE, 1, i, 3, box);
				setBlockState(world, COBBLE, 1, i, 3, box);
				setBlockState(world, COBBLE, 1, i, 3, box);
        	}
        	return true;
        }
	}
	public static class HayBale extends ModStructurePiece {
		public HayBale(BlockPos pos, Direction _dir, ChunkGenerator<?> _gen) {
			super(PIECE_HAYBALE, 0);
			int depth = _gen.func_222529_a(pos.getX()+1, pos.getZ()+1, Heightmap.Type.OCEAN_FLOOR_WG)-1;
			boundingBox = ModMathFunctions.getDirectedBox(pos.getX(), depth, pos.getZ(), _dir, 2, 7, 6);
			setCoordBaseMode(_dir);
		}
		/*---------------------------------------------data-------------------------------------------------*/
		public HayBale(TemplateManager manager, CompoundNBT nbt) {
			super(PIECE_HAYBALE, nbt);
		}
		@Override protected void readAdditional(CompoundNBT compound) {}
		
		/*-------------------------------------------generation----------------------------------------------*/
		public void placeHayBaleBase(IWorld world, MutableBoundingBox box, Random rand, int x, int y, int z) {
			BlockState bale = (rand.nextInt(3)!=0?Blocks.HAY_BLOCK:ModBlocks.STICK_BLOCK.get()).getDefaultState().with(RotatedPillarBlock.AXIS, Direction.Axis.random(rand));
			if (rand.nextInt(4)!=0)
        	setBlockState(world, bale,x, y, z, box);
        }
		public void placeHayBale(IWorld world, MutableBoundingBox box, Random rand, int x, int y, int z, int chance) {
			BlockState bale = (rand.nextInt(3)!=0?Blocks.HAY_BLOCK:ModBlocks.STICK_BLOCK.get()).getDefaultState().with(RotatedPillarBlock.AXIS, Direction.Axis.random(rand));
			if (rand.nextInt(chance)!= 0 && !getBlockStateFromPos(world, x, y - 1, z, box).isAir())
	        	setBlockState(world, bale,x, y, z, box);
        }
		@Override public boolean create(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox box, ChunkPos chunkPos){
			//layer 1
			placeHayBaleBase(world, box, rand, 0, 1, 2);
			placeHayBaleBase(world, box, rand, 1, 1, 2);
			placeHayBaleBase(world, box, rand, 2, 1, 2);
			placeHayBaleBase(world, box, rand, 0, 1, 3);//*
			placeHayBaleBase(world, box, rand, 1, 1, 3);
			placeHayBaleBase(world, box, rand, 2, 1, 3);
			placeHayBaleBase(world, box, rand, 0, 1, 4);
			placeHayBaleBase(world, box, rand, 1, 1, 4);
			placeHayBaleBase(world, box, rand, 2, 1, 4);
			placeHayBaleBase(world, box, rand, 0, 1, 5);
			placeHayBaleBase(world, box, rand, 1, 1, 5);
			placeHayBaleBase(world, box, rand, 2, 1, 5);//*
			placeHayBaleBase(world, box, rand, 0, 1, 6);
			placeHayBaleBase(world, box, rand, 1, 1, 6);
			placeHayBaleBase(world, box, rand, 2, 1, 6);
			//layer 2
			placeHayBale(world, box, rand, 0, 2, 2, 2);
			placeHayBale(world, box, rand, 1, 2, 2, 2);
			placeHayBale(world, box, rand, 2, 2, 2, 2);
			placeHayBale(world, box, rand, 0, 2, 3, 3);
			placeHayBale(world, box, rand, 1, 2, 3, 3);
			placeHayBale(world, box, rand, 2, 2, 3, 3);
			placeHayBale(world, box, rand, 0, 2, 4, 3);
			placeHayBale(world, box, rand, 1, 2, 4, 3);
			placeHayBale(world, box, rand, 2, 2, 4, 3);
			placeHayBale(world, box, rand, 0, 2, 5, 3);
			placeHayBale(world, box, rand, 1, 2, 5, 3);
			placeHayBale(world, box, rand, 2, 2, 5, 3);
			placeHayBale(world, box, rand, 0, 2, 6, 2);
			placeHayBale(world, box, rand, 1, 2, 6, 2);
			placeHayBale(world, box, rand, 2, 2, 6, 2);
			//layer 3
			placeHayBale(world, box, rand, 0, 3, 3, 3);
			placeHayBale(world, box, rand, 1, 3, 3, 3);
			placeHayBale(world, box, rand, 2, 3, 3, 3);
			placeHayBale(world, box, rand, 0, 3, 4, 3);
			placeHayBale(world, box, rand, 1, 3, 4, 3);
			placeHayBale(world, box, rand, 2, 3, 4, 3);
			placeHayBale(world, box, rand, 0, 3, 5, 3);
			placeHayBale(world, box, rand, 1, 3, 5, 3);
			placeHayBale(world, box, rand, 2, 3, 5, 3);
        	return true;
        }
	}
	public static class Crates extends ModStructurePiece {
		public Crates(BlockPos pos, Direction _dir, ChunkGenerator<?> _gen) {
			super(PIECE_CRATE, 0);
			int depth = _gen.func_222529_a(pos.getX()+1, pos.getZ()+1, Heightmap.Type.OCEAN_FLOOR_WG)-1;
			boundingBox = ModMathFunctions.getDirectedBox(pos.getX(), depth, pos.getZ(), _dir, 2, 7, 6);
			setCoordBaseMode(_dir);
		}
		/*---------------------------------------------data-------------------------------------------------*/
		public Crates(TemplateManager manager, CompoundNBT nbt) {
			super(PIECE_CRATE, nbt);
		}
		@Override protected void readAdditional(CompoundNBT compound) {}
		
		/*-------------------------------------------generation----------------------------------------------*/
		public void placeHayBaleBase(IWorld world, MutableBoundingBox box, Random rand, int x, int y, int z) {
			BlockState bale = (rand.nextInt(3)!=0?Blocks.HAY_BLOCK:ModBlocks.STICK_BLOCK.get()).getDefaultState().with(RotatedPillarBlock.AXIS, Direction.Axis.random(rand));
			if (rand.nextInt(4)!=0)
        	setBlockState(world, bale,x, y, z, box);
        }
		@SuppressWarnings("deprecation")
		public void placeHayBale(IWorld world, MutableBoundingBox box, Random rand, int x, int y, int z, int chance) {
			BlockState bale = (rand.nextInt(3)!=0?Blocks.HAY_BLOCK:ModBlocks.STICK_BLOCK.get()).getDefaultState().with(RotatedPillarBlock.AXIS, Direction.Axis.random(rand));
			if (rand.nextInt(chance)!= 0 && !getBlockStateFromPos(world, x,y-1,z, box).isAir())
	        	setBlockState(world, bale,x, y, z, box);
        }
		@Override public boolean create(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox box, ChunkPos chunkPos){
			//layer 1
			//generate a bunch of chests and barrels
        	return true;
        }
	}
	public static class Farm extends ModStructurePiece {
		public Farm(BlockPos pos, Direction _dir, ChunkGenerator<?> _gen) {
			super(PIECE_FARM, 0);
			int depth = _gen.func_222529_a(pos.getX()+1, pos.getZ()+1, Heightmap.Type.OCEAN_FLOOR_WG)-1;
			boundingBox = ModMathFunctions.getDirectedBox(pos.getX(), depth, pos.getZ(), _dir, 2, 7, 6);
			setCoordBaseMode(_dir);
		}

		/*---------------------------------------------data-------------------------------------------------*/
		public Farm(TemplateManager manager, CompoundNBT nbt) {
			super(PIECE_FARM, nbt);
		}
		@Override protected void readAdditional(CompoundNBT compound) {}
		
		/*-------------------------------------------generation----------------------------------------------*/
		static final BlockState FARMLAND = Blocks.FARMLAND.getDefaultState().with(FarmlandBlock.MOISTURE, 7);
		@Override public boolean create(IWorld world, ChunkGenerator<?> _gen, Random rand, MutableBoundingBox box, ChunkPos chunkPos){
        	CROP.selectBlocks(rand, 0, 0, 0, false);
        	BlockState crop = CROP.getBlockState();
        	for (int x=0;x<=2;x++)
        		for (int z=2;z<=6;z++) {
        			setBlockState(world, FARMLAND, x, 0, z, box);
        			setBlockState(world, crop, x, 1, z, box);
        		}
        	setBlockState(world, WATER, 1, 0, 3, box);
        	setBlockState(world, AIR, 1, 1, 3, box);
        	return true;
        }
	}
	public static class LogPile extends ModStructurePiece {
		public LogPile(BlockPos pos, Direction _dir, ChunkGenerator<?> _gen) {
			super(PIECE_LOGS, 0);
			int depth = _gen.func_222529_a(pos.getX()+1, pos.getZ()+1, Heightmap.Type.OCEAN_FLOOR_WG)-1;
			boundingBox = ModMathFunctions.getDirectedBox(pos.getX(), depth, pos.getZ(), _dir, 2, 7, 6);
			setCoordBaseMode(_dir);
		}
		/*---------------------------------------------data-------------------------------------------------*/
		public LogPile(TemplateManager manager, CompoundNBT nbt) {
			super(PIECE_LOGS, nbt);
		}
		@Override protected void readAdditional(CompoundNBT compound) {}
		
		/*-------------------------------------------generation----------------------------------------------*/
		static final BlockState RAIL = Blocks.RAIL.getDefaultState().with(RailBlock.SHAPE, RailShape.EAST_WEST);
		static final BlockState RAILL = Blocks.RAIL.getDefaultState().with(RailBlock.SHAPE, RailShape.ASCENDING_EAST);
		static final BlockState RAILR = Blocks.RAIL.getDefaultState().with(RailBlock.SHAPE, RailShape.ASCENDING_WEST);
		@Override public boolean create(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox box, ChunkPos chunkPos){
        	WALL.selectBlocks(rand, 0, 0, 0, false);
        	BlockState log = WALL.getBlockState().with(LogBlock.AXIS, Direction.Axis.Z);
        	//build
        	setBlockState(world, log, 1, 1, 0, box);
        	setBlockState(world, log, 1, 1, 6, box);
        	for (int i =1; i<=5; i++) {
            	setBlockState(world, log, 0, 1, i, box);
            	setBlockState(world, log, 1, 1, i, box);
            	setBlockState(world, log, 1, 2, i, box);
            	setBlockState(world, log, 2, 1, i, box);
        	}
        	setBlockState(world, RAILL,-1, 1, 2, box);
        	setBlockState(world, RAILL, 0, 2, 2, box);
        	setBlockState(world, RAIL, 1, 3, 2, box);
        	setBlockState(world, RAILR, 2, 2, 2, box);
        	setBlockState(world, RAILR, 3, 1, 2, box);
        	setBlockState(world, RAILL,-1, 1, 4, box);
        	setBlockState(world, RAILL, 0, 2, 4, box);
        	setBlockState(world, RAIL, 1, 3, 4, box);
        	setBlockState(world, RAILR, 2, 2, 4, box);
        	setBlockState(world, RAILR, 3, 1, 4, box);
        	return true;
        }
	}
}