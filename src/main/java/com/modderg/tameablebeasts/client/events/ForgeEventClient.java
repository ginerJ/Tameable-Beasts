package com.modderg.tameablebeasts.client.events;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.client.packet.CToSUpdateFlyingDownKey;
import com.modderg.tameablebeasts.client.packet.CToSUpdateFlyingUpKey;
import com.modderg.tameablebeasts.client.packet.CToSUpdateRiderClicked;
import com.modderg.tameablebeasts.server.entity.abstracts.FlyingRideableTBAnimal;
import com.modderg.tameablebeasts.server.entity.CrestedGeckoEntity;
import com.modderg.tameablebeasts.server.entity.GrapteranodonEntity;
import com.modderg.tameablebeasts.registry.TBPacketRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;


@Mod.EventBusSubscriber(modid = TameableBeasts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeEventClient {

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
