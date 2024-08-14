package com.modderg.tameablebeasts.client.entity.render;

import com.modderg.tameablebeasts.client.entity.model.GrasshopperModel;
import com.modderg.tameablebeasts.server.entity.custom.GrasshopperEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GiantTameableGrasshopperRender extends GeoEntityRenderer<GrasshopperEntity> {
    public GiantTameableGrasshopperRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, (GeoModel<GrasshopperEntity>) new GrasshopperModel());
        this.shadowRadius = 0.8f;
    }
}
