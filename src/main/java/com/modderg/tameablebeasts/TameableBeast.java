package com.modderg.tameablebeasts;

import com.modderg.tameablebeasts.block.BlockInit;
import com.modderg.tameablebeasts.core.GBiomeModifier;
import com.modderg.tameablebeasts.entities.*;
import com.modderg.tameablebeasts.item.CreativeTameableTab;
import com.modderg.tameablebeasts.item.ItemInit;
import com.modderg.tameablebeasts.init.ModEntityClass;
import com.modderg.tameablebeasts.init.SoundInit;
import com.modderg.tameablebeasts.particles.TameableParticles;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(TameableBeast.MODID)
public class TameableBeast {
    public static final String MODID = "tameablebeasts";

    public TameableBeast() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::setup);
        bus.addListener(this::setAttributes);

        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);
        ModEntityClass.ENTITY_TYPES.register(bus);
        SoundInit.SOUNDS.register(bus);
        CreativeTameableTab.TAMEABLE_TABS.register(bus);
        TameableParticles.PARTICLE_TYPES.register(bus);
        MinecraftForge.EVENT_BUS.register(this);
        bus.addListener(this::addCreativeTab);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            SpawnPlacements.register(ModEntityClass.TAMEABLE_RACOON.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING, Animal::checkAnimalSpawnRules);

            SpawnPlacements.register(ModEntityClass.TAMEABLE_PENGUIN.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TameablePenguinEntity::checkPenguinSpawnRules);

            SpawnPlacements.register(ModEntityClass.TAMEABLE_CHIKOTE.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);

            SpawnPlacements.register(ModEntityClass.SCARECROW_ALLAY.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);

            SpawnPlacements.register(ModEntityClass.TAMEABLE_BEETLE.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TameableBeetleEntity::checkFlyingBeetleSpawnRules);

            SpawnPlacements.register(ModEntityClass.QUETZALCOATLUS.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, QuetzalcoatlusEntity::checkQuetzalSpawnRules);

            SpawnPlacements.register(ModEntityClass.TAMEABLE_TEETH.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TameableTeethEntity::checkTeethSpawnRules);

            SpawnPlacements.register(ModEntityClass.GIANT_GRASSHOPPER.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);

            SpawnPlacements.register(ModEntityClass.TAMEABLE_GROUND_BEETLE.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TameableGroundBeetleEntity::checkGroundBeetleSpawnRules);

            SpawnPlacements.register(ModEntityClass.GIANT_ROLY_POLY.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        });
    }

    private void setAttributes(final EntityAttributeCreationEvent event) {
        event.put(ModEntityClass.TAMEABLE_RACOON.get(), TameableRacoonEntity.setCustomAttributes().build());

        event.put(ModEntityClass.TAMEABLE_PENGUIN.get(), TameablePenguinEntity.setCustomAttributes().build());

        event.put(ModEntityClass.TAMEABLE_CHIKOTE.get(), TameableChikoteEntity.setCustomAttributes().build());

        event.put(ModEntityClass.SCARECROW_ALLAY.get(), ScarecrowAllayEntity.setCustomAttributes().build());

        event.put(ModEntityClass.TAMEABLE_BEETLE.get(), TameableBeetleEntity.setCustomAttributes().build());

        event.put(ModEntityClass.QUETZALCOATLUS.get(), QuetzalcoatlusEntity.setCustomAttributes().build());

        event.put(ModEntityClass.TAMEABLE_TEETH.get(), TameableTeethEntity.setCustomAttributes().build());

        event.put(ModEntityClass.GIANT_GRASSHOPPER.get(), GiantTameableGrasshopperEntity.setCustomAttributes().build());

        event.put(ModEntityClass.TAMEABLE_GROUND_BEETLE.get(), TameableGroundBeetleEntity.setCustomAttributes().build());

        event.put(ModEntityClass.GIANT_ROLY_POLY.get(), GiantTameableRolyPolyEntity.setCustomAttributes().build());
    }

    private void addCreativeTab(BuildCreativeModeTabContentsEvent event){
        if(event.getTab() == CreativeTameableTab.TAMEABLE_TAB.get()){
            event.accept(ItemInit.ICEPOP);
            event.accept(ItemInit.ICE_HELMET);
            event.accept(ItemInit.PURPLE_ALLAY);
            event.accept(ItemInit.QUETZAL_EGG);
            event.accept(ItemInit.LEAF);
            event.accept(ItemInit.GRASSHOPPER_SADDLE);
            event.accept(ItemInit.ROLYPOLY_SADDLE);
            event.accept(ItemInit.CHIKOTE_SADDLE);
            event.accept(ItemInit.RACOON_SPAWN_EGG);
            event.accept(ItemInit.PENGUIN_SPAWN_EGG);
            event.accept(ItemInit.CHIKOTE_SPAWN_EGG);
            event.accept(ItemInit.BEETLE_SPAWN_EGG);
            event.accept(ItemInit.QUETZAL_SPAWN_EGG);
            event.accept(ItemInit.GIANT_GRASSHOPPER_SPAWN_EGG);
            event.accept(ItemInit.GROUND_BEETLE_SPAWN_EGG);
            event.accept(ItemInit.GROUND_ROLY_POLY_SPAWN_EGG);
            event.accept(ItemInit.SCARECROW_STRAW_HAT);
            event.accept(ItemInit.IRON_BIG_HOE);
            event.accept(BlockInit.SCARECROW_BLOCK);
        }
    }

    static DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, "tameablebeasts");

    static public RegistryObject<Codec<GBiomeModifier>> G_CODEC = BIOME_MODIFIER_SERIALIZERS.register("example", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(GBiomeModifier::biomes),
                    PlacedFeature.CODEC.fieldOf("feature").forGetter(GBiomeModifier::feature)
            ).apply(builder, GBiomeModifier::new)));
}
