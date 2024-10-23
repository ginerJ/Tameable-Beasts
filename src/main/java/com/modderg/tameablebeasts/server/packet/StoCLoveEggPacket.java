package com.modderg.tameablebeasts.server.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class StoCLoveEggPacket {

    private final BlockPos targetPos;

    public StoCLoveEggPacket(BlockPos targetPos) {
        this.targetPos = targetPos;
    }

    public StoCLoveEggPacket(FriendlyByteBuf buffer) {
        this(buffer.readBlockPos());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(targetPos);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            if (targetPos != null) {

                Random random = new Random();

                for(int i = 0; i < 7; ++i) {
                    double d0 = random.nextGaussian() * 0.02D;
                    double d1 = random.nextGaussian() * 0.02D;
                    double d2 = random.nextGaussian() * 0.02D;
                    Minecraft.getInstance().level.addParticle(ParticleTypes.HEART,
                            targetPos.getX() + random.nextDouble(), targetPos.getY() + 0.5D + random.nextDouble(), targetPos.getZ() + random.nextDouble(), d0, d1, d2);
                }
            }
        });
    }
}
