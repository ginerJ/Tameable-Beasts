package com.modderg.tameablebeasts.entities.custom;

import com.modderg.tameablebeasts.config.ModCommonConfigs;
import com.modderg.tameablebeasts.entities.FlyingRideableGAnimal;
import com.modderg.tameablebeasts.entities.FlyingTameableGAnimal;
import com.modderg.tameablebeasts.entities.TameableGAnimal;
import com.modderg.tameablebeasts.entities.goals.FlyFromNowAndThen;
import com.modderg.tameablebeasts.entities.goals.SwitchingFollowOwnerGoal;
import com.modderg.tameablebeasts.entities.goals.SwitchingMeleeAttackGoal;
import com.modderg.tameablebeasts.entities.goals.TameablePanicGoal;
import com.modderg.tameablebeasts.entities.EntityIinit;
import com.modderg.tameablebeasts.item.ItemInit;
import com.modderg.tameablebeasts.sound.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
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
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
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

import static net.minecraft.world.entity.ai.attributes.Attributes.FLYING_SPEED;
import static net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED;

public class QuetzalcoatlusEntity extends FlyingRideableGAnimal {

    @Override
    protected boolean isSaddle(ItemStack itemStack) {
        return itemStack.is(ItemInit.QUETZAL_SADDLE.get());
    }

    public QuetzalcoatlusEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FLYING_SPEED, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.FOLLOW_RANGE, 48.0D)
                .add(Attributes.JUMP_STRENGTH);
    }

    protected float generateRandomMaxHealth(RandomSource p_218806_) {
        return 20.0F + (float)p_218806_.nextInt(8) + (float)p_218806_.nextInt(9);
    }

    public static boolean checkQuetzalSpawnRules(EntityType<QuetzalcoatlusEntity> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos p_218245_, RandomSource p_218246_) {
        return checkAnimalSpawnRules(p_218242_,p_218243_,p_218244_,p_218245_,p_218246_) && ModCommonConfigs.CAN_SPAWN_QUETZAL.get();
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
        this.targetSelector.addGoal(1, new SwitchingMeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(2, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(3, new OwnerHurtTargetGoal(this));
        this.goalSelector.addGoal(4, new TameablePanicGoal(this, 1.25D));
        this.goalSelector.addGoal(4, new SwitchingFollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
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
    public boolean isFood(ItemStack p_27600_) {
        return p_27600_.is(ItemInit.QUETZAL_MEAT.get());
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_146746_, DifficultyInstance p_146747_, MobSpawnType p_146748_, @org.jetbrains.annotations.Nullable SpawnGroupData p_146749_, @org.jetbrains.annotations.Nullable CompoundTag p_146750_) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.generateRandomMaxHealth(p_146746_.getRandom()));
        this.setHealth(this.getMaxHealth());
        this.setTexture(this.random.nextInt(5));
        return super.finalizeSpawn(p_146746_, p_146747_, p_146748_, p_146749_, p_146750_);
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    @Override
    protected Boolean shouldFly() {
        Entity owner = this.m_269323_();

        return ((this.getGoalsRequireFlying() && !this.isTame())||
                this.isAggressive()||
                (this.getRiderWantsFlying()||
                owner != null && this.distanceTo(owner)>10)||
                this.isFlying() && owner!= null && !owner.isOnGround() && !hasPassenger(owner))
                && super.shouldFly();
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

    @org.jetbrains.annotations.Nullable
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

    public static <T extends QuetzalcoatlusEntity & GeoEntity> AnimationController<T> flyController(T entity) {
        return new AnimationController<>(entity,"movement", 5, event ->{
            if(entity.isInSittingPose()){
                event.getController().setAnimation(RawAnimation.begin().then("sit", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }
            if(entity.isFlying()){
                event.getController().setAnimation(RawAnimation.begin().then("fly", Animation.LoopType.LOOP));
            } else if (event.isMoving()) {
                event.getController().setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
            } else {
                event.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
            }
            return PlayState.CONTINUE;
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(flyController(this));
        super.registerControllers(data);
    }
}
