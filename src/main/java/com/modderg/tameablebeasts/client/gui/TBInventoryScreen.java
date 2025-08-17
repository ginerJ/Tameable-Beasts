package com.modderg.tameablebeasts.client.gui;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.server.entity.abstracts.TBAnimal;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import oshi.util.tuples.Pair;

@OnlyIn(Dist.CLIENT)
public class TBInventoryScreen extends AbstractContainerScreen<TBMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(TameableBeasts.MOD_ID, "textures/gui/mob_gui.png");

    public TBInventoryScreen(TBMenu container, Inventory inv, Component title) {
        super(container, inv, title);

        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        renderBackground(guiGraphics);
        guiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        for (Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> pair: menu.specialSlots) {
            Pair<Integer, Integer> slotPos = pair.getA();
            Pair<Integer, Integer> textPos = pair.getB();

            guiGraphics.blit(TEXTURE, this.leftPos + slotPos.getA(), this.topPos + slotPos.getB(),
                    textPos.getA(), textPos.getB(), 18, 18);
        }

        InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, this.leftPos + 51, this.topPos + 60,
                17, (float)(this.leftPos + 51) - mouseX, (float)(this.topPos + 75 - 50) - mouseY, this.menu.tbAnimal);

        int chestSlot = this.menu.chestSlot;
        if(chestSlot != -1 && this.menu.slots.get(chestSlot).getItem().is(Items.CHEST))
            guiGraphics.blit(TEXTURE, this.leftPos + 79, this.topPos + 17,
                    0, 166, 90, 54);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float pPartialTicks) {

        super.render(guiGraphics, mouseX, mouseY, pPartialTicks);

        TBAnimal mob = this.menu.getMob();

        renderTooltip(guiGraphics, mouseX, mouseY);

        float healthMultiplier = mob.getHealth()/mob.getMaxHealth();
        guiGraphics.blit(TEXTURE, this.leftPos + 62, this.topPos + 73,
                0, 240, (int) (52*healthMultiplier), 8);

        float happyMultiplier = (float) mob.getHappiness()/100;
        guiGraphics.blit(TEXTURE, this.leftPos + 116, this.topPos + 73,
                0, 248, (int) (52*happyMultiplier), 8);
    }
}
