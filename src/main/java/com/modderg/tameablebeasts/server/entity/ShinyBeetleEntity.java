package com.modderg.tameablebeasts.server.entity;

import com.modderg.tameablebeasts.registry.TBTagRegistry;
import com.modderg.tameablebeasts.server.ModCommonConfigs;
import com.modderg.tameablebeasts.registry.TBPOITypesRegistry;
import com.modderg.tameablebeasts.registry.TBEntityRegistry;
import com.modderg.tameablebeasts.server.entity.abstracts.FlyingTBAnimal;

import com.modderg.tameablebeasts.server.entity.goals.*;
import com.modderg.tameablebeasts.registry.TBItemRegistry;
import com.modderg.tameablebeasts.server.item.block.EggBlockItem;
import com.modderg.tameablebeasts.registry.TBSoundRegistry;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.ClientUtils;

import static com.modderg.tameablebeasts.client.entity.TBAnimControllers.flyWalkingController;

public class ShinyBeetleEntity extends FlyingTBAnimal {

    public ShinyBeetleEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.hasWarmthVariants();
        this.extraTameParticles = ParticleTypes.GLOW;
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

    @Override
    public void updateAttributes(){
        double movSpeed = 0.3D;
        if (this.isBaby())
            movSpeed = 0.1D;

        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(movSpeed);

        double maxHealth = 10D;
        if (this.isTame())
            maxHealth = 20D;

        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(maxHealth);
    }

    public static boolean checkFlyingBeetleSpawnRules(EntityType<ShinyBeetleEntity> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos p_218245_, RandomSource p_218246_) {
        return ModCommonConfigs.CAN_SPAWN_FLYING_BEETLE.get();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.addGoals(
                new SitWhenOrderedToGoal(this),
                new OwnerHurtTargetGoal(this),
                new TakeCareOfEggsGoal(this, 15, TBPOITypesRegistry.FLYING_BEETLE_POI),
                new TameablePanicGoal(this, 1.25D),
                new TemptGoal(this, 1.1D, Ingredient.of(TBTagRegistry.Items.SHINY_BEETLE_FOOD), false),
                new NoFlyRandomStrollGoal(this,1.0D),
                new BreedGoal(this, 1.0D),
                new AvoidEntityGoal<>(this,Player.class, 6.0F, 1.0D, 1.2D),
                new FlyFromNowAndThenGoal(this),
                new WaterAvoidingRandomFlyingGoal(this, 1.0D),
                new TBFollowParentGoal(this, 1.0D),
                new LookAtPlayerGoal(this, Player.class, 6.0F),
                new RandomLookAroundGoal(this)
        );

        this.addTargetGoals(
                new IncludesSitingRidingMeleeAttackGoal(this, 1.0D, true),
                new OwnerHurtTargetGoal(this),
                new OwnerHurtByTargetGoal(this)
        );
    }

    int flyingSoundCount = 80;

    @Override
    public void tick() {
        super.tick();

        if(!this.level().isClientSide())
            if(this.isFlying() && flyingSoundCount-- == 0){
                this.playSound(TBSoundRegistry.BEETLE_FLY.get());
                flyingSoundCount = 15;
            }

        else if(this.isFlying() && !this.isInSittingPose())
            if(this.tickCount % 5 == 0)
                this.level().addParticle(ParticleTypes.GLOW, this.getRandomX(0.6D), this.getRandomY(), this.getRandomZ(0.6D),
                        0.0D, 0.0D, 0.0D);

        if (!this.isOrderedToSit() && this.getTarget() != null && this.tickCount%80==0)
            spawnDroneWithTarget(this, this.getTarget());
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
        return new Item[]{TBItemRegistry.BEETLE_ARMOR_PIECE.get(), TBItemRegistry.BEETLE_DUST.get()};
    }

    @Override
    public boolean isTameFood(ItemStack itemStack) {
        
        return itemStack.is(TBTagRegistry.Items.SHINY_BEETLE_FOOD);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(TBTagRegistry.Items.SHINY_BEETLE_TAME_FOOD);
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
                        (this.isFlying() && !owner.onGround()))
                )
        );
    }

    @Override
    public EggBlockItem getEgg() {
        return (EggBlockItem) TBItemRegistry.FLYING_BEETLE_EGG_ITEM.get();
    }

    //BEETLE DRONE STUFF

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        if(!this.isBaby() && entity instanceof LivingEntity living){

            if(random.nextInt(100) < 5)
                this.spawnAtLocation(TBItemRegistry.BEETLE_DUST.get());

            spawnDroneWithTarget(this, living);
            spawnDroneWithTarget(this, living);
        }
        return super.doHurtTarget(entity);
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float p_27568_) {
        if(!this.isBaby() && source.getEntity() instanceof LivingEntity living){
            spawnDroneWithTarget(this, living);
            spawnDroneWithTarget(this, living);
            spawnDroneWithTarget(this, living);
        }

        return super.hurt(source, p_27568_);
    }

    public static void spawnDroneWithTarget(LivingEntity entity, LivingEntity target){
        BeetleDrone drone = TBEntityRegistry.BEETLE_DRONE.get().create(entity.level());

        if(drone == null)
            return;

        RandomSource random = entity.getRandom();
        drone.setTarget(target);
        drone.setPos(
                entity.getX() + (random.nextFloat() - random.nextFloat())*2,
                entity.getY() + random.nextFloat() - random.nextFloat(),
                entity.getZ() + (random.nextFloat() - random.nextFloat())*2
        );

        entity.level().addFreshEntity(drone);
        drone.playSound(SoundEvents.BEEHIVE_EXIT, 1.0F, 1.0F);
    }

    //sounds

    @Override
    public SoundEvent getAmbientSound() {
        return TBSoundRegistry.BEETLE_AMBIENT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return TBSoundRegistry.BEETLE_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource p_21239_) {return TBSoundRegistry.BEETLE_HURT.get();}

    @Override
    protected void playStepSound(@NotNull BlockPos p_20135_, @NotNull BlockState p_20136_) {
        this.playSound(TBSoundRegistry.BEETLE_STEPS.get(), 0.15F, 1.0F);
    }

    @Override
    public SoundEvent getTameSound(){
        return TBSoundRegistry.BEETLE_INTERACT.get();
    }

    @Override
    public SoundEvent getInteractSound(){
        return TBSoundRegistry.BEETLE_INTERACT.get();
    }

    //animation stuff

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar control) {
        control.add(addAnimationTriggers(flyWalkingController(this)).setSoundKeyframeHandler(state -> {
            Player player = ClientUtils.getClientPlayer();

            if (player != null)
                player.playSound(TBSoundRegistry.BEETLE_FLY.get());
        }));
    }
}
