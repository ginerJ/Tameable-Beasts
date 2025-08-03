package com.modderg.tameablebeasts.client.events;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.client.entity.renderer.*;
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
        event.registerEntityRenderer(TBEntityRegistry.RACOON.get(), RacoonRenderer::new);
        event.registerEntityRenderer(TBEntityRegistry.PENGUIN.get(), PenguinRendererer::new);
        event.registerEntityRenderer(TBEntityRegistry.CHIKOTE.get(), ChikoteRenderer::new);
        event.registerEntityRenderer(TBEntityRegistry.SCARECROW_ALLAY.get(), ScarecrowAllayRenderer::new);
        event.registerEntityRenderer(TBEntityRegistry.FLYING_BEETLE.get(), ShinyBeetleRenderer::new);
        event.registerEntityRenderer(TBEntityRegistry.BEETLE_DRONE.get(), BeetleDroneRenderer::new);
        event.registerEntityRenderer(TBEntityRegistry.QUETZALCOATLUS.get(), QuetzalcoatlusRenderer::new);
        event.registerEntityRenderer(TBEntityRegistry.GIANT_GRASSHOPPER.get(), GiantGrasshopperRenderer::new);
        event.registerEntityRenderer(TBEntityRegistry.GROUND_BEETLE.get(), GroundBeetleRender::new);
        event.registerEntityRenderer(TBEntityRegistry.GIANT_ROLY_POLY.get(), RolyPolyRenderer::new);
        event.registerEntityRenderer(TBEntityRegistry.FUR_GOLEM.get(), FurGolemRenderer::new);
        event.registerEntityRenderer(TBEntityRegistry.CRESTED_GECKO.get(), CrestedGeckoRenderer::new);
        event.registerEntityRenderer(TBEntityRegistry.ARGENTAVIS.get(), ArgentavisRenderer::new);
        event.registerEntityRenderer(TBEntityRegistry.GRAPTERANODON.get(), GrapteraRenderer::new);

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

