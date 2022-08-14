package com.thaddev.iw2thshortbows.mechanics;

import com.thaddev.iw2thshortbows.IWant2TryHardsShortbows;
import com.thaddev.iw2thshortbows.content.entities.projectiles.DiamondHeadedArrow;
import com.thaddev.iw2thshortbows.content.entities.projectiles.ShortBowArrow;
import com.thaddev.iw2thshortbows.mechanics.inits.EffectInit;
import com.thaddev.iw2thshortbows.mechanics.inits.ItemInit;
import com.thaddev.iw2thshortbows.mechanics.inits.TagsInit;
import com.thaddev.iw2thshortbows.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.thaddev.iw2thshortbows.util.Utils.component;

@Mod.EventBusSubscriber(modid = IWant2TryHardsShortbows.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Events {
    @SubscribeEvent
    public static void onPlayerStripAxe(final BlockEvent.BlockToolModificationEvent event) {
        if (!event.isSimulated() && event.getPlayer() instanceof ServerPlayer player
            && event.getToolAction() == ToolActions.AXE_STRIP
            && event.getState().is(TagsInit.REGULAR_LOGS)) {
            Level level = player.getLevel();
            BlockPos blockpos = event.getPos();

            InteractionHand otherHand = event.getContext().getHand() == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
            if (player.getItemInHand(otherHand).getItem() == Items.GLASS_BOTTLE) {
                ItemStack stack = player.getItemInHand(otherHand);
                ItemStack newStack = new ItemStack(ItemInit.RAW_RUBBER_BOTTLE.get(), 1);
                stack.shrink(1);
                player.level.playSound(null, blockpos.getX(), blockpos.getY(), blockpos.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1F, 1F);
                if (stack.isEmpty()) {
                    player.getInventory().removeItem(stack);
                    player.setItemInHand(otherHand, newStack);
                } else {
                    if (!player.addItem(newStack)) {
                        ItemEntity drop = new ItemEntity(level, blockpos.getX(), blockpos.getY(), blockpos.getZ(), newStack);
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
        if ((event.getSource().getDirectEntity() instanceof DiamondHeadedArrow | event.getSource().getDirectEntity() instanceof ShortBowArrow) && event.getSource().getEntity() instanceof Player player) {
            player.level.playSound(null, player.position().x, player.position().y, player.position().z, SoundEvents.ARROW_HIT_PLAYER, SoundSource.PLAYERS, 0.3F, 0.5F);
        }
    }

    @SubscribeEvent
    public static void onLogin(final PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && player.level.getServer() != null) {// just in case
            String loader = player.level.getServer().getServerModName().toLowerCase();
            player.sendSystemMessage(
                component(Utils.from("")).copy()
                    .append(Component.literal("https://github.com/MyNameTsThad/IW2THs-Shortbows/blob/forge-119/README.md#ignore-if-you-did-not-come-from-an-in-game-chat-message").setStyle(
                        Style.EMPTY
                            .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/MyNameTsThad/IW2THs-Shortbows/blob/forge-119/README.md#ignore-if-you-did-not-come-from-an-in-game-chat-message"))
                            .withColor(ChatFormatting.BLUE)
                            .withUnderlined(true)
                    ))
                    .append(Component.literal(" (versionid:" + IWant2TryHardsShortbows.buildVersionString(loader) + ")"))
            );
        }
    }
}
