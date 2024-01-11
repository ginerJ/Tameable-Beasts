package com.modderg.tameablebeasts.entities.render;

import com.modderg.tameablebeasts.entities.custom.ArgentavisEntity;
import com.modderg.tameablebeasts.entities.model.TameablePenguinModel;
import com.modderg.tameablebeasts.entities.custom.PenguinEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TameablePenguinRender extends GeoEntityRenderer<PenguinEntity> {

    public TameablePenguinRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, (GeoModel<PenguinEntity>) new TameablePenguinModel());
    }

    @Override
    public void renderRecursively(PoseStack poseStack, PenguinEntity animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if(!animatable.getHelmet() && bone.getName().contains("helmet")){
            return;
        }
        if(animatable.getSword()<1 && bone.getName().contains("sword")){
            return;
        }
        if(animatable.getSword()<2 && bone.getName().contains("sword2")){
            return;
        }
        if(!animatable.hasSaddle() && bone.getName().contains("armor")){
            return;
        }
        super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
