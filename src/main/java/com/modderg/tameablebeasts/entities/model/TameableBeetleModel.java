package com.modderg.tameablebeasts.entities.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.entities.custom.FlyingBeetleEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class TameableBeetleModel extends GeoModel<FlyingBeetleEntity> {

    private final ResourceLocation[][] textures = {
            new ResourceLocation[]{
                    new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/tameable_beetle.png"),
                    new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/tameable_beetle2.png"),
                    new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/tameable_beetle3.png")

            },
            new ResourceLocation[]{
                    new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/tameable_beetle_baby.png")
            },
    };

    @Override
    public ResourceLocation getModelResource(FlyingBeetleEntity entity) {
        if(entity.isBaby()){
            return new ResourceLocation(TameableBeast.MOD_ID, "geo/tameable_beetle_baby.geo.json");
        }
        return new ResourceLocation(TameableBeast.MOD_ID, "geo/tameable_beetle.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FlyingBeetleEntity entity) {
       if(entity.isBaby()){
           return textures[1][0];
       }
        return textures[0][entity.getTextureID()];
    }

    @Override
    public ResourceLocation getAnimationResource(FlyingBeetleEntity entity) {
        if(entity.isBaby()){
            return new ResourceLocation(TameableBeast.MOD_ID, "animations/tameable_baby_beetle_anims.json");
        }
        return new ResourceLocation(TameableBeast.MOD_ID, "animations/tameable_beetle_anims.json");
    }

    @Override
    public void setCustomAnimations(FlyingBeetleEntity animatable, long instanceId, AnimationState<FlyingBeetleEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");
        CoreGeoBone bone2 = getAnimationProcessor().getBone("bone2");

        if (head != null && bone2 != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD-bone2.getRotX());
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD-bone2.getRotY());
        }
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
