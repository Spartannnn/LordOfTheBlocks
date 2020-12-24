package lotb.world.structures.gen;

import com.mojang.datafixers.Dynamic;
import lotb.util.ModMathFunctions;
import lotb.world.structures.pieces.RohanHousePiece;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.function.Function;

public class RohanBasteon extends AbstractModStructure {

    public RohanBasteon(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
        super(configFactoryIn, 5, 1);
    }
    @Override public IStartFactory getStartFactory() {
        return Start::new;
    }
    @Override protected int getSeedModifier() {
        return 14357618;
    }

    public class Start extends StructureStart {
        public Start(Structure<?> _structure, int x, int z, MutableBoundingBox _box, int c, long l) {
            super(_structure, x, z, _box, c, l);
        }

        @Override public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
            int xpos = chunkX << 4;
            int zpos = chunkZ << 4;
            int depth = generator.func_222532_b(xpos+4, zpos-2, Heightmap.Type.OCEAN_FLOOR_WG);

            RohanHousePiece.RohanHouseEntrancePiece startroom = new RohanHousePiece.RohanHouseEntrancePiece( rand, xpos, depth-1, zpos);
            components.add(startroom);
            startroom.buildComponent(components,rand,generator);
            recalculateStructureSize();
        }
    }
}
