package lotb.tools;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;

/**
 * in researching this we found two major polarities, this mod's is ::clockwise::
 * 			clock	  anti
 * NORTH:  +s  -f	 -s  -f		-z	--			z - depth, forward, south
 * EAST	:  +f  +s    +f  -s		+x	+-			y - height, up
 * SOUTH:  -f  -s    -f  +s		+z	++			x - width, side, east
 * WEST	:  -s  +f    +s  +f		-x	-+			building coords go (side, up, forward)
 * 
 * this means that if a box is facing north, and polarity is clockwise, then the box will extend into the +x and -z
 * */

public class ModMathFunctions {
	public static MutableBoundingBox getDirectedBox(int x, int y, int z, Direction dir, int w, int h, int l) {
    	switch (dir) {
    	case EAST:
    		return new MutableBoundingBox(x, y, z, x+l, y+h, z+w);
    	case SOUTH:
    		return new MutableBoundingBox(x-w, y, z, x, y+h, z+l);
    	case WEST:
    		return new MutableBoundingBox(x-l, y, z-w, x,   y+h, z);
    	case NORTH:
    		return new MutableBoundingBox(x, y, z-l, x+w, y+h, z);
    	case UP:
    		return new MutableBoundingBox(x,   y, z-h, x+w, y+l, z);
    	case DOWN:
    		return new MutableBoundingBox(x-w, y-l, z, x,   y,   z+h);
    	}
    	return null;
    }
	public static BlockPos getPosInDir(BlockPos start, Direction dir, int dist) {
		int polarity = dir.getAxisDirection().getOffset();
		switch (dir.getAxis()) {
		case X:
			return new BlockPos(start.getX()+dist*polarity, start.getY(), start.getZ());
		case Y:
			return new BlockPos(start.getX(), start.getY()+dist*polarity,start.getZ());
		case Z:
			return new BlockPos(start.getX(), start.getY(), start.getZ()+dist*polarity);
		}
		return null;
	}
	public static BlockPos getAdgacentPosInDir(MutableBoundingBox start, Direction dir) {
		switch (dir) {
		case EAST:
			return new BlockPos(start.maxX+1, start.minY, start.minZ);
		case UP:
			return new BlockPos(start.minX, start.minY+1, start.maxZ);
		case SOUTH:
			return new BlockPos(start.maxX, start.minY, start.maxZ+1);
		case WEST:
			return new BlockPos(start.minX-1, start.minY, start.maxZ);
		case DOWN:
			return new BlockPos(start.minX, start.minY-1, start.minZ);
		case NORTH:
			return new BlockPos(start.minX, start.minY, start.minZ-1);
		}
		return null;
	}
	public static BlockPos getRelativePosInDir(BlockPos start, Direction dir, int forthOff, int heightOff, int sideOff) {
		switch (dir) {
		case EAST:
			return new BlockPos(start.getX()+forthOff, start.getY()+heightOff, start.getZ()+sideOff);
		case SOUTH:
			return new BlockPos(start.getX()-sideOff, start.getY()+heightOff, start.getZ()+forthOff);
		case WEST:
			return new BlockPos(start.getX()-forthOff, start.getY()+heightOff, start.getZ()-sideOff);
		case NORTH:
			return new BlockPos(start.getX()+sideOff, start.getY()+heightOff, start.getZ()-forthOff);
		case UP:
			return new BlockPos(start.getX()+sideOff, start.getY()+forthOff, start.getZ()+heightOff);
		case DOWN:
			return new BlockPos(start.getX()+sideOff, start.getY()-forthOff, start.getZ()+heightOff);
		}
		return null;
	}
}
