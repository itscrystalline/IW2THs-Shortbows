package com.thaddev.coolideas.mechanics.recipes;

import com.google.gson.JsonObject;
import com.thaddev.coolideas.content.items.materials.MicrochipItem;
import com.thaddev.coolideas.content.items.materials.SiliconPCBItem;
import com.thaddev.coolideas.content.items.weapons.ShortBowBase;
import com.thaddev.coolideas.mechanics.inits.ItemInit;
import com.thaddev.coolideas.mechanics.inits.RecipeSerializerInit;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.SmithingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

import java.util.stream.Stream;

public class ApplyDiamondShortbowRecipe extends SmithingRecipe {
    final Ingredient base;
    final Ingredient addition;
    final ItemStack result;

    public ApplyDiamondShortbowRecipe(Identifier id, Ingredient base, Ingredient addition, ItemStack result) {
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
        NbtCompound compoundtag = base.getNbt();
        if (base.isOf(ItemInit.DIAMOND_SHORTBOW) && add.isOf(ItemInit.SILICON_PCB)) {
            MicrochipItem.MicrochipTypes[] types;
            if ((types = SiliconPCBItem.getTypes(add)).length > 0) {
                for (MicrochipItem.MicrochipTypes type : types) {
                    if (type == MicrochipItem.MicrochipTypes.HOMING) {
                        itemstack.setNbt(compoundtag);
                        ShortBowBase.setDoesHomeArrow(itemstack, true);
                    }
                }
            }
        }

        return itemstack;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializerInit.APPLY_DIAMOND_SHORTBOW;
    }

    @Override
    public boolean isEmpty() {
        return Stream.of(this.base, this.addition).anyMatch(ingredient -> ingredient.getMatchingStacks().length == 0);
    }

    public static class Serializer implements RecipeSerializer<ApplyDiamondShortbowRecipe> {
        @Override
        public ApplyDiamondShortbowRecipe read(Identifier identifier, JsonObject jsonObject) {
            Ingredient ingredient = Ingredient.fromJson(JsonHelper.getObject(jsonObject, "base"));
            Ingredient ingredient2 = Ingredient.fromJson(JsonHelper.getObject(jsonObject, "addition"));
            ItemStack itemStack = ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "result"));
            return new ApplyDiamondShortbowRecipe(identifier, ingredient, ingredient2, itemStack);
        }

        @Override
        public ApplyDiamondShortbowRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            Ingredient ingredient = Ingredient.fromPacket(packetByteBuf);
            Ingredient ingredient2 = Ingredient.fromPacket(packetByteBuf);
            ItemStack itemStack = packetByteBuf.readItemStack();
            return new ApplyDiamondShortbowRecipe(identifier, ingredient, ingredient2, itemStack);
        }

        @Override
        public void write(PacketByteBuf packetByteBuf, ApplyDiamondShortbowRecipe smithingRecipe) {
            smithingRecipe.base.write(packetByteBuf);
            smithingRecipe.addition.write(packetByteBuf);
            packetByteBuf.writeItemStack(smithingRecipe.result);
        }
    }
}
