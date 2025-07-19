package com.modderg.tameablebeasts.server.item;

import com.modderg.tameablebeasts.server.block.ScarecrowBlock;
import com.modderg.tameablebeasts.server.entity.ScarecrowAllayEntity;
import com.modderg.tameablebeasts.registry.TBEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
            ScarecrowAllayEntity scarecrow = TBEntityRegistry.SCARECROW_ALLAY.get().create(level);
            scarecrow.setPos(new Vec3(blockpos.getX(), blockpos.getY(), blockpos.getZ()));
            level.addFreshEntity(scarecrow);
            level.destroyBlock(blockpos, false);
            level.sendBlockUpdated(blockpos, blockstate, level.getBlockState(blockpos), 3);

            if (!context.getPlayer().getAbilities().instabuild) {
                ItemStack itemStack = context.getPlayer().getItemInHand(context.getHand());
                itemStack.shrink(1);
            }
        }

        return super.useOn(context);
    }


}
