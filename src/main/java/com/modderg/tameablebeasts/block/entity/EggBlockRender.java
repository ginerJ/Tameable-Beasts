package com.modderg.tameablebeasts.block.entity;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class EggBlockRender extends GeoBlockRenderer<EggBlockEntity> {
    public EggBlockRender(BlockEntityRendererProvider.Context context) {
        super(new EggBlockModel());
    }
}
