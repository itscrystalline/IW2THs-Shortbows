package com.thaddev.iw2thshortbows.mixins;

import net.minecraft.block.BlockState;
import net.minecraft.item.AxeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Optional;

@Mixin(AxeItem.class)
public interface AxeItemInvoker {
    @Invoker("getStrippedState")
    Optional<BlockState> invokeGetStrippedState(BlockState state);
}
