package com.thaddev.iw2thshortbows.mechanics.lootmodifiers;

import com.google.gson.JsonObject;
import com.thaddev.iw2thshortbows.content.items.materials.MicrochipItem;
import com.thaddev.iw2thshortbows.mechanics.inits.ItemInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MicrochipInDungeonAdditionModifier extends LootModifier {

    protected MicrochipInDungeonAdditionModifier(LootItemCondition[] conditionsIn) {
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

    public static class Serialzer extends GlobalLootModifierSerializer<MicrochipInDungeonAdditionModifier> {

        @Override
        public MicrochipInDungeonAdditionModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] ailootcondition) {
            return new MicrochipInDungeonAdditionModifier(ailootcondition);
        }

        @Override
        public JsonObject write(MicrochipInDungeonAdditionModifier instance) {
            return makeConditions(instance.conditions);
        }
    }
}
