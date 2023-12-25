package com.modderg.tameablebeasts.item;

import com.modderg.tameablebeasts.TameableBeast;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeTameableTab {

    public static final DeferredRegister<CreativeModeTab> TAMEABLE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TameableBeast.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TAMEABLE_TAB = TAMEABLE_TABS.register("tameable_tab", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ItemInit.ICEPOP.get())).title(Component.literal("Tameable Tab")).build());

    public static void register(IEventBus eventBus){
        TAMEABLE_TABS.register(eventBus);
    }
}
