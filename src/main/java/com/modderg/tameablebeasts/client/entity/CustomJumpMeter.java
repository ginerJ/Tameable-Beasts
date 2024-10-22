package com.modderg.tameablebeasts.client.entity;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;

public interface CustomJumpMeter {

    default void renderJumpMeter(GuiGraphics guiGraphics) {
        ResourceLocation sprite = getStaminaSpriteLocation();
        ResourceLocation background = getStaminaBackgroundLocation();

        int screenWidth = guiGraphics.guiWidth(), screenHeight = guiGraphics.guiHeight();

        Vec2 dimensions = getStaminaSpriteDimensions();
        int imageWidth = (int) dimensions.x;
        int imageHeight = (int) dimensions.y;

        int x = (screenWidth - imageWidth) / 2;
        int y = screenHeight - 70;

        guiGraphics.blit(background, x, y, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);

        float staminaHeight = getStaminaHeight();
        int visibleHeight = (int) (imageHeight * staminaHeight);

        int spriteYOffset = imageHeight - visibleHeight;

        guiGraphics.blit(sprite, x, y + spriteYOffset, 0, spriteYOffset, imageWidth, visibleHeight, imageWidth, imageHeight);
    }

    ResourceLocation getStaminaSpriteLocation();
    ResourceLocation getStaminaBackgroundLocation();
    Vec2 getStaminaSpriteDimensions();
    float getStaminaHeight();
}
