package com.modderg.tameablebeasts.server.entity.goals;

import com.modderg.tameablebeasts.server.entity.abstracts.TBAnimal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;

public class TBFollowParentGoal extends FollowParentGoal {
    private final TBAnimal animal;

    public TBFollowParentGoal(TBAnimal p_25319_, double p_25320_) {
        super(p_25319_, p_25320_);
        this.animal = p_25319_;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !animal.isOrderedToSit();
    }

    @Override
    public boolean canContinueToUse(){
        return super.canContinueToUse() && !animal.isOrderedToSit();
    }
}
