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
	/*I am a skilled programmer and software developer currently working on a variety of projects and want to begin teaching computer science. I have a strong passion to share my knowledge and experiences with aspiring programmers. my cousin recently asked me to start teaching them the basics and although it was in a non-proffesional setting, I felt like it gave me some understanding of what it would be like to teach young people about computer science. I have done work in a variety of programming languages and environments such as Java, Python, C#, C++ as well as a small amount of C, javascript and Haskell. I have experience developing code using the game engines unity and Godot, both of which are often used as educational tools. I also have used scratch and byob and am familiar with these programs so can use them in an educational context. Some key projects I have worked on include physics simulations, character AI, procedural generation, compute shaders and ray-tracing as well as many more. I also have had a large amount of experience coding mods for Minecraft in the past and in ongoing projects.*/
}
