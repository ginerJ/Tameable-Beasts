package com.modderg.tameablebeasts.server.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class TBShieldItem extends ShieldItem {

    Item[] repairItems;

    public TBShieldItem(Properties p_43089_, Item... repairTypes) {
        super(p_43089_);

        repairItems = repairTypes;
    }

    public TBShieldItem(Properties p_43089_, ItemStack... repairTypes) {
        super(p_43089_);

        repairItems = Arrays.stream(repairTypes).map(ItemStack::getItem).toArray(Item[]::new);
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack shield, @NotNull ItemStack material) {
        return Arrays.stream(repairItems).anyMatch(item -> item == material.getItem()) || super.isValidRepairItem(shield, material);
    }
}
