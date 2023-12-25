package com.modderg.tameablebeasts.item.block;

import com.modderg.tameablebeasts.block.entity.EggBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class EggBlockItemRenderer extends GeoItemRenderer<EggBlockItem> {
    public EggBlockItemRenderer() {
        super(new EggBlockItemModel());
    }

    @Override
    public RenderType getRenderType(EggBlockItem animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucentCull(texture);
    }
}

