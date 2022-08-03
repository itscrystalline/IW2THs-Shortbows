package com.thaddev.coolideas.content.entities.projectiles;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;

public class ShortBowArrow extends Arrow {
    public ShortBowArrow(Level world, LivingEntity shooter) {
        super(world, shooter);
    }

    @Override
    public void tick() {
        if (inGroundTime > 20 && this.pickup == Pickup.CREATIVE_ONLY) {
            this.discard();
        }
        super.tick();
    }
}
