package com.modderg.tameablebeasts.server.entity;

import com.modderg.tameablebeasts.server.entity.goals.*;
import com.modderg.tameablebeasts.server.entity.navigation.TBFlyingPathNavigation;
import com.modderg.tameablebeasts.server.entity.navigation.TBGroundPathNavigation;
import com.modderg.tameablebeasts.server.packet.InitPackets;
import com.modderg.tameablebeasts.server.packet.StoCSyncFlying;
import net.minecraft.core.BlockPos;
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

import java.util.LinkedList;

public class FlyingTBAnimal extends TBAnimal {

    protected boolean isFlying = true;

    public boolean isFlying() {return isFlying;}
    public void setIsFlying(boolean flying) {isFlying = flying;}

    private static final EntityDataAccessor<Boolean> GOAL_WANT_FLYING = SynchedEntityData.defineId(TBAnimal.class, EntityDataSerializers.BOOLEAN);
    public void setGoalsRequireFlying(boolean i){
        this.getEntityData().set(GOAL_WANT_FLYING, i);
    }
    public boolean getGoalsRequireFlying(){
        return this.getEntityData().get(GOAL_WANT_FLYING);
    }

    protected LinkedList<MoveControl> moveControlRotation = new LinkedList<>();
    protected LinkedList<PathNavigation> pathNavigationRotation = new LinkedList<>();

    protected TBFollowOwnerGoal followOwnerGoal;

    protected FlyingTBAnimal(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);

        this.setPathfindingMalus(BlockPathTypes.WATER, -8f);
        this.setPathfindingMalus(BlockPathTypes.LAVA, -8f);

        moveControlRotation.add(new FlyingMoveControl(this, 20, false));
        moveControlRotation.add(new MoveControl(this));

        pathNavigationRotation.add(new TBFlyingPathNavigation(this, this.level()).canFloat(true));
        pathNavigationRotation.add(new TBGroundPathNavigation(this, this.level()));

        this.moveControl = moveControlRotation.getFirst();
        this.navigation = pathNavigationRotation.getFirst();
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("GOAL_WANT_FLYING", this.getGoalsRequireFlying());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(GOAL_WANT_FLYING, false);
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

        moveControlRotation.addFirst(moveControlRotation.removeLast());
        pathNavigationRotation.addFirst(pathNavigationRotation.removeLast());

        this.moveControl = moveControlRotation.getFirst();
        this.navigation = pathNavigationRotation.getFirst();

        isFlying = moveControl instanceof FlyingMoveControl;

        this.setNoGravity(isFlying);

        if(isFlying)
            this.jumpControl.jump();

        followOwnerGoal.refreshNavigatorPath();
        InitPackets.sendToAll(new StoCSyncFlying(this.getId(), isFlying));
    }

    public boolean isStill() {
        return !(this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-3D);
    }

    int updateFlyCount = 5;

    @Override
    public void tick() {
        if(!level().isClientSide() && updateFlyCount++ % 10 == 0 && this.shouldFly() != isFlying())
            switchNavigation();
        super.tick();
    }

    @Override
    public void travel(@NotNull Vec3 p_218382_) {
        if(isFlying() && this.isControlledByLocalInstance()){
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
        BlockPos position = this.blockPosition();
        Level level = this.level();

        while (position.getY() > level.getMinBuildHeight() && level.isEmptyBlock(position) && level.getFluidState(position).isEmpty())
            position = position.below();

        return !level.getFluidState(position).isEmpty() ||
                position.getY() <= level.getMinBuildHeight();
    }

    @Override
    public boolean isNoGravity() {
        return this.isFlying();
    }

    @Override
    public boolean causeFallDamage(float p_147187_, float p_147188_, @NotNull DamageSource p_147189_) {
        return false;
    }

    public <T extends FlyingTBAnimal & GeoEntity> PlayState flyState(T entity, software.bernie.geckolib.core.animation.AnimationState<T> event) {
        if(entity.isStill())
            event.getController().setAnimation(RawAnimation.begin().then("fly_idle", Animation.LoopType.LOOP));
        else
            event.getController().setAnimation(RawAnimation.begin().then("fly", Animation.LoopType.LOOP));

        return PlayState.CONTINUE;
    }

    public <T extends FlyingTBAnimal & GeoEntity> AnimationController<T> flyController(T entity) {
        return new AnimationController<>(entity,"movement", 5, event ->{
            if(entity.isFlying())
                return flyState(entity, event);

            return groundState(entity, event);
        });
    }

    public <T extends FlyingTBAnimal & GeoEntity> AnimationController<T> justFlyController(T entity) {
        return new AnimationController<>(entity,"movement", 10, event ->{
            if(!entity.isInSittingPose() || !entity.isStill())
                return flyState(entity, event);

            event.setAnimation(RawAnimation.begin().then("sit", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        });
    }
}
