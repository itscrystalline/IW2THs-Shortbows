package com.thaddev.iw2thshortbows.mixins;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {
    @Inject(method = "loadProjectiles", at = @At("HEAD"), cancellable = true)
    private static void loadProjectiles(LivingEntity shooter, ItemStack projectile, CallbackInfoReturnable<Boolean> cir) {
        int i = EnchantmentHelper.getLevel(Enchantments.MULTISHOT, projectile);
        int j = 1 + (i * 2);
        boolean bl = shooter instanceof PlayerEntity && ((PlayerEntity) shooter).getAbilities().creativeMode;
        ItemStack itemStack = shooter.getArrowType(projectile);
        ItemStack itemStack2 = itemStack.copy();
        for (int k = 0; k < j; ++k) {
            if (k > 0) {
                itemStack = itemStack2.copy();
            }
            if (itemStack.isEmpty() && bl) {
                itemStack = new ItemStack(Items.ARROW);
                itemStack2 = itemStack.copy();
            }
            if (CrossbowItemAccessor.callLoadProjectile(shooter, projectile, itemStack, k > 0, bl)) continue;
            cir.setReturnValue(false);
        }
        cir.setReturnValue(true);
    }

    @Inject(method = "shootAll", at = @At("HEAD"), cancellable = true)
    private static void shootAll(World world, LivingEntity entity, Hand hand, ItemStack stack, float speed, float divergence, CallbackInfo ci) {
        List<ItemStack> list = CrossbowItemAccessor.callGetProjectiles(stack);
        float[] fs = CrossbowItemAccessor.callGetSoundPitches(entity.getRandom());
        for (int i = 0; i < list.size(); i++) {
            int rotation = i - ((list.size() - 1) / 2);
            boolean bl = entity instanceof PlayerEntity && ((PlayerEntity) entity).getAbilities().creativeMode;
            ItemStack itemStack = list.get(i);
            if (itemStack.isEmpty()) continue;
            CrossbowItemAccessor.callShoot(world, entity, hand, stack, itemStack, fs[i], bl, speed, divergence, rotation * 10f);
        }
        CrossbowItemAccessor.callPostShoot(world, entity, stack);
        ci.cancel();
    }

    @Inject(method = "getSoundPitches", at = @At("HEAD"), cancellable = true)
    private static void getSoundPitches(Random random, CallbackInfoReturnable<float[]> cir) {
        boolean bl = random.nextBoolean();
        cir.setReturnValue(new float[]{1.0f, CrossbowItemAccessor.callGetSoundPitch(bl, random), CrossbowItemAccessor.callGetSoundPitch(!bl, random), CrossbowItemAccessor.callGetSoundPitch(bl, random), CrossbowItemAccessor.callGetSoundPitch(!bl, random)});
    }
}
