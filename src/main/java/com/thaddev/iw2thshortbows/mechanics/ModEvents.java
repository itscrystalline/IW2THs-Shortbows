package com.thaddev.iw2thshortbows.mechanics;

import com.thaddev.iw2thshortbows.IWant2TryHardsShortbows;
import com.thaddev.iw2thshortbows.mechanics.lootmodifiers.MicrochipInBastionAdditionModifier;
import com.thaddev.iw2thshortbows.mechanics.lootmodifiers.MicrochipInDesertTempleAdditionModifier;
import com.thaddev.iw2thshortbows.mechanics.lootmodifiers.MicrochipInDungeonAdditionModifier;
import com.thaddev.iw2thshortbows.mechanics.lootmodifiers.MicrochipInJungleTempleAdditionModifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IWant2TryHardsShortbows.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void registerModifierSerializers(final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
        event.getRegistry().registerAll(
            new MicrochipInDungeonAdditionModifier.Serialzer().setRegistryName(
                new ResourceLocation(IWant2TryHardsShortbows.MODID, "microchip_in_dungeon")),
            new MicrochipInJungleTempleAdditionModifier.Serialzer().setRegistryName(
                new ResourceLocation(IWant2TryHardsShortbows.MODID, "microchip_in_jungle_temple")),
            new MicrochipInDesertTempleAdditionModifier.Serialzer().setRegistryName(
                new ResourceLocation(IWant2TryHardsShortbows.MODID, "microchip_in_desert_temple")),
            new MicrochipInBastionAdditionModifier.Serialzer().setRegistryName(
                new ResourceLocation(IWant2TryHardsShortbows.MODID, "microchip_in_bastion"))
        );
    }
}
