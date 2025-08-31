package com.modderg.tameablebeasts.registry;

import com.modderg.tameablebeasts.TameableBeasts;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class TBTagRegistry {

    public static  class EntityTypes {

        private static final Function<String, TagKey<EntityType<?>>> tag = name ->
                TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(TameableBeasts.MOD_ID, name));

        public static final TagKey<EntityType<?>> TAMED_BY_PTERA_MEAL_ARROW = tag.apply("tamed_by_ptera_meal_arrow");

        public static final TagKey<EntityType<?>> TAMED_BY_BIRD_BAIT_ARROW = tag.apply("tamed_by_bird_bait_arrow");
    }

    public static  class Items{

        private static final Function<String, TagKey<Item>> tag = name ->
                TagKey.create(Registries.ITEM, new ResourceLocation(TameableBeasts.MOD_ID, name));

        public static final TagKey<Item> ENCHANTABLE_SWARM = tag.apply("enchantable_swarm");
        public static final TagKey<Item> ENCHANTABLE_STING = tag.apply("enchantable_sting");

        public static final TagKey<Item> ARGENTAVIS_FOOD = tag.apply("argentavis_food");
        public static final TagKey<Item> ARGENTAVIS_TAME_FOOD = tag.apply("argentavis_tame_food");

        public static final TagKey<Item> CHIKOTE_FOOD = tag.apply("chikote_food");
        public static final TagKey<Item> CHIKOTE_TAME_FOOD = tag.apply("chikote_tame_food");

        public static final TagKey<Item> CRESTED_GECKO_FOOD = tag.apply("crested_gecko_food");
        public static final TagKey<Item> CRESTED_GECKO_TAME_FOOD = tag.apply("crested_gecko_tame_food");

        public static final TagKey<Item> GRAPTERA_FOOD = tag.apply("graptera_food");
        public static final TagKey<Item> GRAPTERA_TAME_FOOD = tag.apply("graptera_tame_food");

        public static final TagKey<Item> GRASSHOPPER_FOOD = tag.apply("grasshopper_food");
        public static final TagKey<Item> GRASSHOPPER_TAME_FOOD = tag.apply("grasshopper_tame_food");

        public static final TagKey<Item> GROUND_BEETLE_FOOD = tag.apply("ground_beetle_food");
        public static final TagKey<Item> GROUND_BEETLE_METAL_FOOD = tag.apply("ground_beetle_metal_food");
        public static final TagKey<Item> GROUND_BEETLE_TAME_FOOD = tag.apply("ground_beetle_tame_food");

        public static final TagKey<Item> PENGUIN_FOOD = tag.apply("penguin_food");
        public static final TagKey<Item> PENGUIN_TAME_FOOD = tag.apply("penguin_tame_food");

        public static final TagKey<Item> QUETZAL_FOOD = tag.apply("quetzal_food");
        public static final TagKey<Item> QUETZAL_TAME_FOOD = tag.apply("quetzal_tame_food");

        public static final TagKey<Item> RACOON_FOOD = tag.apply("racoon_food");
        public static final TagKey<Item> RACOON_TAME_FOOD = tag.apply("racoon_tame_food");

        public static final TagKey<Item> ROLY_POLY_FOOD = tag.apply("roly_poly_food");
        public static final TagKey<Item> ROLY_POLY_TAME_FOOD = tag.apply("roly_poly_tame_food");

        public static final TagKey<Item> SCARECROW_FOOD = tag.apply("scarecrow_food");

        public static final TagKey<Item> SHINY_BEETLE_FOOD = tag.apply("shiny_beetle_food");
        public static final TagKey<Item> SHINY_BEETLE_TAME_FOOD = tag.apply("shiny_beetle_tame_food");

    }
}
