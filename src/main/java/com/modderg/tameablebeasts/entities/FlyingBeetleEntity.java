package com.modderg.tameablebeasts.entities;

import com.modderg.tameablebeasts.config.ModCommonConfigs;
import com.modderg.tameablebeasts.core.TameableGAnimal;
import com.modderg.tameablebeasts.core.goals.SwitchingFollowOwnerGoal;
import com.modderg.tameablebeasts.core.goals.SwitchingMeleeAttackGoal;
import com.modderg.tameablebeasts.core.goals.TameablePanicGoal;
import com.modderg.tameablebeasts.init.ModEntityClass;
import com.modderg.tameablebeasts.init.SoundInit;
import com.modderg.tameablebeasts.particles.TameableParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.UUID;

public class FlyingBeetleEntity extends TameableGAnimal implements GeoEntity {

    protected int interact = 0;

    private static final EntityDataAccessor<Integer> TEXTUREID = SynchedEntityData.defineId(FlyingBeetleEntity.class, EntityDataSerializers.INT);
    public void setTexture(int i){
        this.getEntityData().set(TEXTUREID, i);
    }
    public int getTextureID(){
        return this.getEntityData().get(TEXTUREID);
    }

    private static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(FlyingBeetleEntity.class, EntityDataSerializers.BOOLEAN);
    public void setFlying(boolean i){
        this.getEntityData().set(FLYING, i);
    }
    public boolean getFlying(){
        return this.getEntityData().get(FLYING);
    }

    public FlyingBeetleEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.switchNavigation(getFlying());
        updateAttributes();
    }
    public boolean isTameFood(ItemStack itemStack) {
        return itemStack.is(Items.HONEYCOMB);
    }

    @Override
    public boolean isFood(ItemStack p_27600_) {
        return p_27600_.is(Items.HONEY_BOTTLE);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.FLYING_SPEED, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(1, new SwitchingMeleeAttackGoal(this, 2D, true));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.goalSelector.addGoal(3, new SwitchingFollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(4, new TameablePanicGoal(this, 2.0D));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.1D, Ingredient.of(Items.HONEY_BOTTLE), false));
        this.goalSelector.addGoal(6, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new AvoidEntityGoal<>(this,Player.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new FollowParentGoalIfNotSitting(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TEXTUREID, this.random.nextInt(3));
        this.entityData.define(FLYING, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("TEXTUREID")) {
            this.setTexture(compound.getInt("TEXTUREID"));
        }
        if (compound.contains("FLYING")) {
            this.setFlying(compound.getBoolean("FLYING"));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("TEXTUREID", this.getTextureID());
        compound.putBoolean("FLYING", this.getFlying());
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if(this.isTameFood(itemstack) && !this.isTame()){
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            if (this.random.nextInt(20) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                this.setOwnerUUID(player.getUUID());
                this.setTame(true);
                this.setTarget((LivingEntity) null);
                this.getLevel().broadcastEntityEvent(this, (byte) 7);
            } else {
                this.getLevel().broadcastEntityEvent(this, (byte) 6);
            }
        }
        if(this.m_269323_() != null && this.m_269323_().isShiftKeyDown()){
            this.setOrderedToSit(!this.isOrderedToSit());
            this.setAggressive(false);
            this.setTarget(null);
            switchNavigation(false);
            return InteractionResult.CONSUME;
        }
        if(interact <= 0 && this.isOnGround()){
            this.playSound(SoundInit.BEETLE_INTERACT.get(), 0.15F, 1.0F);
            interact = 45;
        }
        return super.mobInteract(player, hand);
    }

    public Boolean shouldFly(){
        float followRange;
        if(this.isTame()){
            followRange = 8f;
            if(this.isBaby()){
                return false;
            }
            if(this.isInSittingPose()){
                return false;
            }
            if(this.isAggressive()){
                return true;
            }
            if(this.m_269323_() != null){
                if(this.isAggressive()){
                    return true;
                }
                if(this.distanceTo(this.m_269323_()) > followRange){
                    return true;
                }
                if(getFlying() && !this.m_269323_().isOnGround()){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void tick() {
        this.switchNavigation(shouldFly());
        if(interact >= 0){
            interact --;
        }
        super.tick();
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
        return !shouldFly();
    }

    @Override
    public void travel(Vec3 p_218382_) {
        if(shouldFly()){
            this.getLevel().addParticle(TameableParticles.SHINE_PARTICLES.get(), this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D),
                    this.random.nextGaussian() * 0.02D, this.random.nextGaussian() * 0.02D, this.random.nextGaussian() * 0.02D);
            if ( this.isControlledByLocalInstance()) {
                if (this.isInWater()) {
                    this.moveRelative(0.02F, p_218382_);
                    this.move(MoverType.SELF, this.getDeltaMovement());
                    this.setDeltaMovement(this.getDeltaMovement().scale((double) 0.8F));
                } else if (this.isInLava()) {
                    this.moveRelative(0.02F, p_218382_);
                    this.move(MoverType.SELF, this.getDeltaMovement());
                    this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
                } else {
                    this.moveRelative(this.getSpeed(), p_218382_);
                    this.move(MoverType.SELF, this.getDeltaMovement());
                    this.setDeltaMovement(this.getDeltaMovement().scale((double) 0.91F));
                }
            }
        } else {
            updateAttributes();
            super.travel(p_218382_);
        }
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        FlyingBeetleEntity beetle = ModEntityClass.TAMEABLE_BEETLE.get().create(p_146743_);
        UUID uuid = this.getOwnerUUID();
        if (uuid != null) {
            beetle.setOwnerUUID(uuid);
            beetle.setTame(true);
        }

        return beetle;
    }

    private void updateAttributes(){
        if (isBaby()) {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.1D);
        } else {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        }
    }

    public static boolean checkFlyingBeetleSpawnRules(EntityType<FlyingBeetleEntity> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos p_218245_, RandomSource p_218246_) {
        return checkAnimalSpawnRules(p_218242_,p_218243_,p_218244_,p_218245_,p_218246_) && ModCommonConfigs.CAN_SPAWN_FLYING_BEETLE.get();
    }

    //sounds

    @Override
    public SoundEvent getAmbientSound() {
        if(getFlying()){
            return SoundInit.BEETLE_FLY.get();
        }
        return SoundInit.BEETLE_AMBIENT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundInit.BEETLE_DEATH.get();
    }

    @org.jetbrains.annotations.Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {return SoundInit.BEETLE_HURT.get();}

    @Override
    protected void playStepSound(BlockPos p_20135_, BlockState p_20136_) {
        this.playSound(SoundInit.BEETLE_STEPS.get(), 0.15F, 1.0F);
    }


    //animation stuff
    protected AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);
    public static <T extends FlyingBeetleEntity & GeoEntity> AnimationController<T> flyController(T entity) {
        return new AnimationController<>(entity,"movement", 5, event ->{
            if(entity.isInSittingPose()){
                event.getController().setAnimation(RawAnimation.begin().then("sit", Animation.LoopType.LOOP));
            } else {
                if (entity.interact <= 0){
                    if(entity.shouldFly()){
                        event.getController().setAnimation(RawAnimation.begin().then("fly", Animation.LoopType.LOOP));
                    } else if(event.isMoving()){
                        event.getController().setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
                    } else {
                        event.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
                    }
                } else {
                    if(!entity.isBaby()){
                        entity.getLevel().addParticle(TameableParticles.SHINE_PARTICLES.get(), entity.getRandomX(1.0D), entity.getRandomY() + 0.5D, entity.getRandomZ(1.0D),
                                entity.random.nextGaussian() * 0.02D, entity.random.nextGaussian() * 0.02D, entity.random.nextGaussian() * 0.02D);
                    }
                    event.getController().setAnimation(RawAnimation.begin().then("interact", Animation.LoopType.PLAY_ONCE));
                }
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

}
