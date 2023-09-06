package com.modderg.tameablebeasts.client;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.client.render.*;
import com.modderg.tameablebeasts.entities.TameableTeethEntity;
import com.modderg.tameablebeasts.init.ModEntityClass;

import com.modderg.tameablebeasts.particles.TameableParticles;
import com.modderg.tameablebeasts.particles.custom.CitrineParticles;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TameableBeast.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)

public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityClass.TAMEABLE_RACOON.get(), TameableRacoonRender::new);
        event.registerEntityRenderer(ModEntityClass.TAMEABLE_PENGUIN.get(), TameablePenguinRender::new);
        event.registerEntityRenderer(ModEntityClass.TAMEABLE_CHIKOTE.get(), TameableChikoteRender::new);
        event.registerEntityRenderer(ModEntityClass.SCARECROW_ALLAY.get(), ScarecrowAllayRender::new);
        event.registerEntityRenderer(ModEntityClass.TAMEABLE_BEETLE.get(), TameableBeetleRender::new);
        event.registerEntityRenderer(ModEntityClass.QUETZALCOATLUS.get(), QuetzalcoatlusRender::new);
        event.registerEntityRenderer(ModEntityClass.TAMEABLE_TEETH.get(), TameableTeethRender::new);
        event.registerEntityRenderer(ModEntityClass.GIANT_GRASSHOPPER.get(), GiantTameableGrasshopperRender::new);
        event.registerEntityRenderer(ModEntityClass.TAMEABLE_GROUND_BEETLE.get(), TameableGroundBeetleRender::new);
        event.registerEntityRenderer(ModEntityClass.GIANT_ROLY_POLY.get(), GiantTameableRolyPolyRender::new);
    }

    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(TameableParticles.SHINE_PARTICLES.get(),
                CitrineParticles.Provider::new);

        Minecraft.getInstance().particleEngine.register(TameableParticles.CRACKED_EGG_PARTICLES.get(),
                CitrineParticles.Provider::new);
    }
}
