package com.modderg.tameablebeasts.client.events;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.client.packet.CToSUpdateFlyingDownKey;
import com.modderg.tameablebeasts.client.packet.CToSUpdateFlyingUpKey;
import com.modderg.tameablebeasts.client.packet.CToSUpdateRiderClicked;
import com.modderg.tameablebeasts.registry.TBBlockRegistry;
import com.modderg.tameablebeasts.registry.TBItemRegistry;
import com.modderg.tameablebeasts.server.entity.abstracts.FlyingRideableTBAnimal;
import com.modderg.tameablebeasts.server.entity.CrestedGeckoEntity;
import com.modderg.tameablebeasts.server.entity.GrapteranodonEntity;
import com.modderg.tameablebeasts.registry.TBPacketRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import java.util.List;


@Mod.EventBusSubscriber(modid = TameableBeasts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeEventClient {


    @SubscribeEvent
    public static void onTooltipSet(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> tooltip = event.getToolTip();

        if (stack.is(TBItemRegistry.ICEPOP.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.icepop.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.ICE_HELMET.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.ice_helmet.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.PURPLE_ALLAY.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.purple_allay.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.IRON_BIG_HOE.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.iron_big_hoe.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.BIRD_BAIT_ARROW.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.bird_bait_arrow.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.PTERA_MEAL_ARROW.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.ptera_meal_arrow.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.LEAF.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.leaf.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.BEETLE_DUST.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.beetle_dust.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.BUG_SWORD.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.bug_sword.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.GRASSHOPPER_LEG.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.grasshopper_leg.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.FUR.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.racoon_fur.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.ROLY_POLY_PLAQUE.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.roly_plaque.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.EGG_RESTS.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.egg_rests.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.BIG_BIRD_BAIT.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.big_bird_bait.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.PTERANODON_MEAL.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.pteranodon_meal.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.BUG_SALAD.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.bug_salad.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.ASPHALT.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.asphalt.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.SCARECROW_STRAW_HAT.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.scarecrow_straw_hat.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.BIKER_HELMET.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.biker_helmet.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.FLYING_HELMET.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.flying_helmet.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.ICE_CHESTPLATE.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.ice_chestplate.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.ROLYPOLY_SADDLE.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.roly_poly_saddle.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.ARGENTAVIS_SADDLE.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.argentavis_saddle.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.CRESTED_GECKO_SADDLE.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.crested_gecko_saddle.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.GRASSHOPPER_SADDLE.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.grasshopper_saddle.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.CHIKOTE_SADDLE.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.chikote_saddle.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.QUETZAL_SADDLE.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.quetzal_saddle.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.QUETZAL_STAND.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.quetzal_stand.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBBlockRegistry.SCARECROW_BLOCK_ITEM.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.scarecrow_block.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBBlockRegistry.FUR_BLOCK_ITEM.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.racoon_fur_block.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.QUETZAL_EGG_ITEM.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.quetzalcoatlus_egg.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.CHIKOTE_EGG_ITEM.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.chikote_egg.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.ROLY_POLY_EGG_ITEM.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.roly_poly_egg.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.FLYING_BEETLE_EGG_ITEM.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.beetle_egg.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.GROUND_BEETLE_EGG_ITEM.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.ground_beetle_egg.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.GRASSHOPPER_EGG_ITEM.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.grasshopper_egg.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.PENGUIN_EGG_ITEM.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.penguin_egg.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.CRESTED_GECKO_EGG_ITEM.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.crested_gecko_egg.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.ARGENTAVIS_EGG_ITEM.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.argentavis_egg.tooltip").withStyle(ChatFormatting.DARK_GRAY));

        else if (stack.is(TBItemRegistry.GRAPTERANODON_EGG_ITEM.get()))
            tooltip.add(Component.translatable("item.tameablebeasts.graptera_egg.tooltip").withStyle(ChatFormatting.DARK_GRAY));
    }



    @SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent event){
        if(event.phase == TickEvent.Phase.END){
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null && player.getVehicle() instanceof FlyingRideableTBAnimal animal){

                boolean playerPressingSpace = Minecraft.getInstance().options.keyJump.isDown(),
                        playerPressingShift = Minecraft.getInstance().options.keySprint.isDown();

                if(animal.upInput != playerPressingSpace){
                    animal.upInput = playerPressingSpace;
                    TBPacketRegistry.sendToServer(new CToSUpdateFlyingUpKey(animal.getId(), playerPressingSpace));
                }

                if(animal.downInput != playerPressingShift){
                    animal.downInput = Minecraft.getInstance().options.keySprint.isDown();
                    TBPacketRegistry.sendToServer(new CToSUpdateFlyingDownKey(animal.getId(), playerPressingShift));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onMouseInput(InputEvent.MouseButton event) {
        if (Minecraft.getInstance().screen == null && event.getButton() == GLFW.GLFW_MOUSE_BUTTON_1 && event.getAction() == GLFW.GLFW_PRESS) {
            LocalPlayer player = Minecraft.getInstance().player;

            if (player != null && player.getVehicle() instanceof FlyingRideableTBAnimal animal) {
                if (animal instanceof GrapteranodonEntity pteranodon && !pteranodon.playGrip) {
                    pteranodon.playGrip = true;
                    TBPacketRegistry.sendToServer(new CToSUpdateRiderClicked(animal.getId()));
                }
            }
        }
    }

    @SubscribeEvent(priority= EventPriority.LOWEST)
    public static void onPlayerRender(RenderPlayerEvent.Pre event) {

        Player player = event.getEntity();

        if(player.getVehicle() instanceof CrestedGeckoEntity gecko && gecko.onClimbable()){
            PoseStack poseStack = event.getPoseStack();

            CameraType cameraType = Minecraft.getInstance().options.getCameraType();
            float cameraYaw = Minecraft.getInstance().getEntityRenderDispatcher().camera.getYRot();

            poseStack.pushPose();

            poseStack.mulPose(Axis.YP.rotationDegrees(-cameraYaw));

            if(cameraType==CameraType.THIRD_PERSON_BACK)
                poseStack.mulPose(Axis.XP.rotation(-(float) Math.PI / 2));
            else if(cameraType==CameraType.THIRD_PERSON_FRONT)
                poseStack.mulPose(Axis.XP.rotation((float) Math.PI / 2));

            poseStack.mulPose(Axis.YP.rotationDegrees(cameraYaw));
        }
    }

    @SubscribeEvent
    public static void onPlayerRender(RenderPlayerEvent.Post event) {
        Player player = event.getEntity();

        if(player.getVehicle() instanceof CrestedGeckoEntity gecko && gecko.onClimbable()) {
            PoseStack poseStack = event.getPoseStack();
            poseStack.popPose();
        }
    }
}
