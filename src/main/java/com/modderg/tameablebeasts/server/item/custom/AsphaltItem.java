package com.modderg.tameablebeasts.server.item.custom;

import com.modderg.tameablebeasts.server.block.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class AsphaltItem extends Item {
    public AsphaltItem(Properties p_41383_) {
        super(p_41383_);
    }

    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        if (context.getClickedFace() == Direction.DOWN) {
            return InteractionResult.PASS;
        } else {
            Player player = context.getPlayer();
            BlockState blockstate1 = null;

            if(blockstate.is(Blocks.DIRT) || blockstate.is(Blocks.DIRT_PATH) || blockstate.is(Blocks.GRASS_BLOCK))
                blockstate1 = BlockInit.ASPHALTED_DIRT.get().defaultBlockState();

            BlockState blockstate2 = null;

            if (blockstate1 != null && level.isEmptyBlock(blockpos.above())) {
                level.playSound(player, blockpos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
                blockstate2 = blockstate1;
            }

            if (blockstate2 != null) {
                if (!level.isClientSide) {
                    level.setBlock(blockpos, blockstate2, 11);
                    level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(player, blockstate2));
                    if (player != null) {
                        context.getItemInHand().shrink(1);
                    }
                }

                return InteractionResult.sidedSuccess(level.isClientSide);
            } else
                return InteractionResult.PASS;
        }
    }
}
