package com.modderg.tameablebeasts.client.packet;

import com.modderg.tameablebeasts.server.entity.FlyingRideableTBAnimal;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CToSUpdateFlyingUpKey {

    private final int uuid;
    private final boolean value;


    public CToSUpdateFlyingUpKey(int uuid, boolean value) {
        this.uuid = uuid;
        this.value = value;
    }

    public CToSUpdateFlyingUpKey(FriendlyByteBuf buffer) {
        this(buffer.readInt(), buffer.readBoolean());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(uuid);
        buffer.writeBoolean(value);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(()->{
            Entity entity = context.get().getSender().level().getEntity(uuid);

            if(entity instanceof FlyingRideableTBAnimal animal)
                animal.upInput = value;
        });
    }

}