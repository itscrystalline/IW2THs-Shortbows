package com.thaddev.iw2thshortbows.mechanics.recipes;

import com.google.gson.JsonObject;
import com.thaddev.iw2thshortbows.content.items.materials.MicrochipItem;
import com.thaddev.iw2thshortbows.content.items.materials.SiliconPCBItem;
import com.thaddev.iw2thshortbows.mechanics.inits.ItemInit;
import com.thaddev.iw2thshortbows.mechanics.inits.RecipeSerializerInit;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.SmithingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

import java.util.stream.Stream;

public class ApplySiliconPCBRecipe extends SmithingRecipe {
    final Ingredient base;
    final Ingredient addition;
    final ItemStack result;

    public ApplySiliconPCBRecipe(Identifier id, Ingredient base, Ingredient addition, ItemStack result) {
        super(id, base, addition, result);
        this.base = base;
        this.addition = addition;
        this.result = result;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return this.base.test(inventory.getStack(0)) && this.addition.test(inventory.getStack(1));
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        ItemStack base = inventory.getStack(0);
        ItemStack add = inventory.getStack(1);
        ItemStack itemstack = this.result.copy();
        if (base.isOf(ItemInit.SILICON_PCB) && add.isOf(ItemInit.MICROCHIP)) {
            if (MicrochipItem.getType(add) != MicrochipItem.MicrochipTypes.EMPTY) {
                MicrochipItem.MicrochipTypes[] existingTypes = SiliconPCBItem.getTypes(base);
                MicrochipItem.MicrochipTypes[] newTypes = new MicrochipItem.MicrochipTypes[existingTypes.length + 1];
                System.arraycopy(existingTypes, 0, newTypes, 0, existingTypes.length);
                newTypes[newTypes.length - 1] = MicrochipItem.getType(add);
                SiliconPCBItem.setTypes(itemstack, newTypes);
            }
        }

        return itemstack;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializerInit.APPLY_SILICON_PCB;
    }

    @Override
    public boolean isEmpty() {
        return Stream.of(this.base, this.addition).anyMatch(ingredient -> ingredient.getMatchingStacks().length == 0);
    }

    public static class Serializer implements RecipeSerializer<ApplySiliconPCBRecipe> {
        @Override
        public ApplySiliconPCBRecipe read(Identifier identifier, JsonObject jsonObject) {
            Ingredient ingredient = Ingredient.fromJson(JsonHelper.getObject(jsonObject, "base"));
            Ingredient ingredient2 = Ingredient.fromJson(JsonHelper.getObject(jsonObject, "addition"));
            ItemStack itemStack = ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "result"));
            return new ApplySiliconPCBRecipe(identifier, ingredient, ingredient2, itemStack);
        }

        @Override
        public ApplySiliconPCBRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            Ingredient ingredient = Ingredient.fromPacket(packetByteBuf);
            Ingredient ingredient2 = Ingredient.fromPacket(packetByteBuf);
            ItemStack itemStack = packetByteBuf.readItemStack();
            return new ApplySiliconPCBRecipe(identifier, ingredient, ingredient2, itemStack);
        }

        @Override
        public void write(PacketByteBuf packetByteBuf, ApplySiliconPCBRecipe smithingRecipe) {
            smithingRecipe.base.write(packetByteBuf);
            smithingRecipe.addition.write(packetByteBuf);
            packetByteBuf.writeItemStack(smithingRecipe.result);
        }
    }
}
