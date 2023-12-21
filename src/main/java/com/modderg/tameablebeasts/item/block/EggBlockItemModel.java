package com.modderg.tameablebeasts.item.block;

import com.modderg.tameablebeasts.TameableBeast;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class EggBlockItemModel extends GeoModel<EggBlockItem> {
    @Override
    public ResourceLocation getModelResource(EggBlockItem entity) {
        return new ResourceLocation(TameableBeast.MOD_ID, "geo/"+entity.getSpecies().toLowerCase()+"_egg.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EggBlockItem entity) {
        return new ResourceLocation(TameableBeast.MOD_ID,"textures/block/"+entity.getSpecies().toLowerCase()+"_egg.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EggBlockItem animatable) {
        return null;
    }
}
