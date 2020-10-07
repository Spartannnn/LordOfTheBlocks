package lotb.data;

import lotb.LotbMod;
import lotb.blocks.BarsBlock;
import lotb.blocks.ModDoorBlock;
import lotb.registries.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.fml.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class BlockStatesDataGen extends BlockStateProvider {

    private static final Logger LOGGER = LogManager.getLogger();

    public BlockStatesDataGen(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, LotbMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
    }

    private void registerStates() {
        ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {

            if (block instanceof StairsBlock) {
                this.stairsBlock((StairsBlock) block, this.lotbBlockLoc(block));
            } else if (block instanceof ModDoorBlock) {
                this.doorBlock((DoorBlock) block, this.lotbLoc("block/" + name(block) + "_bottom"), this.lotbLoc("block/" + name(block) + "_top"));
            } else if (block instanceof SlabBlock) {
                this.slabBlock((SlabBlock) block, this.lotbLoc("block/" + name(block)), this.lotbLoc("block/" + name(block).replace("_slab", "")),this.lotbLoc("block/" + name(block) + "_double"), this.lotbLoc("block/" + name(block) + "_top"));
            } else if (block instanceof WallBlock) {
                this.wallBlock((WallBlock) block, this.lotbLoc("block/" + name(block)));
            } else if (block instanceof BarsBlock) {

            } else if (block instanceof RotatedPillarBlock) {

            } else if (block instanceof FenceBlock) {

            } else {
                this.simpleBlock(block);
            }
        });
    }

    private void registerBarsState(Block block) {
    }

    private String name(Block block) {
        return Objects.requireNonNull(block.getRegistryName()).getPath();
    }

    private ResourceLocation lotbBlockLoc(Block block) {
        return this.lotbLoc("block/" + Objects.requireNonNull(block.getRegistryName()).getPath());
    }

    private ResourceLocation lotbLoc(String path) {
        return new ResourceLocation(LotbMod.MODID, path);
    }

}
