package com.modderg.tameablebeasts.client.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.entities.TameablePenguinEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TameablePenguinModel extends GeoModel<TameablePenguinEntity> {

    private ResourceLocation[][] textures = {
            new ResourceLocation[]{
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/tameable_penguin.png"),
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/tameable_penguin2.png")
            },
            new ResourceLocation[]{
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/tameable_baby_penguin.png"),
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/tameable_baby_penguin2.png")
            },
            new ResourceLocation[]{
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/tameable_penguin_sword.png"),
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/tameable_penguin2_sword.png")
            },
            new ResourceLocation[]{
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/armored_tameable_penguin_sword.png"),
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/armored_tameable_penguin2_sword.png")
            },
            new ResourceLocation[]{
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/armored_tameable_penguin.png"),
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/armored_tameable_penguin2.png")
            }
    };

    @Override
    public ResourceLocation getModelResource(TameablePenguinEntity entity) {
        if(entity.isBaby()){return new ResourceLocation(TameableBeast.MODID, "geo/tameable_baby_penguin.geo.json");}
        return new ResourceLocation(TameableBeast.MODID, "geo/tameable_penguin.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TameablePenguinEntity entity) {
         if (entity.getSword()){
             if(entity.getHelmet()){
                 return textures[3][entity.getTextureID()];
             }
             return textures[2][entity.getTextureID()];
         } else if (entity.getHelmet()){
             return textures[4][entity.getTextureID()];
         } else if(entity.isBaby()){return textures[1][entity.getTextureID()];}
        return textures[0][entity.getTextureID()];
    }

    @Override
    public ResourceLocation getAnimationResource(TameablePenguinEntity entity) {
        if(entity.isBaby()){return new ResourceLocation(TameableBeast.MODID, "animations/tameable_baby_penguin.anims.json");}
        return new ResourceLocation(TameableBeast.MODID, "animations/tameable_penguin.anims.json");
    }
}
