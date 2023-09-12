package com.modderg.tameablebeasts.client.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.entities.ChikoteEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TameableChikoteModel extends GeoModel<ChikoteEntity> {

    @Override
    public ResourceLocation getModelResource(ChikoteEntity entity) {
        if(!entity.isBaby()){return new ResourceLocation(TameableBeast.MODID, "geo/tameable_chikote.geo.json");}
        return new ResourceLocation(TameableBeast.MODID, "geo/tameable_baby_chikote.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ChikoteEntity entity) {
        if(entity.isBaby()){
            return new ResourceLocation(TameableBeast.MODID, "textures/entity/tameable_chikote.png");
        }
        return new ResourceLocation(TameableBeast.MODID, "textures/entity/tameable_chikote"+ entity.getTextureID() +".png");
    }

    @Override
    public ResourceLocation getAnimationResource(ChikoteEntity entity) {
        if(!entity.isBaby()){return new ResourceLocation(TameableBeast.MODID, "animations/tameable_chikote_anims.json");}
        return new ResourceLocation(TameableBeast.MODID, "animations/tameable_baby_chikote_anims.json");
    }
}
