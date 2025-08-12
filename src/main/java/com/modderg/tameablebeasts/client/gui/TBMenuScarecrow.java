package com.modderg.tameablebeasts.client.gui;

import com.modderg.tameablebeasts.registry.TBMenuRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;

import java.util.Objects;

public class TBMenuScarecrow extends TBMenu{
    public TBMenuScarecrow(int container_id, Inventory playerInventory, Entity tbAnimal) {
        super(TBMenuRegistry.SCARECROW_MENU_CONTAINER.get(), container_id, playerInventory, tbAnimal);
    }

    public TBMenuScarecrow(int container_id, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(container_id, playerInventory, Objects.requireNonNull(playerInventory.player.level().getEntity(extraData.readInt())));
    }

    @Override
    public void setUpSlots() {
        this.addSpecialSlots(TBMenu.FIRST_SLOT, TBMenu.SCYTHE_SLOT);
    }
}
