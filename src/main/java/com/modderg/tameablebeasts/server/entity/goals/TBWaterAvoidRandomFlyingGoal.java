package com.modderg.tameablebeasts.server.entity.goals;

import com.modderg.tameablebeasts.server.entity.abstracts.FlyingTBAnimal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class TBWaterAvoidRandomFlyingGoal extends Goal {

    protected final FlyingTBAnimal mob;
    protected final double speedModifier;
    protected final int maxInterval;

    protected Vec3 wantedPos;

    protected int interval;

    public TBWaterAvoidRandomFlyingGoal(FlyingTBAnimal mob,  double speedMod, int interval) {
        this.mob = mob;
        this.speedModifier = speedMod;
        this.maxInterval = interval;
        this.interval = mob.getRandom().nextInt(interval);
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (this.mob.isVehicle() || !this.mob.isServerFlying())
            return false;

        if (this.mob.getNoActionTime() >= 100)
            return false;

        if (this.interval > 0 && (!mob.isServerFlying() || mob.getFlightCycleCount() >= 100)){
            interval--;
            return false;
        }

        if(mob.getFlightCycleCount() < 100 && this.mob.isServerFlying())
            wantedPos = this.findLandingSpot();
        else
            wantedPos = this.getPosition();
        return wantedPos != null;
    }

    @Override
    public boolean canContinueToUse() {
        return !this.mob.getNavigation().isDone() && !this.mob.isVehicle();
    }

    @Override
    public void start() {
        this.mob.getNavigation().moveTo(wantedPos.x, wantedPos.y, wantedPos.z, this.speedModifier);
    }


    @Override
    public void stop() {
        this.interval = mob.getRandom().nextInt(maxInterval);
        this.mob.getNavigation().stop();
        super.stop();
    }

    @Nullable
    protected Vec3 getPosition() {

        Vec3 look = this.mob.getViewVector(0.0F);
        int range = 15;

        Vec3 hover = HoverRandomPos.getPos(this.mob, range, 7, look.x, look.z, (float)Math.PI / 2F, 3, 1);
        if (hover != null) return hover;

        return AirAndWaterRandomPos.getPos(this.mob, range, 4, -2, look.x, look.z, (float)Math.PI / 2F);
    }

    @Nullable
    protected Vec3 findLandingSpot() {
        if (mob.onGround())
            return null;

        BlockPos pos = mob.blockPosition();
        Level level = mob.level();

        while (pos.getY() > level.getMinBuildHeight() && level.isEmptyBlock(pos))
            pos = pos.below();

        BlockPos landing = pos.above();
        if (level.isEmptyBlock(landing))
            return Vec3.atCenterOf(landing);

        return null;
    }
}
