package com.modderg.tameablebeasts.client.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.entities.GrasshopperEntity;
import com.modderg.tameablebeasts.entities.RacoonEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class GiantTameableGrasshopperModel extends GeoModel<GrasshopperEntity> {

    private final ResourceLocation[][] textures = {
            new ResourceLocation[] {
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/giant_grasshopper.png"),
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/giant_grasshopper2.png"),
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/giant_grasshopper3.png")
            },
            new ResourceLocation[] {
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/giant_grasshopper_saddled.png"),
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/giant_grasshopper2_saddled.png"),
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/giant_grasshopper3_saddled.png")
            }
    };

    @Override
    public ResourceLocation getModelResource(GrasshopperEntity entity) {
        if(entity.isBaby()){
            return new ResourceLocation(TameableBeast.MODID, "geo/giant_grasshopper_baby.geo.json");
        }
        return new ResourceLocation(TameableBeast.MODID, "geo/giant_grasshopper.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GrasshopperEntity entity) {
        if(entity.isBaby()){
           return new ResourceLocation(TameableBeast.MODID, "textures/entity/giant_grasshopper_baby.png");
        }
        if(entity.getSaddle()){
            return textures[1][entity.getTextureID()];
        }
        return textures[0][entity.getTextureID()];
    }

    @Override
    public ResourceLocation getAnimationResource(GrasshopperEntity entity) {
        return new ResourceLocation(TameableBeast.MODID, "animations/giant_grasshopper_anims.json");
    }

    @Override
    public void setCustomAnimations(GrasshopperEntity animatable, long instanceId, AnimationState<GrasshopperEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
