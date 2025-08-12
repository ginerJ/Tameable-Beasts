package com.modderg.tameablebeasts.client.gui;

import com.modderg.tameablebeasts.registry.TBMenuRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Items;

import java.util.Objects;

public class TBMenuGrasshopper extends TBMenu{
    public TBMenuGrasshopper(int container_id, Inventory playerInventory, Entity tbAnimal) {
        super(TBMenuRegistry.GRASSHOPPER_MENU_CONTAINER.get(), container_id, playerInventory, tbAnimal);
    }

    public TBMenuGrasshopper(int container_id, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(container_id, playerInventory, Objects.requireNonNull(playerInventory.player.level().getEntity(extraData.readInt())));
    }

    @Override
    protected void setupSlots() {
        this.addSpecialSlot(TBMenu.FIRST_SLOT, TBMenu.SADDLE_SLOT, SoundEvents.HORSE_SADDLE, Items.SADDLE);
        this.addSpecialSlot(TBMenu.SECOND_SLOT, TBMenu.CHEST_SLOT, SoundEvents.CHEST_CLOSE, Items.CHEST);
    }
}
