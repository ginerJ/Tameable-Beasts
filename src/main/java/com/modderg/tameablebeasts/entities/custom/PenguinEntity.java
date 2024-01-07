package com.modderg.tameablebeasts.entities.custom;

import com.modderg.tameablebeasts.block.BlockInit;
import com.modderg.tameablebeasts.config.ModCommonConfigs;
import com.modderg.tameablebeasts.entities.TameableGAnimal;
import com.modderg.tameablebeasts.entities.goals.GFollowOwnerGoal;
import com.modderg.tameablebeasts.entities.goals.TakeCareOfEggsGoal;
import com.modderg.tameablebeasts.entities.goals.TameablePanicGoal;
import com.modderg.tameablebeasts.entities.EntityIinit;
import com.modderg.tameablebeasts.item.ItemInit;
import com.modderg.tameablebeasts.item.block.EggBlockItem;
import com.modderg.tameablebeasts.sound.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
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
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.UUID;

public class PenguinEntity extends TameableGAnimal implements GeoEntity {

    private static final EntityDataAccessor<Boolean> SWORD = SynchedEntityData.defineId(PenguinEntity.class, EntityDataSerializers.BOOLEAN);
    public void setSword(boolean i){
        this.getEntityData().set(SWORD, i);
        refreshAttackController();
    }
    public boolean getSword(){
        return this.getEntityData().get(SWORD);
    }

    private static final EntityDataAccessor<Boolean> HELMET = SynchedEntityData.defineId(PenguinEntity.class, EntityDataSerializers.BOOLEAN);
    public void setHelmet(boolean i){
        this.getEntityData().set(HELMET, i);
    }
    public boolean getHelmet(){
        return this.getEntityData().get(HELMET);
    }

    public PenguinEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.textureIdSize = 3;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {

        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 2.5D)
                .add(Attributes.ARMOR, 2.0D);
    }

    public static boolean checkPenguinSpawnRules(EntityType<PenguinEntity> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos p_218245_, RandomSource p_218246_) {
        return (p_218243_.getBlockState(p_218245_.below()).is(Blocks.PACKED_ICE)
                || p_218243_.getBlockState(p_218245_.below()).is(Blocks.ICE)
                || p_218243_.getBlockState(p_218245_.below()).is(Blocks.BLUE_ICE))
                && ModCommonConfigs.CAN_SPAWN_PENGUIN.get();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new TameablePanicGoal(this, 1.2D));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtTargetGoal(this));
        this.goalSelector.addGoal(2, new GFollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(3, new FloatGoal(this));
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4f));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new TakeCareOfEggsGoal(this, 15, BlockInit.PENGUIN_EGG_BLOCK.get()));
        this.goalSelector.addGoal(4, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.1D, Ingredient.of(Items.TROPICAL_FISH), false));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new TameablePanicGoal(this, 1.25D));
        this.goalSelector.addGoal(7, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(9, new FollowParentGoalIfNotSitting(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(11, new RandomLookAroundGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SWORD, false);
        this.entityData.define(HELMET, false);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("SWORD")) {
            this.setSword(compound.getBoolean("SWORD"));
        }
        if (compound.contains("HELMET")) {
            this.setHelmet(compound.getBoolean("HELMET"));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("SWORD", this.getSword());
        compound.putBoolean("HELMET", this.getHelmet());
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (isTameFood(itemstack) && !this.isTame()) {
            tameGAnimal(player, itemstack, 6);
            return InteractionResult.CONSUME;

        } else if (this.isOwnedBy(player)){
            if(!this.isBaby()){
                if (itemstack.is(ItemInit.ICEPOP.get()) && !getSword()){
                    this.setSword(true);
                    itemstack.shrink(1);
                } else if (itemstack.is(ItemInit.ICE_HELMET.get()) && !getHelmet()) {
                    this.setHelmet(true);
                    itemstack.shrink(1);
                }
            }
        }

        updateAttributes();

        return super.mobInteract(player, hand);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(Items.TROPICAL_FISH);
    }

    @Override
    public boolean isTameFood(ItemStack itemStack) {
        return itemStack.is(Items.SALMON)||itemStack.is(Items.COD);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    @Override
    public void updateAttributes(){
        if (this.isTame()) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
            this.setHealth(20.0F);
            if(this.getSword()){
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(15.0D);
            } else {
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(6.0D);
            }
            if(this.getHelmet()){
                this.getAttribute(Attributes.ARMOR).setBaseValue(12.0D);
            } else {
                this.getAttribute(Attributes.ARMOR).setBaseValue(0.0D);
            }
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(2.5D);
            this.getAttribute(Attributes.ARMOR).setBaseValue(2.0D);
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        }
    }

    //sounds

    @Override
    public SoundEvent getAmbientSound() {
        return SoundInit.PENGUIN_AMBIENT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundInit.PENGUIN_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {return SoundInit.PENGUIN_HURT.get();}

    @Override
    protected void playStepSound(BlockPos p_20135_, BlockState p_20136_) {
        this.playSound(SoundInit.PENGUIN_STEPS.get(), 0.15F, 1.0F);
    }

    @Override
    public SoundEvent getTameSound(){
        return SoundInit.PENGUIN_HAPPY.get();
    }

    @Override
    public SoundEvent getInteractSound(){
        return SoundInit.PENGUIN_INTERACT.get();
    }

    @Override
    public EggBlockItem getEgg() {
        return (EggBlockItem) ItemInit.PENGUIN_EGG_ITEM.get();
    }

    //animation stuff

    @Override
    public String getAttackAnim() {
        return this.getSword()?"sword_attack":"attack";
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar control) {
        control.add(groundController(this));
        super.registerControllers(control);
    }
}
