package com.modderg.tameablebeasts.client.entity.render;

import com.modderg.tameablebeasts.client.entity.model.ScarecrowAllayModel;
import com.modderg.tameablebeasts.server.entity.custom.ScarecrowAllayEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ScarecrowAllayRender extends GeoEntityRenderer<ScarecrowAllayEntity> {
    public ScarecrowAllayRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ScarecrowAllayModel());
        this.shadowRadius = 0.5f;

    }

    @Override
    public void renderRecursively(PoseStack poseStack, ScarecrowAllayEntity animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if(bone.getName().contains("light"))
            packedLight = LightTexture.FULL_BRIGHT;

        if(!animatable.hasHoe() && bone.getName().equals("light_hoe"))
            return;

        super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
