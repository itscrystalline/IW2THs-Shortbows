package com.thaddev.iw2thshortbows.client.renderer.entity;

import com.thaddev.iw2thshortbows.IWant2TryHardsShortbows;
import com.thaddev.iw2thshortbows.content.entities.projectiles.DiamondHeadedArrow;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(value = EnvType.CLIENT)
public class DiamondHeadedArrowRenderer extends ProjectileEntityRenderer<DiamondHeadedArrow> {
    public DiamondHeadedArrowRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(DiamondHeadedArrow arrowEntity) {
        return new Identifier(IWant2TryHardsShortbows.MODID, "textures/entity/projectiles/"
            + (arrowEntity.getColor() > 0 ? "tipped_" : "") + "diamond_headed_arrow.png");
    }
}
