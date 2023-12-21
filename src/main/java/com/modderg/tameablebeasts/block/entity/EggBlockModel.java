package com.modderg.tameablebeasts.block.entity;

import com.modderg.tameablebeasts.TameableBeast;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class EggBlockModel extends GeoModel<EggBlockEntity> {

    @Override
    public ResourceLocation getModelResource(EggBlockEntity entity) {
        return new ResourceLocation(TameableBeast.MOD_ID, "geo/"+entity.getCleanSpecies().toLowerCase()+"_egg.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EggBlockEntity entity) {
        return new ResourceLocation(TameableBeast.MOD_ID,"textures/block/"+entity.getCleanSpecies().toLowerCase()+"_egg.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EggBlockEntity animatable) {
        return null;
    }
}
