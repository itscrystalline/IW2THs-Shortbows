package com.thaddev.iw2thshortbows;

import com.thaddev.iw2thshortbows.client.renderer.entity.DiamondHeadedArrowRenderer;
import com.thaddev.iw2thshortbows.mechanics.inits.EntityTypeInit;
import com.thaddev.iw2thshortbows.mechanics.inits.ItemInit;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.potion.PotionUtil;

public class IWant2TryHardsShortbowsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(EntityTypeInit.DIAMOND_HEADED_ARROW, DiamondHeadedArrowRenderer::new);
		ColorProviderRegistry.ITEM.register(
			(itemStack, color) -> color > 0 ? -1 : PotionUtil.getColor(itemStack),
			ItemInit.TIPPED_DIAMOND_HEADED_ARROW
		);
	}
}
