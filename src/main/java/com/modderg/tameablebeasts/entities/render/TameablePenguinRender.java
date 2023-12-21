package com.modderg.tameablebeasts.entities.render;

import com.modderg.tameablebeasts.entities.model.TameablePenguinModel;
import com.modderg.tameablebeasts.entities.custom.PenguinEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TameablePenguinRender extends GeoEntityRenderer<PenguinEntity> {

    public TameablePenguinRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, (GeoModel<PenguinEntity>) new TameablePenguinModel());
    }

    @Override
    public void render(PenguinEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }
}
