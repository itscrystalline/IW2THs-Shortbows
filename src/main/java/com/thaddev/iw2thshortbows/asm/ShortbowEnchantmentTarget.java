package com.thaddev.iw2thshortbows.asm;

import com.thaddev.iw2thshortbows.content.items.weapons.DiamondShortBowItem;
import com.thaddev.iw2thshortbows.content.items.weapons.IronShortBowItem;
import com.thaddev.iw2thshortbows.mixins.EnchantmentTargetMixin;
import net.minecraft.item.Item;

public class ShortbowEnchantmentTarget extends EnchantmentTargetMixin {
    @Override
    public boolean isAcceptableItem(Item item) {
        return (item instanceof IronShortBowItem || item instanceof DiamondShortBowItem);
    }
}
