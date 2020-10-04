package lotb.world.structures.gen;

import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import lotb.world.structures.pieces.DwarvenMineshaftPieces;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class DwarvenMineshaft extends AbstractModStructure {

	public DwarvenMineshaft(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
		super(configFactoryIn,64,8);
	}
	@Override protected int getSeedModifier() {
		return 14339219;
	}
	@Override
	public IStartFactory getStartFactory() {
		return Start::new;
	}
	public class Start extends StructureStart{
		public Start(Structure<?> _structure, int x, int z, MutableBoundingBox _box, int c, long l) {
			super(_structure, x, z, _box, c, l);
		}
		
		@Override
		public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
			DwarvenMineshaftPieces.Room startroom = new DwarvenMineshaftPieces.Room(0, rand, (chunkX << 4), (chunkZ << 4));
	    	components.add(startroom);
	    	startroom.buildComponent(components, rand);
	    	recalculateStructureSize();
		}
	}
}
