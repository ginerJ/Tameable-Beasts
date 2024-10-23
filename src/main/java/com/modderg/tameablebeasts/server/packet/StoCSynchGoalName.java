package com.modderg.tameablebeasts.server.packet;

import com.modderg.tameablebeasts.server.entity.FlyingTBAnimal;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;
import software.bernie.geckolib.util.ClientUtils;

import java.util.function.Supplier;

public class StoCSynchGoalName {

    private final int id;
    private final String goal;

    public StoCSynchGoalName(int id, String goal) {
        this.id = id;
        this.goal = goal;
    }

    public StoCSynchGoalName(FriendlyByteBuf buffer) {
        this(buffer.readInt(), buffer.readUtf());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(id);
        buffer.writeUtf(goal);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() ->
                ClientUtils.getLevel().getEntity(id).setCustomName(Component.literal(goal)));
    }
}
