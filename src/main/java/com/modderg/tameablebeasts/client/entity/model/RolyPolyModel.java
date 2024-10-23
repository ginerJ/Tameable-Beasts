package com.modderg.tameablebeasts.client.entity.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.server.entity.custom.RolyPolyEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class RolyPolyModel extends GeoModel<RolyPolyEntity> {

    @Override
    public ResourceLocation getModelResource(RolyPolyEntity entity) {

        return new ResourceLocation(TameableBeast.MOD_ID, "geo/roly_poly" + (entity.isBaby() ? "_baby" : "") + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RolyPolyEntity entity) {
        if(entity.isBaby())
            return new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/roly_poly_baby.png");

        return new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/roly_poly" + entity.getTextureID() + ".png");

    }

    @Override
    public ResourceLocation getAnimationResource(RolyPolyEntity entity) {
        return new ResourceLocation(TameableBeast.MOD_ID, "animations/roly_poly_anims.json");
    }
}