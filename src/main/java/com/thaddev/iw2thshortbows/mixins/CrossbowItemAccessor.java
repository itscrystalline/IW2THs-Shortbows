package com.thaddev.iw2thshortbows.mixins;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;
import java.util.Random;

@Mixin(CrossbowItem.class)
public interface CrossbowItemAccessor {
    @Invoker
    static boolean callLoadProjectile(LivingEntity pShooter, ItemStack pCrossbowStack, ItemStack pAmmoStack, boolean pHasAmmo, boolean pIsCreative) {
        throw new UnsupportedOperationException();
    }

    @Invoker
    static List<ItemStack> callGetChargedProjectiles(ItemStack pCrossbowStack) {
        throw new UnsupportedOperationException();
    }

    @Invoker
    static float callGetRandomShotPitch(boolean p_220026_, Random p_220027_) {
        throw new UnsupportedOperationException();
    }

    @Invoker
    static float[] callGetShotPitches(Random p_220024_) {
        throw new UnsupportedOperationException();
    }

    @Invoker
    static void callShootProjectile(Level pLevel, LivingEntity pShooter, InteractionHand pHand, ItemStack pCrossbowStack, ItemStack pAmmoStack, float pSoundPitch, boolean pIsCreativeMode, float pVelocity, float pInaccuracy, float pProjectileAngle) {
        throw new UnsupportedOperationException();
    }

    @Invoker
    static void callOnCrossbowShot(Level pLevel, LivingEntity pShooter, ItemStack pCrossbowStack) {
        throw new UnsupportedOperationException();
    }
}
