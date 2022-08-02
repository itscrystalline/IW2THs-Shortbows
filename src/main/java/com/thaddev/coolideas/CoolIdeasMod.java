package com.thaddev.coolideas;

import com.thaddev.coolideas.mechanics.Events;
import com.thaddev.coolideas.mechanics.inits.BlockInit;
import com.thaddev.coolideas.mechanics.inits.ConfiguredFeaturesInit;
import com.thaddev.coolideas.mechanics.inits.EffectInit;
import com.thaddev.coolideas.mechanics.inits.EnchantmentInit;
import com.thaddev.coolideas.mechanics.inits.EntityTypeInit;
import com.thaddev.coolideas.mechanics.inits.ItemInit;
import com.thaddev.coolideas.mechanics.inits.LootTableModifierInit;
import com.thaddev.coolideas.mechanics.inits.OreGenerationInit;
import com.thaddev.coolideas.mechanics.inits.PotionInit;
import com.thaddev.coolideas.mechanics.inits.RecipeSerializerInit;
import com.thaddev.coolideas.util.CustomLogger;
import net.fabricmc.api.ModInitializer;

public class CoolIdeasMod implements ModInitializer {
	public static final String MODID = "coolideas";
	public static final CustomLogger LOGGER = new CustomLogger(MODID);
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
		EnchantmentInit.registerEnchantments();
		BlockInit.registerBlocks();
		OreGenerationInit.generateOres();
		EffectInit.registerEffects();
        PotionInit.registerPotions();
        RecipeSerializerInit.registerRecipes();
	}
}
