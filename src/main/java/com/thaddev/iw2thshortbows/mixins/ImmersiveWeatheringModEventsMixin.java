package com.thaddev.iw2thshortbows.mixins;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.registry.ModEvents;
import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.WeatheringHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(value = ModEvents.class, remap = false)
public class ImmersiveWeatheringModEventsMixin {
    @Inject(method = "lambda$registerEvents$5", at = @At("HEAD"), cancellable = true, remap = false, require = 0)
    private static void lambda$registerEvents$5(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir){
        if (player instanceof ServerPlayerEntity serverPlayer){
            BlockPos targetPos = hitResult.getBlockPos();
            BlockPos fixedPos = targetPos.offset(hitResult.getSide());
            BlockState targetBlock = world.getBlockState(targetPos);
            ItemStack heldItem = serverPlayer.getStackInHand(hand);
            if (heldItem.getItem() instanceof AxeItem) {
                if (ImmersiveWeathering.getConfig().itemUsesConfig.axeStripping && targetBlock.isIn(ModTags.RAW_LOGS)) {
                    Block.dropStack(world, fixedPos, new ItemStack(ImmersiveWeatheringModEventsAccessor.getDroppedBarkMap().get(targetBlock.getBlock())));
                    world.playSound(player, targetPos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    DefaultParticleType barkParticle = WeatheringHelper.getBarkParticle(targetBlock).orElse(null);
                    ParticleUtil.spawnParticle(world, targetPos, barkParticle, UniformIntProvider.create(3, 5));
                    cir.setReturnValue(ActionResult.PASS);
                }
            }
        }
    }
}
