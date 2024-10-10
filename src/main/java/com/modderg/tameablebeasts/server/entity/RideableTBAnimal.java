package com.modderg.tameablebeasts.server.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ItemSteerable;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class RideableTBAnimal extends TBAnimal implements ItemSteerable, TBRideable {

    protected RideableTBAnimal(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("SADDLE", this.hasSaddle());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SADDLE, false);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("SADDLE"))
            this.setSaddle(compound.getBoolean("SADDLE"));
    }

    @NotNull
    public InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (isOwnedBy(player)){
            if (this.hasSaddle() && !isFood(itemstack)){
                if(!player.isShiftKeyDown()){
                    player.startRiding(this);
                    messageRiding(player);

                    return InteractionResult.sidedSuccess(this.level().isClientSide);
                }

            } else if (!this.isBaby() && isSaddle(itemstack)) {
                setSaddle(true);
                this.playSound(SoundEvents.HORSE_SADDLE, 0.15F, 1.0F);
                itemstack.shrink(1);
                return InteractionResult.SUCCESS;
            }
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.isVehicle())
            moving = updateMovingState();
    }

    @Override
    protected void dropAllDeathLoot(@NotNull DamageSource p_21192_) {
        dropSaddle();
        super.dropAllDeathLoot(p_21192_);
    }

    @Override
    public void travel(@NotNull Vec3 pos) {
        if (this.isAlive()) {
            if (this.getControllingPassenger() instanceof Player passenger) {

                this.yRotO = getYRot();
                this.xRotO = getXRot();

                this.setMaxUpStep(1.0f);

                setYRot(passenger.getYRot());
                setXRot(passenger.getXRot() * 0.5f);
                setRot(getYRot(), getXRot());

                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.yBodyRot;
                float x = passenger.xxa * 0.5F;
                float z = passenger.zza;

                if (z <= 0)
                    z *= 0.25f;

                float speedMul = this.getRidingSpeedMultiplier();

                this.setSpeed(applyPotionEffectsToSpeed(this.getAttribute(Attributes.MOVEMENT_SPEED).getBaseValue() * speedMul));

                Vec3 nextPos = new Vec3(x, pos.y, z);

                if (!this.level().noCollision(this, this.getBoundingBox().inflate(nextPos.x,0,nextPos.z).move(nextPos))) {
                    this.horizontalCollision = true;
                }

                super.travel(nextPos);
            } else {
                super.travel(pos);
            }
        }
    }

    public void superTravel(Vec3 pos) {
        super.travel(pos);
    }

    @Override
    public boolean boost() {
        return false;
    }

    public <T extends RideableTBAnimal & GeoEntity> AnimationController<T> vehicleController(T entity) {
        return new AnimationController<>(entity,"movement", 3, event -> vehicleState(entity, event));
    }

    public <T extends RideableTBAnimal & GeoEntity> PlayState vehicleState(T entity, AnimationState<T> event) {
        if(entity.isVehicle()){
            if(entity.moving)
                event.getController().setAnimation(RawAnimation.begin().then("run", Animation.LoopType.LOOP));
            else
                event.getController().setAnimation(RawAnimation.begin().then("sit", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        return groundState(entity, event);
    }

    public boolean moving = false;

    Vec3 previousPosition = this.position();

    public boolean updateMovingState() {
        Vec3 currentPosition = this.position();
        boolean flag = !currentPosition.equals(this.previousPosition);
        previousPosition = currentPosition;
        return flag;
    }
}
