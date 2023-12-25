package com.modderg.tameablebeasts.entities;

import com.modderg.tameablebeasts.entities.custom.FlyingBeetleEntity;
import com.modderg.tameablebeasts.entities.custom.RacoonEntity;
import com.modderg.tameablebeasts.entities.goals.GFollowOwnerGoal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class FlyingTameableGAnimal extends TameableGAnimal {

    protected static final EntityDataAccessor<Boolean> ISFLYING = SynchedEntityData.defineId(FlyingTameableGAnimal.class, EntityDataSerializers.BOOLEAN);

    protected static final EntityDataAccessor<Boolean> GOALSWANTFLYING = SynchedEntityData.defineId(FlyingTameableGAnimal.class, EntityDataSerializers.BOOLEAN);

    protected Goal followOwnerGoal = new GFollowOwnerGoal(this, 1.0D, 6.0F, 2.0F, true);

    protected FlyingTameableGAnimal(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        switchNavigation(shouldFly());
    }

    protected Boolean shouldFly(){
        return !isOrderedToSit();
    }

    protected void switchNavigation(Boolean b){
        if(b && !(moveControl instanceof FlyingMoveControl)){
            this.moveControl = new FlyingMoveControl(this, 20, true);
            this.navigation = new FlyingPathNavigation(this, this.level());
            setIsFlying(true);
            this.jumpControl.jump();
        } else if (!b && (moveControl instanceof FlyingMoveControl)){
            this.moveControl = new MoveControl(this);
            this.navigation = new GroundPathNavigation(this, this.level());
            this.setNoGravity(false);
            setIsFlying(false);
        }
        if (this.level() != null && !this.level().isClientSide) {
            this.goalSelector.removeGoal(followOwnerGoal);
            followOwnerGoal = new GFollowOwnerGoal(this, 1.0D, 6.0F, 2.0F, true);
            this.goalSelector.addGoal(4,followOwnerGoal);
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
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
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

    public static <T extends FlyingTameableGAnimal & GeoEntity> AnimationController<T> flyController(T entity) {
        return new AnimationController<>(entity,"movement", 5, event ->{
            if(entity.isInSittingPose()){
                event.getController().setAnimation(RawAnimation.begin().then("sit", Animation.LoopType.LOOP));
            } else {
                if(entity.isFlying()){
                    if(entity.isStill()){
                        event.getController().setAnimation(RawAnimation.begin().then("fly_idle", Animation.LoopType.LOOP));
                    } else {
                        event.getController().setAnimation(RawAnimation.begin().then("fly", Animation.LoopType.LOOP));
                    }
                } else {
                    if(event.isMoving()){
                        event.getController().setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
                    } else {
                        event.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
                    }
                }
            }
            return PlayState.CONTINUE;
        });
    }
}
