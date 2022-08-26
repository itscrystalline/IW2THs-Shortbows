package com.thaddev.iw2thshortbows.mechanics.inits;

import com.thaddev.iw2thshortbows.IWant2TryHardsShortbows;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreConfiguredFeatures;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.List;

public class ConfiguredFeaturesInit {
    public static final List<OreFeatureConfig.Target> NETHER_SILICON_ORES = List.of(
        OreFeatureConfig.createTarget(OreConfiguredFeatures.NETHERRACK, BlockInit.SILICON_ORE.getDefaultState())
    );

    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> SILICON_ORE = ConfiguredFeatures.register("silicon_ore",
        Feature.ORE, new OreFeatureConfig(NETHER_SILICON_ORES, 4));

    public static void registerConfiguredFeatures() {
        IWant2TryHardsShortbows.LOGGER.debug("Registering Configured Features for " + IWant2TryHardsShortbows.MODID + " (3/11)");
    }
}
