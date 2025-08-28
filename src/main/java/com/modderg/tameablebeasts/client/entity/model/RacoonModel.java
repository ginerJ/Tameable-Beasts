package com.modderg.tameablebeasts.client.entity.model;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.client.entity.TBGeoModel;
import com.modderg.tameablebeasts.server.entity.RacoonEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class RacoonModel extends TBGeoModel<RacoonEntity> {

    static final ResourceLocation[] textures = {
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/racoon0.png"),
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/racoon1.png"),
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/racoon2.png")
    };

    static final ResourceLocation[] models = {
            new ResourceLocation(TameableBeasts.MOD_ID, "geo/racoon.geo.json"),
            new ResourceLocation(TameableBeasts.MOD_ID, "geo/racoon_baby.geo.json"),
            new ResourceLocation(TameableBeasts.MOD_ID, "geo/racoon_fat.geo.json")
    };

    static final ResourceLocation[] animations = {
            new ResourceLocation(TameableBeasts.MOD_ID, "animations/racoon.animation.json"),
            new ResourceLocation(TameableBeasts.MOD_ID, "animations/racoon_baby.animation.json")
    };

    @Override
    public ResourceLocation getModelResource(RacoonEntity entity) {
        if (entity.isBaby())
            return models[1];

        if (entity.isBellyFull())
            return models[2];

        return models[0];
    }

    @Override
    public ResourceLocation getTextureResource(RacoonEntity entity) {
        return textures[entity.getTextureID()];
    }

    @Override
    public ResourceLocation getAnimationResource(RacoonEntity entity) {
        if (entity.isBaby())
            return animations[1];
        return animations[0];
    }
}