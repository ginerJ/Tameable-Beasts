package com.modderg.tameablebeasts.client.gui;

import com.modderg.tameablebeasts.registry.TBItemRegistry;
import com.modderg.tameablebeasts.registry.TBMenuRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Items;

import java.util.Objects;

public class TBMenuPenguin extends TBMenu{
    public TBMenuPenguin(int container_id, Inventory playerInventory, Entity tbAnimal) {
        super(TBMenuRegistry.PENGUIN_MENU_CONTAINER.get(), container_id, playerInventory, tbAnimal);
    }

    public TBMenuPenguin(int container_id, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(container_id, playerInventory, Objects.requireNonNull(playerInventory.player.level().getEntity(extraData.readInt())));
    }

    @Override
    protected void setupSlots() {
        this.addSpecialSlot(TBMenu.FIRST_SLOT, TBMenu.ICE_HELMET_SLOT, SoundEvents.ARMOR_EQUIP_GOLD, TBItemRegistry.ICE_HELMET.get());
        this.addSpecialSlot(TBMenu.SECOND_SLOT, TBMenu.ICE_CHESTPLATE_SLOT, SoundEvents.ARMOR_EQUIP_GOLD, TBItemRegistry.ICE_CHESTPLATE.get());
        this.addSpecialSlot(TBMenu.FOURTH_SLOT, TBMenu.POPSICLE_SLOT, SoundEvents.ARMOR_EQUIP_IRON, TBItemRegistry.ICEPOP.get());
        this.addSpecialSlot(TBMenu.FIFTH_SLOT, TBMenu.POPSICLE_SLOT, SoundEvents.ARMOR_EQUIP_IRON, TBItemRegistry.ICEPOP.get());
    }
}
