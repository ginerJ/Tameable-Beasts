package com.modderg.tameablebeasts.init;
import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.client.model.GiantTameableRolyPolyModel;
import com.modderg.tameablebeasts.entities.*;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityClass {

    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(
            ForgeRegistries.ENTITY_TYPES, TameableBeast.MODID);
    public static final RegistryObject<EntityType<TameableRacoonEntity>> TAMEABLE_RACOON = ENTITY_TYPES.register("tameable_racoon",
            () -> EntityType.Builder.of(TameableRacoonEntity:: new, MobCategory.CREATURE)
                    .sized(0.75f,0.75f)
                    .build(new ResourceLocation(TameableBeast.MODID, "tameable_racoon").toString()));

    public static final RegistryObject<EntityType<TameablePenguinEntity>> TAMEABLE_PENGUIN = ENTITY_TYPES.register("tameable_penguin",
            () -> EntityType.Builder.of(TameablePenguinEntity:: new, MobCategory.CREATURE)
                    .sized(0.5f,0.75f)
                    .build(new ResourceLocation(TameableBeast.MODID, "tameable_penguin").toString()));

    public static final RegistryObject<EntityType<TameableChikoteEntity>> TAMEABLE_CHIKOTE = ENTITY_TYPES.register("tameable_chikote",
            () -> EntityType.Builder.of(TameableChikoteEntity:: new, MobCategory.CREATURE)
                    .sized(0.75f,1.1f)
                    .build(new ResourceLocation(TameableBeast.MODID, "tameable_chikote").toString()));

    public static final RegistryObject<EntityType<ScarecrowAllayEntity>> SCARECROW_ALLAY = ENTITY_TYPES.register("scarecrow_allay",
            () -> EntityType.Builder.of(ScarecrowAllayEntity:: new, MobCategory.CREATURE)
                    .sized(0.75f,1.75f)
                    .build(new ResourceLocation(TameableBeast.MODID, "scarecrow_allay").toString()));

    public static final RegistryObject<EntityType<TameableBeetleEntity>> TAMEABLE_BEETLE = ENTITY_TYPES.register("tameable_beetle",
            () -> EntityType.Builder.of(TameableBeetleEntity:: new, MobCategory.CREATURE)
                    .sized(0.75f,0.75f)
                    .build(new ResourceLocation(TameableBeast.MODID, "tameable_beetle").toString()));

    public static final RegistryObject<EntityType<QuetzalcoatlusEntity>> QUETZALCOATLUS = ENTITY_TYPES.register("quetzalcoatlus",
                () -> EntityType.Builder.of(QuetzalcoatlusEntity:: new, MobCategory.CREATURE)
                    .sized(1f,1.4f)
                    .build(new ResourceLocation(TameableBeast.MODID, "quetzalcoatlus").toString()));

    public static final RegistryObject<EntityType<TameableTeethEntity>> TAMEABLE_TEETH = ENTITY_TYPES.register("tameable_teeth",
            () -> EntityType.Builder.of(TameableTeethEntity:: new, MobCategory.CREATURE)
                    .sized(1f,1f)
                    .build(new ResourceLocation(TameableBeast.MODID, "tameable_teeth").toString()));

    public static final RegistryObject<EntityType<GiantTameableGrasshopperEntity>> GIANT_GRASSHOPPER = ENTITY_TYPES.register("giant_grasshopper",
            () -> EntityType.Builder.of(GiantTameableGrasshopperEntity:: new, MobCategory.CREATURE)
                    .sized(1f,1f)
                    .build(new ResourceLocation(TameableBeast.MODID, "giant_grasshopper").toString()));

    public static final RegistryObject<EntityType<TameableGroundBeetleEntity>> TAMEABLE_GROUND_BEETLE = ENTITY_TYPES.register("tameable_ground_beetle",
            () -> EntityType.Builder.of(TameableGroundBeetleEntity:: new, MobCategory.CREATURE)
                    .sized(0.75f,0.75f)
                    .build(new ResourceLocation(TameableBeast.MODID, "tameable_ground_beetle").toString()));

    public static final RegistryObject<EntityType<GiantTameableRolyPolyEntity>> GIANT_ROLY_POLY = ENTITY_TYPES.register("giant_roly_poly",
            () -> EntityType.Builder.of(GiantTameableRolyPolyEntity:: new, MobCategory.CREATURE)
                    .sized(1f,1f)
                    .build(new ResourceLocation(TameableBeast.MODID, "giant_roly_poly").toString()));
}