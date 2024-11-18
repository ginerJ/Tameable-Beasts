package com.modderg.tameablebeasts.server.entity.navigation;

import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class TBGroundPathNavigation extends GroundPathNavigation {
    private float distancemodifier = 0.75F;

    public TBGroundPathNavigation(Mob entitylivingIn, Level worldIn) {
        super(entitylivingIn, worldIn);
    }

    public TBGroundPathNavigation(Mob entitylivingIn, Level worldIn, float distancemodifier) {
        super(entitylivingIn, worldIn);
        this.distancemodifier = distancemodifier;
    }

    protected void followThePath() {
        Vec3 currentPos = this.getTempMobPos();
        this.maxDistanceToWaypoint = this.mob.getBbWidth() * distancemodifier;
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