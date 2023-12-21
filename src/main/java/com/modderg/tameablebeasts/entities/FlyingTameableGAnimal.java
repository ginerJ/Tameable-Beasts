package com.modderg.tameablebeasts.entities;

import com.modderg.tameablebeasts.entities.custom.RacoonEntity;
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
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FlyingTameableGAnimal extends TameableGAnimal {

    protected static final EntityDataAccessor<Boolean> ISFLYING = SynchedEntityData.defineId(FlyingTameableGAnimal.class, EntityDataSerializers.BOOLEAN);

    protected static final EntityDataAccessor<Boolean> GOALSWANTFLYING = SynchedEntityData.defineId(FlyingTameableGAnimal.class, EntityDataSerializers.BOOLEAN);


    protected FlyingTameableGAnimal(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
    }

    protected Boolean shouldFly(){
        return !isOrderedToSit();
    }

    protected void switchNavigation(Boolean b){
        if(b && !(moveControl instanceof FlyingMoveControl)){
            this.moveControl = new FlyingMoveControl(this, 20, true);
            this.navigation = new FlyingPathNavigation(this, this.getLevel());
            setIsFlying(true);
            this.jumpControl.jump();
        } else if (!b && (moveControl instanceof FlyingMoveControl)){
            this.moveControl = new MoveControl(this);
            this.navigation = new GroundPathNavigation(this, this.getLevel());
            this.setNoGravity(false);
            setIsFlying(false);
        }
    }

    public void setIsFlying(boolean setfly) {
        this.entityData.set(ISFLYING,setfly);
    }

    public boolean isFlying() {
        return this.entityData.get(ISFLYING);
    }
    
    public void setGoalsRequireFlying(boolean goalsRequireFlying) {
        this.entityData.set(GOALSWANTFLYING,goalsRequireFlying);
    }

    public boolean getGoalsRequireFlying() {
        return this.entityData.get(GOALSWANTFLYING);
    }

    protected boolean isStill() {
        return !(this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-3D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(GOALSWANTFLYING, false);
        this.entityData.define(ISFLYING, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("ISFLYING", this.getGoalsRequireFlying());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("ISFLYING")) {
            this.setGoalsRequireFlying(compound.getBoolean("ISFLYING"));
        }
    }

    @Override
    public void tick() {
        if(this.shouldFly() != isFlying()){
            switchNavigation(shouldFly());
        }
        super.tick();
    }

    @Override
    public void travel(Vec3 p_218382_) {
        if(shouldFly()){
            if (this.isControlledByLocalInstance()) {
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
            }
        } else {
            super.travel(p_218382_);
        }
    }

    @Override
    public boolean isNoGravity() {
        return this.isFlying();
    }

    @Override
    public boolean causeFallDamage(float p_147187_, float p_147188_, DamageSource p_147189_) {
        return false;
    }
}
