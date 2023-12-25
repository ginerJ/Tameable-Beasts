package com.modderg.tameablebeasts.entities.custom;

import com.modderg.tameablebeasts.block.custom.ScarecrowBlock;
import com.modderg.tameablebeasts.config.ModCommonConfigs;
import com.modderg.tameablebeasts.entities.RideableTameableGAnimal;
import com.modderg.tameablebeasts.entities.goals.AvoidBlockGoal;
import com.modderg.tameablebeasts.entities.goals.GFollowOwnerGoal;
import com.modderg.tameablebeasts.entities.goals.TameablePanicGoal;
import com.modderg.tameablebeasts.item.ItemInit;
import com.modderg.tameablebeasts.item.block.EggBlockItem;
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
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class ChikoteEntity extends RideableTameableGAnimal {

    @Override
    protected boolean isSaddle(ItemStack itemStack) {
        return itemStack.is(ItemInit.CHIKOTE_SADDLE.get());
    }

    public ChikoteEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.textureIdSize = 8;
        this.specialTxtIdSize = 9;
        this.randomHealthFloor = 20;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D);
    }

    protected float generateRandomMaxHealth(RandomSource p_218806_) {
        return 20.0F + (float)p_218806_.nextInt(8) + (float)p_218806_.nextInt(9);
    }

    public static boolean checkChikoteSpawnRules(EntityType<ChikoteEntity> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos p_218245_, RandomSource p_218246_) {
        return checkAnimalSpawnRules(p_218242_,p_218243_,p_218244_,p_218245_,p_218246_) && ModCommonConfigs.CAN_SPAWN_CHIKOTE.get();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new TameablePanicGoal(this, 1.2D));
        this.goalSelector.addGoal(0, new GFollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, Ingredient.of(Items.BEETROOT), false));
        this.goalSelector.addGoal(4, new AvoidBlockGoal<>(this, ScarecrowBlock.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, ScarecrowAllayEntity.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(8, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(9, new FollowParentGoalIfNotSitting(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(11, new RandomStrollGoal(this, 1.0D, 10));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (isTameFood(itemstack) && !this.isTame()) {
            tameGAnimal(player, itemstack, 10);
            return InteractionResult.CONSUME;
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(Items.BEETROOT);
    }

    @Override
    public boolean isTameFood(ItemStack itemStack) {
        return itemStack.is(Items.POTATO);
    }

    @Override
    public void updateAttributes(){
        if (this.isBaby()) {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.05D);
        } else if (this.getTextureID() == 8){
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        } else{
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        }
    }

    @Override
    public EggBlockItem getEgg() {
        return (EggBlockItem) ItemInit.CHIKOTE_EGG_ITEM.get();
    }

    //sounds

    @Override
    public SoundEvent getAmbientSound() {
        return SoundInit.CHIKOTE_AMBIENT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundInit.CHIKOTE_DEATH.get();
    }

    @org.jetbrains.annotations.Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {return SoundInit.CHIKOTE_HURT.get();}

    @Override
    protected void playStepSound(BlockPos p_20135_, BlockState p_20136_) {
        this.playSound(SoundInit.CHIKOTE_STEPS.get(), 0.15F, 1.0F);
    }

    @Override
    public SoundEvent getTameSound(){
        return SoundInit.CHIKOTE_HAPPY.get();
    }

    @Override
    public SoundEvent getInteractSound(){
        return SoundInit.CHIKOTE_INTERACT.get();
    }

    //animation stuff

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar control) {
        control.add(groundController(this));
        super.registerControllers(control);
    }
}
