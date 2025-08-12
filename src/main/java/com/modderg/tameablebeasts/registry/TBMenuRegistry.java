package com.modderg.tameablebeasts.registry;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.client.gui.*;
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

    public static final RegistryObject<MenuType<TBMenuJustSaddle>> JUST_SADDLE_MENU_CONTAINER = MENUS.register("just_saddle_container",
            () -> IForgeMenuType.create(TBMenuJustSaddle::new));

    public static final RegistryObject<MenuType<TBMenuScarecrow>> SCARECROW_MENU_CONTAINER = MENUS.register("scarecrow_container",
            () -> IForgeMenuType.create(TBMenuScarecrow::new));

    public static final RegistryObject<MenuType<TBMenuQuetzal>> QUETZAL_MENU_CONTAINER = MENUS.register("quetzal_container",
            () -> IForgeMenuType.create(TBMenuQuetzal::new));

    public static final RegistryObject<MenuType<TBMenuGrasshopper>> GRASSHOPPER_MENU_CONTAINER = MENUS.register("grasshopper_container",
            () -> IForgeMenuType.create(TBMenuGrasshopper::new));
}

