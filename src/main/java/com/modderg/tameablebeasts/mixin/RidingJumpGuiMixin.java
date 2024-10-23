package com.modderg.tameablebeasts.mixin;

import com.modderg.tameablebeasts.client.entity.CustomJumpMeter;
import com.modderg.tameablebeasts.server.entity.FlyingRideableTBAnimal;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.PlayerRideableJumping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class RidingJumpGuiMixin {

    @Unique
    private static final ResourceLocation GUI_ICONS_LOCATION = new ResourceLocation("textures/gui/icons.png");

    @Inject(method = "renderJumpMeter", at = @At("HEAD"), cancellable = true)
    private void renderJumpMeterFlying(PlayerRideableJumping p_282774_, GuiGraphics guiGraphics, int p_283351_, CallbackInfo ci) {
        if (p_282774_ instanceof CustomJumpMeter animal) {
            animal.renderJumpMeter(guiGraphics);
            ci.cancel();
        }
    }
}
