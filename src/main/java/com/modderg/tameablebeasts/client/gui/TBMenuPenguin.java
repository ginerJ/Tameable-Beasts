package com.modderg.tameablebeasts.client.gui;

import com.modderg.tameablebeasts.registry.TBMenuRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;

import java.util.Objects;

public class TBMenuPenguin extends TBMenu{
    public TBMenuPenguin(int container_id, Inventory playerInventory, Entity tbAnimal) {
        super(TBMenuRegistry.PENGUIN_MENU_CONTAINER.get(), container_id, playerInventory, tbAnimal);
    }

    public TBMenuPenguin(int container_id, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(container_id, playerInventory, Objects.requireNonNull(playerInventory.player.level().getEntity(extraData.readInt())));
    }

    @Override
    public void setUpSlots() {
        this.addSpecialSlots(TBMenu.FIRST_SLOT, TBMenu.ICE_HELMET_SLOT);
        this.addSpecialSlots(TBMenu.SECOND_SLOT, TBMenu.ICE_CHESTPLATE_SLOT);
        this.addSpecialSlots(TBMenu.FOURTH_SLOT, TBMenu.POPSICLE_SLOT);
        this.addSpecialSlots(TBMenu.FIFTH_SLOT, TBMenu.POPSICLE_SLOT);
    }
}
