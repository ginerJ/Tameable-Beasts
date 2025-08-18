package com.modderg.tameablebeasts.client.entity.renderer;

import com.modderg.tameablebeasts.client.entity.model.QuetzalcoatlusModel;
import com.modderg.tameablebeasts.server.entity.QuetzalcoatlusEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class QuetzalcoatlusRenderer extends GeoEntityRenderer<QuetzalcoatlusEntity> {
    public QuetzalcoatlusRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new QuetzalcoatlusModel());
        this.shadowRadius = 0.7f;
    }

    @Override
    public void renderRecursively(PoseStack poseStack, QuetzalcoatlusEntity animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if(!animatable.hasSaddle() && bone.getName().contains("saddle"))
            return;

        if(!animatable.hasStand() && bone.getName().contains("stand"))
            return;

        if(!animatable.hasChest() && bone.getName().equals("chest"))
            return;

        super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
