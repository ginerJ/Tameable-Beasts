package com.modderg.tameablebeasts.entities.custom;

import com.modderg.tameablebeasts.entities.FlyingTameableGAnimal;
import com.modderg.tameablebeasts.entities.goals.SwitchingMeleeAttackGoal;
import com.modderg.tameablebeasts.item.ItemInit;
import com.modderg.tameablebeasts.sound.SoundInit;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animation.AnimatableManager;

public class ScarecrowAllayEntity extends FlyingTameableGAnimal implements GeoEntity {

    private static final EntityDataAccessor<Boolean> HASHOE = SynchedEntityData.defineId(ScarecrowAllayEntity.class, EntityDataSerializers.BOOLEAN);
    public void setHoe(boolean i){
        this.getEntityData().set(HASHOE, i);
    }
    public boolean getHoe(){
        return this.getEntityData().get(HASHOE);
    }

    public ScarecrowAllayEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.textureIdSize = 3;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.FLYING_SPEED, 0.175F)
                .add(Attributes.MOVEMENT_SPEED, 0.25F)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(1, new SwitchingMeleeAttackGoal(this, 2D, true));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtByTargetGoal(this));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HASHOE, false);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("HASHOE")) {
            this.setHoe(compound.getBoolean("HASHOE"));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("TEXTUREID", this.getTextureID());
        compound.putBoolean("HASHOE", this.getHoe());
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if(this.isOwnedBy(player)){
            if(itemstack.is(ItemInit.IRON_BIG_HOE.get()) && !this.getHoe()){
                this.setHoe(true);
                updateAttributes();
                itemstack.shrink(1);
                return InteractionResult.CONSUME;
            }
            if(itemstack.is(Tags.Items.SHEARS)){
                this.setTextureId(this.random.nextInt(3));
                this.spawnItemParticles(new ItemStack(Items.PUMPKIN),16,this);
                return InteractionResult.CONSUME;
            }
        } else if(!this.isTame()){
            tameGAnimal(player,null,1);
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public boolean isFood(ItemStack itemstack) {
        return itemstack.is(Items.WHEAT);
    }

    public void updateAttributes(){
        if (this.getHoe()) {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(10.D);
        } else {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3.0D);
        }
    }

    //sounds

    @Override
    public SoundEvent getAmbientSound() {
        if(!isStill()){
            return SoundInit.SCARECROW_AMBIENT.get();
        }
        return SoundInit.SCARECROW_FLY.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundInit.SCARECROW_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {return SoundInit.SCARECROW_HURT.get();}

    @Override
    public SoundEvent getTameSound(){
        return SoundInit.SCARECROW_INTERACT.get();
    }

    @Override
    public SoundEvent getInteractSound(){
        return SoundInit.SCARECROW_INTERACT.get();
    }

    @Override
    public void checkDespawn() {
        super.checkDespawn();
    }

    //animation stuff

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar control) {
        control.add(flyController(this));
        super.registerControllers(control);
    }

    @Override
    public String getAttackAnim() {
        return "attack";
    }
}

