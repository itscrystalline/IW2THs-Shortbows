package com.thaddev.coolideas.mechanics;

import com.thaddev.coolideas.CoolIdeasMod;
import com.thaddev.coolideas.mechanics.lootmodifiers.MicrochipInBastionAdditionModifier;
import com.thaddev.coolideas.mechanics.lootmodifiers.MicrochipInDesertTempleAdditionModifier;
import com.thaddev.coolideas.mechanics.lootmodifiers.MicrochipInDungeonAdditionModifier;
import com.thaddev.coolideas.mechanics.lootmodifiers.MicrochipInJungleTempleAdditionModifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CoolIdeasMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void registerModifierSerializers(final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
        event.getRegistry().registerAll(
            new MicrochipInDungeonAdditionModifier.Serialzer().setRegistryName(
                new ResourceLocation(CoolIdeasMod.MODID, "microchip_in_dungeon")),
            new MicrochipInJungleTempleAdditionModifier.Serialzer().setRegistryName(
                new ResourceLocation(CoolIdeasMod.MODID, "microchip_in_jungle_temple")),
            new MicrochipInDesertTempleAdditionModifier.Serialzer().setRegistryName(
                new ResourceLocation(CoolIdeasMod.MODID, "microchip_in_desert_temple")),
            new MicrochipInBastionAdditionModifier.Serialzer().setRegistryName(
                new ResourceLocation(CoolIdeasMod.MODID, "microchip_in_bastion"))
        );
    }
}
