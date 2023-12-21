package com.modderg.tameablebeasts.entities.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.entities.custom.QuetzalcoatlusEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class QuetzalcoatlusModel extends GeoModel<QuetzalcoatlusEntity> {

    @Override
    public ResourceLocation getModelResource(QuetzalcoatlusEntity entity) {
        if(entity.isBaby()){
            return new ResourceLocation(TameableBeast.MOD_ID, "geo/quetzal_baby.geo.json");
        }
        return new ResourceLocation(TameableBeast.MOD_ID, "geo/quetzal.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(QuetzalcoatlusEntity entity) {
        if(entity.isBaby()){
            return new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/quetzal_baby" + entity.getTextureID() + ".png");
        }
        return new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/quetzal" + entity.getTextureID() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(QuetzalcoatlusEntity entity) {
        return new ResourceLocation(TameableBeast.MOD_ID, "animations/quetzal_anims.json");
    }
}
