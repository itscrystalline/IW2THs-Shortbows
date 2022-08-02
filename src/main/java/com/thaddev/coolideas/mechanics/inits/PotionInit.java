package com.thaddev.coolideas.mechanics.inits;

import com.thaddev.coolideas.CoolIdeasMod;
import com.thaddev.coolideas.mixins.BrewingRecipeRegistryMixin;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PotionInit {
    public static Potion VULNERABILITY;
    public static Potion VULNERABILITY_2;
    public static Potion VULNERABILITY_3;
    public static Potion VULNERABILITY_4;

    public static Potion VULNERABILITY_LONG;
    public static Potion VULNERABILITY_2_LONG;
    public static Potion VULNERABILITY_3_LONG;
    public static Potion VULNERABILITY_4_LONG;

    public static Potion registerPotion(String name, StatusEffect effect, int duration, int amplifier) {
        return Registry.register(
            Registry.POTION,
            new Identifier(CoolIdeasMod.MODID, name),
            new Potion(
                new StatusEffectInstance(effect, duration, amplifier)
            )
        );
    }

    public static void registerPotions() {
        CoolIdeasMod.LOGGER.debug("Registering Potions for " + CoolIdeasMod.MODID + " (10/11)");

        VULNERABILITY = registerPotion("vulnerability", EffectInit.VULNERABILITY, 2700, 0);
        VULNERABILITY_2 = registerPotion("vulnerability_2", EffectInit.VULNERABILITY, 1950, 1);
        VULNERABILITY_3 = registerPotion("vulnerability_3", EffectInit.VULNERABILITY, 1200, 2);
        VULNERABILITY_4 = registerPotion("vulnerability_4", EffectInit.VULNERABILITY, 450, 3);

        VULNERABILITY_LONG = registerPotion("vulnerability_long", EffectInit.VULNERABILITY, 4050, 0);
        VULNERABILITY_2_LONG = registerPotion("vulnerability_2_long", EffectInit.VULNERABILITY, 2925, 1);
        VULNERABILITY_3_LONG = registerPotion("vulnerability_3_long", EffectInit.VULNERABILITY, 1800, 2);
        VULNERABILITY_4_LONG = registerPotion("vulnerability_4_long", EffectInit.VULNERABILITY, 675, 3);

        registerPotionRecipes();
    }

    private static void registerPotionRecipes() {
        CoolIdeasMod.LOGGER.debug("Registering Potion Recipes for " + CoolIdeasMod.MODID + " (10/11)");

        //normal
        BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(Potions.WEAKNESS, Items.FERMENTED_SPIDER_EYE, VULNERABILITY);
        BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(VULNERABILITY, Items.SUSPICIOUS_STEW, VULNERABILITY_2);
        BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(VULNERABILITY_2, Items.SHULKER_SHELL, VULNERABILITY_3);
        BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(VULNERABILITY_3, Items.NETHER_STAR, VULNERABILITY_4);

        //long
        BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(VULNERABILITY, Items.REDSTONE, VULNERABILITY_LONG);
        BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(VULNERABILITY_2, Items.REDSTONE, VULNERABILITY_2_LONG);
        BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(VULNERABILITY_3, Items.REDSTONE_BLOCK, VULNERABILITY_3_LONG);
        BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(VULNERABILITY_4, Items.REDSTONE_BLOCK, VULNERABILITY_4_LONG);
    }
}
