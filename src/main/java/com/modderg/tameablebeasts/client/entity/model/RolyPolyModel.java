package com.modderg.tameablebeasts.client.entity.model;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.server.entity.PenguinEntity;
import com.modderg.tameablebeasts.server.entity.RolyPolyEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class RolyPolyModel extends GeoModel<RolyPolyEntity> {

    static final ResourceLocation[] textures = {
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/roly_poly0.png"),
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/roly_poly1.png"),
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/roly_poly2.png"),
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/roly_poly_baby.png")
    };

    static final ResourceLocation[] models = {
            new ResourceLocation(TameableBeasts.MOD_ID, "geo/roly_poly.geo.json"),
            new ResourceLocation(TameableBeasts.MOD_ID, "geo/roly_poly_baby.geo.json")
    };

    static final ResourceLocation animations = new ResourceLocation(TameableBeasts.MOD_ID, "animations/roly_poly_anims.json");


    @Override
    public ResourceLocation getModelResource(RolyPolyEntity entity) {
        if(entity.isBaby())
            return models[1];

        return models[0];
    }

    @Override
    public ResourceLocation getTextureResource(RolyPolyEntity entity) {
        if(entity.isBaby())
            return  textures[3];

        return textures[entity.getTextureID()];
    }

    @Override
    public ResourceLocation getAnimationResource(RolyPolyEntity entity) {
        return animations;
    }
}