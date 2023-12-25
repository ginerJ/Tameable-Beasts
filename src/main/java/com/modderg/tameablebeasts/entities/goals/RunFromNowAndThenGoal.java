package com.modderg.tameablebeasts.entities.goals;

import com.modderg.tameablebeasts.entities.TameableGAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;

public class RunFromNowAndThenGoal extends Goal {

    TameableGAnimal mob;
    float speedModifier;
    private int timer;

    public RunFromNowAndThenGoal(TameableGAnimal mob, float speedModifier) {
        this.mob = mob;
        this.speedModifier = speedModifier;
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
            mob.setRunning(!mob.isRunning());
            this.timer = mob.getRandom().nextInt(0,3000);
        }
        super.tick();
    }

    @Override
    public void stop() {
        mob.setRunning(false);
        super.stop();
    }
}
