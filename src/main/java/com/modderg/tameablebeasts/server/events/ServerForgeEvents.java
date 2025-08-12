package com.modderg.tameablebeasts.server.events;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.registry.TBBlockRegistry;
import com.modderg.tameablebeasts.registry.TBEntityRegistry;
import com.modderg.tameablebeasts.registry.TBPacketRegistry;
import com.modderg.tameablebeasts.server.entity.FurGolemEntity;
import com.modderg.tameablebeasts.registry.TBItemRegistry;
import com.modderg.tameablebeasts.server.entity.abstracts.TBAnimal;
import com.modderg.tameablebeasts.server.packet.StoCEntityInvSyncPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

import static com.modderg.tameablebeasts.registry.TBPacketRegistry.TBNETWORK;

@Mod.EventBusSubscriber(modid = TameableBeasts.MOD_ID)
public class ServerForgeEvents {

    @SubscribeEvent
    public static void onEntityJoin(final EntityJoinLevelEvent event){

        if(event.getEntity() instanceof TBAnimal tbAnimal && tbAnimal.isTame()){
            event.getLevel().getServer().execute(
                    () ->{
                        List<ItemStack> copy = new ArrayList<>();

                        for (int i = 0; i < tbAnimal.getInventory().getSlots(); i++) {
                            ItemStack stack = tbAnimal.getInventory().getStackInSlot(i);
                            if (!stack.isEmpty())
                                copy.add(stack.copy());
                        }

                        TBPacketRegistry.sendToAll(new StoCEntityInvSyncPacket(tbAnimal.getId(), copy));
                    }
            );

        }
    }

    @SubscribeEvent
    public static void onDeath(final LivingDeathEvent event){
        if(event.getEntity() instanceof AbstractIllager illager){
            if(event.getEntity().getRandom().nextInt(100) <= 5)
                illager.spawnAtLocation(TBItemRegistry.PURPLE_ALLAY.get());
        }
    }

    @SubscribeEvent
    public static void onPlace(final BlockEvent.EntityPlaceEvent event){
        if(event.getEntity() == null)
            return;

        Level level = event.getEntity().level();
        BlockState blockState = event.getPlacedBlock();
        BlockPos pos = event.getPos();

        if(blockState.is(TBBlockRegistry.FUR_BLOCK.get())){
            if(level.getBlockState(pos.below()).is(TBBlockRegistry.FUR_BLOCK.get()) &&
                    level.getBlockState(pos.above()).is(Blocks.CARVED_PUMPKIN)){
                level.destroyBlock(pos, false);
                level.destroyBlock(pos.above(), false);
                level.destroyBlock(pos.below(), false);

                summonGolem(level, event.getPos().below());
            }
            if(level.getBlockState(pos.above()).is(TBBlockRegistry.FUR_BLOCK.get()) &&
                    level.getBlockState(pos.above().above()).is(Blocks.CARVED_PUMPKIN)){
                level.destroyBlock(pos, false);
                level.destroyBlock(pos.above(2), false);
                level.destroyBlock(pos.above(), false);

                summonGolem(level, event.getPos());
            }
        }
        if(blockState.is(Blocks.CARVED_PUMPKIN) &&
                level.getBlockState(pos.below()).is(TBBlockRegistry.FUR_BLOCK.get()) &&
                    level.getBlockState(pos.below().below()).is(TBBlockRegistry.FUR_BLOCK.get())){
            level.destroyBlock(pos, false);
            level.destroyBlock(pos.below(), false);
            level.destroyBlock(pos.below(2), false);

            summonGolem(level, event.getPos().below(2));
        }
    }

    public static void summonGolem(Level level, BlockPos pos){
        FurGolemEntity golem = new FurGolemEntity(TBEntityRegistry.FUR_GOLEM.get(),level);
        golem.setPos(pos.getX(), pos.getY(), pos.getZ());
        level.addFreshEntity(golem);
    }
}
