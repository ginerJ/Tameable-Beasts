package com.modderg.tameablebeasts.client.render;

import com.modderg.tameablebeasts.client.model.TameableChikoteModel;
import com.modderg.tameablebeasts.entities.TameableChikoteEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TameableChikoteRender extends GeoEntityRenderer<TameableChikoteEntity> {
    public TameableChikoteRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, (GeoModel<TameableChikoteEntity>) new TameableChikoteModel());
    }

    @Override
    public void render(TameableChikoteEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
        if(entity.getTextureID() == 2){
            stack.scale(1.15f, 1.15f, 1.15f);
        }
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }
}
