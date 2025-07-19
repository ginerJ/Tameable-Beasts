package com.modderg.tameablebeasts.client.events;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.client.entity.render.*;
import com.modderg.tameablebeasts.client.projectile.TameArrowRenderer;
import com.modderg.tameablebeasts.registry.TBBlockEntityRegistry;
import com.modderg.tameablebeasts.client.block.EggBlockRender;
import com.modderg.tameablebeasts.registry.TBEntityRegistry;

import com.modderg.tameablebeasts.client.particles.TameableParticles;
import com.modderg.tameablebeasts.client.particles.custom.CitrineParticles;
import com.modderg.tameablebeasts.registry.TBItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = TameableBeasts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventClient {

    @SubscribeEvent
    public static void onTooltipSet(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> tooltip = event.getToolTip();

        if (stack.is(TBItemRegistry.ICEPOP.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.icepop.tooltip").withStyle(ChatFormatting.GRAY));

        if (stack.is(TBItemRegistry.ICE_HELMET.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.ice_helmet.tooltip").withStyle(ChatFormatting.GRAY));

        if (stack.is(TBItemRegistry.PURPLE_ALLAY.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.purple_allay.tooltip").withStyle(ChatFormatting.GRAY));

        if (stack.is(TBItemRegistry.IRON_BIG_HOE.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.iron_big_hoe.tooltip").withStyle(ChatFormatting.GRAY));

        if (stack.is(TBItemRegistry.BIRD_BAIT_ARROW.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.bird_bait_arrow.tooltip").withStyle(ChatFormatting.GRAY));

        if (stack.is(TBItemRegistry.PTERA_MEAL_ARROW.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.ptera_meal_arrow.tooltip").withStyle(ChatFormatting.GRAY));

        if (stack.is(TBItemRegistry.LEAF.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.leaf.tooltip").withStyle(ChatFormatting.GRAY));

        if (stack.is(TBItemRegistry.BEETLE_DUST.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.beetle_dust.tooltip").withStyle(ChatFormatting.GRAY));

        if (stack.is(TBItemRegistry.BUG_SWORD.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.bug_sword.tooltip").withStyle(ChatFormatting.GRAY));

        if (stack.is(TBItemRegistry.GRASSHOPPER_LEG.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.grasshopper_leg.tooltip").withStyle(ChatFormatting.GRAY));

        if (stack.is(TBItemRegistry.FUR.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.racoon_fur.tooltip").withStyle(ChatFormatting.GRAY));

        if (stack.is(TBItemRegistry.ROLY_POLY_PLAQUE.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.roly_plaque.tooltip").withStyle(ChatFormatting.GRAY));

        if (stack.is(TBItemRegistry.EGG_RESTS.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.egg_rests.tooltip").withStyle(ChatFormatting.GRAY));

        if (stack.is(TBItemRegistry.BIG_BIRD_BAIT.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.big_bird_bait.tooltip").withStyle(ChatFormatting.GRAY));

        if (stack.is(TBItemRegistry.PTERANODON_MEAL.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.pteranodon_meal.tooltip").withStyle(ChatFormatting.GRAY));

        if (stack.is(TBItemRegistry.BUG_SALAD.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.bug_salad.tooltip").withStyle(ChatFormatting.GRAY));

        if (stack.is(TBItemRegistry.ASPHALT.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.asphalt.tooltip").withStyle(ChatFormatting.GRAY));

        if (stack.is(TBItemRegistry.SCARECROW_STRAW_HAT.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.scarecrow_straw_hat.tooltip").withStyle(ChatFormatting.GRAY));

        if (stack.is(TBItemRegistry.BIKER_HELMET.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.biker_helmet.tooltip").withStyle(ChatFormatting.GRAY));

        if (stack.is(TBItemRegistry.FLYING_HELMET.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.flying_helmet.tooltip").withStyle(ChatFormatting.GRAY));

        if (stack.is(TBItemRegistry.ICE_CHESTPLATE.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.ice_chestplate.tooltip").withStyle(ChatFormatting.GRAY));

        if (stack.is(TBItemRegistry.ROLYPOLY_SADDLE.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.roly_poly_saddle.tooltip").withStyle(ChatFormatting.GRAY));

        if (stack.is(TBItemRegistry.ARGENTAVIS_SADDLE.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.argentavis_saddle.tooltip").withStyle(ChatFormatting.GRAY));

        if (stack.is(TBItemRegistry.CRESTED_GECKO_SADDLE.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.crested_gecko_saddle.tooltip").withStyle(ChatFormatting.GRAY));

        if (stack.is(TBItemRegistry.GRASSHOPPER_SADDLE.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.grasshopper_saddle.tooltip").withStyle(ChatFormatting.GRAY));

        if (stack.is(TBItemRegistry.CHIKOTE_SADDLE.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.chikote_saddle.tooltip").withStyle(ChatFormatting.GRAY));

        if (stack.is(TBItemRegistry.QUETZAL_SADDLE.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.quetzal_saddle.tooltip").withStyle(ChatFormatting.GRAY));

        if (stack.is(TBItemRegistry.QUETZAL_STAND.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.quetzal_stand.tooltip").withStyle(ChatFormatting.GRAY));
    }



    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(TBEntityRegistry.RACOON.get(), TameableRacoonRender::new);
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

