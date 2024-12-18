package com.modderg.tameablebeasts;

import com.modderg.tameablebeasts.server.block.BlockEntityInit;
import com.modderg.tameablebeasts.server.block.BlockInit;
import com.modderg.tameablebeasts.server.enchantments.EnchantmentInit;
import com.modderg.tameablebeasts.server.entity.custom.*;
import com.modderg.tameablebeasts.server.block.InitPOITypes;
import com.modderg.tameablebeasts.server.item.CreativeTameableTab;
import com.modderg.tameablebeasts.server.entity.EntityInit;
import com.modderg.tameablebeasts.server.item.ItemInit;
import com.modderg.tameablebeasts.client.sound.SoundInit;
import com.modderg.tameablebeasts.client.ModClientConfigs;
import com.modderg.tameablebeasts.server.ModCommonConfigs;
import com.modderg.tameablebeasts.client.particles.TameableParticles;
import com.modderg.tameablebeasts.server.packet.InitPackets;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
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
import software.bernie.geckolib.GeckoLib;

@Mod(TameableBeast.MOD_ID)
public class TameableBeast {
    public static final String MOD_ID = "tameablebeasts";

    public TameableBeast() {

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::setup);
        bus.addListener(this::setAttributes);

        ItemInit.ITEMS.register(bus);
        EnchantmentInit.ENCHANTMENTS.register(bus);
        bus.addListener(this::addCreativeTab);
        CreativeTameableTab.TAMEABLE_TABS.register(bus);

        BlockInit.BLOCKS.register(bus);
        BlockEntityInit.register(bus);
        InitPOITypes.POI_TYPES.register(bus);

        GeckoLib.initialize();

        EntityInit.ENTITY_TYPES.register(bus);
        EntityInit.init();

        SoundInit.SOUNDS.register(bus);

        TameableParticles.PARTICLE_TYPES.register(bus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ModClientConfigs.SPEC, "tameable-beasts-client.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ModCommonConfigs.SPEC, "tameable-beasts-common.toml");

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {

        event.enqueueWork(InitPackets::register);

        event.enqueueWork(() -> {
            SpawnPlacements.register(EntityInit.RACOON.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING, RacoonEntity::checkRacoonSpawnRules);

            SpawnPlacements.register(EntityInit.PENGUIN.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PenguinEntity::checkPenguinSpawnRules);

            SpawnPlacements.register(EntityInit.CHIKOTE.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ChikoteEntity::checkChikoteSpawnRules);

            SpawnPlacements.register(EntityInit.SCARECROW_ALLAY.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);

            SpawnPlacements.register(EntityInit.FLYING_BEETLE.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FlyingBeetleEntity::checkFlyingBeetleSpawnRules);

            SpawnPlacements.register(EntityInit.BEETLE_DRONE.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);

            SpawnPlacements.register(EntityInit.QUETZALCOATLUS.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, QuetzalcoatlusEntity::checkQuetzalSpawnRules);

            SpawnPlacements.register(EntityInit.GIANT_GRASSHOPPER.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GrasshopperEntity::checkGrasshopperSpawnRules);

            SpawnPlacements.register(EntityInit.GROUND_BEETLE.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GroundBeetleEntity::checkGroundBeetleSpawnRules);

            SpawnPlacements.register(EntityInit.GIANT_ROLY_POLY.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, RolyPolyEntity::checkRolyPolySpawnRules);

            SpawnPlacements.register(EntityInit.CRESTED_GECKO.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CrestedGeckoEntity::checkCrestedGeckoSpawnRules);

            SpawnPlacements.register(EntityInit.FUR_GOLEM.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);

            SpawnPlacements.register(EntityInit.ARGENTAVIS.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ArgentavisEntity::checkArgentavisSpawnRules);

            SpawnPlacements.register(EntityInit.GRAPTERANODON.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GrapteranodonEntity::checkGrapteraSpawnRules);
        });
    }

    private void setAttributes(final EntityAttributeCreationEvent event) {
        event.put(EntityInit.RACOON.get(), RacoonEntity.setCustomAttributes().build());

        event.put(EntityInit.PENGUIN.get(), PenguinEntity.setCustomAttributes().build());

        event.put(EntityInit.CHIKOTE.get(), ChikoteEntity.setCustomAttributes().build());

        event.put(EntityInit.SCARECROW_ALLAY.get(), ScarecrowAllayEntity.setCustomAttributes().build());

        event.put(EntityInit.FLYING_BEETLE.get(), FlyingBeetleEntity.setCustomAttributes().build());
        event.put(EntityInit.BEETLE_DRONE.get(), BeetleDrone.setCustomAttributes().build());

        event.put(EntityInit.QUETZALCOATLUS.get(), QuetzalcoatlusEntity.setCustomAttributes().build());

        event.put(EntityInit.GIANT_GRASSHOPPER.get(), GrasshopperEntity.setCustomAttributes().build());

        event.put(EntityInit.GROUND_BEETLE.get(), GroundBeetleEntity.setCustomAttributes().build());

        event.put(EntityInit.GIANT_ROLY_POLY.get(), RolyPolyEntity.setCustomAttributes().build());

        event.put(EntityInit.FUR_GOLEM.get(), FurGolemEntity.setCustomAttributes().build());

        event.put(EntityInit.CRESTED_GECKO.get(), CrestedGeckoEntity.setCustomAttributes().build());

        event.put(EntityInit.ARGENTAVIS.get(), ArgentavisEntity.setCustomAttributes().build());

        event.put(EntityInit.GRAPTERANODON.get(), GrapteranodonEntity.setCustomAttributes().build());
    }

    private void addCreativeTab(BuildCreativeModeTabContentsEvent event){
        if(event.getTab() == CreativeTameableTab.TAMEABLE_TAB.get()){
            event.accept(ItemInit.ICEPOP);
            event.accept(ItemInit.ICE_HELMET);
            event.accept(ItemInit.ICE_CHESTPLATE);
            event.accept(ItemInit.PURPLE_ALLAY);
            event.accept(ItemInit.LEAF);
            event.accept(ItemInit.GRASSHOPPER_LEG);
            event.accept(ItemInit.BEETLE_DUST);
            event.accept(ItemInit.GRASSHOPPER_SADDLE);
            event.accept(ItemInit.ROLYPOLY_SADDLE);
            event.accept(ItemInit.CHIKOTE_SADDLE);
            event.accept(ItemInit.CRESTED_GECKO_SADDLE);
            event.accept(ItemInit.QUETZAL_SADDLE);
            event.accept(ItemInit.QUETZAL_STAND);
            event.accept(ItemInit.ARGENTAVIS_SADDLE);
            event.accept(ItemInit.RACOON_SPAWN_EGG);
            event.accept(ItemInit.PENGUIN_SPAWN_EGG);
            event.accept(ItemInit.CHIKOTE_SPAWN_EGG);
            event.accept(ItemInit.BEETLE_SPAWN_EGG);
            event.accept(ItemInit.QUETZAL_SPAWN_EGG);
            event.accept(ItemInit.GIANT_GRASSHOPPER_SPAWN_EGG);
            event.accept(ItemInit.GROUND_BEETLE_SPAWN_EGG);
            event.accept(ItemInit.GROUND_ROLY_POLY_SPAWN_EGG);
            event.accept(ItemInit.SCARECROW_SPAWN_EGG);
            event.accept(ItemInit.CRESTED_GECKO_SPAWN_EGG);
            event.accept(ItemInit.ARGENTAVIS_SPAWN_EGG);
            event.accept(ItemInit.GRAPTERA_SPAWN_EGG);
            event.accept(ItemInit.SCARECROW_STRAW_HAT);
            event.accept(ItemInit.FLYING_HELMET);
            event.accept(ItemInit.BIKER_HELMET);
            event.accept(ItemInit.RACOON_HAT);
            event.accept(ItemInit.IRON_BIG_HOE);
            event.accept(ItemInit.BUG_SWORD);
            event.accept(ItemInit.BIRD_BAIT_ARROW);
            event.accept(ItemInit.PTERA_MEAL_ARROW);
            event.accept(ItemInit.FUR);
            event.accept(ItemInit.ROLY_POLY_PLAQUE);
            event.accept(ItemInit.ASPHALT);
            event.accept(ItemInit.EGG_RESTS);
            event.accept(ItemInit.QUETZAL_EGG_ITEM);
            event.accept(ItemInit.CHIKOTE_EGG_ITEM);
            event.accept(ItemInit.ROLY_POLY_EGG_ITEM);
            event.accept(ItemInit.FLYING_BEETLE_EGG_ITEM);
            event.accept(ItemInit.GROUND_BEETLE_EGG_ITEM);
            event.accept(ItemInit.GRASSHOPPER_EGG_ITEM);
            event.accept(ItemInit.PENGUIN_EGG_ITEM);
            event.accept(ItemInit.CRESTED_GECKO_EGG_ITEM);
            event.accept(ItemInit.ARGENTAVIS_EGG_ITEM);
            event.accept(ItemInit.GRAPTERANODON_EGG_ITEM);
            event.accept(ItemInit.QUETZAL_MEAT);
            event.accept(ItemInit.COOKED_QUETZAL_MEAT);
            event.accept(ItemInit.BIG_BIRD_MEAT);
            event.accept(ItemInit.COOKED_BIG_BIRD_MEAT);
            event.accept(ItemInit.BIG_BIRD_BAIT);
            event.accept(ItemInit.PTERANODON_MEAL);
            event.accept(ItemInit.BUG_SALAD);
            event.accept(BlockInit.FUR_BLOCK);
            event.accept(BlockInit.ASPHALTED_DIRT);
            event.accept(BlockInit.SCARECROW_BLOCK);
        }
    }
}
