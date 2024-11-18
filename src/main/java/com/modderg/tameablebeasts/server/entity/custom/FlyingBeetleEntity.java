package com.modderg.tameablebeasts.server.entity.custom;

import com.modderg.tameablebeasts.server.ModCommonConfigs;
import com.modderg.tameablebeasts.server.block.InitPOITypes;
import com.modderg.tameablebeasts.server.entity.EntityInit;
import com.modderg.tameablebeasts.server.entity.FlyingTBAnimal;

import com.modderg.tameablebeasts.server.entity.goals.*;
import com.modderg.tameablebeasts.server.item.ItemInit;
import com.modderg.tameablebeasts.server.item.block.EggBlockItem;
import com.modderg.tameablebeasts.client.sound.SoundInit;
import com.modderg.tameablebeasts.server.tags.TBTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
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
import software.bernie.geckolib.core.animation.AnimationController;

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
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.ARMOR, 15.0D);
    }

    public static boolean checkFlyingBeetleSpawnRules(EntityType<FlyingBeetleEntity> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos p_218245_, RandomSource p_218246_) {
        return ModCommonConfigs.CAN_SPAWN_FLYING_BEETLE.get();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.addGoals(
                new SitWhenOrderedToGoal(this),
                new OwnerHurtTargetGoal(this),
                new TakeCareOfEggsGoal(this, 15, InitPOITypes.FLYING_BEETLE_POI),
                new TameablePanicGoal(this, 1.25D),
                new TemptGoal(this, 1.1D, Ingredient.of(TBTags.Items.SHINY_BEETLE_FOOD), false),
                new RandomStrollAndFlightGoal(this,1.0D),
                new BreedGoal(this, 1.0D),
                new AvoidEntityGoal<>(this,Player.class, 6.0F, 1.0D, 1.2D),
                new FlyFromNowAndThenGoal(this),
                new TBFollowParentGoal(this, 1.0D),
                new LookAtPlayerGoal(this, Player.class, 6.0F)
        );
        this.addTargetGoals(
                new IncludesSitingRidingMeleeAttackGoal(this, 1.0D, true),
                new OwnerHurtTargetGoal(this),
                new OwnerHurtByTargetGoal(this)
        );
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (isTameFood(itemstack) && !this.isTame()) {
            tameGAnimal(player, itemstack, 20);
            updateAttributes();
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public boolean isTameFood(ItemStack itemStack) {
        return itemStack.is(TBTags.Items.SHINY_BEETLE_FOOD);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(TBTags.Items.SHINY_BEETLE_TAME_FOOD);
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
                        (this.getIsFlying() && !owner.onGround()))
                )
        );
    }

    @Override
    public EggBlockItem getEgg() {
        return (EggBlockItem) ItemInit.FLYING_BEETLE_EGG_ITEM.get();
    }

    @Override
    public void updateAttributes() {
        if (this.isTame())
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
    }

    //BEETLE DRONE STUFF

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        if(entity instanceof LivingEntity living){

            if(random.nextInt(100) < 5)
                this.spawnAtLocation(ItemInit.BEETLE_DUST.get());

            spawnDroneWithTarget(this, living);
            spawnDroneWithTarget(this, living);
        }
        return super.doHurtTarget(entity);
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float p_27568_) {
        if(source.getEntity() instanceof LivingEntity living){
            spawnDroneWithTarget(this, living);
            spawnDroneWithTarget(this, living);
            spawnDroneWithTarget(this, living);
        }

        return super.hurt(source, p_27568_);
    }

    public static void spawnDroneWithTarget(LivingEntity entity, LivingEntity target){
        BeetleDrone drone = EntityInit.BEETLE_DRONE.get().create(entity.level());

        if(drone == null)
            return;

        RandomSource random = entity.getRandom();
        drone.setTarget(target);
        drone.setPos(
                entity.getX() + random.nextFloat() - random.nextFloat(),
                entity.getY() + random.nextFloat(),
                entity.getZ() + random.nextFloat() - random.nextFloat());

        entity.level().addFreshEntity(drone);
        drone.playSound(SoundEvents.BEEHIVE_EXIT, 1.0F, 1.0F);
    }

    //sounds

    @Override
    public SoundEvent getAmbientSound() {
        if(getIsFlying())
            return SoundInit.BEETLE_FLY.get();

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
        control.add(addAnimationTriggers(flyingBeetleController(this)));
    }

    public <T extends FlyingTBAnimal & GeoEntity> AnimationController<T> flyingBeetleController(T entity) {
        return new AnimationController<>(entity,"movement", 5, event ->{
            if(entity.getIsFlying()){
                if(entity.tickCount % 5 == 0)
                    entity.level().addParticle(ParticleTypes.GLOW, this.getRandomX(0.6D), this.getRandomY(), this.getRandomZ(0.6D), 0.0D, 0.0D, 0.0D);
                return flyState(entity, event);
            }

            return groundState(entity, event);
        });
    }
}
