package com.modderg.tameablebeasts.client.block;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.server.block.EggBlockEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.model.GeoModel;

public class EggBlockModel<T extends BlockEntity & GeoBlockEntity> extends GeoModel<EggBlockEntity> {

    @Override
    public ResourceLocation getModelResource(EggBlockEntity entity) {
        return new ResourceLocation(TameableBeasts.MOD_ID, "geo/"+entity.getCleanSpecies().toLowerCase()+"_egg.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EggBlockEntity entity) {
        return new ResourceLocation(TameableBeasts.MOD_ID,"textures/block/"+entity.getCleanSpecies().toLowerCase()+"_egg.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EggBlockEntity animatable) {
        return null;
    }
}
