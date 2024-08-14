package com.modderg.tameablebeasts.mixin;

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
    private void renderJumpMeterFlying(PlayerRideableJumping p_282774_, GuiGraphics p_282939_, int p_283351_, CallbackInfo ci) {
        if (p_282774_ instanceof FlyingRideableTBAnimal animal) {
            int k = p_282939_.guiHeight() - 32 + 3;

            p_282939_.blit(GUI_ICONS_LOCATION, p_283351_, k, 0, 84, 182, 5);

            p_282939_.blit(GUI_ICONS_LOCATION, p_283351_, k, 0, 89, (int) (182 * animal.getStaminaScale()), 5);

            ci.cancel();
        }
    }
}
