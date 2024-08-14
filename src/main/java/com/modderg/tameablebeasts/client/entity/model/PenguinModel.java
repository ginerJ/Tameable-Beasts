package com.modderg.tameablebeasts.client.entity.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.client.entity.TBGeoModel;
import com.modderg.tameablebeasts.server.entity.custom.PenguinEntity;
import com.modderg.tameablebeasts.server.entity.custom.RacoonEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.shadowed.eliotlash.mclib.utils.MathHelper;
import software.bernie.shadowed.eliotlash.mclib.utils.MathUtils;

public class PenguinModel extends TBGeoModel<PenguinEntity> {


    @Override
    public ResourceLocation getModelResource(PenguinEntity entity) {
        if(entity.isBaby()){
            return new ResourceLocation(TameableBeast.MOD_ID, "geo/penguin_baby.geo.json");
        }
        return new ResourceLocation(TameableBeast.MOD_ID, "geo/penguin.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PenguinEntity entity) {
       if(entity.isBaby()){
           return new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/penguin4.png");
       }
        return new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/penguin"+entity.getTextureID()+".png");
    }

    @Override
    public ResourceLocation getAnimationResource(PenguinEntity entity) {
        return new ResourceLocation(TameableBeast.MOD_ID, "animations/penguin.anims.json");
    }

    @Override
    public void setCustomAnimations(PenguinEntity animatable, long instanceId, AnimationState<PenguinEntity> animationState) {
        setLookAngeRots(animatable, animationState);
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
