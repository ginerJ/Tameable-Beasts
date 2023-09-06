package com.modderg.tameablebeasts.entities;

import com.modderg.tameablebeasts.core.TameableGAnimal;
import com.modderg.tameablebeasts.item.ItemInit;
import com.modderg.tameablebeasts.init.ModEntityClass;
import com.modderg.tameablebeasts.init.SoundInit;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
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

import java.util.Objects;
import java.util.UUID;

public class TameablePenguinEntity extends TameableGAnimal implements GeoEntity {
    private static final EntityDataAccessor<Integer> TEXTUREID = SynchedEntityData.defineId(TameableRacoonEntity.class, EntityDataSerializers.INT);
    public void setTexture(int i){
        this.getEntityData().set(TEXTUREID, i);
    }
    public int getTextureID(){
        return this.getEntityData().get(TEXTUREID);
    }

    private static final EntityDataAccessor<Boolean> SWORD = SynchedEntityData.defineId(TameableRacoonEntity.class, EntityDataSerializers.BOOLEAN);
    public void setSword(boolean i){
        this.getEntityData().set(SWORD, i);
    }
    public boolean getSword(){
        return this.getEntityData().get(SWORD);
    }

    private static final EntityDataAccessor<Boolean> HELMET = SynchedEntityData.defineId(TameableRacoonEntity.class, EntityDataSerializers.BOOLEAN);
    public void setHelmet(boolean i){
        this.getEntityData().set(HELMET, i);
    }
    public boolean getHelmet(){
        return this.getEntityData().get(HELMET);
    }

    protected int interact = 0;
    public TameablePenguinEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(Items.TROPICAL_FISH);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.goalSelector.addGoal(2, new FloatGoal(this));
        this.goalSelector.addGoal(2, new LeapAtTargetGoal(this, 0.4f));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, Ingredient.of(Items.TROPICAL_FISH), false));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(7, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(9, new FollowParentGoalIfNotSitting(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(11, new RandomLookAroundGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TEXTUREID, this.random.nextInt(2));
        this.entityData.define(SWORD, false);
        this.entityData.define(HELMET, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("TEXTURE")) {
            this.setTexture(compound.getInt("TEXTURE"));
        }
        if (compound.contains("SWORD")) {
            this.setSword(compound.getBoolean("SWORD"));
        }
        if (compound.contains("HELMET")) {
            this.setHelmet(compound.getBoolean("HELMET"));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("TEXTURE", this.getTextureID());
        compound.putBoolean("SWORD", this.getSword());
        compound.putBoolean("HELMET", this.getHelmet());
    }

    public static AttributeSupplier.Builder setCustomAttributes() {

        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.125D)
                .add(Attributes.ATTACK_DAMAGE, 2.5D)
                .add(Attributes.ARMOR, 2.0D);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        updateAttributes();
        if (this.isBaby()){
            if(player.isShiftKeyDown()){
                this.setOrderedToSit(!this.isOrderedToSit());
                return InteractionResult.CONSUME;
            } else {
                interact = 20;
            }
        } else {
            if (Objects.equals(this.getOwnerUUID(), player.getUUID())){
                if(player.isShiftKeyDown()){
                    this.setOrderedToSit(!this.isOrderedToSit());
                    return InteractionResult.CONSUME;
                } else if (itemstack.is(ItemInit.ICEPOP.get()) && !getSword()){
                    this.setSword(true);
                    itemstack.shrink(1);
                } else if (itemstack.is(ItemInit.ICE_HELMET.get()) && !getHelmet()) {
                    this.setHelmet(true);
                    itemstack.shrink(1);
                } else if (interact <= 0 && !isFood(itemstack)){
                    interact = 20;
                }
            } else {
                if (itemstack.is(Items.SALMON) && !this.isTame() || itemstack.is(Items.COD)) {
                    if (!player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }
                    if (this.random.nextInt(4) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                        this.playSound(SoundInit.PENGUIN_HAPPY.get(), 0.15F, 1.0F);
                        this.setOwnerUUID(player.getUUID());
                        this.setTame(true);
                        this.setTarget((LivingEntity) null);
                        this.getLevel().broadcastEntityEvent(this, (byte) 7);
                    } else {
                        this.getLevel().broadcastEntityEvent(this, (byte) 6);
                    }
                    return InteractionResult.SUCCESS;
                } else if (interact <= 0 && !isFood(itemstack) && !this.isInSittingPose()){
                    this.playSound(SoundInit.PENGUIN_INTERACT.get(), 0.15F, 1.0F);
                    interact = 20;
                }
            }
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


    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        TameablePenguinEntity penguin = ModEntityClass.TAMEABLE_PENGUIN.get().create(p_146743_);
        UUID uuid = this.getOwnerUUID();
        if (uuid != null) {
            penguin.setOwnerUUID(uuid);
            penguin.setTame(true);
        }

        return penguin;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_146746_, DifficultyInstance p_146747_, MobSpawnType p_146748_, @Nullable SpawnGroupData p_146749_, @Nullable CompoundTag p_146750_) {
        updateAttributes();
        return super.finalizeSpawn(p_146746_, p_146747_, p_146748_, p_146749_, p_146750_);
    }

    private void updateAttributes(){
        if (this.isTame()) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25D);
            this.setHealth(20.0F);
            if(this.getSword()){
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(15.0D);
            } else {
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(6.0D);
            }
            if(this.getHelmet()){
                this.getAttribute(Attributes.ARMOR).setBaseValue(12.0D);
            } else {
                this.getAttribute(Attributes.ARMOR).setBaseValue(2.0D);
            }
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(2.5D);
            this.getAttribute(Attributes.ARMOR).setBaseValue(2.0D);
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.125D);
        }
    }

    public static boolean checkPenguinSpawnRules(EntityType<TameablePenguinEntity> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos p_218245_, RandomSource p_218246_) {
        if(p_218243_.getBlockState(p_218245_.below()).is(Blocks.PACKED_ICE) ||
                p_218243_.getBlockState(p_218245_.below()).is(Blocks.ICE) ||
                p_218243_.getBlockState(p_218245_.below()).is(Blocks.BLUE_ICE)){
            return true;
        }
        return false;
    }

    //sounds

    @Override
    public SoundEvent getAmbientSound() {
        return SoundInit.PENGUIN_AMBIENT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundInit.PENGUIN_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {return SoundInit.PENGUIN_HURT.get();}

    @Override
    protected void playStepSound(BlockPos p_20135_, BlockState p_20136_) {
        this.playSound(SoundInit.PENGUIN_STEPS.get(), 0.15F, 1.0F);
    }

    //animation stuff
    protected AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);
    public static <T extends TameablePenguinEntity & GeoEntity> AnimationController<T> flyController(T entity) {
        return new AnimationController<>(entity,"movement", 5, event ->{
            if(entity.isInSittingPose()){
                event.getController().setAnimation(RawAnimation.begin().then("sit", Animation.LoopType.LOOP));
            } else {
                if (entity.interact <= 0){
                    if(event.isMoving()){
                        event.getController().setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
                    } else {
                        event.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
                    }
                } else {
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
