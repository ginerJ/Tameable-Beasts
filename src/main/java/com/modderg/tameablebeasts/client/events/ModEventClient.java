package com.modderg.tameablebeasts.client.events;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.client.entity.render.*;
import com.modderg.tameablebeasts.client.projectile.TameArrowRenderer;
import com.modderg.tameablebeasts.server.block.BlockEntityInit;
import com.modderg.tameablebeasts.client.block.EggBlockRender;
import com.modderg.tameablebeasts.server.entity.EntityIinit;

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
        event.registerEntityRenderer(EntityIinit.RACOON.get(), TameableRacoonRender::new);
        event.registerEntityRenderer(EntityIinit.PENGUIN.get(), PenguinRenderer::new);
        event.registerEntityRenderer(EntityIinit.CHIKOTE.get(), TameableChikoteRender::new);
        event.registerEntityRenderer(EntityIinit.SCARECROW_ALLAY.get(), ScarecrowAllayRender::new);
        event.registerEntityRenderer(EntityIinit.FLYING_BEETLE.get(), TameableBeetleRender::new);
        event.registerEntityRenderer(EntityIinit.QUETZALCOATLUS.get(), QuetzalcoatlusRender::new);
        event.registerEntityRenderer(EntityIinit.GIANT_GRASSHOPPER.get(), GiantTameableGrasshopperRender::new);
        event.registerEntityRenderer(EntityIinit.GROUND_BEETLE.get(), TameableGroundBeetleRender::new);
        event.registerEntityRenderer(EntityIinit.GIANT_ROLY_POLY.get(), RolyPolyRender::new);
        event.registerEntityRenderer(EntityIinit.FUR_GOLEM.get(), FurGolemRender::new);
        event.registerEntityRenderer(EntityIinit.CRESTED_GECKO.get(), CrestedGeckoRender::new);
        event.registerEntityRenderer(EntityIinit.ARGENTAVIS.get(), ArgentavisRender::new);
        event.registerEntityRenderer(EntityIinit.GRAPTERANODON.get(), GrapteraRender::new);

        event.registerEntityRenderer(EntityIinit.BIRD_BAIT_ARROW.get(), TameArrowRenderer::new);
        event.registerEntityRenderer(EntityIinit.PTERA_MEAL_ARROW.get(), TameArrowRenderer::new);

        event.registerBlockEntityRenderer(BlockEntityInit.EGG_BLOCK_ENTITY.get(), EggBlockRender::new);
    }

    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(TameableParticles.SHINE_PARTICLES.get(),
                CitrineParticles.Provider::new);

    }
}

