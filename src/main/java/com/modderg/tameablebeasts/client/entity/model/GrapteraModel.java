package com.modderg.tameablebeasts.client.entity.model;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.client.entity.TBGeoModel;
import com.modderg.tameablebeasts.server.entity.GrapteranodonEntity;
import com.modderg.tameablebeasts.server.entity.QuetzalcoatlusEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;

public class GrapteraModel extends TBGeoModel<GrapteranodonEntity> {

    static final ResourceLocation[] textures = {
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/grapteranodon0.png"),
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/grapteranodon1.png"),
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/grapteranodon2.png"),
    };

    static final ResourceLocation[] models = {
            new ResourceLocation(TameableBeasts.MOD_ID, "geo/grapteranodon.geo.json"),
            new ResourceLocation(TameableBeasts.MOD_ID, "geo/grapteranodon_baby.geo.json")
    };

    static final ResourceLocation animations = new ResourceLocation(TameableBeasts.MOD_ID, "animations/graptera.animation.json");

    @Override
    public ResourceLocation getModelResource(GrapteranodonEntity entity) {
        if(entity.isBaby())
            return models[1];
        return models[0];
    }

    @Override
    public ResourceLocation getTextureResource(GrapteranodonEntity entity) {
        return textures[entity.getTextureID()];
    }

    @Override
    public ResourceLocation getAnimationResource(GrapteranodonEntity entity) {
        return animations;
    }

}
