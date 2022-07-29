package com.thaddev.coolideas.mechanics.inits;

import com.google.common.base.Suppliers;
import com.thaddev.coolideas.CoolIdeasMod;
import com.thaddev.coolideas.mechanics.inits.BlockInit;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class ConfiguredFeaturesInit {
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, CoolIdeasMod.MODID);

    public static final Supplier<List<OreConfiguration.TargetBlockState>> NETHER_SILICON_ORES = Suppliers.memoize(
            () -> List.of(
                    OreConfiguration.target(OreFeatures.NETHER_ORE_REPLACEABLES, BlockInit.SILICON_ORE.get().defaultBlockState())
            )
    );

    public static final RegistryObject<ConfiguredFeature<?, ?>> SILICON_ORE = CONFIGURED_FEATURES.register("silicon_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(NETHER_SILICON_ORES.get(), 4)));
}
