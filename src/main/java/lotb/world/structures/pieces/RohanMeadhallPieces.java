package lotb.world.structures.pieces;

import lotb.LotbMod;
import lotb.registries.ModBlocks;
import lotb.util.ModMathFunctions;
import lotb.world.structures.ModStructurePiece;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.Half;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;
import java.util.Random;

public class RohanMeadhallPieces {
    /**=========================================================================================================================\
    |*--------------------------------------------------------ENTRANCE----------------------------------------------------------|
    \*=========================================================================================================================*/
    static final IStructurePieceType PIECES_ENTRANCE = IStructurePieceType.register(RohanMeadhallPieces.Entrance::new,"RoMHE");
    static final IStructurePieceType PIECES_HALL = IStructurePieceType.register(RohanMeadhallPieces.Hall::new,"RoMHH");
    static final IStructurePieceType PIECES_KEEP = IStructurePieceType.register(RohanMeadhallPieces.Entrance::new,"RoMHK");
    static final IStructurePieceType PIECES_LEFT_WING = IStructurePieceType.register(RohanMeadhallPieces.Entrance::new,"RoMHL");
    static final IStructurePieceType PIECES_RIGHT_WING = IStructurePieceType.register(RohanMeadhallPieces.Entrance::new,"RoMHR");

    private static abstract class MeadhallPiece extends ModStructurePiece{
        public MeadhallPiece(IStructurePieceType structurePierceTypeIn, TemplateManager _tmp) {
            super(structurePierceTypeIn, 0);
            setupStructures(_tmp);
        }
        public MeadhallPiece(IStructurePieceType structurePierceTypeIn, TemplateManager _tmp, CompoundNBT nbt) {
            super(structurePierceTypeIn, nbt);
            setupStructures(_tmp);
        }
        public void setupStructures(TemplateManager manager){
            shipwreck = manager.getTemplateDefaulted(new ResourceLocation("shipwreck/with_mast"));
        }
        Template shipwreck;

        BlockState BEAM = Blocks.OAK_LOG.getDefaultState();
        BlockState THATCH = ModBlocks.THATCH_BLOCK.get().getDefaultState();
        BlockState THATCH_SLAB = ModBlocks.THATCH_SLAB.get().getDefaultState();
        BlockState THATCH_STAIRS = ModBlocks.THATCH_STAIRS.get().getDefaultState();
        BlockState THATCH_STAIRS_L = THATCH_STAIRS.with(StairsBlock.FACING,Direction.WEST);
        BlockState THATCH_STAIRS_R = THATCH_STAIRS.with(StairsBlock.FACING,Direction.EAST);
        BlockState THATCH_STAIRS_LU = THATCH_STAIRS_L.with(StairsBlock.HALF, Half.TOP);
        BlockState THATCH_STAIRS_RU = THATCH_STAIRS_R.with(StairsBlock.HALF, Half.TOP);
        BlockState MARK = Blocks.RED_CONCRETE.getDefaultState();
        BlockState COBBLE = Blocks.COBBLESTONE.getDefaultState();

        protected void buildRoofSegment(IWorld world, MutableBoundingBox box, int x, int y, int z){
            final int length = 15;
            int i = x;
            int endl = length*2+x;
            for (int h = y; h <= y+length-3; h++){
                setBlockState(world, THATCH_STAIRS_RU,i,h,z,box);
                setBlockState(world, THATCH_STAIRS_RU,i+1,h,z+1,box);
                setBlockState(world, THATCH_STAIRS_RU,i+2,h,z+2,box);
                setBlockState(world, THATCH_STAIRS_RU,i+2,h,z+3,box);
                setBlockState(world, THATCH_STAIRS_RU,i+1,h,z+4,box);
                setBlockState(world, THATCH_STAIRS_R,i,h+1,z,box);
                setBlockState(world, THATCH_STAIRS_R,i+1,h+1,z+1,box);
                setBlockState(world, THATCH_STAIRS_R,i+2,h+1,z+2,box);
                setBlockState(world, THATCH_STAIRS_R,i+2,h+1,z+3,box);
                setBlockState(world, THATCH_STAIRS_R,i+1,h+1,z+4,box);
                setBlockState(world, THATCH_STAIRS_LU, endl,h,z,box);
                setBlockState(world, THATCH_STAIRS_LU,endl-1,h,z+1,box);
                setBlockState(world, THATCH_STAIRS_LU,endl-2,h,z+2,box);
                setBlockState(world, THATCH_STAIRS_LU,endl-2,h,z+3,box);
                setBlockState(world, THATCH_STAIRS_LU,endl-1,h,z+4,box);
                setBlockState(world, THATCH_STAIRS_L, endl,h+1,z,box);
                setBlockState(world, THATCH_STAIRS_L,endl-1,h+1,z+1,box);
                setBlockState(world, THATCH_STAIRS_L,endl-2,h+1,z+2,box);
                setBlockState(world, THATCH_STAIRS_L,endl-2,h+1,z+3,box);
                setBlockState(world, THATCH_STAIRS_L,endl-1,h+1,z+4,box);
                ++x;--endl;
            }
        }
    }

    public static class Entrance extends MeadhallPiece {
        public Entrance(TemplateManager _tmp, Random rand, int x, int y, int z) {
            super(PIECES_ENTRANCE,_tmp);
            Direction dir = Direction.Plane.HORIZONTAL.random(rand);
            boundingBox = new MutableBoundingBox(x-20, y-10, z-20, 20, 20, 20);
            setCoordBaseMode(Direction.NORTH);
        }
        /*---------------------------------------------data-------------------------------------------------*/
        public Entrance(TemplateManager _tmp, CompoundNBT nbt) {
            super(PIECES_ENTRANCE, _tmp, nbt);
        }
        @Override protected void readAdditional(CompoundNBT tagCompound) {}

        /*-------------------------------------------generation----------------------------------------------*/
        @Override public boolean create(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox box, ChunkPos chunkPos){
            buildRoofSegment(world, boundingBox, 0,20,0);
            buildRoofSegment(world, boundingBox, 0,20,10);
            setBlockState(world, MARK,0,0,0, boundingBox);
            setBlockState(world, MARK,1,1,1, boundingBox);
            setBlockState(world, MARK,2,2,2, boundingBox);
            setBlockState(world, MARK,3,3,3, boundingBox);
            placeTemplate(world,boundingBox,rand,shipwreck,0,0,0, new PlacementSettings());
            return true;
        }
        public void setBlockState(IWorld world, BlockState state, int x, int y, int z , MutableBoundingBox box){
            super.setBlockState(world,state,x,y,z,box);
            LotbMod.LOGGER.warn("adding blockstate {} to {} at {} , {} , {}",state,world,getXWithOffset(x, z),getYWithOffset(y),getZWithOffset(x, z));
        }
    }
    public static class Hall extends MeadhallPiece {
        public Hall(TemplateManager _tmp, Random rand, Direction _dir, int x, int y, int z) {
            super(PIECES_ENTRANCE,_tmp);
            setCoordBaseMode(_dir);
            boundingBox = new MutableBoundingBox(x, y, z, x+7+rand.nextInt(6), y+2+rand.nextInt(6), z+7+rand.nextInt(6));
        }
        /*---------------------------------------------data-------------------------------------------------*/
        public Hall(TemplateManager _tmp, CompoundNBT nbt) {
            super(PIECES_ENTRANCE, _tmp, nbt);
        }
        @Override protected void readAdditional(CompoundNBT tagCompound) {}

        /*-------------------------------------------generation----------------------------------------------*/
        @Override public boolean create(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox box, ChunkPos chunkPos){
            int sectiononelength=rand.nextInt(2)+2;
            int sectiontwolength=rand.nextInt(2)+2;
            int totallength = sectiononelength+sectiontwolength+4;

            for (int x =0; x < totallength*5 ; x++) for (int z =0; z < 30; z++){
                setBlockState(world,COBBLE,x,0,z,box);
            }

            fillWithBlocks(world, box, boundingBox.minX, boundingBox.minY  , boundingBox.minZ, boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ, Blocks.COBBLESTONE.getDefaultState(), Blocks.COBBLESTONE.getDefaultState(), false  );
            return true;
        }
    }

    /**=========================================================================================================================\
    |*----------------------------------------------------------SIDE------------------------------------------------------------|
    \*=========================================================================================================================*/

    /**=========================================================================================================================\
    |*----------------------------------------------------------BACK------------------------------------------------------------|
    \*=========================================================================================================================*/
}
