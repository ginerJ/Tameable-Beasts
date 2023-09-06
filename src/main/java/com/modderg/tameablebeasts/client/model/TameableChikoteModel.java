package com.modderg.tameablebeasts.client.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.entities.TameableChikoteEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TameableChikoteModel extends GeoModel<TameableChikoteEntity> {

    private final ResourceLocation[][] textures = {
            new ResourceLocation[] {
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/tameable_chikote.png"),
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/tameable_chikote2.png"),
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/tameable_chikote3.png")
            },
            new ResourceLocation[] {
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/tameable_chikote_saddled.png"),
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/tameable_chikote2_saddled.png"),
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/tameable_chikote3_saddled.png")
            },
            new ResourceLocation[] {
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/tameable_baby_chikote.png"),
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/tameable_baby_chikote2.png"),
                    new ResourceLocation(TameableBeast.MODID, "textures/entity/tameable_baby_chikote3.png")
            }
    };

    @Override
    public ResourceLocation getModelResource(TameableChikoteEntity entity) {
        if(!entity.isBaby()){return new ResourceLocation(TameableBeast.MODID, "geo/tameable_chikote.geo.json");}
        return new ResourceLocation(TameableBeast.MODID, "geo/tameable_baby_chikote.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TameableChikoteEntity entity) {
        if(!entity.isBaby()){
            if(entity.getSaddle()){
                return textures[1][entity.getTextureID()];
            }
            return textures[0][entity.getTextureID()];
        }
        return textures[2][entity.getTextureID()];
    }

    @Override
    public ResourceLocation getAnimationResource(TameableChikoteEntity entity) {
        if(!entity.isBaby()){return new ResourceLocation(TameableBeast.MODID, "animations/tameable_chikote_anims.json");}
        return new ResourceLocation(TameableBeast.MODID, "animations/tameable_baby_chikote_anims.json");
    }
}
