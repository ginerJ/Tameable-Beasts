package com.modderg.tameablebeasts.server.entity.custom;

import com.modderg.tameablebeasts.server.ModCommonConfigs;
import com.modderg.tameablebeasts.server.entity.FlyingRideableTBAnimal;

import com.modderg.tameablebeasts.server.entity.TBAnimal;
import com.modderg.tameablebeasts.server.entity.goals.*;
import com.modderg.tameablebeasts.server.item.ItemInit;
import com.modderg.tameablebeasts.server.item.block.EggBlockItem;
import com.modderg.tameablebeasts.client.sound.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animation.AnimatableManager;

public class QuetzalcoatlusEntity extends FlyingRideableTBAnimal {

    private static final EntityDataAccessor<Boolean> STAND = SynchedEntityData.defineId(TBAnimal.class, EntityDataSerializers.BOOLEAN);
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
        this.attackAnims.add("attack");
        this.consumeStaminaModule = 14;
        this.recoverStaminaModule = 7;
        this.downMovementAngle = 15F;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FLYING_SPEED, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.FOLLOW_RANGE, 48.0D)
                .add(Attributes.JUMP_STRENGTH);
    }

    public static boolean checkQuetzalSpawnRules(EntityType<QuetzalcoatlusEntity> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos p_218245_, RandomSource p_218246_) {
        return checkAnimalSpawnRules(p_218242_,p_218243_,p_218244_,p_218245_,p_218246_) && ModCommonConfigs.CAN_SPAWN_QUETZAL.get();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        addGoals(
                new SitWhenOrderedToGoal(this),
                new TakeCareOfEggsGoal(this, 15, InitPOITypes.QUETZAL_POI),
                new TameablePanicGoal(this, 1.25D),
                new RandomStrollGoal(this, 1.0D),
                new TemptGoal(this, 1.0D, Ingredient.of(ItemInit.BIG_BIRD_MEAT.get()), false),
                new FlyFromNowAndThenGoal(this),
                new TBFollowParentGoal(this, 1.0D),
                new BreedGoal(this, 1.0D),
                new WaterAvoidingRandomFlyingGoal(this, 1.0D),
                new LookAtPlayerGoal(this, Player.class, 6.0F),
                new FloatGoal(this)
        );

        addTargetGoals(
                new OwnerHurtByTargetGoal(this),
                new IncludesSitingRidingMeleeAttackGoal(this, 1.0D, true),
                new HurtByTargetGoal(this).setAlertOthers(),
                new OwnerHurtTargetGoal(this)
        );
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (isTameFood(itemstack) && !this.isTame()) {
            tameGAnimal(player, itemstack, 33);
            return InteractionResult.SUCCESS;
        }

         else if (isOwnedBy(player) && !this.isBaby() && itemstack.is(ItemInit.QUETZAL_STAND.get())) {
            setStand(true);
            this.playSound(SoundEvents.HORSE_SADDLE, 0.15F, 1.0F);
            itemstack.shrink(1);
            return InteractionResult.SUCCESS;
        }

        else if (this.isTame() && this.hasStand() && !this.isOwnedBy(player))
            if(!this.isInSittingPose()){
                player.startRiding(this);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }

        return super.mobInteract(player, hand);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
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
        if (compound.contains("STAND"))
            this.setStand(compound.getBoolean("STAND"));
    }

    @Override
    public Item itemSaddle() {
        return ItemInit.QUETZAL_SADDLE.get();
    }

    @Override
    public Item hatBoostItem() {
        return ItemInit.FLYING_HELMET.get();
    }

    @Override
    public boolean isFood(ItemStack p_27600_) {
        return p_27600_.is(ItemInit.BIG_BIRD_MEAT.get());
    }

    @Override
    public boolean isTameFood(ItemStack itemStack) {
        return this.getHealth() < 5 && itemStack.is(ItemInit.PTERANODON_MEAL.get());
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
        if(this.getPassengers().get(0).equals(rider))
            super.positionRider(rider, p_19958_);
        else
            if(this.getPassengers().get(1).equals(rider))
                positionWithOffSets(rider,0.9f,-0.7f,1.1f, 0.9f);
            else if (this.getPassengers().get(2).equals(rider))
                positionWithOffSets(rider,0.9f,0.7f,1.1f, 0.9f);
            else
                positionWithOffSets(rider,0.6f,0.0f,1.f, 0.9f);
    }

    public void positionWithOffSets(Entity rider, float xOffset, float zOffset, float yMovingOffSet, float yStillOffSet){

        double cos = Math.cos((this.getYRot()) * (Math.PI / 180F));
        double sin = Math.sin((this.getYRot()) * (Math.PI / 180F));

        double offsetX = sin * xOffset;
        double offsetZ = cos * -xOffset;

        double offsetX2 = cos * zOffset;
        double offsetZ2 = sin * zOffset;

        rider.setPos(this.getX() + offsetX + offsetX2,
                this.getY() + rider.getMyRidingOffset() + (this.isFlying() ? yMovingOffSet : yStillOffSet),
                this.getZ() + offsetZ + offsetZ2);
    }

    @Override
    protected boolean canAddPassenger(@NotNull Entity entity) {
        return (this.getPassengers().size() < 5 && this.hasStand() && !this.getPassengers().isEmpty())
                || (this.isOwnedBy((LivingEntity) entity) && super.canAddPassenger(entity));
    }

    @Override
    protected void dropAllDeathLoot(@NotNull DamageSource p_21192_) {
        if(this.hasStand())
            spawnAtLocation(ItemInit.QUETZAL_STAND.get());

        super.dropAllDeathLoot(p_21192_);
    }

    @Override
    public float getRidingSpeedMultiplier() {
        if(this.isInWater())
            return 0.4f;

        LivingEntity passenger = getControllingPassenger();
        if (passenger instanceof Player p  && this.hatBoostItem() != null &&
                isHatBoostItem(p.getInventory().getArmor(3))){
            return 1f;
        }
        return 0.6F;
    }

    //sounds

    @Override
    public SoundEvent getAmbientSound() {
        if(isFlying())
            return SoundInit.QUETZAL_FLY.get();

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

    //animation stuff

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar control) {
        control.add(addAnimationTriggers(glideFlyController(this)));
    }
}
