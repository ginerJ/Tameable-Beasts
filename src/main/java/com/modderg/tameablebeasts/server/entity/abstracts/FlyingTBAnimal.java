package com.modderg.tameablebeasts.server.entity.abstracts;

import com.modderg.tameablebeasts.server.entity.goals.*;
import com.modderg.tameablebeasts.server.entity.navigation.TBFlyingPathNavigation;
import com.modderg.tameablebeasts.registry.TBPacketRegistry;
import com.modderg.tameablebeasts.server.packet.StoCSyncFlyingPacket;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;


public class FlyingTBAnimal extends TBAnimal {

    protected boolean isFlying = true;

    public boolean isFlying() {return isFlying;}
    public void setIsFlying(boolean flying) {this.isFlying = flying;}

    private static final EntityDataAccessor<Boolean> GOAL_WANT_FLYING = SynchedEntityData.defineId(FlyingTBAnimal.class, EntityDataSerializers.BOOLEAN);
    public void setGoalsRequireFlying(boolean i){
        this.getEntityData().set(GOAL_WANT_FLYING, i);
    }
    public boolean getGoalsRequireFlying(){
        return this.getEntityData().get(GOAL_WANT_FLYING);
    }

    protected TBFollowOwnerGoal followOwnerGoal;

    protected FlyingTBAnimal(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);

        this.setPathfindingMalus(BlockPathTypes.WATER, -8f);
        this.setPathfindingMalus(BlockPathTypes.LAVA, -8f);
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

        if(moveControl instanceof FlyingMoveControl){
            this.moveControl = this.createMoveControl();
            this.navigation = this.createNavigation(this.level());
        } else {
            this.moveControl = new FlyingMoveControl(this, 20, false);
            this.navigation = new TBFlyingPathNavigation(this, this.level()).canFloat(true);
            this.jumpControl.jump();
        }

        isFlying = moveControl instanceof FlyingMoveControl;

        this.setNoGravity(isFlying);

        followOwnerGoal.refreshNavigatorPath();
        TBPacketRegistry.sendToAll(new StoCSyncFlyingPacket(this.getId(), isFlying));
    }

    public boolean isStill() {
        return !(this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-3D);
    }

    @Override
    public void setOrderedToSit(boolean p_21840_) {
        super.setOrderedToSit(p_21840_);
    }

    int updateFlyCount = 1;

    @Override
    public void tick() {
        if(!level().isClientSide() && updateFlyCount++ % 15 == 0 && this.shouldFly() != this.isFlying())
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
