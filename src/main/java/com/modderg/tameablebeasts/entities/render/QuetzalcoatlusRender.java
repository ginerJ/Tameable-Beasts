package com.modderg.tameablebeasts.entities.render;

import com.modderg.tameablebeasts.entities.model.QuetzalcoatlusModel;
import com.modderg.tameablebeasts.entities.custom.QuetzalcoatlusEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class QuetzalcoatlusRender extends GeoEntityRenderer<QuetzalcoatlusEntity> {
    public QuetzalcoatlusRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, (GeoModel<QuetzalcoatlusEntity>) new QuetzalcoatlusModel());
    }

    @Override
    public void render(QuetzalcoatlusEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
        stack.scale(1.25f, 1.25f, 1.25f);
        if(entity.isBaby()){
            stack.scale(0.35f, 0.35f, 0.35f);
        }
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }

    @Override
    public void renderRecursively(PoseStack poseStack, QuetzalcoatlusEntity animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if(!animatable.hasSaddle() && bone.getName().contains("saddle")){
            return;
        }
        super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
