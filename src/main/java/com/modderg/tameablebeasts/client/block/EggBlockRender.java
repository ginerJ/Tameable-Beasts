package com.modderg.tameablebeasts.client.block;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class EggBlockRender<T extends BlockEntity & GeoBlockEntity> extends GeoBlockRenderer<T> {

    public EggBlockRender(BlockEntityRendererProvider.Context context) {
        super(new EggBlockModel());
    }

    @Override
    public RenderType getRenderType(T animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucentCull(texture);
    }
}
