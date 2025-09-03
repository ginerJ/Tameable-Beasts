package com.modderg.tameablebeasts.server.entity;

import com.modderg.tameablebeasts.client.gui.TBItemStackHandler;
import com.modderg.tameablebeasts.client.gui.TBMenu;
import com.modderg.tameablebeasts.client.gui.TBMenuPenguin;
import com.modderg.tameablebeasts.registry.TBTagRegistry;
import com.modderg.tameablebeasts.server.ModCommonConfigs;
import com.modderg.tameablebeasts.registry.TBPOITypesRegistry;
import com.modderg.tameablebeasts.server.entity.abstracts.RideableTBAnimal;

import com.modderg.tameablebeasts.server.entity.abstracts.TBSemiAquatic;
import com.modderg.tameablebeasts.server.entity.goals.*;
import com.modderg.tameablebeasts.registry.TBItemRegistry;
import com.modderg.tameablebeasts.server.item.block.EggBlockItem;
import com.modderg.tameablebeasts.registry.TBSoundRegistry;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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

import static com.modderg.tameablebeasts.client.entity.TBAnimControllers.vehicleState;

public class PenguinEntity extends RideableTBAnimal implements GeoEntity, TBSemiAquatic {

    public PenguinEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);

        this.hasWarmthVariants();

        this.attackAnims.add("attack");
        this.attackAnims.add("sword_attack");
        this.attackAnims.add("sword_attack2");

        this.setMaxUpStep(1.0f);

        this.inventory = new TBItemStackHandler(this, 4);

        if(!level().isClientSide())
            initPathAndMoveControls();
    }

    @Override public @NotNull MobType getMobType() {
        return MobType.WATER;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {

        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 2.5D)
                .add(Attributes.ARMOR, 2.0D);
    }

    @Override
    public void updateAttributes(){

        double armor = 0;
        double movSpeed = 0.3D;
        double maxHealth = 10D;
        double attackDamage = 2.5D;

        if(isInWater())
            movSpeed = 2.5D;

        if (this.isTame()) {
            maxHealth = 35D;

            attackDamage = 5.0D + 5.0D * this.hasSword();

            if(this.getHelmet()) armor += 8d;
            if(this.hasSaddle()) armor += 12d;
        }

        this.getAttribute(Attributes.ARMOR).setBaseValue(armor);
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(movSpeed);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(maxHealth);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(attackDamage);
    }

    TBFollowOwnerGoal followOwnerGoal;

    @Override
    protected void registerGoals() {
        super.registerGoals();
        followOwnerGoal = new TBFollowOwnerGoal(this, 1.0D, 10.0F, 6.0F, false, true);

        addGoals(
                new SitWhenOrderedToGoal(this),
                new TameablePanicGoal(this, 1.2D),
                followOwnerGoal,
                new BreedGoal(this, 1.0D),
                new TakeCareOfEggsGoal(this, 15, TBPOITypesRegistry.PENGUIN_POI),
                new RunFromNowAndThenGoal(this),
                new TemptGoal(this, 1.1D, Ingredient.of(TBTagRegistry.Items.PENGUIN_FOOD), false),
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

    public int hasSword(){
        return (!this.isBaby() ? 1 : 0) * (Boolean.compare(this.inventory.getStackInSlot(2).is(TBItemRegistry.ICEPOP.get()), false)
                + Boolean.compare(this.inventory.getStackInSlot(3).is(TBItemRegistry.ICEPOP.get()), false));
    }

    public boolean getHelmet() {
        return !this.isBaby() && this.inventory.getStackInSlot(0).is(TBItemRegistry.ICE_HELMET.get());
    }

    @Override
    public boolean hasSaddle() {
        return !this.isBaby() && this.inventory.getStackInSlot(1).is(TBItemRegistry.ICE_CHESTPLATE.get());
    }

    @Override public TBFollowOwnerGoal getFollowOwnerGoal() {return followOwnerGoal;}

    @Override
    public EggBlockItem getEgg() {
        return (EggBlockItem) TBItemRegistry.PENGUIN_EGG_ITEM.get();
    }

    public static boolean checkPenguinSpawnRules(EntityType<PenguinEntity> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos p_218245_, RandomSource p_218246_) {
        return checkAnimalSpawnRules(p_218242_, p_218243_, p_218244_, p_218245_, p_218246_) && ModCommonConfigs.CAN_SPAWN_PENGUIN.get();
    }

    @Override
    protected TBMenu createMenu(int containerId, Inventory playerInventory) {
        return new TBMenuPenguin(containerId, playerInventory, this);
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
        if(!level().isClientSide() && this.isInWater() != this.isAquatic()){
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
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public Item[] getBrushDrops() {
        return new Item[]{Items.FEATHER};
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(TBTagRegistry.Items.PENGUIN_FOOD);
    }

    @Override
    public boolean isTameFood(ItemStack itemStack) {
        
        return itemStack.is(TBTagRegistry.Items.PENGUIN_TAME_FOOD);
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
        return TBSoundRegistry.PENGUIN_AMBIENT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return TBSoundRegistry.PENGUIN_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource p_21239_) {return TBSoundRegistry.PENGUIN_HURT.get();}

    @Override
    protected void playStepSound(@NotNull BlockPos p_20135_, @NotNull BlockState blockState) {
        if(this.isVehicle() && (blockState.is(Blocks.ICE)||
                blockState.is(Blocks.BLUE_ICE)||
                blockState.is(Blocks.PACKED_ICE)||
                blockState.is(Blocks.FROSTED_ICE)))
            this.playSound(SoundEvents.UI_STONECUTTER_TAKE_RESULT, 0.15F, 1.0F);

        this.playSound(TBSoundRegistry.PENGUIN_STEPS.get(), 0.15F, 1.0F);
    }

    @Override
    public SoundEvent getTameSound(){
        return TBSoundRegistry.PENGUIN_HAPPY.get();
    }

    @Override
    public SoundEvent getInteractSound(){
        return TBSoundRegistry.PENGUIN_INTERACT.get();
    }

    //ANIMATION STUFF

    @Override
    public void playAttackAnim() {
        triggerAnim("movement", this.hasSword() < 1 ? "attack": attackAnims.get(random.nextInt(attackAnims.size())));
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
