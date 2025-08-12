package com.modderg.tameablebeasts.server.packet;

import com.modderg.tameablebeasts.server.entity.abstracts.TBAnimal;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public record StoCEntityInvSyncPacket(int entityId, List<ItemStack> items) {

    public StoCEntityInvSyncPacket(FriendlyByteBuf buffer) {
        this(buffer.readVarInt(),
                IntStream.range(0, buffer.readVarInt())
                        .mapToObj(i -> buffer.readItem())
                        .toList());
    }

    public static void encode(StoCEntityInvSyncPacket msg, FriendlyByteBuf buf) {
        buf.writeVarInt(msg.entityId);
        buf.writeVarInt(msg.items.size());
        for (ItemStack stack : msg.items) buf.writeItem(stack);
    }

    public static void handle(StoCEntityInvSyncPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            Entity e = mc.level.getEntity(msg.entityId);
            if (e instanceof TBAnimal ent) {
                for (int i = 0; i < ent.getInventory().getSlots(); i++)
                    ent.getInventory().setStackInSlot(i, i < msg.items.size() ? msg.items.get(i) : ItemStack.EMPTY);
            }
        });
    }
}

