package com.thaddev.coolideas;

import com.mojang.logging.LogUtils;
import com.thaddev.coolideas.mechanics.inits.BlockInit;
import com.thaddev.coolideas.mechanics.inits.ConfiguredFeaturesInit;
import com.thaddev.coolideas.mechanics.inits.EffectInit;
import com.thaddev.coolideas.mechanics.inits.EnchantmentInit;
import com.thaddev.coolideas.mechanics.inits.EntityTypeInit;
import com.thaddev.coolideas.mechanics.inits.GlobalLootModifierInit;
import com.thaddev.coolideas.mechanics.inits.ItemInit;
import com.thaddev.coolideas.mechanics.inits.PlacedFeaturesInit;
import com.thaddev.coolideas.mechanics.inits.PotionInit;
import com.thaddev.coolideas.mechanics.inits.PotionRecipeInit;
import com.thaddev.coolideas.mechanics.inits.RecipeSerializerInit;
import net.minecraft.client.Minecraft;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;


@Mod(CoolIdeasMod.MODID)
public class CoolIdeasMod {
    public static final String MODID = "coolideas";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static CoolIdeasMod instance;

    public Minecraft mc;

    public CoolIdeasMod() {
        instance = this;
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::setup);
        ItemInit.ITEMS.register(modEventBus);
        EntityTypeInit.ENTITIES.register(modEventBus);
        EnchantmentInit.ENCHANTMENTS.register(modEventBus);
        EffectInit.MOB_EFFECTS.register(modEventBus);
        PotionInit.POTIONS.register(modEventBus);
        BlockInit.BLOCKS.register(modEventBus);
        ConfiguredFeaturesInit.CONFIGURED_FEATURES.register(modEventBus);
        PlacedFeaturesInit.PLACED_FEATURES.register(modEventBus);
        GlobalLootModifierInit.GLOBAL_LOOT_MODIFIERS.register(modEventBus);
        RecipeSerializerInit.RECIPES.register(modEventBus);
    }

    public void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(PotionRecipeInit::register);
    }
}
