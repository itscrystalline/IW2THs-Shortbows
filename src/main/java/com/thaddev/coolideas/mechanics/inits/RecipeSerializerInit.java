package com.thaddev.coolideas.mechanics.inits;

import com.thaddev.coolideas.CoolIdeasMod;
import com.thaddev.coolideas.mechanics.recipes.ApplyDiamondShortbowRecipe;
import com.thaddev.coolideas.mechanics.recipes.ApplySiliconPCBRecipe;
import com.thaddev.coolideas.mechanics.recipes.TippedDiamondHeadedArrowRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;

public class RecipeSerializerInit {
    public static SpecialRecipeSerializer<TippedDiamondHeadedArrowRecipe> TIPPED_DIAMOND_HEADED_ARROW;
    public static RecipeSerializer<ApplySiliconPCBRecipe> APPLY_SILICON_PCB;
    public static RecipeSerializer<ApplyDiamondShortbowRecipe> APPLY_DIAMOND_SHORTBOW;

    public static void registerRecipes() {
        CoolIdeasMod.LOGGER.debug("Registering Recipes for " + CoolIdeasMod.MODID);

        TIPPED_DIAMOND_HEADED_ARROW = registerRecipeSerializer(
            "crafting_special_tipped_diamond_headed_arrow",
            new SpecialRecipeSerializer<>(TippedDiamondHeadedArrowRecipe::new)
        );
//        TIPPED_DIAMOND_HEADED_ARROW = Registry.register(
//            Registry.RECIPE_SERIALIZER,
//            new Identifier(CoolIdeasMod.MODID, "crafting_special_tipped_diamond_headed_arrow"),
//            new SpecialRecipeSerializer<>(id -> new TippedDiamondHeadedArrowRecipe(new Identifier(CoolIdeasMod.MODID, "crafting_special_tipped_diamond_headed_arrow")))
//        );
//        Registry.register(
//            Registry.RECIPE_TYPE,
//            new Identifier(
//                CoolIdeasMod.MODID,
//                TippedDiamondHeadedArrowRecipe.Type.ID
//            ),
//            TippedDiamondHeadedArrowRecipe.Type.INSTANCE
//        );
        APPLY_SILICON_PCB = registerRecipeSerializer(
            "smithing_special_apply_silicon_pcb",
            new ApplySiliconPCBRecipe.Serializer()
        );
        APPLY_DIAMOND_SHORTBOW = registerRecipeSerializer(
            "smithing_special_apply_diamond_shortbow",
            new ApplyDiamondShortbowRecipe.Serializer()
        );
    }

    public static <S extends RecipeSerializer<T>, T extends Recipe<?>> S registerRecipeSerializer(String name, S serializer) {
        return RecipeSerializer.register(name, serializer);
    }
}
