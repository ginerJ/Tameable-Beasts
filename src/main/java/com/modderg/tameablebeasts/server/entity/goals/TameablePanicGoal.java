package com.modderg.tameablebeasts.server.entity.goals;

import com.modderg.tameablebeasts.server.entity.TBAnimal;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.PanicGoal;

public class TameablePanicGoal extends PanicGoal {

    private TBAnimal entity;

    public TameablePanicGoal(PathfinderMob p_25691_, double p_25692_) {
        super(p_25691_, p_25692_);
        this.entity = (TBAnimal) p_25691_;
    }

    @Override
    public boolean canUse() {
        return !entity.isTame() && super.canUse();
    }
}