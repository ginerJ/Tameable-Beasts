package com.modderg.tameablebeasts.entities;

import com.modderg.tameablebeasts.item.ItemInit;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ItemSteerable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class RideableTameableGAnimal extends TameableGAnimal implements ItemSteerable {

    private static final EntityDataAccessor<Boolean> SADDLE = SynchedEntityData.defineId(RideableTameableGAnimal.class, EntityDataSerializers.BOOLEAN);
    public void setSaddle(boolean i){
        this.getEntityData().set(SADDLE, i);
    }
    public boolean hasSaddle(){
        return this.getEntityData().get(SADDLE);
    }

    protected Item itemSaddle() {
        return null;
    }
    protected boolean isSaddle(ItemStack itemStack) {
        return itemStack.is(itemSaddle());
    }

    protected Item hatBoostItem() {
        return null;
    }
    protected boolean isHatBoostItem(ItemStack itemStack) {
        return itemStack.is(hatBoostItem());
    }

    protected RideableTameableGAnimal(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
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
        if (compound.contains("SADDLE")) {
            this.setSaddle(compound.getBoolean("SADDLE"));
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (isOwnedBy(player)){
            if (this.hasSaddle() && !isFood(itemstack)){
                if(!player.isShiftKeyDown() && !this.isInSittingPose()){
                    player.startRiding(this);
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
    protected void dropAllDeathLoot(DamageSource p_21192_) {
        if(this.hasSaddle() && this.itemSaddle()!=null){
            this.level().addFreshEntity(new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), new ItemStack(itemSaddle())));
        }
        super.dropAllDeathLoot(p_21192_);
    }

    public float getRidingSpeedMultiplier(){
        LivingEntity passenger = getControllingPassenger();
        if (passenger instanceof Player p  && this.hatBoostItem() != null &&
                isHatBoostItem(p.getInventory().getArmor(3))){
            return 1.6f;
        }
        else return 1f;
    }

    @Override
    public void travel(Vec3 pos) {
        if (this.isAlive()) {
            if (this.canBeControlledByRider()) {
                LivingEntity passenger = getControllingPassenger();
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


                this.setSpeed((float) this.getAttribute(Attributes.MOVEMENT_SPEED).getBaseValue() * speedMul);

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

    @Nullable
    public LivingEntity getControllingPassenger() {
        return (!this.getPassengers().isEmpty() &&
                this.isOwnedBy((LivingEntity) this.getPassengers().get(0)))
                ? (LivingEntity) this.getPassengers().get(0) : null;
    }

    @Override
    public boolean canBeControlledByRider() {
        return this.getControllingPassenger() != null &&
                this.isOwnedBy(this.getControllingPassenger());
    }

    @Override
    public boolean boost() {
        return false;
    }
}
