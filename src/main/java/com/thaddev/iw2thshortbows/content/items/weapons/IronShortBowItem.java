package com.thaddev.iw2thshortbows.content.items.weapons;

import com.thaddev.iw2thshortbows.mechanics.inits.ItemInit;
import com.thaddev.iw2thshortbows.util.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.thaddev.iw2thshortbows.util.Utils.component;

public class IronShortBowItem extends ShortBowBase {
    public IronShortBowItem(Properties properties) {
        super(properties);
    }

    @Override
    public double getPowerBaseDamage(double baseDamage, int powerLevel) {
        return baseDamage + (double) powerLevel * 0.75D + 0.75D;
    }

    @Override
    public float getCritChance() {
        return 0.2f;
    }

    @Override
    public float getRubberbandHitChance() {
        return 0.2f;
    }

    @Override
    public int getTicksOnFire() {
        return 100;
    }

    @Override
    public float getRubberbandHitDamage() {
        return 6f;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags) {
        tooltip.add(component(Utils.fromNoTag("(%$white)Shoots Instantly! (%$italic)Make sure to not hit yourself!")));
        super.appendHoverText(stack, world, tooltip, flags);
    }

    @Override
    public int getDefaultProjectileRange() {
        return 20;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return (enchantment == Enchantments.MULTISHOT) || super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public boolean isValidRepairItem(ItemStack thisItem, ItemStack repairItem) {
        return repairItem.getItem() == ItemInit.IRON_ROD.get() || repairItem.getItem() == ItemInit.LATEX_BAND.get()
                || repairItem.getItem() == ItemInit.RUBBER_BAND.get() || super.isValidRepairItem(thisItem, repairItem);
    }

    @Override
    public boolean canHomeArrows() {
        return false;
    }
}
