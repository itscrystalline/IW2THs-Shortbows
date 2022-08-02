package com.thaddev.coolideas.mechanics.inits;

import com.thaddev.coolideas.content.world.feature.OrePlacementUtils;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class PlacedFeaturesInit {
    public static final Holder<PlacedFeature> SILICON_ORE_PLACED = PlacementUtils.register("silicon_ore_placed",
        ConfiguredFeaturesInit.SILICON_ORE,
        OrePlacementUtils.commonOrePlacement(
            4,
            HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(0), VerticalAnchor.aboveBottom(80))
        )
    );
}
