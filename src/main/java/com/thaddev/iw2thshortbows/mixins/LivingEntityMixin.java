package com.thaddev.iw2thshortbows.mixins;

import com.thaddev.iw2thshortbows.content.entities.projectiles.DiamondHeadedArrow;
import com.thaddev.iw2thshortbows.content.entities.projectiles.ShortBowArrow;
import com.thaddev.iw2thshortbows.mechanics.inits.EffectInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;applyDamage(Lnet/minecraft/entity/damage/DamageSource;F)V", shift = At.Shift.BEFORE))
    public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity thisEntity = (LivingEntity) (Object) this;
        StatusEffectInstance effect;
        if ((effect = thisEntity.getStatusEffect(EffectInit.VULNERABILITY)) != null && thisEntity.timeUntilRegen > 10f) {
            int amplifier = Math.min(effect.getAmplifier() + 1, 4);
            int toReduce = amplifier > 3 ? ((amplifier - 1) * 2) + 3 : amplifier * 2;
            thisEntity.timeUntilRegen = 20 - toReduce;
        }
    }

    @Inject(method = "applyDamage", at = @At("HEAD"))
    public void applyDamage(DamageSource source, float amount, CallbackInfo ci) {
        LivingEntity thisEntity = (LivingEntity) (Object) this;
        if (!thisEntity.isInvulnerableTo(source)) {
            if ((source.getSource() instanceof DiamondHeadedArrow || source.getSource() instanceof ShortBowArrow) && source.getAttacker() instanceof ServerPlayerEntity player) {
                player.world.playSound(null, player.getPos().x, player.getPos().y, player.getPos().z, SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.PLAYERS, 0.3F, 0.5F);
            }
        }
    }
}
