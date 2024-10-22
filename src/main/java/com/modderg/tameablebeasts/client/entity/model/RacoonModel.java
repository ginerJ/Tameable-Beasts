package com.modderg.tameablebeasts.client.entity.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.server.entity.custom.RacoonEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class RacoonModel extends GeoModel<RacoonEntity> {

    private ResourceLocation[] textures = {
            new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/tameable_racoon.png"),
            new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/tameable_racoon2.png"),
            new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/tameable_racoon3.png")
    };

    private ResourceLocation[] bbytextures = {
            new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/tameable_baby_racoon.png"),
            new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/tameable_baby_racoon2.png"),
            new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/tameable_baby_racoon3.png")
    };

    @Override
    public ResourceLocation getModelResource(RacoonEntity entity) {
        if(entity.isBaby()){
            return new ResourceLocation(TameableBeast.MOD_ID, "geo/tameable_baby_racoon.geo.json");
        }
        return new ResourceLocation(TameableBeast.MOD_ID, "geo/tameable_racoon" + (entity.isBellyFull()?"_fat":"") + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RacoonEntity entity) {
        if(entity.isBaby()){
            return bbytextures[entity.getTextureID()];
        }
        return textures[entity.getTextureID()];
    }

    @Override
    public ResourceLocation getAnimationResource(RacoonEntity entity) {
        if(entity.isBaby()){
            return new ResourceLocation(TameableBeast.MOD_ID, "animations/tameable_baby_racoon.anims.json");
        }
        return new ResourceLocation(TameableBeast.MOD_ID, "animations/tameable_racoon.anims.json");
    }

    @Override
    public void setCustomAnimations(RacoonEntity animatable, long instanceId, AnimationState<RacoonEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");
        CoreGeoBone entity = getAnimationProcessor().getBone("entity");

        if (head != null && entity != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            float headPitch = (entityData.headPitch()) * Mth.DEG_TO_RAD;
            float headYaw = entityData.netHeadYaw() * Mth.DEG_TO_RAD;

            Quaternionf desiredHeadRotation = new Quaternionf().rotateXYZ(headPitch-entity.getRotX()*2, headYaw, 0);

            head.setRotX(desiredHeadRotation.x);
            head.setRotY(desiredHeadRotation.y);
            head.setRotZ(desiredHeadRotation.z);
        }

        super.setCustomAnimations(animatable, instanceId, animationState);
    }

}