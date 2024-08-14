package com.modderg.tameablebeasts.client.entity.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.client.entity.TBGeoModel;
import com.modderg.tameablebeasts.server.entity.custom.ArgentavisEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class ArgentavisModel extends TBGeoModel<ArgentavisEntity> {

    @Override
    public ResourceLocation getModelResource(ArgentavisEntity entity) {
        return new ResourceLocation(TameableBeast.MOD_ID, "geo/"+(entity.isBaby()?"baby_":"")+"argentavis.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ArgentavisEntity entity) {
        return new ResourceLocation(TameableBeast.MOD_ID,"textures/entity/argentavis"+entity.getTextureID()+".png");
    }

    @Override
    public ResourceLocation getAnimationResource(ArgentavisEntity entity) {
        return new ResourceLocation(TameableBeast.MOD_ID, "animations/argentavis.anims.json");
    }

    @Override
    public void setCustomAnimations(ArgentavisEntity animatable, long instanceId, AnimationState<ArgentavisEntity> animationState) {
        setHeadAndBodyRots(animatable, animationState);
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}