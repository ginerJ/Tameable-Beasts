package com.modderg.tameablebeasts.server.entity.goals;

import com.modderg.tameablebeasts.server.entity.FlyingTBAnimal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;

import java.util.EnumSet;

public class FlyFromNowAndThenGoal extends Goal {

    public FlyingTBAnimal mob;
    private int timer;

    public FlyFromNowAndThenGoal(FlyingTBAnimal mob) {
        this.mob = mob;
        this.timer = mob.getRandom().nextInt(0,2000);
    }

    @Override
    public boolean canUse() {
        return !mob.isTame() && !mob.isOrderedToSit();
    }

    @Override
    public boolean canContinueToUse() {
        return !mob.isTame() && !mob.isOrderedToSit();
    }

    BlockPos landingPos = null;
    @Override
    public void tick() {
        if(timer--<=0){
            mob.setGoalsRequireFlying(!mob.getGoalsRequireFlying());
            this.timer = mob.getRandom().nextInt(0,2000);
            landingPos = null;

        } else if (timer < 50 && !mob.onGround() && mob.getGoalsRequireFlying())
            landingPos = findLandingSpot();

        if(landingPos != null)
            mob.getNavigation().moveTo(landingPos.getX(), landingPos.getY(), landingPos.getZ(), 1.0D);

        super.tick();
    }

    protected BlockPos findLandingSpot() {
        BlockPos position = mob.blockPosition();
        Level level = mob.level();

        while (position.getY() > level.getMinBuildHeight() && level.isEmptyBlock(position))
            position = position.below();

        if (!level.getFluidState(position).isEmpty())
            return position;
        return null;
    }
}
