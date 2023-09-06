package com.modderg.tameablebeasts.client.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.entities.QuetzalcoatlusEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class QuetzalcoatlusModel extends GeoModel<QuetzalcoatlusEntity> {

    private final ResourceLocation[][] textures = {
            new ResourceLocation[] {
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/quetzal.png"),
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/quetzal2.png"),
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/quetzal3.png")
            },
            new ResourceLocation[] {
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/quetzal_saddled.png"),
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/quetzal2_saddled.png"),
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/quetzal3_saddled.png")
            },
            new ResourceLocation[] {
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/quetzal_baby.png"),
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/quetzal_baby2.png"),
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/quetzal_baby3.png")
            }
    };

    @Override
    public ResourceLocation getModelResource(QuetzalcoatlusEntity entity) {
        if(entity.isBaby()){
            return new ResourceLocation(TameableBeast.MODID, "geo/quetzal_baby.geo.json");
        }
        return new ResourceLocation(TameableBeast.MODID, "geo/quetzal.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(QuetzalcoatlusEntity entity) {
        if(entity.isBaby()){
            return textures[2][entity.getTextureID()];
        }
        if(entity.getSaddle()){
            return textures[1][entity.getTextureID()];
        }
        return textures[0][entity.getTextureID()];
    }

    @Override
    public ResourceLocation getAnimationResource(QuetzalcoatlusEntity entity) {
        return new ResourceLocation(TameableBeast.MODID, "animations/quetzal_anims.json");
    }
}
