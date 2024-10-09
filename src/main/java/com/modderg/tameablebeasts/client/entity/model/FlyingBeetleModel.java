package com.modderg.tameablebeasts.client.entity.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.client.entity.TBGeoModel;
import com.modderg.tameablebeasts.server.entity.custom.FlyingBeetleEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;

public class FlyingBeetleModel extends TBGeoModel<FlyingBeetleEntity> {

        private final ResourceLocation[][] textures = {
                new ResourceLocation[]{
                        new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/tameable_beetle.png"),
                        new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/tameable_beetle2.png"),
                        new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/tameable_beetle3.png")

                },
                new ResourceLocation[]{
                        new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/tameable_beetle_baby.png")
                },
        };

        @Override
        public ResourceLocation getModelResource(FlyingBeetleEntity entity) {
        if(entity.isBaby()){
            return new ResourceLocation(TameableBeast.MOD_ID, "geo/tameable_beetle_baby.geo.json");
        }
        return new ResourceLocation(TameableBeast.MOD_ID, "geo/tameable_beetle.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FlyingBeetleEntity entity) {
       if(entity.isBaby()){
           return textures[1][0];
       }
        return textures[0][entity.getTextureID()];
    }

    @Override
    public ResourceLocation getAnimationResource(FlyingBeetleEntity entity) {
        if(entity.isBaby()){
            return new ResourceLocation(TameableBeast.MOD_ID, "animations/tameable_baby_beetle_anims.json");
        }
        return new ResourceLocation(TameableBeast.MOD_ID, "animations/tameable_beetle_anims.json");
    }

    @Override
    public void setCustomAnimations(FlyingBeetleEntity animatable, long instanceId, AnimationState<FlyingBeetleEntity> animationState) {
        setLookAngle(animatable, animationState);
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
