package com.modderg.tameablebeasts.client.entity.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.client.entity.TBGeoModel;
import com.modderg.tameablebeasts.server.entity.custom.ScarecrowAllayEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class ScarecrowAllayModel extends TBGeoModel<ScarecrowAllayEntity> {

    @Override
    public ResourceLocation getModelResource(ScarecrowAllayEntity entity) {
        return new ResourceLocation(TameableBeast.MOD_ID, "geo/scarecrow_allay.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ScarecrowAllayEntity entity) {
        return new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/scarecrow_allay"+ entity.getTextureID() +".png");
    }

    @Override
    public ResourceLocation getAnimationResource(ScarecrowAllayEntity entity) {
        return new ResourceLocation(TameableBeast.MOD_ID, "animations/scarecrow_allay_anims.json");
    }

    @Override
    public void setCustomAnimations(ScarecrowAllayEntity animatable, long instanceId, AnimationState<ScarecrowAllayEntity> animationState) {
        setLookAngle(animatable, animationState);
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
