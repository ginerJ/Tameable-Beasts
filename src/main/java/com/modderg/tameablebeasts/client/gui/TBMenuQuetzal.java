package com.modderg.tameablebeasts.client.gui;

import com.modderg.tameablebeasts.registry.TBItemRegistry;
import com.modderg.tameablebeasts.registry.TBMenuRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Items;

import java.util.Objects;

public class TBMenuQuetzal extends TBMenu{
    public TBMenuQuetzal(int container_id, Inventory playerInventory, Entity tbAnimal) {
        super(TBMenuRegistry.QUETZAL_MENU_CONTAINER.get(), container_id, playerInventory, tbAnimal);
    }

    public TBMenuQuetzal(int container_id, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(container_id, playerInventory, Objects.requireNonNull(playerInventory.player.level().getEntity(extraData.readInt())));
    }

    @Override
    protected void setupSlots() {
        this.addSpecialSlot(TBMenu.FIRST_SLOT, TBMenu.SADDLE_SLOT, SoundEvents.HORSE_SADDLE, Items.SADDLE);
        this.addSpecialSlot(TBMenu.SECOND_SLOT, TBMenu.STAND_SLOT, SoundEvents.CAMEL_SADDLE, TBItemRegistry.QUETZAL_STAND.get());
        this.addSpecialSlot(TBMenu.THIRD_SLOT, TBMenu.CHEST_SLOT, SoundEvents.CHEST_CLOSE, Items.CHEST);
        this.setupChestSlots();
    }
}
