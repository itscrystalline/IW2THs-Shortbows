package com.thaddev.coolideas.mechanics.inits;

import com.thaddev.coolideas.CoolIdeasMod;
import com.thaddev.coolideas.content.entities.projectiles.DiamondHeadedArrow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityTypeInit {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, CoolIdeasMod.MODID);

    public static final RegistryObject<EntityType<DiamondHeadedArrow>> DIAMOND_HEADED_ARROW = ENTITIES.register("diamond_headed_arrow",
            () -> EntityType.Builder.<DiamondHeadedArrow>of(DiamondHeadedArrow::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build(new ResourceLocation(CoolIdeasMod.MODID, "diamond_headed_arrow").toString()));
}
