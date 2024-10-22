package com.modderg.tameablebeasts.server.item;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.server.block.BlockInit;
import com.modderg.tameablebeasts.server.entity.EntityInit;
import com.modderg.tameablebeasts.server.item.block.EggBlockItem;
import com.modderg.tameablebeasts.server.item.block.HatItem;
import com.modderg.tameablebeasts.server.item.custom.AsphaltItem;
import com.modderg.tameablebeasts.server.item.custom.PenguinArmor;
import com.modderg.tameablebeasts.server.item.custom.PurpleAllay;
import com.modderg.tameablebeasts.server.projectiles.BirdBaitTameArrow;
import com.modderg.tameablebeasts.server.projectiles.PteraMealTameArrow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class ItemInit {
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TameableBeast.MOD_ID);

        //misc
        public static final RegistryObject<Item> ICEPOP = ITEMS.register("icepop", () -> new Item(new Properties().food(Foods.SWEET_BERRIES)));
        public static final RegistryObject<Item> ICE_HELMET = ITEMS.register("ice_helmet", () -> new PenguinArmor(new Properties().stacksTo(1)));
        public static final RegistryObject<Item> ICE_CHESTPLATE = ITEMS.register("ice_chestplate", () -> new PenguinArmor(new Properties().stacksTo(1)));
        public static final RegistryObject<Item> PURPLE_ALLAY = ITEMS.register("purple_allay", () -> new PurpleAllay(new Properties().stacksTo(1)));
        public static final RegistryObject<Item> LEAF = ITEMS.register("leaf", () -> new Item(new Properties()));
        public static final RegistryObject<Item> GRASSHOPPER_LEG = ITEMS.register("grasshopper_leg", () -> new Item(new Properties()));
        public static final RegistryObject<Item> BEETLE_DUST = ITEMS.register("beetle_dust", () -> new GlowInkSacItem(new Properties()));
        public static final RegistryObject<Item> FUR = ITEMS.register("racoon_fur", () -> new Item(new Properties()));
        public static final RegistryObject<Item> ROLY_POLY_PLAQUE = ITEMS.register("roly_plaque", () -> new Item(new Properties()));
        public static final RegistryObject<Item> ASPHALT = ITEMS.register("asphalt", () -> new AsphaltItem(new Properties()));

        public static final RegistryObject<Item> EGG_RESTS = ITEMS.register("egg_rests", () -> new Item(new Properties()));
        public static final RegistryObject<Item> QUETZAL_MEAT = ITEMS.register("quetzal_meat", () -> new Item(new Properties().food(Foods.BEEF)));
        public static final RegistryObject<Item> COOKED_QUETZAL_MEAT = ITEMS.register("cooked_quetzal_meat", () -> new Item(new Properties().food(Foods.COOKED_BEEF)));
        public static final RegistryObject<Item> BIG_BIRD_MEAT = ITEMS.register("big_bird_meat", () -> new Item(new Properties().food(Foods.BEEF)));
        public static final RegistryObject<Item> COOKED_BIG_BIRD_MEAT = ITEMS.register("cooked_big_bird_meat", () -> new Item(new Properties().food(Foods.COOKED_BEEF)));
        public static final RegistryObject<Item> BIG_BIRD_BAIT = ITEMS.register("big_bird_bait", () -> new Item(new Properties().food(Foods.COOKED_BEEF)));
        public static final RegistryObject<Item> PTERANODON_MEAL = ITEMS.register("pteranodon_meal", () -> new Item(new Properties().food(Foods.COOKED_CHICKEN)));
        public static final RegistryObject<Item> BUG_SALAD = ITEMS.register("bug_salad", () -> new Item(new Properties().food(Foods.BEETROOT_SOUP)));


        public static final RegistryObject<Item> GRASSHOPPER_SADDLE = ITEMS.register("grasshopper_saddle", () -> new Item(new Properties().stacksTo(1)));
        public static final RegistryObject<Item> CRESTED_GECKO_SADDLE = ITEMS.register("crested_gecko_saddle", () -> new Item(new Properties().stacksTo(1)));
        public static final RegistryObject<Item> ROLYPOLY_SADDLE = ITEMS.register("roly_poly_saddle", () -> new Item(new Properties().stacksTo(1)));
        public static final RegistryObject<Item> CHIKOTE_SADDLE = ITEMS.register("chikote_saddle", () -> new Item(new Properties().stacksTo(1)));
        public static final RegistryObject<Item> QUETZAL_SADDLE = ITEMS.register("quetzal_saddle", () -> new Item(new Properties().stacksTo(1)));
        public static final RegistryObject<Item> ARGENTAVIS_SADDLE = ITEMS.register("argentavis_saddle", () -> new Item(new Properties().stacksTo(1)));
        public static final RegistryObject<Item> QUETZAL_STAND = ITEMS.register("quetzal_stand", () -> new Item(new Properties().stacksTo(1)));


        //tools and weapons
        public static final RegistryObject<Item> IRON_BIG_HOE = ITEMS.register("iron_big_hoe", () -> new HoeItem(Tiers.IRON, -1, -1.0F, (new Properties())));
        public static final RegistryObject<Item> BUG_SWORD = ITEMS.register("bug_sword", () -> new SwordItem(Tiers.DIAMOND, 2, -2F, new Item.Properties()));


        //spawn eggs
        public static final RegistryObject<ForgeSpawnEggItem> RACOON_SPAWN_EGG = ITEMS.register("racoon_spawn_egg", () -> new ForgeSpawnEggItem(
                EntityInit.RACOON, 0xA8846E, 0x5D4130, new Properties()));

        public static final RegistryObject<ForgeSpawnEggItem> PENGUIN_SPAWN_EGG = ITEMS.register("penguin_spawn_egg", () -> new ForgeSpawnEggItem(
                EntityInit.PENGUIN, 0x080A27, 0xECEDF6, new Properties()));

        public static final RegistryObject<ForgeSpawnEggItem> CHIKOTE_SPAWN_EGG = ITEMS.register("chikote_spawn_egg", () -> new ForgeSpawnEggItem(
                EntityInit.CHIKOTE, 0xFFF38E, 0xDCB834, new Properties()));
    
        public static final RegistryObject<ForgeSpawnEggItem> BEETLE_SPAWN_EGG = ITEMS.register("beetle_spawn_egg", () -> new ForgeSpawnEggItem(
                EntityInit.FLYING_BEETLE, 0x224E79, 0x3991A9, new Properties()));

        public static final RegistryObject<ForgeSpawnEggItem> QUETZAL_SPAWN_EGG = ITEMS.register("quetzal_spawn_egg", () -> new ForgeSpawnEggItem(
                EntityInit.QUETZALCOATLUS, 0x743B62, 0xAC786E, new Properties()));

        public static final RegistryObject<ForgeSpawnEggItem> GIANT_GRASSHOPPER_SPAWN_EGG = ITEMS.register("giant_grasshopper_spawn_egg", () -> new ForgeSpawnEggItem(
                EntityInit.GIANT_GRASSHOPPER, 0xC5E152, 0x7EBB27, new Properties()));

        public static final RegistryObject<ForgeSpawnEggItem> GROUND_BEETLE_SPAWN_EGG = ITEMS.register("ground_beetle_spawn_egg", () -> new ForgeSpawnEggItem(
                EntityInit.GROUND_BEETLE, 0x9E75D3, 0x7346AC, new Properties()));

        public static final RegistryObject<ForgeSpawnEggItem> GROUND_ROLY_POLY_SPAWN_EGG = ITEMS.register("giant_roly_poly_spawn_egg", () -> new ForgeSpawnEggItem(
                EntityInit.GIANT_ROLY_POLY, 0x6E77B8, 0x3A4072, new Properties()));

        public static final RegistryObject<ForgeSpawnEggItem> SCARECROW_SPAWN_EGG = ITEMS.register("scarecrow_spawn_egg", () -> new ForgeSpawnEggItem(
                EntityInit.SCARECROW_ALLAY, 0xFFC347, 0x6A5ACD, new Properties()));

        public static final RegistryObject<ForgeSpawnEggItem> CRESTED_GECKO_SPAWN_EGG = ITEMS.register("crested_gecko_spawn_egg", () -> new ForgeSpawnEggItem(
                EntityInit.CRESTED_GECKO, 0x728452, 0xC9B96A, new Properties()));

        public static final RegistryObject<ForgeSpawnEggItem> ARGENTAVIS_SPAWN_EGG = ITEMS.register("argentavis_spawn_egg", () -> new ForgeSpawnEggItem(
                EntityInit.ARGENTAVIS, 0xECB67D, 0x86511A, new Properties()));

        public static final RegistryObject<ForgeSpawnEggItem> GRAPTERA_SPAWN_EGG = ITEMS.register("graptera_spawn_egg", () -> new ForgeSpawnEggItem(
                EntityInit.GRAPTERANODON, 0xC36B58, 0x828673, new Properties()));


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

        public static final RegistryObject<Item> CRESTED_GECKO_EGG_ITEM = ITEMS.register("crested_gecko_egg",
                () -> new EggBlockItem(BlockInit.CRESTED_GECKO_EGG_BLOCK.get(), new Properties().stacksTo(1)));

        public static final RegistryObject<Item> ARGENTAVIS_EGG_ITEM = ITEMS.register("argentavis_egg",
                () -> new EggBlockItem(BlockInit.ARGENTAVIS_EGG_BLOCK.get(), new Properties().stacksTo(1)));

        public static final RegistryObject<Item> GRAPTERANODON_EGG_ITEM = ITEMS.register("grapterandon_egg",
                () -> new EggBlockItem(BlockInit.GRAPTERANODON_EGG_BLOCK.get(), new Properties().stacksTo(1)));


        //weapon
        public static final RegistryObject<Item> BIRD_BAIT_ARROW = ITEMS.register("bird_bait_arrow", () -> new ArrowItem(new Properties()){
                        public @NotNull AbstractArrow createArrow(@NotNull Level p_40513_, @NotNull ItemStack p_40514_, @NotNull LivingEntity p_40515_) {
                                return new BirdBaitTameArrow(p_40513_, p_40515_);
                        }});

        public static final RegistryObject<Item> PTERA_MEAL_ARROW = ITEMS.register("ptera_meal_arrow", () -> new ArrowItem(new Properties()){
                        public @NotNull AbstractArrow createArrow(@NotNull Level p_40513_, @NotNull ItemStack p_40514_, @NotNull LivingEntity p_40515_) {
                                return new PteraMealTameArrow(p_40513_, p_40515_);
                        }});
}