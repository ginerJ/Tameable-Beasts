package com.modderg.tameablebeasts.client.entity.model;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.client.entity.TBGeoModel;
import com.modderg.tameablebeasts.server.entity.FlyingBeetleEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ShinyBeetleModel extends TBGeoModel<FlyingBeetleEntity> {
    
    static final ResourceLocation[] textures = {
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/shiny_beetle0.png"),
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/shiny_beetle1.png"),
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/shiny_beetle2.png"),
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/shiny_beetle_baby.png")
    };

    static final ResourceLocation[] models = {
            new ResourceLocation(TameableBeasts.MOD_ID, "geo/shiny_beetle.geo.json"),
            new ResourceLocation(TameableBeasts.MOD_ID, "geo/shiny_beetle_baby.geo.json")
    };

    static final ResourceLocation[] animations = {
            new ResourceLocation(TameableBeasts.MOD_ID, "animations/shiny_beetle.animation.json"),
            new ResourceLocation(TameableBeasts.MOD_ID, "animations/shiny_beetle_baby.animation.json")
    };

    @Override
    public ResourceLocation getModelResource(FlyingBeetleEntity entity) {
        if(entity.isBaby())
            return models[1];
        return models[0];
    }

    @Override
    public ResourceLocation getTextureResource(FlyingBeetleEntity entity) {
        if(entity.isBaby())
            return  textures[3];

        return textures[entity.getTextureID()];
    }

    @Override
    public ResourceLocation getAnimationResource(FlyingBeetleEntity entity) {
        if(entity.isBaby())
            return  animations[1];

        return animations[0];
    }
}
