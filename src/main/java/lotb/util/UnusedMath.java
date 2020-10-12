package lotb.util;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;

public class UnusedMath {
	public static MutableBoundingBox getPointDirectedBox(int x, int y, int z, Direction dir, int w, int h, int l) {
    	switch (dir) {
    	case EAST:
    		return new MutableBoundingBox(x, y, z, x+l, y+h, z+w);
    	case UP:
    		return new MutableBoundingBox(x, y, z, x+w, y+l, z+h);
    	case SOUTH:
    		return new MutableBoundingBox(x, y, z, x+w, y+h, z+l);
    	case WEST:
    		return new MutableBoundingBox(x-l, y, z, x, y+h, z+w);
    	case DOWN:
    		return new MutableBoundingBox(x, y-l, z, x+w, y, z+h);
    	case NORTH:
    		return new MutableBoundingBox(x, y, z-l, x+w, y+h, z);
    	}
    	return null;
    }
	@Deprecated
	public static BlockPos getAdgacentPosInDir(MutableBoundingBox start, Direction dir) {
		switch (dir) {
		case EAST:
			return new BlockPos(start.maxX+1,start.minY,start.minZ);
		case UP:
			return new BlockPos(start.minX,start.maxY+1,start.minZ);
		case SOUTH:
			return new BlockPos(start.minX,start.minY,start.maxZ+1);
		case WEST:
			return new BlockPos(start.minX-1,start.maxY,start.maxZ);
		case DOWN:
			return new BlockPos(start.maxX,start.minY-1,start.maxZ);
		case NORTH:
			return new BlockPos(start.maxX,start.maxY,start.minZ-1);
		}
		return null;
	}
	public static MutableBoundingBox getOldDirectedBox(int x, int y, int z, Direction dir, int w, int h, int l) {
    	switch (dir) {
    	case EAST:
    		return new MutableBoundingBox(x, y, z, x+l, y+h, z+w);
    	case UP:
    		return new MutableBoundingBox(x, y, z, x+w, y+l, z+h);
    	case SOUTH:
    		return new MutableBoundingBox(x, y, z, x+w, y+h, z+l);
    	case WEST:
    		return new MutableBoundingBox(x-l, y-h, z-w, x, y, z);
    	case DOWN:
    		return new MutableBoundingBox(x-w, y-l, z-h, x, y, z);
    	case NORTH:
    		return new MutableBoundingBox(x-w, y-h, z-l, x, y, z);
    	}
    	return null;
    }
	public static MutableBoundingBox getAdgacentDirectedBox(MutableBoundingBox orig, Direction dir, int w,int h,int l) {
		switch (dir) {
    	case EAST:
    		return new MutableBoundingBox( orig.maxX+1 , orig.minY , orig.minZ , orig.maxX+1+l , orig.minY+h , orig.minZ+w );
    	case UP:
    		return new MutableBoundingBox( orig.minX , orig.maxY+1 , orig.minZ , orig.minX+w , orig.maxY+1+l , orig.minZ+h );
    	case SOUTH:
    		return new MutableBoundingBox( orig.minX , orig.minY , orig.maxZ+1 , orig.minX+w , orig.minY+h , orig.maxZ+1+l );
    	case WEST:
    		return new MutableBoundingBox( orig.minX-1-l , orig.minY , orig.minZ , orig.minX-1 , orig.minY+h , orig.minZ+w );
    	case DOWN:
    		return new MutableBoundingBox( orig.minX , orig.minY-1-l , orig.minZ , orig.minX+w , orig.minY-1 , orig.minZ+h );
    	case NORTH:
    		return new MutableBoundingBox( orig.minX , orig.minY , orig.minZ-1-l , orig.minX+w , orig.minY+h , orig.minZ-1 );
    	}
    	return null;
	}
}
