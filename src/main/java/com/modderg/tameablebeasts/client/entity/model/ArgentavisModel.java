package com.modderg.tameablebeasts.client.entity.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.client.entity.TBGeoModel;
import com.modderg.tameablebeasts.server.entity.custom.ArgentavisEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;

public class ArgentavisModel extends TBGeoModel<ArgentavisEntity> {

    @Override
    public ResourceLocation getModelResource(ArgentavisEntity entity) {
        return new ResourceLocation(TameableBeast.MOD_ID, "geo/"+(entity.isBaby()?"baby_":"")+"argentavis.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ArgentavisEntity entity) {
        return new ResourceLocation(TameableBeast.MOD_ID,"textures/entity/argentavis"+entity.getTextureID()+".png");
    }

    @Override
    public ResourceLocation getAnimationResource(ArgentavisEntity entity) {
        return new ResourceLocation(TameableBeast.MOD_ID, "animations/argentavis.anims.json");
    }

    @Override
    public void setCustomAnimations(ArgentavisEntity animatable, long instanceId, AnimationState<ArgentavisEntity> animationState) {
        setLookAngle(animatable, animationState);
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}