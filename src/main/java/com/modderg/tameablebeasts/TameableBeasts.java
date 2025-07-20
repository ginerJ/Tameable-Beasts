package com.modderg.tameablebeasts;

import com.modderg.tameablebeasts.registry.TBBlockEntityRegistry;
import com.modderg.tameablebeasts.registry.TBBlockRegistry;
import com.modderg.tameablebeasts.registry.TBEnchantmentRegistry;
import com.modderg.tameablebeasts.server.entity.*;
import com.modderg.tameablebeasts.registry.TBPOITypesRegistry;
import com.modderg.tameablebeasts.server.item.TBCreativeTab;
import com.modderg.tameablebeasts.registry.TBEntityRegistry;
import com.modderg.tameablebeasts.registry.TBItemRegistry;
import com.modderg.tameablebeasts.client.sound.SoundInit;
import com.modderg.tameablebeasts.client.ModClientConfigs;
import com.modderg.tameablebeasts.server.ModCommonConfigs;
import com.modderg.tameablebeasts.client.particles.TameableParticles;
import com.modderg.tameablebeasts.registry.TBPacketRegistry;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import software.bernie.geckolib.GeckoLib;

@Mod(TameableBeasts.MOD_ID)
public class TameableBeasts {
    public static final String MOD_ID = "tameablebeasts";

    public TameableBeasts() {

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::setup);
        bus.addListener(this::setAttributes);

        TBItemRegistry.TB_ITEMS.register(bus);
        TBEnchantmentRegistry.ENCHANTMENTS.register(bus);
        bus.addListener(this::addCreativeTab);
        TBCreativeTab.TAMEABLE_TABS.register(bus);

        TBBlockRegistry.TB_BLOCKS.register(bus);
        TBBlockEntityRegistry.register(bus);
        TBPOITypesRegistry.POI_TYPES.register(bus);

        GeckoLib.initialize();

        TBEntityRegistry.ENTITY_TYPES.register(bus);
        TBEntityRegistry.init();

        SoundInit.SOUNDS.register(bus);

        TameableParticles.PARTICLE_TYPES.register(bus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ModClientConfigs.SPEC, "tameable-beasts-client.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ModCommonConfigs.SPEC, "tameable-beasts-common.toml");

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {

        event.enqueueWork(TBPacketRegistry::register);

        event.enqueueWork(() -> {
            SpawnPlacements.register(TBEntityRegistry.RACOON.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING, RacoonEntity::checkRacoonSpawnRules);

            SpawnPlacements.register(TBEntityRegistry.PENGUIN.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PenguinEntity::checkPenguinSpawnRules);

            SpawnPlacements.register(TBEntityRegistry.CHIKOTE.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ChikoteEntity::checkChikoteSpawnRules);

            SpawnPlacements.register(TBEntityRegistry.SCARECROW_ALLAY.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);

            SpawnPlacements.register(TBEntityRegistry.FLYING_BEETLE.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FlyingBeetleEntity::checkFlyingBeetleSpawnRules);

            SpawnPlacements.register(TBEntityRegistry.BEETLE_DRONE.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);

            SpawnPlacements.register(TBEntityRegistry.QUETZALCOATLUS.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, QuetzalcoatlusEntity::checkQuetzalSpawnRules);

            SpawnPlacements.register(TBEntityRegistry.GIANT_GRASSHOPPER.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GrasshopperEntity::checkGrasshopperSpawnRules);

            SpawnPlacements.register(TBEntityRegistry.GROUND_BEETLE.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GroundBeetleEntity::checkGroundBeetleSpawnRules);

            SpawnPlacements.register(TBEntityRegistry.GIANT_ROLY_POLY.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, RolyPolyEntity::checkRolyPolySpawnRules);

            SpawnPlacements.register(TBEntityRegistry.CRESTED_GECKO.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CrestedGeckoEntity::checkCrestedGeckoSpawnRules);

            SpawnPlacements.register(TBEntityRegistry.FUR_GOLEM.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);

            SpawnPlacements.register(TBEntityRegistry.ARGENTAVIS.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ArgentavisEntity::checkArgentavisSpawnRules);

            SpawnPlacements.register(TBEntityRegistry.GRAPTERANODON.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GrapteranodonEntity::checkGrapteraSpawnRules);
        });
    }

    private void setAttributes(final EntityAttributeCreationEvent event) {
        event.put(TBEntityRegistry.RACOON.get(), RacoonEntity.setCustomAttributes().build());

        event.put(TBEntityRegistry.PENGUIN.get(), PenguinEntity.setCustomAttributes().build());

        event.put(TBEntityRegistry.CHIKOTE.get(), ChikoteEntity.setCustomAttributes().build());

        event.put(TBEntityRegistry.SCARECROW_ALLAY.get(), ScarecrowAllayEntity.setCustomAttributes().build());

        event.put(TBEntityRegistry.FLYING_BEETLE.get(), FlyingBeetleEntity.setCustomAttributes().build());
        event.put(TBEntityRegistry.BEETLE_DRONE.get(), BeetleDrone.setCustomAttributes().build());

        event.put(TBEntityRegistry.QUETZALCOATLUS.get(), QuetzalcoatlusEntity.setCustomAttributes().build());

        event.put(TBEntityRegistry.GIANT_GRASSHOPPER.get(), GrasshopperEntity.setCustomAttributes().build());

        event.put(TBEntityRegistry.GROUND_BEETLE.get(), GroundBeetleEntity.setCustomAttributes().build());

        event.put(TBEntityRegistry.GIANT_ROLY_POLY.get(), RolyPolyEntity.setCustomAttributes().build());

        event.put(TBEntityRegistry.FUR_GOLEM.get(), FurGolemEntity.setCustomAttributes().build());

        event.put(TBEntityRegistry.CRESTED_GECKO.get(), CrestedGeckoEntity.setCustomAttributes().build());

        event.put(TBEntityRegistry.ARGENTAVIS.get(), ArgentavisEntity.setCustomAttributes().build());

        event.put(TBEntityRegistry.GRAPTERANODON.get(), GrapteranodonEntity.setCustomAttributes().build());
    }

    private void addCreativeTab(BuildCreativeModeTabContentsEvent event){
        if(event.getTab() == TBCreativeTab.TAMEABLE_TAB.get()){
            for (RegistryObject<Item> registryItem: TBItemRegistry.TB_ITEMS.getEntries())
                event.accept(registryItem);
        }
    }
}
