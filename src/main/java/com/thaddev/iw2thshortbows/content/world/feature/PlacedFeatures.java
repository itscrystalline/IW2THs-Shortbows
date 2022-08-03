package com.thaddev.iw2thshortbows.content.world.feature;

import com.thaddev.iw2thshortbows.mechanics.inits.ConfiguredFeaturesInit;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;

public class PlacedFeatures {
    public static final RegistryEntry<PlacedFeature> SILICON_ORE_PLACED = net.minecraft.world.gen.feature.PlacedFeatures.register("silicon_ore_placed",
        ConfiguredFeaturesInit.SILICON_ORE,
        OrePlacementUtils.modifiersWithCount(
            4,
            HeightRangePlacementModifier.trapezoid(YOffset.fixed(0), YOffset.fixed(80))
        )
    );
}
