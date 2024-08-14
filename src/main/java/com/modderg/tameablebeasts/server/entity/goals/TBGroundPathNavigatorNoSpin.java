package com.modderg.tameablebeasts.server.entity.goals;

import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class TBGroundPathNavigatorNoSpin extends GroundPathNavigation {
    private float distancemodifier = 0.75F;

    public TBGroundPathNavigatorNoSpin(Mob entitylivingIn, Level worldIn) {
        super(entitylivingIn, worldIn);
    }

    public TBGroundPathNavigatorNoSpin(Mob entitylivingIn, Level worldIn, float distancemodifier) {
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


    private boolean shouldTargetNextNodeInDirection(Vec3 currentPosition) {
        if (this.path.getNextNodeIndex() + 1 >= this.path.getNodeCount())
            return false;
        else {
            Vec3 vector3d = Vec3.atBottomCenterOf(this.path.getNextNodePos());
            if (!currentPosition.closerThan(vector3d, 2.0D))
                return false;
            else {
                Vec3 vector3d1 = Vec3.atBottomCenterOf(this.path.getNodePos(this.path.getNextNodeIndex() + 1));
                Vec3 vector3d2 = vector3d1.subtract(vector3d);
                Vec3 vector3d3 = currentPosition.subtract(vector3d);
                return vector3d2.dot(vector3d3) > 0.0D;
            }
        }
    }

}