package com.thaddev.coolideas.content.items.weapons;

import com.thaddev.coolideas.content.entities.projectiles.DiamondHeadedArrow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class DiamondHeadedArrowItem extends ArrowItem {

    public DiamondHeadedArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull AbstractArrow createArrow(@NotNull Level world, @NotNull ItemStack arrowStack, @NotNull LivingEntity livingEntity) {
        DiamondHeadedArrow arrow = new DiamondHeadedArrow(livingEntity, world);
        arrow.setEffectsFromItem(arrowStack);
        return arrow;
    }

    @Override
    public boolean isInfinite(@NotNull ItemStack stack, @NotNull ItemStack bow, net.minecraft.world.entity.player.@NotNull Player player) {
        int enchant = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.INFINITY_ARROWS, bow);
        return enchant > 0 && this.getClass() == DiamondHeadedArrowItem.class;
    }
}
