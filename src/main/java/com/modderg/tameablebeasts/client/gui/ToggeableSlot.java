package com.modderg.tameablebeasts.client.gui;

import com.modderg.tameablebeasts.server.entity.abstracts.TBAnimal;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

class ToggeableSlot extends SlotItemHandler {

    boolean isToggled = false;

    public ToggeableSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    public void toggle(boolean b) {
        isToggled = b;
    }

    @Override
    public boolean isActive() {
        return isToggled;
    }
}