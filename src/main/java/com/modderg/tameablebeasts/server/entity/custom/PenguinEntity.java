package com.modderg.tameablebeasts.server.entity.custom;

import com.modderg.tameablebeasts.server.ModCommonConfigs;
import com.modderg.tameablebeasts.server.block.InitPOITypes;
import com.modderg.tameablebeasts.server.entity.RideableTBAnimal;

import com.modderg.tameablebeasts.server.entity.TBAnimal;
import com.modderg.tameablebeasts.server.entity.TBSemiAquatic;
import com.modderg.tameablebeasts.server.entity.goals.*;
import com.modderg.tameablebeasts.server.item.ItemInit;
import com.modderg.tameablebeasts.server.item.block.EggBlockItem;
import com.modderg.tameablebeasts.client.sound.SoundInit;
import com.modderg.tameablebeasts.server.tags.TBTags;
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
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.stream.IntStream;

public class PenguinEntity extends RideableTBAnimal implements GeoEntity, TBSemiAquatic {

    private static final EntityDataAccessor<Integer> SWORD = SynchedEntityData.defineId(PenguinEntity.class, EntityDataSerializers.INT);
    public void setSword(int i){this.getEntityData().set(SWORD, i);}
    public int getSword(){
        return this.getEntityData().get(SWORD);
    }

    private static final EntityDataAccessor<Boolean> HELMET = SynchedEntityData.defineId(PenguinEntity.class, EntityDataSerializers.BOOLEAN);
    public void setHelmet(boolean i){
        this.getEntityData().set(HELMET, i);
    }
    public boolean getHelmet(){
        return this.getEntityData().get(HELMET);
    }

    @Override
    public Item itemSaddle() {
        return ItemInit.ICE_CHESTPLATE.get();
    }

    public PenguinEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.textureIdSize = 5;
        this.attackAnims.add("attack");
        this.attackAnims.add("sword_attack");
        this.attackAnims.add("sword_attack2");

        if(!level().isClientSide())
            initPathAndMoveControls();
    }

    @Override public @NotNull MobType getMobType() {
        return MobType.WATER;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {

        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 2.5D)
                .add(Attributes.ARMOR, 2.0D);
    }

    TBFollowOwnerGoal followOwnerGoal;
    @Override public TBFollowOwnerGoal getFollowOwnerGoal() {return followOwnerGoal;}

    @Override
    protected void registerGoals() {
        super.registerGoals();
        followOwnerGoal = new TBFollowOwnerGoal(this, 1.0D, 10.0F, 6.0F, false, true);

        addGoals(
                new TameablePanicGoal(this, 1.2D),
                followOwnerGoal,
                new BreedGoal(this, 1.0D),
                new TakeCareOfEggsGoal(this, 15, InitPOITypes.PENGUIN_POI),
                new SitWhenOrderedToGoal(this),
                new RunFromNowAndThenGoal(this),
                new TemptGoal(this, 1.1D, Ingredient.of(TBTags.Items.PENGUIN_FOOD), false),
                new IncludesSitingRidingMeleeAttackGoal(this, 1.0D, false),
                new TameablePanicGoal(this, 1.25D),
                new SemiAquaticRandomStrollGoal(this, 1.0D),
                new TBFollowParentGoal(this, 1.0D),
                new LookAtPlayerGoal(this, Player.class, 6.0F),
                new RandomLookAroundGoal(this)
        );

        addTargetGoals(
                new OwnerHurtByTargetGoal(this),
                new OwnerHurtTargetGoal(this),
                new NonTameRandomTargetGoal<>(this, AbstractFish.class, false, null)
        );
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SWORD, 0);
        this.entityData.define(HELMET, false);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("SWORD"))
            this.setSword(compound.getInt("SWORD"));

        if (compound.contains("HELMET"))
            this.setHelmet(compound.getBoolean("HELMET"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("SWORD", this.getSword());
        compound.putBoolean("HELMET", this.getHelmet());
    }

    @Override
    public void updateAttributes(){

        if(isInWater())
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(2.5D);
        else
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);

        if (this.isTame()) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);

            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(5.0D + 5.0D * this.getSword());

            double armor = 0;
            if(this.getHelmet()) armor += 8d;
            if(this.hasSaddle()) armor += 12d;

            this.getAttribute(Attributes.ARMOR).setBaseValue(armor);

            return;
        }
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(2.5D);
        this.getAttribute(Attributes.ARMOR).setBaseValue(0.0D);
    }

    @Override
    protected void dropAllDeathLoot(@NotNull DamageSource p_21192_) {
        if(this.getHelmet())
            this.spawnAtLocation(ItemInit.ICE_HELMET.get());

        IntStream.range(0,this.getSword()).forEach(e->
                this.spawnAtLocation(ItemInit.ICEPOP.get()));

        super.dropAllDeathLoot(p_21192_);
    }

    @Override
    public EggBlockItem getEgg() {
        return (EggBlockItem) ItemInit.PENGUIN_EGG_ITEM.get();
    }

    public static boolean checkPenguinSpawnRules(EntityType<PenguinEntity> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos p_218245_, RandomSource p_218246_) {
        boolean isColdBiome = p_218243_.getBiome(p_218245_).value().getBaseTemperature() < 0.15F;

        return (p_218243_.getBlockState(p_218245_.below()).is(Blocks.PACKED_ICE)
                || p_218243_.getBlockState(p_218245_.below()).is(Blocks.ICE)
                || p_218243_.getBlockState(p_218245_.below()).is(Blocks.BLUE_ICE))
                && isColdBiome
                && ModCommonConfigs.CAN_SPAWN_PENGUIN.get();
    }


    //SWIMMING STUFF

    @Override
    public boolean isNoGravity() {
        return this.isInWater();
    }

    @Override
    public void tick() {
        if(this.isInWater() && !this.getPassengers().isEmpty())
            ejectPassengers();
        if(!level().isClientSide() &&
                this.isInWater() != this.isAquatic()){
            switchNavigation();
            updateAttributes();
        }

        super.tick();
    }

    @Override
    public boolean canDrownInFluidType(FluidType type) {
        if (type == ForgeMod.WATER_TYPE.get()) return false;
        return super.canDrownInFluidType(type);
    }
    @Override
    public boolean isPushedByFluid(FluidType type) {
        if (type == ForgeMod.WATER_TYPE.get()) return false;
        return super.isPushedByFluid(type);
    }

    @Override
    protected boolean isAffectedByFluids() {
        return false;
    }

    //TAMING STUFF

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (isTameFood(itemstack) && !this.isTame()) {
            tameGAnimal(player, itemstack, 20);
            return InteractionResult.SUCCESS;

        } else if (this.isOwnedBy(player)){
            if(!this.isBaby()){
                if (itemstack.is(ItemInit.ICEPOP.get()) && getSword()<2){
                    this.setSword(this.getSword()+1);
                    itemstack.shrink(1);
                    return InteractionResult.SUCCESS;
                } else if (itemstack.is(ItemInit.ICE_HELMET.get()) && !getHelmet()) {
                    this.setHelmet(true);
                    itemstack.shrink(1);
                    return InteractionResult.SUCCESS;
                }
            }
        }

        updateAttributes();

        return super.mobInteract(player, hand);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(TBTags.Items.PENGUIN_FOOD);
    }

    @Override
    public boolean isTameFood(ItemStack itemStack) {
        return itemStack.is(TBTags.Items.PENGUIN_TAME_FOOD);
    }

    //RIDING STUFF

    @Override
    public boolean shouldRiderSit() {
        return false;
    }

    //max actual speed 0.6, min 0.03
    @Override
    public float getRidingSpeedMultiplier(){

        BlockState blockState = getBlockStateOn();

        if(blockState.is(Blocks.ICE)||
                blockState.is(Blocks.BLUE_ICE)||
                blockState.is(Blocks.PACKED_ICE)||
                blockState.is(Blocks.FROSTED_ICE))
            return 2f;

        return 0.1f;
    }

    //SOUND STUFF

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
    protected SoundEvent getHurtSound(@NotNull DamageSource p_21239_) {return SoundInit.PENGUIN_HURT.get();}

    @Override
    protected void playStepSound(@NotNull BlockPos p_20135_, @NotNull BlockState blockState) {
        if(this.isVehicle() && (blockState.is(Blocks.ICE)||
                blockState.is(Blocks.BLUE_ICE)||
                blockState.is(Blocks.PACKED_ICE)||
                blockState.is(Blocks.FROSTED_ICE)))
            this.playSound(SoundEvents.UI_STONECUTTER_TAKE_RESULT, 0.15F, 1.0F);

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

    //ANIMATION STUFF

    @Override
    public void playAttackAnim() {
        triggerAnim("movement", this.getSword() < 1 ? "attack": attackAnims.get(random.nextInt(attackAnims.size())));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar control) {
        control.add(addAnimationTriggers(penguinVehicleController(this)));
    }

    public <T extends RideableTBAnimal & GeoEntity> AnimationController<T> penguinVehicleController(T entity) {
        return new AnimationController<>(entity,"movement", 3, event ->{
            if(entity.isInWater()){
                if(event.isMoving())
                    event.getController().setAnimation(RawAnimation.begin().then("swim", Animation.LoopType.LOOP));
                else
                    event.getController().setAnimation(RawAnimation.begin().then("sit", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }
            return vehicleState(entity, event);
        });
    }
}
