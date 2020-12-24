package lotb.world.structures.pieces;

import lotb.registries.ModBlocks;
import lotb.util.ModMathFunctions;
import lotb.world.structures.ModStructurePiece;
import net.minecraft.block.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public abstract class RohanHousePiece extends ModStructurePiece{
    static final IStructurePieceType PIECE_ROHAN_HOUSE_START = IStructurePieceType.register(RohanHouseEntrancePiece::new, "RoHOs");
    static final IStructurePieceType PIECE_ROHAN_HOUSE_MID = IStructurePieceType.register(RohanHouseMidPiece::new, "RoHOm");
    static final IStructurePieceType PIECE_ROHAN_HOUSE_END = IStructurePieceType.register(RohanHouseEndPiece::new, "RoHOe");
    static final IStructurePieceType PIECE_ROHAN_HOUSE_CORNER = IStructurePieceType.register(RohanHouseCornerPiece::new, "RoHOc");
    static final IStructurePieceType PIECE_ROHAN_HOUSE_TURN = IStructurePieceType.register(RohanHouseTurnPiece::new, "RoHOl");
    static final IStructurePieceType PIECE_ROHAN_HOUSE_TSHAPE = IStructurePieceType.register(RohanHouseTShapePiece::new, "RoHOt");
    static final IStructurePieceType PIECE_ROHAN_HOUSE_CROSS = IStructurePieceType.register(RohanHouseCrossPiece::new, "RoHOr");

    public byte roomtype;
    public byte roomvar;
    public RohanHousePiece(IStructurePieceType _type, Direction _dir, byte _roomtype, byte _rv) {
        super(_type, 0);
        setCoordBaseMode(_dir);
        roomtype = _roomtype;
        roomvar = _rv;
    }
    public RohanHousePiece(IStructurePieceType _type, CompoundNBT nbt) { super(_type, nbt);}
    @Override protected void readAdditional(@Nonnull CompoundNBT nbt) {}

    protected final BlockState AIR = Blocks.GLASS.getDefaultState();//Blocks.AIR.getDefaultState();
    protected static final BlockState PLANKS = Blocks.OAK_PLANKS.getDefaultState();
    protected static final BlockState LOG = Blocks.OAK_LOG.getDefaultState();
    protected static final BlockState LOGX = LOG.with(LogBlock.AXIS, Direction.Axis.X);
    protected static final BlockState LOGZ = LOG.with(LogBlock.AXIS, Direction.Axis.Z);
    protected static final BlockState POST = Blocks.SPRUCE_FENCE.getDefaultState();
    protected static final BlockState STONEBRICK = Blocks.STONE_BRICKS.getDefaultState();
    protected static final BlockState STONEBRICK_STAIRS = Blocks.STONE_BRICK_STAIRS.getDefaultState();
    protected static final BlockState STONEBRICK_SLAB = Blocks.STONE_BRICK_SLAB.getDefaultState();
    protected static final BlockState STONEBRICK_WALL = Blocks.STONE_BRICK_WALL.getDefaultState();
    protected static final BlockState COBBLE = Blocks.COBBLESTONE.getDefaultState();
    protected static final BlockState COBBLE_STAIRS = Blocks.COBBLESTONE_STAIRS.getDefaultState();
    protected static final BlockState COBBLE_STAIRS_U = COBBLE_STAIRS.with(StairsBlock.HALF, Half.TOP);
    protected static final BlockState FIRE = Blocks.CAMPFIRE.getDefaultState();
    protected static final BlockState THATCH = ModBlocks.THATCH_BLOCK.get().getDefaultState();
    protected static final BlockState THATCH_SLAB = ModBlocks.THATCH_SLAB.get().getDefaultState();
    protected static final BlockState THATCH_STAIRS = ModBlocks.THATCH_STAIRS.get().getDefaultState();
    protected static final BlockState THATCH_STAIRS_B = ModBlocks.THATCH_STAIRS.get().getDefaultState().with(StairsBlock.FACING, Direction.SOUTH);
    protected static final BlockState THATCH_STAIRS_R = ModBlocks.THATCH_STAIRS.get().getDefaultState().with(StairsBlock.FACING, Direction.EAST);
    protected static final BlockState THATCH_STAIRS_L = ModBlocks.THATCH_STAIRS.get().getDefaultState().with(StairsBlock.FACING, Direction.WEST);
    protected static final BlockState THATCH_STAIRS_U = THATCH_STAIRS.with(StairsBlock.HALF, Half.TOP);
    protected static final BlockState THATCH_STAIRS_BU = THATCH_STAIRS_B.with(StairsBlock.HALF, Half.TOP);
    protected static final BlockState THATCH_STAIRS_RU = THATCH_STAIRS_R.with(StairsBlock.HALF, Half.TOP);
    protected static final BlockState THATCH_STAIRS_LU = THATCH_STAIRS_L.with(StairsBlock.HALF, Half.TOP);
    protected static final BlockState GLASS = Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.EAST,true).with(PaneBlock.WEST,true);

    /*-------------------------------------------segments----------------------------------------------*/
    protected void buildSupportDown(IWorld world, MutableBoundingBox box, int _x, int _z, BlockState state){
        BlockState logstate = state.rotate(getRotation());
        int x = this.getXWithOffset(_x, _z);
        int y = this.getYWithOffset(0);
        int z = this.getZWithOffset(_x, _z);
        int offset = y % 5;
        if (box.isVecInside(new BlockPos(x, y, z))) {
            while((world.isAirBlock(new BlockPos(x, y, z)) || world.getBlockState(new BlockPos(x, y, z)).getMaterial().isLiquid()) && y > 1) {
                if (y%5 == offset) world.setBlockState(new BlockPos(x, y, z), logstate, 2);
                --y;
            }

        }
    }

    protected void wallStraightL(IWorld world, MutableBoundingBox box, int x, int y, int z){
        for (int i = z; i <= z+3; i++) {
            for (int h = y; h <= y+4; h++) {
                setBlockState(world, PLANKS, x+6, h, i, box);
                setBlockState(world, AIR, x+5, h, i, box);
            }
            setBlockState(world, STONEBRICK,x+5, y, i, box);
            replaceAirAndLiquidDownwards(world, PLANKS, x+6, y-1, i, box);
            replaceAirAndLiquidDownwards(world, STONEBRICK,x+5, y-1, i, box);
        }
        for (int h = y; h <= y+4; h++) {
            setBlockState(world, PLANKS, x + 6, h, z+4, box);
            //setBlockState(world, PLANKS, x + 6, h, z+5, box);
        }
        replaceAirAndLiquidDownwards(world, PLANKS, x+6, y-1, 4, box);
        //replaceAirAndLiquidDownwards(world, PLANKS, x+6, y-1, 5, box);
        for (int l = y-2; l<= y+3; l++) setBlockState(world, LOG, x+7, l, z+5, box);
        replaceAirAndLiquidDownwards(world, LOG, x+7, y-3, z+5, box);
        for (int l = z; l <= z+4; l++) buildSupportDown(world,box,x+7, z+l, LOGZ);
    }
    protected void wallStraightR(IWorld world, MutableBoundingBox box, int x, int y, int z, boolean fire){
        for (int i = z; i <= z+3; i++) {
            for (int h = y; h <= y+4; h++) {
                setBlockState(world, PLANKS, x+2, h, i, box);
                setBlockState(world, AIR, x+3, h, i, box);
                setBlockState(world, AIR, x+4, h, i, box);
            }
            replaceAirAndLiquidDownwards(world, PLANKS, x+2, y-1, i, box);
        }
        for (int h = y; h <= y+4; h++) {
            setBlockState(world, PLANKS, x+2, h, 4, box);
            //setBlockState(world, PLANKS, x+2, h, 5, box);
        }
        replaceAirAndLiquidDownwards(world, PLANKS, x+2, y-1, 4, box);
        //replaceAirAndLiquidDownwards(world, PLANKS, x+2, y-1, 5, box);
        setBlockState(world, STONEBRICK,x+3, y, z, box);
        setBlockState(world, STONEBRICK,x+3, y, z+1, box);
        setBlockState(world, STONEBRICK,x+3, y, z+2, box);
        setBlockState(world, STONEBRICK,x+3, y, z+3, box);
        replaceAirAndLiquidDownwards(world, STONEBRICK,x+3, y-1, z, box);
        replaceAirAndLiquidDownwards(world, STONEBRICK,x+3, y-1, z+1, box);
        replaceAirAndLiquidDownwards(world, STONEBRICK,x+3, y-1, z+2, box);
        replaceAirAndLiquidDownwards(world, STONEBRICK,x+3, y-1, z+3, box);
        replaceAirAndLiquidDownwards(world, COBBLE,    x+4, y-1, z, box);
        replaceAirAndLiquidDownwards(world, COBBLE,    x+4, y-1, z+1, box);
        replaceAirAndLiquidDownwards(world, COBBLE,    x+4, y-1, z+2, box);
        replaceAirAndLiquidDownwards(world, COBBLE,    x+4, y-1, z+3, box);

        for (int l = y-2;l<=y+3;l++) setBlockState(world, LOG, x+1, l, z+5, box);
        replaceAirAndLiquidDownwards(world, LOG, x+1, y-3, z+5, box);
        for (int l = z; l <= z+4; l++) buildSupportDown(world,box,x+1, l,LOGZ);

        if (fire){
            setBlockState(world, COBBLE,            x+4, y, z, box);
            setBlockState(world, COBBLE_STAIRS.with(StairsBlock.FACING,Direction.SOUTH),x+4, y, z+1, box);
            setBlockState(world, FIRE,x+4, y, z+2, box);
            setBlockState(world, AIR, x+4, y+5, z+2, box);
            setBlockState(world, AIR, x+4, y+6, z+2, box);
            setBlockState(world, AIR, x+4, y+7, z+2, box);
            setBlockState(world, COBBLE_STAIRS,x+4, y, z+3, box);
        }else
            for (int i =z;i<z+3;i++) setBlockState(world, COBBLE, x+4, y, i, box);
    }
    protected void wallTurnL(IWorld world, MutableBoundingBox box, int x, int y, int z){
        for (int h = y+1; h <= y+4; h++) {
            setBlockState(world, AIR,x+5, h, z, box);
            setBlockState(world, AIR,x+5, h, z+1, box);
            setBlockState(world, AIR,x+5, h, z+2, box);
            setBlockState(world, AIR,x+5, h, z+3, box);
            setBlockState(world, AIR,x+6, h, z+1, box);
            setBlockState(world, AIR,x+6, h, z+2, box);
            setBlockState(world, AIR,x+6, h, z+3, box);
            setBlockState(world, AIR,x+7, h, z+1, box);
            setBlockState(world, AIR,x+7, h, z+2, box);
            setBlockState(world, AIR,x+7, h, z+3, box);
        }
        //floor and walls
        setBlockState(world, STONEBRICK,x+5, y, z, box);
        setBlockState(world, STONEBRICK,x+5, y, z+1, box);
        setBlockState(world, COBBLE,    x+5, y, z+2, box);
        setBlockState(world, STONEBRICK,x+5, y, z+3, box);
        replaceAirAndLiquidDownwards(world, STONEBRICK,x+5, y-1, z, box);
        replaceAirAndLiquidDownwards(world, STONEBRICK,x+5, y-1, z+1, box);
        replaceAirAndLiquidDownwards(world, COBBLE,    x+5, y-1, z+2, box);
        replaceAirAndLiquidDownwards(world, STONEBRICK,x+5, y-1, z+3, box);
        setBlockState(world, STONEBRICK,x+6, y, z+1, box);
        setBlockState(world, COBBLE,    x+6, y, z+2, box);
        setBlockState(world, STONEBRICK,x+6, y, z+3, box);
        replaceAirAndLiquidDownwards(world, STONEBRICK,x+6,y-1, z+1, box);
        replaceAirAndLiquidDownwards(world, COBBLE,    x+6,y-1, z+2, box);
        replaceAirAndLiquidDownwards(world, STONEBRICK,x+6,y-1, z+3, box);
        setBlockState(world, STONEBRICK,x+7, y, z+1, box);
        setBlockState(world, COBBLE,    x+7, y, z+2, box);
        setBlockState(world, STONEBRICK,x+7, y, z+3, box);
        replaceAirAndLiquidDownwards(world, STONEBRICK,x+7,y-1, z+1, box);
        replaceAirAndLiquidDownwards(world, COBBLE,    x+7,y-1, z+2, box);
        replaceAirAndLiquidDownwards(world, STONEBRICK,x+7,y-1, z+3, box);
        for (int h = y; h <= y+4; h++) {
            setBlockState(world, PLANKS, x+6, h, z, box);
            setBlockState(world, PLANKS, x+7, h, z, box);
            setBlockState(world, PLANKS, x+6, h, z+4, box);
            setBlockState(world, PLANKS, x+7, h, z+4, box);
        }
        replaceAirAndLiquidDownwards(world, PLANKS, x+6, y-1, z, box);
        replaceAirAndLiquidDownwards(world, PLANKS, x+7, y-1, z, box);
        replaceAirAndLiquidDownwards(world, PLANKS, x+6, y-1, z+4, box);
        replaceAirAndLiquidDownwards(world, PLANKS, x+7, y-1, z+4, box);
        //pillars
        for (int l = y-2;l<=y+3;l++) setBlockState(world, LOG, x+7, l, z+5, box);
        replaceAirAndLiquidDownwards(world, LOG, x+7, y-3, z+5, box);
    }
    protected void wallTurnR(IWorld world, MutableBoundingBox box, int x, int y, int z, boolean fire){
        for (int h = y+1; h <= y+4; h++) {
            setBlockState(world, AIR,x+1, h, z+1, box);
            setBlockState(world, AIR,x+1, h, z+2, box);
            setBlockState(world, AIR,x+1, h, z+3, box);
            setBlockState(world, AIR,x+2, h, z+1, box);
            setBlockState(world, AIR,x+2, h, z+2, box);
            setBlockState(world, AIR,x+2, h, z+3, box);
            setBlockState(world, AIR,x+3, h, z, box);
            setBlockState(world, AIR,x+3, h, z+1, box);
            setBlockState(world, AIR,x+3, h, z+2, box);
            setBlockState(world, AIR,x+3, h, z+3, box);
            setBlockState(world, AIR,x+4, h, z, box);
            setBlockState(world, AIR,x+4, h, z+1, box);
            setBlockState(world, AIR,x+4, h, z+2, box);
            setBlockState(world, AIR,x+4, h, z+3, box);
        }
        setBlockState(world, STONEBRICK,x+1, y, z+1, box);
        setBlockState(world, COBBLE,    x+1, y, z+2, box);
        setBlockState(world, STONEBRICK,x+1, y, z+3, box);
        replaceAirAndLiquidDownwards(world, STONEBRICK,x+1,y-1, z+1, box);
        replaceAirAndLiquidDownwards(world, COBBLE,    x+1,y-1, z+2, box);
        replaceAirAndLiquidDownwards(world, STONEBRICK,x+1,y-1, z+3, box);
        setBlockState(world, STONEBRICK,x+2, y, z+1, box);
        setBlockState(world, COBBLE,    x+2, y, z+2, box);
        setBlockState(world, STONEBRICK,x+2, y, z+3, box);
        replaceAirAndLiquidDownwards(world, STONEBRICK,x+2,y-1, z+1, box);
        replaceAirAndLiquidDownwards(world, COBBLE,    x+2,y-1, z+2, box);
        replaceAirAndLiquidDownwards(world, STONEBRICK,x+2,y-1, z+3, box);
        setBlockState(world, STONEBRICK,x+3, y, z, box);
        setBlockState(world, STONEBRICK,x+3, y, z+1, box);
        setBlockState(world, COBBLE,    x+3, y, z+2, box);
        setBlockState(world, STONEBRICK,x+3, y, z+3, box);
        replaceAirAndLiquidDownwards(world, STONEBRICK,x+3, y-1, z, box);
        replaceAirAndLiquidDownwards(world, STONEBRICK,x+3, y-1,z+1, box);
        replaceAirAndLiquidDownwards(world, COBBLE,    x+3, y-1,z+2, box);
        replaceAirAndLiquidDownwards(world, STONEBRICK,x+3, y-1,z+3, box);
        replaceAirAndLiquidDownwards(world, COBBLE,x+4, y-1, z, box);
        replaceAirAndLiquidDownwards(world, COBBLE,x+4, y-1, z+1, box);
        replaceAirAndLiquidDownwards(world, COBBLE,x+4, y-1, z+2, box);
        replaceAirAndLiquidDownwards(world, COBBLE,x+4, y-1, z+3, box);
        for (int h = y; h <= y+4; h++) {
            setBlockState(world, PLANKS, x+1, h, z, box);
            setBlockState(world, PLANKS, x+2, h, z, box);
            setBlockState(world, PLANKS, x+1, h, z+4, box);
            setBlockState(world, PLANKS, x+2, h, z+4, box);
        }
        replaceAirAndLiquidDownwards(world, PLANKS, x+1, y-1, z, box);
        replaceAirAndLiquidDownwards(world, PLANKS, x+2, y-1, z, box);
        replaceAirAndLiquidDownwards(world, PLANKS, x+1, y-1, z+4, box);
        replaceAirAndLiquidDownwards(world, PLANKS, x+2, y-1, z+4, box);
        for (int l = -2;l<=3;l++) setBlockState(world, LOG, 1, l, 5, box);
        replaceAirAndLiquidDownwards(world, LOG, 1, -3, 5, box);
        if (fire){
            setBlockState(world, COBBLE,            x+4, y, z, box);
            setBlockState(world, COBBLE_STAIRS.with(StairsBlock.FACING,Direction.SOUTH),     x+4, y, z+1, box);
            setBlockState(world, FIRE,              x+4, y, z+2, box);
            setBlockState(world, COBBLE_STAIRS,     x+4, y, z+3, box);
            setBlockState(world, AIR,               x+4, y+5, z+2, box);
            setBlockState(world, AIR,               x+4, y+6, z+2, box);
            setBlockState(world, AIR,               x+4, y+7, z+2, box);
        }else{
            setBlockState(world, COBBLE,    x+4, y, z, box);
            setBlockState(world, COBBLE,    x+4, y, z+1, box);
            setBlockState(world, COBBLE,    x+4, y, z+2, box);
            setBlockState(world, COBBLE,    x+4, y, z+3, box);
        }
    }

    protected void wallCont(IWorld world, MutableBoundingBox box, int x, int y, int z){
        for (int h = y; h <= y+4; h++) {
            setBlockState(world, PLANKS, x+2, h, z, box);
            setBlockState(world, PLANKS, x+6, h, z, box);
            if (h!=y){
                setBlockState(world, AIR,x+3, h, z, box);
                setBlockState(world, AIR,x+4, h, z, box);
                setBlockState(world, AIR,x+5, h, z, box);
                setBlockState(world, AIR,x+3, h, z-1, box);
                setBlockState(world, AIR,x+4, h, z-1, box);
                setBlockState(world, AIR,x+5, h, z-1, box);
            }
        }
        replaceAirAndLiquidDownwards(world, PLANKS, x+2, y-1, z, box);
        replaceAirAndLiquidDownwards(world, PLANKS, x+6, y-1, z, box);
        setBlockState(world, STONEBRICK,x+3, y, z, box);
        setBlockState(world, COBBLE,    x+4, y, z, box);
        setBlockState(world, STONEBRICK,x+5, y, z, box);
        setBlockState(world, STONEBRICK,x+3, y, z-1, box);
        setBlockState(world, COBBLE,    x+4, y, z-1, box);
        setBlockState(world, STONEBRICK,x+5, y, z-1, box);
        replaceAirAndLiquidDownwards(world, STONEBRICK,x+3, y-1, z, box);
        replaceAirAndLiquidDownwards(world, COBBLE,    x+4, y-1, z, box);
        replaceAirAndLiquidDownwards(world, STONEBRICK,x+5, y-1, z, box);
        replaceAirAndLiquidDownwards(world, STONEBRICK,x+3, y-1, z-1, box);
        replaceAirAndLiquidDownwards(world, COBBLE,    x+4, y-1, z-1, box);
        replaceAirAndLiquidDownwards(world, STONEBRICK,x+5, y-1, z-1, box);
    }
    protected void wallEnd(IWorld world, MutableBoundingBox box, Random rand, int x, int y, int z){
        for (int h = y+1; h <= y+4; h++){
            setBlockState(world, PLANKS, x, h, z, box);
            setBlockState(world, PLANKS, x+1, h, z, box);
            setBlockState(world, PLANKS, x+2, h, z, box);
            setBlockState(world, PLANKS, x+3, h, z, box);
            setBlockState(world, PLANKS, x+4, h, z, box);
            setBlockState(world, AIR, x+1, h, z-1, box);
            setBlockState(world, AIR, x+2, h, z-1, box);
            setBlockState(world, AIR, x+3, h, z-1, box);
        }
        setBlockState(world, PLANKS, x+1, y+5, z, box);
        setBlockState(world, rand.nextBoolean()?GLASS:PLANKS, x+2, y+2, z, box);
        setBlockState(world, PLANKS, x+2, y+3, z, box);
        setBlockState(world, PLANKS, x+2, y+4, z, box);
        setBlockState(world, PLANKS, x+2, y+5, z, box);
        setBlockState(world, PLANKS, x+2, y+6, z, box);
        setBlockState(world, PLANKS, x+3, y+5, z, box);
        replaceAirAndLiquidDownwards(world, PLANKS, x, y, z, box);
        replaceAirAndLiquidDownwards(world, PLANKS, x+1, y, z, box);
        replaceAirAndLiquidDownwards(world, PLANKS, x+2, y, z, box);
        replaceAirAndLiquidDownwards(world, PLANKS, x+3, y, z, box);
        replaceAirAndLiquidDownwards(world, PLANKS, x+4, y, z, box);
        setBlockState(world, POST,x, y+3, z+1, box);
        setBlockState(world, POST,x+4, y+3, z+1, box);
        setBlockState(world, POST,x+2, y+5, z+1, box);
        setBlockState(world, STONEBRICK, x+1, y, z-1, box);
        setBlockState(world, STONEBRICK, x+2, y, z-1, box);
        setBlockState(world, STONEBRICK, x+3, y, z-1, box);
        replaceAirAndLiquidDownwards(world, STONEBRICK,x+1, y-1, z-1, box);
        replaceAirAndLiquidDownwards(world, STONEBRICK,x+2, y-1, z-1, box);
        replaceAirAndLiquidDownwards(world, STONEBRICK,x+3, y-1, z-1, box);
        buildSupportDown(world, box, x, z+1, LOGX);
        buildSupportDown(world, box, x+1, z+1, LOGX);
        buildSupportDown(world, box, x+2, z+1, LOGX);
        buildSupportDown(world, box, x+3, z+1, LOGX);
        buildSupportDown(world, box, x+4, z+1, LOGX);
    }

    protected void roofStraightL(IWorld world, MutableBoundingBox box, int x, int y, int z, boolean fire){
        for (int i = z; i <= z+5; i++) {
            setBlockState(world, THATCH_STAIRS_LU,x+8, y+2, i, box);
            setBlockState(world, THATCH_STAIRS_L, x+8, y+3, i, box);
            setBlockState(world, THATCH_STAIRS_L, x+7, y+4, i, box);
            setBlockState(world, THATCH_STAIRS_L, x+6, y+5, i, box);
        }
        setBlockState(world, THATCH_STAIRS_L, x+7, y+3, z, box);
        setBlockState(world, THATCH_STAIRS_L, x+7, y+3, z+1, box);
        setBlockState(world, THATCH_STAIRS_L, x+7, y+3, z+2, box);
        setBlockState(world, THATCH_STAIRS_L, x+7, y+3, z+3, box);
        setBlockState(world, THATCH_STAIRS_L, x+7, y+3, z+4, box);
        setBlockState(world, THATCH_STAIRS_RU,x+5, y+5, z, box);
        setBlockState(world, THATCH_STAIRS_L, x+5, y+6, z, box);
        setBlockState(world, THATCH_STAIRS_L, x+5, y+6, z+4, box);
        setBlockState(world, THATCH_STAIRS_L, x+5, y+6, z+5, box);
        setBlockState(world, THATCH, x+4, y+6, z, box);
        setBlockState(world, THATCH, x+4, y+6, z+4, box);
        setBlockState(world, THATCH, x+4, y+6, z+5, box);
        setBlockState(world, THATCH_SLAB, x+4, y+7, z, box);
        setBlockState(world, THATCH_SLAB, x+4, y+7, z+4, box);
        setBlockState(world, THATCH_SLAB, x+4, y+7, z+5, box);
        if (!fire){
            setBlockState(world, THATCH_STAIRS_L, x+5, y+6, z+1, box);
            setBlockState(world, THATCH_STAIRS_L, x+5, y+6, z+2, box);
            setBlockState(world, THATCH_STAIRS_L, x+5, y+6, z+3, box);
            setBlockState(world, THATCH_STAIRS_RU,x+5, y+5, z+1, box);
            setBlockState(world, THATCH_STAIRS_RU,x+5, y+5, z+2, box);
            setBlockState(world, THATCH_STAIRS_RU,x+5, y+5, z+3, box);
            setBlockState(world, THATCH, x+4, y+6, z+1, box);
            setBlockState(world, THATCH, x+4, y+6, z+2, box);
            setBlockState(world, THATCH, x+4, y+6, z+3, box);
            setBlockState(world, THATCH_SLAB,x+4, y+7, z+1, box);
            setBlockState(world, THATCH_SLAB,x+4, y+7, z+2, box);
            setBlockState(world, THATCH_SLAB,x+4, y+7, z+3, box);
        }
    }
    protected void roofStraightR(IWorld world, MutableBoundingBox box, int x, int y, int z, boolean fire){
        for (int i = z; i <= z+5; i++) {
            setBlockState(world, THATCH_STAIRS_RU,x, y+2, i, box);
            setBlockState(world, THATCH_STAIRS_R, x, y+3, i, box);
            setBlockState(world, THATCH_STAIRS_R, x+1, y+4, i, box);
            setBlockState(world, THATCH_STAIRS_R, x+2, y+5, i, box);
        }
        setBlockState(world, THATCH_STAIRS_R, x+3, y+6, z, box);
        setBlockState(world, THATCH_STAIRS_R, x+3, y+6, z+4, box);
        setBlockState(world, THATCH_STAIRS_R, x+3, y+6, z+5, box);
        setBlockState(world, THATCH_STAIRS_LU,x+3, y+5, z, box);
        setBlockState(world, THATCH_STAIRS_LU, x+1, y+3, z, box);
        setBlockState(world, THATCH_STAIRS_LU, x+1, y+3, z+1, box);
        setBlockState(world, THATCH_STAIRS_LU, x+1, y+3, z+2, box);
        setBlockState(world, THATCH_STAIRS_LU, x+1, y+3, z+3, box);
        setBlockState(world, THATCH_STAIRS_LU, x+1, y+3, z+4, box);
        if (fire){
            chimney(world,box,x+3,y+5,z+1);
        }else{
            setBlockState(world, THATCH_STAIRS_LU,x+3, y+5, z+1, box);
            setBlockState(world, THATCH_STAIRS_LU,x+3, y+5, z+2, box);
            setBlockState(world, THATCH_STAIRS_LU,x+3, y+5, z+3, box);
            setBlockState(world, THATCH_STAIRS_R, x+3, y+6, z+1, box);
            setBlockState(world, THATCH_STAIRS_R, x+3, y+6, z+2, box);
            setBlockState(world, THATCH_STAIRS_R, x+3, y+6, z+3, box);
        }
    }
    protected void roofTurnL(IWorld world, MutableBoundingBox box, int x, int y, int z, boolean fire) {
        setBlockState(world, THATCH_STAIRS_L,  x+5, y+6, z, box);
        setBlockState(world, THATCH_STAIRS_RU, x+5, y+5, z, box);
        setBlockState(world, THATCH_STAIRS_L.with(StairsBlock.SHAPE, StairsShape.INNER_RIGHT),    x+6, y+5, z, box);
        setBlockState(world, THATCH_STAIRS,    x+7, y+5, z, box);
        setBlockState(world, THATCH_STAIRS,    x+6, y+6, z+1, box);
        setBlockState(world, THATCH_STAIRS_BU, x+6, y+5, z+1, box);
        setBlockState(world, THATCH_STAIRS,    x+7, y+6, z+1, box);
        setBlockState(world, THATCH_STAIRS_BU, x+7, y+5, z+1, box);
        setBlockState(world, THATCH_STAIRS_B,  x+6, y+6, z+3, box);
        setBlockState(world, THATCH_STAIRS_U,  x+6, y+5, z+3, box);
        setBlockState(world, THATCH_STAIRS_B,  x+7, y+6, z+3, box);
        setBlockState(world, THATCH_STAIRS_U,  x+7, y+5, z+3, box);
        setBlockState(world, THATCH_STAIRS_L,  x+5, y+6, z+4, box);
        setBlockState(world, THATCH_STAIRS_RU, x+5, y+5, z+4, box);
        setBlockState(world, THATCH_STAIRS_L.with(StairsBlock.SHAPE, StairsShape.INNER_LEFT),  x+6, y+5, z+4, box);
        setBlockState(world, THATCH_STAIRS_B,  x+7, y+5, z+4, box);
        setBlockState(world, THATCH_STAIRS_L,  x+5, y+6, z+5, box);
        setBlockState(world, THATCH_STAIRS_RU, x+5, y+5, z+5, box);
        setBlockState(world, THATCH_STAIRS_L,  x+6, y+5, z+5, box);
        setBlockState(world, THATCH_STAIRS_L.with(StairsBlock.SHAPE, StairsShape.INNER_LEFT),  x+7, y+4, z+5, box);
        setBlockState(world, THATCH_STAIRS_RU.with(StairsBlock.SHAPE, StairsShape.OUTER_RIGHT),x+7, y+3, z+5, box);
        setBlockState(world, THATCH_SLAB, x+6, y+7, z+2, box);
        setBlockState(world, THATCH_SLAB, x+7, y+7, z+2, box);
        setBlockState(world, THATCH_SLAB, x+4, y+7, z+0, box);
        setBlockState(world, THATCH_SLAB, x+4, y+7, z+1, box);
        setBlockState(world, THATCH_SLAB, x+4, y+7, z+5, box);
        if (fire){
        }else{
            setBlockState(world, THATCH_SLAB, x+4, y+7, z+2, box);
            setBlockState(world, THATCH_SLAB, x+4, y+7, z+3, box);
            setBlockState(world, THATCH_SLAB, x+4, y+7, z+4, box);
            setBlockState(world, THATCH_SLAB, x+5, y+7, z+2, box);
            setBlockState(world, THATCH_STAIRS_L.with(StairsBlock.SHAPE, StairsShape.INNER_RIGHT),x+5, y+6, z+1, box);
            setBlockState(world, THATCH_STAIRS_RU.with(StairsBlock.SHAPE, StairsShape.OUTER_LEFT),x+5, y+5, z+1, box);
            setBlockState(world, THATCH_STAIRS_L.with(StairsBlock.SHAPE, StairsShape.INNER_LEFT), x+5, y+6, z+3, box);
            setBlockState(world, THATCH_STAIRS_RU.with(StairsBlock.SHAPE, StairsShape.OUTER_LEFT),x+5, y+5, z+3, box);
        }
    }
    protected void roofTurnR(IWorld world, MutableBoundingBox box, int x, int y, int z, boolean fire){
        setBlockState(world, THATCH_STAIRS,    x+1, y+5, z, box);
        setBlockState(world, THATCH_STAIRS_R.with(StairsBlock.SHAPE, StairsShape.INNER_LEFT),    x+2, y+5, z, box);
        setBlockState(world, THATCH_STAIRS_LU, x+3, y+5, z, box);
        setBlockState(world, THATCH_STAIRS_R,  x+3,y+6, z, box);
        setBlockState(world, THATCH_STAIRS_BU, x+1, y+5, z+1, box);
        setBlockState(world, THATCH_STAIRS,    x+1, y+6, z+1, box);
        setBlockState(world, THATCH_STAIRS_BU, x+2, y+5, z+1, box);
        setBlockState(world, THATCH_STAIRS,    x+2, y+6, z+1, box);
        setBlockState(world, THATCH_SLAB, x+1, y+7, z+2, box);
        setBlockState(world, THATCH_SLAB, x+2, y+7, z+2, box);
        setBlockState(world, THATCH_STAIRS_U,  x+1, y+5, z+3, box);
        setBlockState(world, THATCH_STAIRS_B,  x+1, y+6, z+3, box);
        setBlockState(world, THATCH_STAIRS_U,  x+2, y+5, z+3, box);
        setBlockState(world, THATCH_STAIRS_B,  x+2, y+6, z+3, box);
        setBlockState(world, THATCH_STAIRS_B,  x+1, y+5, z+4, box);
        setBlockState(world, THATCH_STAIRS_R.with(StairsBlock.SHAPE, StairsShape.INNER_RIGHT),  2, y+5, z+4, box);
        setBlockState(world, THATCH_STAIRS_LU, x+3,y+5, z+4, box);
        setBlockState(world, THATCH_STAIRS_R,  x+3,y+6, z+4, box);
        setBlockState(world, THATCH_STAIRS_LU, x+3,y+5, z+5, box);
        setBlockState(world, THATCH_STAIRS_R,  x+3,y+6, z+5, box);
        setBlockState(world, THATCH_STAIRS_LU.with(StairsBlock.SHAPE, StairsShape.OUTER_LEFT),  x+1, y+3, z+5, box);
        setBlockState(world, THATCH_STAIRS_R.with(StairsBlock.SHAPE, StairsShape.INNER_RIGHT),  x+1, y+4, z+5, box);
        setBlockState(world, THATCH_STAIRS_R,  x+2, 5, z+5, box);
        if (fire){
            chimney(world,box,x+3,y+5,z+1);
        }else{
            setBlockState(world, THATCH_STAIRS_LU.with(StairsBlock.SHAPE, StairsShape.OUTER_RIGHT),x+3, y+5, z+1, box);
            setBlockState(world, THATCH_STAIRS_R.with(StairsBlock.SHAPE, StairsShape.INNER_LEFT),  x+3, y+6, z+1, box);
            setBlockState(world, THATCH_SLAB, x+3, y+7, 2, box);
            setBlockState(world, THATCH_STAIRS_LU.with(StairsBlock.SHAPE, StairsShape.OUTER_RIGHT),x+3, y+5, z+3, box);
            setBlockState(world, THATCH_STAIRS_R.with(StairsBlock.SHAPE, StairsShape.INNER_RIGHT), x+3, y+6, z+3, box);
        }
    }

    protected void roofEndEdge(IWorld world, MutableBoundingBox box, int x, int y, int z){
        setBlockState(world, THATCH_STAIRS_BU.with(StairsBlock.SHAPE, StairsShape.OUTER_LEFT), x, y+2, 6, box);
        setBlockState(world, THATCH_STAIRS_BU.with(StairsBlock.SHAPE, StairsShape.OUTER_RIGHT),x+8, y+2, 6, box);
        setBlockState(world, THATCH_STAIRS_LU, x+1, y+3, z+6, box);
        setBlockState(world, THATCH_STAIRS_RU, x+7, y+3, z+6, box);
        setBlockState(world, THATCH_STAIRS_LU, x+2, y+4, z+6, box);
        setBlockState(world, THATCH_STAIRS_RU, x+6, y+4, z+6, box);
        setBlockState(world, THATCH_STAIRS_LU, x+3, y+5, z+5, box);
        setBlockState(world, THATCH_STAIRS_RU, x+5, y+5, z+5, box);
        setBlockState(world, THATCH_STAIRS_LU, x+3, y+5, z+6, box);
        setBlockState(world, THATCH_STAIRS_RU, x+5, y+5, z+6, box);
        setBlockState(world, THATCH_STAIRS_R, x, y+3, 6, box);
        setBlockState(world, THATCH_STAIRS_L, x+8, y+3, 6, box);
        setBlockState(world, THATCH_STAIRS_R, x+1, y+4, z+6, box);
        setBlockState(world, THATCH_STAIRS_L, x+7, y+4, z+6, box);
        setBlockState(world, THATCH_STAIRS_R, x+2, y+5, z+6, box);
        setBlockState(world, THATCH_STAIRS_L, x+6, y+5, z+6, box);
        setBlockState(world, THATCH_STAIRS_B.with(StairsBlock.SHAPE, StairsShape.OUTER_LEFT), x+3, y+6, z+6, box);
        setBlockState(world, THATCH_STAIRS_B.with(StairsBlock.SHAPE, StairsShape.OUTER_RIGHT),x+5, y+6, z+6, box);
        setBlockState(world, THATCH, x+4, y+6, z+5, box);
        setBlockState(world, THATCH_STAIRS_B, x+4, y+6, z+6, box);
        /*setBlockState(world, SPRUCE_SL, x+3, y+6, z+6, box);
          setBlockState(world, SPRUCE_SR, x+5, y+6, z+6, box);
          setBlockState(world, SPRUCE, x+4, y+6, z+5, box);
          setBlockState(world, SPRUCE_UB, x+4, y+6, z+6, box);
          setBlockState(world, SPRUCE_S, x+4, y+7, z+5, box);
          setBlockState(world, SPRUCE, x+4, y+7, z+6, box);
          setBlockState(world, SPRUCE_UB, x+4, y+7, z+7, box);*/
    }
    protected void chimney(IWorld world, MutableBoundingBox box, int x, int y, int z){
        setBlockState(world, COBBLE,       x, y, z, box);
        setBlockState(world, COBBLE_STAIRS.with(StairsBlock.SHAPE,StairsShape.INNER_RIGHT),x, y+1, z, box);
        setBlockState(world, COBBLE,       x+2, y, z, box);
        setBlockState(world, COBBLE_STAIRS.with(StairsBlock.SHAPE,StairsShape.INNER_LEFT),x+2, y+1, z, box);
        setBlockState(world, COBBLE,       x, y, z+2, box);
        setBlockState(world, COBBLE_STAIRS.with(StairsBlock.FACING,Direction.SOUTH).with(StairsBlock.SHAPE,StairsShape.INNER_LEFT),x, y+1, z+2, box);
        setBlockState(world, COBBLE,       x+2, y, z+2, box);
        setBlockState(world, COBBLE_STAIRS.with(StairsBlock.FACING,Direction.SOUTH).with(StairsBlock.SHAPE,StairsShape.INNER_RIGHT),x+2, y+1, z+2, box);

        setBlockState(world, COBBLE_STAIRS_U.with(StairsBlock.FACING,Direction.EAST),x+2, y, z, box);
        setBlockState(world, COBBLE_STAIRS_U.with(StairsBlock.FACING,Direction.EAST),x+2, y,z+1, box);
        setBlockState(world, COBBLE_STAIRS_U.with(StairsBlock.FACING,Direction.EAST),x+2, y,z+2, box);
        setBlockState(world, COBBLE,       x+2, y+1,z+1, box);
        setBlockState(world, COBBLE_STAIRS.with(StairsBlock.FACING,Direction.WEST),x+2, y+2, z+1, box);

        setBlockState(world, COBBLE,       x+1, y+1, z, box);
        setBlockState(world, COBBLE_STAIRS,x+1, y+2, z, box);
        setBlockState(world, COBBLE,       x+1, y+1, z+2, box);
        setBlockState(world, COBBLE_STAIRS.with(StairsBlock.FACING,Direction.SOUTH),x+1, y+2, z+2, box);

        setBlockState(world, COBBLE_STAIRS_U.with(StairsBlock.FACING,Direction.WEST),x, y, z, box);
        setBlockState(world, COBBLE_STAIRS_U.with(StairsBlock.FACING,Direction.WEST),x, y, z+1, box);
        setBlockState(world, COBBLE_STAIRS_U.with(StairsBlock.FACING,Direction.WEST),x, y, z+2, box);
        setBlockState(world, COBBLE,           x, y+1,z+1, box);
        setBlockState(world, COBBLE_STAIRS.with(StairsBlock.FACING,Direction.EAST),x, y+2, z+1, box);
    }

    /*-------------------------------------------other pieces----------------------------------------------*/
    public static abstract class RohanHouseMirroredPiece extends RohanHousePiece {
        protected final boolean flip;
        private final Mirror mirror;

        public RohanHouseMirroredPiece(IStructurePieceType _type, Direction _dir, byte _room, boolean _flip, byte _rv) {
            super(_type, _dir, _room, _rv);
            flip = _flip;
            Direction d = getCoordBaseMode();
            if ( d == Direction.SOUTH || d == Direction.WEST)
                mirror = Mirror.LEFT_RIGHT;
            else
                mirror = Mirror.NONE;
        }

        public RohanHouseMirroredPiece(IStructurePieceType _type, CompoundNBT nbt) {
            super(_type, nbt);
            flip = nbt.getBoolean("Flp");
            Direction d = getCoordBaseMode();
            if ( d == Direction.SOUTH || d == Direction.WEST)
                mirror = Mirror.LEFT_RIGHT;
            else
                mirror = Mirror.NONE;
        }

        protected int getXWithOffset(int x, int z) {
            Direction direction = this.getCoordBaseMode();
            if (direction == null) {
                return x;
            } else {
                switch(direction) {
                    case NORTH:
                        return this.boundingBox.maxX - x;
                    case SOUTH:
                        return this.boundingBox.minX + x;
                    case WEST:
                        return this.boundingBox.maxX - z;
                    case EAST:
                        return this.boundingBox.minX + z;
                    default:
                        return x;
                }
            }
        }

        protected int getZWithOffset(int x, int z) {
            Direction direction = this.getCoordBaseMode();
            if (direction == null) {
                return z;
            } else {
                switch(direction) {
                    case NORTH:
                        return this.boundingBox.maxZ - z;
                    case SOUTH:
                        return this.boundingBox.minZ + z;
                    case WEST:
                        return this.boundingBox.minZ + x;
                    case EAST:
                        return this.boundingBox.maxZ - x;
                    default:
                        return z;
                }
            }
        }

        @Override protected void readAdditional(@Nonnull CompoundNBT nbt) {
            nbt.putBoolean("Flp", flip);
        }
        protected void setBlockState(IWorld world, BlockState state, int x, int y, int z, MutableBoundingBox box) {
            super.setBlockState(world,!flip?state.mirror(mirror==null?Mirror.NONE:mirror):state,x,y,z,box);
        }
    }

    public static class RohanHouseEntrancePiece extends RohanHousePiece {
        int length;
        BlockPos origin;

        public RohanHouseEntrancePiece(Random rand, int x, int y, int z) {
            super(PIECE_ROHAN_HOUSE_START, Direction.Plane.HORIZONTAL.random(rand), (byte)0, (byte)0);
            length = rand.nextInt(3) + 2;
            origin = new BlockPos(x, y, z);
            boundingBox = ModMathFunctions.getDirectedBox(x, y, z, getCoordBaseMode(), 8, 6, 7);
        }

        /*---------------------------------------------serialisation-------------------------------------------------*/
        public RohanHouseEntrancePiece(TemplateManager manager, CompoundNBT nbt) {
            super(PIECE_ROHAN_HOUSE_START, nbt);
        }

        /*-------------------------------------------generation----------------------------------------------*/
        public void buildComponent(List<StructurePiece> components, Random rand, ChunkGenerator<?> generator) {
            Direction direction = getCoordBaseMode();
            boolean goleft = false;
            boolean goright= false;
            for (int i = 1; i <= length; i++) {
                BlockPos pos = ModMathFunctions.getPosInDir(origin, direction, i * 6 + 2);
                boolean last = (i==length);
                goleft = rand.nextBoolean() && !goleft;
                goright = rand.nextBoolean() && !goright;
                if (goleft && goright) components.add( last ? new RohanHouseTShapePiece(rand, pos, direction,(byte)rand.nextInt(3),(byte)0) : new RohanHouseCrossPiece(rand, pos, direction,(byte)rand.nextInt(3),(byte)0));
                else if (goleft || goright) components.add( last? new RohanHouseCornerPiece(rand, pos, direction,(byte)rand.nextInt(3),(byte)0, goright) : new RohanHouseTurnPiece(rand, pos, direction,(byte)rand.nextInt(3),(byte)0, goright));
                else components.add( last? new RohanHouseEndPiece(rand, pos, direction,(byte)rand.nextInt(3),(byte)0) : new RohanHouseMidPiece(rand, pos, direction,(byte)rand.nextInt(3),(byte)0));
                if (goright) components.add( new RohanHouseEndPiece(rand, ModMathFunctions.getRelativePosInDir(pos, direction,-2,0,0), direction.rotateYCCW(),(byte)rand.nextInt(3),(byte)0));
                if (goleft)components.add( new RohanHouseEndPiece(rand, ModMathFunctions.getRelativePosInDir(pos, direction,6,0,8), direction.rotateY(),(byte)rand.nextInt(3),(byte)0));
            }
        }

        static final BlockState DOOR = Blocks.OAK_DOOR.getDefaultState().with(DoorBlock.FACING, Direction.SOUTH);
        @Override public boolean create(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox box, ChunkPos chunkPos) {
            if (roomtype>5) {
            }else {
                for (int i = 3; i <= 5; i++)
                    for (int j = 1; j <= 3; j++) {
                        setBlockState(world, AIR, i, j, 0, box);
                        setBlockState(world, AIR, i, j, 1, box);
                    }
                //door frame
                setBlockState(world, PLANKS, 2, 1, 2, box);
                setBlockState(world, PLANKS, 2, 2, 2, box);
                setBlockState(world, PLANKS, 2, 3, 2, box);
                setBlockState(world, PLANKS, 2, 4, 2, box);
                setBlockState(world, PLANKS, 3, 1, 2, box);
                setBlockState(world, PLANKS, 3, 2, 2, box);
                setBlockState(world, PLANKS, 3, 3, 2, box);
                setBlockState(world, PLANKS, 3, 4, 2, box);
                setBlockState(world, PLANKS, 3, 5, 2, box);
                setBlockState(world, DOOR, 4, 1, 2, box);
                setBlockState(world, DOOR.with(DoorBlock.HALF, DoubleBlockHalf.UPPER), 4, 2, 2, box);
                setBlockState(world, PLANKS, 4, 3, 2, box);
                setBlockState(world, PLANKS, 4, 4, 2, box);
                setBlockState(world, PLANKS, 4, 5, 2, box);
                setBlockState(world, PLANKS, 4, 6, 2, box);
                setBlockState(world, PLANKS, 5, 1, 2, box);
                setBlockState(world, PLANKS, 5, 2, 2, box);
                setBlockState(world, PLANKS, 5, 3, 2, box);
                setBlockState(world, PLANKS, 5, 4, 2, box);
                setBlockState(world, PLANKS, 5, 5, 2, box);
                setBlockState(world, PLANKS, 6, 1, 2, box);
                setBlockState(world, PLANKS, 6, 2, 2, box);
                setBlockState(world, PLANKS, 6, 3, 2, box);
                setBlockState(world, PLANKS, 6, 4, 2, box);
                replaceAirAndLiquidDownwards(world, PLANKS, 2, 0, 2, box);
                replaceAirAndLiquidDownwards(world, PLANKS, 3, 0, 2, box);
                replaceAirAndLiquidDownwards(world, PLANKS, 4, 0, 2, box);
                replaceAirAndLiquidDownwards(world, PLANKS, 5, 0, 2, box);
                replaceAirAndLiquidDownwards(world, PLANKS, 6, 0, 2, box);
                setBlockState(world, POST, 2, 3, 1, box);
                setBlockState(world, POST, 6, 3, 1, box);
                //pillars front
                for (int l = -2; l <= 3; l++) {
                    setBlockState(world, LOG, 1, l, 1, box);
                    setBlockState(world, LOG, 7, l, 1, box);
                    setBlockState(world, LOG, 1, l, 7, box);
                    setBlockState(world, LOG, 7, l, 7, box);
                }
                replaceAirAndLiquidDownwards(world, LOG, 1, -3, 1, box);
                replaceAirAndLiquidDownwards(world, LOG, 7, -3, 1, box);
                replaceAirAndLiquidDownwards(world, LOG, 1, -3, 7, box);
                replaceAirAndLiquidDownwards(world, LOG, 7, -3, 7, box);
                for (int l = 2; l <= 6; l++) {
                    setBlockState(world, LOGX, l, 4, 1, box);
                    buildSupportDown(world, box, l, 1, LOGX);
                }
                for (int l = 2; l <= 4; l++) {
                    buildSupportDown(world, box, 1, l, LOGZ);
                    buildSupportDown(world, box, 7, l, LOGZ);
                }
                setBlockState(world, STONEBRICK, 4, 0, 3, box);
                setBlockState(world, POST, 4, 5, 1, box);
                //roof
                for (int i = 0; i <= 7; i++) {
                    setBlockState(world, THATCH_STAIRS_R, 0, 3, i, box);
                    setBlockState(world, THATCH_STAIRS_R, 1, 4, i, box);
                    setBlockState(world, THATCH_STAIRS_R, 2, 5, i, box);
                    setBlockState(world, THATCH_STAIRS_L, 6, 5, i, box);
                    setBlockState(world, THATCH_STAIRS_L, 7, 4, i, box);
                    setBlockState(world, THATCH_STAIRS_L, 8, 3, i, box);
                }
                for (int i = 2; i <= 7; i++) {
                    setBlockState(world, THATCH_STAIRS_R, 1, 3, i, box);
                    setBlockState(world, THATCH_STAIRS_L, 7, 3, i, box);
                    setBlockState(world, THATCH_STAIRS_RU, 0, 2, i, box);
                    setBlockState(world, THATCH_STAIRS_LU, 8, 2, i, box);
                }
                setBlockState(world, THATCH_STAIRS_U.with(StairsBlock.SHAPE, StairsShape.OUTER_RIGHT), 0, 2, 0, box);
                setBlockState(world, THATCH_STAIRS_U.with(StairsBlock.SHAPE, StairsShape.OUTER_LEFT), 8, 2, 0, box);
                setBlockState(world, THATCH_STAIRS_LU, 1, 3, 0, box);
                setBlockState(world, THATCH_STAIRS_RU, 7, 3, 0, box);
                setBlockState(world, THATCH_STAIRS_LU, 2, 4, 0, box);
                setBlockState(world, THATCH_STAIRS_RU, 6, 4, 0, box);
                setBlockState(world, THATCH_STAIRS_LU, 3, 5, 1, box);
                setBlockState(world, THATCH_STAIRS_RU, 5, 5, 1, box);
                setBlockState(world, THATCH_STAIRS_LU, 3, 5, 0, box);
                setBlockState(world, THATCH_STAIRS_RU, 5, 5, 0, box);
                setBlockState(world, THATCH_STAIRS.with(StairsBlock.SHAPE, StairsShape.OUTER_RIGHT), 3, 6, 0, box);
                setBlockState(world, THATCH_STAIRS.with(StairsBlock.SHAPE, StairsShape.OUTER_LEFT), 5, 6, 0, box);
                setBlockState(world, THATCH, 4, 6, 1, box);
                setBlockState(world, THATCH_STAIRS, 4, 6, 0, box);
                setBlockState(world, THATCH_STAIRS_LU, 3, 5, 3, box);
                setBlockState(world, THATCH_STAIRS_LU, 3, 5, 7, box);
                setBlockState(world, THATCH_STAIRS_R,3, 6, 1, box);
                setBlockState(world, THATCH_STAIRS_R,3, 6, 2, box);
                setBlockState(world, THATCH_STAIRS_R,3, 6, 3, box);
                setBlockState(world, THATCH_STAIRS_R,3, 6, 7, box);
                setBlockState(world, THATCH,         4, 6, 3, box);
                setBlockState(world, THATCH_SLAB,    4, 7, 1, box);
                setBlockState(world, THATCH_SLAB,    4, 7, 2, box);
                setBlockState(world, THATCH_SLAB,    4, 7, 3, box);
                setBlockState(world, THATCH,         4, 6, 7, box);
                setBlockState(world, THATCH_SLAB,    4, 7, 7, box);
                setBlockState(world, THATCH_STAIRS_RU, 5, 5, 3, box);
                setBlockState(world, THATCH_STAIRS_RU, 5, 5, 7, box);
                setBlockState(world, THATCH_STAIRS_L,5, 6, 1, box);
                setBlockState(world, THATCH_STAIRS_L,5, 6, 2, box);
                setBlockState(world, THATCH_STAIRS_L,5, 6, 3, box);
                setBlockState(world, THATCH_STAIRS_L,5, 6, 7, box);
                fillWithBlocks(world, box, 3, 1, 3, 5, 4, 7, AIR, AIR, false);
                //roof
                if (roomtype==0){
                    //floor and walls
                    for (int i = 3; i <= 7; i++) {
                        for (int h = 0; h <= 4; h++) {
                            setBlockState(world, PLANKS, 2, h, i, box);
                            setBlockState(world, PLANKS, 6, h, i, box);
                        }
                        setBlockState(world, STONEBRICK, 3, 0, i, box);
                        setBlockState(world, STONEBRICK, 5, 0, i, box);
                        replaceAirAndLiquidDownwards(world, PLANKS, 2, -1, i, box);
                        replaceAirAndLiquidDownwards(world, PLANKS, 6, -1, i, box);
                        replaceAirAndLiquidDownwards(world, STONEBRICK, 3, -1, i, box);
                        replaceAirAndLiquidDownwards(world, COBBLE, 4, -1, i, box);
                        replaceAirAndLiquidDownwards(world, STONEBRICK, 5, -1, i, box);
                    }
                    setBlockState(world, COBBLE, 4, 0, 3, box);
                    setBlockState(world, COBBLE_STAIRS.with(StairsBlock.FACING,Direction.SOUTH), 4, 0, 4, box);
                    setBlockState(world, FIRE, 4, 0, 5, box);
                    setBlockState(world, COBBLE_STAIRS, 4, 0, 6, box);
                    setBlockState(world, COBBLE, 4, 0, 7, box);
                    chimney(world,box,3,5,4);
                }else{
                    //floor and walls
                    for (int i = 3; i <= 7; i++) {
                        for (int h = 0; h <= 4; h++) {
                            setBlockState(world, PLANKS, 2, h, i, box);
                            setBlockState(world, PLANKS, 6, h, i, box);
                        }
                        setBlockState(world, STONEBRICK, 3, 0, i, box);
                        setBlockState(world, COBBLE, 4, 0, i, box);
                        setBlockState(world, STONEBRICK, 5, 0, i, box);
                        replaceAirAndLiquidDownwards(world, PLANKS, 2, -1, i, box);
                        replaceAirAndLiquidDownwards(world, PLANKS, 6, -1, i, box);
                        replaceAirAndLiquidDownwards(world, STONEBRICK, 3, -1, i, box);
                        replaceAirAndLiquidDownwards(world, COBBLE, 4, -1, i, box);
                        replaceAirAndLiquidDownwards(world, STONEBRICK, 5, -1, i, box);
                    }
                    //roof
                    for (int i = 1; i <= 7; i++) {
                        setBlockState(world, THATCH_STAIRS_RU, 0, 2, i, box);
                        setBlockState(world, THATCH_STAIRS_LU, 8, 2, i, box);
                        setBlockState(world, THATCH_STAIRS_R, 3, 6, i, box);
                        setBlockState(world, THATCH_STAIRS_L, 5, 6, i, box);
                        setBlockState(world, THATCH_SLAB, 4, 7, i, box);
                    }
                    for (int i = 3; i <= 7; i++) {
                        setBlockState(world, THATCH_STAIRS_LU, 3, 5, i, box);
                        setBlockState(world, THATCH_STAIRS_RU, 5, 5, i, box);
                        setBlockState(world, THATCH, 4, 6, i, box);
                    }
                    switch (roomtype){
                        case 1:
                            setBlockState(world, Blocks.PUMPKIN.getDefaultState(), 4, 1, 4, box);
                        case 2:
                            setBlockState(world, Blocks.RED_CONCRETE.getDefaultState(), 4, 1, 4, box);
                        case 3:
                            setBlockState(world, Blocks.IRON_BLOCK.getDefaultState(), 4, 1, 4, box);
                        case 4:
                            setBlockState(world, Blocks.CARTOGRAPHY_TABLE.getDefaultState(), 4, 1, 4, box);
                        case 5:
                            setBlockState(world, Blocks.SMOKER.getDefaultState(), 4, 1, 4, box);
                    }
                }
            }
            return true;
        }
    }
    public static class RohanHouseMidPiece extends RohanHousePiece {
        public RohanHouseMidPiece(Random rand, BlockPos pos, Direction _dir, byte _rt, byte _rv) {
            super(PIECE_ROHAN_HOUSE_MID, _dir, _rt, _rv);
            boundingBox = ModMathFunctions.getDirectedBox(pos.getX(),pos.getY(),pos.getZ(),_dir,8,6,5);
        }
        /*---------------------------------------------serialisation-------------------------------------------------*/
        public RohanHouseMidPiece(TemplateManager manager, CompoundNBT nbt) {
            super(PIECE_ROHAN_HOUSE_MID, nbt);
        }
        @Override protected void readAdditional(@Nonnull CompoundNBT tagCompound) {}

        /*---------------------------------------------generation-------------------------------------------------*/
        @Override public boolean create(IWorld world, ChunkGenerator<?> gen, Random rand, MutableBoundingBox box, ChunkPos pos) {
            if (roomtype>5) {
            }else{
                //roof
                boolean fire = (roomtype==0);
                wallStraightL(world,box,0,0,0);
                wallStraightR(world,box,0,0,0,fire);
                roofStraightL(world,box,0,0,0,fire);
                roofStraightR(world,box,0,0,0,fire);
                wallCont(world,box,0,0,5);
            }return true;
        }
    }
    public static class RohanHouseEndPiece extends RohanHousePiece{
        public RohanHouseEndPiece(Random rand, BlockPos pos, Direction _dir, byte _rt, byte _rv) {
            super(PIECE_ROHAN_HOUSE_END, _dir, _rt,_rv);
            setCoordBaseMode(_dir);
            boundingBox = ModMathFunctions.getDirectedBox(pos.getX(),pos.getY(),pos.getZ(),_dir,8,6,6);
        }
        /*---------------------------------------------serialisation-------------------------------------------------*/
        public RohanHouseEndPiece(TemplateManager manager, CompoundNBT nbt) {
            super(PIECE_ROHAN_HOUSE_END, nbt);
        }
        @Override protected void readAdditional(@Nonnull CompoundNBT tagCompound) {}

        /*---------------------------------------------generation-------------------------------------------------*/
        @Override public boolean create(IWorld world, ChunkGenerator<?> gen, Random rand, MutableBoundingBox box, ChunkPos pos) {
            if (roomtype>5) {
                if (roomvar==0){
                    //fullroom
                }else if(roomvar==1){
                    //open front
                }else if(roomvar==2){
                    //open back
                }
            }else{
                for (int l = 2; l <= 6; l++) {
                    setBlockState(world, LOGX, l, 4, 5, box);
                }
                buildSupportDown(world, box, 2, 5, LOGX);
                buildSupportDown(world, box, 6, 5, LOGX);

                wallStraightL(world,box,0,0,0);
                wallStraightR(world,box,0,0,0,false);
                roofStraightL(world,box,0,0,0,false);
                roofStraightR(world,box,0,0,0,false);
                roofEndEdge(world,box,0,0,0);
                //----------------------interior----------------------//
                if (roomtype==0) { //fire
                    setBlockState(world, COBBLE,4, 0, 0, box);
                    setBlockState(world, COBBLE,4, 0, 1, box);
                    setBlockState(world, COBBLE_STAIRS.with(StairsBlock.FACING,Direction.SOUTH),         4, 0, 2, box);
                    setBlockState(world, FIRE,4, 0, 3, box);
                    setBlockState(world, AIR, 4, 5, 3, box);
                    setBlockState(world, AIR, 4, 6, 3, box);
                    setBlockState(world, AIR, 4, 7, 3, box);
                    setBlockState(world, PLANKS, 2, 1, 4, box);
                    setBlockState(world, PLANKS, 2, 2, 4, box);
                    setBlockState(world, PLANKS, 2, 3, 4, box);
                    setBlockState(world, PLANKS, 2, 4, 4, box);
                    setBlockState(world, STONEBRICK, 3, 1,4, box);
                    setBlockState(world, STONEBRICK, 3, 2,4, box);
                    setBlockState(world, COBBLE, 3, 3, 4, box);
                    setBlockState(world, COBBLE_STAIRS, 3, 4, 4, box);
                    setBlockState(world, PLANKS, 3, 5, 4, box);
                    setBlockState(world, STONEBRICK, 4, 1, 4, box);
                    setBlockState(world, STONEBRICK, 4, 2, 4, box);
                    setBlockState(world, STONEBRICK, 4, 3, 4, box);
                    setBlockState(world, COBBLE, 4, 4, 4, box);
                    setBlockState(world, COBBLE, 4, 5, 4, box);
                    setBlockState(world, COBBLE, 4, 6, 4, box);
                    setBlockState(world, STONEBRICK, 5, 1, 4, box);
                    setBlockState(world, STONEBRICK, 5, 2, 4, box);
                    setBlockState(world, COBBLE, 5, 3, 4, box);
                    setBlockState(world, COBBLE_STAIRS, 5, 4, 4, box);
                    setBlockState(world, PLANKS, 5, 5, 4, box);
                    setBlockState(world, PLANKS, 6, 1, 4, box);
                    setBlockState(world, PLANKS, 6, 2, 4, box);
                    setBlockState(world, PLANKS, 6, 3, 4, box);
                    setBlockState(world, PLANKS, 6, 4, 4, box);
                    replaceAirAndLiquidDownwards(world, PLANKS, 2, 0, 4, box);
                    replaceAirAndLiquidDownwards(world, COBBLE, 3, 0, 4, box);
                    replaceAirAndLiquidDownwards(world, COBBLE, 4, 0, 4, box);
                    replaceAirAndLiquidDownwards(world, COBBLE, 5, 0, 4, box);
                    replaceAirAndLiquidDownwards(world, PLANKS, 6, 0, 4, box);
                    replaceAirAndLiquidDownwards(world, STONEBRICK, 3, 0, 5, box);
                    replaceAirAndLiquidDownwards(world, STONEBRICK, 4, 0, 5, box);
                    replaceAirAndLiquidDownwards(world, STONEBRICK, 5, 0, 5, box);

                    setBlockState(world, STONEBRICK, 3, 1,5, box);
                    setBlockState(world, STONEBRICK, 3, 2,5, box);
                    setBlockState(world, STONEBRICK_STAIRS.with(StairsBlock.FACING,Direction.EAST), 3, 3,5, box);
                    setBlockState(world, STONEBRICK, 4, 1,5, box);
                    setBlockState(world, STONEBRICK, 4, 2,5, box);
                    setBlockState(world, STONEBRICK, 4, 3,5, box);
                    setBlockState(world, STONEBRICK_STAIRS.with(StairsBlock.FACING,Direction.SOUTH), 4, 4,5, box);
                    setBlockState(world, STONEBRICK, 5, 1,5, box);
                    setBlockState(world, STONEBRICK, 5, 2,5, box);
                    setBlockState(world, STONEBRICK_STAIRS.with(StairsBlock.FACING,Direction.WEST), 5, 3,5, box);
                    //room
                    setBlockState(world, STONEBRICK, 3, 1, 3, box);
                    setBlockState(world, STONEBRICK_STAIRS, 3, 2, 3, box);
                    setBlockState(world, STONEBRICK, 5, 1, 3, box);
                    setBlockState(world, STONEBRICK_STAIRS, 5, 2, 3, box);
                    if (rand.nextBoolean()){
                        setBlockState(world, STONEBRICK_WALL, 3, 1, 2, box);
                        setBlockState(world, STONEBRICK_WALL, 5, 1, 2, box);
                        setBlockState(world, STONEBRICK_SLAB, 3, 2, 2, box);
                        setBlockState(world, STONEBRICK_SLAB, 4, 2, 2, box);
                        setBlockState(world, STONEBRICK_SLAB, 5, 2, 2, box);
                    }
                    //chimney
                    chimney(world,box,3,5,2);
                }
                else { //far wall
                    wallEnd(world,box,rand,2,0,4);
                    if (roomtype==1) {
                        //kitchen
                    } else if(roomtype == 2){
                        //bedmain
                    } else if (roomtype == 3){
                        //single bed
                    } else if (roomtype==4) {
                        //double bed
                    } else {
                        //study
                    }
                    if (roomvar==0){}//open roof
                    else if (roomvar==1){}//cieling ladder
                    else if (roomvar==2){}//cieling no ladder
                }
            }
            return true;
        }
    }

    public static class RohanHouseCornerPiece extends RohanHouseMirroredPiece{
        public RohanHouseCornerPiece(Random rand, BlockPos pos, Direction _dir, byte _rt, byte _rv, boolean _flip) {
            super(PIECE_ROHAN_HOUSE_CORNER, _dir, _rt,_flip,_rv);
            boundingBox = ModMathFunctions.getDirectedBox(pos.getX(),pos.getY(),pos.getZ(),_dir,8,6,5);
        }
        /*---------------------------------------------serialisation-------------------------------------------------*/
        public RohanHouseCornerPiece(TemplateManager manager, CompoundNBT nbt) {
            super(PIECE_ROHAN_HOUSE_CORNER, nbt);
        }
        @Override protected void readAdditional(@Nonnull CompoundNBT tagCompound) {}

        @Override public boolean create(IWorld world, ChunkGenerator<?> gen, Random rand, MutableBoundingBox box, ChunkPos pos) {
            if (roomtype>5) {
            }else{
                boolean fire = (roomtype==0);
                if (flip){
                    wallTurnL(world,box,0,0,0);
                    wallStraightR(world,box,0,0,0,fire);
                    roofTurnL(world,box,0,0,0,fire);
                    roofStraightR(world,box,0,0,0,fire);
                }else{
                    wallStraightL(world,box,0,0,0);
                    wallTurnR(world,box,0,0,0,fire);
                    roofStraightL(world,box,0,0,0,fire);
                    roofTurnR(world,box,0,0,0,fire);
                }
                wallEnd(world,box,rand,2,0,4);
                roofEndEdge(world,box,0,0,0);
            }
            return true;
        }
    }
    public static class RohanHouseTurnPiece extends RohanHouseMirroredPiece {
        public RohanHouseTurnPiece(Random rand, BlockPos pos, Direction _dir, byte _rt, byte _rv,boolean _flip) {
            super(PIECE_ROHAN_HOUSE_TURN, _dir, _rt, _flip, _rv);
            boundingBox = ModMathFunctions.getDirectedBox(pos.getX(),pos.getY(),pos.getZ(),_dir,8,6,5);
        }
        /*---------------------------------------------serialisation-------------------------------------------------*/
        public RohanHouseTurnPiece(TemplateManager manager, CompoundNBT nbt) {
            super(PIECE_ROHAN_HOUSE_TURN, nbt);
        }

        @Override public boolean create(IWorld world, ChunkGenerator<?> gen, Random rand, MutableBoundingBox box, ChunkPos pos) {
            if (roomtype>5) {
            }else{
                boolean fire = (roomtype==0);
                if (flip){
                    wallTurnL(world,box,0,0,0);
                    wallStraightR(world,box,0,0,0,fire);
                    roofTurnL(world,box,0,0,0,fire);
                    roofStraightR(world,box,0,0,0,fire);
                }else{
                    wallStraightL(world,box,0,0,0);
                    wallTurnR(world,box,0,0,0,fire);
                    roofStraightL(world,box,0,0,0,fire);
                    roofTurnR(world,box,0,0,0,fire);
                }
                wallCont(world,box,0,0,5);
            }
            return true;
        }
    }
    public static class RohanHouseTShapePiece extends RohanHousePiece{
        public RohanHouseTShapePiece(Random rand, BlockPos pos, Direction _dir, byte _rt, byte _rv) {
            super(PIECE_ROHAN_HOUSE_TSHAPE, _dir, _rt,_rv);
            boundingBox = ModMathFunctions.getDirectedBox(pos.getX(),pos.getY(),pos.getZ(),_dir,8,6,5);
        }
        /*---------------------------------------------serialisation-------------------------------------------------*/
        public RohanHouseTShapePiece(TemplateManager manager, CompoundNBT nbt) {
            super(PIECE_ROHAN_HOUSE_TSHAPE, nbt);
        }
        @Override protected void readAdditional(@Nonnull CompoundNBT tagCompound) {}

        @Override public boolean create(IWorld world, ChunkGenerator<?> gen, Random rand, MutableBoundingBox box, ChunkPos pos) {
            setBlockState(world, POST, 2, 3, 5, box);
            setBlockState(world, POST, 6, 3, 5, box);
            setBlockState(world, POST,4, 5, 5, box);
            //roof
            boolean fire = (roomtype==0);
            wallTurnL(world,box,0,0,0);
            wallTurnR(world,box,0,0,0, fire);
            roofTurnL(world,box,0,0,0, fire);
            roofTurnR(world,box,0,0,0, fire);
            wallEnd(world,box,rand,2,0,4);
            roofEndEdge(world,box,0,0,0);
            return true;
        }
    }
    public static class RohanHouseCrossPiece extends RohanHousePiece{
        public RohanHouseCrossPiece(Random rand, BlockPos pos, Direction _dir, byte _rt, byte _rv) {
            super(PIECE_ROHAN_HOUSE_CROSS, _dir,_rt,_rv);
            boundingBox = ModMathFunctions.getDirectedBox(pos.getX(),pos.getY(),pos.getZ(),_dir,8,6,5);
        }
        /*---------------------------------------------serialisation-------------------------------------------------*/
        public RohanHouseCrossPiece(TemplateManager manager, CompoundNBT nbt) {
            super(PIECE_ROHAN_HOUSE_CROSS, nbt);
        }
        @Override protected void readAdditional(@Nonnull CompoundNBT tagCompound) {}

        @Override public boolean create(IWorld world, ChunkGenerator<?> gen, Random rand, MutableBoundingBox box, ChunkPos pos) {
            //roof
            boolean fire = (roomtype==0);
            wallTurnL(world,box,0,0,0);
            wallTurnR(world,box,0,0,0, fire);
            roofTurnL(world,box,0,0,0, fire);
            roofTurnR(world,box,0,0,0, fire);
            wallCont(world,box,0,0,5);
            //interior
            return true;
        }
    }
}