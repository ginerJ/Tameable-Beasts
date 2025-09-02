package com.modderg.tameablebeasts.client.item;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.server.item.BugArmorHelmetItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BugArmorHelmerModel extends GeoModel<BugArmorHelmetItem> {
    @Override
    public ResourceLocation getModelResource(BugArmorHelmetItem animatable) {
        return new ResourceLocation(TameableBeasts.MOD_ID, "geo/bug_armor_helmet.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BugArmorHelmetItem animatable) {
        return new ResourceLocation(TameableBeasts.MOD_ID, "textures/models/armor/bug_armor_helmet.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BugArmorHelmetItem animatable) {
        return new ResourceLocation(TameableBeasts.MOD_ID, "animations/nothing.animation.json");
    }
}
