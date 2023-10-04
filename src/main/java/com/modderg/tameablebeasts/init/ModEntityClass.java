package com.modderg.tameablebeasts.init;
import com.modderg.tameablebeasts.TameableBeast;
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
    public static final RegistryObject<EntityType<RacoonEntity>> TAMEABLE_RACOON = ENTITY_TYPES.register("tameable_racoon",
            () -> EntityType.Builder.of(RacoonEntity:: new, MobCategory.CREATURE)
                    .sized(0.75f,0.75f)
                    .build(new ResourceLocation(TameableBeast.MODID, "tameable_racoon").toString()));

    public static final RegistryObject<EntityType<PenguinEntity>> TAMEABLE_PENGUIN = ENTITY_TYPES.register("tameable_penguin",
            () -> EntityType.Builder.of(PenguinEntity:: new, MobCategory.CREATURE)
                    .sized(0.5f,0.75f)
                    .build(new ResourceLocation(TameableBeast.MODID, "tameable_penguin").toString()));

    public static final RegistryObject<EntityType<ChikoteEntity>> TAMEABLE_CHIKOTE = ENTITY_TYPES.register("tameable_chikote",
            () -> EntityType.Builder.of(ChikoteEntity:: new, MobCategory.CREATURE)
                    .sized(1f,1.2f)
                    .build(new ResourceLocation(TameableBeast.MODID, "tameable_chikote").toString()));

    public static final RegistryObject<EntityType<ScarecrowAllayEntity>> SCARECROW_ALLAY = ENTITY_TYPES.register("scarecrow_allay",
            () -> EntityType.Builder.of(ScarecrowAllayEntity:: new, MobCategory.CREATURE)
                    .sized(0.75f,1.75f)
                    .build(new ResourceLocation(TameableBeast.MODID, "scarecrow_allay").toString()));

    public static final RegistryObject<EntityType<FlyingBeetleEntity>> TAMEABLE_BEETLE = ENTITY_TYPES.register("tameable_beetle",
            () -> EntityType.Builder.of(FlyingBeetleEntity:: new, MobCategory.CREATURE)
                    .sized(0.75f,0.75f)
                    .build(new ResourceLocation(TameableBeast.MODID, "tameable_beetle").toString()));

    public static final RegistryObject<EntityType<QuetzalcoatlusEntity>> QUETZALCOATLUS = ENTITY_TYPES.register("quetzalcoatlus",
                () -> EntityType.Builder.of(QuetzalcoatlusEntity:: new, MobCategory.CREATURE)
                    .sized(1f,1.4f)
                    .build(new ResourceLocation(TameableBeast.MODID, "quetzalcoatlus").toString()));

    public static final RegistryObject<EntityType<GrasshopperEntity>> GIANT_GRASSHOPPER = ENTITY_TYPES.register("giant_grasshopper",
            () -> EntityType.Builder.of(GrasshopperEntity:: new, MobCategory.CREATURE)
                    .sized(1f,1f)
                    .build(new ResourceLocation(TameableBeast.MODID, "giant_grasshopper").toString()));

    public static final RegistryObject<EntityType<GroundBeetleEntity>> TAMEABLE_GROUND_BEETLE = ENTITY_TYPES.register("ground_beetle",
            () -> EntityType.Builder.of(GroundBeetleEntity:: new, MobCategory.CREATURE)
                    .sized(0.75f,0.75f)
                    .build(new ResourceLocation(TameableBeast.MODID, "ground_beetle").toString()));

    public static final RegistryObject<EntityType<RolyPolyEntity>> GIANT_ROLY_POLY = ENTITY_TYPES.register("giant_roly_poly",
            () -> EntityType.Builder.of(RolyPolyEntity:: new, MobCategory.CREATURE)
                    .sized(1f,1f)
                    .build(new ResourceLocation(TameableBeast.MODID, "giant_roly_poly").toString()));
}