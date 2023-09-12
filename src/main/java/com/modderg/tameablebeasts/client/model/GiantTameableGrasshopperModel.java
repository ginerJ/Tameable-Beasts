package com.modderg.tameablebeasts.client.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.entities.GrasshopperEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GiantTameableGrasshopperModel extends GeoModel<GrasshopperEntity> {

    private final ResourceLocation[][] textures = {
            new ResourceLocation[] {
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/giant_grasshopper.png"),
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/giant_grasshopper2.png"),
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/giant_grasshopper3.png")
            },
            new ResourceLocation[] {
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/giant_grasshopper_saddled.png"),
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/giant_grasshopper2_saddled.png"),
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/giant_grasshopper3_saddled.png")
            }
    };

    @Override
    public ResourceLocation getModelResource(GrasshopperEntity entity) {
        if(entity.isBaby()){
            return new ResourceLocation(TameableBeast.MODID, "geo/giant_grasshopper_baby.geo.json");
        }
        return new ResourceLocation(TameableBeast.MODID, "geo/giant_grasshopper.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GrasshopperEntity entity) {
        if(entity.isBaby()){
           return new ResourceLocation(TameableBeast.MODID, "textures/entity/giant_grasshopper_baby.png");
        }
        if(entity.getSaddle()){
            return textures[1][entity.getTextureID()];
        }
        return textures[0][entity.getTextureID()];
    }

    @Override
    public ResourceLocation getAnimationResource(GrasshopperEntity entity) {
        return new ResourceLocation(TameableBeast.MODID, "animations/giant_grasshopper_anims.json");
    }
}
