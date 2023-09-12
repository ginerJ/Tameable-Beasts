package com.modderg.tameablebeasts.client.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.entities.GroundBeetleEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TameableGroundBeetleModel extends GeoModel<GroundBeetleEntity> {

    private final ResourceLocation[][] textures = {
            new ResourceLocation[]{
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/ground_beetle.png"),
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/ground_beetle2.png")
            },
            new ResourceLocation[]{
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/tameable_beetle_baby.png")
            },
    };

    @Override
    public ResourceLocation getModelResource(GroundBeetleEntity entity) {
        if(entity.isBaby()){
            return new ResourceLocation(TameableBeast.MODID, "geo/tameable_beetle_baby.geo.json");
        }
        return new ResourceLocation(TameableBeast.MODID, "geo/ground_beetle.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GroundBeetleEntity entity) {
        if(entity.isBaby()){
            return textures[1][0];
        }
        return textures[0][entity.getTextureID()];
    }

    @Override
    public ResourceLocation getAnimationResource(GroundBeetleEntity entity) {
        if(entity.isBaby()){
            return new ResourceLocation(TameableBeast.MODID, "animations/tameable_baby_beetle_anims.json");
        }
        return new ResourceLocation(TameableBeast.MODID, "animations/ground_beetle_anims.json");
    }
}
