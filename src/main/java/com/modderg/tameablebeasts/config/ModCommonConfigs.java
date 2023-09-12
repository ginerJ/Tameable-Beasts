package com.modderg.tameablebeasts.config;

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
    public static final ForgeConfigSpec.ConfigValue<Boolean> CAN_SPAWN_RACOON;

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

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}