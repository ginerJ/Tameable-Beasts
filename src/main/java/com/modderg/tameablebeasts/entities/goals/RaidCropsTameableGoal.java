package com.modderg.tameablebeasts.entities.goals;

import com.modderg.tameablebeasts.block.BlockInit;
import com.modderg.tameablebeasts.block.custom.ScarecrowBlock;
import com.modderg.tameablebeasts.entities.TameableGAnimal;
import com.modderg.tameablebeasts.entities.custom.ScarecrowAllayEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class RaidCropsTameableGoal extends Goal {


    TameableGAnimal mob;
    int radius;

    BlockPos targetPos;

    public RaidCropsTameableGoal(TameableGAnimal mob, int radius){
        this.mob = mob;
        this.radius = radius;
    }

    @Override
    public boolean canUse() {
        targetPos = hasCropsToRaid();
        if (targetPos != null &&
                !checkForScarecrowBlock() &&
                !checkForScarecrow()){
            return true;
        } else {
            targetPos = null;
            return false;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return targetPos != null &&
                mob.level().getBlockState(targetPos).getBlock() instanceof CropBlock
                && !checkForScarecrowBlock() && !checkForScarecrow();
    }

    @Override
    public void stop() {
        this.targetPos = null;
    }

    int raidTimer = 60;
    @Override
    public void tick() {
        if(--raidTimer < 0){
            this.mob.getNavigation()
                    .moveTo(targetPos.getX(),targetPos.getY(),targetPos.getZ(),1f);
        }

        if(mob.distanceToSqr(targetPos.getCenter()) <=1){
            mob.level().destroyBlock(targetPos,false);
            mob.level().playLocalSound(targetPos, SoundEvents.GENERIC_EAT, SoundSource.BLOCKS, 2.0F, mob.level().random.nextFloat() * 0.1F + 0.9F, false);
            targetPos = null;
            raidTimer = 60;
        }
    }

    private static final TargetingConditions TARGETING_CONDITIONS = TargetingConditions.forNonCombat().range(8.0D).ignoreLineOfSight();
    private boolean checkForScarecrow(){
        List<? extends ScarecrowAllayEntity> list = mob.level().getNearbyEntities(ScarecrowAllayEntity.class, TARGETING_CONDITIONS, this.mob, this.mob.getBoundingBox().inflate(10.0D));
        return !list.isEmpty();
    }

    private boolean checkForScarecrowBlock(){
        BlockPos entityPos = mob.blockPosition();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {

                    BlockPos currentPos = entityPos.offset(x, y, z);

                    BlockState state = mob.level().getBlockState(currentPos);
                    Block currentBlock = state.getBlock();


                    if(currentBlock instanceof ScarecrowBlock) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private BlockPos hasCropsToRaid(){
        BlockPos entityPos = mob.blockPosition();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {

                    BlockPos currentPos = entityPos.offset(x, y, z);

                    BlockState state = mob.level().getBlockState(currentPos);
                    Block currentBlock = state.getBlock();


                    if(currentBlock instanceof CropBlock crop && crop.isMaxAge(state)) {
                        return currentPos;
                    }
                }
            }
        }
        return null;
    }
}
