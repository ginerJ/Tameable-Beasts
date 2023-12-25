package com.modderg.tameablebeasts.block.entity;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class EggBlockRender extends GeoBlockRenderer<EggBlockEntity> {
    public EggBlockRender(BlockEntityRendererProvider.Context context) {
        super(new EggBlockModel());
    }

    @Override
    public RenderType getRenderType(EggBlockEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucentCull(texture);
    }
}
