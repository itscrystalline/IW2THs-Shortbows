package com.thaddev.coolideas.mechanics.lootmodifiers;

import com.google.gson.JsonObject;
import com.thaddev.coolideas.content.items.materials.MicrochipItem;
import com.thaddev.coolideas.mechanics.inits.ItemInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MicrochipInDesertTempleAdditionModifier extends LootModifier {

    protected MicrochipInDesertTempleAdditionModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    @NotNull
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        if (context.getRandom().nextFloat() <= 0.1) {
            ItemStack toAdd = new ItemStack(ItemInit.MICROCHIP.get(), 1);
            MicrochipItem.setType(toAdd, MicrochipItem.MicrochipTypes.HOMING);
            generatedLoot.add(toAdd);
        }
        return generatedLoot;
    }

    public static class Serialzer extends GlobalLootModifierSerializer<MicrochipInDesertTempleAdditionModifier> {
        @Override
        public MicrochipInDesertTempleAdditionModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] ailootcondition) {
            return new MicrochipInDesertTempleAdditionModifier(ailootcondition);
        }

        @Override
        public JsonObject write(MicrochipInDesertTempleAdditionModifier instance) {
            return makeConditions(instance.conditions);
        }
    }
}
