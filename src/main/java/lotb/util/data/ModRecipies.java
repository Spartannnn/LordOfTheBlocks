package lotb.util.data;

import java.util.function.Consumer;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.SingleItemRecipeBuilder;
import net.minecraft.item.crafting.Ingredient;

@Deprecated
public class ModRecipies extends RecipeProvider{

	public ModRecipies(DataGenerator generatorIn) {
		super(generatorIn);
	}
	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
		
	}

	protected  void registerVariants(Consumer<IFinishedRecipe> consumer,Block block,Block slab,Block stair,boolean stonecut) {
		ShapedRecipeBuilder.shapedRecipe(slab,  6).key('#', block).patternLine("###").build(consumer,slab.getRegistryName().getNamespace());
		ShapedRecipeBuilder.shapedRecipe(stair, 6).key('#', block).patternLine("##").patternLine("###").build(consumer,stair.getRegistryName().getNamespace());
		if (stonecut) {
			SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(block), slab, 2).build(consumer,slab.getRegistryName().getNamespace()+"_stonecut");
			SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(block), stair).build(consumer, stair.getRegistryName().getNamespace()+"_stonecut");
		}
	}protected void registerVariants(Consumer<IFinishedRecipe> consumer,Block block,Block slab,Block stair,Block wall,boolean stonecut) {
		registerVariants(consumer,block,slab,stair,stonecut);
		ShapedRecipeBuilder.shapedRecipe(wall, 6).key('#', block).patternLine("###").patternLine("###").patternLine("###").build(consumer,wall.getRegistryName().getNamespace());
		if (stonecut)
			SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(block), wall).build(consumer, wall.getRegistryName().getNamespace()+"_stonecut");
	}
}
