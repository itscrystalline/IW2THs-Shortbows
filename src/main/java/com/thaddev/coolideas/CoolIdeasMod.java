package com.thaddev.coolideas;

import com.thaddev.coolideas.mechanics.Events;
import com.thaddev.coolideas.mechanics.inits.BlockInit;
import com.thaddev.coolideas.mechanics.inits.ConfiguredFeaturesInit;
import com.thaddev.coolideas.mechanics.inits.EffectInit;
import com.thaddev.coolideas.mechanics.inits.EntityTypeInit;
import com.thaddev.coolideas.mechanics.inits.ItemInit;
import com.thaddev.coolideas.mechanics.inits.OreGenerationInit;
import com.thaddev.coolideas.mechanics.inits.PotionInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoolIdeasMod implements ModInitializer {
	public static final String MODID = "coolideas";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	public static CoolIdeasMod instance;
	public static CoolIdeasModClient client;

	@Override
	public void onInitialize() {
		instance = this;

		Events.registerEvents();

		ConfiguredFeaturesInit.registerConfiguredFeatures();
		ItemInit.registerItems();
		EntityTypeInit.registerEntityTypes();
		BlockInit.registerBlocks();
		OreGenerationInit.generateOres();
		EffectInit.registerEffects();
		PotionInit.registerPotions();
	}

	@Environment(value = EnvType.CLIENT)
	public void printMessage(String message) {
		client.printMessage(message);
	}
}
