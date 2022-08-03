package com.thaddev.iw2thshortbows.content.items.materials;

import com.thaddev.iw2thshortbows.util.ColorUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.thaddev.iw2thshortbows.util.ColorUtils.component;

public class MicrochipItem extends Item {
    MicrochipTypes type;

    public MicrochipItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack thisStack, @Nullable Level world, List<Component> tag, TooltipFlag flags) {
        MicrochipTypes type;
        if ((type = getType(thisStack)) != null) {
            tag.add(component(ColorUtils.fromNoTag(type.getName())));
        }
        super.appendHoverText(thisStack, world, tag, flags);
    }

    public static MicrochipTypes getType(ItemStack stack) {
        CompoundTag compoundtag = stack.getTag();
        return compoundtag != null && compoundtag.getInt("MicroChipType") != 0 ? MicrochipTypes.getById(compoundtag.getInt("MicroChipType")) : MicrochipTypes.EMPTY;
    }

    public static void setType(ItemStack stack, MicrochipTypes type) {
        if (stack.getItem() instanceof MicrochipItem) {
            CompoundTag compoundtag = stack.getOrCreateTag();
            compoundtag.putInt("MicroChipType", type.getId());
        }
    }

    public enum MicrochipTypes {
        EMPTY(1, "(%$gray)Empty"),
        HOMING(2, "(%$green)Homing");

        private final int id;
        private final String name;

        MicrochipTypes(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public static MicrochipTypes getById(int id) {
            for (MicrochipTypes type : values()) {
                if (type.getId() == id) {
                    return type;
                }
            }
            return EMPTY;
        }

        public static MicrochipTypes[] getByIds(int[] ids) {
            MicrochipTypes[] types = new MicrochipTypes[ids.length];
            for (int i = 0; i < ids.length; i++) {
                types[i] = getById(ids[i]);
            }
            return types;
        }

        public static int[] getIntsFromMicrochipTypes(MicrochipItem.MicrochipTypes[] types){
            int[] ints = new int[types.length];
            for (int i = 0; i < types.length; i++) {
                ints[i] = types[i].getId();
            }
            return ints;
        }
    }
}

