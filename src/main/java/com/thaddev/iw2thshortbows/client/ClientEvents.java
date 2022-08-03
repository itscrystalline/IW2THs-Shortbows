package com.thaddev.iw2thshortbows.client;

import com.thaddev.iw2thshortbows.IWant2TryHardsShortbows;
import com.thaddev.iw2thshortbows.client.renderer.entity.DiamondHeadedArrowRenderer;
import com.thaddev.iw2thshortbows.mechanics.inits.EntityTypeInit;
import com.thaddev.iw2thshortbows.mechanics.inits.ItemInit;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IWant2TryHardsShortbows.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void clientSetupEntityRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityTypeInit.DIAMOND_HEADED_ARROW.get(), DiamondHeadedArrowRenderer::new);
    }

    @SubscribeEvent
    public static void colorItems(final ColorHandlerEvent.Item event) {
        event.getItemColors().register(
            (itemStack, color) -> color > 0 ? -1 : PotionUtils.getColor(itemStack),
            ItemInit.TIPPED_DIAMOND_HEADED_ARROW.get()
        );
    }
}
