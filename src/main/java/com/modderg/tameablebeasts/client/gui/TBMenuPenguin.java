package com.modderg.tameablebeasts.client.gui;

import oshi.util.tuples.Pair;
import com.modderg.tameablebeasts.registry.TBAdvancementRegistry;
import com.modderg.tameablebeasts.registry.TBItemRegistry;
import com.modderg.tameablebeasts.registry.TBMenuRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
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
    protected void setupSlots() {
        int a = this.addSpecialSlot(
                TBMenu.FIRST_SLOT_POS, TBMenu.ICE_HELMET_SLOT_TEXTURE, SoundEvents.ARMOR_EQUIP_GOLD, TBItemRegistry.ICE_HELMET.get());

        this.addSpecialSlot(TBMenu.SECOND_SLOT_POS, TBMenu.ICE_CHESTPLATE_SLOT_TEXTURE, SoundEvents.ARMOR_EQUIP_GOLD, TBItemRegistry.ICE_CHESTPLATE.get());
        this.addSpecialSlot(TBMenu.FOURTH_SLOT_POS, TBMenu.POPSICLE_SLOT_TEXTURE, SoundEvents.ARMOR_EQUIP_IRON, TBItemRegistry.ICEPOP.get());

        int b = this.addSpecialSlot(
                TBMenu.FIFTH_SLOT_POS, TBMenu.POPSICLE_SLOT_TEXTURE, SoundEvents.ARMOR_EQUIP_IRON, TBItemRegistry.ICEPOP.get());

        this.advancementsInfo.add(new Pair<>(new Pair<>(a, b), TBAdvancementRegistry.ICE_ARMOR));
    }
}
