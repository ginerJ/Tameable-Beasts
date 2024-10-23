package com.modderg.tameablebeasts.client.entity.render;

import com.modderg.tameablebeasts.client.entity.model.RacoonModel;
import com.modderg.tameablebeasts.server.entity.custom.RacoonEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TameableRacoonRender extends GeoEntityRenderer<RacoonEntity> {
    public TameableRacoonRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new RacoonModel());
        this.shadowRadius = 0.4f;
    }
}
