package com.modderg.tameablebeasts.client.packet;

import com.modderg.tameablebeasts.server.entity.abstracts.FlyingRideableTBAnimal;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CtoSSyncRiderWantsFlying {

    private final int id;
    private final boolean flying;

    public CtoSSyncRiderWantsFlying(int id, boolean flying) {
        this.id = id;
        this.flying = flying;
    }

    public CtoSSyncRiderWantsFlying(FriendlyByteBuf buffer) {
        this(buffer.readInt(), buffer.readBoolean());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(id);
        buffer.writeBoolean(flying);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() ->
                ((FlyingRideableTBAnimal) context.get().getSender().level().getEntity(id)).setRiderWantsFlying(flying));
    }
}
