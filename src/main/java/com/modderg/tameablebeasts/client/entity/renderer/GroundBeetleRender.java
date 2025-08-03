package com.modderg.tameablebeasts.client.entity.renderer;

import com.modderg.tameablebeasts.client.entity.model.GroundBeetleModel;
import com.modderg.tameablebeasts.server.entity.GroundBeetleEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GroundBeetleRender extends GeoEntityRenderer<GroundBeetleEntity> {
    public GroundBeetleRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, (GeoModel<GroundBeetleEntity>) new GroundBeetleModel());
        this.shadowRadius = 0.6f;
    }

    @Override
    public void render(GroundBeetleEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
      if(entity.isBaby()){
          stack.scale(0.9f, 0.9f, 0.9f);
      }else {
          stack.scale(1.15f, 1.15f, 1.15f);
      }
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }
}
