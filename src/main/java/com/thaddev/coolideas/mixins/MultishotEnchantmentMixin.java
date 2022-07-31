package com.thaddev.coolideas.mixins;

import com.thaddev.coolideas.mechanics.inits.ItemInit;
import net.minecraft.enchantment.MultishotEnchantment;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultishotEnchantment.class)
public class MultishotEnchantmentMixin extends EnchantmentMixin {
    @Inject(method = "getMaxLevel", at = @At("HEAD"), cancellable = true)
    public void getMaxLevel(CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        callbackInfoReturnable.setReturnValue(2);
    }

    @Override
    protected void customIsAcceptableItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.isOf(ItemInit.DIAMOND_SHORTBOW) || stack.isOf(ItemInit.IRON_SHORTBOW)) {
            cir.setReturnValue(true);
        }
    }
}