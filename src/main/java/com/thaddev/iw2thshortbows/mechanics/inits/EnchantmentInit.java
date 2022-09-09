package com.thaddev.iw2thshortbows.mechanics.inits;

import com.thaddev.iw2thshortbows.IWant2TryHardsShortbows;
import com.thaddev.iw2thshortbows.content.enchantment.InheritEnchantment;
import com.thaddev.iw2thshortbows.content.enchantment.PrecisionEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EnchantmentInit {

    public static Enchantment INHERIT = registerEnchantment("inherit",
        new InheritEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND)
    );
    public static Enchantment PRECISION = registerEnchantment("precision",
        new PrecisionEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND)
    );

    private static Enchantment registerEnchantment(String name, Enchantment enchantment) {
        return Registry.register(Registry.ENCHANTMENT, new Identifier(IWant2TryHardsShortbows.MODID, name), enchantment);
    }

    public static void registerEnchantments() {
        IWant2TryHardsShortbows.LOGGER.debug("Registering Enchantments for " + IWant2TryHardsShortbows.MODID + " (6/11)");
    }
}
