package com.thaddev.iw2thshortbows.mixins;

import com.thaddev.iw2thshortbows.IWant2TryHardsShortbows;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLevel.class)
public class ClientLevelMixin {
    @Inject(method = "disconnect", at = @At("HEAD"))
    private void handleSystemChat(CallbackInfo ci) {
        IWant2TryHardsShortbows.instance.isMismatching = false;
    }
}