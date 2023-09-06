package com.modderg.tameablebeasts.client.render;

import com.modderg.tameablebeasts.client.model.GiantTameableRolyPolyModel;
import com.modderg.tameablebeasts.entities.GiantTameableRolyPolyEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GiantTameableRolyPolyRender extends GeoEntityRenderer<GiantTameableRolyPolyEntity> {
    public GiantTameableRolyPolyRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, (GeoModel<GiantTameableRolyPolyEntity>) new GiantTameableRolyPolyModel());
    }

    @Override
    public void render(GiantTameableRolyPolyEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
        if(entity.isBaby()){
            stack.scale(0.45f, 0.45f, 0.45f);
        }
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }

}
