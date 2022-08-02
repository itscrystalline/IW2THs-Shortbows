package com.thaddev.coolideas.mechanics.inits;

import com.thaddev.coolideas.CoolIdeasMod;
import com.thaddev.coolideas.content.items.materials.MicrochipItem;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.function.SetNbtLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class LootTableModifierInit {
    private static final Identifier DUNGEON_CHEST = new Identifier("minecraft", "chests/simple_dungeon");
    private static final Identifier JUNGLE_TEMPLE = new Identifier("minecraft", "chests/jungle_temple_dispenser");
    private static final Identifier DESERT_TEMPLE = new Identifier("minecraft", "chests/desert_pyramid");
    private static final Identifier BASTION_BRIDGE = new Identifier("minecraft", "chests/bastion_bridge");
    private static final Identifier BASTION_HOGLIN_STABLE = new Identifier("minecraft", "chests/bastion_hoglin_stable");
    private static final Identifier BASTION_TREASURE = new Identifier("minecraft", "chests/bastion_treasure");
    private static final Identifier BASTION_OTHER = new Identifier("minecraft", "chests/bastion_other");

    public static void modifyLootTables() {
        CoolIdeasMod.LOGGER.debug("Modifying Loot tables for " + CoolIdeasMod.MODID + " (4/11)");
        LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (DUNGEON_CHEST.equals(id) || JUNGLE_TEMPLE.equals(id) || DESERT_TEMPLE.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(RandomChanceLootCondition.builder(0.1f))
                    .with(ItemEntry.builder(ItemInit.MICROCHIP))
                    .apply(SetNbtLootFunction.builder(MicrochipItem.getType(MicrochipItem.MicrochipTypes.HOMING)))
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 1f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
            if (BASTION_BRIDGE.equals(id) || BASTION_HOGLIN_STABLE.equals(id) || BASTION_TREASURE.equals(id) || BASTION_OTHER.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(RandomChanceLootCondition.builder(0.25f))
                    .with(ItemEntry.builder(ItemInit.MICROCHIP))
                    .apply(SetNbtLootFunction.builder(MicrochipItem.getType(MicrochipItem.MicrochipTypes.HOMING)))
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 1f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
        }));
    }
}
