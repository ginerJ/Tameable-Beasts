package com.modderg.tameablebeasts.entities.goals;

import com.modderg.tameablebeasts.entities.FlyingTameableGAnimal;
import net.minecraft.world.entity.ai.goal.Goal;

public class FlyFromNowAndThen extends Goal {

    public FlyingTameableGAnimal mob;
    private int timer;

    public FlyFromNowAndThen(FlyingTameableGAnimal mob) {
        this.mob = mob;
        this.timer = mob.getRandom().nextInt(0,4000);
    }

    @Override
    public boolean canUse() {
        return !mob.isOrderedToSit();
    }

    @Override
    public boolean canContinueToUse() {
        return !mob.isOrderedToSit();
    }

    @Override
    public void tick() {
        if(timer--<=0){
            mob.setGoalsRequireFlying(!mob.getGoalsRequireFlying());
            this.timer = mob.getRandom().nextInt(0,4000);
        } else if (timer < 50 && !mob.isOnGround() && mob.isFlying()){
            mob.setDeltaMovement(mob.getDeltaMovement().x,-0.005f,mob.getDeltaMovement().z);
        }
        super.tick();
    }

    @Override
    public void stop() {
        mob.setGoalsRequireFlying(false);
        super.stop();
    }
}
