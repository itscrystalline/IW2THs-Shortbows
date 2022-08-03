package com.thaddev.iw2thshortbows.mechanics.inits;

import com.thaddev.iw2thshortbows.IWant2TryHardsShortbows;
import com.thaddev.iw2thshortbows.mechanics.recipes.ApplyDiamondShortbowRecipe;
import com.thaddev.iw2thshortbows.mechanics.recipes.ApplySiliconPCBRecipe;
import com.thaddev.iw2thshortbows.mechanics.recipes.TippedDiamondHeadedArrowRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeSerializerInit {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, IWant2TryHardsShortbows.MODID);

    public static final RegistryObject<SimpleRecipeSerializer<TippedDiamondHeadedArrowRecipe>> TIPPED_DIAMOND_HEADED_ARROW =
        RECIPES.register(
            "crafting_special_tipped_diamond_headed_arrow",
            () -> new SimpleRecipeSerializer<>(TippedDiamondHeadedArrowRecipe::new)
        );

    public static final RegistryObject<RecipeSerializer<ApplySiliconPCBRecipe>> APPLY_SILICON_PCB =
        RECIPES.register(
            "smithing_special_apply_silicon_pcb",
            ApplySiliconPCBRecipe.Serializer::new
        );

    public static final RegistryObject<RecipeSerializer<ApplyDiamondShortbowRecipe>> APPLY_DIAMOND_SHORTBOW =
        RECIPES.register(
            "smithing_special_apply_diamond_shortbow",
            ApplyDiamondShortbowRecipe.Serializer::new
        );
}
