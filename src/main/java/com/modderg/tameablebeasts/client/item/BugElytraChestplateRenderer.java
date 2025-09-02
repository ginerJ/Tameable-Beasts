package com.modderg.tameablebeasts.client.item;

import com.modderg.tameablebeasts.server.item.BugElytraChestplateItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class BugElytraChestplateRenderer extends GeoArmorRenderer<BugElytraChestplateItem> {
    public BugElytraChestplateRenderer() {
        super(new BugElytraChestplateModel());
    }
}
