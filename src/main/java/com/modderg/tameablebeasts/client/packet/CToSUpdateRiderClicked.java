package com.modderg.tameablebeasts.client.packet;

import com.modderg.tameablebeasts.server.entity.GrapteranodonEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CToSUpdateRiderClicked {

    private final int uuid;


    public CToSUpdateRiderClicked(int uuid) {
        this.uuid = uuid;
    }

    public CToSUpdateRiderClicked(FriendlyByteBuf buffer) {
        this(buffer.readInt());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(uuid);
    }


    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(()->{
            Entity entity = context.get().getSender().level().getEntity(uuid);

            if(entity instanceof GrapteranodonEntity graptera)
                graptera.tryGrabbing();
        });
    }

}