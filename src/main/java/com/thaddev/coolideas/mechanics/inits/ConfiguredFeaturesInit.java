package com.thaddev.coolideas.mechanics.inits;

import com.thaddev.coolideas.CoolIdeasMod;
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
        CoolIdeasMod.LOGGER.debug("Registering Configured Features for " + CoolIdeasMod.MODID + " (2/11)");
    }
}
