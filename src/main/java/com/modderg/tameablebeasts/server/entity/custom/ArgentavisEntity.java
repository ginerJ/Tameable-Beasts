package com.modderg.tameablebeasts.server.entity.custom;

import com.modderg.tameablebeasts.server.ModCommonConfigs;
import com.modderg.tameablebeasts.server.entity.FlyingRideableTBAnimal;
import com.modderg.tameablebeasts.server.entity.goals.*;
import com.modderg.tameablebeasts.server.item.ItemInit;
import com.modderg.tameablebeasts.server.item.block.EggBlockItem;
import com.modderg.tameablebeasts.client.sound.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
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
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class ArgentavisEntity extends FlyingRideableTBAnimal {

    public ArgentavisEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.textureIdSize = 7;
        this.healthFloor = 20;
        this.attackAnims.add("attack");
        this.consumeStaminaModule = 7;
        this.recoverStaminaModule = 15;
        this.downMovementAngle = 5F;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.FLYING_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.FOLLOW_RANGE, 48.0D)
                .add(Attributes.JUMP_STRENGTH);
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (!this.isTame() && isTameFood(itemstack)) {
            tameGAnimal(player, itemstack, 33);
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public Item itemSaddle() {
        return ItemInit.ARGENTAVIS_SADDLE.get();
    }

    public static boolean checkArgentavisSpawnRules(EntityType<ArgentavisEntity> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos blockpos, RandomSource p_218246_) {
        return checkAnimalSpawnRules(p_218242_,p_218243_,p_218244_,blockpos,p_218246_) &&
                ModCommonConfigs.CAN_SPAWN_ARGENTAVIS.get() && blockpos.getY() >= ModCommonConfigs.ARGENTAVIS_SPAWN_HEIGHT.get();
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
        this.targetSelector.addGoal(1, new IncludesSitingRidingMeleeAttackGoal(this, 1.0D, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtByTargetGoal(this));
        this.goalSelector.addGoal(3, new TakeCareOfEggsGoal(this, 15, InitPOITypes.ARGENTAVIS_POI));
        this.goalSelector.addGoal(4, new TameablePanicGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this,1.0D));
        this.goalSelector.addGoal(6, new TemptGoal(this, 1.0D, Ingredient.of(ItemInit.QUETZAL_MEAT.get()), false));
        this.goalSelector.addGoal(7, new FlyFromNowAndThenGoal(this));
        this.goalSelector.addGoal(8, new TBFollowParentGoal(this, 1.0D));
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
    public boolean isTameFood(ItemStack itemStack) {
        return this.getHealth() < 5 && itemStack.is(ItemInit.BIG_BIRD_BAIT.get());
    }

    @Override
    public float getRidingSpeedMultiplier() {
        if(this.isInWater())
            return 0.6f;
        if(this.getDeltaMovement().y < 0)
            return 1.5f;

        return 1f;
    }

    @Override
    public void setBaby(boolean p_146756_) {
        this.setAge(p_146756_ ? -48000 : 0);
    }

    @Override
    public EggBlockItem getEgg() {
        return (EggBlockItem) ItemInit.ARGENTAVIS_EGG_ITEM.get();
    }

    //sound stuff

    @Override
    public SoundEvent getAmbientSound() {
        if(isFlying())
            return SoundInit.QUETZAL_FLY.get();

        return SoundInit.ARGENTAVIS_AMBIENT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundInit.ARGENTAVIS_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource p_21239_) {return SoundInit.ARGENTAVIS_HURT.get();}

    @Override
    protected void playStepSound(@NotNull BlockPos p_20135_, @NotNull BlockState p_20136_) {
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
    public void registerControllers(AnimatableManager.ControllerRegistrar control) {
        control.add(addAnimationTriggers(glideFlyController(this)));
    }
}
