package com.modderg.tameablebeasts.client.item;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.server.item.BugArmorHelmet;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BugArmorHelmerModel extends GeoModel<BugArmorHelmet> {
    @Override
    public ResourceLocation getModelResource(BugArmorHelmet animatable) {
        return new ResourceLocation(TameableBeasts.MOD_ID, "geo/bug_armor_helmet.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BugArmorHelmet animatable) {
        return new ResourceLocation(TameableBeasts.MOD_ID, "textures/models/armor/bug_armor_helmet.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BugArmorHelmet animatable) {
        return new ResourceLocation(TameableBeasts.MOD_ID, "animations/nothing.animation.json");
    }
}
