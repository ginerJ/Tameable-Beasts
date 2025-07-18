package com.modderg.tameablebeasts.client.entity.model;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.client.entity.TBGeoModel;
import com.modderg.tameablebeasts.server.entity.PenguinEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;

public class PenguinModel extends TBGeoModel<PenguinEntity> {


    @Override
    public ResourceLocation getModelResource(PenguinEntity entity) {
        if(entity.isBaby()){
            return new ResourceLocation(TameableBeasts.MOD_ID, "geo/penguin_baby.geo.json");
        }
        return new ResourceLocation(TameableBeasts.MOD_ID, "geo/penguin.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PenguinEntity entity) {
       if(entity.isBaby()){
           return new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/penguin4.png");
       }
        return new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/penguin"+entity.getTextureID()+".png");
    }

    @Override
    public ResourceLocation getAnimationResource(PenguinEntity entity) {
        return new ResourceLocation(TameableBeasts.MOD_ID, "animations/penguin.anims.json");
    }

    @Override
    public void setCustomAnimations(PenguinEntity animatable, long instanceId, AnimationState<PenguinEntity> animationState) {
        setLookAngle(animatable, animationState);
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
