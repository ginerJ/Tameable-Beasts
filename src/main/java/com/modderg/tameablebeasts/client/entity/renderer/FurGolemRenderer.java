package com.modderg.tameablebeasts.client.entity.renderer;

import com.modderg.tameablebeasts.client.entity.model.FurGolemModel;
import com.modderg.tameablebeasts.server.entity.FurGolemEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FurGolemRenderer extends GeoEntityRenderer<FurGolemEntity> {
    public FurGolemRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, (GeoModel<FurGolemEntity>) new FurGolemModel());
        this.shadowRadius = 0.6f;
    }
}
