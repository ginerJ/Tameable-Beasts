package com.modderg.tameablebeasts.server.packet;

import com.modderg.tameablebeasts.server.entity.abstracts.FlyingTBAnimal;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import software.bernie.geckolib.util.ClientUtils;

import java.util.function.Supplier;

public class StoCSyncFlying {

    private final int id;
    private final boolean flying;

    public StoCSyncFlying(int id, boolean flying) {
        this.id = id;
        this.flying = flying;
    }

    public StoCSyncFlying(FriendlyByteBuf buffer) {
        this(buffer.readInt(), buffer.readBoolean());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(id);
        buffer.writeBoolean(flying);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() ->
                ((FlyingTBAnimal) ClientUtils.getLevel().getEntity(id)).setIsFlying(flying));
    }
}
