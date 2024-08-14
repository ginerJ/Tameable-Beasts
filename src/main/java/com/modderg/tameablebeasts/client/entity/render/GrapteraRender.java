package com.modderg.tameablebeasts.client.entity.render;

import com.modderg.tameablebeasts.client.entity.model.GrapteraModel;
import com.modderg.tameablebeasts.server.entity.custom.GrapteranodonEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GrapteraRender extends GeoEntityRenderer<GrapteranodonEntity> {
    public GrapteraRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new GrapteraModel());
        this.shadowRadius = 0.7f;
    }

    @Override
    public void renderRecursively(PoseStack poseStack, GrapteranodonEntity animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if(!animatable.hasSaddle() && bone.getName().contains("saddle"))
            return;

        super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
