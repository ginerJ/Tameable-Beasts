package com.modderg.tameablebeasts.registry;

import com.modderg.tameablebeasts.TameableBeasts;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class TBAdvancementRegistry {
    public final static ResourceLocation BRUSH_A_BEAST = new ResourceLocation(TameableBeasts.MOD_ID, "brush_a_beast");
    public final static ResourceLocation METAL_BEETLE = new ResourceLocation(TameableBeasts.MOD_ID, "metal_beetle");
    public final static ResourceLocation FUR_GOLEM = new ResourceLocation(TameableBeasts.MOD_ID, "fur_golem");

    public static void grantAdvancement(ServerPlayer player, ResourceLocation advancement){
        Advancement adv = player.getServer().getAdvancements().getAdvancement(advancement);

        if(adv != null)
            for (String criteria : player.getAdvancements().getOrStartProgress(adv).getRemainingCriteria())
                player.getAdvancements().award(adv, criteria);
    }
}
