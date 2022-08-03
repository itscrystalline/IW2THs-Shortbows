package com.thaddev.iw2thshortbows.content.items.weapons;

import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
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

    public void fillItemCategory(CreativeModeTab p_43356_, NonNullList<ItemStack> p_43357_) {
        if (this.allowdedIn(p_43356_)) {
            for (Potion potion : Registry.POTION) {
                if (!potion.getEffects().isEmpty()) {
                    p_43357_.add(PotionUtils.setPotion(new ItemStack(this), potion));
                }
            }
        }

    }

    public void appendHoverText(ItemStack p_43359_, @Nullable Level p_43360_, List<Component> p_43361_, TooltipFlag p_43362_) {
        PotionUtils.addPotionTooltip(p_43359_, p_43361_, 0.125F);
    }

    public @NotNull String getDescriptionId(ItemStack p_43364_) {
        return PotionUtils.getPotion(p_43364_).getName(this.getDescriptionId() + ".effect.");
    }
}
