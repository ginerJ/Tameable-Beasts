package com.modderg.tameablebeasts.client.render;

import com.modderg.tameablebeasts.client.model.GiantTameableGrasshopperModel;
import com.modderg.tameablebeasts.entities.GrasshopperEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GiantTameableGrasshopperRender extends GeoEntityRenderer<GrasshopperEntity> {
    public GiantTameableGrasshopperRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, (GeoModel<GrasshopperEntity>) new GiantTameableGrasshopperModel());
    }

    @Override
    public void render(GrasshopperEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
        if(entity.isBaby()){
            stack.scale(0.5f, 0.5f, 0.5f);
        }
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }
}
