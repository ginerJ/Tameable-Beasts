package com.modderg.tameablebeasts.client.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.entities.TeethEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TameableTeethModel extends GeoModel<TeethEntity> {
    @Override
    public ResourceLocation getModelResource(TeethEntity entity) {
        return new ResourceLocation(TameableBeast.MODID, "geo/teeth.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TeethEntity entity) {
        return new ResourceLocation(TameableBeast.MODID, "textures/entity/teeth.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TeethEntity entity) {
        return new ResourceLocation(TameableBeast.MODID, "animations/teeth_anims.json");
    }
}
