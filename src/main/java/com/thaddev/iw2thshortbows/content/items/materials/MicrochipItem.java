package com.thaddev.iw2thshortbows.content.items.materials;

import com.thaddev.iw2thshortbows.util.Utils;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.thaddev.iw2thshortbows.util.Utils.component;

public class MicrochipItem extends Item {
    MicrochipTypes type;

    public MicrochipItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(@NotNull ItemStack stack, @Nullable World world, @NotNull List<Text> tooltip, @NotNull TooltipContext context) {
        MicrochipTypes type;
        if ((type = getType(stack)) != null) {
            tooltip.add(component(Utils.fromNoTag(type.getName())));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    public static MicrochipTypes getType(ItemStack stack) {
        NbtCompound compoundtag = stack.getNbt();
        return compoundtag != null && compoundtag.getInt("MicroChipType") != 0 ? MicrochipTypes.getById(compoundtag.getInt("MicroChipType")) : MicrochipTypes.EMPTY;
    }

    public static void setType(ItemStack stack, MicrochipTypes type) {
        if (stack.getItem() instanceof MicrochipItem) {
            NbtCompound compoundtag = stack.getOrCreateNbt();
            compoundtag.putInt("MicroChipType", type.getId());
        }
    }

    public static NbtCompound getType(MicrochipTypes type) {
        NbtCompound compoundtag = new NbtCompound();
        compoundtag.putInt("MicroChipType", type.getId());
        return compoundtag;
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

        public static int[] getIntsFromMicrochipTypes(MicrochipTypes[] types) {
            int[] ints = new int[types.length];
            for (int i = 0; i < types.length; i++) {
                ints[i] = types[i].getId();
            }
            return ints;
        }
    }
}

