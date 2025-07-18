package com.modderg.tameablebeasts.server.entity.goals;

import com.modderg.tameablebeasts.server.entity.abstracts.TBAnimal;
import com.modderg.tameablebeasts.server.entity.abstracts.TBSemiAquatic;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class SemiAquaticRandomStrollGoal extends RandomStrollGoal {

    TBAnimal tameable;

    public SemiAquaticRandomStrollGoal(TBAnimal mob, double speed) {
        super(mob, speed, 10);
        tameable = mob;
    }

    @Override
    public boolean canUse() {
        return !tameable.isOrderedToSit() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return !tameable.isOrderedToSit() && super.canContinueToUse();
    }

    @Nullable
    protected Vec3 getPosition() {
        if(tameable instanceof TBSemiAquatic && tameable.isInWater())
            return BehaviorUtils.getRandomSwimmablePos(this.tameable, 10, 7);
        return super.getPosition();
    }
}
