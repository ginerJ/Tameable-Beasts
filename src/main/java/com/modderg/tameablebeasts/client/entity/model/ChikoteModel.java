package com.modderg.tameablebeasts.client.entity.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.client.entity.TBGeoModel;
import com.modderg.tameablebeasts.server.entity.custom.ChikoteEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;

public class ChikoteModel extends TBGeoModel<ChikoteEntity> {

    @Override
    public ResourceLocation getModelResource(ChikoteEntity entity) {
        if(!entity.isBaby())
            return new ResourceLocation(TameableBeast.MOD_ID, "geo/chikote.geo.json");
        return new ResourceLocation(TameableBeast.MOD_ID, "geo/chikote_baby.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ChikoteEntity entity) {
        return new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/chikote"+ entity.getTextureID() +".png");
    }

    @Override
    public ResourceLocation getAnimationResource(ChikoteEntity entity) {
        if(entity.isBaby())
            return new ResourceLocation(TameableBeast.MOD_ID, "animations/chikote_baby_anims.json");
        return new ResourceLocation(TameableBeast.MOD_ID, "animations/chikote_anims.json");
    }

    @Override
    public void setCustomAnimations(ChikoteEntity animatable, long instanceId, AnimationState<ChikoteEntity> animationState) {
        setLookAngle(animatable, animationState);
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
