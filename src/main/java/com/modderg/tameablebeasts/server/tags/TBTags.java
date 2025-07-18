package com.modderg.tameablebeasts.server.tags;

import com.modderg.tameablebeasts.TameableBeasts;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class TBTags {

    public static  class EntityTypes {
        public static final TagKey<EntityType<?>> TAMED_BY_PTERA_MEAL_ARROW = tag("tamed_by_ptera_meal_arrow");

        public static final TagKey<EntityType<?>> TAMED_BY_BIRD_BAIT_ARROW = tag("tamed_by_bird_bait_arrow");

        private static TagKey<EntityType<?>> tag(String name){
            return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(TameableBeasts.MOD_ID, name));
        }
    }

    public static  class Items{
        public static final TagKey<Item> ARGENTAVIS_FOOD = tag("argentavis_food");
        public static final TagKey<Item> ARGENTAVIS_TAME_FOOD = tag("argentavis_tame_food");

        public static final TagKey<Item> CHIKOTE_FOOD = tag("chikote_food");
        public static final TagKey<Item> CHIKOTE_TAME_FOOD = tag("chikote_tame_food");

        public static final TagKey<Item> CRESTED_GECKO_FOOD = tag("crested_gecko_food");
        public static final TagKey<Item> CRESTED_GECKO_TAME_FOOD = tag("crested_gecko_tame_food");

        public static final TagKey<Item> GRAPTERA_FOOD = tag("graptera_food");
        public static final TagKey<Item> GRAPTERA_TAME_FOOD = tag("graptera_tame_food");

        public static final TagKey<Item> GRASSHOPPER_FOOD = tag("grasshopper_food");
        public static final TagKey<Item> GRASSHOPPER_TAME_FOOD = tag("grasshopper_tame_food");

        public static final TagKey<Item> GROUND_BEETLE_FOOD = tag("ground_beetle_food");
        public static final TagKey<Item> GROUND_BEETLE_TAME_FOOD = tag("ground_beetle_tame_food");

        public static final TagKey<Item> PENGUIN_FOOD = tag("penguin_food");
        public static final TagKey<Item> PENGUIN_TAME_FOOD = tag("penguin_tame_food");

        public static final TagKey<Item> QUETZAL_FOOD = tag("quetzal_food");
        public static final TagKey<Item> QUETZAL_TAME_FOOD = tag("quetzal_tame_food");

        public static final TagKey<Item> RACOON_FOOD = tag("racoon_food");
        public static final TagKey<Item> RACOON_TAME_FOOD = tag("racoon_tame_food");

        public static final TagKey<Item> ROLY_POLY_FOOD = tag("roly_poly_food");
        public static final TagKey<Item> ROLY_POLY_TAME_FOOD = tag("roly_poly_tame_food");

        public static final TagKey<Item> SCARECROW_FOOD = tag("scarecrow_food");

        public static final TagKey<Item> SHINY_BEETLE_FOOD = tag("shiny_beetle_food");
        public static final TagKey<Item> SHINY_BEETLE_TAME_FOOD = tag("shiny_beetle_tame_food");

        private static TagKey<Item> tag(String name){
            return ItemTags.create(new ResourceLocation(TameableBeasts.MOD_ID, name));
        }
    }
}
