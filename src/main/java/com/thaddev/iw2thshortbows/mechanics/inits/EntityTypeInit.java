package com.thaddev.iw2thshortbows.mechanics.inits;

import com.thaddev.iw2thshortbows.IWant2TryHardsShortbows;
import com.thaddev.iw2thshortbows.content.entities.projectiles.DiamondHeadedArrow;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityTypeInit {

    public static final EntityType<DiamondHeadedArrow> DIAMOND_HEADED_ARROW = Registry.register(Registry.ENTITY_TYPE,
        new Identifier(IWant2TryHardsShortbows.MODID, "diamond_headed_arrow"),
        FabricEntityTypeBuilder.<DiamondHeadedArrow>create(SpawnGroup.MISC, DiamondHeadedArrow::new)
            .dimensions(EntityDimensions.fixed(0.5F, 0.5F))
            .trackRangeBlocks(4)
            .trackedUpdateRate(10)
            .build()
    );

    public static void registerEntityTypes() {
        IWant2TryHardsShortbows.LOGGER.debug("Registering Entity Types for " + IWant2TryHardsShortbows.MODID + " (5/11)");
    }
}
