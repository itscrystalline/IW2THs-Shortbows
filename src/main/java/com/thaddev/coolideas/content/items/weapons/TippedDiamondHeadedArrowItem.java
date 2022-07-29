package com.thaddev.coolideas.content.items.weapons;

import com.thaddev.coolideas.content.entities.projectiles.DiamondHeadedArrow;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class TippedDiamondHeadedArrowItem extends DiamondHeadedArrowItem {
    public TippedDiamondHeadedArrowItem(Properties properties) {
        super(properties);
    }

    public @NotNull ItemStack getDefaultInstance() {
        return PotionUtils.setPotion(super.getDefaultInstance(), Potions.POISON);
    }

    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> stacks) {
        for (Potion potion : Registry.POTION) {
            if (potion.allowedInCreativeTab(this, tab, this.allowedIn(tab))) {
                if (!potion.getEffects().isEmpty()) {
                    stacks.add(PotionUtils.setPotion(new ItemStack(this), potion));
                }
            }
        }

    }

    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tag, TooltipFlag flags) {
        PotionUtils.addPotionTooltip(stack, tag, 0.125F);
    }

    public @NotNull String getDescriptionId(@NotNull ItemStack stack) {
        return PotionUtils.getPotion(stack).getName(this.getDescriptionId() + ".effect.");
    }
}
