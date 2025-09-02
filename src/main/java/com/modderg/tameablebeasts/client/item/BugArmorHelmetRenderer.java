package com.modderg.tameablebeasts.client.item;

import com.modderg.tameablebeasts.server.item.BugArmorHelmetItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class BugArmorHelmetRenderer extends GeoArmorRenderer<BugArmorHelmetItem> {
    public BugArmorHelmetRenderer() {
        super(new BugArmorHelmerModel());
    }
}
