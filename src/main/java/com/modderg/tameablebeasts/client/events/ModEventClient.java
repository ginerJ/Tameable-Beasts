package com.modderg.tameablebeasts.client.events;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.client.entity.renderer.*;
import com.modderg.tameablebeasts.client.gui.TBInventoryScreen;
import com.modderg.tameablebeasts.client.gui.TBMenu;
import com.modderg.tameablebeasts.client.projectile.TameArrowRenderer;
import com.modderg.tameablebeasts.registry.TBBlockEntityRegistry;
import com.modderg.tameablebeasts.client.block.EggBlockRender;
import com.modderg.tameablebeasts.registry.TBEntityRegistry;

import com.modderg.tameablebeasts.client.particles.TameableParticles;
import com.modderg.tameablebeasts.client.particles.custom.CitrineParticles;
import com.modderg.tameablebeasts.registry.TBMenuRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

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

    @SubscribeEvent
    public static void registerEntityRenders(FMLClientSetupEvent event) {
        event.enqueueWork(() -> MenuScreens.register(TBMenuRegistry.TBMOB_MENU_CONTAINER.get(), TBInventoryScreen::new));
        event.enqueueWork(() -> MenuScreens.register(TBMenuRegistry.PENGUIN_MENU_CONTAINER.get(), TBInventoryScreen::new));
        event.enqueueWork(() -> MenuScreens.register(TBMenuRegistry.JUST_SADDLE_MENU_CONTAINER.get(), TBInventoryScreen::new));
    }
}

