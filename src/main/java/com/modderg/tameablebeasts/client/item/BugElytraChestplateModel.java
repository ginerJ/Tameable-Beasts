package com.modderg.tameablebeasts.client.item;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.server.item.BugElytraChestplateItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BugElytraChestplateModel extends GeoModel<BugElytraChestplateItem> {
    @Override
    public ResourceLocation getModelResource(BugElytraChestplateItem animatable) {
        return new ResourceLocation(TameableBeasts.MOD_ID, "geo/bug_elytra.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BugElytraChestplateItem animatable) {
        return new ResourceLocation(TameableBeasts.MOD_ID, "textures/models/armor/bug_armor_elytra.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BugElytraChestplateItem animatable) {
        return new ResourceLocation(TameableBeasts.MOD_ID, "animations/bug_elytra.animation.json");
    }
}
