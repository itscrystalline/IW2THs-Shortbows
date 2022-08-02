package com.thaddev.coolideas.mechanics.inits;

import com.thaddev.coolideas.CoolIdeasMod;
import com.thaddev.coolideas.content.effects.VulnerabilityEffect;
import com.thaddev.coolideas.util.ColorUtils;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EffectInit {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, CoolIdeasMod.MODID);

    public static final RegistryObject<MobEffect> VULNERABILITY = MOB_EFFECTS.register("vulnerability",
        () -> new VulnerabilityEffect(MobEffectCategory.HARMFUL, ColorUtils.rgbToInteger(50, 0, 0)));
}
