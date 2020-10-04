package lotb.registries;

import lotb.LotbMod;
import lotb.inventory.recipies.WorkBenchRecipie;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class ModRecipies {
	public static final IRecipeType<WorkBenchRecipie> ROHAN_WORKBENCH = register(new ResourceLocation(LotbMod.MODID, "rohan"));

    private static <T extends IRecipe<?>> net.minecraft.item.crafting.IRecipeType<T> register(final ResourceLocation resource){
        return Registry.register(Registry.RECIPE_TYPE, resource, new IRecipeType<T>(){
            @Override public String toString(){
                return resource.toString();
            }
        });
    }
    public interface IRecipeFactory<R extends WorkBenchRecipie>{
        R create(ResourceLocation id, String group, Ingredient ingredient, ItemStack stack);
    }
}
