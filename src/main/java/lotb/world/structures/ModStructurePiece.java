package lotb.world.structures;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import com.mojang.datafixers.util.Pair;

import lotb.entities.npc.AbstractNpcEntity;
import lotb.lore.Faction;
import lotb.registries.ModBlocks;
import lotb.world.structures.pieces.RohanFortCampPieces.Path;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.BarrelTileEntity;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructurePiece;

public abstract class ModStructurePiece extends StructurePiece{
	static final IStructurePieceType PIECE_TYPE 	= getRegistry();
	protected static IStructurePieceType getRegistry() {
		return IStructurePieceType.register(Path::new,"RFCP");
	}
	public ModStructurePiece(IStructurePieceType structurePierceTypeIn, int type) {
		super(structurePierceTypeIn, type);
	}
	public ModStructurePiece(IStructurePieceType structurePierceTypeIn, CompoundNBT nbt) {
		super(structurePierceTypeIn, nbt);
	}
	/*---------------------------------------------GENERATION-------------------------------------------------*/
	public int getDepthAt(ChunkGenerator<?> _gen, int x, int z) {
		return ( _gen.func_222529_a(getXWithOffset(x,z), getZWithOffset(x,z), Heightmap.Type.OCEAN_FLOOR_WG))-boundingBox.minY;
	}
	
	public void overlayBlock(IWorld _world, ChunkGenerator<?> _gen, BlockState block, int x, int y, int z, MutableBoundingBox box) {
		int depth = ( _gen.func_222529_a(getXWithOffset(x,z), getZWithOffset(x,z), Heightmap.Type.OCEAN_FLOOR_WG))-boundingBox.minY;
		setBlockState(_world,block,x,depth+y,z,box);
	}
	public void setRandomBlock(IWorld _world, Random rand, BlockSelector block, int x, int y, int z, MutableBoundingBox box) {
		block.selectBlocks(rand, x,y,z, false);
        setBlockState(_world,block.getBlockState(),x,y,z,box);
	}
	public void fillDownBlockRandom(IWorld _world, Random rand,BlockSelector block, int x, int y, int z, MutableBoundingBox box) {
		block.selectBlocks(rand, x,y,z, false);
		replaceAirAndLiquidDownwards(_world,block.getBlockState(),x,y,z,box);
	}
	public void overlayBlockRandom(IWorld _world, ChunkGenerator<?> _gen, Random rand,BlockSelector block, int x, int y, int z, MutableBoundingBox box) {
		block.selectBlocks(rand, x,y,z, false);
        overlayBlock(_world,_gen,block.getBlockState(),x,y,z,box);
	}
	
	public void fillWithBlocks(IWorld _world, BlockState block, int minx, int miny, int minz, int maxx, int maxy, int maxz, MutableBoundingBox box) {
		for (int x = minx; x <= maxx; x++)
			for (int y = miny;y<=maxy;y++)
				for (int z = minz;z<=maxz;z++)
					setBlockState(_world,block,x,y,z,box);
	}
	
	/*---------------------------------------------PLACEMENTS-------------------------------------------------*/
	public void placeRandomCake(IWorld world, MutableBoundingBox box, Random rand, int x, int y, int z) {
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
    	setBlockState(world, cake.getDefaultState(),x, y, z, box);
    }

    public void placeFactionBanner(IWorld world, MutableBoundingBox box, int x, int y, int z, int rot, Faction faction){
		setBlockState(world, Blocks.GREEN_BANNER.getDefaultState().with(BannerBlock.ROTATION, rot), x,y,z, box);
	}
	public void placeWallFactionBanner(IWorld world, MutableBoundingBox box, int x, int y, int z, Direction dir, Faction faction){
		setBlockState(world, Blocks.GREEN_WALL_BANNER.getDefaultState().with(WallBannerBlock.HORIZONTAL_FACING, Direction.WEST), x,y,z, box);
	}

	protected void generateChest(IWorld worldIn, MutableBoundingBox boundsIn, Random rand, int x, int y, int z, ResourceLocation resourceLocationIn, BlockState state) {
		BlockPos blockpos = new BlockPos(this.getXWithOffset(x, z), this.getYWithOffset(y), this.getZWithOffset(x, z));
		if (boundsIn.isVecInside(blockpos) && worldIn.getBlockState(blockpos).getBlock() != Blocks.CHEST) {
			if (state == null) {
				state = correctFacing(worldIn, blockpos, Blocks.CHEST.getDefaultState());
			}
			worldIn.setBlockState(blockpos, state, 2);
			TileEntity tileentity = worldIn.getTileEntity(blockpos);
			if (tileentity instanceof ChestTileEntity) 
				((ChestTileEntity)tileentity).setLootTable(resourceLocationIn, rand.nextLong());
		}
	}
	protected void generateBarrel(IWorld worldIn, MutableBoundingBox boundsIn, Random rand, int x, int y, int z, ResourceLocation resourceLocationIn, Direction dir) {
		BlockPos pos = new BlockPos(getXWithOffset(x, z), getYWithOffset(y), getZWithOffset(x, z));
		if (boundsIn.isVecInside(pos) && worldIn.getBlockState(pos).getBlock() != Blocks.BARREL) {
			worldIn.setBlockState(pos, Blocks.BARREL.getDefaultState().with(BarrelBlock.PROPERTY_FACING,getRotation().rotate(dir)), 2);
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof BarrelTileEntity) 
				((BarrelTileEntity)tileentity).setLootTable(resourceLocationIn, rand.nextLong());
		}
	}
	
	protected void generateItemFrame(IWorld _world, int x, int y, int z, Direction dir, Item item) {
		BlockPos pos = new BlockPos(getXWithOffset(x, z),getYWithOffset(y), getZWithOffset(x, z));
		ItemFrameEntity itemframe = new ItemFrameEntity(_world.getWorld(), pos, getRotation().rotate(dir));
        itemframe.setDisplayedItemWithUpdate(new ItemStack(item), false);
        _world.addEntity(itemframe);	
	}
	protected void generateArmourStand(IWorld _world, int x, int y, int z) {
		ArmorStandEntity armourstand = new ArmorStandEntity(_world.getWorld(), getXWithOffset(x,z),getYWithOffset(y),getZWithOffset(x,z));
        _world.addEntity(armourstand);	
	}
	
	/*----------------------------------------------ENTITIES--------------------------------------------------*/
	protected void spawnEntity(IWorld _world, MutableBoundingBox box, EntityType<? extends MobEntity> entityType, int x1, int y1, int z1) {
		int x = this.getXWithOffset(x1, z1);
		int y = this.getYWithOffset(y1);
		int z = this.getZWithOffset(x1, z1);
		MobEntity entity = entityType.create(_world.getWorld());
		entity.setLocationAndAngles(x + 0.5D, y, z + 0.5D, 0.0F, 0.0F);
		entity.enablePersistence();
		entity.onInitialSpawn(_world, _world.getDifficultyForLocation(new BlockPos(entity)), SpawnReason.STRUCTURE, (ILivingEntityData)null, (CompoundNBT)null);
		_world.addEntity(entity);
	}
	protected void spawnNPC(IWorld _world, MutableBoundingBox box, EntityType<? extends AbstractNpcEntity> entityType, int x1, int y1, int z1) {
		int x = this.getXWithOffset(x1, z1);
		int y = this.getYWithOffset(y1);
		int z = this.getZWithOffset(x1, z1);
		AbstractNpcEntity entity = entityType.create(_world.getWorld());
		entity.setLocationAndAngles(x + 0.5D, y, z + 0.5D, 0.0F, 0.0F);
		entity.enablePersistence();
		entity.onInitialSpawn(_world, _world.getDifficultyForLocation(new BlockPos(entity)), SpawnReason.STRUCTURE, (ILivingEntityData)null, (CompoundNBT)null);
		_world.addEntity(entity);
	}
	
	/*-------------------------------------------BLOCK-SELECTOR-------------------------------------------------*/
	public static class RandomSelector extends StructurePiece.BlockSelector {
		BlockState[] pallate;
		@SafeVarargs
		public RandomSelector(Pair<Integer,BlockState>... blocks) {
			List<BlockState> pallateList = new ArrayList<BlockState>();
			for (Pair<Integer,BlockState> pair : blocks)
				for (int i =0; i<pair.getFirst(); i++)
					pallateList.add(pair.getSecond());
			pallate = pallateList.toArray(new BlockState[pallateList.size()]);
		}

		@Override
		public void selectBlocks(Random rand, int x, int y, int z, boolean wall) {
			this.blockstate = pallate[rand.nextInt(pallate.length)];
		}
	}
}
