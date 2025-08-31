package com.modderg.tameablebeasts.registry;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.server.item.BugArmorHelmet;
import com.modderg.tameablebeasts.server.item.TBShieldItem;
import com.modderg.tameablebeasts.server.item.block.EggBlockItem;
import com.modderg.tameablebeasts.server.item.block.HatItem;
import com.modderg.tameablebeasts.server.item.AsphaltItem;
import com.modderg.tameablebeasts.server.item.PurpleAllay;
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

public class TBItemRegistry {
        public static final DeferredRegister<Item> TB_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TameableBeasts.MOD_ID);

        //misc
        public static final RegistryObject<Item> ICEPOP = TB_ITEMS.register("icepop",
                () -> new Item(new Properties().food(Foods.SWEET_BERRIES)));

        public static final RegistryObject<Item> ICE_HELMET = TB_ITEMS.register("ice_helmet",
                () -> new Item(new Properties().stacksTo(1)));

        public static final RegistryObject<Item> ICE_CHESTPLATE = TB_ITEMS.register("ice_chestplate",
                () -> new Item(new Properties().stacksTo(1)));

        public static final RegistryObject<Item> PURPLE_ALLAY = TB_ITEMS.register("purple_allay",
                () -> new PurpleAllay(new Properties().stacksTo(1)));

        public static final RegistryObject<Item> LEAF = TB_ITEMS.register("leaf",
                () -> new Item(new Properties()));

        public static final RegistryObject<Item> GRASSHOPPER_LEG = TB_ITEMS.register("grasshopper_leg",
                () -> new Item(new Properties()));

        public static final RegistryObject<Item> BEETLE_DUST = TB_ITEMS.register("beetle_dust",
                () -> new GlowInkSacItem(new Properties()));

        public static final RegistryObject<Item> FUR = TB_ITEMS.register("racoon_fur",
                () -> new Item(new Properties()));

        public static final RegistryObject<Item> ROLY_POLY_PLAQUE = TB_ITEMS.register("roly_plaque",
                () -> new Item(new Properties()));

        public static final RegistryObject<Item> BEETLE_ARMOR_PIECE = TB_ITEMS.register("beetle_armor_piece",
                () -> new Item(new Properties()));

        public static final RegistryObject<Item> ASPHALT = TB_ITEMS.register("asphalt",
                () -> new AsphaltItem(new Properties()));

        public static final RegistryObject<Item> EGG_RESTS = TB_ITEMS.register("egg_rests",
                () -> new Item(new Properties()));

        public static final RegistryObject<Item> QUETZAL_MEAT = TB_ITEMS.register("quetzal_meat",
                () -> new Item(new Properties().food(Foods.BEEF)));

        public static final RegistryObject<Item> COOKED_QUETZAL_MEAT = TB_ITEMS.register("cooked_quetzal_meat",
                () -> new Item(new Properties().food(Foods.COOKED_BEEF)));

        public static final RegistryObject<Item> BIG_BIRD_MEAT = TB_ITEMS.register("big_bird_meat",
                () -> new Item(new Properties().food(Foods.BEEF)));

        public static final RegistryObject<Item> COOKED_BIG_BIRD_MEAT = TB_ITEMS.register("cooked_big_bird_meat",
                () -> new Item(new Properties().food(Foods.COOKED_BEEF)));

        public static final RegistryObject<Item> BIG_BIRD_BAIT = TB_ITEMS.register("big_bird_bait",
                () -> new Item(new Properties().food(Foods.COOKED_BEEF)));

        public static final RegistryObject<Item> PTERANODON_MEAL = TB_ITEMS.register("pteranodon_meal",
                () -> new Item(new Properties().food(Foods.COOKED_CHICKEN)));

        public static final RegistryObject<Item> BUG_SALAD = TB_ITEMS.register("bug_salad",
                () -> new Item(new Properties().food(Foods.BEETROOT_SOUP)));

        public static final RegistryObject<Item> QUETZAL_STAND = TB_ITEMS.register("quetzal_stand",
                () -> new Item(new Properties().stacksTo(1)));


        //tools and weapons

        public static final RegistryObject<Item> IRON_BIG_HOE = TB_ITEMS.register("iron_big_hoe",
                () -> new HoeItem(Tiers.IRON, -1, -1.0F, (new Properties())));

        public static final RegistryObject<Item> BUG_SWORD = TB_ITEMS.register("bug_sword",
                () -> new SwordItem(Tiers.DIAMOND, 2, -2F, new Item.Properties()));

        public static final RegistryObject<Item> IRON_SHIELD = TB_ITEMS.register("metal_shield",
                () -> new TBShieldItem((new Item.Properties()).durability(448), Items.RAW_IRON));

        public static final RegistryObject<Item> BEETLE_GEM = TB_ITEMS.register("beetle_gem",
                () -> new Item(new Properties()));

        public static final RegistryObject<Item> ARTHROPOD_SMITHING_TEMPLATE = TB_ITEMS.register("arthropod_upgrade_smithing_template",
                () -> new Item(new Properties()));

        public static final RegistryObject<Item> BUG_ARMOR_HELMET = TB_ITEMS.register("bug_armor_helmet",
                () -> new BugArmorHelmet(TBArmorMaterials.BUG_ARMOR, ArmorItem.Type.HELMET, new Properties()));

        public static final RegistryObject<Item> BUG_ARMOR_CHESTPLATE = TB_ITEMS.register("bug_armor_chestplate",
                () -> new ArmorItem(TBArmorMaterials.BUG_ARMOR, ArmorItem.Type.CHESTPLATE, new Properties()));

        public static final RegistryObject<Item> BUG_ARMOR_LEGGINGS = TB_ITEMS.register("bug_armor_leggings",
                () -> new ArmorItem(TBArmorMaterials.BUG_ARMOR, ArmorItem.Type.LEGGINGS, new Properties()));

        public static final RegistryObject<Item> BUG_ARMOR_BOOTS = TB_ITEMS.register("bug_armor_boots",
                () -> new ArmorItem(TBArmorMaterials.BUG_ARMOR, ArmorItem.Type.BOOTS, new Properties()));

        public static final RegistryObject<Item> BEETLE_SHIELD = TB_ITEMS.register("beetle_shield",
                () -> new TBShieldItem((new Item.Properties()).durability(512), TBArmorMaterials.BUG_ARMOR.getRepairIngredient().getItems()));

        public static final RegistryObject<Item> BEETLE_SWORD = TB_ITEMS.register("beetle_sword",
                () -> new SwordItem(TBTierRegistry.BEETLE_GEM, 2, -1.8F, new Item.Properties()));


        //spawn eggs
        public static final RegistryObject<ForgeSpawnEggItem> RACOON_SPAWN_EGG = TB_ITEMS.register("racoon_spawn_egg", () -> new ForgeSpawnEggItem(
                TBEntityRegistry.RACOON, 0xA8846E, 0x5D4130, new Properties()));

        public static final RegistryObject<ForgeSpawnEggItem> PENGUIN_SPAWN_EGG = TB_ITEMS.register("penguin_spawn_egg", () -> new ForgeSpawnEggItem(
                TBEntityRegistry.PENGUIN, 0x080A27, 0xECEDF6, new Properties()));

        public static final RegistryObject<ForgeSpawnEggItem> CHIKOTE_SPAWN_EGG = TB_ITEMS.register("chikote_spawn_egg", () -> new ForgeSpawnEggItem(
                TBEntityRegistry.CHIKOTE, 0xFFF38E, 0xDCB834, new Properties()));
    
        public static final RegistryObject<ForgeSpawnEggItem> BEETLE_SPAWN_EGG = TB_ITEMS.register("beetle_spawn_egg", () -> new ForgeSpawnEggItem(
                TBEntityRegistry.FLYING_BEETLE, 0x224E79, 0x3991A9, new Properties()));

        public static final RegistryObject<ForgeSpawnEggItem> QUETZAL_SPAWN_EGG = TB_ITEMS.register("quetzal_spawn_egg", () -> new ForgeSpawnEggItem(
                TBEntityRegistry.QUETZALCOATLUS, 0x743B62, 0xAC786E, new Properties()));

        public static final RegistryObject<ForgeSpawnEggItem> GIANT_GRASSHOPPER_SPAWN_EGG = TB_ITEMS.register("giant_grasshopper_spawn_egg", () -> new ForgeSpawnEggItem(
                TBEntityRegistry.GIANT_GRASSHOPPER, 0xC5E152, 0x7EBB27, new Properties()));

        public static final RegistryObject<ForgeSpawnEggItem> GROUND_BEETLE_SPAWN_EGG = TB_ITEMS.register("ground_beetle_spawn_egg", () -> new ForgeSpawnEggItem(
                TBEntityRegistry.GROUND_BEETLE, 0x9E75D3, 0x7346AC, new Properties()));

        public static final RegistryObject<ForgeSpawnEggItem> GROUND_ROLY_POLY_SPAWN_EGG = TB_ITEMS.register("giant_roly_poly_spawn_egg", () -> new ForgeSpawnEggItem(
                TBEntityRegistry.GIANT_ROLY_POLY, 0x6E77B8, 0x3A4072, new Properties()));

        public static final RegistryObject<ForgeSpawnEggItem> SCARECROW_SPAWN_EGG = TB_ITEMS.register("scarecrow_spawn_egg", () -> new ForgeSpawnEggItem(
                TBEntityRegistry.SCARECROW_ALLAY, 0xFFC347, 0x6A5ACD, new Properties()));

        public static final RegistryObject<ForgeSpawnEggItem> CRESTED_GECKO_SPAWN_EGG = TB_ITEMS.register("crested_gecko_spawn_egg", () -> new ForgeSpawnEggItem(
                TBEntityRegistry.CRESTED_GECKO, 0x728452, 0xC9B96A, new Properties()));

        public static final RegistryObject<ForgeSpawnEggItem> ARGENTAVIS_SPAWN_EGG = TB_ITEMS.register("argentavis_spawn_egg", () -> new ForgeSpawnEggItem(
                TBEntityRegistry.ARGENTAVIS, 0xECB67D, 0x86511A, new Properties()));

        public static final RegistryObject<ForgeSpawnEggItem> GRAPTERA_SPAWN_EGG = TB_ITEMS.register("graptera_spawn_egg", () -> new ForgeSpawnEggItem(
                TBEntityRegistry.GRAPTERANODON, 0xC36B58, 0x828673, new Properties()));


        //hats
        public static final RegistryObject<Item> SCARECROW_STRAW_HAT = TB_ITEMS.register("scarecrow_straw_hat",
                () -> new HatItem(new Properties().stacksTo(1)));

        public static final RegistryObject<Item> FLYING_HELMET = TB_ITEMS.register("flying_helmet",
                () -> new HatItem(new Properties().stacksTo(1)));

        public static final RegistryObject<Item> BIKER_HELMET = TB_ITEMS.register("biker_helmet",
                () -> new HatItem(new Properties().stacksTo(1)));

        public static final RegistryObject<Item> RACOON_HAT = TB_ITEMS.register("racoon_hat",
                () -> new HatItem(new Properties().stacksTo(1)));


        //blocks
        public static final RegistryObject<Item> QUETZAL_EGG_ITEM = TB_ITEMS.register("quetzalcoatlus_egg",
                () -> new EggBlockItem(TBBlockRegistry.QUETZAL_EGG_BLOCK.get(), new Properties().stacksTo(1)));

        public static final RegistryObject<Item> CHIKOTE_EGG_ITEM = TB_ITEMS.register("chikote_egg",
                () -> new EggBlockItem(TBBlockRegistry.CHIKOTE_EGG_BLOCK.get(), new Properties().stacksTo(1)));

        public static final RegistryObject<Item> ROLY_POLY_EGG_ITEM = TB_ITEMS.register("roly_poly_egg",
                () -> new EggBlockItem(TBBlockRegistry.ROLY_POLY_EGG_BLOCK.get(), new Properties().stacksTo(1)));

        public static final RegistryObject<Item> FLYING_BEETLE_EGG_ITEM = TB_ITEMS.register("beetle_egg",
                () -> new EggBlockItem(TBBlockRegistry.FLYING_BEETLE_EGG_BLOCK.get(), new Properties().stacksTo(1)));

        public static final RegistryObject<Item> GROUND_BEETLE_EGG_ITEM = TB_ITEMS.register("ground_beetle_egg",
                () -> new EggBlockItem(TBBlockRegistry.GROUND_BEETLE_EGG_BLOCK.get(), new Properties().stacksTo(1)));

        public static final RegistryObject<Item> GRASSHOPPER_EGG_ITEM = TB_ITEMS.register("grasshopper_egg",
                () -> new EggBlockItem(TBBlockRegistry.GRASSHOPPER_EGG_BLOCK.get(), new Properties().stacksTo(1)));

        public static final RegistryObject<Item> PENGUIN_EGG_ITEM = TB_ITEMS.register("penguin_egg",
                () -> new EggBlockItem(TBBlockRegistry.PENGUIN_EGG_BLOCK.get(), new Properties().stacksTo(1)));

        public static final RegistryObject<Item> CRESTED_GECKO_EGG_ITEM = TB_ITEMS.register("crested_gecko_egg",
                () -> new EggBlockItem(TBBlockRegistry.CRESTED_GECKO_EGG_BLOCK.get(), new Properties().stacksTo(1)));

        public static final RegistryObject<Item> ARGENTAVIS_EGG_ITEM = TB_ITEMS.register("argentavis_egg",
                () -> new EggBlockItem(TBBlockRegistry.ARGENTAVIS_EGG_BLOCK.get(), new Properties().stacksTo(1)));

        public static final RegistryObject<Item> GRAPTERANODON_EGG_ITEM = TB_ITEMS.register("grapterandon_egg",
                () -> new EggBlockItem(TBBlockRegistry.GRAPTERANODON_EGG_BLOCK.get(), new Properties().stacksTo(1)));


        //weapon
        public static final RegistryObject<Item> BIRD_BAIT_ARROW = TB_ITEMS.register("bird_bait_arrow", () -> new ArrowItem(new Properties()){
                        public @NotNull AbstractArrow createArrow(@NotNull Level p_40513_, @NotNull ItemStack p_40514_, @NotNull LivingEntity p_40515_) {
                                return new BirdBaitTameArrow(p_40513_, p_40515_);
                        }});

        public static final RegistryObject<Item> PTERA_MEAL_ARROW = TB_ITEMS.register("ptera_meal_arrow", () -> new ArrowItem(new Properties()){
                        public @NotNull AbstractArrow createArrow(@NotNull Level p_40513_, @NotNull ItemStack p_40514_, @NotNull LivingEntity p_40515_) {
                                return new PteraMealTameArrow(p_40513_, p_40515_);
                        }});
}