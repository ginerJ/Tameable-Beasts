package com.modderg.tameablebeasts.entities.custom;

import com.modderg.tameablebeasts.block.BlockInit;
import com.modderg.tameablebeasts.config.ModCommonConfigs;
import com.modderg.tameablebeasts.entities.FlyingRideableGAnimal;
import com.modderg.tameablebeasts.entities.goals.FlyFromNowAndThen;
import com.modderg.tameablebeasts.entities.goals.SwitchingMeleeAttackGoal;
import com.modderg.tameablebeasts.entities.goals.TakeCareOfEggsGoal;
import com.modderg.tameablebeasts.entities.goals.TameablePanicGoal;
import com.modderg.tameablebeasts.item.ItemInit;
import com.modderg.tameablebeasts.item.block.EggBlockItem;
import com.modderg.tameablebeasts.sound.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimatableManager;

import java.util.Random;

public class QuetzalcoatlusEntity extends FlyingRideableGAnimal {

    private static final EntityDataAccessor<Boolean> STAND = SynchedEntityData.defineId(QuetzalcoatlusEntity.class, EntityDataSerializers.BOOLEAN);
    public void setStand(boolean i){
        this.getEntityData().set(STAND, i);
    }
    public boolean hasStand(){
        return this.getEntityData().get(STAND);
    }


    public QuetzalcoatlusEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.textureIdSize = 6;
        this.healthFloor = 30;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FLYING_SPEED, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D)
                .add(Attributes.FOLLOW_RANGE, 48.0D)
                .add(Attributes.JUMP_STRENGTH);
    }

    public static boolean checkQuetzalSpawnRules(EntityType<QuetzalcoatlusEntity> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos p_218245_, RandomSource p_218246_) {
        return checkAnimalSpawnRules(p_218242_,p_218243_,p_218244_,p_218245_,p_218246_) && ModCommonConfigs.CAN_SPAWN_QUETZAL.get();
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
        this.targetSelector.addGoal(1, new SwitchingMeleeAttackGoal(this, 1.0D, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtByTargetGoal(this));
        this.goalSelector.addGoal(3, new TakeCareOfEggsGoal(this, 15, BlockInit.QUETZAL_EGG_BLOCK.get()));
        this.goalSelector.addGoal(4, new TameablePanicGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this,1.0D));
        this.goalSelector.addGoal(6, new TemptGoal(this, 1.0D, Ingredient.of(Items.ROTTEN_FLESH), false));
        this.goalSelector.addGoal(7, new FlyFromNowAndThen(this));
        this.goalSelector.addGoal(8, new FollowParentGoalIfNotSitting(this, 1.0D));
        this.goalSelector.addGoal(9, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(9, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(11, new FloatGoal(this));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (isOwnedBy(player) && !this.isBaby() && itemstack.is(ItemInit.QUETZAL_STAND.get())) {
            setStand(true);
            this.playSound(SoundEvents.HORSE_SADDLE, 0.15F, 1.0F);
            itemstack.shrink(1);
            return InteractionResult.SUCCESS;
        }

        if (this.isTame() && this.hasStand() && !this.isOwnedBy(player)){
            if(!this.isInSittingPose()){
                player.startRiding(this);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }

        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("STAND", this.hasStand());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STAND, false);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("STAND")) {
            this.setStand(compound.getBoolean("STAND"));
        }
    }

    @Override
    protected Item itemSaddle() {
        return ItemInit.QUETZAL_SADDLE.get();
    }

    @Override
    protected Item hatBoostItem() {
        return ItemInit.FLYING_HELMET.get();
    }

    @Override
    public boolean isFood(ItemStack p_27600_) {
        return p_27600_.is(ItemInit.QUETZAL_MEAT.get());
    }

    @Override
    protected Boolean shouldFly() {
        Entity owner = this.getOwner();
        if(this.isTame()) this.setGoalsRequireFlying(false);
        return ((this.getGoalsRequireFlying())||
                this.isAggressive()||
                (this.getRiderWantsFlying()||
                (owner != null && this.distanceTo(owner)>10) && !this.isWandering())||
                (this.isFlying() && owner!= null && !owner.onGround() && !hasPassenger(owner)))
                && super.shouldFly();
    }

    @Override
    public EggBlockItem getEgg() {
        return (EggBlockItem) ItemInit.QUETZAL_EGG_ITEM.get();
    }

    @Override
    public void setBaby(boolean p_146756_) {
        this.setAge(p_146756_ ? -48000 : 0);
    }

    @Override
    protected void positionRider(Entity rider, MoveFunction p_19958_) {
        if(this.getPassengers().get(0).equals(rider)) {
            super.positionRider(rider, p_19958_);

        } else {
            if(this.getPassengers().get(1).equals(rider)){
                positionWithOffSets(rider,1.4f,-0.7f,0.9f, 0.5f);

            } else if (this.getPassengers().get(2).equals(rider)){
                positionWithOffSets(rider,1.4f,0.7f,0.9f, 0.5f);

            } else {
                positionWithOffSets(rider,1f,0.0f,0.85f, 0.5f);

            }
        }
    }
    public void positionWithOffSets(Entity rider, float xOffset, float zOffset, float yMovingOffSet, float yStillOffSet){
        double offsetX = Math.sin((this.getYRot()) * (Math.PI / 180F)) * xOffset;
        double offsetZ = Math.cos((this.getYRot()) * (Math.PI / 180F)) * -xOffset;

        double offsetX2 = Math.cos((this.getYRot()) * (Math.PI / 180F)) * zOffset;
        double offsetZ2 = Math.sin((this.getYRot()) * (Math.PI / 180F)) * zOffset;

        rider.setPos(this.getX() + offsetX + offsetX2,
                this.getY() + rider.getMyRidingOffset() + (this.isFlying() && !this.isStill() ? yMovingOffSet : yStillOffSet),
                this.getZ() + offsetZ + offsetZ2);
    }

    @Override
    protected boolean canAddPassenger(Entity entity) {
        return (this.getPassengers().size() < 5 && this.hasStand() && !this.getPassengers().isEmpty())
                || (this.isOwnedBy((LivingEntity) entity) && super.canAddPassenger(entity));
    }

    //sounds

    @Override
    public SoundEvent getAmbientSound() {
        if(isFlying()){
            return SoundInit.QUETZAL_FLY.get();
        }
        return SoundInit.QUETZAL_AMBIENT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundInit.QUETZAL_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {return SoundInit.QUETZAL_HURT.get();}

    @Override
    protected void playStepSound(BlockPos p_20135_, BlockState p_20136_) {
        this.playSound(SoundInit.QUETZAL_STEPS.get(), 0.15F, 1.0F);
    }

    @Override
    public SoundEvent getTameSound(){
        return SoundInit.QUETZAL_INTERACT.get();
    }

    @Override
    public SoundEvent getInteractSound(){
        return SoundInit.QUETZAL_INTERACT.get();
    }

    @Override
    public boolean canSpawnEgg() {
        return true;
    }

    //animation stuff

    @Override
    public String getAttackAnim(){
        return "attack";
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar control) {
        control.add(flyController(this));
        super.registerControllers(control);
    }
}
