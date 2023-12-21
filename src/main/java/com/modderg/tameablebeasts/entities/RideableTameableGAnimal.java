package com.modderg.tameablebeasts.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ItemSteerable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;

import javax.annotation.Nullable;

public class RideableTameableGAnimal extends TameableGAnimal implements ItemSteerable {

    private static final EntityDataAccessor<Boolean> SADDLE = SynchedEntityData.defineId(RideableTameableGAnimal.class, EntityDataSerializers.BOOLEAN);
    public void setSaddle(boolean i){
        this.getEntityData().set(SADDLE, i);
    }
    public boolean getSaddle(){
        return this.getEntityData().get(SADDLE);
    }

    protected boolean isSaddle(ItemStack itemStack) {
        return false;
    }

    protected RideableTameableGAnimal(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("SADDLE", this.getSaddle());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SADDLE, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("SADDLE")) {
            this.setSaddle(compound.getBoolean("SADDLE"));
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
    public void travel(Vec3 pos) {
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

                this.setSpeed(0.3f);
                super.travel(new Vec3(x, pos.y, z));
            } else {
                super.travel(pos);
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
}
