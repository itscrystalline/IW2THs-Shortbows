package com.thaddev.iw2thshortbows.mechanics.inits;

import com.thaddev.iw2thshortbows.IWant2TryHardsShortbows;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockInit {
    public static  final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, IWant2TryHardsShortbows.MODID);

    public static RegistryObject<Block> SILICON_ORE = registerBlock("silicon_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties
                    .of(Material.STONE, MaterialColor.NETHER)
                        .requiresCorrectToolForDrops()
                        .strength(3f)
                        .sound(SoundType.NETHER_ORE), UniformInt.of(4, 10)),
            CreativeModeTab.TAB_MATERIALS);

    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> returnVal = BLOCKS.register(name, block);
        registerBlockItem(name, returnVal, tab);
        return returnVal;
    }
    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        return ItemInit.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }
}
