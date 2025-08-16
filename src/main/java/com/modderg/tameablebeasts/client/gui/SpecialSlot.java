package com.modderg.tameablebeasts.client.gui;

import com.modderg.tameablebeasts.server.entity.abstracts.TBAnimal;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

class SpecialSlot extends SlotItemHandler {

    TBAnimal animal;
    public Item matchingItem;
    public SoundEvent soundEvent;

    public SpecialSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, TBAnimal animal, SoundEvent soundEvent, Item matchingItem) {
        super(itemHandler, index, xPosition, yPosition);

        this.matchingItem = matchingItem;
        this.animal = animal;
        this.soundEvent = soundEvent;
    }

    @Override
    public void set(@NotNull ItemStack newStack) {

        super.set(newStack);

        if(this.getItem().is(matchingItem))
            animal.playSound(soundEvent);
    }

    @Override
    public void onTake(@NotNull Player player, @NotNull ItemStack stack) {
        super.onTake(player, stack);

        if(stack.is(matchingItem))
            animal.playSound(soundEvent);
    }

    @Override
    public @NotNull ItemStack remove(int amount) {
        ItemStack before = this.getItem().copy();
        ItemStack result = super.remove(amount);

        if (!before.isEmpty() && before.is(matchingItem) && this.getItem().isEmpty())
            animal.playSound(soundEvent);

        return result;
    }
}