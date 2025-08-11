package com.modderg.tameablebeasts.client.gui;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.server.block.EggBlockEntity;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TameableBeasts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class OverlayEggHatching {

    private static final ResourceLocation HATCH_MENU = new ResourceLocation(TameableBeasts.MOD_ID, "textures/gui/hatch_gui.png");

    public static IGuiOverlay HATCH_OVERLAY = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {

        if(Minecraft.getInstance().screen == null &&
                Minecraft.getInstance().cameraEntity instanceof Player player &&
                Minecraft.getInstance().hitResult instanceof BlockHitResult blockHitResult &&
                player.level().getBlockEntity(blockHitResult.getBlockPos()) instanceof EggBlockEntity eggBlockEntity){

                renderHatchingData(guiGraphics, eggBlockEntity);
        }
    });

    @SubscribeEvent
    public static void registerEntityRenders(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("hatch", OverlayEggHatching.HATCH_OVERLAY);
    }

    private static void renderHatchingData(GuiGraphics guiGraphics, EggBlockEntity eggBlockEntity){

        Minecraft minecraft = Minecraft.getInstance();
        Window window = minecraft.getWindow();

        Player player = minecraft.level.getPlayerByUUID(eggBlockEntity.getOwnerUUID());

        String ownerName = "Owned by: " + (player != null ? player.getName().getString() : "None");


        int x = window.getGuiScaledWidth()/2;
        int y = window.getGuiScaledHeight()/2-68;

        boolean isWarm = eggBlockEntity.isWarm();
        boolean goneBad = eggBlockEntity.goBadTimer <= 0;

        guiGraphics.blit(HATCH_MENU,x,y,0,0,121,68,121,68);

        guiGraphics.drawString(Minecraft.getInstance().font, eggBlockEntity.getCleanSpecies().replace("_"," "),
                x+5,y+5, 0x000000, false);

        guiGraphics.drawString(Minecraft.getInstance().font, ownerName,x+5,y+15,
                0x000000, false);

        guiGraphics.drawString(Minecraft.getInstance().font,"Status: ",x+5,y+25,
                0x000000, false);

        guiGraphics.drawString(Minecraft.getInstance().font,goneBad?"Gone Bad":(isWarm?"Warm":"Cold"),x+40,y+25,
                goneBad?0x98BB23:(isWarm?0xF37E22:0x36D2FC), false);
    }
}
