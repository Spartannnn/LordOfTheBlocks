package lotb.data;

import lotb.util.data.ModLootTables;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.util.NonNullList;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraft.world.storage.loot.conditions.MatchTool;
import net.minecraft.world.storage.loot.functions.ApplyBonus;
import net.minecraft.world.storage.loot.functions.ExplosionDecay;
import net.minecraft.world.storage.loot.functions.ILootFunction;

/**
 * Use this interface if you want to add a loot table for the block
 * The {@link ModLootTables} class iterate through all registered blocks and check for this interface.
 * If a class implement this interface, then it will generate custom loot tables for the block otherwise it generate
 * a loot table for a self drop
 */
public interface ILootTableGeneratorData {

    /**
     * @return a list of items that should be dropped
     */
    NonNullList<Item> getDrops();

    /**
     * @return a list of conditions
     */
    NonNullList<ILootCondition.IBuilder> getLootConditions();

    /**
     * NOTE: If you use not the defaultOreFunctions method, then you HAVE TO return
     * <code>
     *     createNNList({@link ExplosionDecay#builder()});
     * </code>
     *
     * @return a list of functions
     */
    NonNullList<ILootFunction.IBuilder> getLootFunctions();

    /**
     * Use this method to create easily a NonNullList
     *
     * @param elements the elements in the list
     * @param <T>      the type
     * @return the created nn list
     */
    default <T> NonNullList<T> createNNList(T... elements) {
        NonNullList<T> list = NonNullList.withSize(elements.length, elements[0]);
        for (int i = 0; i < elements.length; i++)
            list.set(i, elements[i]);
        return list;
    }

    /**
     * @return conditions for ore blocks
     */
    default NonNullList<ILootCondition.IBuilder> defaultOreConditions() {
        return createNNList(MatchTool.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.exactly(1)))));
    }

    /**
     * @return functions for ore blocks
     */
    default NonNullList<ILootFunction.IBuilder> defaultOreFunctions() {
        return createNNList(ApplyBonus.oreDrops(Enchantments.FORTUNE), ExplosionDecay.builder());
    }

}
