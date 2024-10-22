package com.modderg.tameablebeasts.client.events;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.client.entity.render.*;
import com.modderg.tameablebeasts.client.projectile.TameArrowRenderer;
import com.modderg.tameablebeasts.server.block.BlockEntityInit;
import com.modderg.tameablebeasts.client.block.EggBlockRender;
import com.modderg.tameablebeasts.server.entity.EntityInit;

import com.modderg.tameablebeasts.client.particles.TameableParticles;
import com.modderg.tameablebeasts.client.particles.custom.CitrineParticles;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TameableBeast.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventClient {

    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityInit.RACOON.get(), TameableRacoonRender::new);
        event.registerEntityRenderer(EntityInit.PENGUIN.get(), PenguinRenderer::new);
        event.registerEntityRenderer(EntityInit.CHIKOTE.get(), TameableChikoteRender::new);
        event.registerEntityRenderer(EntityInit.SCARECROW_ALLAY.get(), ScarecrowAllayRender::new);
        event.registerEntityRenderer(EntityInit.FLYING_BEETLE.get(), FlyingBeetleRender::new);
        event.registerEntityRenderer(EntityInit.BEETLE_DRONE.get(), BeetleDroneRender::new);
        event.registerEntityRenderer(EntityInit.QUETZALCOATLUS.get(), QuetzalcoatlusRender::new);
        event.registerEntityRenderer(EntityInit.GIANT_GRASSHOPPER.get(), GiantTameableGrasshopperRender::new);
        event.registerEntityRenderer(EntityInit.GROUND_BEETLE.get(), TameableGroundBeetleRender::new);
        event.registerEntityRenderer(EntityInit.GIANT_ROLY_POLY.get(), RolyPolyRender::new);
        event.registerEntityRenderer(EntityInit.FUR_GOLEM.get(), FurGolemRender::new);
        event.registerEntityRenderer(EntityInit.CRESTED_GECKO.get(), CrestedGeckoRender::new);
        event.registerEntityRenderer(EntityInit.ARGENTAVIS.get(), ArgentavisRender::new);
        event.registerEntityRenderer(EntityInit.GRAPTERANODON.get(), GrapteraRender::new);

        event.registerEntityRenderer(EntityInit.BIRD_BAIT_ARROW.get(), TameArrowRenderer::new);
        event.registerEntityRenderer(EntityInit.PTERA_MEAL_ARROW.get(), TameArrowRenderer::new);

        event.registerBlockEntityRenderer(BlockEntityInit.EGG_BLOCK_ENTITY.get(), EggBlockRender::new);
    }

    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(TameableParticles.SHINE_PARTICLES.get(),
                CitrineParticles.Provider::new);

    }
}

