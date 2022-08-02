package com.thaddev.coolideas.mechanics.inits;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

import java.util.List;

public class ConfiguredFeaturesInit {
    public static final List<OreConfiguration.TargetBlockState> NETHER_SILICON_ORES = List.of(
        OreConfiguration.target(OreFeatures.NETHER_ORE_REPLACEABLES, BlockInit.SILICON_ORE.get().defaultBlockState())
    );

    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> SILICON_ORE = FeatureUtils.register("silicon_ore",
        Feature.ORE, new OreConfiguration(NETHER_SILICON_ORES, 4));
}
