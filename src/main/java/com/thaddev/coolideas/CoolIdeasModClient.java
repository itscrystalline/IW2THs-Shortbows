package com.thaddev.coolideas;

import com.thaddev.coolideas.client.renderer.entity.DiamondHeadedArrowRenderer;
import com.thaddev.coolideas.mechanics.inits.EntityTypeInit;
import com.thaddev.coolideas.mechanics.inits.ItemInit;
import com.thaddev.coolideas.util.ColorUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.potion.PotionUtil;
import net.minecraft.text.Text;

public class CoolIdeasModClient implements ClientModInitializer {
	public MinecraftClient mc;

	@Override
	public void onInitializeClient() {
		CoolIdeasMod.client = this;

		EntityRendererRegistry.register(EntityTypeInit.DIAMOND_HEADED_ARROW, DiamondHeadedArrowRenderer::new);
		ColorProviderRegistry.ITEM.register(
			(itemStack, color) -> color > 0 ? -1 : PotionUtil.getColor(itemStack),
			ItemInit.TIPPED_DIAMOND_HEADED_ARROW
		);

		mc = MinecraftClient.getInstance();
	}

	public void printMessage(String message) {
		if (mc != null && mc.inGameHud != null && mc.player != null) {
			mc.inGameHud.getChatHud().addMessage(Text.of(ColorUtils.from(message)));
		}
	}
}
