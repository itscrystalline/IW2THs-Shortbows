package com.thaddev.iw2thshortbows;

import com.mojang.logging.LogUtils;
import com.thaddev.iw2thshortbows.mechanics.inits.BlockInit;
import com.thaddev.iw2thshortbows.mechanics.inits.EffectInit;
import com.thaddev.iw2thshortbows.mechanics.inits.EnchantmentInit;
import com.thaddev.iw2thshortbows.mechanics.inits.EntityTypeInit;
import com.thaddev.iw2thshortbows.mechanics.inits.ItemInit;
import com.thaddev.iw2thshortbows.mechanics.inits.PotionInit;
import com.thaddev.iw2thshortbows.mechanics.inits.PotionRecipeInit;
import com.thaddev.iw2thshortbows.mechanics.inits.RecipeSerializerInit;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;


@Mod(IWant2TryHardsShortbows.MODID)
public class IWant2TryHardsShortbows {
    public static final String MODID = "iw2thshortbows";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static IWant2TryHardsShortbows instance;
    public static String VERSION = "1.1.0-patch2";

    public static final String MESSAGE_WELCOME = "message.iw2thshortbows.welcome";
    public static final String SCREEN_VERSION_MISMATCH = "menu.iw2thshortbows.modmismatch";

    public Minecraft mc;

    //CLIENT ONLY
    public boolean isMismatching = false;

    public IWant2TryHardsShortbows() {
        instance = this;
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        IWant2TryHardsShortbows.LOGGER.info("Initializing IWant2TryHardsShortbows version {}", VERSION);

        modEventBus.addListener(this::setup);
        ItemInit.ITEMS.register(modEventBus);
        EntityTypeInit.ENTITIES.register(modEventBus);
        EnchantmentInit.ENCHANTMENTS.register(modEventBus);
        EffectInit.MOB_EFFECTS.register(modEventBus);
        PotionInit.POTIONS.register(modEventBus);
        BlockInit.BLOCKS.register(modEventBus);
        RecipeSerializerInit.RECIPES.register(modEventBus);
    }

    public void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(PotionRecipeInit::register);
    }

    public static String buildVersionString(String modLoader) {
        return modLoader + "-mc" + SharedConstants.VERSION_STRING + "-" + VERSION;
    }
}
