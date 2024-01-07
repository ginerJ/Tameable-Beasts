package com.modderg.tameablebeasts.events;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.entities.custom.CrestedGeckoEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;
import org.joml.Vector3f;


@Mod.EventBusSubscriber(modid = TameableBeast.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeEventClient {
    @SubscribeEvent(priority= EventPriority.LOWEST)
    public static void onPlayerRender(RenderPlayerEvent.Pre event) {

        Player player = event.getEntity();

        if(player.getVehicle() instanceof CrestedGeckoEntity gecko && gecko.isClimbing()){
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

        if(player.getVehicle() instanceof CrestedGeckoEntity gecko && gecko.isClimbing()) {
            PoseStack poseStack = event.getPoseStack();
            poseStack.popPose();
        }
    }
}
