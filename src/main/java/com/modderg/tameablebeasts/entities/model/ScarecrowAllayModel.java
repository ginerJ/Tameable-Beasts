package com.modderg.tameablebeasts.entities.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.entities.custom.ScarecrowAllayEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class ScarecrowAllayModel extends GeoModel<ScarecrowAllayEntity> {

    private final ResourceLocation[][] textures = {
            new ResourceLocation[] {
                    new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/scarecrow_allay.png"),
                    new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/scarecrow_allay2.png"),
                    new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/scarecrow_allay3.png")
            },
            new ResourceLocation[] {
                    new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/scarecrow_allay_hoe.png"),
                    new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/scarecrow_allay_hoe2.png"),
                    new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/scarecrow_allay_hoe3.png")
            }
    };

    @Override
    public ResourceLocation getModelResource(ScarecrowAllayEntity entity) {
        return new ResourceLocation(TameableBeast.MOD_ID, "geo/scarecrow_allay.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ScarecrowAllayEntity entity) {
        if(entity.getHoe()){
            return textures[1][entity.getTextureID()];
        }
        return textures[0][entity.getTextureID()];
    }

    @Override
    public ResourceLocation getAnimationResource(ScarecrowAllayEntity entity) {
        return new ResourceLocation(TameableBeast.MOD_ID, "animations/scarecrow_allay_anims.json");
    }

    @Override
    public void setCustomAnimations(ScarecrowAllayEntity animatable, long instanceId, AnimationState<ScarecrowAllayEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
