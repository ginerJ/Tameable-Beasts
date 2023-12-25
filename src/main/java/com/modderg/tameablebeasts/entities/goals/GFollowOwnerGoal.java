package com.modderg.tameablebeasts.entities.goals;

import com.modderg.tameablebeasts.entities.TameableGAnimal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;

public class GFollowOwnerGoal extends FollowOwnerGoal {

    private final TameableGAnimal mob;

    public GFollowOwnerGoal(TameableGAnimal mob, double p_25295_, float p_25296_, float p_25297_, boolean p_25298_) {
        super(mob, p_25295_, p_25296_, p_25297_, p_25298_);
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        if(mob.isWandering()){
            return false;
        }
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if(mob.isWandering()){
            return false;
        }
        return super.canContinueToUse();
    }
}
