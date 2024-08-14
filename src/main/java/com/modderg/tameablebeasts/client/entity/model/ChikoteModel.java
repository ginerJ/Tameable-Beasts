package com.modderg.tameablebeasts.client.entity.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.client.entity.TBGeoModel;
import com.modderg.tameablebeasts.server.entity.custom.ChikoteEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

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
        setLookAngeRots(animatable, animationState);
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
