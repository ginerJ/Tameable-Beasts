package com.modderg.tameablebeasts.entities.render;

import com.modderg.tameablebeasts.entities.model.TameableRacoonModel;
import com.modderg.tameablebeasts.entities.custom.RacoonEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TameableRacoonRender extends GeoEntityRenderer<RacoonEntity> {
    public TameableRacoonRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TameableRacoonModel());
    }
}
