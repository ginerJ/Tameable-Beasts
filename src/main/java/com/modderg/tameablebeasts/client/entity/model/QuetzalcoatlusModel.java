package com.modderg.tameablebeasts.client.entity.model;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.client.entity.TBGeoModel;
import com.modderg.tameablebeasts.server.entity.QuetzalcoatlusEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;

public class QuetzalcoatlusModel extends TBGeoModel<QuetzalcoatlusEntity> {

    @Override
    public ResourceLocation getModelResource(QuetzalcoatlusEntity entity) {
        if(entity.isBaby()){
            return new ResourceLocation(TameableBeasts.MOD_ID, "geo/quetzal_baby.geo.json");
        }
        return new ResourceLocation(TameableBeasts.MOD_ID, "geo/quetzal.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(QuetzalcoatlusEntity entity) {
        return new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/quetzal" + entity.getTextureID() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(QuetzalcoatlusEntity entity) {
        return new ResourceLocation(TameableBeasts.MOD_ID, "animations/quetzal.animation.json");
    }

    @Override
    public void setCustomAnimations(QuetzalcoatlusEntity animatable, long instanceId, AnimationState<QuetzalcoatlusEntity> animationState) {
        setLookAngle(animatable, animationState);
        super.setCustomAnimations(animatable, instanceId, animationState);
    }

}
