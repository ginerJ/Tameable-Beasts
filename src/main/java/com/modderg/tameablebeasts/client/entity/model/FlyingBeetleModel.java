package com.modderg.tameablebeasts.client.entity.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.client.entity.TBGeoModel;
import com.modderg.tameablebeasts.server.entity.custom.FlyingBeetleEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;

public class FlyingBeetleModel extends TBGeoModel<FlyingBeetleEntity> {


        @Override
        public ResourceLocation getModelResource(FlyingBeetleEntity entity) {
        if(entity.isBaby()){
            return new ResourceLocation(TameableBeast.MOD_ID, "geo/tameable_beetle_baby.geo.json");
        }
        return new ResourceLocation(TameableBeast.MOD_ID, "geo/tameable_beetle.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FlyingBeetleEntity entity) {
       if(entity.isBaby())
           return  new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/tameable_beetle_baby.png");

        return new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/tameable_beetle" + entity.getTextureID() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(FlyingBeetleEntity entity) {
        if(entity.isBaby())
            return new ResourceLocation(TameableBeast.MOD_ID, "animations/tameable_baby_beetle_anims.json");

        return new ResourceLocation(TameableBeast.MOD_ID, "animations/tameable_beetle_anims.json");
    }

    @Override
    public void setCustomAnimations(FlyingBeetleEntity animatable, long instanceId, AnimationState<FlyingBeetleEntity> animationState) {
        setLookAngle(animatable, animationState);
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
