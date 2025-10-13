package com.modderg.tameablebeasts.mixin;

import com.modderg.tameablebeasts.server.entity.abstracts.FlyingRideableTBAnimal;
import com.modderg.tameablebeasts.server.entity.abstracts.RideableTBAnimal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
    @Inject(
            method = "handleSetEntityPassengersPacket",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Gui;setOverlayMessage(Lnet/minecraft/network/chat/Component;Z)V"
            ),
            cancellable = true
    )
    private void cancelMountOnboardMessage(ClientboundSetPassengersPacket packet, CallbackInfo ci) {
        Player player = Minecraft.getInstance().player;
        if (player != null && player.isPassenger()) {
            Entity vehicle = player.getVehicle();
            if (vehicle instanceof FlyingRideableTBAnimal flyingRideableTBAnimal) {
                ci.cancel();
                flyingRideableTBAnimal.messageRiding(player);
            }
            else if(vehicle instanceof RideableTBAnimal rideableTBAnimal) {
                ci.cancel();
                rideableTBAnimal.messageRiding(player);
            }
        }
    }
}
