package com.modderg.tameablebeasts.client.entity.model;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.client.entity.TBGeoModel;
import com.modderg.tameablebeasts.server.entity.CrestedGeckoEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class CrestedGeckoModel extends TBGeoModel<CrestedGeckoEntity> {

    static final ResourceLocation[] textures = {
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/crested_gecko0.png"),
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/crested_gecko1.png"),
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/crested_gecko2.png"),
    };

    static final ResourceLocation[] models = {
            new ResourceLocation(TameableBeasts.MOD_ID, "geo/crested_gecko.geo.json"),
            new ResourceLocation(TameableBeasts.MOD_ID, "geo/crested_gecko_baby.geo.json")
    };

    static final ResourceLocation animations = new ResourceLocation(TameableBeasts.MOD_ID, "animations/crested_gecko.animation.json");


    @Override
    public ResourceLocation getModelResource(CrestedGeckoEntity entity) {
        if(entity.isBaby())
            return models[1];
        return models[0];
    }

    @Override
    public ResourceLocation getTextureResource(CrestedGeckoEntity entity) {
        return textures[entity.getTextureID()];
    }

    @Override
    public ResourceLocation getAnimationResource(CrestedGeckoEntity entity) {
        return animations;
    }
}