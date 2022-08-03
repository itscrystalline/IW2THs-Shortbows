package com.thaddev.coolideas.content.enchantments;

import com.thaddev.coolideas.content.items.weapons.DiamondShortBowItem;
import com.thaddev.coolideas.content.items.weapons.IronShortBowItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

public class PrecisionEnchantment extends Enchantment {
    public PrecisionEnchantment(Rarity p_44676_, EnchantmentCategory p_44677_, EquipmentSlot[] p_44678_) {
        super(p_44676_, p_44677_, p_44678_);
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getMinCost(int p_45083_) {
        return 5 + 20 * (p_45083_ - 1);
    }

    @Override
    public int getMaxCost(int p_45085_) {
        return super.getMinCost(p_45085_) + 50;
    }

    @Override
    protected boolean checkCompatibility(@NotNull Enchantment enchantment) {
        return !(enchantment instanceof PrecisionEnchantment);
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof IronShortBowItem || stack.getItem() instanceof DiamondShortBowItem;
    }
}
