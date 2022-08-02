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

public class MicrochipInJungleTempleAdditionModifier extends LootModifier {

    protected MicrochipInJungleTempleAdditionModifier(LootItemCondition[] conditionsIn) {
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

    public static class Serialzer extends GlobalLootModifierSerializer<MicrochipInJungleTempleAdditionModifier> {

        @Override
        public MicrochipInJungleTempleAdditionModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] ailootcondition) {
            return new MicrochipInJungleTempleAdditionModifier(ailootcondition);
        }

        @Override
        public JsonObject write(MicrochipInJungleTempleAdditionModifier instance) {
            return makeConditions(instance.conditions);
        }
    }
}
