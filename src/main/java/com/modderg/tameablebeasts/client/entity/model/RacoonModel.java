package com.modderg.tameablebeasts.client.entity.model;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.server.entity.RacoonEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class RacoonModel extends GeoModel<RacoonEntity> {

    static final ResourceLocation[] textures = {
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/racoon0.png"),
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/racoon1.png"),
            new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/racoon2.png")
    };

    static final ResourceLocation[] models = {
            new ResourceLocation(TameableBeasts.MOD_ID, "geo/racoon.geo.json"),
            new ResourceLocation(TameableBeasts.MOD_ID, "geo/racoon_baby.geo.json"),
            new ResourceLocation(TameableBeasts.MOD_ID, "geo/racoon_fat.geo.json")
    };

    static final ResourceLocation animations = new ResourceLocation(TameableBeasts.MOD_ID, "animations/tameable_racoon.anims.json");

    @Override
    public ResourceLocation getModelResource(RacoonEntity entity) {
        if (entity.isBaby())
            return models[1];

        if (entity.isBellyFull())
            return models[2];

        return models[0];
    }

    @Override
    public ResourceLocation getTextureResource(RacoonEntity entity) {
        return textures[entity.getTextureID()];
    }

    @Override
    public ResourceLocation getAnimationResource(RacoonEntity entity) {
        return animations;
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