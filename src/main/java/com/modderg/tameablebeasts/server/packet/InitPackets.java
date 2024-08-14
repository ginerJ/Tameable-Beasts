package com.modderg.tameablebeasts.server.packet;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.client.packet.CToSUpdateFlyingDownKey;
import com.modderg.tameablebeasts.client.packet.CToSUpdateFlyingUpKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkConstants;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class InitPackets {

    public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(
                    new ResourceLocation(TameableBeast.MOD_ID, "main"))
            .serverAcceptedVersions((s)->true)
            .clientAcceptedVersions((s)->true)
            .networkProtocolVersion(() ->NetworkConstants.NETVERSION)
            .simpleChannel();

    public static void register(){

        int id = 0;

        INSTANCE.messageBuilder(CToSUpdateFlyingUpKey.class, id++)
                .encoder(CToSUpdateFlyingUpKey::encode)
                .decoder(CToSUpdateFlyingUpKey::new)
                .consumerMainThread(CToSUpdateFlyingUpKey::handle)
                .add();

        INSTANCE.messageBuilder(CToSUpdateFlyingDownKey.class, id++)
                .encoder(CToSUpdateFlyingDownKey::encode)
                .decoder(CToSUpdateFlyingDownKey::new)
                .consumerMainThread(CToSUpdateFlyingDownKey::handle)
                .add();

        INSTANCE.messageBuilder(StoCLoveEggPacket.class, id++)
                .encoder(StoCLoveEggPacket::encode)
                .decoder(StoCLoveEggPacket::new)
                .consumerMainThread(StoCLoveEggPacket::handle)
                .add();

        INSTANCE.messageBuilder(StoCSyncFlying.class, id++)
                .encoder(StoCSyncFlying::encode)
                .decoder(StoCSyncFlying::new)
                .consumerMainThread(StoCSyncFlying::handle)
                .add();

        INSTANCE.messageBuilder(CtoSSyncRiderWantsFlying.class, id++)
                .encoder(CtoSSyncRiderWantsFlying::encode)
                .decoder(CtoSSyncRiderWantsFlying::new)
                .consumerMainThread(CtoSSyncRiderWantsFlying::handle)
                .add();
    }

    public static void sendToServer(Object msg){
        INSTANCE.send(PacketDistributor.SERVER.noArg(), msg);
    }

    public static void sendToClient(Object msg, ServerPlayer player)
    {
        if (INSTANCE.isRemotePresent(player.connection.connection))
        {
            INSTANCE.send(PacketDistributor.PLAYER.with(()-> player), msg);        }
    }

    public static void sendToAll(Object msg)
    {
        INSTANCE.send(PacketDistributor.ALL.noArg(), msg);
    }
}