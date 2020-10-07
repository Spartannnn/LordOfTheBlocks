package lotb.data;

import lotb.LotbMod;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.ExistingFileHelper;

public class BlockModelsDataGen extends BlockModelProvider {

    public BlockModelsDataGen(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, LotbMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
    }

    @Override
    public String getName() {
        return "Lotb Block Model Provider";
    }
}
