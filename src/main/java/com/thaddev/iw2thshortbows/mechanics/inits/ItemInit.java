package com.thaddev.iw2thshortbows.mechanics.inits;

import com.thaddev.iw2thshortbows.IWant2TryHardsShortbows;
import com.thaddev.iw2thshortbows.content.items.materials.MicrochipItem;
import com.thaddev.iw2thshortbows.content.items.materials.SiliconPCBItem;
import com.thaddev.iw2thshortbows.content.items.tools.TweezersItem;
import com.thaddev.iw2thshortbows.content.items.weapons.DiamondHeadedArrowItem;
import com.thaddev.iw2thshortbows.content.items.weapons.DiamondShortBowItem;
import com.thaddev.iw2thshortbows.content.items.weapons.IronShortBowItem;
import com.thaddev.iw2thshortbows.content.items.weapons.TippedDiamondHeadedArrowItem;
import com.thaddev.iw2thshortbows.content.items.weapons.WoodenShortBowItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ItemInit {
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(IWant2TryHardsShortbows.MODID, name), item);
    }

    public static final Item WOODEN_SHORTBOW = registerItem("wooden_shortbow",
        new WoodenShortBowItem(new FabricItemSettings()
            .group(ItemGroup.COMBAT)
            .maxCount(1)
            .maxDamage(125)));
    public static final Item IRON_SHORTBOW = registerItem("iron_shortbow",
        new IronShortBowItem(new FabricItemSettings()
            .group(ItemGroup.COMBAT)
            .maxCount(1)
            .maxDamage(650)));
    public static final Item DIAMOND_SHORTBOW = registerItem("diamond_shortbow",
        new DiamondShortBowItem(new FabricItemSettings()
            .group(ItemGroup.COMBAT)
            .maxCount(1)
            .maxDamage(1075)));

    public static final Item DIAMOND_HEADED_ARROW = registerItem("diamond_headed_arrow",
        new DiamondHeadedArrowItem(new FabricItemSettings()
            .group(ItemGroup.COMBAT)));
    public static final Item TIPPED_DIAMOND_HEADED_ARROW = registerItem("tipped_diamond_headed_arrow",
        new TippedDiamondHeadedArrowItem(new FabricItemSettings()
            .group(ItemGroup.COMBAT)));

    public static final Item TWEEZERS = registerItem("tweezers",
        new TweezersItem(new FabricItemSettings()
            .group(ItemGroup.TOOLS)
            .maxCount(1)
            .maxDamage(400)));

    public static final Item RAW_RUBBER_BOTTLE = registerItem("raw_rubber_bottle",
        new Item(new FabricItemSettings()
            .group(ItemGroup.MATERIALS)
            .recipeRemainder(Items.GLASS_BOTTLE)
            .maxCount(8)));
    public static final Item RUBBER_BOTTLE = registerItem("rubber_bottle",
        new Item(new FabricItemSettings()
            .group(ItemGroup.MATERIALS)
            .recipeRemainder(Items.GLASS_BOTTLE)
            .maxCount(8)));
    public static final Item RUBBER_BAND = registerItem("rubber_band",
        new Item(new FabricItemSettings()
            .group(ItemGroup.MATERIALS)));
    public static final Item LATEX_BAND = registerItem("latex_band",
        new Item(new FabricItemSettings()
            .group(ItemGroup.MATERIALS)));
    public static final Item IRON_ROD = registerItem("iron_rod",
        new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item SPIDER_WEB = registerItem("spider_web",
        new Item(new FabricItemSettings().group(ItemGroup.MATERIALS)));
    public static final Item DIAMOND_ROD = registerItem("diamond_rod",
        new Item(new FabricItemSettings().group(ItemGroup.MATERIALS)));
    public static final Item DIAMOND_ARROW_HEAD = registerItem("diamond_arrow_head",
        new Item(new FabricItemSettings().group(ItemGroup.COMBAT)));
    public static final Item CARBON_FIBER = registerItem("carbon_fiber",
        new Item(new FabricItemSettings()
            .group(ItemGroup.MATERIALS)
            .fireproof()));
    public static final Item RAW_SILICON = registerItem("raw_silicon",
        new Item(new FabricItemSettings().group(ItemGroup.MATERIALS)));
    public static final Item SILICON = registerItem("silicon",
        new Item(new FabricItemSettings().group(ItemGroup.MATERIALS)));
    public static final Item SILICON_PCB = registerItem("silicon_pcb",
        new SiliconPCBItem(new FabricItemSettings().group(ItemGroup.MATERIALS)));
    public static final Item MICROCHIP = registerItem("microchip",
        new MicrochipItem(new FabricItemSettings()
            .group(ItemGroup.MATERIALS)
            .maxDamage(1)
            .rarity(Rarity.UNCOMMON)));


    public static void registerItems() {
        IWant2TryHardsShortbows.LOGGER.debug("Registering Items for " + IWant2TryHardsShortbows.MODID + " (3/11)");
    }
}
