package com.modderg.tameablebeasts.client.entity.render;

import com.modderg.tameablebeasts.client.entity.model.BeetleDroneModel;
import com.modderg.tameablebeasts.client.entity.model.FlyingBeetleModel;
import com.modderg.tameablebeasts.server.entity.custom.BeetleDrone;
import com.modderg.tameablebeasts.server.entity.custom.FlyingBeetleEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BeetleDroneRender extends GeoEntityRenderer<BeetleDrone> {
    public BeetleDroneRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BeetleDroneModel());
        this.shadowRadius = 0.2f;
    }

    @Override
    public void renderRecursively(PoseStack poseStack, BeetleDrone animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, LightTexture.FULL_BRIGHT, packedOverlay, red, green, blue, alpha);
    }
}
