package com.modderg.tameablebeasts.entities.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.entities.custom.ChikoteEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TameableChikoteModel extends GeoModel<ChikoteEntity> {

    @Override
    public ResourceLocation getModelResource(ChikoteEntity entity) {
        if(!entity.isBaby()){return new ResourceLocation(TameableBeast.MOD_ID, "geo/tameable_chikote.geo.json");}
        return new ResourceLocation(TameableBeast.MOD_ID, "geo/tameable_baby_chikote.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ChikoteEntity entity) {
        return new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/tameable"+ (entity.isBaby() ? "_baby":"") +"_chikote"+ entity.getTextureID() +".png");
    }

    @Override
    public ResourceLocation getAnimationResource(ChikoteEntity entity) {
        if(!entity.isBaby()){return new ResourceLocation(TameableBeast.MOD_ID, "animations/tameable_chikote_anims.json");}
        return new ResourceLocation(TameableBeast.MOD_ID, "animations/tameable_baby_chikote_anims.json");
    }
}
