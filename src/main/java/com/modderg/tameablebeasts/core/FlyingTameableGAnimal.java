package com.modderg.tameablebeasts.core;

import com.modderg.tameablebeasts.entities.RacoonEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FlyingTameableGAnimal extends TameableGAnimal {
    protected int flyFollowRange = 0;

    protected boolean isFlyMoving = false;
    protected Vec3 lastPos = new Vec3(0,0,0);
    protected static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(RacoonEntity.class, EntityDataSerializers.BOOLEAN);
    public void setFlying(boolean i){
        this.getEntityData().set(FLYING, i);
    }
    public boolean getFlying(){
        return this.getEntityData().get(FLYING);
    }
    protected FlyingTameableGAnimal(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.switchNavigation(getFlying());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FLYING, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("FLYING")) {
            this.setFlying(compound.getBoolean("FLYING"));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("FLYING", this.getFlying());
    }

    protected Boolean shouldFly(){
        if(this.isTame()){
            if(this.isBaby()){
                return false;
            }
            if(this.isInSittingPose()){
                return false;
            }
            if(this.isAggressive()){
                return true;
            }
            if(this.m_269323_() != null){
                if(this.isAggressive()){
                    return true;
                }
                if(this.distanceTo(this.m_269323_()) > flyFollowRange){
                    return true;
                }
                if(getFlying() && !this.m_269323_().isOnGround()){
                    return true;
                }
            }
        }
        return false;
    }

    protected void switchNavigation(Boolean b){
        if(b && !(moveControl instanceof FlyingMoveControl)){
            this.moveControl = new FlyingMoveControl(this, 20, true);
            this.navigation = new FlyingPathNavigation(this, this.getLevel());
            setFlying(true);
        } else if (!b && (moveControl instanceof FlyingMoveControl)){
            this.moveControl = new MoveControl(this);
            this.navigation = new GroundPathNavigation(this, this.getLevel());
            this.setNoGravity(false);
            setFlying(false);
        }
    }

    @Override
    public void tick() {
        if(this.position() ==  lastPos){
            isFlyMoving = false;
        } else {
            isFlyMoving = true;
        }
        lastPos = this.position();
        this.switchNavigation(shouldFly());
        super.tick();
    }

    @Override
    public void travel(Vec3 p_218382_) {
        if(shouldFly()){
            if (this.isControlledByLocalInstance()) {
                if (this.isInWater()) {
                    this.moveRelative(0.02F, p_218382_);
                    this.move(MoverType.SELF, this.getDeltaMovement());
                    this.setDeltaMovement(this.getDeltaMovement().scale((double) 0.8F));
                } else if (this.isInLava()) {
                    this.moveRelative(0.02F, p_218382_);
                    this.move(MoverType.SELF, this.getDeltaMovement());
                    this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
                } else {
                    this.moveRelative(this.getSpeed(), p_218382_);
                    this.move(MoverType.SELF, this.getDeltaMovement());
                    this.setDeltaMovement(this.getDeltaMovement().scale((double) 0.91F));
                }
            }
        } else {
            super.travel(p_218382_);
        }
    }

    protected boolean isStill() {
        return !(this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-3D);
    }
}
