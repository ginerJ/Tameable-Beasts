package com.modderg.tameablebeasts.registry;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.server.block.EggBlockEntity;
import com.modderg.tameablebeasts.server.entity.abstracts.TBAnimal;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TBBlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TameableBeasts.MOD_ID);

    //make one entityType
    public static final RegistryObject<BlockEntityType<EggBlockEntity<TBAnimal>>> EGG_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("egg_block_entity", () -> BlockEntityType.Builder.of(EggBlockEntity::new,
                    TBBlockRegistry.QUETZAL_EGG_BLOCK.get(),
                    TBBlockRegistry.CHIKOTE_EGG_BLOCK.get(),
                    TBBlockRegistry.ROLY_POLY_EGG_BLOCK.get(),
                    TBBlockRegistry.FLYING_BEETLE_EGG_BLOCK.get(),
                    TBBlockRegistry.GROUND_BEETLE_EGG_BLOCK.get(),
                    TBBlockRegistry.GRASSHOPPER_EGG_BLOCK.get(),
                    TBBlockRegistry.PENGUIN_EGG_BLOCK.get(),
                    TBBlockRegistry.CRESTED_GECKO_EGG_BLOCK.get(),
                    TBBlockRegistry.ARGENTAVIS_EGG_BLOCK.get(),
                    TBBlockRegistry.GRAPTERANODON_EGG_BLOCK.get()
            ).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
