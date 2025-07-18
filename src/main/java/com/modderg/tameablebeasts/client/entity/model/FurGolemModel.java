package com.modderg.tameablebeasts.client.entity.model;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.server.entity.FurGolemEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FurGolemModel extends GeoModel<FurGolemEntity> {

    @Override
    public ResourceLocation getModelResource(FurGolemEntity entity) {
        return new ResourceLocation(TameableBeasts.MOD_ID, "geo/fur_golem.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FurGolemEntity entity) {
        return new ResourceLocation(TameableBeasts.MOD_ID,"textures/entity/fur_golem.png");
    }

    @Override
    public ResourceLocation getAnimationResource(FurGolemEntity entity) {
        return new ResourceLocation(TameableBeasts.MOD_ID, "animations/fur_golem.anims.json");
    }
}