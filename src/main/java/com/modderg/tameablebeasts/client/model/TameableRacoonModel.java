package com.modderg.tameablebeasts.client.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.entities.PenguinEntity;
import com.modderg.tameablebeasts.entities.RacoonEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class TameableRacoonModel extends GeoModel<RacoonEntity> {

    private ResourceLocation[] textures = {
            new ResourceLocation(TameableBeast.MODID, "textures/entity/tameable_racoon.png"),
            new ResourceLocation(TameableBeast.MODID, "textures/entity/tameable_racoon2.png"),
            new ResourceLocation(TameableBeast.MODID, "textures/entity/tameable_racoon3.png")
    };

    private ResourceLocation[] bbytextures = {
            new ResourceLocation(TameableBeast.MODID, "textures/entity/tameable_baby_racoon.png"),
            new ResourceLocation(TameableBeast.MODID, "textures/entity/tameable_baby_racoon2.png"),
            new ResourceLocation(TameableBeast.MODID, "textures/entity/tameable_baby_racoon3.png")
    };

    @Override
    public ResourceLocation getModelResource(RacoonEntity entity) {
        if(entity.isBaby()){
            return new ResourceLocation(TameableBeast.MODID, "geo/tameable_baby_racoon.geo.json");
        }
        return new ResourceLocation(TameableBeast.MODID, "geo/tameable_racoon" + (entity.hasPolen()?"_fat":"") + ".geo.json");
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
            return new ResourceLocation(TameableBeast.MODID, "animations/tameable_baby_racoon.anims.json");
        }
        return new ResourceLocation(TameableBeast.MODID, "animations/tameable_racoon.anims.json");
    }

    @Override
    public void setCustomAnimations(RacoonEntity animatable, long instanceId, AnimationState<RacoonEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null && !animationState.isCurrentAnimationStage("sit")) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}