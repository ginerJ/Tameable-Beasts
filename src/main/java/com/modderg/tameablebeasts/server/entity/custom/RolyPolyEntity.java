package com.modderg.tameablebeasts.server.entity.custom;

import com.modderg.tameablebeasts.server.ModCommonConfigs;
import com.modderg.tameablebeasts.server.block.BlockInit;
import com.modderg.tameablebeasts.server.entity.RideableTBAnimal;

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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animation.AnimatableManager;

public class RolyPolyEntity extends RideableTBAnimal {

    @Override
    public Item itemSaddle() {
        return ItemInit.ROLYPOLY_SADDLE.get();
    }

    @Override
    public Item hatBoostItem() {
        return ItemInit.BIKER_HELMET.get();
    }

    public RolyPolyEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.textureIdSize = 9;
        this.healthFloor = 15;
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.225D);
    }

    public static boolean checkRolyPolySpawnRules(EntityType<RolyPolyEntity> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos p_218245_, RandomSource p_218246_) {
        return checkAnimalSpawnRules(p_218242_,p_218243_,p_218244_,p_218245_,p_218246_) && ModCommonConfigs.CAN_SPAWN_ROLY_POLY.get();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new TameablePanicGoal(this, 1.2D));
        this.goalSelector.addGoal(1, new TBFollowOwnerGoal(this, 1.0D, 10.0F, 6.0F));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, Ingredient.of(ItemInit.LEAF.get()), false));
        this.goalSelector.addGoal(3, new RunFromNowAndThenGoal(this, 1.2F));
        this.goalSelector.addGoal(3, new TakeCareOfEggsGoal(this, 15, InitPOITypes.ROLY_POLY_POI));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));
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
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(ItemInit.LEAF.get());
    }

    @Override
    public boolean isTameFood(ItemStack itemStack) {
        return itemStack.is(ItemInit.BUG_SALAD.get());
    }

    @Override
    public EggBlockItem getEgg() {
        return (EggBlockItem) ItemInit.ROLY_POLY_EGG_ITEM.get();
    }

    @Override
    public float getRidingSpeedMultiplier() {
        return super.getRidingSpeedMultiplier() * (this.getBlockStateOn().is(BlockInit.ASPHALTED_DIRT.get()) ? 1.75F : 1.0F);
    }

    //sounds

    @Override
    public SoundEvent getAmbientSound() {
        return SoundInit.ROLYPOLY_AMBIENT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundInit.ROLYPOLY_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {return SoundInit.ROLYPOLY_HURT.get();}

    @Override
    protected void playStepSound(BlockPos p_20135_, BlockState p_20136_) {
        if(isRunning()){
            this.playSound(SoundInit.ROLYPOLY_ROLL.get(), 0.15F, 1.0F);
        } else{
            this.playSound(SoundInit.ROLYPOLY_STEPS.get(), 0.15F, 1.0F);
        }
    }

    @Override
    public SoundEvent getTameSound(){
        return SoundInit.ROLYPOLY_INTERACT.get();
    }

    @Override
    public SoundEvent getInteractSound(){return SoundInit.ROLYPOLY_INTERACT.get();}

    @Override
    public boolean isRunning() {
        return super.isRunning() || this.hasControllingPassenger();
    }

    @Override
    public void updateAttributes(){
        if (this.isRunning())
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        else
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    //animation stuff

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar control) {
        control.add(addAnimationTriggers(vehicleController(this)));
    }
}
