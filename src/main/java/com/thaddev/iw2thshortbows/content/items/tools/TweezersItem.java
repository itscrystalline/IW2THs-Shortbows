package com.thaddev.iw2thshortbows.content.items.tools;

import com.thaddev.iw2thshortbows.mechanics.inits.ItemInit;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TweezersItem extends Item {
    public TweezersItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockpos = context.getBlockPos();
        BlockState blockstate = world.getBlockState(blockpos);
        Block block = blockstate.getBlock();
        if (block == Blocks.COBWEB) {
            PlayerEntity player = context.getPlayer();
            ItemStack itemstack = context.getStack();
            if (player instanceof ServerPlayerEntity) {
                Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, blockpos, itemstack);
            }

            int drops = (int) (4 + (Math.random() * 5f));
            ItemEntity itemDrop = new ItemEntity(world, blockpos.getX() + 0.5, blockpos.getY() + 0.5, blockpos.getZ() + 0.5,
                new ItemStack(ItemInit.SPIDER_WEB, drops));

            world.spawnEntity(itemDrop);
            world.playSound(player, blockpos, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.setBlockState(blockpos, Blocks.AIR.getDefaultState());
            if (player != null) {
                itemstack.damage(1, player, (player1) -> {
                    player1.sendToolBreakStatus(context.getHand());
                });
            }

            return ActionResult.success(world.isClient);
        }
        return super.useOnBlock(context);
    }
}
