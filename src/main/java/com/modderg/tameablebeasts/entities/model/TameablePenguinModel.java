package com.modderg.tameablebeasts.entities.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.entities.custom.PenguinEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class TameablePenguinModel extends GeoModel<PenguinEntity> {

    private ResourceLocation[][] textures = {
            new ResourceLocation[]{
                    new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/tameable_penguin.png"),
                    new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/tameable_penguin2.png"),
                    new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/tameable_penguin3.png")
            },
            new ResourceLocation[]{
                    new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/tameable_baby_penguin.png"),
                    new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/tameable_baby_penguin2.png"),
                    new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/tameable_baby_penguin3.png")
            },
            new ResourceLocation[]{
                    new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/tameable_penguin_sword.png"),
                    new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/tameable_penguin2_sword.png"),
                    new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/tameable_penguin3_sword.png")
            },
            new ResourceLocation[]{
                    new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/armored_tameable_penguin_sword.png"),
                    new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/armored_tameable_penguin2_sword.png"),
                    new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/armored_tameable_penguin3_sword.png")
            },
            new ResourceLocation[]{
                    new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/armored_tameable_penguin.png"),
                    new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/armored_tameable_penguin2.png"),
                    new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/armored_tameable_penguin3.png")
            }
    };

    @Override
    public ResourceLocation getModelResource(PenguinEntity entity) {
        if(entity.isBaby()){return new ResourceLocation(TameableBeast.MOD_ID, "geo/tameable_baby_penguin.geo.json");}
        return new ResourceLocation(TameableBeast.MOD_ID, "geo/tameable_penguin.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PenguinEntity entity) {
         if (entity.getSword()){
             if(entity.getHelmet()){
                 return textures[3][entity.getTextureID()];
             }
             return textures[2][entity.getTextureID()];
         } else if (entity.getHelmet()){
             return textures[4][entity.getTextureID()];
         } else if(entity.isBaby()){return textures[1][entity.getTextureID()];}
        return textures[0][entity.getTextureID()];
    }

    @Override
    public ResourceLocation getAnimationResource(PenguinEntity entity) {
        if(entity.isBaby()){return new ResourceLocation(TameableBeast.MOD_ID, "animations/tameable_baby_penguin.anims.json");}
        return new ResourceLocation(TameableBeast.MOD_ID, "animations/tameable_penguin.anims.json");
    }

    @Override
    public void setCustomAnimations(PenguinEntity animatable, long instanceId, AnimationState<PenguinEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
