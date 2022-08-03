package com.thaddev.iw2thshortbows.content.items.weapons;

import com.thaddev.iw2thshortbows.util.ColorUtils;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.thaddev.iw2thshortbows.util.ColorUtils.component;

public class WoodenShortBowItem extends ShortBowBase {
    public WoodenShortBowItem(Settings settings) {
        super(settings);
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
    public void appendTooltip(@NotNull ItemStack stack, @Nullable World world, @NotNull List<Text> tooltip, @NotNull TooltipContext context) {
        tooltip.add(component(ColorUtils.fromNoTag("(%$green)Shoots Instantly! (%$italic)beware... it can break easily!")));
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public int getRange() {
        return 15;
    }

    @Override
    public boolean canRepair(ItemStack thisItem, ItemStack repairItem) {
        return repairItem.getItem() == Items.STICK || /*repairItem.getItem() == ItemInit.RUBBER_BAND.get() ||*/ super.canRepair(thisItem, repairItem);
    }
}
