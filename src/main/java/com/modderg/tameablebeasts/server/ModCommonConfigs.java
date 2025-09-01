package com.modderg.tameablebeasts.server;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModCommonConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> CAN_SPAWN_CHIKOTE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> CAN_SPAWN_FLYING_BEETLE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> CAN_SPAWN_ROLY_POLY;
    public static final ForgeConfigSpec.ConfigValue<Boolean> CAN_SPAWN_GRASSHOPPER;
    public static final ForgeConfigSpec.ConfigValue<Boolean> CAN_SPAWN_GROUND_BEETLE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> CAN_SPAWN_PENGUIN;
    public static final ForgeConfigSpec.ConfigValue<Boolean> CAN_SPAWN_QUETZAL;
    public static final ForgeConfigSpec.ConfigValue<Boolean> CAN_SPAWN_ARGENTAVIS;
    public static final ForgeConfigSpec.ConfigValue<Boolean> CAN_SPAWN_RACOON;
    public static final ForgeConfigSpec.ConfigValue<Boolean> CAN_SPAWN_GRAPTERA;
    public static final ForgeConfigSpec.ConfigValue<Boolean> CAN_SPAWN_CRESTED_GECKO;

    public static final ForgeConfigSpec.ConfigValue<Integer> ARGENTAVIS_SPAWN_HEIGHT;
    public static final ForgeConfigSpec.ConfigValue<Integer> QUETZAL_SPAWN_HEIGHT;
    public static final ForgeConfigSpec.ConfigValue<Integer> GRAPTERA_SPAWN_HEIGHT;

    public static final ForgeConfigSpec.ConfigValue<Integer> MIN_IRON_TRANSFORMS_GROUND_BEETLE;

    public static final ForgeConfigSpec.ConfigValue<Integer> ARTHROPOD_TRIM_DROP_CHANCE;


    static {
        BUILDER.push("Tameable Beasts Configs");

        CAN_SPAWN_CHIKOTE = BUILDER.comment("Spawn Chikote")
                .define("Chikotes spawning enabled:", true);

        CAN_SPAWN_FLYING_BEETLE = BUILDER.comment("Spawn Flying Beetle")
                .define("Flying Beetles spawning enabled:", true);

        CAN_SPAWN_ROLY_POLY = BUILDER.comment("Spawn Roly Poly")
                .define("Roly Polys spawning enabled:", true);

        CAN_SPAWN_GRASSHOPPER = BUILDER.comment("Spawn Grasshopper")
                .define("Grasshoppers spawning enabled:", true);

        CAN_SPAWN_GROUND_BEETLE = BUILDER.comment("Spawn Ground Beetle")
                .define("Ground Beetles spawning enabled:", true);

        CAN_SPAWN_PENGUIN = BUILDER.comment("Spawn Penguin")
                .define("Penguins spawning enabled:", true);

        CAN_SPAWN_QUETZAL = BUILDER.comment("Spawn QuetzalCoatulus")
                .define("QuetzalCoatuluss spawning enabled:", true);

        CAN_SPAWN_RACOON = BUILDER.comment("Spawn Racoon")
                .define("Racoons spawning enabled:", true);

        CAN_SPAWN_CRESTED_GECKO = BUILDER.comment("Spawn Crested Gecko")
                .define("Crested Geckos spawning enabled:", true);

        CAN_SPAWN_ARGENTAVIS = BUILDER.comment("Spawn Argentavis")
                .define("Argentavis spawning enabled:", true);

        CAN_SPAWN_GRAPTERA = BUILDER.comment("Spawn Grapteranodons")
                .define("Grapteranodons spawning enabled:", true);


        ARGENTAVIS_SPAWN_HEIGHT = BUILDER.comment("Argentavis Spawn Height")
                .define("Argentavis minimum spawn height:", 120);

        QUETZAL_SPAWN_HEIGHT = BUILDER.comment("Quetzal Spawn Height")
                .define("Quetzal minimum spawn height:", 140);

        GRAPTERA_SPAWN_HEIGHT = BUILDER.comment("Graptera Spawn Height")
                .define("Graptera minimum spawn height:", 100);


        MIN_IRON_TRANSFORMS_GROUND_BEETLE = BUILDER.comment("Raw Iron to Transform Ground Beetle to Metal Beetle")
                .define("Minimum amount of Raw Iron that transforms Ground Beetle:", 64);


        ARTHROPOD_TRIM_DROP_CHANCE = BUILDER.comment("Arthropod Trim Drop Chance")
                .define("Chance (over 100) all arthropods have of dropping Arthropod Upgrade Smithing Template upon defeat:", 2);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}