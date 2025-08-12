package com.modderg.tameablebeasts.client.gui;

import com.modderg.tameablebeasts.server.entity.abstracts.TBAnimal;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

class SpecialSlot extends SlotItemHandler {

    TBAnimal animal;
    Item matchingItem;
    SoundEvent soundEvent;

    public SpecialSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, TBAnimal animal, SoundEvent soundEvent, Item matchingItem) {
        super(itemHandler, index, xPosition, yPosition);

        this.matchingItem = matchingItem;
        this.animal = animal;
        this.soundEvent = soundEvent;
    }

    @Override
    public void set(@NotNull ItemStack newStack) {
        if(this.getItem().is(matchingItem))
            animal.playSound(soundEvent);

        super.set(newStack);

        if(this.getItem().is(matchingItem))
            animal.playSound(soundEvent);
    }
}