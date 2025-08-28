package com.modderg.tameablebeasts.client.entity.model;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.client.entity.TBGeoModel;
import com.modderg.tameablebeasts.server.entity.ArgentavisEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ArgentavisModel extends TBGeoModel<ArgentavisEntity> {

    static final ResourceLocation[] textures = {
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/argentavis0.png"),
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/argentavis1.png"),
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/argentavis2.png"),
    };

    static final ResourceLocation[] models = {
            new ResourceLocation(TameableBeasts.MOD_ID, "geo/argentavis.geo.json"),
            new ResourceLocation(TameableBeasts.MOD_ID, "geo/argentavis_baby.geo.json")
    };

    static final ResourceLocation animations = new ResourceLocation(TameableBeasts.MOD_ID, "animations/argentavis.animation.json");


    @Override
    public ResourceLocation getModelResource(ArgentavisEntity entity) {
        if(entity.isBaby())
            return models[1];
        return models[0];
    }

    @Override
    public ResourceLocation getTextureResource(ArgentavisEntity entity) {
        return textures[entity.getTextureID()];
    }

    @Override
    public ResourceLocation getAnimationResource(ArgentavisEntity entity) {
        return animations;
    }
}