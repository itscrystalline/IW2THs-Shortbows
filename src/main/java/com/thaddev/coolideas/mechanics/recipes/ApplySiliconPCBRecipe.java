package com.thaddev.coolideas.mechanics.recipes;

import com.google.gson.JsonObject;
import com.thaddev.coolideas.content.items.materials.MicrochipItem;
import com.thaddev.coolideas.content.items.materials.SiliconPCBItem;
import com.thaddev.coolideas.mechanics.inits.ItemInit;
import com.thaddev.coolideas.mechanics.inits.RecipeSerializerInit;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.UpgradeRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ApplySiliconPCBRecipe extends UpgradeRecipe {
    final Ingredient base;
    final Ingredient addition;
    final ItemStack result;

    public ApplySiliconPCBRecipe(ResourceLocation resource, Ingredient base, Ingredient addition, ItemStack result) {
        super(resource, base, addition, result);
        this.base = base;
        this.addition = addition;
        this.result = result;
    }

    @Override
    @NotNull
    public ItemStack assemble(Container container) {
        ItemStack base = container.getItem(0);
        ItemStack add = container.getItem(1);
        ItemStack itemstack = this.getResultItem().copy();
        if (base.is(ItemInit.SILICON_PCB.get()) && add.is(ItemInit.MICROCHIP.get())) {
            if (MicrochipItem.getType(add) != MicrochipItem.MicrochipTypes.EMPTY){
                MicrochipItem.MicrochipTypes[] existingTypes = SiliconPCBItem.getTypes(base);
                MicrochipItem.MicrochipTypes[] newTypes = new MicrochipItem.MicrochipTypes[existingTypes.length + 1];
                System.arraycopy(existingTypes, 0, newTypes, 0, existingTypes.length);
                newTypes[newTypes.length - 1] = MicrochipItem.getType(add);
                SiliconPCBItem.setTypes(itemstack, newTypes);
            }
        }

        return itemstack;
    }

    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeSerializerInit.APPLY_SILICON_PCB.get();
    }

    public static class Serializer implements RecipeSerializer<ApplySiliconPCBRecipe> {
        @NotNull
        public ApplySiliconPCBRecipe fromJson(ResourceLocation resourceLocation, JsonObject json) {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "base"));
            Ingredient ingredient1 = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "addition"));
            ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            return new ApplySiliconPCBRecipe(resourceLocation, ingredient, ingredient1, itemstack);
        }

        @NotNull
        public ApplySiliconPCBRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf byteBuf) {
            Ingredient ingredient = Ingredient.fromNetwork(byteBuf);
            Ingredient ingredient1 = Ingredient.fromNetwork(byteBuf);
            ItemStack itemstack = byteBuf.readItem();
            return new ApplySiliconPCBRecipe(resourceLocation, ingredient, ingredient1, itemstack);
        }

        public void toNetwork(@NotNull FriendlyByteBuf byteBuf, ApplySiliconPCBRecipe recipe) {
            recipe.base.toNetwork(byteBuf);
            recipe.addition.toNetwork(byteBuf);
            byteBuf.writeItem(recipe.result);
        }
    }
}
