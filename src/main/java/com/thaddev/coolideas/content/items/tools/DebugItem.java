package com.thaddev.coolideas.content.items.tools;

import com.thaddev.coolideas.CoolIdeasMod;
import com.thaddev.coolideas.util.ColorUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DebugItem extends Item {
    public DebugItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, Player player, @NotNull InteractionHand hand) {
        if (!world.isClientSide()){
            CoolIdeasMod.instance.printMessage(ColorUtils.from("(%$green)DEBUGITEM: Damage Boost: (%$red)" +
                    (Objects.requireNonNull(player.getAttribute(Attributes.ATTACK_DAMAGE)).getValue() - Objects.requireNonNull(player.getAttribute(Attributes.ATTACK_DAMAGE)).getBaseValue())));
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
