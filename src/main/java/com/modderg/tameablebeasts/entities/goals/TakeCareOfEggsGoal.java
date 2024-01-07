package com.modderg.tameablebeasts.entities.goals;

import com.modderg.tameablebeasts.block.entity.EggBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import software.bernie.geckolib.animatable.GeoEntity;

public class TakeCareOfEggsGoal extends Goal {
    Animal mob;
    Block specificEgg;
    int radius;
    BlockPos targetPos;

    public TakeCareOfEggsGoal(Animal mob, int radius){
        this.mob = mob;
        this.radius = radius;
    }

    public TakeCareOfEggsGoal(Animal mob, int radius, Block specificEgg){
        this.mob = mob;
        this.radius = radius;
        this.specificEgg = specificEgg;
    }

    @Override
    public boolean canUse() {
        targetPos = hasEggToTakeCareOff();
        return targetPos != null;
    }

    @Override
    public boolean canContinueToUse() {
        return targetPos != null &&
                (mob.level().getBlockEntity(targetPos) instanceof EggBlockEntity egg &&
                        egg.goBadTimer < 1500);
    }

    @Override
    public void stop() {
        this.targetPos = null;
    }

    int careTimer = 0;

    @Override
    public void tick() {
        if(--careTimer < 0){
            mob.setMaxUpStep(1.5f);
            this.mob.getNavigation()
                    .moveTo(targetPos.getX(),targetPos.getY()+1,targetPos.getZ(),1f);
        }

        if(mob.getOnPos().equals(targetPos) &&
                mob.level().getBlockEntity(targetPos) instanceof EggBlockEntity egg){
            if (GeoEntity.class.isAssignableFrom(mob.getClass()))
                ((GeoEntity) mob).triggerAnim("TakeCareOfEggControl","hatch");
            egg.goBadTimer = 3000;
            targetPos = null;
            careTimer = 60;
        }
    }

    private BlockPos hasEggToTakeCareOff(){
        BlockPos entityPos = mob.blockPosition();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {

                    BlockPos currentPos = entityPos.offset(x, y, z);

                    BlockEntity currentBlock = mob.level().getBlockEntity(currentPos);


                    if(currentBlock instanceof EggBlockEntity egg && egg.goBadTimer < 1500) {
                        if(specificEgg == null|| egg.getBlockState().getBlock().equals(specificEgg)){
                            return currentPos;
                        }
                    }
                }
            }
        }
        return null;
    }
}
