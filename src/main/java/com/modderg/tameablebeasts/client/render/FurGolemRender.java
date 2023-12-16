package com.modderg.tameablebeasts.client.render;

import com.modderg.tameablebeasts.client.model.FurGolemModel;
import com.modderg.tameablebeasts.client.model.TameableRacoonModel;
import com.modderg.tameablebeasts.entities.FurGolemEntity;
import com.modderg.tameablebeasts.entities.RacoonEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FurGolemRender extends GeoEntityRenderer<FurGolemEntity> {
    public FurGolemRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, (GeoModel<FurGolemEntity>) new FurGolemModel());
    }

    @Override
    public void render(FurGolemEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }
}
