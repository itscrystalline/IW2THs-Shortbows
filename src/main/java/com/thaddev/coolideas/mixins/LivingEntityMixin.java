package com.thaddev.coolideas.mixins;

import com.thaddev.coolideas.content.entities.projectiles.DiamondHeadedArrow;
import com.thaddev.coolideas.content.entities.projectiles.ShortBowArrow;
import com.thaddev.coolideas.mechanics.inits.EffectInit;
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

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "applyDamage", at = @At("HEAD"))
    public void applyDamage(DamageSource source, float amount, CallbackInfo ci) {
        LivingEntity thisEntity = (LivingEntity) (Object) this;
        if (!thisEntity.isInvulnerableTo(source)) {
            StatusEffectInstance effect;
            if ((effect = thisEntity.getStatusEffect(EffectInit.VULNERABILITY)) != null) {
                int amplifier = Math.min(effect.getAmplifier() + 1, 4);
                int toReduce = amplifier > 3 ? ((amplifier - 1) * 2) + 3 : amplifier * 2;
                thisEntity.timeUntilRegen = 20 - toReduce;
            }
            if ((source.getSource() instanceof DiamondHeadedArrow || source.getSource() instanceof ShortBowArrow) && source.getAttacker() instanceof ServerPlayerEntity player) {
                player.world.playSound(null, player.getPos().x, player.getPos().y, player.getPos().z, SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.PLAYERS, 0.3F, 0.5F);
            }
        }
    }
}
