package com.modderg.tameablebeasts.client.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.entities.TameableTeethEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TameableTeethModel extends GeoModel<TameableTeethEntity> {
    @Override
    public ResourceLocation getModelResource(TameableTeethEntity entity) {
        return new ResourceLocation(TameableBeast.MODID, "geo/teeth.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TameableTeethEntity entity) {
        return new ResourceLocation(TameableBeast.MODID, "textures/entity/teeth.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TameableTeethEntity entity) {
        return new ResourceLocation(TameableBeast.MODID, "animations/teeth_anims.json");
    }
}
