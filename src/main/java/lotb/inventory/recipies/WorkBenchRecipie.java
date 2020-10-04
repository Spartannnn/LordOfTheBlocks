package lotb.inventory.recipies;

import com.google.gson.JsonObject;

import lotb.registries.ModRecipies;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SingleItemRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class WorkBenchRecipie extends SingleItemRecipe {
	public WorkBenchRecipie(IRecipeType<?> type, IRecipeSerializer<?> serializer, ResourceLocation id, String group, Ingredient ingredient, ItemStack result) {
		super(type, serializer, id, group, ingredient, result);
	}
	@Override
	public boolean matches(IInventory inv, World worldIn) {
		return ingredient.test(inv.getStackInSlot(0));
	}
	public static class Serializer<R extends WorkBenchRecipie> extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<R>
    {
        private final ModRecipies.IRecipeFactory<R> factory;
        
        public Serializer(ModRecipies.IRecipeFactory<R> _factory){
            factory = _factory;
        }

        @Override @SuppressWarnings("deprecation")
        public R read(ResourceLocation recipeId, JsonObject json)
        {
            String group = JSONUtils.getString(json, "group", "");
            Ingredient ingredient;
            if(JSONUtils.isJsonArray(json, "ingredient")){
                ingredient = Ingredient.deserialize(JSONUtils.getJsonArray(json, "ingredient"));
            }else{
                ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(json, "ingredient"));
            }
            String result = JSONUtils.getString(json, "result");
            int count = JSONUtils.getInt(json, "count");
            ItemStack stack = new ItemStack(Registry.ITEM.getOrDefault(new ResourceLocation(result)), count);
            return factory.create(recipeId, group, ingredient, stack);
        }
        @Override
        public R read(ResourceLocation recipeId, PacketBuffer buffer){
            String group = buffer.readString(32767);
            Ingredient ingredient = Ingredient.read(buffer);
            ItemStack stack = buffer.readItemStack();
            return factory.create(recipeId, group, ingredient, stack);
        }
        @Override
        public void write(PacketBuffer buffer, R recipe){
            buffer.writeString(recipe.group);
            recipe.ingredient.write(buffer);
            buffer.writeItemStack(recipe.result);
        }

        
    }

}
