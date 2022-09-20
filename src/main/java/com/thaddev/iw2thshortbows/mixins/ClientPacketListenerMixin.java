package com.thaddev.iw2thshortbows.mixins;

import com.thaddev.iw2thshortbows.IWant2TryHardsShortbows;
import com.thaddev.iw2thshortbows.client.gui.ModMismatchScreen;
import com.thaddev.iw2thshortbows.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.thaddev.iw2thshortbows.util.Utils.component;

@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin {

    @Shadow public abstract RegistryAccess registryAccess();

    @Inject(method = "handleSystemChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;handleSystemChat(Lnet/minecraft/network/chat/ChatType;Lnet/minecraft/network/chat/Component;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void handleSystemChat(ClientboundSystemChatPacket packet, CallbackInfo ci) {
        String message = packet.content().getString();
        if (message.contains("IWant2TryHardsShortbows")) {
            IWant2TryHardsShortbows.LOGGER.info("Message successfully suppressed: " + message);
            //FORMAT: [IWant2TryHardsShortbows] https://github.com/MyNameTsThad/IW2THs-Shortbows/blob/forge-119/README.md#ignore-if-you-did-not-come-from-an-in-game-chat-message (versionid:<Modloader>-mc<GameVersion>-<ModVersion>)
            //  example: [IWant2TryHardsShortbows] https://github.com/MyNameTsThad/IW2THs-Shortbows/blob/forge-119/README.md#ignore-if-you-did-not-come-from-an-in-game-chat-message (versionid:forge-mc1.19-2.0.0)
            String[] split = message.split(" ");
            String serverVersionString = split[2].substring(11, split[2].length() - 1);
            String serverVersion = serverVersionString.split("-")[1];
            if (serverVersionString.split("-").length > 2) {
                serverVersion += "-";
                serverVersion += serverVersionString.split("-")[2];
            }
            String serverModLoader = serverVersionString.split("-")[0];

            String clientModLoader = ClientBrandRetriever.getClientModName();
            String clientVersion = IWant2TryHardsShortbows.VERSION;
            //TO SEND: [IWant2TryHardsShortbows] Welcome, <playerName>! Server is running IWant2TryHard's Shortbows version <modVersion>, For <modLoader> <gameVersion>.

            String niceServerModLoader = Utils.niceify(serverModLoader);
            String niceClientModLoader = Utils.niceify(clientModLoader);
            Registry<ChatType> registry = this.registryAccess().registryOrThrow(Registry.CHAT_TYPE_REGISTRY);
            ChatType chatType = packet.resolveType(registry);
            Minecraft.getInstance().gui.handleSystemChat(
                chatType,
                component(Utils.from(""))
                    .append(Component.translatable(IWant2TryHardsShortbows.MESSAGE_WELCOME, Utils.fromNoTag("(%$yellow)" + Minecraft.getInstance().player.getName().getString() + "(%$reset)"), serverVersion, niceServerModLoader, SharedConstants.VERSION_STRING)
                        .withStyle(ChatFormatting.GRAY)
                    )
            );

            if (!serverVersion.equals(clientVersion)) {
                Minecraft.getInstance().gui.getChat()
                    .addMessage(component(Utils.from("(%$red)(%$bold) Mod Version mismatch! Client is " + clientVersion + ", while server is " + serverVersion)));
                Minecraft.getInstance().setScreen(new ModMismatchScreen(clientVersion, serverVersion, niceClientModLoader, niceServerModLoader));
            }
            if (!serverModLoader.equals(clientModLoader)) {
                Minecraft.getInstance().gui.getChat()
                    .addMessage(component(Utils.from("(%$red)(%$bold) Modloader mismatch! Client is " + niceClientModLoader + ", while server is " + niceServerModLoader)));
                Minecraft.getInstance().setScreen(new ModMismatchScreen(clientVersion, serverVersion, niceClientModLoader, niceServerModLoader));
            }

            ci.cancel();
        }
    }
}