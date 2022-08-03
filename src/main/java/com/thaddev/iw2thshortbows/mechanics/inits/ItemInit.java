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
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, IWant2TryHardsShortbows.MODID);

    public static final RegistryObject<Item> WOODEN_SHORTBOW = ITEMS.register("wooden_shortbow",
        () -> new WoodenShortBowItem(new Item.Properties()
            .tab(CreativeModeTab.TAB_COMBAT)
            .stacksTo(1)
            .durability(125)));
    public static final RegistryObject<Item> IRON_SHORTBOW = ITEMS.register("iron_shortbow",
        () -> new IronShortBowItem(new Item.Properties()
            .tab(CreativeModeTab.TAB_COMBAT)
            .stacksTo(1)
            .durability(650)));
    public static final RegistryObject<Item> DIAMOND_SHORTBOW = ITEMS.register("diamond_shortbow",
        () -> new DiamondShortBowItem(new Item.Properties()
            .tab(CreativeModeTab.TAB_COMBAT)
            .stacksTo(1)
            .durability(1075)));

    public static final RegistryObject<Item> DIAMOND_HEADED_ARROW = ITEMS.register("diamond_headed_arrow",
        () -> new DiamondHeadedArrowItem(new Item.Properties()
            .tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> TIPPED_DIAMOND_HEADED_ARROW = ITEMS.register("tipped_diamond_headed_arrow",
        () -> new TippedDiamondHeadedArrowItem(new Item.Properties()
            .tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> TWEEZERS = ITEMS.register("tweezers",
        () -> new TweezersItem(new Item.Properties()
            .tab(CreativeModeTab.TAB_TOOLS)
            .stacksTo(1)
            .durability(400)));

    public static final RegistryObject<Item> RAW_RUBBER_BOTTLE = ITEMS.register("raw_rubber_bottle",
        () -> new Item(new Item.Properties()
            .tab(CreativeModeTab.TAB_MATERIALS)
            .craftRemainder(Items.GLASS_BOTTLE)
            .stacksTo(8)));
    public static final RegistryObject<Item> RUBBER_BOTTLE = ITEMS.register("rubber_bottle",
        () -> new Item(new Item.Properties()
            .tab(CreativeModeTab.TAB_MATERIALS)
            .craftRemainder(Items.GLASS_BOTTLE)
            .stacksTo(8)));
    public static final RegistryObject<Item> RUBBER_BAND = ITEMS.register("rubber_band",
        () -> new Item(new Item.Properties()
            .tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> LATEX_BAND = ITEMS.register("latex_band",
        () -> new Item(new Item.Properties()
            .tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> SPIDER_WEB = ITEMS.register("spider_web",
        () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> IRON_ROD = ITEMS.register("iron_rod",
        () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> DIAMOND_ROD = ITEMS.register("diamond_rod",
        () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> DIAMOND_ARROW_HEAD = ITEMS.register("diamond_arrow_head",
        () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> CARBON_FIBER = ITEMS.register("carbon_fiber",
        () -> new Item(new Item.Properties()
            .tab(CreativeModeTab.TAB_MATERIALS)
            .fireResistant()));
    public static final RegistryObject<Item> RAW_SILICON = ITEMS.register("raw_silicon",
        () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> SILICON = ITEMS.register("silicon",
        () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> SILICON_PCB = ITEMS.register("silicon_pcb",
        () -> new SiliconPCBItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> MICROCHIP = ITEMS.register("microchip",
        () -> new MicrochipItem(new Item.Properties()
            .tab(CreativeModeTab.TAB_MATERIALS)
            .stacksTo(1)
            .rarity(Rarity.UNCOMMON)));
}
