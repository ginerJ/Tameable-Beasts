package com.modderg.tameablebeasts.client.entity.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.client.entity.TBGeoModel;
import com.modderg.tameablebeasts.server.entity.custom.GrapteranodonEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;

public class GrapteraModel extends TBGeoModel<GrapteranodonEntity> {

    @Override
    public ResourceLocation getModelResource(GrapteranodonEntity entity) {
        if(entity.isBaby())
            return new ResourceLocation(TameableBeast.MOD_ID, "geo/grapteranodon_baby.geo.json");

        return new ResourceLocation(TameableBeast.MOD_ID, "geo/grapteranodon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GrapteranodonEntity entity) {
        return new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/grapteranodon" + entity.getTextureID() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(GrapteranodonEntity entity) {
        return new ResourceLocation(TameableBeast.MOD_ID, "animations/graptera_anims.json");
    }

    @Override
    public void setCustomAnimations(GrapteranodonEntity animatable, long instanceId, AnimationState<GrapteranodonEntity> animationState) {
        setLookAngle(animatable, animationState);
        super.setCustomAnimations(animatable, instanceId, animationState);
    }

}
