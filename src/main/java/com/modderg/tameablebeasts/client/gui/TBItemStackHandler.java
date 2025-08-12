package com.modderg.tameablebeasts.client.gui;

import com.modderg.tameablebeasts.server.entity.abstracts.TBAnimal;
import net.minecraftforge.items.ItemStackHandler;

public class TBItemStackHandler extends ItemStackHandler {

    TBAnimal owner;

    public TBItemStackHandler(TBAnimal animal, int size) {
        super(size);
        this.owner = animal;
    }

    @Override
    protected void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
        owner.updateAttributes();
    }
}
