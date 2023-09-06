package com.modderg.tameablebeasts.client.render;

import com.modderg.tameablebeasts.client.model.TameableRacoonModel;
import com.modderg.tameablebeasts.entities.TameableRacoonEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TameableRacoonRender extends GeoEntityRenderer<TameableRacoonEntity> {
    public TameableRacoonRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, (GeoModel<TameableRacoonEntity>) new TameableRacoonModel());
    }

    @Override
    public void render(TameableRacoonEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }
}
