package com.modderg.tameablebeasts.client.projectile;

import com.modderg.tameablebeasts.server.projectiles.AbstractTameArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class TameArrowRenderer extends ArrowRenderer<AbstractTameArrow> {

    public static final ResourceLocation NORMAL_ARROW_LOCATION = new ResourceLocation("textures/entity/projectiles/arrow.png");

    public TameArrowRenderer(EntityRendererProvider.Context p_173917_) {
        super(p_173917_);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractTameArrow p_114482_) {
        return NORMAL_ARROW_LOCATION;
    }
}
