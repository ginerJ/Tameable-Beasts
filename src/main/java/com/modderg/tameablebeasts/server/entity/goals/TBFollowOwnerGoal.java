package com.modderg.tameablebeasts.server.entity.goals;

import com.modderg.tameablebeasts.server.entity.abstracts.TBAnimal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

import java.util.EnumSet;

public class TBFollowOwnerGoal extends Goal {

    private final Level level;
    private final TBAnimal mob;
    private LivingEntity owner;
    private final double speedModifier;
    private int timeToRecalcPath = 0;
    private PathNavigation navigation;
    private boolean canFly = false;
    private boolean swims = false;
    private float oldWaterCost;
    private final float startDistance;
    private final float stopDistance;

    public TBFollowOwnerGoal(TBAnimal mob, double p_25295_, float p_25296_, float p_25297_, boolean flies, boolean swims) {
        this(mob, p_25295_, p_25296_, p_25297_, flies);
        this.swims = swims;
    }

    public TBFollowOwnerGoal(TBAnimal mob, double p_25295_, float p_25296_, float p_25297_, boolean flies) {
        this(mob, p_25295_, p_25296_, p_25297_);
        this.canFly = flies;
    }

    public TBFollowOwnerGoal(TBAnimal mob, double p_25295_, float p_25296_, float p_25297_) {
        this.level = mob.level();
        this.mob = mob;
        this.owner = mob.getOwner();
        this.speedModifier = p_25295_;
        this.navigation = mob.getNavigation();
        this.startDistance = p_25296_;
        this.stopDistance = p_25297_;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean canUse() {
        LivingEntity livingentity = this.mob.getOwner();
        if (livingentity == null || mob.isWandering() || livingentity.isSpectator())
            return false;
        else if (this.unableToMove())
            return false;
        else if (this.mob.distanceToSqr(livingentity) < (double)(this.startDistance * this.startDistance))
            return false;
        else {
            this.owner = livingentity;
            return true;
        }
    }

    public boolean canContinueToUse() {
        if (this.navigation.isDone() || mob.isWandering())
            return false;
        else if (this.unableToMove())
            return false;
        else
            return !(this.mob.distanceToSqr(this.owner) <= (double)(this.stopDistance * this.stopDistance));
    }

    private boolean unableToMove() {
        return this.mob.isOrderedToSit() || this.mob.isPassenger() || this.mob.isLeashed();
    }

    public void start() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.mob.getPathfindingMalus(BlockPathTypes.WATER);
    }

    public void stop() {
        this.owner = null;
        this.navigation.stop();
        this.mob.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
    }

    @Override
    public void tick() {
        this.mob.getLookControl().setLookAt(this.owner, 10.0F, (float)this.mob.getMaxHeadXRot());
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = this.adjustedTickDelay(10);
            if (this.mob.distanceToSqr(this.owner) >= 144.0D)
                this.teleportToOwner();

            this.navigation.moveTo(this.owner, this.speedModifier);
        }
    }

    private void teleportToOwner() {
        BlockPos blockpos = this.owner.blockPosition();

        for(int i = 0; i < 10; ++i) {
            int j = this.randomIntInclusive(-3, 3);
            int k = this.randomIntInclusive(-1, 1);
            int l = this.randomIntInclusive(-3, 3);
            boolean flag = this.maybeTeleportTo(blockpos.getX() + j, blockpos.getY() + k, blockpos.getZ() + l);
            if (flag)
                return;
        }

    }

    private boolean maybeTeleportTo(int p_25304_, int p_25305_, int p_25306_) {
        if (Math.abs((double)p_25304_ - this.owner.getX()) < 2.0D && Math.abs((double)p_25306_ - this.owner.getZ()) < 2.0D)
            return false;
        else if (!this.canTeleportTo(new BlockPos(p_25304_, p_25305_, p_25306_)))
            return false;
        else {
            this.mob.moveTo((double)p_25304_ + 0.5D, p_25305_, (double)p_25306_ + 0.5D, this.mob.getYRot(), this.mob.getXRot());
            this.navigation.stop();
            return true;
        }
    }

    private boolean canTeleportTo(BlockPos p_25308_) {
        BlockPathTypes blockpathtypes = WalkNodeEvaluator.getBlockPathTypeStatic(this.level, p_25308_.mutable());

        BlockState blockstate = this.level.getBlockState(p_25308_.below());

        if(this.swims) {
            if(blockpathtypes != BlockPathTypes.WALKABLE && !blockstate.is(Blocks.WATER))
                return false;
        } else if (this.canFly) {
            if(blockpathtypes != BlockPathTypes.WALKABLE && !blockstate.is(Blocks.AIR))
                return false;
        } else if (blockpathtypes != BlockPathTypes.WALKABLE || blockstate.getBlock() instanceof LeavesBlock)
                return false;

        BlockPos blockpos = p_25308_.subtract(this.mob.blockPosition());
        return this.level.noCollision(this.mob, this.mob.getBoundingBox().move(blockpos));
    }

    private int randomIntInclusive(int p_25301_, int p_25302_) {
        return this.mob.getRandom().nextInt(p_25302_ - p_25301_ + 1) + p_25301_;
    }

    public void refreshNavigatorPath() {
        this.navigation = this.mob.getNavigation();
    }
}
