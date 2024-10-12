package com.modderg.tameablebeasts.server.entity;

import com.modderg.tameablebeasts.server.entity.navigation.TBGroundPathNavigation;
import com.modderg.tameablebeasts.server.item.block.EggBlockItem;
import com.modderg.tameablebeasts.server.packet.InitPackets;
import com.modderg.tameablebeasts.server.packet.StoCSynchGoalName;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.ArrayList;
import java.util.List;

public class TBAnimal extends TamableAnimal implements GeoEntity {

    private static final EntityDataAccessor<Integer> TEXTURE_ID = SynchedEntityData.defineId(TBAnimal.class, EntityDataSerializers.INT);
    public void setTextureId(int i){
        this.getEntityData().set(TEXTURE_ID, i);
    }
    public int getTextureID(){
        return this.getEntityData().get(TEXTURE_ID);
    }

    private static final EntityDataAccessor<Boolean> WANDERING = SynchedEntityData.defineId(TBAnimal.class, EntityDataSerializers.BOOLEAN);
    public void setWandering(boolean i){
        this.getEntityData().set(WANDERING, i);
    }
    public boolean isWandering(){
        return this.getEntityData().get(WANDERING);
    }

    protected static final EntityDataAccessor<Boolean> GOALS_WANT_RUNNING = SynchedEntityData.defineId(TBAnimal.class, EntityDataSerializers.BOOLEAN);
    public void setRunning(boolean i){
        this.getEntityData().set(GOALS_WANT_RUNNING, i);
        updateAttributes();
    }

    public boolean isRunning(){
        return this.getEntityData().get(GOALS_WANT_RUNNING);
    }

    protected List<String> attackAnims = new ArrayList<>();

    protected TBAnimal(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        updateAttributes();
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("TEXTURE_ID", this.getTextureID());
        compound.putBoolean("WANDERING", this.isWandering());
        compound.putBoolean("GOALS_WANT_RUNNING", this.isWandering());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TEXTURE_ID, 0);
        this.entityData.define(WANDERING, false);
        this.entityData.define(GOALS_WANT_RUNNING, false);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        
        if (compound.contains("TEXTURE_ID"))
            this.setTextureId(compound.getInt("TEXTURE_ID"));
        
        if (compound.contains("WANDERING"))
            this.setWandering(compound.getBoolean("WANDERING"));

        if (compound.contains("GOALS_WANT_RUNNING"))
            this.setWandering(compound.getBoolean("GOALS_WANT_RUNNING"));
    }

    @Override
    public boolean canAttack(@NotNull LivingEntity target) {
        return !(target instanceof TBAnimal tg && (this.getOwner() instanceof Player p && tg.isOwnedBy(p))) && super.canAttack(target);
    }

    protected int textureIdSize = 0;
    protected int healthFloor = 0;

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor levelAccessor, @NotNull DifficultyInstance p_146747_, @NotNull MobSpawnType p_146748_, @Nullable SpawnGroupData p_146749_, @Nullable CompoundTag p_146750_) {

        if(textureIdSize > 0)
            this.setTextureId(this.random.nextInt(textureIdSize));
        this.updateAttributes();

        if(healthFloor > 0){
            float health = generateRandomMaxHealth(healthFloor);
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(health);
            this.setHealth(health);
        }

        return super.finalizeSpawn(levelAccessor, p_146747_, p_146748_, p_146749_, p_146750_);
    }

    @Override
    public void aiStep() {
        if (!this.level().isClientSide && this.isAlive() && this.tickCount % 240 == 0)
            this.heal(1.0F);

//        goalSelector.getRunningGoals().findFirst().ifPresent(
//                goal -> this.setCustomName(Component.literal(goal.getGoal().toString()))
//        );


        super.aiStep();
    }

    protected float generateRandomMaxHealth(int floor) {
        RandomSource random = this.getRandom();
        return floor + random.nextInt(8) + random.nextInt(9);
    }

    public void updateAttributes(){}

    protected void addGoals(Goal... goals){
        int i = 0;
        for (Goal goal: goals){
            i++;
            this.goalSelector.addGoal(i, goal);
        }
    }

    protected void addTargetGoals(Goal... goals){
        int i = 0;
        for (Goal goal: goals){
            i++;
            this.targetSelector.addGoal(i, goal);
        }
    }

    //SITTING STUFF

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {

        if (this.isOwnedBy(player) && player.isShiftKeyDown()) {
            if(!isInSittingPose() && !isWandering()){
                this.setWandering(true);
                this.messageState("wandering", player);
            } else {
                this.setWandering(false);
                this.messageState(this.isInSittingPose() ? "following":"sitting", player);
                this.setInSittingPose(!this.isOrderedToSit());
                this.setOrderedToSit(!this.isOrderedToSit());
            }
            return InteractionResult.SUCCESS;
        }

        if(isFood(player.getItemInHand(hand)))
            this.heal(5f);

        if(!this.isInSittingPose() && !(this instanceof FlyingTBAnimal flyAnimal && flyAnimal.isFlying()))
            triggerAnim("movement", "interact");

        this.playSound(this.getInteractSound(), 0.45F, 1.0F);

        return  super.mobInteract(player, hand);
    }

    @Override public boolean isOrderedToSit() {
        return !this.isVehicle() && super.isOrderedToSit();
    }

    @Override public boolean isInSittingPose() {
        return !this.isVehicle() && super.isInSittingPose();
    }

    @Override
    public void setInSittingPose(boolean p_21838_) {
        playStepSound(this.getOnPos(), this.level().getBlockState(this.getOnPos()));
        super.setInSittingPose(p_21838_);
    }

    //TAMING STUFF

    public boolean isTameFood(ItemStack itemStack) {
        return false;
    }

    public void tameGAnimal(Player player, ItemStack itemStack, int chance){
        if (!player.getAbilities().instabuild && itemStack!= null) itemStack.shrink(1);
        if (this.random.nextInt(100) < chance && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
            this.setOwnerUUID(player.getUUID());
            this.setTame(true);
            this.level().broadcastEntityEvent(this, (byte) 7);
            this.playSound(getTameSound(), 0.15F, 1.0F);
        } else
            this.level().broadcastEntityEvent(this, (byte) 6);
    }

    //BREEDING STUFF

    public EggBlockItem getEgg(){
        return null;
    }

    public boolean breedDropsEgg(){
        return this.getEgg() != null && this.isTame();
    }

    public int genChildTextId(){
        if(this.random.nextInt(100) <= 30)
            return this.random.nextInt(textureIdSize);
        return this.getTextureID();
    }

    @Override
    public void spawnChildFromBreeding(@NotNull ServerLevel level, @NotNull Animal mob) {
        if(!breedDropsEgg()) {
            super.spawnChildFromBreeding(level, mob);
            return;
        }

        spawnAtLocation(getEgg());
        super.finalizeSpawnChildFromBreeding(level, mob, null);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel p_146743_, @NotNull AgeableMob p_146744_) {
        if(!this.isTame()){
            TBAnimal animal = (TBAnimal) this.getType().create(this.level());
            animal.setTextureId(this.genChildTextId());
            return animal;
        }
        return null;
    }

    //RIDING STUFF

    @Override
    public LivingEntity getControllingPassenger() {
        if(!this.getPassengers().isEmpty() && this.getPassengers().get(0) instanceof LivingEntity passenger && this.isOwnedBy(passenger))
            return passenger;
        return null;
    }

    //NAVIGATION STUFF

    @Override
    public @NotNull PathNavigation createNavigation(@NotNull Level p_21480_) {
        return new TBGroundPathNavigation(this, this.level());
    }

    public void setMoveControl(MoveControl control){this.moveControl = control;}
    public void setPathNavigation(PathNavigation navigation){this.navigation = navigation;}

    //SOUND STUFF

    public SoundEvent getTameSound(){
        return null;
    }
    public SoundEvent getInteractSound(){
        return null;
    }

    //CLIENT EFFECTS

    public void messageState(String txt, Player player){
        if (player instanceof LocalPlayer)
            Minecraft.getInstance().gui.setOverlayMessage(Component.literal(txt), false);
    }

    public void spawnItemParticles(ItemStack p_21061_, int p_21062_, Entity animal) {
        for(int i = 0; i < p_21062_; ++i) {
            Vec3 vec3 = new Vec3(((double)random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
            vec3 = vec3.xRot(-animal.getXRot() * ((float)Math.PI / 180F));
            vec3 = vec3.yRot(-animal.getYRot() * ((float)Math.PI / 180F));
            double d0 = (double)(-random.nextFloat()) * 0.6D - 0.3D;
            Vec3 vec31 = new Vec3(((double)random.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
            vec31 = vec31.xRot(-animal.getXRot() * ((float)Math.PI / 180F));
            vec31 = vec31.yRot(-animal.getYRot() * ((float)Math.PI / 180F));
            vec31 = vec31.add(animal.getX(), animal.getEyeY(), animal.getZ());
            if (animal.level() instanceof ServerLevel)
                ((ServerLevel) animal.level()).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, p_21061_), vec31.x, vec31.y, vec31.z, 1, vec3.x, vec3.y + 0.05D, vec3.z, 0.0D);
            else
                animal.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, p_21061_), vec31.x, vec31.y, vec31.z, vec3.x, vec3.y + 0.05D, vec3.z);
        }
    }

    //ANIMATION STUFF

    protected AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);

    public <T extends TBAnimal & GeoEntity> AnimationController<T> groundController(T entity) {
        return new AnimationController<>(entity,"movement", 5, event -> groundState(entity, event));
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity p_21372_) {
        if(!attackAnims.isEmpty())
            playAttackAnim();
        return super.doHurtTarget(p_21372_);
    }

    public void playAttackAnim(){
        triggerAnim("movement", "attack");
    }

    public AnimationController<?> addAnimationTriggers(AnimationController<?> controller){
        for (String attack: attackAnims)
            controller.triggerableAnim(attack, RawAnimation.begin().then(attack, Animation.LoopType.PLAY_ONCE));
        controller.triggerableAnim("interact", RawAnimation.begin().then("interact", Animation.LoopType.PLAY_ONCE));
        return controller;
    }

    @Override public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {}

    @Override public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    public <T extends TBAnimal & GeoEntity> PlayState groundState(T entity, software.bernie.geckolib.core.animation.AnimationState<T> event) {

        if(event.isMoving()||entity.onClimbable())
            event.getController().setAnimation(RawAnimation.begin().then(entity.isRunning() ? "run" : "walk", Animation.LoopType.LOOP));
        else
        if(entity.isInSittingPose())
            event.setAnimation(RawAnimation.begin().then("sit", Animation.LoopType.LOOP));
        else
            event.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));

        return PlayState.CONTINUE;
    }
}
