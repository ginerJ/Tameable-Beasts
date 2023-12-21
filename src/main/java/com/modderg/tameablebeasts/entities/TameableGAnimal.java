package com.modderg.tameablebeasts.entities;

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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
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
    public void setTexture(int i){
        this.getEntityData().set(TEXTUREID, i);
    }
    public int getTextureID(){
        return this.getEntityData().get(TEXTUREID);
    }

    protected TameableGAnimal(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("TEXTUREID", this.getTextureID());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TEXTUREID, 0);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("TEXTUREID")) {
            this.setTexture(compound.getInt("TEXTUREID"));
            updateAttributes();
        }
    }

    public boolean isTameFood(ItemStack itemStack) {
        return false;
    }

    protected void updateAttributes(){}

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {

        if (this.isOwnedBy(player) && player.isShiftKeyDown()) {
            this.setInSittingPose(!this.isOrderedToSit());
            this.setOrderedToSit(!this.isOrderedToSit());
            return InteractionResult.CONSUME;
        }

        if(isFood(player.getItemInHand(hand))){
            this.heal(5f);
        }

        if(!this.isInSittingPose())
            triggerAnim("InteractionController", "interact");

        return  super.mobInteract(player, hand);
    }

    @Override
    public void setTarget(@Nullable LivingEntity entity) {
        if (entity instanceof TameableGAnimal animal && animal.m_269323_() != null && animal.isOwnedBy(this.m_269323_())){return;}
        super.setTarget(entity);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    @Override
    public void setInSittingPose(boolean p_21838_) {
        this.messageState(this.isInSittingPose() ? "following":"sitting");
        super.setInSittingPose(p_21838_);
    }

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
            this.getLevel().broadcastEntityEvent(this, (byte) 7);
        } else {
            this.getLevel().broadcastEntityEvent(this, (byte) 6);
        }
    }

    public void messageState(String txt){
        if (this.m_269323_() != null && this.m_269323_().getLevel().isClientSide && this.m_269323_().getLevel() instanceof ClientLevel) {
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
            if (animal.getLevel() instanceof ServerLevel) //Forge: Fix MC-2518 spawnParticle is nooped on server, need to use server specific variant
                ((ServerLevel) animal.getLevel()).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, p_21061_), vec31.x, vec31.y, vec31.z, 1, vec3.x, vec3.y + 0.05D, vec3.z, 0.0D);
            else
                animal.getLevel().addParticle(new ItemParticleOption(ParticleTypes.ITEM, p_21061_), vec31.x, vec31.y, vec31.z, vec3.x, vec3.y + 0.05D, vec3.z);
        }
    }

    //ANIMATION STUFF

    protected AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "InteractionController", state -> PlayState.STOP)
                .triggerableAnim("interact", RawAnimation.begin().then("interact", Animation.LoopType.PLAY_ONCE)));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    public class FollowParentGoalIfNotSitting extends FollowParentGoal{
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
