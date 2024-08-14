package com.modderg.tameablebeasts.server.entity.custom;

import com.modderg.tameablebeasts.client.sound.SoundInit;
import com.modderg.tameablebeasts.server.ModCommonConfigs;
import com.modderg.tameablebeasts.server.entity.FlyingRideableTBAnimal;
import com.modderg.tameablebeasts.server.entity.goals.*;
import com.modderg.tameablebeasts.server.item.ItemInit;
import com.modderg.tameablebeasts.server.item.block.EggBlockItem;
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
import software.bernie.geckolib.core.animation.AnimatableManager;

public class GrapteranodonEntity extends FlyingRideableTBAnimal {


    public GrapteranodonEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.textureIdSize = 3;
        this.healthFloor = 20;
        this.attackAnims.add("attack");
        this.consumeStaminaModule = 10;
        this.recoverStaminaModule = 8;
        this.downMovementAngle = 10F;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.FLYING_SPEED, 0.15D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.FOLLOW_RANGE, 48.0D)
                .add(Attributes.JUMP_STRENGTH);
    }

    public static boolean checkGrapteraSpawnRules(EntityType<GrapteranodonEntity> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos p_218245_, RandomSource p_218246_) {
        return checkAnimalSpawnRules(p_218242_,p_218243_,p_218244_,p_218245_,p_218246_) && ModCommonConfigs.CAN_SPAWN_QUETZAL.get();
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
        this.targetSelector.addGoal(1, new IncludesSitingRidingMeleeAttackGoal(this, 1.0D, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtByTargetGoal(this));
        this.goalSelector.addGoal(3, new TakeCareOfEggsGoal(this, 15, InitPOITypes.GRAPTERANODON_POI));
        this.goalSelector.addGoal(4, new TameablePanicGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this,1.0D));
        this.goalSelector.addGoal(6, new TemptGoal(this, 1.0D, Ingredient.of(ItemInit.BIG_BIRD_MEAT.get()), false));
        this.goalSelector.addGoal(7, new FlyFromNowAndThenGoal(this));
        this.goalSelector.addGoal(8, new TBFollowParentGoal(this, 1.0D));
        this.goalSelector.addGoal(9, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(9, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(11, new FloatGoal(this));
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (isTameFood(itemstack) && !this.isTame()) {
            tameGAnimal(player, itemstack, 33);
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public Item itemSaddle() {
        return ItemInit.QUETZAL_SADDLE.get();
    }

    @Override
    public Item hatBoostItem() {
        return ItemInit.FLYING_HELMET.get();
    }

    @Override
    public boolean isFood(ItemStack p_27600_) {
        return p_27600_.is(ItemInit.BIG_BIRD_MEAT.get());
    }

    @Override
    public boolean isTameFood(ItemStack itemStack) {
        return this.getHealth() < 5 && itemStack.is(ItemInit.PTERANODON_MEAL.get());
    }

    @Override
    public EggBlockItem getEgg() {
        return (EggBlockItem) ItemInit.GRAPTERANODON_EGG_ITEM.get();
    }

    @Override
    public float getRidingSpeedMultiplier() {
        if(this.isInWater())
            return 0.4f;

        LivingEntity passenger = getControllingPassenger();
        if (passenger instanceof Player p  && this.hatBoostItem() != null &&
                isHatBoostItem(p.getInventory().getArmor(3)))
            return 1.1f;

        return 0.8F;
    }

    //sounds

    @Override
    public SoundEvent getAmbientSound() {
        if(isFlying())
            return SoundInit.QUETZAL_FLY.get();

        return SoundInit.QUETZAL_AMBIENT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundInit.QUETZAL_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {return SoundInit.QUETZAL_HURT.get();}

    @Override
    protected void playStepSound(BlockPos p_20135_, BlockState p_20136_) {
        this.playSound(SoundInit.QUETZAL_STEPS.get(), 0.15F, 1.0F);
    }

    @Override
    public SoundEvent getTameSound(){
        return SoundInit.QUETZAL_INTERACT.get();
    }

    @Override
    public SoundEvent getInteractSound(){
        return SoundInit.QUETZAL_INTERACT.get();
    }

    //animation stuff

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar control) {
        control.add(addAnimationTriggers(glideFlyController(this)));
    }
}
