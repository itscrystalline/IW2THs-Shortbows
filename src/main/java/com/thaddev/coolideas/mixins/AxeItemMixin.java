package com.thaddev.coolideas.mixins;

import com.thaddev.coolideas.mechanics.inits.ItemInit;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AxeItem.class)
public class AxeItemMixin {
    @Inject(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ActionResult;success(Z)Lnet/minecraft/util/ActionResult;", shift = At.Shift.BEFORE))
    public void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        World world;
        if (!(world = context.getWorld()).isClient() && context.getPlayer() instanceof ServerPlayerEntity player) {
            BlockPos pos = context.getBlockPos();
            Hand otherHand = context.getHand() == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND;

            if (player.getStackInHand(otherHand).getItem() == Items.GLASS_BOTTLE) {
                ItemStack stack = player.getStackInHand(otherHand);
                ItemStack newStack = new ItemStack(ItemInit.RAW_RUBBER_BOTTLE, 1);
                stack.decrement(1);
                world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1F, 1F);
                if (stack.isEmpty()) {
                    player.getInventory().removeOne(stack);
                    player.setStackInHand(otherHand, newStack);
                } else {
                    if (!player.giveItemStack(newStack)) {
                        ItemEntity drop = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), newStack);
                        world.spawnEntity(drop);
                    }
                }
            }
        }
    }
}
