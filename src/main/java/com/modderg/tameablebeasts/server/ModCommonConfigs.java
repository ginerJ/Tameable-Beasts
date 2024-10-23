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

    static {
        BUILDER.push("Tameable Beasts Configs");

        CAN_SPAWN_CHIKOTE = BUILDER.comment("Spawn Chikote")
                .define("Chikotes enabled:", true);

        CAN_SPAWN_FLYING_BEETLE = BUILDER.comment("Spawn Flying Beetle")
                .define("Flying Beetles enabled:", true);

        CAN_SPAWN_ROLY_POLY = BUILDER.comment("Spawn Roly Poly")
                .define("Roly Polys enabled:", true);

        CAN_SPAWN_GRASSHOPPER = BUILDER.comment("Spawn Grasshopper")
                .define("Grasshoppers enabled:", true);

        CAN_SPAWN_GROUND_BEETLE = BUILDER.comment("Spawn Ground Beetle")
                .define("Ground Beetles enabled:", true);

        CAN_SPAWN_PENGUIN = BUILDER.comment("Spawn Penguin")
                .define("Penguins enabled:", true);

        CAN_SPAWN_QUETZAL = BUILDER.comment("Spawn QuetzalCoatulus")
                .define("QuetzalCoatuluss enabled:", true);

        CAN_SPAWN_RACOON = BUILDER.comment("Spawn Racoon")
                .define("Racoons enabled:", true);

        CAN_SPAWN_CRESTED_GECKO = BUILDER.comment("Spawn Crested Gecko")
                .define("Crested Geckos enabled:", true);

        CAN_SPAWN_ARGENTAVIS = BUILDER.comment("Spawn Argentavis")
                .define("Argentavis enabled:", true);

        CAN_SPAWN_GRAPTERA = BUILDER.comment("Spawn Grapteranodons")
                .define("Grapteranodons enabled:", true);


        ARGENTAVIS_SPAWN_HEIGHT = BUILDER.comment("Argentavis Spawn Height")
                .define("Argentavis minimum spawn height:", 100);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}