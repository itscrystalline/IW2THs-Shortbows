package com.thaddev.coolideas.content.items.weapons;

import com.thaddev.coolideas.mechanics.inits.ItemInit;
import com.thaddev.coolideas.util.ColorUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.thaddev.coolideas.util.ColorUtils.component;

public class WoodenShortBowItem extends ShortBowBase {
    public WoodenShortBowItem(Properties properties) {
        super(properties);
    }

    @Override
    public double getPowerBaseDamage(double baseDamage, int powerLevel) {
        return (baseDamage * 0.75D) + (double) powerLevel * 0.5D + 0.5D;
    }

    @Override
    public float getCritChance() {
        return 0.05f;
    }

    @Override
    public float getRubberbandHitChance() {
        return 0.05f;
    }

    @Override
    public int getTicksOnFire() {
        return 75;
    }

    @Override
    public float getRubberbandHitDamage() {
        return 2f;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags) {
        tooltip.add(component(ColorUtils.fromNoTag("(%$green)Shoots Instantly! (%$italic)beware... it can break easily!")));
        super.appendHoverText(stack, world, tooltip, flags);
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }

    @Override
    public boolean isValidRepairItem(ItemStack thisItem, ItemStack repairItem) {
        return repairItem.getItem() == Items.STICK || repairItem.getItem() == ItemInit.RUBBER_BAND.get() || super.isValidRepairItem(thisItem, repairItem);
    }
}
