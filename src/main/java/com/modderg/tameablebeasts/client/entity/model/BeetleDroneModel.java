package com.modderg.tameablebeasts.client.entity.model;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.server.entity.BeetleDrone;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BeetleDroneModel extends GeoModel<BeetleDrone> {

    @Override
    public ResourceLocation getModelResource(BeetleDrone entity) {
        return new ResourceLocation(TameableBeasts.MOD_ID, "geo/beetle_drone.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BeetleDrone entity) {
        return new ResourceLocation(TameableBeasts.MOD_ID, "textures/entity/beetle_drone.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BeetleDrone entity) {
        return new ResourceLocation(TameableBeasts.MOD_ID, "animations/shiny_beetle.animation.json");
    }
}
