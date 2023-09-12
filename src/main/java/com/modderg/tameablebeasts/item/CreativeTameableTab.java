package com.modderg.tameablebeasts.item;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.init.ItemInit;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeTameableTab {

    public static final DeferredRegister<CreativeModeTab> TAMEABLE_TABS = DeferredRegister.create(Registries.f_279569_, TameableBeast.MODID);

    public static final RegistryObject<CreativeModeTab> TAMEABLE_TAB = TAMEABLE_TABS.register("tameable_tab", () ->
            CreativeModeTab.builder().m_257737_(() -> new ItemStack(ItemInit.ICEPOP.get())).m_257941_(Component.literal("Tameable Tab")).m_257652_());

    public static void register(IEventBus eventBus){
        TAMEABLE_TABS.register(eventBus);
    }
}
