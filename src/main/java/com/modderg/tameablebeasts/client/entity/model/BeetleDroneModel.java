package com.modderg.tameablebeasts.client.entity.model;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.client.entity.TBGeoModel;
import com.modderg.tameablebeasts.server.entity.custom.BeetleDrone;
import com.modderg.tameablebeasts.server.entity.custom.FlyingBeetleEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;

public class BeetleDroneModel extends TBGeoModel<BeetleDrone> {

    @Override
    public ResourceLocation getModelResource(BeetleDrone entity) {
        return new ResourceLocation(TameableBeast.MOD_ID, "geo/beetle_drone.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BeetleDrone entity) {
        return new ResourceLocation(TameableBeast.MOD_ID, "textures/entity/beetle_drone.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BeetleDrone entity) {
        return new ResourceLocation(TameableBeast.MOD_ID, "animations/tameable_beetle_anims.json");
    }
}
