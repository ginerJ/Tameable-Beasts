package com.modderg.tameablebeasts.entities;

import com.modderg.tameablebeasts.config.ModCommonConfigs;
import com.modderg.tameablebeasts.core.TameableGAnimal;
import com.modderg.tameablebeasts.init.ModEntityClass;
import com.modderg.tameablebeasts.init.SoundInit;
import com.modderg.tameablebeasts.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
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

public class GiantTameableRolyPolyEntity extends TameableGAnimal implements GeoEntity, ItemSteerable {

    protected int interact = 0;

    protected boolean running = false;
    private int walkTimer = random.nextInt(10, 1000);

    private static final EntityDataAccessor<Boolean> SADDLE = SynchedEntityData.defineId(GiantTameableRolyPolyEntity.class, EntityDataSerializers.BOOLEAN);
    public void setSaddle(boolean i){
        this.getEntityData().set(SADDLE, i);
    }
    public boolean getSaddle(){
        return this.getEntityData().get(SADDLE);
    }

    private static final EntityDataAccessor<Integer> TEXTUREID = SynchedEntityData.defineId(GiantTameableRolyPolyEntity.class, EntityDataSerializers.INT);
    public void setTexture(int i){
        this.getEntityData().set(TEXTUREID, i);
    }
    public int getTextureID(){
        return this.getEntityData().get(TEXTUREID);
    }

    public GiantTameableRolyPolyEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
    }

    @Override
    public boolean isFood(ItemStack p_27600_) {
        return p_27600_.is(ItemInit.LEAF.get());
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, Ingredient.of(Items.OAK_LEAVES), false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(6, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new FollowParentGoalIfNotSitting(this, 1.0D));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TEXTUREID, this.random.nextInt(4));
        this.entityData.define(SADDLE, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("TEXTUREID")) {
            this.setTexture(compound.getInt("TEXTUREID"));
        }
        if (compound.contains("SADDLE")) {
            this.setSaddle(compound.getBoolean("SADDLE"));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("TEXTUREID", this.getTextureID());
        compound.putBoolean("SADDLE", this.getSaddle());
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if(this.isBaby()){
            if(Objects.equals(this.getOwnerUUID(), player.getUUID()) && player.isShiftKeyDown()){
                this.setOrderedToSit(!this.isOrderedToSit());
                return InteractionResult.CONSUME;
            } else if(interact <= 0){
                interact = 48;
            }
        } else if (this.isTame() && !this.isFood(itemstack)){
            if(Objects.equals(this.getOwnerUUID(), player.getUUID())){
                if (player.isShiftKeyDown()) {
                    this.setOrderedToSit(!this.isOrderedToSit());
                    return InteractionResult.CONSUME;
                } else if (this.getSaddle() && !this.isInSittingPose()){
                    player.startRiding(this);
                    return InteractionResult.sidedSuccess(this.getLevel().isClientSide);
                } else if (itemstack.is(ItemInit.ROLYPOLY_SADDLE.get()) && !this.getSaddle()) {
                    setSaddle(true);
                    this.playSound(SoundEvents.HORSE_SADDLE, 0.15F, 1.0F);
                    itemstack.shrink(1);
                    return InteractionResult.SUCCESS;
                }
            }
        } else {
            if (itemstack.is(Items.OAK_LEAVES)) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                if (this.random.nextInt(10) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                    this.setOwnerUUID(player.getUUID());
                    this.setTame(true);
                    this.getLevel().broadcastEntityEvent(this, (byte) 7);
                } else {
                    this.getLevel().broadcastEntityEvent(this, (byte) 6);
                }
                return InteractionResult.SUCCESS;
            }
        }
        if(interact <= 0 && !this.isInSittingPose()){
            this.playSound(SoundInit.ROLYPOLY_INTERACT.get(), 0.15F, 1.0F);
            interact = 48;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void tick() {
        if(interact >= 0){
            interact --;
        }
        if(!this.isTame()){
            if(walkTimer > 0){
                walkTimer --;
            } else if (walkTimer == 0){
                walkTimer = random.nextInt(10, 1000);
                running = !running;
                updateAttributes();
            }
        }
        super.tick();
    }

    @Override
    public @org.jetbrains.annotations.Nullable AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        GiantTameableRolyPolyEntity giantRoly = ModEntityClass.GIANT_ROLY_POLY.get().create(p_146743_);
        UUID uuid = this.getOwnerUUID();
        if (uuid != null) {
            giantRoly.setOwnerUUID(uuid);
            giantRoly.setTame(true);
        }

        return giantRoly;
    }

    private void updateAttributes(){
        if (running) {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        } else {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2D);
        }
    }

    public static boolean checkRolyPolySpawnRules(EntityType<GiantTameableRolyPolyEntity> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos p_218245_, RandomSource p_218246_) {
        return checkAnimalSpawnRules(p_218242_,p_218243_,p_218244_,p_218245_,p_218246_) && ModCommonConfigs.CAN_SPAWN_ROLY_POLY.get();
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

    @org.jetbrains.annotations.Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {return SoundInit.ROLYPOLY_HURT.get();}

    @Override
    protected void playStepSound(BlockPos p_20135_, BlockState p_20136_) {
        if(hasControllingPassenger()){
            this.playSound(SoundInit.ROLYPOLY_ROLL.get(), 0.15F, 1.0F);
        } else{
            this.playSound(SoundInit.ROLYPOLY_STEPS.get(), 0.15F, 1.0F);
        }
    }

    //ride stuff

    @Override
    public void travel(Vec3 p_213352_1_) {
        if (this.isAlive()) {
            if (this.canBeControlledByRider()) {
                this.setAggressive(false);
                this.maxUpStep = 1.0F;
                LivingEntity livingentity = (LivingEntity)this.getControllingPassenger();

                this.setYRot(livingentity.getYRot());
                this.yRotO = this.getYRot();
                this.setXRot(livingentity.getXRot() * 0.5F);
                this.setRot(this.getYRot(), this.getXRot());
                this.yBodyRot = this.getYRot();

                float f = livingentity.xxa * 0.5F;
                float f1 = livingentity.zza;
                this.setSpeed((float) this.getAttribute(Attributes.MOVEMENT_SPEED).getValue() + 0.2f);

                updateAttributes();
                running = true;

                super.travel(new Vec3((double) f, p_213352_1_.y, (double) f1));
            } else {
                if(this.isTame() && this.m_269323_() != null){
                    if(distanceTo(Objects.requireNonNull(this.m_269323_())) > 8f){ //m_269323_ is getOwner
                        this.running = true;
                        updateAttributes();
                    } else {
                        this.running = false;
                        updateAttributes();
                    }
                }
                super.travel(p_213352_1_);
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


    //animation stuff

    protected AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);

    public static <T extends GiantTameableRolyPolyEntity & GeoEntity> AnimationController<T> flyController(T entity) {
        return new AnimationController<>(entity,"movement", 10, event ->{
            if(entity.isInSittingPose() || entity.hurtTime > 0) {
                event.getController().setAnimation(RawAnimation.begin().then("sit", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }
            if (entity.interact > 0) {
                event.getController().setAnimation(RawAnimation.begin().then("interact", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }

            if (!entity.canBeControlledByRider()) {
                event.getController().transitionLength(10);
                if (event.isMoving()) {
                    if (entity.getAttribute(Attributes.MOVEMENT_SPEED).getValue() >= 0.3D) {
                        event.getController().setAnimation(RawAnimation.begin().then("run", Animation.LoopType.LOOP));
                    } else {
                        event.getController().setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
                    }
                } else {
                    event.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
                }
            } else {
                if (event.isMoving()) {
                    event.getController().transitionLength(1);
                    event.getController().setAnimation(RawAnimation.begin().then("run", Animation.LoopType.LOOP));
                } else {
                    event.getController().transitionLength(6);
                    event.getController().setAnimation(RawAnimation.begin().then("sit", Animation.LoopType.LOOP));
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
