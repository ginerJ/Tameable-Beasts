package com.modderg.tameablebeasts.registry;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.client.packet.CToSUpdateFlyingDownKey;
import com.modderg.tameablebeasts.client.packet.CToSUpdateFlyingUpKey;
import com.modderg.tameablebeasts.client.packet.CToSUpdateRiderClicked;
import com.modderg.tameablebeasts.client.packet.CtoSSyncRiderWantsFlying;
import com.modderg.tameablebeasts.server.packet.StoCEntityInvSyncPacket;
import com.modderg.tameablebeasts.server.packet.StoCLoveEggPacket;
import com.modderg.tameablebeasts.server.packet.StoCSyncFlyingPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkConstants;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class TBPacketRegistry {

    public static final SimpleChannel TBNETWORK = NetworkRegistry.ChannelBuilder.named(
                    new ResourceLocation(TameableBeasts.MOD_ID, "main"))
            .serverAcceptedVersions((s)->true)
            .clientAcceptedVersions((s)->true)
            .networkProtocolVersion(() ->NetworkConstants.NETVERSION)
            .simpleChannel();

    public static void register(){

        int id = 0;

        TBNETWORK.messageBuilder(CToSUpdateFlyingUpKey.class, id++)
                .encoder(CToSUpdateFlyingUpKey::encode)
                .decoder(CToSUpdateFlyingUpKey::new)
                .consumerMainThread(CToSUpdateFlyingUpKey::handle)
                .add();

        TBNETWORK.messageBuilder(CToSUpdateFlyingDownKey.class, id++)
                .encoder(CToSUpdateFlyingDownKey::encode)
                .decoder(CToSUpdateFlyingDownKey::new)
                .consumerMainThread(CToSUpdateFlyingDownKey::handle)
                .add();

        TBNETWORK.messageBuilder(StoCLoveEggPacket.class, id++)
                .encoder(StoCLoveEggPacket::encode)
                .decoder(StoCLoveEggPacket::new)
                .consumerMainThread(StoCLoveEggPacket::handle)
                .add();

        TBNETWORK.messageBuilder(StoCSyncFlyingPacket.class, id++)
                .encoder(StoCSyncFlyingPacket::encode)
                .decoder(StoCSyncFlyingPacket::new)
                .consumerMainThread(StoCSyncFlyingPacket::handle)
                .add();

        TBNETWORK.messageBuilder(StoCEntityInvSyncPacket.class, id++)
                .encoder(StoCEntityInvSyncPacket::encode)
                .decoder(StoCEntityInvSyncPacket::new)
                .consumerMainThread(StoCEntityInvSyncPacket::handle)
                .add();

        TBNETWORK.messageBuilder(CtoSSyncRiderWantsFlying.class, id++)
                .encoder(CtoSSyncRiderWantsFlying::encode)
                .decoder(CtoSSyncRiderWantsFlying::new)
                .consumerMainThread(CtoSSyncRiderWantsFlying::handle)
                .add();

        TBNETWORK.messageBuilder(CToSUpdateRiderClicked.class, id++)
                .encoder(CToSUpdateRiderClicked::encode)
                .decoder(CToSUpdateRiderClicked::new)
                .consumerMainThread(CToSUpdateRiderClicked::handle)
                .add();
    }

    public static void sendToServer(Object msg){
        TBNETWORK.send(PacketDistributor.SERVER.noArg(), msg);
    }

    public static void sendToClient(Object msg, ServerPlayer player)
    {
        if (TBNETWORK.isRemotePresent(player.connection.connection))
        {
            TBNETWORK.send(PacketDistributor.PLAYER.with(()-> player), msg);        }
    }

    public static void sendToAll(Object msg)
    {
        TBNETWORK.send(PacketDistributor.ALL.noArg(), msg);
    }
}