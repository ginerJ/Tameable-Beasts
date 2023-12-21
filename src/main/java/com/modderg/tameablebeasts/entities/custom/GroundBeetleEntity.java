package com.modderg.tameablebeasts.entities.custom;

import com.modderg.tameablebeasts.entities.TameableGAnimal;
import com.modderg.tameablebeasts.entities.EntityIinit;
import com.modderg.tameablebeasts.sound.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nullable;
import java.util.UUID;

public class GroundBeetleEntity extends TameableGAnimal implements GeoEntity, NeutralMob {
    private static final EntityDataAccessor<Integer> TEXTUREID = SynchedEntityData.defineId(GroundBeetleEntity.class, EntityDataSerializers.INT);
    public void setTexture(int i){
        this.getEntityData().set(TEXTUREID, i);
    }
    public int getTextureID(){
        return this.getEntityData().get(TEXTUREID);
    }

    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(GroundBeetleEntity.class, EntityDataSerializers.INT);
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    @Nullable
    private UUID persistentAngerTarget;

    public GroundBeetleEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
    }

    public boolean isTameFood(ItemStack itemStack) {
        return itemStack.is(Items.HONEYCOMB);
    }

    @Override
    public boolean isFood(ItemStack p_27600_) {
        return p_27600_.is(Items.HONEY_BOTTLE);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(1, new FleeSunGoal(this, 3.0F));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.goalSelector.addGoal(2, new FloatGoal(this));
        this.goalSelector.addGoal(2, new LeapAtTargetGoal(this, 0.4f));
        this.goalSelector.addGoal(3, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.1D, Ingredient.of(Items.HONEY_BOTTLE), false));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(6, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(8, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(9, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(11, new FollowParentGoalIfNotSitting(this, 1.0D));
        this.goalSelector.addGoal(12, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(13, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TEXTUREID, 0);
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("TEXTUREID")) {
            this.setTexture(compound.getInt("TEXTUREID"));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("TEXTUREID", this.getTextureID());
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_146746_, DifficultyInstance p_146747_, MobSpawnType p_146748_, @org.jetbrains.annotations.Nullable SpawnGroupData p_146749_, @org.jetbrains.annotations.Nullable CompoundTag p_146750_) {
        this.setTexture(this.random.nextInt(2));
        return super.finalizeSpawn(p_146746_, p_146747_, p_146748_, p_146749_, p_146750_);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if(this.isTameFood(itemstack) && !this.isTame()){
            tameGAnimal(player, itemstack, 10);
        }
        if(this.m_269323_() != null && this.m_269323_().isShiftKeyDown()){
            this.setOrderedToSit(!this.isOrderedToSit());
            return InteractionResult.CONSUME;
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public void tick() {
        updateAttributes();
        super.tick();
    }

    @Override
    public @org.jetbrains.annotations.Nullable AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        FlyingBeetleEntity beetle = EntityIinit.TAMEABLE_BEETLE.get().create(p_146743_);
        UUID uuid = this.getOwnerUUID();
        if (uuid != null) {
            beetle.setOwnerUUID(uuid);
            beetle.setTame(true);
        }

        return beetle;
    }

    protected void updateAttributes(){
        if (isBaby()) {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.1D);
        } else {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        }
    }

    public static boolean checkGroundBeetleSpawnRules(EntityType<GroundBeetleEntity> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos p_218245_, RandomSource p_218246_) {
        return true;
    }

    //sounds

    @Override
    public SoundEvent getAmbientSound() {
        return SoundInit.BEETLE_AMBIENT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundInit.BEETLE_DEATH.get();
    }

    @org.jetbrains.annotations.Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {return SoundInit.BEETLE_HURT.get();}

    @Override
    protected void playStepSound(BlockPos p_20135_, BlockState p_20136_) {
        this.playSound(SoundInit.BEETLE_STEPS.get(), 0.15F, 1.0F);
    }

    @Override
    public SoundEvent getTameSound(){
        return SoundInit.BEETLE_INTERACT.get();
    }

    @Override
    public SoundEvent getInteractSound(){
        return SoundInit.BEETLE_INTERACT.get();
    }



    //anger stuff

    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    public void setRemainingPersistentAngerTime(int p_30404_) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, p_30404_);
    }

    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Nullable
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    public void setPersistentAngerTarget(@Nullable UUID p_30400_) {
        this.persistentAngerTarget = p_30400_;
    }

    //animation stuff
    protected AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);
    public static <T extends GroundBeetleEntity & GeoEntity> AnimationController<T> flyController(T entity) {
        return new AnimationController<>(entity,"movement", 5, event ->{
            if(entity.isInSittingPose()){
                event.getController().setAnimation(RawAnimation.begin().then("sit", Animation.LoopType.LOOP));
            } else {
                if(event.isMoving()){
                    event.getController().setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
                } else {
                    event.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
                }
            }
            return PlayState.CONTINUE;
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(flyController(this));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }
}
