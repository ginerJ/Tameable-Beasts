package com.modderg.tameablebeasts.entities.custom;

import com.modderg.tameablebeasts.config.ModCommonConfigs;
import com.modderg.tameablebeasts.entities.FlyingTameableGAnimal;
import com.modderg.tameablebeasts.entities.goals.FlyFromNowAndThen;
import com.modderg.tameablebeasts.entities.goals.SwitchingFollowOwnerGoal;
import com.modderg.tameablebeasts.entities.goals.SwitchingMeleeAttackGoal;
import com.modderg.tameablebeasts.entities.goals.TameablePanicGoal;
import com.modderg.tameablebeasts.sound.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class FlyingBeetleEntity extends FlyingTameableGAnimal {

    public FlyingBeetleEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.FLYING_SPEED, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    public static boolean checkFlyingBeetleSpawnRules(EntityType<FlyingBeetleEntity> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos p_218245_, RandomSource p_218246_) {
        return ModCommonConfigs.CAN_SPAWN_FLYING_BEETLE.get();
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(1, new SwitchingMeleeAttackGoal(this, 2D, true));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.goalSelector.addGoal(3, new SwitchingFollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(4, new TameablePanicGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.1D, Ingredient.of(Items.HONEY_BOTTLE), false));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this,1.0D));
        this.goalSelector.addGoal(6, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new AvoidEntityGoal<>(this,Player.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(6, new FlyFromNowAndThen(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new FollowParentGoalIfNotSitting(this, 1.0D));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor p_146746_, @NotNull DifficultyInstance p_146747_, @NotNull MobSpawnType p_146748_, @Nullable SpawnGroupData p_146749_, @Nullable CompoundTag p_146750_) {
        this.setTexture(this.random.nextInt(3));
        return super.finalizeSpawn(p_146746_, p_146747_, p_146748_, p_146749_, p_146750_);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (isTameFood(itemstack) && !this.isTame()) {
            tameGAnimal(player, itemstack, 8);
            return InteractionResult.CONSUME;
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public boolean isTameFood(ItemStack itemStack) {
        return itemStack.is(Items.HONEYCOMB);
    }

    @Override
    public boolean isFood(ItemStack p_27600_) {
        return p_27600_.is(Items.HONEY_BOTTLE);
    }

    @Override
    protected Boolean shouldFly() {
        Entity owner = this.m_269323_();

        return (this.getGoalsRequireFlying()||this.isAggressive()||
                (owner != null && this.distanceTo(owner)>10)||
                this.isFlying() && owner!= null && !owner.isOnGround())
                && super.shouldFly();
    }

    //sounds

    @Override
    public SoundEvent getAmbientSound() {
        if(isFlying()){
            return SoundInit.BEETLE_FLY.get();
        }
        return SoundInit.BEETLE_AMBIENT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundInit.BEETLE_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource p_21239_) {return SoundInit.BEETLE_HURT.get();}

    @Override
    protected void playStepSound(@NotNull BlockPos p_20135_, @NotNull BlockState p_20136_) {
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


    //animation stuff
    public static <T extends FlyingBeetleEntity & GeoEntity> AnimationController<T> flyController(T entity) {
        return new AnimationController<>(entity,"movement", 5, event ->{
            if(entity.isInSittingPose()){
                event.getController().setAnimation(RawAnimation.begin().then("sit", Animation.LoopType.LOOP));
            } else {
                if(entity.isFlying()){
                    event.getController().setAnimation(RawAnimation.begin().then("fly", Animation.LoopType.LOOP));
                } else if(event.isMoving()){
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
        super.registerControllers(data);
    }
}