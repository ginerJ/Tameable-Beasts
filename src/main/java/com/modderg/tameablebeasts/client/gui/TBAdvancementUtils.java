package com.modderg.tameablebeasts.client.gui;

import com.modderg.tameablebeasts.TameableBeasts;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TBAdvancementUtils {
    public static final DeferredRegister<Item> TB_ADVANCEMENT_SPRITES = DeferredRegister.create(ForgeRegistries.ITEMS, TameableBeasts.MOD_ID);

    public static final RegistryObject<Item> TAMEABLEBEASTS = TB_ADVANCEMENT_SPRITES.register("tameablebeasts",
            () -> new Item(new Item.Properties()));
}
