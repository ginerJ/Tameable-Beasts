package com.modderg.tameablebeasts.entities.goals;

import java.util.EnumSet;
import javax.annotation.Nullable;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.entities.TameableGAnimal;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;

public class TameablePanicGoal extends PanicGoal {

    private TameableGAnimal entity;

    public TameablePanicGoal(PathfinderMob p_25691_, double p_25692_) {
        super(p_25691_, p_25692_);
        this.entity = (TameableGAnimal) p_25691_;
    }

    @Override
    public boolean canUse() {
        return !entity.isTame() && super.canUse();
    }
}