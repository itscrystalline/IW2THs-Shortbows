package com.thaddev.iw2thshortbows.mechanics.inits;

import com.thaddev.iw2thshortbows.IWant2TryHardsShortbows;
import com.thaddev.iw2thshortbows.content.effects.VulnerabilityEffect;
import com.thaddev.iw2thshortbows.util.Utils;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EffectInit {
    public static StatusEffect VULNERABILITY;

    public static StatusEffect registerStatusEffect(String name) {
        return Registry.register(
            Registry.STATUS_EFFECT,
            new Identifier(IWant2TryHardsShortbows.MODID, name),
            new VulnerabilityEffect(StatusEffectCategory.HARMFUL, Utils.rgbToInteger(50, 0, 0)));
    }

    public static void registerEffects() {
        IWant2TryHardsShortbows.LOGGER.debug("Registering Effects for " + IWant2TryHardsShortbows.MODID + " (9/11)");
        VULNERABILITY = registerStatusEffect("vulnerability");
    }
}
