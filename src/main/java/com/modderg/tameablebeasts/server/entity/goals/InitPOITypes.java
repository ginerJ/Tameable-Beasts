package com.modderg.tameablebeasts.server.entity.goals;

import com.google.common.collect.ImmutableSet;
import com.modderg.tameablebeasts.server.block.BlockInit;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class InitPOITypes {

    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, "tameablebeasts");

    public static final RegistryObject<PoiType> ARGENTAVIS_POI =
            POI_TYPES.register("argentavis_poi", () -> new PoiType(
                    ImmutableSet.of(BlockInit.ARGENTAVIS_EGG_BLOCK.get().defaultBlockState())
                    , 1, 15));

    public static final RegistryObject<PoiType> CRESTED_GECKO_POI =
            POI_TYPES.register("crested_gecko_poi", () -> new PoiType(
                    ImmutableSet.of(BlockInit.CRESTED_GECKO_EGG_BLOCK.get().defaultBlockState())
                    , 1, 15));

    public static final RegistryObject<PoiType> QUETZAL_POI =
            POI_TYPES.register("quetzal_poi", () -> new PoiType(
                    ImmutableSet.of(BlockInit.QUETZAL_EGG_BLOCK.get().defaultBlockState())
                    , 1, 15));

    public static final RegistryObject<PoiType> CHIKOTE_POI =
            POI_TYPES.register("chikote_poi", () -> new PoiType(
                    ImmutableSet.of(BlockInit.CHIKOTE_EGG_BLOCK.get().defaultBlockState())
                    , 1, 15));

    public static final RegistryObject<PoiType> ROLY_POLY_POI =
            POI_TYPES.register("roly_poly_poi", () -> new PoiType(
                    ImmutableSet.of(BlockInit.ROLY_POLY_EGG_BLOCK.get().defaultBlockState())
                    , 1, 15));

    public static final RegistryObject<PoiType> FLYING_BEETLE_POI =
            POI_TYPES.register("flying_beetle_poi", () -> new PoiType(
                    ImmutableSet.of(BlockInit.FLYING_BEETLE_EGG_BLOCK.get().defaultBlockState())
                    , 1, 15));

    public static final RegistryObject<PoiType> GROUND_BEETLE_POI =
            POI_TYPES.register("ground_beetle_poi", () -> new PoiType(
                    ImmutableSet.of(BlockInit.GROUND_BEETLE_EGG_BLOCK.get().defaultBlockState())
                    , 1, 15));

    public static final RegistryObject<PoiType> GRASSHOPPER_POI =
            POI_TYPES.register("grasshopper_poi", () -> new PoiType(
                    ImmutableSet.of(BlockInit.GRASSHOPPER_EGG_BLOCK.get().defaultBlockState())
                    , 1, 15));

    public static final RegistryObject<PoiType> PENGUIN_POI =
            POI_TYPES.register("penguin_poi", () -> new PoiType(
                    ImmutableSet.of(BlockInit.PENGUIN_EGG_BLOCK.get().defaultBlockState())
                    , 1, 15));

    public static final RegistryObject<PoiType> GRAPTERANODON_POI =
            POI_TYPES.register("graptera_poi", () -> new PoiType(
                    ImmutableSet.of(BlockInit.GRAPTERANODON_EGG_BLOCK.get().defaultBlockState())
                    , 1, 15));

    public static final RegistryObject<PoiType> SCARECROW_POI =
            POI_TYPES.register("scarecrow_poi", () -> new PoiType(
                    ImmutableSet.of(BlockInit.SCARECROW_BLOCK.get().defaultBlockState())
                    , 1, 15));
}
