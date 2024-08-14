package com.modderg.tameablebeasts.client.entity.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.client.entity.TBGeoModel;
import com.modderg.tameablebeasts.server.entity.custom.GrasshopperEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class GrasshopperModel extends TBGeoModel<GrasshopperEntity> {

    @Override
    public ResourceLocation getModelResource(GrasshopperEntity entity) {
        if(entity.isBaby())
            return new ResourceLocation(TameableBeast.MOD_ID, "geo/giant_grasshopper_baby.geo.json");
        return new ResourceLocation(TameableBeast.MOD_ID, "geo/giant_grasshopper.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GrasshopperEntity entity) {
        if(entity.hasSaddle())
            return new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/giant_grasshopper"+ entity.getTextureID() +"_saddled.png");
        return new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/giant_grasshopper"+ entity.getTextureID() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(GrasshopperEntity entity) {
        return new ResourceLocation(TameableBeast.MOD_ID, "animations/giant_grasshopper_anims.json");
    }

    @Override
    public void setCustomAnimations(GrasshopperEntity animatable, long instanceId, AnimationState<GrasshopperEntity> animationState) {
        setLookAngeRots(animatable, animationState);
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
