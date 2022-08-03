package com.thaddev.iw2thshortbows.mixins;

import net.minecraft.world.item.enchantment.MultiShotEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiShotEnchantment.class)
public abstract class MultiShotEnchantmentMixin {
    @Inject(method = "getMaxLevel", at = @At("HEAD"), cancellable = true)
    public void getMaxLevel(CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        callbackInfoReturnable.setReturnValue(2);
    }
}
