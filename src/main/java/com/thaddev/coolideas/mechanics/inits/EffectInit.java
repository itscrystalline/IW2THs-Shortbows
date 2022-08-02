package com.thaddev.coolideas.mechanics.inits;

import com.thaddev.coolideas.CoolIdeasMod;
import com.thaddev.coolideas.content.effects.VulnerabilityEffect;
import com.thaddev.coolideas.util.ColorUtils;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EffectInit {
    public static StatusEffect VULNERABILITY;

    public static StatusEffect registerStatusEffect(String name) {
        return Registry.register(
            Registry.STATUS_EFFECT,
            new Identifier(CoolIdeasMod.MODID, name),
            new VulnerabilityEffect(StatusEffectCategory.HARMFUL, ColorUtils.rgbToInteger(10, 0, 0)));
    }

    public static void registerEffects() {
        CoolIdeasMod.LOGGER.debug("Registering Effects for " + CoolIdeasMod.MODID + " (9/11)");
        VULNERABILITY = registerStatusEffect("vulnerability");
    }
}
