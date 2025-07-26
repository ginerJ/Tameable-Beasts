package com.modderg.tameablebeasts.server.entity.navigation;

import com.modderg.tameablebeasts.server.entity.abstracts.TBAnimal;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class TBGroundPathNavigation extends GroundPathNavigation {

    private float distanceModifier = 0.75F;
    private final TBAnimal tbAnimal;

    public TBGroundPathNavigation(TBAnimal tbAnimal, Level worldIn) {
        super(tbAnimal, worldIn);
        this.tbAnimal = tbAnimal;
    }

    public TBGroundPathNavigation(TBAnimal tbAnimal, Level worldIn, float distancemodifier) {
        this(tbAnimal, worldIn);
        this.distanceModifier = distancemodifier;
    }

    @Override
    public void tick() {
        super.tick();
    }

    protected void followThePath() {
        Vec3 currentPos = this.getTempMobPos();
        this.maxDistanceToWaypoint = this.mob.getBbWidth() * distanceModifier;
        Vec3i nextNodePos = this.path.getNextNodePos();
        double dx = Math.abs(this.mob.getX() - (nextNodePos.getX() + 0.5));
        double dy = Math.abs(this.mob.getY() - nextNodePos.getY());
        double dz = Math.abs(this.mob.getZ() - (nextNodePos.getZ() + 0.5));

        if (dx < this.maxDistanceToWaypoint && dz < this.maxDistanceToWaypoint && dy < 1.0D) {
            this.path.advance();
        }
        this.doStuckDetection(currentPos);
    }
}