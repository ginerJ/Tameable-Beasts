package com.modderg.tameablebeasts.client.entity.model;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.client.entity.TBGeoModel;
import com.modderg.tameablebeasts.server.entity.ScarecrowAllayEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;

public class ScarecrowAllayModel extends TBGeoModel<ScarecrowAllayEntity> {

    @Override
    public ResourceLocation getModelResource(ScarecrowAllayEntity entity) {
        return new ResourceLocation(TameableBeasts.MOD_ID, "geo/scarecrow_allay.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ScarecrowAllayEntity entity) {
        return new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/scarecrow_allay"+ entity.getTextureID() +".png");
    }

    @Override
    public ResourceLocation getAnimationResource(ScarecrowAllayEntity entity) {
        return new ResourceLocation(TameableBeasts.MOD_ID, "animations/scarecrow_allay.animation.json");
    }

    @Override
    public void setCustomAnimations(ScarecrowAllayEntity animatable, long instanceId, AnimationState<ScarecrowAllayEntity> animationState) {
        setLookAngle(animatable, animationState);
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
