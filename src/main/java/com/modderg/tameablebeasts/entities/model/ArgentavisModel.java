package com.modderg.tameablebeasts.entities.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.entities.custom.ArgentavisEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ArgentavisModel extends GeoModel<ArgentavisEntity> {

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
}