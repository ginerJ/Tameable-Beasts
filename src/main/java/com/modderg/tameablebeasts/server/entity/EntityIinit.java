package com.modderg.tameablebeasts.server.entity;
import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.server.entity.custom.*;
import com.modderg.tameablebeasts.server.projectiles.BirdBaitTameArrow;
import com.modderg.tameablebeasts.server.projectiles.PteraMealTameArrow;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EntityIinit {

    public static Map<String, RegistryObject<EntityType<?>>> beastsMap;

    public static void init() {
        List<RegistryObject<EntityType<?>>> types = ENTITY_TYPES.getEntries().stream().toList();
        List<String> names = ENTITY_TYPES.getEntries().stream().map(e -> e.getId().getPath()).toList();

        beastsMap = IntStream.range(0, names.size())
                .boxed()
                .collect(Collectors.toMap(names::get, types::get));
    }

    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(
            ForgeRegistries.ENTITY_TYPES, TameableBeast.MOD_ID);

    public static final RegistryObject<EntityType<RacoonEntity>> RACOON = ENTITY_TYPES.register("tameable_racoon",
            () -> EntityType.Builder.of(RacoonEntity:: new, MobCategory.CREATURE)
                    .sized(0.75f,0.75f)
                    .build(new ResourceLocation(TameableBeast.MOD_ID, "tameable_racoon").toString()));

    public static final RegistryObject<EntityType<PenguinEntity>> PENGUIN = ENTITY_TYPES.register("tameable_penguin",
            () -> EntityType.Builder.of(PenguinEntity:: new, MobCategory.CREATURE)
                    .sized(0.5f,0.95f)
                    .build(new ResourceLocation(TameableBeast.MOD_ID, "tameable_penguin").toString()));

    public static final RegistryObject<EntityType<ChikoteEntity>> CHIKOTE = ENTITY_TYPES.register("tameable_chikote",
            () -> EntityType.Builder.of(ChikoteEntity:: new, MobCategory.CREATURE)
                    .sized(1f,1.5f)
                    .build(new ResourceLocation(TameableBeast.MOD_ID, "tameable_chikote").toString()));

    public static final RegistryObject<EntityType<ScarecrowAllayEntity>> SCARECROW_ALLAY = ENTITY_TYPES.register("scarecrow_allay",
            () -> EntityType.Builder.of(ScarecrowAllayEntity:: new, MobCategory.CREATURE)
                    .sized(0.75f,1.75f)
                    .build(new ResourceLocation(TameableBeast.MOD_ID, "scarecrow_allay").toString()));

    public static final RegistryObject<EntityType<FlyingBeetleEntity>> FLYING_BEETLE = ENTITY_TYPES.register("tameable_beetle",
            () -> EntityType.Builder.of(FlyingBeetleEntity:: new, MobCategory.CREATURE)
                    .sized(0.75f,0.75f)
                    .build(new ResourceLocation(TameableBeast.MOD_ID, "tameable_beetle").toString()));

    public static final RegistryObject<EntityType<BeetleDrone>> BEETLE_DRONE = ENTITY_TYPES.register("beetle_drone",
            () -> EntityType.Builder.of(BeetleDrone:: new, MobCategory.CREATURE)
                    .sized(0.3f,0.3f)
                    .build(new ResourceLocation(TameableBeast.MOD_ID, "beetle_drone").toString()));

    public static final RegistryObject<EntityType<QuetzalcoatlusEntity>> QUETZALCOATLUS = ENTITY_TYPES.register("quetzalcoatlus",
                () -> EntityType.Builder.of(QuetzalcoatlusEntity:: new, MobCategory.CREATURE)
                    .sized(1f,1.6f)
                    .build(new ResourceLocation(TameableBeast.MOD_ID, "quetzalcoatlus").toString()));

    public static final RegistryObject<EntityType<GrasshopperEntity>> GIANT_GRASSHOPPER = ENTITY_TYPES.register("giant_grasshopper",
            () -> EntityType.Builder.of(GrasshopperEntity:: new, MobCategory.CREATURE)
                    .sized(1f,1f)
                    .build(new ResourceLocation(TameableBeast.MOD_ID, "giant_grasshopper").toString()));

    public static final RegistryObject<EntityType<GroundBeetleEntity>> GROUND_BEETLE = ENTITY_TYPES.register("ground_beetle",
            () -> EntityType.Builder.of(GroundBeetleEntity:: new, MobCategory.CREATURE)
                    .sized(0.75f,0.75f)
                    .build(new ResourceLocation(TameableBeast.MOD_ID, "ground_beetle").toString()));

    public static final RegistryObject<EntityType<RolyPolyEntity>> GIANT_ROLY_POLY = ENTITY_TYPES.register("giant_roly_poly",
            () -> EntityType.Builder.of(RolyPolyEntity:: new, MobCategory.CREATURE)
                    .sized(1f,1f)
                    .build(new ResourceLocation(TameableBeast.MOD_ID, "giant_roly_poly").toString()));

    public static final RegistryObject<EntityType<FurGolemEntity>> FUR_GOLEM = ENTITY_TYPES.register("fur_golem",
            () -> EntityType.Builder.of(FurGolemEntity:: new, MobCategory.CREATURE)
                    .sized(0.9f,1.65f)
                    .build(new ResourceLocation(TameableBeast.MOD_ID, "fur_golem").toString()));

    public static final RegistryObject<EntityType<CrestedGeckoEntity>> CRESTED_GECKO = ENTITY_TYPES.register("crested_gecko",
            () -> EntityType.Builder.of(CrestedGeckoEntity:: new, MobCategory.CREATURE)
                    .sized(1.5f,0.75f)
                    .build(new ResourceLocation(TameableBeast.MOD_ID, "crested_gecko").toString()));

    public static final RegistryObject<EntityType<ArgentavisEntity>> ARGENTAVIS = ENTITY_TYPES.register("argentavis",
            () -> EntityType.Builder.of(ArgentavisEntity:: new, MobCategory.CREATURE)
                    .sized(1f,1.3f)
                    .build(new ResourceLocation(TameableBeast.MOD_ID, "argentavis").toString()));

    public static final RegistryObject<EntityType<GrapteranodonEntity>> GRAPTERANODON = ENTITY_TYPES.register("graptera",
            () -> EntityType.Builder.of(GrapteranodonEntity:: new, MobCategory.CREATURE)
                    .sized(1f,1.3f)
                    .build(new ResourceLocation(TameableBeast.MOD_ID, "graptera").toString()));




    public static final RegistryObject<EntityType<BirdBaitTameArrow>> BIRD_BAIT_ARROW = ENTITY_TYPES.register("bird_bait_arrow",
            () -> EntityType.Builder.<BirdBaitTameArrow>of(BirdBaitTameArrow:: new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20)
                    .build(new ResourceLocation(TameableBeast.MOD_ID, "bird_bait_arrow").toString()));

    public static final RegistryObject<EntityType<PteraMealTameArrow>> PTERA_MEAL_ARROW = ENTITY_TYPES.register("ptera_meal_arrow",
            () -> EntityType.Builder.<PteraMealTameArrow>of(PteraMealTameArrow:: new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20)
                    .build(new ResourceLocation(TameableBeast.MOD_ID, "ptera_meal_arrow").toString()));
}