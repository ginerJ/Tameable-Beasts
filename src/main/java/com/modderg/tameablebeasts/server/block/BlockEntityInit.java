package com.modderg.tameablebeasts.server.block;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.server.entity.TBAnimal;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TameableBeast.MOD_ID);

    //make one entityType
    public static final RegistryObject<BlockEntityType<EggBlockEntity<TBAnimal>>> EGG_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("egg_block_entity", () -> BlockEntityType.Builder.of(EggBlockEntity::new,
                    BlockInit.QUETZAL_EGG_BLOCK.get(),
                    BlockInit.CHIKOTE_EGG_BLOCK.get(),
                    BlockInit.ROLY_POLY_EGG_BLOCK.get(),
                    BlockInit.FLYING_BEETLE_EGG_BLOCK.get(),
                    BlockInit.GROUND_BEETLE_EGG_BLOCK.get(),
                    BlockInit.GRASSHOPPER_EGG_BLOCK.get(),
                    BlockInit.PENGUIN_EGG_BLOCK.get(),
                    BlockInit.CRESTED_GECKO_EGG_BLOCK.get(),
                    BlockInit.ARGENTAVIS_EGG_BLOCK.get(),
                    BlockInit.GRAPTERANODON_EGG_BLOCK.get()
            ).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
