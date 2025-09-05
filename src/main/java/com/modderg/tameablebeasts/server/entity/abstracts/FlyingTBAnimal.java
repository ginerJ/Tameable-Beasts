package com.modderg.tameablebeasts.server.entity.abstracts;

import com.modderg.tameablebeasts.server.entity.goals.*;
import com.modderg.tameablebeasts.server.entity.navigation.TBFlyingPathNavigation;
import com.modderg.tameablebeasts.server.entity.navigation.TBGroundPathNavigation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import static com.modderg.tameablebeasts.constants.TBConstants.*;


public class FlyingTBAnimal extends TBAnimal {

    private static final EntityDataAccessor<Boolean> GOAL_WANT_FLYING = SynchedEntityData.defineId(FlyingTBAnimal.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_FLYING = SynchedEntityData.defineId(FlyingTBAnimal.class, EntityDataSerializers.BOOLEAN);

    private final MoveControl groundMoveControl;
    private final PathNavigation groundPathNavigation;

    private final FlyingMoveControl flyingMoveControl;
    private final PathNavigation TBFlyingPathNavigation;

    private boolean serverFlying = false;

    protected int flightCycleCount = this.random.nextInt(MOB_FLIGHT_CYCLE_TICKS);

    protected TBFollowOwnerGoal followOwnerGoal;

    protected FlyingTBAnimal(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);

        groundMoveControl = (this.moveControl = new MoveControl(this));
        groundPathNavigation = (this.navigation = new TBGroundPathNavigation(this, this.level()));

        flyingMoveControl = new FlyingMoveControl(this, 20, false);
        TBFlyingPathNavigation = new TBFlyingPathNavigation(this, this.level()).canFloat(true);

        this.setPathfindingMalus(BlockPathTypes.WATER, -8f);
        this.setPathfindingMalus(BlockPathTypes.LAVA, -8f);
    }

    public boolean isServerFlying() {return serverFlying;}
    public boolean isClientFlying() {return this.entityData.get(IS_FLYING);}

    public void setGoalsRequireFlying(boolean i){
        this.getEntityData().set(GOAL_WANT_FLYING, i);
    }

    public boolean getGoalsRequireFlying(){
        return this.getEntityData().get(GOAL_WANT_FLYING);
    }

    public int getFlightCycleCount(){
        return flightCycleCount;
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("GOAL_WANT_FLYING", this.getGoalsRequireFlying());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(GOAL_WANT_FLYING, !this.onGround());
        this.entityData.define(IS_FLYING, false);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        if (compound.contains("GOAL_WANT_FLYING"))
            this.setGoalsRequireFlying(compound.getBoolean("GOAL_WANT_FLYING"));
    }

    protected void registerGoals() {
        super.registerGoals();
        followOwnerGoal = new TBFollowOwnerGoal(this, 1.0D, 10f, 6F, true);
        this.goalSelector.addGoal(0, followOwnerGoal);
    }

    protected Boolean shouldFly(){
        return !isOrderedToSit();
    }

    protected void switchNavigation(){

        boolean shouldFly = this.shouldFly();

        this.serverFlying = shouldFly;
        this.entityData.set(IS_FLYING, shouldFly);

        this.moveControl = shouldFly ? flyingMoveControl : groundMoveControl;
        this.navigation = shouldFly ? TBFlyingPathNavigation : groundPathNavigation;

        this.setNoGravity(shouldFly);

        followOwnerGoal.refreshNavigatorPath();
    }

    public boolean isStill() {
        return !(this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-3D);
    }

    @Override
    public void setOrderedToSit(boolean p_21840_) {
        super.setOrderedToSit(p_21840_);
    }

    @Override
    public void tick() {

        if(flightCycleCount-- < 0){
            this.setGoalsRequireFlying(!this.getGoalsRequireFlying());
            flightCycleCount = this.random.nextInt(MOB_FLIGHT_CYCLE_TICKS);
        }

        int uFlyModule = this.isTame() ? 5: 20;

        if(!level().isClientSide() && this.tickCount % uFlyModule == 0 && this.shouldFly() != this.isServerFlying())
            switchNavigation();

        super.tick();
    }

    @Override
    public void travel(@NotNull Vec3 p_218382_) {
        if(isServerFlying() && this.isControlledByLocalInstance()){
            if (this.isInWater()) {
                this.moveRelative(0.02F, p_218382_);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale( 0.8F));
            } else if (this.isInLava()) {
                this.moveRelative(0.02F, p_218382_);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
            } else {
                this.moveRelative(this.getSpeed(), p_218382_);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale( 0.91F));
            }
        } else
            super.travel(p_218382_);
    }

    protected boolean isOverFluidOrVoid() {
        BlockPos.MutableBlockPos pos = this.blockPosition().mutable();
        Level level = this.level();

        for (int i = 0; i < 15 && pos.getY() > level.getMinBuildHeight(); i++) {
            if (!level.isEmptyBlock(pos) || !level.getFluidState(pos).isEmpty()) {
                return !level.getFluidState(pos).isEmpty();
            }
            pos.move(Direction.DOWN);
        }

        return pos.getY() <= level.getMinBuildHeight();
    }


    @Override
    public boolean isNoGravity() {
        return this.isServerFlying();
    }

    @Override
    public boolean causeFallDamage(float p_147187_, float p_147188_, @NotNull DamageSource p_147189_) {
        return false;
    }
}
