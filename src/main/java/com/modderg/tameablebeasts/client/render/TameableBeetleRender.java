package com.modderg.tameablebeasts.client.render;

import com.modderg.tameablebeasts.client.model.TameableBeetleModel;
import com.modderg.tameablebeasts.entities.FlyingBeetleEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TameableBeetleRender extends GeoEntityRenderer<FlyingBeetleEntity> {
    public TameableBeetleRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, (GeoModel<FlyingBeetleEntity>) new TameableBeetleModel());
    }

    @Override
    public void render(FlyingBeetleEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
        if(entity.isBaby()){
            stack.scale(0.75f, 0.75f, 0.75f);
        }
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }

    @Override
    public void renderRecursively(PoseStack poseStack, FlyingBeetleEntity animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if(bone.getName().equals("elytra") || bone.getName().equals("elytra3") || bone.getName().equals("eye") || bone.getName().equals("eye2")){
            packedLight = LightTexture.FULL_BRIGHT;
        }
        super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
