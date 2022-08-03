package com.thaddev.iw2thshortbows.client.renderer.entity;

import com.thaddev.iw2thshortbows.IWant2TryHardsShortbows;
import com.thaddev.iw2thshortbows.content.entities.projectiles.DiamondHeadedArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class DiamondHeadedArrowRenderer extends ArrowRenderer<DiamondHeadedArrow> {

    public DiamondHeadedArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(DiamondHeadedArrow entity) {
        Item refItem = entity.getPickupItem().getItem();
        return new ResourceLocation(IWant2TryHardsShortbows.MODID, "textures/entity/projectiles/"
                + Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(refItem)).getPath() + ".png");
    }
}
