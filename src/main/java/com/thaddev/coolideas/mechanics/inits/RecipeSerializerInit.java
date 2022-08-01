package com.thaddev.coolideas.mechanics.inits;

import com.thaddev.coolideas.CoolIdeasMod;
import com.thaddev.coolideas.mechanics.recipes.ApplyDiamondShortbowRecipe;
import com.thaddev.coolideas.mechanics.recipes.ApplySiliconPCBRecipe;
import com.thaddev.coolideas.mechanics.recipes.TippedDiamondHeadedArrowRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.Identifier;

public class RecipeSerializerInit {
    public static SpecialRecipeSerializer<TippedDiamondHeadedArrowRecipe> TIPPED_DIAMOND_HEADED_ARROW = registerRecipeSerializer(
        new Identifier(CoolIdeasMod.MODID, "crafting_special_tipped_diamond_headed_arrow"),
        new SpecialRecipeSerializer<>(TippedDiamondHeadedArrowRecipe::new)
    );
    public static RecipeSerializer<ApplySiliconPCBRecipe> APPLY_SILICON_PCB = registerRecipeSerializer(
        new Identifier(CoolIdeasMod.MODID, "smithing_special_apply_silicon_pcb"),
        new ApplySiliconPCBRecipe.Serializer()
    );
    public static RecipeSerializer<ApplyDiamondShortbowRecipe> APPLY_DIAMOND_SHORTBOW = registerRecipeSerializer(
        new Identifier(CoolIdeasMod.MODID, "smithing_special_apply_diamond_shortbow"),
        new ApplyDiamondShortbowRecipe.Serializer()
    );

    public static void registerRecipes() {
        CoolIdeasMod.LOGGER.debug("Registering Recipes for " + CoolIdeasMod.MODID);
    }

    public static <S extends RecipeSerializer<T>, T extends Recipe<?>> S registerRecipeSerializer(Identifier name, S serializer) {
        return RecipeSerializer.register(name.toString(), serializer);
    }
}
