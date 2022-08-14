package com.thaddev.iw2thshortbows.mixins;

import com.thaddev.iw2thshortbows.IWant2TryHardsShortbows;
import com.thaddev.iw2thshortbows.client.gui.ModMismatchScreen;
import com.thaddev.iw2thshortbows.util.Utils;
import net.minecraft.SharedConstants;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.registry.DynamicRegistryManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.thaddev.iw2thshortbows.util.Utils.component;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Shadow
    private DynamicRegistryManager.Immutable registryManager;

    @Inject(method = "onGameMessage", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;addChatMessage(Lnet/minecraft/network/MessageType;Lnet/minecraft/text/Text;Ljava/util/UUID;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void onGameMessage(GameMessageS2CPacket packet, CallbackInfo ci) {
        String message = packet.getMessage().getString();
        if (message.contains("IWant2TryHardsShortbows")) {
            IWant2TryHardsShortbows.LOGGER.debug("Message successfully suppressed: " + message);
            //FORMAT: [IWant2TryHardsShortbows] https://github.com/MyNameTsThad/IW2THs-Shortbows/blob/forge-119/README.md#ignore-if-you-did-not-come-from-an-in-game-chat-message (versionid:<Modloader>-mc<GameVersion>-<ModVersion>)
            //  example: [IWant2TryHardsShortbows] https://github.com/MyNameTsThad/IW2THs-Shortbows/blob/forge-119/README.md#ignore-if-you-did-not-come-from-an-in-game-chat-message (versionid:forge-mc1.19-2.0.0)
            String[] split = message.split(" ");
            String serverVersionString = split[2].substring(11, split[2].length() - 1);
            String serverVersion = serverVersionString.split("-")[2];
            if (serverVersionString.split("-").length > 3)
                serverVersion += serverVersionString.split("-")[3];
            String serverModLoader = serverVersionString.split("-")[0];

            String clientModLoader = ClientBrandRetriever.getClientModName();
            String clientVersion = IWant2TryHardsShortbows.VERSION;
            //TO SEND: [CoolIdeas] Welcome, <playerName>! Server is running Cool Ideas Mod version <modVersion>, For <modLoader> <gameVersion>.

            String niceServerModLoader = Utils.niceify(serverModLoader);
            String niceClientModLoader = Utils.niceify(clientModLoader);
            MinecraftClient.getInstance().inGameHud.addChatMessage(
                packet.getType(),
                component(Utils.from(""))
                    .append(new TranslatableText(IWant2TryHardsShortbows.MESSAGE_WELCOME, Utils.fromNoTag("(%$yellow)" + MinecraftClient.getInstance().player.getName().getString() + "(%$reset)"), serverVersion, niceServerModLoader, SharedConstants.VERSION_NAME)
                        .formatted(Formatting.GRAY)
                    ),
                packet.getSender()
            );

            if (!serverVersion.equals(clientVersion)) {
                MinecraftClient.getInstance().inGameHud.getChatHud()
                    .addMessage(component(Utils.from("(%$red)(%$bold) Mod Version mismatch! Client is " + clientVersion + ", while server is " + serverVersion)));
                MinecraftClient.getInstance().setScreen(new ModMismatchScreen(clientVersion, serverVersion, niceClientModLoader, niceServerModLoader));
            }
            if (!serverModLoader.equals(clientModLoader)) {
                MinecraftClient.getInstance().inGameHud.getChatHud()
                    .addMessage(component(Utils.from("(%$red)(%$bold) Modloader mismatch! Client is " + niceClientModLoader + ", while server is " + niceServerModLoader)));
                MinecraftClient.getInstance().setScreen(new ModMismatchScreen(clientVersion, serverVersion, niceClientModLoader, niceServerModLoader));
            }

            ci.cancel();
        }
    }
} 