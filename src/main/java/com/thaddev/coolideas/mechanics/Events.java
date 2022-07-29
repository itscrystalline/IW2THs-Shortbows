package com.thaddev.coolideas.mechanics;

import com.thaddev.coolideas.CoolIdeasMod;
import com.thaddev.coolideas.content.entities.projectiles.DiamondHeadedArrow;
import com.thaddev.coolideas.content.entities.projectiles.ShortBowArrow;
import com.thaddev.coolideas.mechanics.inits.EffectInit;
import com.thaddev.coolideas.mechanics.inits.ItemInit;
import com.thaddev.coolideas.util.ColorUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = CoolIdeasMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Events {
    @SubscribeEvent
    public static void onPlayerStripAxe(final BlockEvent.BlockToolModificationEvent event) {
        if (!event.isSimulated() && event.getPlayer() instanceof ServerPlayer player
                && event.getToolAction() == ToolActions.AXE_STRIP
                && !event.getFinalState().getBlock().getDescriptionId().contains("stripped")){
            Level level = player.getLevel();
            BlockPos blockpos = event.getPos();
            BlockState blockState = level.getBlockState(blockpos);

            InteractionHand otherHand = event.getContext().getHand() == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
            if (player.getItemInHand(otherHand).getItem() == Items.GLASS_BOTTLE){
                ItemStack stack = player.getItemInHand(otherHand);
                ItemStack newStack = new ItemStack(ItemInit.RAW_RUBBER_BOTTLE.get(), 1);
                BlockPos pos = event.getPos();
                stack.shrink(1);
                if (stack.isEmpty()) {
                    player.getInventory().removeItem(stack);
                    player.setItemInHand(otherHand, newStack);
                } else {
                    if (!player.addItem(newStack)) {
                        ItemEntity drop = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), newStack);
                        level.addFreshEntity(drop);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(final LivingHurtEvent event) {
        MobEffectInstance effect;
        if ((effect = event.getEntity().getEffect(EffectInit.VULNERABILITY.get())) != null) {
            int amplifier = Math.min(effect.getAmplifier() + 1, 4);
            int toReduce = amplifier > 3 ? ((amplifier - 1) * 2) + 3 : amplifier * 2;
            event.getEntity().invulnerableTime = 20 - toReduce;
        }
        if (event.getSource().getDirectEntity() instanceof DiamondHeadedArrow && event.getSource().getEntity() instanceof Player player) {
            player.level.playSound(null, player.position().x, player.position().y, player.position().z, SoundEvents.ARROW_HIT_PLAYER, SoundSource.PLAYERS, 0.3F, 0.5F);
        }

    }
}
