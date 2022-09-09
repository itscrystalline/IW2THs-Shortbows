package com.thaddev.iw2thshortbows.mixins;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(CrossbowItem.class)
public interface CrossbowItemAccessor {
    @Invoker
    static boolean callLoadProjectile(LivingEntity shooter, ItemStack crossbow, ItemStack projectile, boolean simulated, boolean creative) {
        throw new UnsupportedOperationException();
    }

    @Invoker
    static List<ItemStack> callGetProjectiles(ItemStack crossbow) {
        throw new UnsupportedOperationException();
    }

    @Invoker
    static void callShoot(World world, LivingEntity shooter, Hand hand, ItemStack crossbow, ItemStack projectile, float soundPitch, boolean creative, float speed, float divergence, float simulated) {
        throw new UnsupportedOperationException();
    }

    @Invoker
    static float[] callGetSoundPitches(Random random) {
        throw new UnsupportedOperationException();
    }

    @Invoker
    static float callGetSoundPitch(boolean flag, Random random) {
        throw new UnsupportedOperationException();
    }

    @Invoker
    static void callPostShoot(World world, LivingEntity entity, ItemStack stack) {
        throw new UnsupportedOperationException();
    }
}
