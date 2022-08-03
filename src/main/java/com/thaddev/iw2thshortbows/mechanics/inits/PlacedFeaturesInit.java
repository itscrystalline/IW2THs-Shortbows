package com.thaddev.iw2thshortbows.mechanics.inits;

import com.thaddev.iw2thshortbows.IWant2TryHardsShortbows;
import com.thaddev.iw2thshortbows.content.world.feature.OrePlacementUtils;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class PlacedFeaturesInit {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, IWant2TryHardsShortbows.MODID);

    public static final RegistryObject<PlacedFeature> SILICON_ORE_PLACED = PLACED_FEATURES.register("silicon_ore_placed",
            () -> new PlacedFeature(ConfiguredFeaturesInit.SILICON_ORE.getHolder().get(),
                    OrePlacementUtils.commonOrePlacement(
                            4,
                            HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(0), VerticalAnchor.aboveBottom(80))
                    )
            )
    );
}
