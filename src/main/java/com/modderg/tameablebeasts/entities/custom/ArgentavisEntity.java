package com.modderg.tameablebeasts.entities.custom;

import com.modderg.tameablebeasts.block.BlockInit;
import com.modderg.tameablebeasts.config.ModCommonConfigs;
import com.modderg.tameablebeasts.entities.FlyingRideableGAnimal;
import com.modderg.tameablebeasts.entities.FlyingTameableGAnimal;
import com.modderg.tameablebeasts.entities.goals.FlyFromNowAndThen;
import com.modderg.tameablebeasts.entities.goals.SwitchingMeleeAttackGoal;
import com.modderg.tameablebeasts.entities.goals.TakeCareOfEggsGoal;
import com.modderg.tameablebeasts.entities.goals.TameablePanicGoal;
import com.modderg.tameablebeasts.item.ItemInit;
import com.modderg.tameablebeasts.item.block.EggBlockItem;
import com.modderg.tameablebeasts.sound.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
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
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class ArgentavisEntity extends FlyingRideableGAnimal {

    @Override
    protected Item itemSaddle() {
        return ItemInit.ARGENTAVIS_SADDLE.get();
    }

    public ArgentavisEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.textureIdSize = 6;
        this.healthFloor = 20;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.FLYING_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 15.0D)
                .add(Attributes.FOLLOW_RANGE, 48.0D)
                .add(Attributes.JUMP_STRENGTH);
    }

    public static boolean checkArgentavisSpawnRules(EntityType<ArgentavisEntity> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos blockpos, RandomSource p_218246_) {
        return checkAnimalSpawnRules(p_218242_,p_218243_,p_218244_,blockpos,p_218246_) && ModCommonConfigs.CAN_SPAWN_ARGENTAVIS.get() && blockpos.getY() >= 100;
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
        this.targetSelector.addGoal(1, new SwitchingMeleeAttackGoal(this, 1.0D, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtByTargetGoal(this));
        this.goalSelector.addGoal(3, new TakeCareOfEggsGoal(this, 15, BlockInit.ARGENTAVIS_EGG_BLOCK.get()));
        this.goalSelector.addGoal(4, new TameablePanicGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this,1.0D));
        this.goalSelector.addGoal(6, new TemptGoal(this, 1.0D, Ingredient.of(ItemInit.QUETZAL_MEAT.get()), false));
        this.goalSelector.addGoal(7, new FlyFromNowAndThen(this));
        this.goalSelector.addGoal(8, new FollowParentGoalIfNotSitting(this, 1.0D));
        this.goalSelector.addGoal(9, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(9, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(11, new FloatGoal(this));
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(ItemInit.QUETZAL_MEAT.get());
    }

    @Override
    protected Boolean shouldFly() {
        Entity owner = this.getOwner();
        if(this.isTame()) this.setGoalsRequireFlying(false);
        return ((this.getGoalsRequireFlying())||
                (this.isAggressive()&&!isControlledByLocalInstance())||
                (this.getRiderWantsFlying()||
                        (owner != null && this.distanceTo(owner)>10) && !this.isWandering())||
                (this.isFlying() && owner!= null && !owner.onGround() && !hasPassenger(owner)))
                && super.shouldFly();
    }

    @Override
    public float getRidingSpeedMultiplier() {
        if(this.isInWater())
            return 0.5f;
        if(this.getDeltaMovement().y < 0){
            return 2.5f;
        }
        return 2f;
    }

    @Override
    public void setBaby(boolean p_146756_) {
        this.setAge(p_146756_ ? -48000 : 0);
    }

    @Override
    public EggBlockItem getEgg() {
        return (EggBlockItem) ItemInit.ARGENTAVIS_EGG_ITEM.get();
    }

    @Override
    public boolean canSpawnEgg() {
        return true;
    }

    //sound stuff

    @Override
    public SoundEvent getAmbientSound() {
        if(isFlying()){
            return SoundInit.QUETZAL_FLY.get();
        }
        return SoundInit.ARGENTAVIS_AMBIENT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundInit.ARGENTAVIS_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {return SoundInit.ARGENTAVIS_HURT.get();}

    @Override
    protected void playStepSound(BlockPos p_20135_, BlockState p_20136_) {
        this.playSound(SoundInit.QUETZAL_STEPS.get(), 0.15F, 1.0F);
    }

    @Override
    public SoundEvent getTameSound(){
        return SoundInit.ARGENTAVIS_INTERACT.get();
    }

    @Override
    public SoundEvent getInteractSound(){
        return SoundInit.ARGENTAVIS_INTERACT.get();
    }

    //animation stuff

    @Override
    public String getAttackAnim(){
        return "attack";
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar control) {
        control.add(glideFlyController(this));
        super.registerControllers(control);
    }

    public static <T extends FlyingTameableGAnimal & GeoEntity> AnimationController<T> glideFlyController(T entity) {
        return new AnimationController<>(entity,"movement", 5, event ->{
            if(entity.isInSittingPose()){
                event.getController().setAnimation(RawAnimation.begin().then("sit", Animation.LoopType.LOOP));
            } else {
                if(entity.isFlying()){
                    if(entity.isStill()){
                        event.getController().setAnimation(RawAnimation.begin().then("fly_idle", Animation.LoopType.LOOP));
                    } else {
                        if(entity.getDeltaMovement().y < 0 && entity.isControlledByLocalInstance())
                            event.getController().setAnimation(RawAnimation.begin().then("glide", Animation.LoopType.LOOP));
                        else event.getController().setAnimation(RawAnimation.begin().then("fly", Animation.LoopType.LOOP));
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
