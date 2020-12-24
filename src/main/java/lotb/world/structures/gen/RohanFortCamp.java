package lotb.world.structures.gen;

import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import lotb.world.structures.pieces.RohanFortCampPieces;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class RohanFortCamp extends AbstractModStructure {

	public RohanFortCamp(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
		super(configFactoryIn, 64, 4);
	}
	@Override public IStartFactory getStartFactory() {
		return Start::new;
	}
	@Override protected int getSeedModifier() {
		return 14357619;
	}
	public class Start extends StructureStart{
		public Start(Structure<?> _structure, int x, int z, MutableBoundingBox _box, int c, long l) {
			super(_structure, x, z, _box, c, l);
		}
		
		@Override
		public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
			int xpos = chunkX << 4;
			int zpos = chunkZ << 4;
			int depth = generator.func_222532_b(xpos, zpos, Heightmap.Type.OCEAN_FLOOR_WG);
			
			RohanFortCampPieces.Gate startroom = new RohanFortCampPieces.Gate(0, rand, xpos, depth, zpos);
	    	components.add(startroom);
			Data campData = new Data();
	    	startroom.buildComponent(components, rand, campData, generator);
	    	recalculateStructureSize();
		}
	}
	public class Data{
		public int numberoftents = 0;
		public int numberofstables = 0;
		
		//public boolean buildingkitchen	= false;
		//public boolean buidlingarena	= false;
		public boolean captainPlaced	 = false;
		//tent
		public boolean utilityPlaced	 = false;
		public boolean planTentPlaced	 = false;
		public boolean kitchenPlaced	 = false;
		public boolean infirmaryPlaced	 = false;
		//single
		public boolean blacksmithPlaced	 = false;
		public boolean meleeAreaPlaced	 = false;
		public boolean firePlaced 		 = false;
		//double
		public boolean archeryAreaPlaced = false;
		public boolean cavalryAreaPlaced = false;
		public boolean eatingAreaPlaced	 = false;
		//small
		public boolean wellPlaced 		 = false;
		
		public boolean requiredMet() {
			return (numberoftents > 4) && (numberofstables > 2) 
				 && utilityPlaced && planTentPlaced  && infirmaryPlaced && blacksmithPlaced  && meleeAreaPlaced && firePlaced
				 && archeryAreaPlaced && cavalryAreaPlaced && wellPlaced;
		}
	}
}
