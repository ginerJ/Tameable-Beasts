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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.List;

public class GrapteranodonEntity extends FlyingRideableTBAnimal {

    public GrapteranodonEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.textureIdSize = 5;
        this.healthFloor = 15;
        this.attackAnims.add("attack");
        this.consumeStaminaModule = 10;
        this.recoverStaminaModule = 10;
        this.downMovementAngle = 10F;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.FLYING_SPEED, 0.225D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.FOLLOW_RANGE, 48.0D)
                .add(Attributes.JUMP_STRENGTH);
    }

    public static boolean checkGrapteraSpawnRules(EntityType<GrapteranodonEntity> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos p_218245_, RandomSource p_218246_) {
        return checkAnimalSpawnRules(p_218242_,p_218243_,p_218244_,p_218245_,p_218246_) && ModCommonConfigs.CAN_SPAWN_GRAPTERA.get();
    }

    protected void registerGoals() {
        super.registerGoals();

        this.addGoals(
                new SitWhenOrderedToGoal(this),
                new TakeCareOfEggsGoal(this, 15, InitPOITypes.GRAPTERANODON_POI),
                new TameablePanicGoal(this, 1.25D),
                new RandomStrollGoal(this,1.0D),
                new TemptGoal(this, 1.0D, Ingredient.of(Items.SALMON), false),
                new FlyFromNowAndThenGoal(this),
                new TBFollowParentGoal(this, 1.0D),
                new BreedGoal(this, 1.0D),
                new WaterAvoidingRandomFlyingGoal(this, 1.0D),
                new LookAtPlayerGoal(this, Player.class, 6.0F),
                new FloatGoal(this)
        );

        this.addTargetGoals(
                new IncludesSitingRidingMeleeAttackGoal(this, 1.0D, true),
                new HurtByTargetGoal(this),
                new OwnerHurtTargetGoal(this),
                new OwnerHurtByTargetGoal(this)
        );
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
        boolean isFood = p_27600_.is(Items.SALMON);
        if(isFood)
            eatEmote = true;
        return isFood;
    }

    @Override
    public boolean isTameFood(ItemStack itemStack) {
        boolean isFood = this.getHealth() < 5 && itemStack.is(ItemInit.PTERANODON_MEAL.get());
        if(isFood)
            eatEmote = true;
        return isFood;
    }

    @Override
    public EggBlockItem getEgg() {
        return (EggBlockItem) ItemInit.GRAPTERANODON_EGG_ITEM.get();
    }

    @Override
    public float getRidingSpeedMultiplier() {
        if(this.isInWater())
            return 0.5f;

        LivingEntity passenger = getControllingPassenger();
        if (passenger instanceof Player p  && this.hatBoostItem() != null &&
                isHatBoostItem(p.getInventory().getArmor(3)))
            return 1.2f;

        return 0.8F;
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() - 0.2;
    }

    public void tryGrabbing() {
        if(!this.isFlying())
            return;

        if(this.getPassengers().size() >= 2){
            this.getPassengers().get(1).stopRiding();
            return;
        }

        playGrip = true;

        AABB boundingBox = this.getBoundingBox().inflate(2);

        List<Entity> nearbyEntities = level().getEntities(
                this,
                boundingBox,
                entity -> entity != this
        );

        nearbyEntities.stream().findFirst()
                .ifPresent(pickedUp -> pickedUp.startRiding(this));
    }

    @Override
    protected void positionRider(@NotNull Entity entity, @NotNull MoveFunction moveFunction) {

        if(this.getPassengers().size() >= 2 && this.getPassengers().get(1).equals(entity)){
            double d0 = this.getY() + this.getPassengersRidingOffset() + entity.getMyRidingOffset() -0.4;
            moveFunction.accept(entity, this.getX(), d0 - entity.getBbHeight(), this.getZ());
        } else
            super.positionRider(entity, moveFunction);
    }

    @Override
    protected boolean canAddPassenger(@NotNull Entity p_20354_) {
        return getPassengers().size() <= 1;
    }

    @Override
    public boolean onGround() {
        boolean onGround = super.onGround();

        if (onGround && this.getPassengers().size() >= 2)
            this.getPassengers().get(1).stopRiding();

        return onGround;
    }

    @Override
    public boolean shouldRiderSit() {
        return super.shouldRiderSit();
    }

    @Override
    public String getRidingMessage() {
        return super.getRidingMessage() + ", Left Click to Grab";
    }

    //sounds

    @Override
    public SoundEvent getAmbientSound() {
        if(isFlying())
            return SoundInit.GRAPTERA_FLY.get();
        eatEmote = true;
        return SoundInit.QUETZAL_AMBIENT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        eatEmote = true;
        return SoundInit.GRAPTERA_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource p_21239_) {
        eatEmote = true;
        return SoundInit.GRAPTERA_HURT.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos p_20135_, @NotNull BlockState p_20136_) {
        this.playSound(SoundInit.CHIKOTE_STEPS.get(), 0.15F, 1.0F);
    }

    @Override
    public SoundEvent getTameSound(){
        eatEmote = true;
        return SoundInit.GRAPTERA_INTERACT.get();
    }

    @Override
    public SoundEvent getInteractSound(){
        eatEmote = true;
        return SoundInit.GRAPTERA_INTERACT.get();
    }

    //animation stuff

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(addAnimationTriggers(glideFlyController(this)));
        controllers.add(legsMouthController(this));
    }

    public boolean playGrip = false;
    public boolean eatEmote = false;

    public <T extends FlyingRideableTBAnimal & GeoEntity> AnimationController<T> legsMouthController(T entity) {
        return new AnimationController<>(entity, "legs&mouthController", 10, event -> {
            AnimationController<T> controller = event.getController();

            if (playGrip) {
                controller.setAnimation(RawAnimation.begin().then("leg_grab", Animation.LoopType.PLAY_ONCE));
                playGrip = false;
                controller.forceAnimationReset();
            }
            if (eatEmote) {
                controller.setAnimation(RawAnimation.begin().then("bite", Animation.LoopType.PLAY_ONCE));
                eatEmote = false;
                controller.forceAnimationReset();
            }
            return PlayState.CONTINUE;
        });
    }

}
