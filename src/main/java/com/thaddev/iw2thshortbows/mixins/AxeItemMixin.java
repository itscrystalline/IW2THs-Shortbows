package com.thaddev.iw2thshortbows.mixins;

import com.thaddev.iw2thshortbows.IWant2TryHardsShortbows;
import com.thaddev.iw2thshortbows.mechanics.inits.ItemInit;
import com.thaddev.iw2thshortbows.mechanics.inits.TagsInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
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

import java.util.Optional;

@Mixin(AxeItem.class)
public class AxeItemMixin {
    @Inject(method = "useOnBlock", at = @At(value = "RETURN"))
    private void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        IWant2TryHardsShortbows.LOGGER.debug("MIXIN: useOnBlock fired!");
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        PlayerEntity player = context.getPlayer();
        Hand otherHand = context.getHand() == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND;
        Optional<BlockState> optional = ((AxeItemInvoker) this).invokeGetStrippedState(blockState);
        if (optional.isPresent()) {
            if (player != null && player.getStackInHand(otherHand).getItem() == Items.GLASS_BOTTLE) {
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
        }
    }
}
