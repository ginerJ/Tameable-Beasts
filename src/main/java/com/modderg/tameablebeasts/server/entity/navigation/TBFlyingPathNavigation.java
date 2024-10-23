package com.modderg.tameablebeasts.server.entity.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class TBFlyingPathNavigation extends FlyingPathNavigation {
    private float distancemodifier = 1.5F;

    public TBFlyingPathNavigation(Mob p_26424_, Level p_26425_) {
        super(p_26424_, p_26425_);
    }

    @Override
    public boolean isStableDestination(BlockPos pos) {
        return !this.level.getBlockState(pos.below()).isAir();
    }

    @Override
    protected void followThePath() {
        Vec3 vector3d = this.getTempMobPos();
        this.maxDistanceToWaypoint = this.mob.getBbWidth() * distancemodifier;
        Vec3i vector3i = this.path.getNextNodePos();

        double d0 = Math.abs(this.mob.getX() - ((double) vector3i.getX() + 0.5D));
        double d1 = Math.abs(this.mob.getY() - (double) vector3i.getY());
        double d2 = Math.abs(this.mob.getZ() - ((double) vector3i.getZ() + 0.5D));

        boolean flag = d0 < (double) this.maxDistanceToWaypoint && d2 < (double) this.maxDistanceToWaypoint && d1 < 1.0D;

        if (flag || this.canCutCorner(this.path.getNextNode().type) && shouldTargetNextNodeInDirection(vector3d))
            this.path.advance();

        this.doStuckDetection(vector3d);
    }

    private boolean shouldTargetNextNodeInDirection(Vec3 currentPosition) {
        if (this.path.getNextNodeIndex() + 1 >= this.path.getNodeCount())
            return false;
        else {
            Vec3 vec3d = Vec3.atBottomCenterOf(this.path.getNextNodePos());
            if (!currentPosition.closerThan(vec3d, 2.0D))
                return false;
            else {
                Vec3 vec1 = Vec3.atBottomCenterOf(this.path.getNodePos(this.path.getNextNodeIndex() + 1));
                Vec3 vec2 = vec1.subtract(vec3d);
                Vec3 vec3 = currentPosition.subtract(vec3d);
                return vec2.dot(vec3) > 0.0D;
            }
        }
    }

    public TBFlyingPathNavigation canFloat(boolean canFloat) {
        this.setCanFloat(canFloat);
        return this;
    }
}
