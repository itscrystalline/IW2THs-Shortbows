package com.thaddev.iw2thshortbows.mechanics.inits;

import com.thaddev.iw2thshortbows.IWant2TryHardsShortbows;
import com.thaddev.iw2thshortbows.content.entities.projectiles.DiamondHeadedArrow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityTypeInit {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, IWant2TryHardsShortbows.MODID);

    public static final RegistryObject<EntityType<DiamondHeadedArrow>> DIAMOND_HEADED_ARROW = ENTITIES.register("diamond_headed_arrow",
            () -> EntityType.Builder.<DiamondHeadedArrow>of(DiamondHeadedArrow::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build(new ResourceLocation(IWant2TryHardsShortbows.MODID, "diamond_headed_arrow").toString()));
}
