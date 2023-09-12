package com.modderg.tameablebeasts.client.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.entities.GiantTameableRolyPolyEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GiantTameableRolyPolyModel extends GeoModel<GiantTameableRolyPolyEntity> {
    @Override
    public ResourceLocation getModelResource(GiantTameableRolyPolyEntity entity) {
        if(entity.isBaby()){
            return new ResourceLocation(TameableBeast.MODID, "geo/giant_roly_poly_baby.geo.json");
        }
        return new ResourceLocation(TameableBeast.MODID, "geo/giant_roly_poly.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GiantTameableRolyPolyEntity entity) {
        if(entity.isBaby()){
            return new ResourceLocation(TameableBeast.MODID, "textures/entity/giant_roly_poly_baby.png");
        }
        if(entity.getSaddle()){
            return new ResourceLocation(TameableBeast.MODID, "textures/entity/giant_roly_poly_saddle" + entity.getTextureID() + ".png");
        } else {
            return new ResourceLocation(TameableBeast.MODID, "textures/entity/giant_roly_poly" + entity.getTextureID() + ".png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(GiantTameableRolyPolyEntity entity) {
        return new ResourceLocation(TameableBeast.MODID, "animations/giant_roly_poly_anims.json");
    }
}