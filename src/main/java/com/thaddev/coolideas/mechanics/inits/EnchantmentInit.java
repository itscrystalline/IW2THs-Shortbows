package com.thaddev.coolideas.mechanics.inits;

import com.thaddev.coolideas.CoolIdeasMod;
import com.thaddev.coolideas.content.enchantment.InheritEnchantment;
import com.thaddev.coolideas.content.enchantment.PrecisionEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EnchantmentInit {

    public static Enchantment INHERIT = registerEnchantment("inherit",
        new InheritEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND)
    );
    public static Enchantment PRECISION = registerEnchantment("precision",
        new PrecisionEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND)
    );

    private static Enchantment registerEnchantment(String name, Enchantment enchantment) {
        return Registry.register(Registry.ENCHANTMENT, new Identifier(CoolIdeasMod.MODID, name), enchantment);
    }

    public static void registerEnchantments() {
        CoolIdeasMod.LOGGER.debug("Registering Enchantments for " + CoolIdeasMod.MODID + " (6/11)");
    }
}
