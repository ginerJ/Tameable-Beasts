package com.modderg.tameablebeasts.client.entity.model;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.server.entity.RolyPolyEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class RolyPolyModel extends GeoModel<RolyPolyEntity> {

    @Override
    public ResourceLocation getModelResource(RolyPolyEntity entity) {

        return new ResourceLocation(TameableBeasts.MOD_ID, "geo/roly_poly" + (entity.isBaby() ? "_baby" : "") + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RolyPolyEntity entity) {
        if(entity.isBaby())
            return new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/roly_poly_baby.png");

        return new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/roly_poly" + entity.getTextureID() + ".png");

    }

    @Override
    public ResourceLocation getAnimationResource(RolyPolyEntity entity) {
        return new ResourceLocation(TameableBeasts.MOD_ID, "animations/roly_poly_anims.json");
    }
}