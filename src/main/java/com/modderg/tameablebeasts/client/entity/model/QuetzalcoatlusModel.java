package com.modderg.tameablebeasts.client.entity.model;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.client.entity.TBGeoModel;
import com.modderg.tameablebeasts.server.entity.QuetzalcoatlusEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class QuetzalcoatlusModel extends TBGeoModel<QuetzalcoatlusEntity> {

    static final ResourceLocation[] textures = {
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/quetzal0.png"),
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/quetzal1.png"),
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/quetzal2.png"),
    };

    static final ResourceLocation[] models = {
            new ResourceLocation(TameableBeasts.MOD_ID, "geo/quetzal.geo.json"),
            new ResourceLocation(TameableBeasts.MOD_ID, "geo/quetzal_baby.geo.json")
    };

    static final ResourceLocation animations = new ResourceLocation(TameableBeasts.MOD_ID, "animations/quetzal.animation.json");


    @Override
    public ResourceLocation getModelResource(QuetzalcoatlusEntity entity) {
        if(entity.isBaby())
            return models[1];
        return models[0];
    }

    @Override
    public ResourceLocation getTextureResource(QuetzalcoatlusEntity entity) {
        return textures[entity.getTextureID()];
    }

    @Override
    public ResourceLocation getAnimationResource(QuetzalcoatlusEntity entity) {
        return animations;
    }

}
