package com.modderg.tameablebeasts.server.entity;

import com.modderg.tameablebeasts.client.gui.TBItemStackHandler;
import com.modderg.tameablebeasts.client.gui.TBMenu;
import com.modderg.tameablebeasts.client.gui.TBMenuJustSaddle;
import com.modderg.tameablebeasts.server.ModCommonConfigs;
import com.modderg.tameablebeasts.server.entity.abstracts.RideableTBAnimal;

import com.modderg.tameablebeasts.server.entity.goals.*;
import com.modderg.tameablebeasts.server.entity.navigation.TBWallClimberNavigation;
import com.modderg.tameablebeasts.registry.TBItemRegistry;
import com.modderg.tameablebeasts.server.item.block.EggBlockItem;
import com.modderg.tameablebeasts.client.sound.SoundInit;
import com.modderg.tameablebeasts.server.tags.TBTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animation.AnimatableManager;

public class CrestedGeckoEntity extends RideableTBAnimal {

    public CrestedGeckoEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.textureIdSize = 8;

        this.inventory = new TBItemStackHandler(this, 1);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3D);
    }

    @Override
    public void updateAttributes(){
        double maxHealth = 25D;
        if (this.isTame())
            maxHealth = 35D;

        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(maxHealth);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        addGoals(
                new SitWhenOrderedToGoal(this),
                new TBFollowOwnerGoal(this, 1.0D, 10.0F, 6.0F),
                new FloatGoal(this),
                new TemptGoal(this, 1.1D, Ingredient.of(TBTags.Items.CRESTED_GECKO_FOOD), false),
                new TameablePanicGoal(this, 1.5D),
                new BreedGoal(this, 1.0D),
                new WaterAvoidingRandomStrollGoal(this, 1.0D),
                new TBFollowParentGoal(this, 1.0D),
                new LookAtPlayerGoal(this, Player.class, 6.0F),
                new RandomLookAroundGoal(this)
        );
    }

    //actual speed 0.21
    @Override
    public float getRidingSpeedMultiplier() {
        return 0.7F;
    }

    @Override
    @NotNull
    public PathNavigation createNavigation(@NotNull Level level) {
        return new TBWallClimberNavigation(this, level);
    }

    public static boolean checkCrestedGeckoSpawnRules(EntityType<CrestedGeckoEntity> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos p_218245_, RandomSource p_218246_) {
        return checkAnimalSpawnRules(p_218242_,p_218243_,p_218244_,p_218245_,p_218246_) && ModCommonConfigs.CAN_SPAWN_CRESTED_GECKO.get();
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (isTameFood(itemstack) && !this.isTame()) {
            tameGAnimal(player, itemstack, 20);
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(TBTags.Items.CRESTED_GECKO_FOOD);
    }

    @Override
    public boolean isTameFood(ItemStack itemStack) {
        return itemStack.is(TBTags.Items.CRESTED_GECKO_TAME_FOOD);
    }

    @Override
    public EggBlockItem getEgg() {
        return (EggBlockItem) TBItemRegistry.CRESTED_GECKO_EGG_ITEM.get();
    }

    @Override
    public boolean causeFallDamage(float p_147187_, float p_147188_, @NotNull DamageSource p_147189_) {
        if(!this.isTame()) return super.causeFallDamage(p_147187_, p_147188_, p_147189_);
        return false;
    }

    //CLIMB STUFF

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide)
            this.setClimbing(this.horizontalCollision);
    }

    @Override
    public boolean onClimbable() {
        return !this.isOrderedToSit() && (this.entityData.get(DATA_FLAGS_ID) & 128) != 0 ||
                (this.isControlledByLocalInstance() && this.horizontalCollision);
    }

    public void setClimbing(boolean climbing) {
        byte b0 = this.entityData.get(DATA_FLAGS_ID);
        if (climbing)
            b0 |= (byte) 128;
         else
            b0 &= (byte) ~128;

        this.entityData.set(DATA_FLAGS_ID, b0);
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose p_19975_) {
        if(onClimbable())
            return new EntityDimensions(0.9f,1.6f,true);
        return this.getType().getDimensions();
    }

    @Override
    protected void positionRider(@NotNull Entity rider, @NotNull MoveFunction moveFunction) {
        if (this.hasPassenger(rider))
            if(this.onClimbable())
                positionWithOffSets(rider, moveFunction, -0.5f, 0,0);
            else
                positionWithOffSets(rider, moveFunction, 0.5f, 0,0);
    }

    public void positionWithOffSets(Entity rider, MoveFunction moveF, float xOffset, float zOffset, float yOffset){
        double offsetX = Math.sin((this.getYRot()) * (Math.PI / 180F)) * xOffset;
        double offsetZ = Math.cos((this.getYRot()) * (Math.PI / 180F)) * -xOffset;

        double offsetX2 = Math.cos((this.getYRot()) * (Math.PI / 180F)) * zOffset;
        double offsetZ2 = Math.sin((this.getYRot()) * (Math.PI / 180F)) * zOffset;

        double d0 = this.getY() + this.getPassengersRidingOffset() + rider.getMyRidingOffset();
        moveF.accept(rider,
                this.getX() + offsetX + offsetX2,
                d0 + yOffset,
                this.getZ() + offsetZ + offsetZ2);
    }

    @Override
    public @NotNull Vec3 handleRelativeFrictionAndCalculateMovement(@NotNull Vec3 p_21075_, float p_21076_) {
        this.moveRelative(this.getFrictionInfluencedSpeed(p_21076_), p_21075_);
        this.setDeltaMovement(this.handleOnClimbable(this.getDeltaMovement()));
        this.move(MoverType.SELF, this.getDeltaMovement());
        Vec3 vec3 = this.getDeltaMovement();
        if ((this.horizontalCollision || this.jumping) && (this.onClimbable() || this.getFeetBlockState().is(Blocks.POWDER_SNOW) && PowderSnowBlock.canEntityWalkOnPowderSnow(this)))
            vec3 = new Vec3(vec3.x, 0.2D, vec3.z);
        return vec3;
    }

    protected float getFrictionInfluencedSpeed(float p_21331_) {
        return this.onGround() ? this.getSpeed() * (0.21600002F / (p_21331_ * p_21331_ * p_21331_)) : this.getFlyingSpeed();
    }

    protected Vec3 handleOnClimbable(Vec3 p_21298_) {
        if (this.onClimbable()) {
            this.resetFallDistance();
            float f = 0.45F;
            double d0 = Mth.clamp(p_21298_.x, -f, f);
            double d1 = Mth.clamp(p_21298_.z, -f, f);
            double d2 = Math.max(p_21298_.y, -f);
            if (d2 < 0.0D && !this.getFeetBlockState().isScaffolding(this) && this.isSuppressingSlidingDownLadder())
                d2 = 0.0D;

            p_21298_ = new Vec3(d0, d2, d1);
        }

        return p_21298_;
    }

    @Override
    protected TBMenu createMenu(int containerId, Inventory playerInventory) {
        return new TBMenuJustSaddle(containerId, playerInventory, this);
    }

    @Override
    public boolean hasSaddle() {
        return this.inventory.getStackInSlot(0).is(Items.SADDLE);
    }

    //sound stuff

    @Override
    public SoundEvent getAmbientSound() {
        return SoundInit.CRESTED_GECKO_AMBIENT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundInit.CRESTED_GECKO_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource p_21239_) {return SoundInit.CRESTED_GECKO_HURT.get();}

    @Override
    protected void playStepSound(@NotNull BlockPos p_20135_, @NotNull BlockState p_20136_) {
        this.playSound(SoundInit.CHIKOTE_STEPS.get(), 0.15F, 1.0F);
    }

    @Override
    public SoundEvent getTameSound(){
        return SoundInit.CRESTED_GECKO_INTERACT.get();
    }

    @Override
    public SoundEvent getInteractSound(){
        return SoundInit.CRESTED_GECKO_INTERACT.get();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar control) {
        control.add(addAnimationTriggers(groundController(this)));
    }
}
