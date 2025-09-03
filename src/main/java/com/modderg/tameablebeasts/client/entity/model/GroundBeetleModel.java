package com.modderg.tameablebeasts.client.entity.model;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.client.entity.TBGeoModel;
import com.modderg.tameablebeasts.server.entity.GroundBeetleEntity;
import net.minecraft.resources.ResourceLocation;

public class GroundBeetleModel extends TBGeoModel<GroundBeetleEntity> {

    static final ResourceLocation[][] textures = {
        {
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/jaw_beetle0.png"),
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/jaw_beetle1.png"),
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/jaw_beetle2.png"),
        },
        {
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/rhyno_beetle0.png"),
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/rhyno_beetle1.png"),
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/rhyno_beetle2.png"),
        },
        {
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/jaw_metal_beetle0.png"),
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/jaw_metal_beetle1.png"),
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/jaw_metal_beetle2.png"),
        },
        {
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/rhyno_metal_beetle0.png"),
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/rhyno_metal_beetle1.png"),
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/rhyno_metal_beetle2.png"),
        }
    };

    static final ResourceLocation bb_texture = new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/shiny_beetle_baby.png");


    static final ResourceLocation[] models = {
            new ResourceLocation(TameableBeasts.MOD_ID, "geo/ground_beetle.geo.json"),
            new ResourceLocation(TameableBeasts.MOD_ID, "geo/shiny_beetle_baby.geo.json"),
            new ResourceLocation(TameableBeasts.MOD_ID, "geo/metal_beetle.geo.json")
    };

    static final ResourceLocation[] animations = {
            new ResourceLocation(TameableBeasts.MOD_ID, "animations/ground_beetle.animation.json"),
            new ResourceLocation(TameableBeasts.MOD_ID, "animations/shiny_beetle_baby.animation.json"),
            new ResourceLocation(TameableBeasts.MOD_ID, "animations/metal_beetle.animation.json")
    };

    @Override
    public ResourceLocation getModelResource(GroundBeetleEntity entity) {
        if(entity.isBaby())
            return models[1];

        if(entity.isMetallic())
            return models[2];

        return models[0];
    }

    @Override
    public ResourceLocation getTextureResource(GroundBeetleEntity entity) {
        if(entity.isBaby())
            return  bb_texture;

        return textures[entity.getBeetleID() + (entity.isMetallic() ? 2:0)][entity.getTextureID()];
    }

    @Override
    public ResourceLocation getAnimationResource(GroundBeetleEntity entity) {
        if(entity.isBaby())
            return  animations[1];

        if(entity.isMetallic())
            return animations[2];

        return animations[0];
    }
}
