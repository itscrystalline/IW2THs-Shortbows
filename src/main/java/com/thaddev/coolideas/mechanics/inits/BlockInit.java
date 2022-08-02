package com.thaddev.coolideas.mechanics.inits;

import com.thaddev.coolideas.CoolIdeasMod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;

public class BlockInit {
    public static final Block SILICON_ORE = registerBlock("silicon_ore",
        new OreBlock(FabricBlockSettings.of(Material.STONE)
            .requiresTool()
            .strength(3f)
            .sounds(BlockSoundGroup.NETHER_ORE), UniformIntProvider.create(4, 10)),
        ItemGroup.MATERIALS
    );

    private static Block registerBlock(String name, Block block, ItemGroup tab) {
        registerBlockItem(name, block, tab);
        return Registry.register(Registry.BLOCK, new Identifier(CoolIdeasMod.MODID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup tab) {
        return Registry.register(
            Registry.ITEM,
            new Identifier(CoolIdeasMod.MODID, name),
            new BlockItem(block, new FabricItemSettings().group(tab)));
    }

    public static void registerBlocks() {
        CoolIdeasMod.LOGGER.debug("Registering Blocks for " + CoolIdeasMod.MODID + " (7/11)");
    }
}
