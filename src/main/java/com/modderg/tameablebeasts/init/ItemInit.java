package com.modderg.tameablebeasts.init;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.item.IceHelmetItem;
import com.modderg.tameablebeasts.item.PurpleAllay;
import com.modderg.tameablebeasts.item.QuetzalEggItem;
import com.modderg.tameablebeasts.item.HatItem;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TameableBeast.MODID);

    //items
    public static final RegistryObject<Item> ICEPOP = ITEMS.register("icepop", () -> new Item(new Item.Properties().food(Foods.SWEET_BERRIES)));
    public static final RegistryObject<Item> ICE_HELMET = ITEMS.register("ice_helmet", () -> new IceHelmetItem(new IceHelmetItem.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PURPLE_ALLAY = ITEMS.register("purple_allay", () -> new PurpleAllay(new PurpleAllay.Properties().stacksTo(1)));
    public static final RegistryObject<Item> QUETZAL_EGG = ITEMS.register("quetzal_egg", () -> new QuetzalEggItem(new QuetzalEggItem.Properties().stacksTo(16)));
    public static final RegistryObject<Item> LEAF = ITEMS.register("leaf", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> FUR = ITEMS.register("racoon_fur", () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> GRASSHOPPER_SADDLE = ITEMS.register("grasshopper_saddle", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ROLYPOLY_SADDLE = ITEMS.register("roly_poly_saddle", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> CHIKOTE_SADDLE = ITEMS.register("chikote_saddle", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> QUETZAL_SADDLE = ITEMS.register("quetzal_saddle", () -> new Item(new Item.Properties().stacksTo(1)));


    //spawn eggs
    public static final RegistryObject<ForgeSpawnEggItem> RACOON_SPAWN_EGG = ITEMS.register("racoon_spawn_egg", () -> new ForgeSpawnEggItem(
            ModEntityClass.TAMEABLE_RACOON, 0xA8846E, 0x5D4130, new Item.Properties()));

    public static final RegistryObject<ForgeSpawnEggItem> PENGUIN_SPAWN_EGG = ITEMS.register("penguin_spawn_egg", () -> new ForgeSpawnEggItem(
            ModEntityClass.TAMEABLE_PENGUIN, 0x080A27, 0xECEDF6, new Item.Properties()));

    public static final RegistryObject<ForgeSpawnEggItem> CHIKOTE_SPAWN_EGG = ITEMS.register("chikote_spawn_egg", () -> new ForgeSpawnEggItem(
            ModEntityClass.TAMEABLE_CHIKOTE, 0xFFF38E, 0xDCB834, new Item.Properties()));

    public static final RegistryObject<ForgeSpawnEggItem> BEETLE_SPAWN_EGG = ITEMS.register("beetle_spawn_egg", () -> new ForgeSpawnEggItem(
            ModEntityClass.TAMEABLE_BEETLE, 0x224E79, 0x3991A9, new Item.Properties()));

    public static final RegistryObject<ForgeSpawnEggItem> QUETZAL_SPAWN_EGG = ITEMS.register("quetzal_spawn_egg", () -> new ForgeSpawnEggItem(
            ModEntityClass.QUETZALCOATLUS, 0x743B62, 0xAC786E, new Item.Properties()));

    public static final RegistryObject<ForgeSpawnEggItem> GIANT_GRASSHOPPER_SPAWN_EGG = ITEMS.register("giant_grasshopper_spawn_egg", () -> new ForgeSpawnEggItem(
            ModEntityClass.GIANT_GRASSHOPPER, 0xC5E152, 0x7EBB27, new Item.Properties()));

    public static final RegistryObject<ForgeSpawnEggItem> GROUND_BEETLE_SPAWN_EGG = ITEMS.register("ground_beetle_spawn_egg", () -> new ForgeSpawnEggItem(
            ModEntityClass.TAMEABLE_GROUND_BEETLE, 0x9E75D3, 0x7346AC, new Item.Properties()));

    public static final RegistryObject<ForgeSpawnEggItem> GROUND_ROLY_POLY_SPAWN_EGG = ITEMS.register("giant_roly_poly_spawn_egg", () -> new ForgeSpawnEggItem(
            ModEntityClass.GIANT_ROLY_POLY, 0x6E77B8, 0x3A4072, new Item.Properties()));

    public static final RegistryObject<ForgeSpawnEggItem> SCARECROW_SPAWN_EGG = ITEMS.register("scarecrow_spawn_egg", () -> new ForgeSpawnEggItem(
            ModEntityClass.SCARECROW_ALLAY, 0xFFC347, 0x6A5ACD, new Item.Properties()));


    //hats
    public static final RegistryObject<Item> SCARECROW_STRAW_HAT = ITEMS.register("scarecrow_straw_hat", () -> new HatItem(new HatItem.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FLYING_HELMET = ITEMS.register("flying_helmet", () -> new HatItem(new HatItem.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BIKER_HELMET = ITEMS.register("biker_helmet", () -> new HatItem(new HatItem.Properties().stacksTo(1)));

    //tools
    public static final RegistryObject<Item> IRON_BIG_HOE = ITEMS.register("iron_big_hoe", () -> new HoeItem(Tiers.IRON, -1, -1.0F, (new Item.Properties())));
}
