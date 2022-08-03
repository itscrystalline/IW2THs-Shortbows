package com.thaddev.coolideas.content.entities.projectiles;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.world.World;

public class ShortBowArrow extends ArrowEntity {

    public ShortBowArrow(World world, LivingEntity owner) {
        super(world, owner);
    }

    @Override
    public void tick() {
        if (inGroundTime > 20 && this.pickupType == PickupPermission.CREATIVE_ONLY) {
            this.discard();
        }
        super.tick();
    }
}
