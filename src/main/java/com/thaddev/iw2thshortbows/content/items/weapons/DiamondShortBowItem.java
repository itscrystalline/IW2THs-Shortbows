package com.thaddev.iw2thshortbows.content.items.weapons;

import com.thaddev.iw2thshortbows.mechanics.inits.ItemInit;
import com.thaddev.iw2thshortbows.util.ColorUtils;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.thaddev.iw2thshortbows.util.ColorUtils.component;

public class DiamondShortBowItem extends ShortBowBase {

    public DiamondShortBowItem(Settings settings) {
        super(settings);
    }

    @Override
    public double getPowerBaseDamage(double baseDamage, int powerLevel) {
        return baseDamage + (double) powerLevel + 1D;
    }

    @Override
    public float getCritChance() {
        return 0.4f;
    }

    @Override
    public float getRubberbandHitChance() {
        return 0.4f;
    }

    @Override
    public int getTicksOnFire() {
        return 150;
    }

    @Override
    public float getRubberbandHitDamage() {
        return 8f;
    }

    @Override
    public void appendTooltip(@NotNull ItemStack stack, @Nullable World world, @NotNull List<Text> tooltip, @NotNull TooltipContext context) {
        tooltip.add(component(ColorUtils.fromNoTag("(%$blue)Shoots Instantly! (%$italic)Make sure to not hit yourself!")));
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public int getRange() {
        return 30;
    }

    @Override
    public boolean canRepair(@NotNull ItemStack thisItem, ItemStack repairItem) {
        return repairItem.getItem() == ItemInit.DIAMOND_ROD || repairItem.getItem() == ItemInit.LATEX_BAND
            || repairItem.getItem() == ItemInit.CARBON_FIBER || super.canRepair(thisItem, repairItem);
    }

    @Override
    public boolean canHomeArrows() {
        return true;
    }
}
