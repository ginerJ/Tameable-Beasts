package com.modderg.tameablebeasts.entities;

import com.modderg.tameablebeasts.item.ItemInit;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

import static net.minecraft.world.entity.ai.attributes.Attributes.FLYING_SPEED;
import static net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED;

public class FlyingRideableGAnimal extends FlyingTameableGAnimal implements ItemSteerable, PlayerRideableJumping {

    private static final EntityDataAccessor<Boolean> SADDLE = SynchedEntityData.defineId(FlyingRideableGAnimal.class, EntityDataSerializers.BOOLEAN);
    public void setSaddle(boolean i){
        this.getEntityData().set(SADDLE, i);
    }
    public boolean getSaddle(){
        return this.getEntityData().get(SADDLE);
    }

    protected boolean isSaddle(ItemStack itemStack) {
        return false;
    }

    protected static final EntityDataAccessor<Boolean> RIDERWANTSFLYING = SynchedEntityData.defineId(FlyingRideableGAnimal.class, EntityDataSerializers.BOOLEAN);
    public void setRiderWantsFlying(boolean riderWantsFlying) {this.entityData.set(RIDERWANTSFLYING,riderWantsFlying);}
    public boolean getRiderWantsFlying() {
        return this.entityData.get(RIDERWANTSFLYING);
    }

    protected FlyingRideableGAnimal(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("SADDLE", this.getSaddle());
        compound.putBoolean("RIDERWANTSFLYING", this.getRiderWantsFlying());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SADDLE, false);
        this.entityData.define(RIDERWANTSFLYING, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("SADDLE")) {
            this.setSaddle(compound.getBoolean("SADDLE"));
        }if (compound.contains("RIDERWANTSFLYING")) {
            this.setRiderWantsFlying(compound.getBoolean("RIDERWANTSFLYING"));
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (isOwnedBy(player)){
            if (this.getSaddle()){
                if(!player.isShiftKeyDown() && !this.isInSittingPose()){
                    player.startRiding(this);
                    return InteractionResult.sidedSuccess(this.getLevel().isClientSide);
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
    protected Boolean shouldFly() {
        return  super.shouldFly();
    }

    @Override
    public void travel(Vec3 vec3) {
        if (this.isAlive()) {
            if (this.canBeControlledByRider()) {
                LivingEntity passenger = getControllingPassenger();
                this.yRotO = getYRot();
                this.xRotO = getXRot();

                this.m_274367_(1.0f);

                setYRot(passenger.getYRot());
                setXRot(passenger.getXRot() * 0.5f);
                setRot(getYRot(), getXRot());

                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.yBodyRot;
                float x = passenger.xxa * 0.5F;
                float z = passenger.zza;

                if (z <= 0)
                    z *= 0.25f;

                this.setSpeed(0.1f);

                if(!getRiderWantsFlying()){
                    super.travel(new Vec3(x, vec3.y, z));
                } else if (getRiderWantsFlying())
                {
                    float speed = (float) getAttributeValue(isFlying()? FLYING_SPEED : MOVEMENT_SPEED);
                    if (passenger instanceof Player p && p.getInventory().getArmor(3).is(ItemInit.FLYING_HELMET.get())){
                        speed = speed * 0.4f;
                    } else {
                        speed = speed * 0.2f;
                    }

                    moveDist = moveDist > 0? moveDist : 0;
                    float movY = (float) (-passenger.getXRot() * (Math.PI / 180));
                    vec3 = new Vec3(x, movY, z);
                    moveRelative(speed, vec3);
                    move(MoverType.SELF, getDeltaMovement());
                    if (getDeltaMovement().lengthSqr() < 0.1)
                        setDeltaMovement(getDeltaMovement().add(0, Math.sin(tickCount / 4f) * 0.03, 0));
                    setDeltaMovement(getDeltaMovement().scale(0.9f));
                }

            } else if (isFlying()) {
                if (this.isControlledByLocalInstance()) {
                    if (this.isInLava()) {
                        this.moveRelative(0.02F, vec3);
                        this.move(MoverType.SELF, this.getDeltaMovement());
                        this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
                    } else {
                        this.moveRelative(this.getSpeed(), vec3);
                        this.move(MoverType.SELF, this.getDeltaMovement());
                        this.setDeltaMovement(this.getDeltaMovement().scale( 0.91F));
                    }
                }
            } else {
                super.travel(vec3);
            }
        }
    }

    @Nullable
    public LivingEntity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : (LivingEntity) this.getPassengers().get(0);
    }

    @Override
    public boolean boost() {
        return false;
    }

    @Override
    public void onPlayerJump(int p_21696_) {}

    @Override
    public boolean canJump() {
        return true;
    }

    @Override
    public void handleStartJump(int p_21695_) {
        this.setRiderWantsFlying(!this.getRiderWantsFlying());
    }

    @Override
    public void handleStopJump() {}
}