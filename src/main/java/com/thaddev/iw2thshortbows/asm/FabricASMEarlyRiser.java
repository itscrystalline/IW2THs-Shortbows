package com.thaddev.iw2thshortbows.asm;

import com.chocohead.mm.api.ClassTinkerers;
import com.thaddev.iw2thshortbows.IWant2TryHardsShortbows;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public class FabricASMEarlyRiser implements Runnable {
    @Override
    public void run() {
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();

        IWant2TryHardsShortbows.LOGGER.debug("Initializing " + IWant2TryHardsShortbows.MODID + " Early Riser");
        String enchantmentTarget = remapper.mapClassName("intermediary", "net.minecraft.class_1886");
        ClassTinkerers.enumBuilder(enchantmentTarget).addEnumSubclass("SHORTBOW", "com.thaddev.iw2thshortbows.asm.ShortbowEnchantmentTarget").build();
    }
}
