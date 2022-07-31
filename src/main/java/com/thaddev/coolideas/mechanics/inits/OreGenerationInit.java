package com.thaddev.coolideas.mechanics.inits;

import com.thaddev.coolideas.CoolIdeasMod;
import com.thaddev.coolideas.content.world.feature.PlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.gen.GenerationStep;

public class OreGenerationInit {
    public static void generateOres() {
        CoolIdeasMod.LOGGER.debug("Adding Ore Generations for " + CoolIdeasMod.MODID);
        BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(),
            GenerationStep.Feature.UNDERGROUND_ORES, PlacedFeatures.SILICON_ORE_PLACED.getKey().get()
        );
    }
}
