package com.modderg.tameablebeasts.entities;

import com.modderg.tameablebeasts.core.FlyingTameableGAnimal;
import com.modderg.tameablebeasts.core.goals.SwitchingFollowOwnerGoal;
import com.modderg.tameablebeasts.core.goals.SwitchingMeleeAttackGoal;
import com.modderg.tameablebeasts.init.SoundInit;
import com.modderg.tameablebeasts.init.ItemInit;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.Objects;

public class ScarecrowAllayEntity extends FlyingTameableGAnimal implements GeoEntity, FlyingAnimal {
    protected int interact = 0;

    private static final EntityDataAccessor<Integer> TEXTUREID = SynchedEntityData.defineId(ScarecrowAllayEntity.class, EntityDataSerializers.INT);
    public void setTexture(int i){
        this.getEntityData().set(TEXTUREID, i);
    }
    public int getTextureID(){
        return this.getEntityData().get(TEXTUREID);
    }

    private static final EntityDataAccessor<Boolean> HOE = SynchedEntityData.defineId(ScarecrowAllayEntity.class, EntityDataSerializers.BOOLEAN);
    public void setHoe(boolean i){
        this.getEntityData().set(HOE, i);
    }
    public boolean getHoe(){
        return this.getEntityData().get(HOE);
    }

    public ScarecrowAllayEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        updateAttributes();
    }

    public static AttributeSupplier.Builder setCustomAttributes() {

        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.FLYING_SPEED, (double)0.2F)
                .add(Attributes.MOVEMENT_SPEED, (double)0.25F)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(1, new SwitchingMeleeAttackGoal(this, 2D, true));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.goalSelector.addGoal(3, new SwitchingFollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    @Override
    protected Boolean shouldFly() {
        return !isInSittingPose();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TEXTUREID, this.random.nextInt(3));
        this.entityData.define(HOE, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("TEXTUREID")) {
            this.setTexture(compound.getInt("TEXTUREID"));
            updateAttributes();
        }
        if (compound.contains("HOE")) {
            this.setHoe(compound.getBoolean("HOE"));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("TEXTUREID", this.getTextureID());
        compound.putBoolean("HOE", this.getHoe());
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if(Objects.equals(this.getOwnerUUID(), player.getUUID())){
            if(player.isShiftKeyDown()){
                this.setInSittingPose(!this.isOrderedToSit());
                this.setOrderedToSit(!this.isOrderedToSit());
                return InteractionResult.CONSUME;
            }
        } else {
            this.setOwnerUUID(player.getUUID());
            this.setTame(true);
            this.setTarget((LivingEntity) null);
            this.getLevel().broadcastEntityEvent(this, (byte) 7);
        }
        if(itemstack.is(ItemInit.IRON_BIG_HOE.get()) && !this.getHoe()){
            this.setHoe(true);
            updateAttributes();
            itemstack.shrink(1);
        }
        if(interact <= 0){
            this.playSound(SoundInit.SCARECROW_INTERACT.get(), 0.15F, 1.0F);
            if(player.getItemInHand(hand).is(Items.SHEARS)){
                this.playSound(SoundEvents.SHEEP_SHEAR, 0.15F, 1.0F);
                if(getTextureID() < 2){
                    setTexture(getTextureID() + 1);
                } else {
                    setTexture(0);
                }
            }
            interact = 35;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void tick() {
        if(interact >= 0){
            interact --;
        }
        super.tick();
    }

    private void updateAttributes(){
        if (this.getHoe()) {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(10.D);
        } else {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3.0D);
        }
    }


    @Override
    public boolean causeFallDamage(float p_147187_, float p_147188_, DamageSource p_147189_) {
        return false;
    }

    //sounds

    @Override
    public SoundEvent getAmbientSound() {
        if(isMoving()){
            return SoundInit.SCARECROW_AMBIENT.get();
        }
        return SoundInit.SCARECROW_FLY.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundInit.SCARECROW_DEATH.get();
    }

    @org.jetbrains.annotations.Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {return SoundInit.SCARECROW_HURT.get();}

    //animation stuff

    protected AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);

    public static <T extends ScarecrowAllayEntity & GeoEntity> AnimationController<T> flyController(T entity) {
        return new AnimationController<>(entity,"movement", 5, event ->{
            if(entity.isInSittingPose()){
                event.getController().setAnimation(RawAnimation.begin().then("sit", Animation.LoopType.LOOP));
            } else {
                if (entity.interact <= 0) {
                    if (entity.isMoving()) {
                        event.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
                    } else {
                        event.getController().setAnimation(RawAnimation.begin().then("fly", Animation.LoopType.LOOP));
                    }
                } else {
                    event.getController().setAnimation(RawAnimation.begin().then("interact", Animation.LoopType.LOOP));
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

    @Override
    public boolean isFlying() {
        return false;
    }
}

