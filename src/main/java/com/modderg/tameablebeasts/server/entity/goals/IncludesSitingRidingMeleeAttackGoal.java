package com.modderg.tameablebeasts.server.entity.goals;

import com.modderg.tameablebeasts.server.entity.TBAnimal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class IncludesSitingRidingMeleeAttackGoal extends MeleeAttackGoal {

    private TBAnimal animal;

    public IncludesSitingRidingMeleeAttackGoal(TBAnimal p_25552_, double p_25553_, boolean p_25554_) {
        super(p_25552_, p_25553_, p_25554_);
        this.animal = p_25552_;
    }

    @Override
    public boolean canUse() {
        return !animal.isOrderedToSit() && !animal.hasControllingPassenger() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return !animal.isOrderedToSit() && !animal.hasControllingPassenger() && super.canContinueToUse();
    }
}
