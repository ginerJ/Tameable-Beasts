package com.modderg.tameablebeasts.entities.custom;

import com.modderg.tameablebeasts.config.ModCommonConfigs;
import com.modderg.tameablebeasts.entities.goals.TameablePanicGoal;
import com.modderg.tameablebeasts.entities.TameableGAnimal;
import com.modderg.tameablebeasts.entities.goals.StealPolenGoal;
import com.modderg.tameablebeasts.entities.EntityIinit;
import com.modderg.tameablebeasts.item.ItemInit;
import com.modderg.tameablebeasts.sound.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
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

public class RacoonEntity extends TameableGAnimal implements GeoEntity {

    private static final EntityDataAccessor<Integer> TEXTUREID = SynchedEntityData.defineId(RacoonEntity.class, EntityDataSerializers.INT);
    public void setTexture(int i){
        this.getEntityData().set(TEXTUREID, i);
    }
    public int getTextureID(){
        return this.getEntityData().get(TEXTUREID);
    }

    private static final EntityDataAccessor<Boolean> HASPOLEN = SynchedEntityData.defineId(RacoonEntity.class, EntityDataSerializers.BOOLEAN);
    public void setPolen(boolean i){
        this.getEntityData().set(HASPOLEN, i);
    }
    public boolean hasPolen(){return this.getEntityData().get(HASPOLEN);}

    public RacoonEntity(EntityType<? extends TameableGAnimal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(Items.EGG);
    }

    public boolean isTameFood(ItemStack itemStack) {
        return itemStack.is(Items.MELON_SLICE);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.goalSelector.addGoal(2, new FloatGoal(this));
        this.goalSelector.addGoal(2, new LeapAtTargetGoal(this, 0.4f));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, Ingredient.of(Items.EGG), false));
        this.goalSelector.addGoal(3, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(5, new StealPolenGoal(this, 1.1D));
        this.goalSelector.addGoal(6, new TameablePanicGoal(this, 1.25D));
        this.goalSelector.addGoal(7, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(8, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(9, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new FollowParentGoalIfNotSitting(this, 1.0D));
        this.goalSelector.addGoal(11, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(12, new RandomLookAroundGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TEXTUREID, 0);
        this.entityData.define(HASPOLEN, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("TEXTUREID")) {
            this.setTexture(compound.getInt("TEXTUREID"));
        }
        if (compound.contains("HASPOLEN")) {
            this.setPolen(compound.getBoolean("HASPOLEN"));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("TEXTUREID", this.getTextureID());
        compound.putBoolean("HASPOLEN", this.hasPolen());
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_146746_, DifficultyInstance p_146747_, MobSpawnType p_146748_, @Nullable SpawnGroupData p_146749_, @Nullable CompoundTag p_146750_) {
        this.setTexture(this.random.nextInt(3));
        return super.finalizeSpawn(p_146746_, p_146747_, p_146748_, p_146749_, p_146750_);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (this.isTameFood(itemstack) && !this.isTame()) {
            this.updateAttributes();
            tameGAnimal(player, itemstack, 3);
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    private int dropFurTime = getRandom().nextInt(300,1500);

    @Override
    public void tick() {
        if(this.hasPolen() && dropFurTime-- <= 0) {
            dropFurTime = getRandom().nextInt(300,1500);
            if(this.hasPolen()){
                ItemEntity item = new ItemEntity(this.getLevel(),this.getX(),this.getY(),this.getX(),new ItemStack(ItemInit.FUR.get()));
                item.setPos(this.getPosition(1.0F));
                getLevel().addFreshEntity(item);
                this.setPolen(false);
            }
        }
        super.tick();
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        RacoonEntity racoon = EntityIinit.TAMEABLE_RACOON.get().create(p_146743_);
        racoon.setTexture(this.getTextureID());
        UUID uuid = this.getOwnerUUID();
        if (uuid != null) {
            racoon.setOwnerUUID(uuid);
            racoon.setTame(true);
        }
        return racoon;
    }

    protected void updateAttributes(){
        if (this.isTame()) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(15.0D);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
        }
        if(hasPolen()) this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2D);
        else this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
    }

    //sounds

    @Override
    public SoundEvent getAmbientSound() {
        return SoundInit.RACOON_AMBIENT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundInit.RACOON_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {return SoundInit.RACOON_HURT.get();}

    @Override
    protected void playStepSound(BlockPos p_20135_, BlockState p_20136_) {
        this.playSound(SoundInit.RACOON_STEPS.get(), 0.15F, 1.0F);
    }

    @Override
    public SoundEvent getTameSound(){
        return SoundInit.RACOON_INTERACT.get();
    }

    @Override
    public SoundEvent getInteractSound(){return SoundInit.RACOON_HAPPY.get();}


    public static boolean checkRacoonSpawnRules(EntityType<RacoonEntity> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos p_218245_, RandomSource p_218246_) {
        return checkAnimalSpawnRules(p_218242_,p_218243_,p_218244_,p_218245_,p_218246_) && ModCommonConfigs.CAN_SPAWN_RACOON.get();
    }

    //animation stuff

    protected AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);
    public static <T extends RacoonEntity & GeoEntity> AnimationController<T> groundController(T entity) {
        return new AnimationController<>(entity,"movement", 5, event ->{
            if(entity.isInSittingPose()){
                event.getController().setAnimation(RawAnimation.begin().then("sit", Animation.LoopType.LOOP));
            } else {
                if(event.isMoving()){
                    event.getController().setAnimation(RawAnimation.begin().then("scurry", Animation.LoopType.LOOP));
                } else {
                    event.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
                }
            }
            return PlayState.CONTINUE;
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(groundController(this));
        super.registerControllers(data);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }
}
