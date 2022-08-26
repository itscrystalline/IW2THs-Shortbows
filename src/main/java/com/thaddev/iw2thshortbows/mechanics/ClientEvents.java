package com.thaddev.iw2thshortbows.mechanics;


import com.thaddev.iw2thshortbows.IWant2TryHardsShortbows;
import com.thaddev.iw2thshortbows.util.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class ClientEvents {
    public static void registerEvents() {
        IWant2TryHardsShortbows.LOGGER.debug("Registering Client Events for " + IWant2TryHardsShortbows.MODID);

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
