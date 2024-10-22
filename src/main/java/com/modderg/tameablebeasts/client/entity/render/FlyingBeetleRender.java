package com.modderg.tameablebeasts.client.entity.render;

import com.modderg.tameablebeasts.client.entity.model.FlyingBeetleModel;
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

public class FlyingBeetleRender extends GeoEntityRenderer<FlyingBeetleEntity> {
    public FlyingBeetleRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FlyingBeetleModel());
        this.shadowRadius = 0.6f;
    }

    @Override
    public void render(FlyingBeetleEntity entity, float entityYaw, float partialTicks, @NotNull PoseStack stack, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
        if(entity.isBaby()){
            stack.scale(0.75f, 0.75f, 0.75f);
        }
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }

    @Override
    public void renderRecursively(PoseStack poseStack, FlyingBeetleEntity animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        String boneName = bone.getName();
        if(boneName.equals("elytra") || boneName.equals("elytra3") || boneName.equals("eye") || boneName.equals("eye2") || boneName.equals("belly")){
            packedLight = LightTexture.FULL_BRIGHT;
        }
        super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
