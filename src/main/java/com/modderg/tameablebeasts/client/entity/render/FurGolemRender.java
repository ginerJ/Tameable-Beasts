package com.modderg.tameablebeasts.client.entity.render;

import com.modderg.tameablebeasts.client.entity.model.FurGolemModel;
import com.modderg.tameablebeasts.server.entity.custom.FurGolemEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FurGolemRender extends GeoEntityRenderer<FurGolemEntity> {
    public FurGolemRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, (GeoModel<FurGolemEntity>) new FurGolemModel());
        this.shadowRadius = 0.6f;
    }
}
