package com.modderg.tameablebeasts.client.render;

import com.modderg.tameablebeasts.client.model.TameableTeethModel;
import com.modderg.tameablebeasts.entities.TameableTeethEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TameableTeethRender extends GeoEntityRenderer<TameableTeethEntity> {
    public TameableTeethRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, (GeoModel<TameableTeethEntity>) new TameableTeethModel());
    }

    @Override
    public void render(TameableTeethEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
        stack.scale(1.15f, 1.15f, 1.15f);
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }
}
