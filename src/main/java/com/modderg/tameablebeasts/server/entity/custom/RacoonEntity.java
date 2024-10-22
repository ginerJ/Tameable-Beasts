package com.modderg.tameablebeasts.server.entity.custom;

import com.modderg.tameablebeasts.server.ModCommonConfigs;
import com.modderg.tameablebeasts.server.entity.goals.*;
import com.modderg.tameablebeasts.server.entity.TBAnimal;
import com.modderg.tameablebeasts.server.item.ItemInit;
import com.modderg.tameablebeasts.client.sound.SoundInit;
import com.modderg.tameablebeasts.server.tags.TBTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animation.AnimatableManager;

import java.util.function.Predicate;

public class RacoonEntity extends TBAnimal implements GeoEntity {

    private static final Predicate<LivingEntity> AVOID_VICTIMS = (p_28463_) -> !p_28463_.isDiscrete() && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(p_28463_);

    private static final EntityDataAccessor<Boolean> IS_BELLY_FULL = SynchedEntityData.defineId(TBAnimal.class, EntityDataSerializers.BOOLEAN);
    public void setBellyFull(boolean i){
        this.getEntityData().set(IS_BELLY_FULL, i);
    }
    public boolean isBellyFull(){return this.getEntityData().get(IS_BELLY_FULL);}

    public RacoonEntity(EntityType<? extends TBAnimal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
        this.textureIdSize = 3;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D);
    }

    public static boolean checkRacoonSpawnRules(EntityType<RacoonEntity> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos p_218245_, RandomSource p_218246_) {
        return checkAnimalSpawnRules(p_218242_,p_218243_,p_218244_,p_218245_,p_218246_) && ModCommonConfigs.CAN_SPAWN_RACOON.get();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.addGoals(
                new TBFollowOwnerGoal(this, 1.0D, 10.0F, 6.0F),
                new FloatGoal(this),
                new RacoonStealFoodGoal(this, 1.3D),
                new AvoidEntityGoal<>(this, Player.class, 16.0F, 1.6D, 1.4D, (player) -> this.isBellyFull() && !this.isTame() && AVOID_VICTIMS.test(player)),
                new LeapAtTargetGoal(this, 0.4f),
                new TemptGoal(this, 1.1D, Ingredient.of(TBTags.Items.RACOON_FOOD), false),
                new SitWhenOrderedToGoal(this),
                new BreedGoal(this, 1.0D),
                new TBFollowParentGoal(this, 1.0D),
                new TameablePanicGoal(this, 1.2D),
                new LookAtPlayerGoal(this, Player.class, 6.0F),
                new WaterAvoidingRandomStrollGoal(this, 1.0D),
                new RandomLookAroundGoal(this)
        );

        this.addTargetGoals(
                new OwnerHurtByTargetGoal(this),
                new OwnerHurtTargetGoal(this),
                new IncludesSitingRidingMeleeAttackGoal(this, 1.0D, true)
        );
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_BELLY_FULL, false);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("TEXTUREID"))
            this.setTextureId(compound.getInt("TEXTUREID"));

        if (compound.contains("IS_BELLY_FULL"))
            this.setBellyFull(compound.getBoolean("IS_BELLY_FULL"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("TEXTUREID", this.getTextureID());
        compound.putBoolean("IS_BELLY_FULL", this.isBellyFull());
    }

    private int dropFurTime = getRandom().nextInt(300,1000);

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (this.isTameFood(itemstack) && !this.isTame()) {
            this.updateAttributes();
            tameGAnimal(player, itemstack, 15);
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(TBTags.Items.RACOON_FOOD);
    }

    public boolean isTameFood(ItemStack itemStack) {
        return itemStack.is(TBTags.Items.RACOON_TAME_FOOD);
    }

    @Override
    public void tick() {
        if(this.isBellyFull() && dropFurTime-- <= 0) {
            dropFurTime = getRandom().nextInt(300,1500);
            spawnAtLocation(ItemInit.FUR.get());
            this.setBellyFull(false);
        }
        super.tick();
    }

    public void updateAttributes(){
        if (this.isTame())
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(15.0D);
        else
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);

        if(isBellyFull()) this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2D);
        else this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
    }

    //sounds

    @Override
    public SoundEvent getAmbientSound() {
        return SoundInit.RACOON_AMBIENT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundInit.RACOON_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {return SoundInit.RACOON_HURT.get();}

    @Override
    protected void playStepSound(BlockPos p_20135_, BlockState p_20136_) {
        this.playSound(SoundInit.RACOON_STEPS.get(), 0.15F, 1.0F);
    }

    @Override
    public SoundEvent getTameSound(){
        return SoundInit.RACOON_INTERACT.get();
    }

    @Override
    public SoundEvent getInteractSound(){return SoundInit.RACOON_HAPPY.get();}

    //animation stuff

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar control) {
        control.add(addAnimationTriggers(groundController(this)));
    }
}
