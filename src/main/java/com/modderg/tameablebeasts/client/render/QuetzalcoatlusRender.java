package com.modderg.tameablebeasts.client.render;

import com.modderg.tameablebeasts.client.model.QuetzalcoatlusModel;
import com.modderg.tameablebeasts.entities.QuetzalcoatlusEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
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
}
