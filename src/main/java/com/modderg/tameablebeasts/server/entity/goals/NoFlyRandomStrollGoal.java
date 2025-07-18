package com.modderg.tameablebeasts.server.entity.goals;

import com.modderg.tameablebeasts.server.entity.abstracts.FlyingTBAnimal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;

public class NoFlyRandomStrollGoal extends RandomStrollGoal {

    FlyingTBAnimal flyingMob;

    public NoFlyRandomStrollGoal(FlyingTBAnimal p_25734_, double p_25735_) {
        super(p_25734_, p_25735_);
        this.flyingMob = p_25734_;
    }

    @Override
    public boolean canUse() {
        if (flyingMob.isFlying())
            return false;
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if (flyingMob.isFlying())
            return false;
        return super.canContinueToUse();
    }
}
