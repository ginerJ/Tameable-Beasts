package com.modderg.tameablebeasts.entities;

import com.modderg.tameablebeasts.block.entity.EggBlockEntity;
import com.modderg.tameablebeasts.item.block.EggBlockItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
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
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
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

public class TameableGAnimal extends TamableAnimal implements GeoEntity {

    private static final EntityDataAccessor<Integer> TEXTUREID = SynchedEntityData.defineId(TameableGAnimal.class, EntityDataSerializers.INT);
    public void setTextureId(int i){
        this.getEntityData().set(TEXTUREID, i);
    }
    public int getTextureID(){
        return this.getEntityData().get(TEXTUREID);
    }

    private static final EntityDataAccessor<Boolean> WANDERING = SynchedEntityData.defineId(TameableGAnimal.class, EntityDataSerializers.BOOLEAN);
    public void setWandering(boolean i){
        this.getEntityData().set(WANDERING, i);
    }
    public boolean isWandering(){
        return this.getEntityData().get(WANDERING);
    }

    protected static final EntityDataAccessor<Boolean> GOALSWANTSRUNNING = SynchedEntityData.defineId(TameableGAnimal.class, EntityDataSerializers.BOOLEAN);
    public void setRunning(boolean i){
        this.getEntityData().set(GOALSWANTSRUNNING, i);
        updateAttributes();
    }
    public boolean isRunning(){
        return this.getEntityData().get(GOALSWANTSRUNNING);
    }

    protected TameableGAnimal(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        updateAttributes();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("TEXTUREID", this.getTextureID());
        compound.putBoolean("WANDERING", this.isWandering());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TEXTUREID, 0);
        this.entityData.define(WANDERING, false);
        this.entityData.define(GOALSWANTSRUNNING, false);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("TEXTUREID")) {
            this.setTextureId(compound.getInt("TEXTUREID"));
            updateAttributes();
        }
        if (compound.contains("WANDERING")) {
            this.setWandering(compound.getBoolean("WANDERING"));
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {

        if (this.isOwnedBy(player) && player.isShiftKeyDown()) {
            if(!isInSittingPose() && !isWandering()){
                this.setWandering(true);
                this.messageState("wandering");
            } else {
                this.setWandering(false);
                this.setInSittingPose(!this.isOrderedToSit());
                this.setOrderedToSit(!this.isOrderedToSit());
            }
            return InteractionResult.CONSUME;
        }

        if(isFood(player.getItemInHand(hand))){
            this.heal(5f);
        }

        if(!this.isInSittingPose() && !(this instanceof FlyingTameableGAnimal flyAnimal && flyAnimal.isFlying()))
            triggerAnim("InteractionController", "interact");

        return  super.mobInteract(player, hand);
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        return !(target instanceof TameableGAnimal tg && (this.getOwner() instanceof Player p && tg.isOwnedBy(p))) && super.canAttack(target);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    @Override
    public void spawnChildFromBreeding(ServerLevel level, Animal mob) {
        if(getEgg()!= null){
            ItemStack itemstack = new ItemStack(getEgg());
            getEgg().setTextureId(itemstack, this.genChildTextId());
            ItemEntity itemEntity = new ItemEntity(level, this.getX(), this.getY(), this.getZ(), itemstack);
            level.addFreshEntity(itemEntity);
            super.finalizeSpawnChildFromBreeding(level, mob, null);
        }
    }

    public EggBlockItem getEgg(){
        return null;
    }

    public int genChildTextId(){
        int poll = this.random.nextInt(101);

        if(poll <= 10){
            return this.random.nextInt(textureIdSize-1);
        } else if (20 >= poll && specialTxtIdSize != 0){
            return this.random.nextInt(textureIdSize-1, specialTxtIdSize);
        }

        return this.getTextureID();
    }

    @Override
    public void setInSittingPose(boolean p_21838_) {
        this.messageState(this.isInSittingPose() ? "following":"sitting");
        super.setInSittingPose(p_21838_);
    }

    protected int textureIdSize = 0;
    protected int specialTxtIdSize = 0;

    protected int randomHealthFloor = 0;

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelAccessor, DifficultyInstance p_146747_, MobSpawnType p_146748_, @Nullable SpawnGroupData p_146749_, @Nullable CompoundTag p_146750_) {
        this.setTextureId(this.random.nextInt(textureIdSize));
        if(randomHealthFloor != 0){
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.generateRandomMaxHealth(levelAccessor.getRandom()));
            this.setHealth(this.getMaxHealth());
        }
        this.updateAttributes();
        return super.finalizeSpawn(levelAccessor, p_146747_, p_146748_, p_146749_, p_146750_);
    }

    protected float generateRandomMaxHealth(RandomSource p_218806_) {
        return randomHealthFloor + (float)p_218806_.nextInt(8) + (float)p_218806_.nextInt(9);
    }

    public boolean isTameFood(ItemStack itemStack) {
        return false;
    }

    public void updateAttributes(){}

    public SoundEvent getTameSound(){
        return null;
    }

    public SoundEvent getInteractSound(){
        return null;
    }

    public void tameGAnimal(Player player, ItemStack itemStack, int chance){
        if (!player.getAbilities().instabuild && itemStack!= null) itemStack.shrink(1);
        if (this.random.nextInt(chance) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
            this.playSound(getTameSound(), 0.15F, 1.0F);
            this.setOwnerUUID(player.getUUID());
            this.setTame(true);
            this.level().broadcastEntityEvent(this, (byte) 7);
        } else {
            this.level().broadcastEntityEvent(this, (byte) 6);
        }
    }

    public void messageState(String txt){
        if (this.getOwner() != null && this.getOwner().level().isClientSide && this.getOwner().level() instanceof ClientLevel) {
            Minecraft.getInstance().gui.setOverlayMessage(Component.literal(txt), false);
        }
    }

    public boolean canBeControlledByRider() {
        if (!(this.getControllingPassenger() instanceof Player)) {
            return false;
        }
        else return this.isOwnedBy(this.getControllingPassenger());
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
            if (animal.level() instanceof ServerLevel) //Forge: Fix MC-2518 spawnParticle is nooped on server, need to use server specific variant
                ((ServerLevel) animal.level()).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, p_21061_), vec31.x, vec31.y, vec31.z, 1, vec3.x, vec3.y + 0.05D, vec3.z, 0.0D);
            else
                animal.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, p_21061_), vec31.x, vec31.y, vec31.z, vec3.x, vec3.y + 0.05D, vec3.z);
        }
    }

    //ANIMATION STUFF

    protected AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);

    public static <T extends TameableGAnimal & GeoEntity> AnimationController<T> groundController(T entity) {
        return new AnimationController<>(entity,"movement", 5, event ->{
            if(entity.isInSittingPose()){
                event.getController().setAnimation(RawAnimation.begin().then("sit", Animation.LoopType.LOOP));
            } else {
                if(event.isMoving()){
                    event.getController().setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
                } else {
                    event.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
                }
            }
            return PlayState.CONTINUE;
        });
    }

    public String getAttackAnim(){
        return null;
    }

    @Override
    public boolean doHurtTarget(Entity p_21372_) {
        if(getAttackAnim() != null)
            triggerAnim("AttackController", "attack");
        return super.doHurtTarget(p_21372_);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar control) {
        control.add(new AnimationController<>(this, "InteractionController", state -> PlayState.STOP)
                .triggerableAnim("interact", RawAnimation.begin().then("interact", Animation.LoopType.PLAY_ONCE)));
        control.add(new AnimationController<>(this, "AttackController", state -> PlayState.STOP)
                .triggerableAnim("attack", RawAnimation.begin().then(getAttackAnim(), Animation.LoopType.PLAY_ONCE)));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    public void refreshAttackController() {
        AnimatableManager<TameableGAnimal> animatableManager = this.getAnimatableInstanceCache().getManagerForId(this.getId());
        animatableManager.removeController("AttackController");
        animatableManager.addController(new AnimationController<>(this, "AttackController", state -> PlayState.STOP)
                .triggerableAnim("attack", RawAnimation.begin().then(getAttackAnim(), Animation.LoopType.PLAY_ONCE)));
    }

    public static class FollowParentGoalIfNotSitting extends FollowParentGoal{
        private final TameableGAnimal animal;

        public FollowParentGoalIfNotSitting(TameableGAnimal p_25319_, double p_25320_) {
            super(p_25319_, p_25320_);
            this.animal = p_25319_;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && !animal.isOrderedToSit();
        }

        @Override
        public boolean canContinueToUse(){
            return super.canContinueToUse() && !animal.isOrderedToSit();
        }
    }
}
