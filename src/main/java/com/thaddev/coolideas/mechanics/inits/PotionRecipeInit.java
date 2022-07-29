package com.thaddev.coolideas.mechanics.inits;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;

public class PotionRecipeInit {
    public static void genNormalRecipe(Potion inputPot, Potion outputPot, Item ingredient) {
        ItemStack input = new ItemStack(Items.POTION), output = new ItemStack(Items.POTION);
        PotionUtils.setPotion(input, inputPot);
        PotionUtils.setPotion(output, outputPot);
        BrewingRecipeRegistry.addRecipe(Ingredient.of(input), Ingredient.of(ingredient), output);
    }
    public static void genSplashRecipe(Potion pot) {
        ItemStack input = new ItemStack(Items.POTION), output = new ItemStack(Items.SPLASH_POTION);
        PotionUtils.setPotion(input, pot);
        PotionUtils.setPotion(output, pot);
        BrewingRecipeRegistry.addRecipe(Ingredient.of(input), Ingredient.of(Items.GUNPOWDER), output);
    }
    public static void genLingeringRecipe(Potion pot) {
        ItemStack input = new ItemStack(Items.POTION), output = new ItemStack(Items.LINGERING_POTION);
        PotionUtils.setPotion(input, pot);
        PotionUtils.setPotion(output, pot);
        BrewingRecipeRegistry.addRecipe(Ingredient.of(input), Ingredient.of(Items.DRAGON_BREATH), output);
    }

    public static void register() {
        //normal
        genNormalRecipe(Potions.WEAKNESS, PotionInit.VULNERABILITY.get(), Items.FERMENTED_SPIDER_EYE);
        genNormalRecipe(PotionInit.VULNERABILITY.get(), PotionInit.VULNERABILITY_2.get(), Items.SUSPICIOUS_STEW);
        genNormalRecipe(PotionInit.VULNERABILITY_2.get(), PotionInit.VULNERABILITY_3.get(), Items.SHULKER_SHELL);
        genNormalRecipe(PotionInit.VULNERABILITY_3.get(), PotionInit.VULNERABILITY_4.get(), Items.NETHER_STAR);

        //long
        genNormalRecipe(PotionInit.VULNERABILITY.get(), PotionInit.VULNERABILITY_LONG.get(), Items.REDSTONE);
        genNormalRecipe(PotionInit.VULNERABILITY_2.get(), PotionInit.VULNERABILITY_2_LONG.get(), Items.REDSTONE);
        genNormalRecipe(PotionInit.VULNERABILITY_3.get(), PotionInit.VULNERABILITY_3_LONG.get(), Items.REDSTONE_BLOCK);
        genNormalRecipe(PotionInit.VULNERABILITY_4.get(), PotionInit.VULNERABILITY_4_LONG.get(), Items.REDSTONE_BLOCK);

        //splash - normal
        genSplashRecipe(PotionInit.VULNERABILITY.get());
        genSplashRecipe(PotionInit.VULNERABILITY_2.get());
        genSplashRecipe(PotionInit.VULNERABILITY_3.get());
        genSplashRecipe(PotionInit.VULNERABILITY_4.get());

        //splash - long
        genSplashRecipe(PotionInit.VULNERABILITY_LONG.get());
        genSplashRecipe(PotionInit.VULNERABILITY_2_LONG.get());
        genSplashRecipe(PotionInit.VULNERABILITY_3_LONG.get());
        genSplashRecipe(PotionInit.VULNERABILITY_4_LONG.get());

        //lingering - normal
        genLingeringRecipe(PotionInit.VULNERABILITY.get());
        genLingeringRecipe(PotionInit.VULNERABILITY_2.get());
        genLingeringRecipe(PotionInit.VULNERABILITY_3.get());
        genLingeringRecipe(PotionInit.VULNERABILITY_4.get());

        //lingering - long
        genLingeringRecipe(PotionInit.VULNERABILITY_LONG.get());
        genLingeringRecipe(PotionInit.VULNERABILITY_2_LONG.get());
        genLingeringRecipe(PotionInit.VULNERABILITY_3_LONG.get());
        genLingeringRecipe(PotionInit.VULNERABILITY_4_LONG.get());

    }
}