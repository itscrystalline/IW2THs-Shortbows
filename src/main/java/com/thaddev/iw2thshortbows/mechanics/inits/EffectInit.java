package com.thaddev.iw2thshortbows.mechanics.inits;

import com.thaddev.iw2thshortbows.IWant2TryHardsShortbows;
import com.thaddev.iw2thshortbows.content.effects.VulnerabilityEffect;
import com.thaddev.iw2thshortbows.util.Utils;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EffectInit {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, IWant2TryHardsShortbows.MODID);

    public static final RegistryObject<MobEffect> VULNERABILITY = MOB_EFFECTS.register("vulnerability",
        () -> new VulnerabilityEffect(MobEffectCategory.HARMFUL, Utils.rgbToInteger(50, 0, 0)));
}
