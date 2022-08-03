package com.thaddev.iw2thshortbows.mechanics.inits;

import com.thaddev.iw2thshortbows.IWant2TryHardsShortbows;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class TagsInit {
    public static final TagKey<Block> REGULAR_LOGS = blockTag("regular_logs");
    public static final TagKey<Block> STRIPPED_LOGS = blockTag("stripped_logs");

    private static TagKey<Block> blockTag(String name) {
        return BlockTags.create(new ResourceLocation(IWant2TryHardsShortbows.MODID, name));
    }
}
