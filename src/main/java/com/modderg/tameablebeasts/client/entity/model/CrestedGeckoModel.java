package com.modderg.tameablebeasts.client.entity.model;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.client.entity.TBGeoModel;
import com.modderg.tameablebeasts.server.entity.CrestedGeckoEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;

public class CrestedGeckoModel extends TBGeoModel<CrestedGeckoEntity> {

    @Override
    public ResourceLocation getModelResource(CrestedGeckoEntity entity) {
        return new ResourceLocation(TameableBeasts.MOD_ID, "geo/crested_gecko"+(entity.isBaby()?"_baby":"") + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CrestedGeckoEntity entity) {
        return new ResourceLocation(TameableBeasts.MOD_ID,"textures/entity/crested_gecko"+entity.getTextureID()+".png");
    }

    @Override
    public ResourceLocation getAnimationResource(CrestedGeckoEntity entity) {
        return new ResourceLocation(TameableBeasts.MOD_ID, "animations/crested_gecko.anims.json");
    }

    @Override
    public void setCustomAnimations(CrestedGeckoEntity animatable, long instanceId, AnimationState<CrestedGeckoEntity> animationState) {
        CoreGeoBone climb = getAnimationProcessor().getBone("climb");

        if(climb != null){
            if(animatable.onClimbable())
                climb.setRotX((float) (Math.PI/2));
            else climb.setRotX(0);
        }

        setLookAngle(animatable, animationState);

        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}