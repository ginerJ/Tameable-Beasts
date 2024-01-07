package com.modderg.tameablebeasts.entities.render;

import com.modderg.tameablebeasts.entities.model.FurGolemModel;
import com.modderg.tameablebeasts.entities.custom.FurGolemEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FurGolemRender extends GeoEntityRenderer<FurGolemEntity> {
    public FurGolemRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, (GeoModel<FurGolemEntity>) new FurGolemModel());
    }
}
