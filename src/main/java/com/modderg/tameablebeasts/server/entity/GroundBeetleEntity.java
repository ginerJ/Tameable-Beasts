package com.modderg.tameablebeasts.server.entity;

import com.modderg.tameablebeasts.registry.TBTagRegistry;
import com.modderg.tameablebeasts.server.ModCommonConfigs;
import com.modderg.tameablebeasts.server.entity.abstracts.TBAnimal;
import com.modderg.tameablebeasts.server.entity.goals.TBFollowOwnerGoal;
import com.modderg.tameablebeasts.server.entity.goals.IncludesSitingRidingMeleeAttackGoal;
import com.modderg.tameablebeasts.registry.TBPOITypesRegistry;
import com.modderg.tameablebeasts.server.entity.goals.TakeCareOfEggsGoal;
import com.modderg.tameablebeasts.registry.TBItemRegistry;
import com.modderg.tameablebeasts.server.item.block.EggBlockItem;
import com.modderg.tameablebeasts.client.sound.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
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
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

public class GroundBeetleEntity extends TBAnimal implements GeoEntity, NeutralMob {

    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(GroundBeetleEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> BEETLE_ID = SynchedEntityData.defineId(GroundBeetleEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> IRON_CONSUMED = SynchedEntityData.defineId(GroundBeetleEntity.class, EntityDataSerializers.INT);

    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);

    @Nullable
    private UUID persistentAngerTarget;

    public GroundBeetleEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.hasWarmthVariants();
        this.attackAnims.add("attack");
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.ARMOR, 15.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
        this.entityData.define(BEETLE_ID, 0);
        this.entityData.define(IRON_CONSUMED, 0);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        if (compound.contains("BEETLE_ID"))
            this.setBeetleId(compound.getInt("BEETLE_ID"));

        if (compound.contains("IRON_CONSUMED"))
            this.setConsumedIron(compound.getInt("IRON_CONSUMED"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("BEETLE_ID", this.getBeetleID());
        compound.putInt("IRON_CONSUMED", this.getConsumedIron());
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (this.isTameFood(itemstack) && !this.isTame()) {
            tameGAnimal(player, itemstack, 20);
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    public void setBeetleId(int i){
        this.getEntityData().set(BEETLE_ID, i);
    }

    public int getBeetleID(){
        return this.getEntityData().get(BEETLE_ID);
    }

    public void setConsumedIron(int i){
        this.getEntityData().set(IRON_CONSUMED, i);
    }

    public int getConsumedIron(){
        return this.getEntityData().get(IRON_CONSUMED);
    }

    public boolean isMetallic() {return this.getConsumedIron() > ModCommonConfigs.MIN_IRON_TRANSFORMS_GROUND_BEETLE.get();}

    public static boolean checkGroundBeetleSpawnRules(EntityType<GroundBeetleEntity> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos p_218245_, RandomSource p_218246_) {
        return ModCommonConfigs.CAN_SPAWN_GROUND_BEETLE.get();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.addGoals(
                new TBFollowOwnerGoal(this, 1.0D, 10.0F, 6.0F),
                new FleeSunGoal(this, 3.0F),
                new TakeCareOfEggsGoal(this, 15, TBPOITypesRegistry.GROUND_BEETLE_POI),
                new FloatGoal(this),
                new LeapAtTargetGoal(this, 0.4f),
                new SitWhenOrderedToGoal(this),
                new TemptGoal(this, 1.1D, Ingredient.of(TBTagRegistry.Items.GROUND_BEETLE_FOOD), false),
                new IncludesSitingRidingMeleeAttackGoal(this, 1.0D, true),
                new BreedGoal(this, 1.0D),
                new WaterAvoidingRandomStrollGoal(this, 1.0D),
                new LookAtPlayerGoal(this, Player.class, 6.0F),
                new RandomLookAroundGoal(this)
        );

        this.addTargetGoals(
                new IncludesSitingRidingMeleeAttackGoal(this, 1.0D, true),
                new OwnerHurtByTargetGoal(this),
                new HurtByTargetGoal(this),
                new OwnerHurtTargetGoal(this)
        );
    }

    @Override
    public Item[] getBrushDrops() {
        return new Item[]{TBItemRegistry.BEETLE_ARMOR_PIECE.get(), this.isMetallic() ? Items.RAW_IRON : TBItemRegistry.BEETLE_ARMOR_PIECE.get()};
    }

    @Override
    public boolean isTameFood(ItemStack itemStack) {
        
        boolean isFood = itemStack.is(TBTagRegistry.Items.GROUND_BEETLE_FOOD);
        if (isFood)
            playBite = true;
        return isFood;
    }

    @Override
    public boolean isFood(ItemStack p_27600_) {
        boolean isFood = p_27600_.is(TBTagRegistry.Items.GROUND_BEETLE_TAME_FOOD);
        boolean isMetalFood = p_27600_.is(TBTagRegistry.Items.GROUND_BEETLE_METAL_FOOD);

        if (!this.isBaby() && isMetalFood)
            this.setConsumedIron(this.getConsumedIron() + 1);

        if (isFood)
            playBite = true;

        return isFood || isMetalFood;
    }

    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel p_146743_, @NotNull AgeableMob parent) {
        return null;
    }

    @Override
    public void updateAttributes(){
        double movSpeed = 0.3D;
        double maxHealth = 15D;
        double armor = 15D;
        double knockResist = 0D;

        if (this.isBaby())
            movSpeed = 0.1D;

        if (this.isTame())
            maxHealth = 30D;

        if (this.isMetallic()){
            armor = 30D;
            knockResist = 0.8D;
        }

        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(movSpeed);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(maxHealth);
        this.getAttribute(Attributes.ARMOR).setBaseValue(armor);
        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(knockResist);
    }

    @Override
    public EggBlockItem getEgg() {
        return (EggBlockItem) TBItemRegistry.GROUND_BEETLE_EGG_ITEM.get();
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity target) {
        boolean ret = super.doHurtTarget(target);

        if (!this.isMetallic() && !(target instanceof LivingEntity))
            return ret;

        LivingEntity livingTarget = (LivingEntity) target;

        if (this.getBeetleID() == 1){
            Vec3 currMotion = target.getDeltaMovement();

            Vec3 newMotion = new Vec3(currMotion.x, (1- Objects.requireNonNull(livingTarget.getAttribute(Attributes.KNOCKBACK_RESISTANCE)).getBaseValue()), currMotion.z);
            target.setDeltaMovement(newMotion);

            return ret;
        }

        double dx = this.getX() - target.getX();
        double dz =  this.getZ() - target.getZ();

        livingTarget.knockback(1.5D, dx, dz);

        return ret;
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor levelAccessor, @NotNull DifficultyInstance p_146747_, @NotNull MobSpawnType p_146748_, @org.jetbrains.annotations.Nullable SpawnGroupData p_146749_, @org.jetbrains.annotations.Nullable CompoundTag p_146750_) {

        this.setBeetleId(this.getRandom().nextInt(2));

        return super.finalizeSpawn(levelAccessor, p_146747_, p_146748_, p_146749_, p_146750_);
    }

    //sounds

    @Override
    public SoundEvent getAmbientSound() {
        playBite = true;
        return SoundInit.BEETLE_AMBIENT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        playBite = true;
        return SoundInit.BEETLE_DEATH.get();
    }

    @org.jetbrains.annotations.Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource p_21239_) {
        playBite = true;
        return SoundInit.BEETLE_HURT.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos p_20135_, @NotNull BlockState p_20136_) {
        this.playSound(SoundInit.BEETLE_STEPS.get(), 0.15F, 1.0F);
    }

    @Override
    public SoundEvent getTameSound(){
        playBite = true;
        return SoundInit.BEETLE_INTERACT.get();
    }

    @Override
    public SoundEvent getInteractSound(){
        playBite = true;
        return SoundInit.BEETLE_INTERACT.get();
    }

    //anger stuff

    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    public void setRemainingPersistentAngerTime(int p_30404_) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, p_30404_);
    }

    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Nullable
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    public void setPersistentAngerTarget(@Nullable UUID p_30400_) {
        this.persistentAngerTarget = p_30400_;
    }

    //animation stuff

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar control) {
        control.add(addAnimationTriggers(groundController(this)));
        control.add(biteController(this));
    }

    public boolean playBite = false;

    public <T extends TBAnimal & GeoEntity> AnimationController<T> biteController(T entity) {
        return new AnimationController<>(entity, "mouthController", 10, event -> {
            AnimationController<T> controller = event.getController();
            if (playBite) {
                controller.setAnimation(RawAnimation.begin().then("bite", Animation.LoopType.PLAY_ONCE));
                playBite = false;
            }
            return PlayState.CONTINUE;
        });
    }
}
