package com.modderg.tameablebeasts.client.entity.model;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.client.entity.TBGeoModel;
import com.modderg.tameablebeasts.server.entity.ChikoteEntity;
import com.modderg.tameablebeasts.server.entity.PenguinEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;

public class ChikoteModel extends TBGeoModel<ChikoteEntity> {

    static final ResourceLocation[] textures = {
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/chikote0.png"),
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/chikote1.png"),
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/chikote2.png"),
    };

    static final ResourceLocation[] models = {
            new ResourceLocation(TameableBeasts.MOD_ID, "geo/chikote.geo.json"),
            new ResourceLocation(TameableBeasts.MOD_ID, "geo/chikote_baby.geo.json")
    };

    static final ResourceLocation animations = new ResourceLocation(TameableBeasts.MOD_ID, "animations/chikote.animation.json");


    @Override
    public ResourceLocation getModelResource(ChikoteEntity entity) {
        if(entity.isBaby())
            return models[1];
        return models[0];
    }

    @Override
    public ResourceLocation getTextureResource(ChikoteEntity entity) {
        return textures[entity.getTextureID()];
    }

    @Override
    public ResourceLocation getAnimationResource(ChikoteEntity entity) {
        return animations;
    }
}
