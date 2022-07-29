package com.thaddev.coolideas.content.entities.projectiles;

import com.thaddev.coolideas.CoolIdeasMod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.Objects;

public class ShortBowArrow extends Arrow {
    public ShortBowArrow(Level world, LivingEntity shooter) {
        super(world, shooter);
    }

    @Override
    public void tick() {
        if (inGroundTime > 20){
            this.discard();
        }
        super.tick();
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        if (entityHitResult.getEntity() instanceof LivingEntity && this.getOwner() instanceof Player player) {
            this.level.playSound((Player) null, player.position().x, player.position().y, player.position().z, SoundEvents.ARROW_HIT_PLAYER, SoundSource.PLAYERS, 0.3F, 0.5F);
        }
        super.onHitEntity(entityHitResult);
    }
}
