package com.thaddev.coolideas;

import com.thaddev.coolideas.mechanics.Events;
import com.thaddev.coolideas.mechanics.inits.BlockInit;
import com.thaddev.coolideas.mechanics.inits.ConfiguredFeaturesInit;
import com.thaddev.coolideas.mechanics.inits.EffectInit;
import com.thaddev.coolideas.mechanics.inits.EntityTypeInit;
import com.thaddev.coolideas.mechanics.inits.ItemInit;
import com.thaddev.coolideas.mechanics.inits.LootTableModifierInit;
import com.thaddev.coolideas.mechanics.inits.OreGenerationInit;
import com.thaddev.coolideas.mechanics.inits.PotionInit;
import com.thaddev.coolideas.mechanics.inits.RecipeSerializerInit;
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
        LootTableModifierInit.modifyLootTables();
        EntityTypeInit.registerEntityTypes();
		BlockInit.registerBlocks();
		OreGenerationInit.generateOres();
		EffectInit.registerEffects();
        PotionInit.registerPotions();
        RecipeSerializerInit.registerRecipes();
	}

	@Environment(value = EnvType.CLIENT)
	public void printMessage(String message) {
        if (client != null) client.printMessage(message);
        else LOGGER.error("Cannot find Client! Is this world running on an integrated server?");
    }
}
