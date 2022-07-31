package com.thaddev.coolideas.content.items.weapons;

import com.thaddev.coolideas.content.entities.projectiles.DiamondHeadedArrow;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DiamondHeadedArrowItem extends ArrowItem {

    public DiamondHeadedArrowItem(Settings settings) {
        super(settings);
    }

    @Override
    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
        DiamondHeadedArrow arrow = new DiamondHeadedArrow(world, shooter);
        arrow.initFromStack(stack);
        return arrow;
    }
}
