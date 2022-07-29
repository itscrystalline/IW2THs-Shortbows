package com.thaddev.coolideas.content.items.materials;

import com.thaddev.coolideas.util.ColorUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.thaddev.coolideas.util.ColorUtils.component;

public class SiliconPCBItem extends Item {
    MicrochipItem.MicrochipTypes[] types;

    public SiliconPCBItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack thisStack, @Nullable Level world, List<Component> tag, TooltipFlag flags) {
        for (MicrochipItem.MicrochipTypes type : getTypes(thisStack)) {
            tag.add(component(ColorUtils.fromNoTag(type.getName())));
        }
        super.appendHoverText(thisStack, world, tag, flags);
    }

    public static MicrochipItem.MicrochipTypes[] getTypes(ItemStack stack) {
        CompoundTag compoundtag = stack.getTag();
        return compoundtag != null && compoundtag.getIntArray("MicroChipTypes").length != 0 ?
            MicrochipItem.MicrochipTypes.getByIds(compoundtag.getIntArray("MicroChipTypes")) :
            new MicrochipItem.MicrochipTypes[]{};
    }

    public static void setTypes(ItemStack stack, MicrochipItem.MicrochipTypes[] types) {
        if (stack.getItem() instanceof SiliconPCBItem) {
            CompoundTag compoundtag = stack.getOrCreateTag();
            compoundtag.putIntArray("MicroChipTypes", MicrochipItem.MicrochipTypes.getIntsFromMicrochipTypes(types));
        }
    }
}
