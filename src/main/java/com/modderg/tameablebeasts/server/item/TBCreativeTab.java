package com.modderg.tameablebeasts.server.item;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.registry.TBItemRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TBCreativeTab {

    public static final DeferredRegister<CreativeModeTab> TAMEABLE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TameableBeasts.MOD_ID);

//    public static final RegistryObject<CreativeModeTab> TAMEABLE_TAB = TAMEABLE_TABS.register("tameable_tab", () ->
//            CreativeModeTab.builder().icon(() -> new ItemStack(TBItemRegistry.ICEPOP.get())).title(Component.literal("Tameable Tab")).build());
    public static final RegistryObject<CreativeModeTab> TAMEABLE_TAB = TAMEABLE_TABS.register("tameable_tab", () ->
        CreativeModeTab.builder().icon(() -> new ItemStack(TBItemRegistry.ICEPOP.get())).title(Component.translatable("gui." + TameableBeasts.MOD_ID + ".creative_mode_tab")).build());

    public static void register(IEventBus eventBus){
        TAMEABLE_TABS.register(eventBus);
    }
}
