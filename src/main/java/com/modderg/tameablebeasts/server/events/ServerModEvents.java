package com.modderg.tameablebeasts.server.events;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.server.block.BlockInit;
import com.modderg.tameablebeasts.server.entity.EntityIinit;
import com.modderg.tameablebeasts.server.entity.custom.ChikoteEntity;
import com.modderg.tameablebeasts.server.entity.custom.FurGolemEntity;
import com.modderg.tameablebeasts.server.entity.custom.PenguinEntity;
import com.modderg.tameablebeasts.server.entity.custom.RacoonEntity;
import com.modderg.tameablebeasts.server.item.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent.Operation;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TameableBeast.MOD_ID)
public class ServerModEvents {

    @SubscribeEvent
    public static void onDeath(final LivingDeathEvent event){
        if(event.getEntity() instanceof AbstractIllager){
            if(event.getEntity().getRandom().nextInt(100) <= 5){
                Level level = event.getEntity().level();
                Vec3 vec3 = event.getEntity().position();
                ItemEntity item = new ItemEntity(level, vec3.x, vec3.y, vec3.z, new ItemStack(ItemInit.PURPLE_ALLAY.get()));
                level.addFreshEntity(item);
            }
        }

    }

    @SubscribeEvent
    public static void onPlace(final BlockEvent.EntityPlaceEvent event){
        if(event.getEntity() == null)
            return;

        Level level = event.getEntity().level();
        BlockState blockState = event.getPlacedBlock();
        BlockPos pos = event.getPos();

        if(blockState.is(BlockInit.FUR_BLOCK.get())){
            if(level.getBlockState(pos.below()).is(BlockInit.FUR_BLOCK.get()) &&
                    level.getBlockState(pos.above()).is(Blocks.CARVED_PUMPKIN)){
                level.destroyBlock(pos, false);
                level.destroyBlock(pos.above(), false);
                level.destroyBlock(pos.below(), false);

                summonGolem(level, event.getPos().below());
            }
            if(level.getBlockState(pos.above()).is(BlockInit.FUR_BLOCK.get()) &&
                    level.getBlockState(pos.above().above()).is(Blocks.CARVED_PUMPKIN)){
                level.destroyBlock(pos, false);
                level.destroyBlock(pos.above(2), false);
                level.destroyBlock(pos.above(), false);

                summonGolem(level, event.getPos());
            }
        }
        if(blockState.is(Blocks.CARVED_PUMPKIN) &&
                level.getBlockState(pos.below()).is(BlockInit.FUR_BLOCK.get()) &&
                    level.getBlockState(pos.below().below()).is(BlockInit.FUR_BLOCK.get())){
            level.destroyBlock(pos, false);
            level.destroyBlock(pos.below(), false);
            level.destroyBlock(pos.below(2), false);

            summonGolem(level, event.getPos().below(2));
        }
    }

    public static void summonGolem(Level level, BlockPos pos){
        FurGolemEntity golem = new FurGolemEntity(EntityIinit.FUR_GOLEM.get(),level);
        golem.setPos(pos.getX(), pos.getY(), pos.getZ());
        level.addFreshEntity(golem);
    }

}
