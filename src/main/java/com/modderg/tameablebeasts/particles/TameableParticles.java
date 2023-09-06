package com.modderg.tameablebeasts.particles;

import com.modderg.tameablebeasts.TameableBeast;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TameableParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, TameableBeast.MODID);

    public static final RegistryObject<SimpleParticleType> SHINE_PARTICLES =
            PARTICLE_TYPES.register("shine_particles", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> CRACKED_EGG_PARTICLES =
            PARTICLE_TYPES.register("cracked_egg_particles", () -> new SimpleParticleType(true));


    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
