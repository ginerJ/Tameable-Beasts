package com.modderg.tameablebeasts.client.gui;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.server.block.EggBlockEntity;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TameableBeast.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class HatchingOverlay {

    private static final ResourceLocation HATCH_MENU = new ResourceLocation(TameableBeast.MOD_ID, "textures/gui/hatch_gui.png");

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
        event.registerAboveAll("hatch", HatchingOverlay.HATCH_OVERLAY);
    }

    private static void renderHatchingData(GuiGraphics guiGraphics, EggBlockEntity eggBlockEntity){

        Minecraft minecraft = Minecraft.getInstance();
        Window window = minecraft.getWindow();

        int x = window.getGuiScaledWidth()/2;
        int y = window.getGuiScaledHeight()/2-68;

        guiGraphics.blit(HATCH_MENU,x,y,0,0,121,68,121,68);

        guiGraphics.drawString(Minecraft.getInstance().font, eggBlockEntity.getGuiSpecies(),
                x+5,y+5, 0x000000, false);

        //drat ownership

        Player player = minecraft.level.getPlayerByUUID(eggBlockEntity.getOwnerUUID());

        String ownerName = I18n.get("gui.text.tameablebeasts.hatching_gui.owned_by");
        ownerName += (player != null ? player.getName().getString() : "None");
        ownerName = ownerName.substring(0, Math.min(ownerName.length(), 17));

        guiGraphics.drawString(Minecraft.getInstance().font, ownerName,x+5,y+15,
                0x000000, false);

        //draw status text

        boolean isWarm = eggBlockEntity.isWarm();
        boolean goneBad = eggBlockEntity.goBadTimer <= 0;

        String status_txt = I18n.get("gui.text.tameablebeasts.hatching_gui.status");
        guiGraphics.drawString(Minecraft.getInstance().font, status_txt,x+5,y+25,
                0x000000, false);

        int statusWidth = Minecraft.getInstance().font.width(status_txt);

        String statusKey = "gui.text.tameablebeasts.hatching_gui." + (goneBad ? "gone_bad" : (isWarm ? "warm" : "cold"));
        int statusColor = goneBad ? 0x98BB23 : (isWarm ? 0xF37E22 : 0x36D2FC);

        guiGraphics.drawString(Minecraft.getInstance().font, I18n.get(statusKey),
                x + 5 + statusWidth, y + 25, statusColor, false);
    }
}
