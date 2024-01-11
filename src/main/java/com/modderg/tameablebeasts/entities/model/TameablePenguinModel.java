package com.modderg.tameablebeasts.entities.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.entities.custom.FlyingBeetleEntity;
import com.modderg.tameablebeasts.entities.custom.PenguinEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class TameablePenguinModel extends GeoModel<PenguinEntity> {


    @Override
    public ResourceLocation getModelResource(PenguinEntity entity) {
        if(entity.isBaby()){
            return new ResourceLocation(TameableBeast.MOD_ID, "geo/tameable_baby_penguin.geo.json");
        }
        return new ResourceLocation(TameableBeast.MOD_ID, "geo/tameable_penguin.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PenguinEntity entity) {
       if(entity.isBaby()){
           return new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/tameable_baby_penguin"+entity.getTextureID()+".png");
       }
        return new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/tameable_penguin"+entity.getTextureID()+".png");
    }

    @Override
    public ResourceLocation getAnimationResource(PenguinEntity entity) {
        if(entity.isBaby()){
            return new ResourceLocation(TameableBeast.MOD_ID, "animations/tameable_baby_penguin.anims.json");
        }
        return new ResourceLocation(TameableBeast.MOD_ID, "animations/tameable_penguin.anims.json");
    }

    @Override
    public void setCustomAnimations(PenguinEntity animatable, long instanceId, AnimationState<PenguinEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");
        CoreGeoBone bone2 = getAnimationProcessor().getBone("entity");

        if (head != null && bone2 != null && !animatable.isBaby()) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD-bone2.getRotX());
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD-bone2.getRotY());
        }
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
