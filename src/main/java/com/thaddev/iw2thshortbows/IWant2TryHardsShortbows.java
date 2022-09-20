package com.thaddev.iw2thshortbows;

import com.thaddev.iw2thshortbows.mechanics.Events;
import com.thaddev.iw2thshortbows.mechanics.inits.BlockInit;
import com.thaddev.iw2thshortbows.mechanics.inits.ConfiguredFeaturesInit;
import com.thaddev.iw2thshortbows.mechanics.inits.EffectInit;
import com.thaddev.iw2thshortbows.mechanics.inits.EnchantmentInit;
import com.thaddev.iw2thshortbows.mechanics.inits.EntityTypeInit;
import com.thaddev.iw2thshortbows.mechanics.inits.ItemInit;
import com.thaddev.iw2thshortbows.mechanics.inits.LootTableModifierInit;
import com.thaddev.iw2thshortbows.mechanics.inits.OreGenerationInit;
import com.thaddev.iw2thshortbows.mechanics.inits.PotionInit;
import com.thaddev.iw2thshortbows.mechanics.inits.RecipeSerializerInit;
import com.thaddev.iw2thshortbows.util.CustomLogger;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class IWant2TryHardsShortbows implements ModInitializer {
	public static final String MODID = "iw2thshortbows";
	public static final CustomLogger LOGGER = new CustomLogger(MODID);
	public static IWant2TryHardsShortbows instance;
	public static IWant2TryHardsShortbowsClient client;
	public static String VERSION = "1.1.2";

	public static final String MESSAGE_WELCOME = "message.iw2thshortbows.welcome";
	public static final String SCREEN_VERSION_MISMATCH = "menu.iw2thshortbows.modmismatch";

	//CLIENT ONLY
	public boolean isMismatching = false;

	@Override
	public void onInitialize() {
		instance = this;

		if (FabricLoader.getInstance().isModLoaded("immersive_weathering")) {
			LOGGER.debug("Immersive Weathering (modid: immersive_weathering) detected! Mixing into `ModEvents#lambda$registerEvents$5`!");
		}

		IWant2TryHardsShortbows.LOGGER.debug("Initializing IWant2TryHardsShortbows version " + VERSION);

		Events.registerEvents();
		ConfiguredFeaturesInit.registerConfiguredFeatures();
        ItemInit.registerItems();
        LootTableModifierInit.modifyLootTables();
		EntityTypeInit.registerEntityTypes();
		EnchantmentInit.registerEnchantments();
		BlockInit.registerBlocks();
		OreGenerationInit.generateOres();
		EffectInit.registerEffects();
        PotionInit.registerPotions();
        RecipeSerializerInit.registerRecipes();
	}

	public static String buildVersionString(String modLoader) {
		return modLoader + "-" + VERSION;
	}
}
