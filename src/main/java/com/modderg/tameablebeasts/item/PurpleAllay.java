package com.modderg.tameablebeasts.item;

import com.modderg.tameablebeasts.block.ScarecrowBlock;
import com.modderg.tameablebeasts.entities.ScarecrowAllayEntity;
import com.modderg.tameablebeasts.init.ModEntityClass;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class PurpleAllay extends Item {
    public PurpleAllay(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        Block block = blockstate.getBlock();

        if (block instanceof ScarecrowBlock) {
            ScarecrowAllayEntity scarecrow = ModEntityClass.SCARECROW_ALLAY.get().create(level);
            scarecrow.setPos(new Vec3(blockpos.getX(), blockpos.getY(), blockpos.getZ()));
            level.addFreshEntity(scarecrow);
            level.destroyBlock(blockpos, true);
        }

        return super.useOn(context);
    }


}
