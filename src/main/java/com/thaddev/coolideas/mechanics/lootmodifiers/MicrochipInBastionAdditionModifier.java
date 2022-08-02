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

public class MicrochipInBastionAdditionModifier extends LootModifier {

    protected MicrochipInBastionAdditionModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    @NotNull
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        if (context.getRandom().nextFloat() <= 0.25) {
            ItemStack toAdd = new ItemStack(ItemInit.MICROCHIP.get(), 1);
            MicrochipItem.setType(toAdd, MicrochipItem.MicrochipTypes.HOMING);
            generatedLoot.add(toAdd);
        }
        return generatedLoot;
    }

    public static class Serialzer extends GlobalLootModifierSerializer<MicrochipInBastionAdditionModifier> {
        @Override
        public MicrochipInBastionAdditionModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] ailootcondition) {
            return new MicrochipInBastionAdditionModifier(ailootcondition);
        }

        @Override
        public JsonObject write(MicrochipInBastionAdditionModifier instance) {
            return makeConditions(instance.conditions);
        }
    }
}
