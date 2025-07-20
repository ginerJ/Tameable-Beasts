package com.modderg.tameablebeasts.client.events;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.client.entity.render.*;
import com.modderg.tameablebeasts.client.projectile.TameArrowRenderer;
import com.modderg.tameablebeasts.registry.TBBlockEntityRegistry;
import com.modderg.tameablebeasts.client.block.EggBlockRender;
import com.modderg.tameablebeasts.registry.TBEntityRegistry;

import com.modderg.tameablebeasts.client.particles.TameableParticles;
import com.modderg.tameablebeasts.client.particles.custom.CitrineParticles;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TameableBeasts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventClient {

    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(TBEntityRegistry.RACOON.get(), RacoonRender::new);
        event.registerEntityRenderer(TBEntityRegistry.PENGUIN.get(), PenguinRenderer::new);
        event.registerEntityRenderer(TBEntityRegistry.CHIKOTE.get(), TameableChikoteRender::new);
        event.registerEntityRenderer(TBEntityRegistry.SCARECROW_ALLAY.get(), ScarecrowAllayRender::new);
        event.registerEntityRenderer(TBEntityRegistry.FLYING_BEETLE.get(), FlyingBeetleRender::new);
        event.registerEntityRenderer(TBEntityRegistry.BEETLE_DRONE.get(), BeetleDroneRender::new);
        event.registerEntityRenderer(TBEntityRegistry.QUETZALCOATLUS.get(), QuetzalcoatlusRender::new);
        event.registerEntityRenderer(TBEntityRegistry.GIANT_GRASSHOPPER.get(), GiantTameableGrasshopperRender::new);
        event.registerEntityRenderer(TBEntityRegistry.GROUND_BEETLE.get(), TameableGroundBeetleRender::new);
        event.registerEntityRenderer(TBEntityRegistry.GIANT_ROLY_POLY.get(), RolyPolyRender::new);
        event.registerEntityRenderer(TBEntityRegistry.FUR_GOLEM.get(), FurGolemRender::new);
        event.registerEntityRenderer(TBEntityRegistry.CRESTED_GECKO.get(), CrestedGeckoRender::new);
        event.registerEntityRenderer(TBEntityRegistry.ARGENTAVIS.get(), ArgentavisRender::new);
        event.registerEntityRenderer(TBEntityRegistry.GRAPTERANODON.get(), GrapteraRender::new);

        event.registerEntityRenderer(TBEntityRegistry.BIRD_BAIT_ARROW.get(), TameArrowRenderer::new);
        event.registerEntityRenderer(TBEntityRegistry.PTERA_MEAL_ARROW.get(), TameArrowRenderer::new);

        event.registerBlockEntityRenderer(TBBlockEntityRegistry.EGG_BLOCK_ENTITY.get(), EggBlockRender::new);
    }

    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(TameableParticles.SHINE_PARTICLES.get(),
                CitrineParticles.Provider::new);
    }
}

