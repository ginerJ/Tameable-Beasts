package com.modderg.tameablebeasts.client.entity.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.client.entity.TBGeoModel;
import com.modderg.tameablebeasts.server.entity.custom.QuetzalcoatlusEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaterniond;
import org.joml.Quaternionf;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class QuetzalcoatlusModel extends TBGeoModel<QuetzalcoatlusEntity> {

    @Override
    public ResourceLocation getModelResource(QuetzalcoatlusEntity entity) {
        if(entity.isBaby()){
            return new ResourceLocation(TameableBeast.MOD_ID, "geo/quetzal_baby.geo.json");
        }
        return new ResourceLocation(TameableBeast.MOD_ID, "geo/quetzal.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(QuetzalcoatlusEntity entity) {
        return new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/quetzal" + entity.getTextureID() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(QuetzalcoatlusEntity entity) {
        return new ResourceLocation(TameableBeast.MOD_ID, "animations/quetzal_anims.json");
    }

    @Override
    public void setCustomAnimations(QuetzalcoatlusEntity animatable, long instanceId, AnimationState<QuetzalcoatlusEntity> animationState) {
        setHeadAndBodyRots(animatable, animationState);
        super.setCustomAnimations(animatable, instanceId, animationState);
    }

}
