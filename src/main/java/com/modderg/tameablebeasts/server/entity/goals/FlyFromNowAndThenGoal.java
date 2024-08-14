package com.modderg.tameablebeasts.server.entity.goals;

import com.modderg.tameablebeasts.server.entity.FlyingTBAnimal;
import net.minecraft.world.entity.ai.goal.Goal;

public class FlyFromNowAndThenGoal extends Goal {

    public FlyingTBAnimal mob;
    private int timer;

    public FlyFromNowAndThenGoal(FlyingTBAnimal mob) {
        this.mob = mob;
        this.timer = mob.getRandom().nextInt(0,3000);
    }

    @Override
    public boolean canUse() {
        return !mob.isTame() && !mob.isOrderedToSit();
    }

    @Override
    public boolean canContinueToUse() {
        return !mob.isTame() && !mob.isOrderedToSit();
    }

    @Override
    public void tick() {
        if(timer--<=0){
            mob.setGoalsRequireFlying(!mob.getGoalsRequireFlying());
            this.timer = mob.getRandom().nextInt(0,4000);

        } else if (timer < 50 && !mob.onGround() && mob.isFlying())
            mob.setDeltaMovement(mob.getDeltaMovement().x,-0.005f,mob.getDeltaMovement().z);

        super.tick();
    }

    @Override
    public void stop() {
        mob.setGoalsRequireFlying(false);
        super.stop();
    }
}
