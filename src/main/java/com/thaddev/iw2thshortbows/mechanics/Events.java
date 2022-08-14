package com.thaddev.iw2thshortbows.mechanics;


import com.thaddev.iw2thshortbows.IWant2TryHardsShortbows;
import com.thaddev.iw2thshortbows.mechanics.inits.ItemInit;
import com.thaddev.iw2thshortbows.mechanics.inits.TagsInit;
import com.thaddev.iw2thshortbows.util.Utils;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

import static com.thaddev.iw2thshortbows.util.Utils.component;

public class Events {
    public static void registerEvents() {
        IWant2TryHardsShortbows.LOGGER.debug("Registering Events for " + IWant2TryHardsShortbows.MODID + " (1/11)");
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            BlockPos blockPos = hitResult.getBlockPos();
            BlockState blockState = world.getBlockState(blockPos);
            Hand otherHand = hand == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND;
            if (player.getStackInHand(hand).getItem() instanceof AxeItem && player.getStackInHand(otherHand).getItem() == Items.GLASS_BOTTLE) {
                if (blockState.isIn(TagsInit.REGULAR_LOGS)) {
                    ItemStack stack = player.getStackInHand(otherHand);
                    ItemStack newStack = new ItemStack(ItemInit.RAW_RUBBER_BOTTLE, 1);
                    stack.decrement(1);
                    world.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1F, 1F);
                    if (stack.isEmpty()) {
                        player.getInventory().removeOne(stack);
                        player.setStackInHand(otherHand, newStack);
                    } else {
                        if (!player.giveItemStack(newStack)) {
                            ItemEntity drop = new ItemEntity(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), newStack);
                            world.spawnEntity(drop);
                        }
                    }
                }
            }
            return ActionResult.PASS;
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            String loader = server.getServerModName().toLowerCase();
            handler.player.sendMessage(
                component(Utils.from("")).copy()
                    .append(Text.of("https://github.com/MyNameTsThad/IW2THs-Shortbows/blob/forge-119/README.md#ignore-if-you-did-not-come-from-an-in-game-chat-message").copy().setStyle(
                        Style.EMPTY
                            .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/MyNameTsThad/IW2THs-Shortbows/blob/forge-119/README.md#ignore-if-you-did-not-come-from-an-in-game-chat-message"))
                            .withColor(Formatting.BLUE)
                            .withUnderline(true)
                    ))
                    .append(Text.of(" (versionid:" + IWant2TryHardsShortbows.buildVersionString(loader) + ")")),
                false
            );
        });

        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
            if (IWant2TryHardsShortbows.instance.isMismatching) {
                MinecraftClient.getInstance().textRenderer.drawWithShadow(matrixStack, Utils.fromNoTag("(%$white)(%$bold)(%$underline)Version Mismatch! (from IWant2TryHardsShortbows)"), 10, 10, 100);
                MinecraftClient.getInstance().textRenderer.drawWithShadow(matrixStack, Utils.fromNoTag("(%$white)Please change your modloader / mod version to match"), 10, 22, 100);
                MinecraftClient.getInstance().textRenderer.drawWithShadow(matrixStack, Utils.fromNoTag("(%$white)the server modloader / mod version in the warning"), 10, 32, 100);
                MinecraftClient.getInstance().textRenderer.drawWithShadow(matrixStack, Utils.fromNoTag("(%$white)message displayed when you join!"), 10, 42, 100);
                MinecraftClient.getInstance().textRenderer.drawWithShadow(matrixStack, Utils.fromNoTag("(%$gold)(%$underline)If you encounter a bug and report it, Anything that happens in"), 10, 62, 100);
                MinecraftClient.getInstance().textRenderer.drawWithShadow(matrixStack, Utils.fromNoTag("(%$gold)(%$underline)this server connection instance will not be considered valid evidence."), 10, 74, 100);
            }
        });
    }
}
