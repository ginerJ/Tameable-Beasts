package com.modderg.tameablebeasts.client.entity.model;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.client.entity.TBGeoModel;
import com.modderg.tameablebeasts.server.entity.PenguinEntity;
import net.minecraft.resources.ResourceLocation;

public class PenguinModel extends TBGeoModel<PenguinEntity> {

    static final ResourceLocation[] textures = {
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/penguin0.png"),
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/penguin1.png"),
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/penguin2.png"),
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/penguin_baby.png")
    };

    static final ResourceLocation[] models = {
            new ResourceLocation(TameableBeasts.MOD_ID, "geo/penguin.geo.json"),
            new ResourceLocation(TameableBeasts.MOD_ID, "geo/penguin_baby.geo.json")
    };

    static final ResourceLocation animations = new ResourceLocation(TameableBeasts.MOD_ID, "animations/penguin.animation.json");


    @Override
    public ResourceLocation getModelResource(PenguinEntity entity) {
        if(entity.isBaby())
            return models[1];
        return models[0];
    }

    @Override
    public ResourceLocation getTextureResource(PenguinEntity entity) {
       if(entity.isBaby())
           return  textures[3];

        return textures[entity.getTextureID()];
    }

    @Override
    public ResourceLocation getAnimationResource(PenguinEntity entity) {
        return animations;
    }
}
