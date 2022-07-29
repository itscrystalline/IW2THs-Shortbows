package com.thaddev.coolideas.mechanics.recipes;

import com.thaddev.coolideas.mechanics.inits.ItemInit;
import com.thaddev.coolideas.mechanics.inits.RecipeSerializerInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class TippedDiamondHeadedArrowRecipe extends CustomRecipe {
    public TippedDiamondHeadedArrowRecipe(ResourceLocation resource) {
        super(resource);
    }

    public boolean matches(CraftingContainer craftingContainer, Level world) {
        if (craftingContainer.getWidth() == 3 && craftingContainer.getHeight() == 3) {
            for(int i = 0; i < craftingContainer.getWidth(); ++i) {
                for(int j = 0; j < craftingContainer.getHeight(); ++j) {
                    ItemStack itemstack = craftingContainer.getItem(i + j * craftingContainer.getWidth());
                    if (itemstack.isEmpty()) {
                        return false;
                    }

                    if (i == 1 && j == 1) {
                        if (!itemstack.is(Items.LINGERING_POTION)) {
                            return false;
                        }
                    } else if (!itemstack.is(ItemInit.DIAMOND_HEADED_ARROW.get())) {
                        return false;
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    @NotNull
    public ItemStack assemble(CraftingContainer craftingContainer) {
        ItemStack itemstack = craftingContainer.getItem(1 + craftingContainer.getWidth());
        if (!itemstack.is(Items.LINGERING_POTION)) {
            return ItemStack.EMPTY;
        } else {
            ItemStack itemstack1 = new ItemStack(ItemInit.TIPPED_DIAMOND_HEADED_ARROW.get(), 8);
            PotionUtils.setPotion(itemstack1, PotionUtils.getPotion(itemstack));
            PotionUtils.setCustomEffects(itemstack1, PotionUtils.getCustomEffects(itemstack));
            return itemstack1;
        }
    }

    public boolean canCraftInDimensions(int p_44505_, int p_44506_) {
        return p_44505_ >= 2 && p_44506_ >= 2;
    }

    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeSerializerInit.TIPPED_DIAMOND_HEADED_ARROW.get();
    }
}
