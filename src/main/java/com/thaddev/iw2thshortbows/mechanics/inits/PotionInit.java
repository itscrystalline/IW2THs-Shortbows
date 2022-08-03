package com.thaddev.iw2thshortbows.mechanics.inits;

import com.thaddev.iw2thshortbows.IWant2TryHardsShortbows;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PotionInit {
    public static  final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, IWant2TryHardsShortbows.MODID);

    public static final RegistryObject<Potion> VULNERABILITY = POTIONS.register("vulnerability",
            () -> new Potion(new MobEffectInstance(EffectInit.VULNERABILITY.get(), 2700, 0)));
    public static final RegistryObject<Potion> VULNERABILITY_2 = POTIONS.register("vulnerability_2",
            () -> new Potion(new MobEffectInstance(EffectInit.VULNERABILITY.get(), 1950, 1)));
    public static final RegistryObject<Potion> VULNERABILITY_3 = POTIONS.register("vulnerability_3",
            () -> new Potion(new MobEffectInstance(EffectInit.VULNERABILITY.get(), 1200, 2)));
    public static final RegistryObject<Potion> VULNERABILITY_4 = POTIONS.register("vulnerability_4",
            () -> new Potion(new MobEffectInstance(EffectInit.VULNERABILITY.get(), 450, 3)));

    public static final RegistryObject<Potion> VULNERABILITY_LONG = POTIONS.register("vulnerability_long",
            () -> new Potion(new MobEffectInstance(EffectInit.VULNERABILITY.get(), 4050, 0)));
    public static final RegistryObject<Potion> VULNERABILITY_2_LONG = POTIONS.register("vulnerability_2_long",
            () -> new Potion(new MobEffectInstance(EffectInit.VULNERABILITY.get(), 2925, 1)));
    public static final RegistryObject<Potion> VULNERABILITY_3_LONG = POTIONS.register("vulnerability_3_long",
            () -> new Potion(new MobEffectInstance(EffectInit.VULNERABILITY.get(), 1800, 2)));
    public static final RegistryObject<Potion> VULNERABILITY_4_LONG = POTIONS.register("vulnerability_4_long",
            () -> new Potion(new MobEffectInstance(EffectInit.VULNERABILITY.get(), 675, 3)));

}
