package com.modderg.tameablebeasts.entities.render;

import com.modderg.tameablebeasts.entities.model.ScarecrowAllayModel;
import com.modderg.tameablebeasts.entities.custom.ScarecrowAllayEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ScarecrowAllayRender extends GeoEntityRenderer<ScarecrowAllayEntity> {
    public ScarecrowAllayRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, (GeoModel<ScarecrowAllayEntity>) new ScarecrowAllayModel());
    }

    @Override
    public void render(ScarecrowAllayEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
        stack.scale(0.85f, 0.85f, 0.85f);
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }

    @Override
    public void renderRecursively(PoseStack poseStack, ScarecrowAllayEntity animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if(bone.getName().contains("light")){
            packedLight = LightTexture.FULL_BRIGHT;
        }
        super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
