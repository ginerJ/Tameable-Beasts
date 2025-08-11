package com.modderg.tameablebeasts.registry;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.client.gui.TBMenu;
import com.modderg.tameablebeasts.client.gui.TBMenuPenguin;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = TameableBeasts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TBMenuRegistry {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, TameableBeasts.MOD_ID);

    public static final RegistryObject<MenuType<TBMenu>> TBMOB_MENU_CONTAINER = MENUS.register("tameable_mob_container",
            () -> IForgeMenuType.create(TBMenu::new));

    public static final RegistryObject<MenuType<TBMenuPenguin>> PENGUIN_MENU_CONTAINER = MENUS.register("penguin_container",
            () -> IForgeMenuType.create(TBMenuPenguin::new));
}

