package com.thaddev.coolideas.content.items.materials;

import com.thaddev.coolideas.util.ColorUtils;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.thaddev.coolideas.util.ColorUtils.component;

public class SiliconPCBItem extends Item {
    MicrochipItem.MicrochipTypes[] types;

    public SiliconPCBItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(@NotNull ItemStack stack, @Nullable World world, @NotNull List<Text> tooltip, @NotNull TooltipContext context) {
        for (MicrochipItem.MicrochipTypes type : getTypes(stack)) {
            tooltip.add(component(ColorUtils.fromNoTag(type.getName())));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    public static MicrochipItem.MicrochipTypes[] getTypes(ItemStack stack) {
        NbtCompound compoundtag = stack.getNbt();
        return compoundtag != null && compoundtag.getIntArray("MicroChipTypes").length != 0 ?
            MicrochipItem.MicrochipTypes.getByIds(compoundtag.getIntArray("MicroChipTypes")) :
            new MicrochipItem.MicrochipTypes[]{};
    }

    public static void setTypes(ItemStack stack, MicrochipItem.MicrochipTypes[] types) {
        if (stack.getItem() instanceof SiliconPCBItem) {
            NbtCompound compoundtag = stack.getOrCreateNbt();
            compoundtag.putIntArray("MicroChipTypes", MicrochipItem.MicrochipTypes.getIntsFromMicrochipTypes(types));
        }
    }
}
