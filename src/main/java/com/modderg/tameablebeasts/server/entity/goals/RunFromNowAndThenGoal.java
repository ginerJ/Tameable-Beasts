package com.modderg.tameablebeasts.server.entity.goals;

import com.modderg.tameablebeasts.server.entity.TBAnimal;
import net.minecraft.world.entity.ai.goal.Goal;

public class RunFromNowAndThenGoal extends Goal {

    TBAnimal mob;
    float speedModifier;
    private int timer;
    int maxWait = 1000;

    public RunFromNowAndThenGoal(TBAnimal mob, float speedModifier) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.timer = mob.getRandom().nextInt(0,maxWait);
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
            mob.setRunning(!mob.isRunning());
            this.timer = mob.getRandom().nextInt(0,maxWait);
        }
        super.tick();
    }

    @Override
    public void stop() {
        mob.setRunning(false);
        super.stop();
    }
}
