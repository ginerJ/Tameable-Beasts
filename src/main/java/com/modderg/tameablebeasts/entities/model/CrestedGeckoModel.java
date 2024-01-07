package com.modderg.tameablebeasts.entities.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.entities.custom.CrestedGeckoEntity;
import com.modderg.tameablebeasts.entities.custom.FlyingBeetleEntity;
import com.modderg.tameablebeasts.entities.custom.FurGolemEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class CrestedGeckoModel extends GeoModel<CrestedGeckoEntity> {

    @Override
    public ResourceLocation getModelResource(CrestedGeckoEntity entity) {
        return new ResourceLocation(TameableBeast.MOD_ID, "geo/crested_gecko"+(entity.isBaby()?"_baby":"") + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CrestedGeckoEntity entity) {
        return new ResourceLocation(TameableBeast.MOD_ID,"textures/entity/crested_gecko"+entity.getTextureID()+".png");
    }

    @Override
    public ResourceLocation getAnimationResource(CrestedGeckoEntity entity) {
        return new ResourceLocation(TameableBeast.MOD_ID, "animations/crested_gecko.anims.json");
    }

    @Override
    public void setCustomAnimations(CrestedGeckoEntity animatable, long instanceId, AnimationState<CrestedGeckoEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");
        CoreGeoBone body = getAnimationProcessor().getBone("body");
        CoreGeoBone climb = getAnimationProcessor().getBone("climb");

        EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        if(climb != null){
            if(animatable.isClimbing())
                climb.setRotX((float) (Math.PI/2));
            else climb.setRotX(0);
        }

        if (head != null && body != null && !animatable.isClimbing()) {

            head.setRotX(-(entityData.headPitch() * Mth.DEG_TO_RAD+body.getRotX()));
            head.setRotY(-(-(entityData.netHeadYaw() * Mth.DEG_TO_RAD-body.getRotY()) + (float) Math.PI));
        }
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}