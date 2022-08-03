package com.thaddev.coolideas.content.enchantment;

import com.thaddev.coolideas.content.items.weapons.DiamondShortBowItem;
import com.thaddev.coolideas.content.items.weapons.IronShortBowItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class InheritEnchantment extends Enchantment {
    public InheritEnchantment(Enchantment.Rarity weight, EnchantmentTarget type, EquipmentSlot... slotTypes) {
        super(weight, type, slotTypes);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getMinPower(int p_45083_) {
        return 5 + 20 * (p_45083_ - 1);
    }

    @Override
    public int getMaxPower(int p_45085_) {
        return super.getMinPower(p_45085_) + 50;
    }

    @Override
    protected boolean canAccept(@NotNull Enchantment enchantment) {
        return !(enchantment instanceof InheritEnchantment);
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof IronShortBowItem || stack.getItem() instanceof DiamondShortBowItem;
    }
}
