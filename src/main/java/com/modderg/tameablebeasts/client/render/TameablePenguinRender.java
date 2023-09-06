package com.modderg.tameablebeasts.client.render;

import com.modderg.tameablebeasts.client.model.TameablePenguinModel;
import com.modderg.tameablebeasts.entities.TameablePenguinEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TameablePenguinRender extends GeoEntityRenderer<TameablePenguinEntity> {

    public TameablePenguinRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, (GeoModel<TameablePenguinEntity>) new TameablePenguinModel());
    }

    @Override
    public void render(TameablePenguinEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }
}
