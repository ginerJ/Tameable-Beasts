package com.modderg.tameablebeasts.server.entity;

import com.modderg.tameablebeasts.client.gui.TBItemStackHandler;
import com.modderg.tameablebeasts.client.gui.TBMenu;
import com.modderg.tameablebeasts.client.gui.TBMenuJustSaddle;
import com.modderg.tameablebeasts.server.ModCommonConfigs;
import com.modderg.tameablebeasts.registry.TBBlockRegistry;
import com.modderg.tameablebeasts.registry.TBPOITypesRegistry;
import com.modderg.tameablebeasts.server.entity.abstracts.RideableTBAnimal;

import com.modderg.tameablebeasts.server.entity.goals.*;
import com.modderg.tameablebeasts.registry.TBItemRegistry;
import com.modderg.tameablebeasts.server.item.block.EggBlockItem;
import com.modderg.tameablebeasts.client.sound.SoundInit;
import com.modderg.tameablebeasts.server.tags.TBTags;
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
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animation.AnimatableManager;

public class RolyPolyEntity extends RideableTBAnimal {

    public RolyPolyEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);

        this.hasWarmthVariants();

        this.inventory = new TBItemStackHandler(this, 1);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.225D)
                .add(Attributes.MAX_HEALTH, 20D);
    }

    @Override
    public void updateAttributes(){
        double movSpeed = 0.25D;
        if (this.isRunning())
            movSpeed = 0.3D;

        if(this.isTame())
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(40D);

        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(movSpeed);
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.ARTHROPOD;
    }


    @Override
    public boolean hatBoostItem(Player player) {
        return player.getInventory().getArmor(3).is(TBItemRegistry.BIKER_HELMET.get());
    }

    public static boolean checkRolyPolySpawnRules(EntityType<RolyPolyEntity> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos p_218245_, RandomSource p_218246_) {
        return checkAnimalSpawnRules(p_218242_,p_218243_,p_218244_,p_218245_,p_218246_) && ModCommonConfigs.CAN_SPAWN_ROLY_POLY.get();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.addGoals(
                new TameablePanicGoal(this, 1.2D),
                new TBFollowOwnerGoal(this, 1.0D, 10.0F, 6.0F),
                new FloatGoal(this),
                new SitWhenOrderedToGoal(this),
                new TemptGoal(this, 1.0D, Ingredient.of(TBTags.Items.ROLY_POLY_FOOD), false),
                new RunFromNowAndThenGoal(this),
                new TakeCareOfEggsGoal(this, 15, TBPOITypesRegistry.ROLY_POLY_POI),
                new WaterAvoidingRandomStrollGoal(this, 1.0D),
                new BreedGoal(this, 1.0D),
                new TBFollowParentGoal(this, 1.0D),
                new LookAtPlayerGoal(this, Player.class, 6.0F)
        );
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
    public Item[] getBrushDrops() {
        return new Item[]{TBItemRegistry.ROLY_POLY_PLAQUE.get()};
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(TBTags.Items.ROLY_POLY_FOOD);
    }

    @Override
    public boolean isTameFood(ItemStack itemStack) {
        return itemStack.is(TBTags.Items.ROLY_POLY_TAME_FOOD);
    }

    @Override
    public EggBlockItem getEgg() {
        return (EggBlockItem) TBItemRegistry.ROLY_POLY_EGG_ITEM.get();
    }

    //max actual speed 0.84, min 0.3
    @Override
    public float getRidingSpeedMultiplier() {
        return super.getRidingSpeedMultiplier() * (this.getBlockStateOn().is(TBBlockRegistry.ASPHALTED_DIRT.get()) ? 1.75F : 1.0F);
    }

    @Override
    protected TBMenu createMenu(int containerId, Inventory playerInventory) {
        return new TBMenuJustSaddle(containerId, playerInventory, this);
    }

    @Override
    public boolean hasSaddle() {
        return this.inventory.getStackInSlot(0).is(Items.SADDLE);
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
    protected SoundEvent getHurtSound(@NotNull DamageSource p_21239_) {return SoundInit.ROLYPOLY_HURT.get();}

    @Override
    protected void playStepSound(@NotNull BlockPos p_20135_, @NotNull BlockState p_20136_) {
        if(isRunning())
            this.playSound(SoundInit.ROLYPOLY_ROLL.get(), 0.15F, 1.0F);
        else
            this.playSound(SoundInit.ROLYPOLY_STEPS.get(), 0.15F, 1.0F);
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

    //animation stuff

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar control) {
        control.add(addAnimationTriggers(vehicleController(this)));
    }
}
