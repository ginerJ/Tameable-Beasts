package com.modderg.tameablebeasts.item;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.block.BlockInit;
import com.modderg.tameablebeasts.entities.EntityIinit;
import com.modderg.tameablebeasts.item.block.EggBlockItem;
import com.modderg.tameablebeasts.item.block.HatItem;
import com.modderg.tameablebeasts.item.custom.IceHelmetItem;
import com.modderg.tameablebeasts.item.custom.PurpleAllay;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TameableBeast.MOD_ID);

        //items
        public static final RegistryObject<Item> ICEPOP = ITEMS.register("icepop", () -> new Item(new Properties().food(Foods.SWEET_BERRIES)));
        public static final RegistryObject<Item> ICE_HELMET = ITEMS.register("ice_helmet", () -> new IceHelmetItem(new Properties().stacksTo(1)));
        public static final RegistryObject<Item> PURPLE_ALLAY = ITEMS.register("purple_allay", () -> new PurpleAllay(new Properties().stacksTo(1)));
        public static final RegistryObject<Item> LEAF = ITEMS.register("leaf", () -> new Item(new Properties()));
        public static final RegistryObject<Item> FUR = ITEMS.register("racoon_fur", () -> new Item(new Properties()));
        public static final RegistryObject<Item> ROLY_POLY_PLAQUE = ITEMS.register("roly_plaque", () -> new Item(new Properties()));

        public static final RegistryObject<Item> EGG_RESTS = ITEMS.register("egg_rests", () -> new Item(new Properties()));
        public static final RegistryObject<Item> QUETZAL_MEAT = ITEMS.register("quetzal_meat", () -> new Item(new Properties().food(Foods.BEEF)));
        public static final RegistryObject<Item> COOKED_QUETZAL_MEAT = ITEMS.register("cooked_quetzal_meat", () -> new Item(new Properties().food(Foods.COOKED_BEEF)));

        public static final RegistryObject<Item> GRASSHOPPER_SADDLE = ITEMS.register("grasshopper_saddle", () -> new Item(new Properties().stacksTo(1)));
        public static final RegistryObject<Item> CRESTED_GECKO_SADDLE = ITEMS.register("crested_gecko_saddle", () -> new Item(new Properties().stacksTo(1)));
        public static final RegistryObject<Item> ROLYPOLY_SADDLE = ITEMS.register("roly_poly_saddle", () -> new Item(new Properties().stacksTo(1)));
        public static final RegistryObject<Item> CHIKOTE_SADDLE = ITEMS.register("chikote_saddle", () -> new Item(new Properties().stacksTo(1)));
        public static final RegistryObject<Item> QUETZAL_SADDLE = ITEMS.register("quetzal_saddle", () -> new Item(new Properties().stacksTo(1)));
        public static final RegistryObject<Item> QUETZAL_STAND = ITEMS.register("quetzal_stand", () -> new Item(new Properties().stacksTo(1)));


        //spawn eggs
        public static final RegistryObject<ForgeSpawnEggItem> RACOON_SPAWN_EGG = ITEMS.register("racoon_spawn_egg", () -> new ForgeSpawnEggItem(
                EntityIinit.TAMEABLE_RACOON, 0xA8846E, 0x5D4130, new Properties()));

        public static final RegistryObject<ForgeSpawnEggItem> PENGUIN_SPAWN_EGG = ITEMS.register("penguin_spawn_egg", () -> new ForgeSpawnEggItem(
                EntityIinit.TAMEABLE_PENGUIN, 0x080A27, 0xECEDF6, new Properties()));

        public static final RegistryObject<ForgeSpawnEggItem> CHIKOTE_SPAWN_EGG = ITEMS.register("chikote_spawn_egg", () -> new ForgeSpawnEggItem(
                EntityIinit.TAMEABLE_CHIKOTE, 0xFFF38E, 0xDCB834, new Properties()));
    
        public static final RegistryObject<ForgeSpawnEggItem> BEETLE_SPAWN_EGG = ITEMS.register("beetle_spawn_egg", () -> new ForgeSpawnEggItem(
                EntityIinit.TAMEABLE_BEETLE, 0x224E79, 0x3991A9, new Properties()));

        public static final RegistryObject<ForgeSpawnEggItem> QUETZAL_SPAWN_EGG = ITEMS.register("quetzal_spawn_egg", () -> new ForgeSpawnEggItem(
                EntityIinit.QUETZALCOATLUS, 0x743B62, 0xAC786E, new Properties()));

        public static final RegistryObject<ForgeSpawnEggItem> GIANT_GRASSHOPPER_SPAWN_EGG = ITEMS.register("giant_grasshopper_spawn_egg", () -> new ForgeSpawnEggItem(
                EntityIinit.GIANT_GRASSHOPPER, 0xC5E152, 0x7EBB27, new Properties()));

        public static final RegistryObject<ForgeSpawnEggItem> GROUND_BEETLE_SPAWN_EGG = ITEMS.register("ground_beetle_spawn_egg", () -> new ForgeSpawnEggItem(
                EntityIinit.TAMEABLE_GROUND_BEETLE, 0x9E75D3, 0x7346AC, new Properties()));

        public static final RegistryObject<ForgeSpawnEggItem> GROUND_ROLY_POLY_SPAWN_EGG = ITEMS.register("giant_roly_poly_spawn_egg", () -> new ForgeSpawnEggItem(
                EntityIinit.GIANT_ROLY_POLY, 0x6E77B8, 0x3A4072, new Properties()));

        public static final RegistryObject<ForgeSpawnEggItem> SCARECROW_SPAWN_EGG = ITEMS.register("scarecrow_spawn_egg", () -> new ForgeSpawnEggItem(
                EntityIinit.SCARECROW_ALLAY, 0xFFC347, 0x6A5ACD, new Properties()));

        public static final RegistryObject<ForgeSpawnEggItem> CRESTED_GECKO_SPAWN_EGG = ITEMS.register("crested_gecko_spawn_egg", () -> new ForgeSpawnEggItem(
                EntityIinit.CRESTED_GECKO, 0x728452, 0xC9B96A, new Properties()));


        //hats
        public static final RegistryObject<Item> SCARECROW_STRAW_HAT = ITEMS.register("scarecrow_straw_hat", () -> new HatItem(new Properties().stacksTo(1)));
        public static final RegistryObject<Item> FLYING_HELMET = ITEMS.register("flying_helmet", () -> new HatItem(new Properties().stacksTo(1)));
        public static final RegistryObject<Item> BIKER_HELMET = ITEMS.register("biker_helmet", () -> new HatItem(new Properties().stacksTo(1)));
        public static final RegistryObject<Item> RACOON_HAT = ITEMS.register("racoon_hat", () -> new HatItem(new Properties().stacksTo(1)));

        //blocks
        public static final RegistryObject<Item> QUETZAL_EGG_ITEM = ITEMS.register("quetzalcoatlus_egg",
                () -> new EggBlockItem(BlockInit.QUETZAL_EGG_BLOCK.get(), new Properties().stacksTo(1)));

        public static final RegistryObject<Item> CHIKOTE_EGG_ITEM = ITEMS.register("chikote_egg",
                () -> new EggBlockItem(BlockInit.CHIKOTE_EGG_BLOCK.get(), new Properties().stacksTo(1)));

        public static final RegistryObject<Item> ROLY_POLY_EGG_ITEM = ITEMS.register("roly_poly_egg",
                () -> new EggBlockItem(BlockInit.ROLY_POLY_EGG_BLOCK.get(), new Properties().stacksTo(1)));

        public static final RegistryObject<Item> FLYING_BEETLE_EGG_ITEM = ITEMS.register("beetle_egg",
                () -> new EggBlockItem(BlockInit.FLYING_BEETLE_EGG_BLOCK.get(), new Properties().stacksTo(1)));

        public static final RegistryObject<Item> GROUND_BEETLE_EGG_ITEM = ITEMS.register("ground_beetle_egg",
                () -> new EggBlockItem(BlockInit.GROUND_BEETLE_EGG_BLOCK.get(), new Properties().stacksTo(1)));

        public static final RegistryObject<Item> GRASSHOPPER_EGG_ITEM = ITEMS.register("grasshopper_egg",
                () -> new EggBlockItem(BlockInit.GRASSHOPPER_EGG_BLOCK.get(), new Properties().stacksTo(1)));

        public static final RegistryObject<Item> PENGUIN_EGG_ITEM = ITEMS.register("penguin_egg",
                () -> new EggBlockItem(BlockInit.PENGUIN_EGG_BLOCK.get(), new Properties().stacksTo(1)));

        //tools
        public static final RegistryObject<Item> IRON_BIG_HOE = ITEMS.register("iron_big_hoe", () -> new HoeItem(Tiers.IRON, -1, -1.0F, (new Properties())));
    }