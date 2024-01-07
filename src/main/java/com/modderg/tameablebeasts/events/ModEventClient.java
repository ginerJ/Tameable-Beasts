package com.modderg.tameablebeasts.events;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.block.BlockEntityInit;
import com.modderg.tameablebeasts.block.entity.EggBlockRender;
import com.modderg.tameablebeasts.entities.render.*;
import com.modderg.tameablebeasts.entities.EntityIinit;

import com.modderg.tameablebeasts.particles.TameableParticles;
import com.modderg.tameablebeasts.particles.custom.CitrineParticles;
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
        event.registerEntityRenderer(EntityIinit.TAMEABLE_RACOON.get(), TameableRacoonRender::new);
        event.registerEntityRenderer(EntityIinit.TAMEABLE_PENGUIN.get(), TameablePenguinRender::new);
        event.registerEntityRenderer(EntityIinit.TAMEABLE_CHIKOTE.get(), TameableChikoteRender::new);
        event.registerEntityRenderer(EntityIinit.SCARECROW_ALLAY.get(), ScarecrowAllayRender::new);
        event.registerEntityRenderer(EntityIinit.TAMEABLE_BEETLE.get(), TameableBeetleRender::new);
        event.registerEntityRenderer(EntityIinit.QUETZALCOATLUS.get(), QuetzalcoatlusRender::new);
        event.registerEntityRenderer(EntityIinit.GIANT_GRASSHOPPER.get(), GiantTameableGrasshopperRender::new);
        event.registerEntityRenderer(EntityIinit.TAMEABLE_GROUND_BEETLE.get(), TameableGroundBeetleRender::new);
        event.registerEntityRenderer(EntityIinit.GIANT_ROLY_POLY.get(), GiantTameableRolyPolyRender::new);
        event.registerEntityRenderer(EntityIinit.FUR_GOLEM.get(), FurGolemRender::new);
        event.registerEntityRenderer(EntityIinit.CRESTED_GECKO.get(), CrestedGeckoRender::new);

        event.registerBlockEntityRenderer(BlockEntityInit.EGG_BLOCK_ENTITY.get(), EggBlockRender::new);
    }


    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(TameableParticles.SHINE_PARTICLES.get(),
                CitrineParticles.Provider::new);

        Minecraft.getInstance().particleEngine.register(TameableParticles.CRACKED_EGG_PARTICLES.get(),
                CitrineParticles.Provider::new);
    }
}

