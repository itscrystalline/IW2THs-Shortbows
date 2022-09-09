package com.thaddev.iw2thshortbows.mixins;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Random;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {
    @Inject(method = "tryLoadProjectiles", at = @At("HEAD"), cancellable = true)
    private static void loadProjectiles(LivingEntity shooter, ItemStack projectile, CallbackInfoReturnable<Boolean> cir) {
        int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, projectile);
        int j = 1 + (i * 2);
        boolean bl = shooter instanceof Player && ((Player) shooter).getAbilities().instabuild;
        ItemStack itemStack = shooter.getProjectile(projectile);
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

    @Inject(method = "performShooting", at = @At("HEAD"), cancellable = true)
    private static void shootAll(Level world, LivingEntity entity, InteractionHand hand, ItemStack stack, float speed, float divergence, CallbackInfo ci) {
        if (entity instanceof Player player && net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, entity.level, player, 1, true) < 0) return;
        List<ItemStack> list = CrossbowItemAccessor.callGetChargedProjectiles(stack);
        float[] fs = CrossbowItemAccessor.callGetShotPitches(entity.getRandom());
        for (int i = 0; i < list.size(); i++) {
            int rotation = i - ((list.size() - 1) / 2);
            boolean bl = entity instanceof Player && ((Player) entity).getAbilities().instabuild;
            ItemStack itemStack = list.get(i);
            if (itemStack.isEmpty()) continue;
            CrossbowItemAccessor.callShootProjectile(world, entity, hand, stack, itemStack, fs[i], bl, speed, divergence, rotation * 10f);
        }
        CrossbowItemAccessor.callOnCrossbowShot(world, entity, stack);
        ci.cancel();
    }

    @Inject(method = "getShotPitches", at = @At("HEAD"), cancellable = true)
    private static void getSoundPitches(Random random, CallbackInfoReturnable<float[]> cir) {
        boolean bl = random.nextBoolean();
        cir.setReturnValue(new float[]{1.0f, CrossbowItemAccessor.callGetRandomShotPitch(bl, random), CrossbowItemAccessor.callGetRandomShotPitch(!bl, random), CrossbowItemAccessor.callGetRandomShotPitch(bl, random), CrossbowItemAccessor.callGetRandomShotPitch(!bl, random)});
    }
}
