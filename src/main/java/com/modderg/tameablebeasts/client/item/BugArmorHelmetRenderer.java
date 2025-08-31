package com.modderg.tameablebeasts.client.item;

import com.modderg.tameablebeasts.server.item.BugArmorHelmet;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class BugArmorHelmetRenderer extends GeoArmorRenderer<BugArmorHelmet> {
    public BugArmorHelmetRenderer() {
        super(new BugArmorHelmerModel());
    }
}
