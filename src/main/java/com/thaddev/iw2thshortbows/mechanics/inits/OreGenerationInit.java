package com.thaddev.iw2thshortbows.mechanics.inits;

import com.thaddev.iw2thshortbows.IWant2TryHardsShortbows;
import com.thaddev.iw2thshortbows.content.world.feature.PlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.gen.GenerationStep;

public class OreGenerationInit {
    public static void generateOres() {
        IWant2TryHardsShortbows.LOGGER.debug("Adding Ore Generations for " + IWant2TryHardsShortbows.MODID + " (8/11)");
        BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(),
            GenerationStep.Feature.UNDERGROUND_ORES, PlacedFeatures.SILICON_ORE_PLACED.getKey().get()
        );
    }
}
