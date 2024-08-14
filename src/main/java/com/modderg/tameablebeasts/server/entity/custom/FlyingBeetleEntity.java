package com.modderg.tameablebeasts.server.entity.custom;

import com.modderg.tameablebeasts.server.ModCommonConfigs;
import com.modderg.tameablebeasts.server.entity.FlyingTBAnimal;

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
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimatableManager;

public class FlyingBeetleEntity extends FlyingTBAnimal {

    public FlyingBeetleEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.textureIdSize = 3;
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.FLYING_SPEED, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.ARMOR, 15.0D);
    }

    public static boolean checkFlyingBeetleSpawnRules(EntityType<FlyingBeetleEntity> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos p_218245_, RandomSource p_218246_) {
        return ModCommonConfigs.CAN_SPAWN_FLYING_BEETLE.get();
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(1, new IncludesSitingRidingMeleeAttackGoal(this, 2D, true));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.goalSelector.addGoal(3, new TakeCareOfEggsGoal(this, 15, InitPOITypes.FLYING_BEETLE_POI));
        this.goalSelector.addGoal(4, new TameablePanicGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.1D, Ingredient.of(Items.HONEY_BOTTLE), false));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this,1.0D));
        this.goalSelector.addGoal(6, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new AvoidEntityGoal<>(this,Player.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(6, new FlyFromNowAndThenGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new TBFollowParentGoal(this, 1.0D));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (isTameFood(itemstack) && !this.isTame()) {
            tameGAnimal(player, itemstack, 20);
            return InteractionResult.SUCCESS;
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
        Entity owner = this.getOwner();

        return super.shouldFly() && !this.isBaby() && (

                (this.getGoalsRequireFlying() && (
                        !this.isTame() ||
                        this.isWandering())
                ) ||

                (!hasControllingPassenger() && (
                        this.isAggressive() ||
                        this.isOverFluidOrVoid())
                ) ||

                (owner != null && (
                        (this.distanceTo(owner) > 10 && !this.isWandering()) ||
                        (this.isFlying() && !owner.onGround()))
                )
        );
    }

    @Override
    public EggBlockItem getEgg() {
        return (EggBlockItem) ItemInit.FLYING_BEETLE_EGG_ITEM.get();
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

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar control) {
        control.add(addAnimationTriggers(flyController(this)));
    }
}
