package com.modderg.tameablebeasts.entities.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.entities.custom.RolyPolyEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GiantTameableRolyPolyModel extends GeoModel<RolyPolyEntity> {
    @Override
    public ResourceLocation getModelResource(RolyPolyEntity entity) {
        if(entity.isBaby()){
            return new ResourceLocation(TameableBeast.MOD_ID, "geo/giant_roly_poly_baby.geo.json");
        }
        return new ResourceLocation(TameableBeast.MOD_ID, "geo/giant_roly_poly.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RolyPolyEntity entity) {
        if(entity.isBaby()){
            return new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/giant_roly_poly_baby.png");
        }
        if(entity.hasSaddle()){
            return new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/giant_roly_poly_saddle" + entity.getTextureID() + ".png");
        } else {
            return new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/giant_roly_poly" + entity.getTextureID() + ".png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(RolyPolyEntity entity) {
        return new ResourceLocation(TameableBeast.MOD_ID, "animations/giant_roly_poly_anims.json");
    }
}