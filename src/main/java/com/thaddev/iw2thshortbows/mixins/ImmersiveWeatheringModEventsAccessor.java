package com.thaddev.iw2thshortbows.mixins;

import com.ordana.immersive_weathering.registry.ModEvents;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.HashMap;

@Pseudo
@Mixin(value = ModEvents.class, remap = false)
public interface ImmersiveWeatheringModEventsAccessor {
    @Accessor("DROPPED_BARK")
    static HashMap<Block, Item> getDroppedBarkMap() {
        throw new AssertionError();
    }
}
