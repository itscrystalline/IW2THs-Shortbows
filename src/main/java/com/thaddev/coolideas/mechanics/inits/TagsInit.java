package com.thaddev.coolideas.mechanics.inits;

import com.thaddev.coolideas.CoolIdeasMod;
import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TagsInit {
    public static final TagKey<Block> REGULAR_LOGS = blockTag("regular_logs");
    public static final TagKey<Block> STRIPPED_LOGS = blockTag("stripped_logs");

    private static TagKey<Block> blockTag(String id) {
        return TagKey.of(Registry.BLOCK_KEY, new Identifier(CoolIdeasMod.MODID, id));
    }
}
