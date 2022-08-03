package com.thaddev.iw2thshortbows.mechanics.inits;

import com.thaddev.iw2thshortbows.IWant2TryHardsShortbows;
import com.thaddev.iw2thshortbows.content.enchantments.InheritEnchantment;
import com.thaddev.iw2thshortbows.content.enchantments.PrecisionEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EnchantmentInit {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, IWant2TryHardsShortbows.MODID);

    public static RegistryObject<Enchantment> PRECISION = ENCHANTMENTS.register("precision",
            () -> new PrecisionEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND}));
    public static RegistryObject<Enchantment> INHERIT = ENCHANTMENTS.register("inherit",
            () -> new InheritEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND}));
}
