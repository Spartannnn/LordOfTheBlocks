package lotb.tools.data;

import lotb.data.ILootTableGeneratorData;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraft.world.storage.loot.conditions.MatchTool;
import net.minecraft.world.storage.loot.functions.ILootFunction;
import net.minecraft.world.storage.loot.functions.SetCount;

/**
 * This is your default block class. So:
 * If you have block like XYZOre and this ore drops 2 apples and 3 strings. Then you can create the XYZOre class,
 * and implement the ILootTableGenerationData interface.
 * Then you have 3 Methods.
 * <p>
 * In the first method: getDrops() you should return a list of items that should be dropped, in my example i write:
 * return createNNList(Items.APPLE, Items.STRING);
 * <p>
 * In the second method: getLootConditions() you should return the conditions. For ore return "defaultOreConditions()" from the interface self,
 * but if you want other conditions write: return createNNList(X); X = Conditions, conditions can be found in the:
 * net.minecraft.world.storage.loot.conditions. package
 * <p>
 * In the third method: getLootFunctions() you should return the functions. For ore return "defaultOreFunctions()" from the interface self,
 * but if you want other functions write: return createNNList(X); X = Functions, functions can be found in the:
 * net.minecraft.world.storage.loot.functions. package
 * <p>
 * But, if you want NO condition and NO function, then just return null.
 * <p>
 * IMPORTANT:
 * If a class does not implement this interface, the loot table provider create a loot table where the block himself drops.
 */
public class ExampleLootTableClass extends Block implements ILootTableGeneratorData {

    public ExampleLootTableClass(Properties properties) {
        super(properties);
    }

    @Override
    public NonNullList<Item> getDrops() {
        return createNNList(Items.APPLE, Items.STRING);
    }

    @Override
    public NonNullList<ILootCondition.IBuilder> getLootConditions() {
        return createNNList(MatchTool.builder(ItemPredicate.Builder.create().item(Items.BEDROCK))); //This means, that the apples and strings drops, if the player use a bedrock block to dig this block
    }

    @Override
    public NonNullList<ILootFunction.IBuilder> getLootFunctions() {
        return createNNList(
                SetCount.builder(RandomValueRange.of(2, 2)), //Here is the function for the apple - this means, that the count of drops from apples always 2
                SetCount.builder(RandomValueRange.of(0, 3))); //Here is the function for the strings - this means, that the count of drops form strings is random between 0 and 3
    }
}
