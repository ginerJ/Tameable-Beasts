package com.modderg.tameablebeasts.entities;

import com.modderg.tameablebeasts.config.ModCommonConfigs;
import com.modderg.tameablebeasts.core.TameableGAnimal;
import com.modderg.tameablebeasts.core.goals.SwitchingFollowOwnerGoal;
import com.modderg.tameablebeasts.core.goals.SwitchingMeleeAttackGoal;
import com.modderg.tameablebeasts.core.goals.TameablePanicGoal;
import com.modderg.tameablebeasts.init.ItemInit;
import com.modderg.tameablebeasts.init.ModEntityClass;
import com.modderg.tameablebeasts.init.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
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
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Wolf;
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
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

import static net.minecraft.world.entity.ai.attributes.Attributes.FLYING_SPEED;
import static net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED;

public class QuetzalcoatlusEntity extends TameableGAnimal implements GeoEntity, ItemSteerable, PlayerRideableJumping, NeutralMob {
    private int flyCount = 0;

    @Nullable
    private UUID persistentAngerTarget;
    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(QuetzalcoatlusEntity.class, EntityDataSerializers.INT);
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);

    private static final EntityDataAccessor<Integer> TEXTUREID = SynchedEntityData.defineId(QuetzalcoatlusEntity.class, EntityDataSerializers.INT);
    public void setTexture(int i){
        this.getEntityData().set(TEXTUREID, i);
    }
    public int getTextureID(){
        return this.getEntityData().get(TEXTUREID);
    }

    private static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(QuetzalcoatlusEntity.class, EntityDataSerializers.BOOLEAN);
    public void setFlying(boolean i){
        this.getEntityData().set(FLYING, i);
    }
    public boolean getFlying(){
        return this.getEntityData().get(FLYING);
    }

    private static final EntityDataAccessor<Boolean> SADDLE = SynchedEntityData.defineId(QuetzalcoatlusEntity.class, EntityDataSerializers.BOOLEAN);
    public void setSaddle(boolean i){
        this.getEntityData().set(SADDLE, i);
    }
    public boolean getSaddle(){
        return this.getEntityData().get(SADDLE);
    }

    public QuetzalcoatlusEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.switchNavigation(getFlying());
    }

    public boolean isTameFood(ItemStack itemStack) {
        return itemStack.is(Items.BEEF);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FLYING_SPEED, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.FOLLOW_RANGE, 48.0D)
                .add(Attributes.JUMP_STRENGTH);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
        this.targetSelector.addGoal(1, new SwitchingMeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(2, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(3, new OwnerHurtTargetGoal(this));
        this.goalSelector.addGoal(4, new TameablePanicGoal(this, 1.25D));
        this.goalSelector.addGoal(4, new SwitchingFollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(5, new TameablePanicGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new TemptGoal(this, 1.0D, Ingredient.of(Items.ROTTEN_FLESH), false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new FollowParentGoalIfNotSitting(this, 1.0D));
        this.goalSelector.addGoal(9, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(11, new FloatGoal(this));
        this.goalSelector.addGoal(12, new QuetzalRandomLookAroundGoal(this, this));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("TEXTUREID", this.getTextureID());
        compound.putBoolean("FLYING", this.getFlying());
        compound.putBoolean("SADDLE", this.getSaddle());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TEXTUREID, 0);
        this.entityData.define(FLYING, false);
        this.entityData.define(SADDLE, false);
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("TEXTUREID")) {
            this.setTexture(compound.getInt("TEXTUREID"));
        }
        if (compound.contains("FLYING")) {
            this.setFlying(compound.getBoolean("FLYING"));
            switchNavigation(this.getFlying());
        }
        if (compound.contains("SADDLE")) {
            this.setSaddle(compound.getBoolean("SADDLE"));
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_146746_, DifficultyInstance p_146747_, MobSpawnType p_146748_, @org.jetbrains.annotations.Nullable SpawnGroupData p_146749_, @org.jetbrains.annotations.Nullable CompoundTag p_146750_) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double)this.generateRandomMaxHealth(p_146746_.getRandom()));
        this.setHealth(this.getMaxHealth());
        this.setTexture(this.random.nextInt(5));
        return super.finalizeSpawn(p_146746_, p_146747_, p_146748_, p_146749_, p_146750_);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (this.isTame() && !this.isFood(itemstack)){
            if(this.isOwnedBy(player)){
                if (player.isShiftKeyDown()) {
                    this.setOrderedToSit(!this.isOrderedToSit());
                    this.setAggressive(false);
                    this.setTarget(null);
                    switchNavigation(false);
                    return InteractionResult.CONSUME;

                } else if (!this.isBaby()){
                    if (this.getSaddle() && !this.isInSittingPose()){
                        player.startRiding(this);
                        return InteractionResult.sidedSuccess(this.getLevel().isClientSide);

                    } else if (itemstack.is(ItemInit.QUETZAL_SADDLE.get()) && !this.getSaddle()) {
                        setSaddle(true);
                        this.playSound(SoundEvents.HORSE_SADDLE, 0.15F, 1.0F);
                        itemstack.shrink(1);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }

        interact = 60 ;
        return super.mobInteract(player, hand);
    }

    private Boolean shouldFly(){
        float followRange;
        if(this.isTame()){
            followRange = 12f;
            if(this.isInSittingPose()){
                return false;
            }
            if(this.isAggressive()){
                return true;
            }
            if(this.m_269323_() != null && this.getLevel() != null){
                if(this.distanceTo(this.m_269323_()) > followRange){
                    return true;
                }
                if(getFlying() && !this.m_269323_().isOnGround()){
                    return true;
                }
            }
            return false;
        } else {
            if(flyCount > 0){
                return true;
            }
            if(isAggressive()){
                return true;
            }
        }
        return false;
    }

    @Override
    public void tick() {
        if(!this.hasControllingPassenger() && !this.isInSittingPose() ){
            this.switchNavigation(shouldFly());
        }
        if(interact >= 0){
            interact --;
        }
        if(flyCount >= 0){
            flyCount --;
        }
        super.tick();
    }

    @Override
    public @org.jetbrains.annotations.Nullable AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        QuetzalcoatlusEntity quetzal = ModEntityClass.QUETZALCOATLUS.get().create(p_146743_);
        UUID uuid = this.getOwnerUUID();
        if (uuid != null) {
            quetzal.setTexture(this.getTextureID());
            quetzal.setOwnerUUID(uuid);
            quetzal.setTame(true);
        }

        return quetzal;
    }

    @Override
    public boolean isFood(ItemStack p_27600_) {
        return p_27600_.is(Items.ROTTEN_FLESH);
    }

    private void switchNavigation(Boolean b){
        if(b && !(moveControl instanceof FlyingMoveControl)){
            this.moveControl = new FlyingMoveControl(this, 20, true);
            this.navigation = new FlyingPathNavigation(this, this.getLevel());
            setFlying(true);
        } else if (!b && (moveControl instanceof FlyingMoveControl)){
            this.moveControl = new MoveControl(this);
            this.navigation = new GroundPathNavigation(this, this.getLevel());
            this.setNoGravity(false);
            setFlying(false);
        }
    }

    @Override
    public boolean causeFallDamage(float p_148750_, float p_148751_, DamageSource p_148752_) {
        return false;
    }

    public static boolean checkQuetzalSpawnRules(EntityType<QuetzalcoatlusEntity> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos p_218245_, RandomSource p_218246_) {
        return checkAnimalSpawnRules(p_218242_,p_218243_,p_218244_,p_218245_,p_218246_) && ModCommonConfigs.CAN_SPAWN_QUETZAL.get();
    }

    @Override
    public boolean hurt(DamageSource p_27567_, float p_27568_) {
        this.flyCount = 50;
        return super.hurt(p_27567_, p_27568_);
    }


    //anger

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

    //spawn and death

    protected float generateRandomMaxHealth(RandomSource p_218806_) {
        return 20.0F + (float)p_218806_.nextInt(8) + (float)p_218806_.nextInt(9);
    }

    //ride stuff

    @Override
    public void travel(Vec3 vec3) {
        if (this.isAlive()) {
             if (this.canBeControlledByRider()) {
                 this.setAggressive(false);
                 this.m_274367_(1.0F);
                 LivingEntity livingentity = (LivingEntity)this.getControllingPassenger();

                 this.setYRot(livingentity.getYRot());
                 this.yRotO = this.getYRot();
                 this.setXRot(livingentity.getXRot() * 0.5F);
                 this.setRot(this.getYRot(), this.getXRot());
                 this.yBodyRot = this.getYRot();

                 float f = livingentity.xxa * 0.5F;
                 float f1 = livingentity.zza;
                 this.setSpeed(0.1F);

                 if(!getFlying()){
                    super.travel(new Vec3((double) f, vec3.y, (double) f1));
                 }

                if (getFlying())
                {
                    float speed = (float) getAttributeValue(getFlying()? FLYING_SPEED : MOVEMENT_SPEED);
                    if (getControllingPassenger() instanceof Player p && p.getInventory().getArmor(3).is(ItemInit.FLYING_HELMET.get())){
                        speed = speed * 0.4f;
                    } else {
                        speed = speed * 0.2f;
                    }

                    moveDist = moveDist > 0? moveDist : 0;
                    float movY = (float) (-this.getControllingPassenger().getXRot() * (Math.PI / 180));
                    vec3 = new Vec3(f, movY, f1);
                    moveRelative(speed, vec3);
                    move(MoverType.SELF, getDeltaMovement());
                    if (getDeltaMovement().lengthSqr() < 0.1)
                        setDeltaMovement(getDeltaMovement().add(0, Math.sin(tickCount / 4f) * 0.03, 0));
                    setDeltaMovement(getDeltaMovement().scale(0.9f));
                }

            } else if (getFlying()) {
                if (this.isControlledByLocalInstance()) {
                     if (this.isInLava()) {
                        this.moveRelative(0.02F, vec3);
                        this.move(MoverType.SELF, this.getDeltaMovement());
                        this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
                    } else {
                        this.moveRelative(this.getSpeed(), vec3);
                        this.move(MoverType.SELF, this.getDeltaMovement());
                        this.setDeltaMovement(this.getDeltaMovement().scale((double) 0.91F));
                    }
                }
            } else {
                super.travel(vec3);
            }
        }
    }

    @Nullable
    public LivingEntity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : (LivingEntity) this.getPassengers().get(0);
    }

    @Override
    public boolean boost() {
        return false;
    }

    @Override
    public void onPlayerJump(int p_21696_) {

    }

    @Override
    public boolean canJump() {
        return true;
    }

    @Override
    public void handleStartJump(int p_21695_) {
        this.switchNavigation(!getFlying());
    }

    @Override
    public void handleStopJump() {

    }

    //sounds

    @Override
    public SoundEvent getAmbientSound() {
        if(getFlying()){
            return SoundInit.QUETZAL_FLY.get();
        }
        return SoundInit.QUETZAL_AMBIENT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundInit.QUETZAL_DEATH.get();
    }

    @org.jetbrains.annotations.Nullable
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

    protected AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);

    public static <T extends QuetzalcoatlusEntity & GeoEntity> AnimationController<T> flyController(T entity) {
        return new AnimationController<>(entity,"movement", 5, event ->{
            if(entity.isInSittingPose()){
                event.getController().setAnimation(RawAnimation.begin().then("sit", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }
            if (entity.interact > 0) {
                event.getController().setAnimation(RawAnimation.begin().then("interact", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }
            if(entity.getFlying()){
                event.getController().setAnimation(RawAnimation.begin().then("fly", Animation.LoopType.LOOP));
            } else if (event.isMoving()) {
                event.getController().setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
            } else {
                event.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
            }
            return PlayState.CONTINUE;
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(flyController(this));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }


    public class QuetzalRandomLookAroundGoal extends RandomLookAroundGoal {

        private final QuetzalcoatlusEntity quetzal;

        public QuetzalRandomLookAroundGoal(Mob p_25720_, QuetzalcoatlusEntity quetzal) {
            super(p_25720_);
            this.quetzal = quetzal;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && !quetzal.canBeControlledByRider();
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && !quetzal.canBeControlledByRider();
        }
    }
}
