package com.modderg.tameablebeasts.client.gui;

import com.modderg.tameablebeasts.registry.TBMenuRegistry;
import com.modderg.tameablebeasts.server.entity.abstracts.TBAnimal;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.Objects;

public class TBMenu extends AbstractContainerMenu {
    protected final TBAnimal tbAnimal;

    public ArrayList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> specialSlots = new ArrayList<>();

    public static final Pair<Integer, Integer> FIRST_SLOT = new Pair<>(7, 17);
    public static final Pair<Integer, Integer> SECOND_SLOT = new Pair<>(7, 35);
    public static final Pair<Integer, Integer> THIRD_SLOT = new Pair<>(7 , 53);
    public static final Pair<Integer, Integer> FOURTH_SLOT = new Pair<>(79, 17);
    public static final Pair<Integer, Integer> FIFTH_SLOT = new Pair<>(79, 35);
    public static final Pair<Integer, Integer> SIXTH_SLOT = new Pair<>(79, 53);

    public static final Pair<Integer, Integer> CHEST_SLOT = new Pair<>(0, 220);
    public static final Pair<Integer, Integer> SADDLE_SLOT = new Pair<>(18, 220);
    public static final Pair<Integer, Integer> STAND_SLOT = new Pair<>(36, 220);
    public static final Pair<Integer, Integer> ICE_HELMET_SLOT = new Pair<>(54, 220);
    public static final Pair<Integer, Integer> ICE_CHESTPLATE_SLOT = new Pair<>(72, 220);
    public static final Pair<Integer, Integer> POPSICLE_SLOT = new Pair<>(90, 220);
    public static final Pair<Integer, Integer> SCYTHE_SLOT = new Pair<>(108, 220);

    public TBMenu(MenuType<?> menuType, int container_id, Inventory playerInventory, Entity tbAnimal) {
        super(menuType, container_id);
        this.tbAnimal = (TBAnimal) tbAnimal;

        setupSlots();
        createPlayerHotBar(playerInventory);
        createPlayerInventory(playerInventory);
    }

    public TBMenu(int container_id, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(TBMenuRegistry.TBMOB_MENU_CONTAINER.get(), container_id, playerInventory, Objects.requireNonNull(playerInventory.player.level().getEntity(extraData.readInt())));
    }

    public TBMenu(int container_id, Inventory playerInventory, Entity tbAnimal) {
        this(TBMenuRegistry.TBMOB_MENU_CONTAINER.get(), container_id, playerInventory, tbAnimal);
    }

    protected void setupSlots(){}

    public void addSpecialSlot(Pair<Integer, Integer> slotPos, Pair<Integer, Integer> slotType, SoundEvent soundEvent, Item matchingItem) {
        specialSlots.add(new Pair<>(slotPos, slotType));

        this.tbAnimal.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent( inventory -> {
            addSlot(new SpecialSlot(inventory, this.slots.size(), slotPos.getA() + 1, slotPos.getB() + 1,
                    tbAnimal, soundEvent, matchingItem));
        });
    }

    private void createPlayerHotBar(Inventory playerInventory){
        for(int column = 0; column < 9; column++)
            addSlot(new Slot(playerInventory, column, 8 + column * 18, 142));
    }

    private void createPlayerInventory(Inventory playerInventory) {
        for(int row = 0; row < 3; row++)
            for(int column = 0; column < 9; column++)
                addSlot(new Slot(playerInventory, 9 + column + row * 9, 8 + column * 18, 84 + row*18));
    }

    public TBAnimal getMob() {
        return this.tbAnimal;
    }

    @Override
    public boolean stillValid(@NotNull Player playerIn) {
        return this.tbAnimal.isAlive();
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        Slot slot = getSlot(index);
        if (slot == null || !slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stack = slot.getItem();
        ItemStack copy  = stack.copy();

        int playerEnd = 36;          // player range = [0, 36)
        int entityEnd = this.slots.size(); // entity range = [36, size)

        boolean moved = (index < playerEnd)
                ? moveItemStackTo(stack, playerEnd, entityEnd, false) // player → entity
                : moveItemStackTo(stack, 0, playerEnd, false);         // entity → player

        if (!moved) return ItemStack.EMPTY;

        if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
        else slot.setChanged();

        slot.onTake(player, stack);
        return copy;
    }


}
