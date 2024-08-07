package com.modderg.tameablebeasts.entities.custom;

import com.modderg.tameablebeasts.block.BlockInit;
import com.modderg.tameablebeasts.config.ModCommonConfigs;
import com.modderg.tameablebeasts.entities.RideableTameableGAnimal;
import com.modderg.tameablebeasts.entities.goals.GFollowOwnerGoal;
import com.modderg.tameablebeasts.entities.goals.InitPOITypes;
import com.modderg.tameablebeasts.entities.goals.TakeCareOfEggsGoal;
import com.modderg.tameablebeasts.entities.goals.TameablePanicGoal;
import com.modderg.tameablebeasts.item.ItemInit;
import com.modderg.tameablebeasts.item.block.EggBlockItem;
import com.modderg.tameablebeasts.sound.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
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
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
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
import software.bernie.geckolib.core.animation.AnimatableManager;

public class CrestedGeckoEntity extends RideableTameableGAnimal {

    @Override
    protected Item itemSaddle() {
        return ItemInit.CRESTED_GECKO_SADDLE.get();
    }

    public CrestedGeckoEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.textureIdSize = 8;
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new WallClimberNavigation(this, level);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new GFollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.1D, Ingredient.of(Items.MELON), false));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        //this.goalSelector.addGoal(3, new TakeCareOfEggsGoal(this, 15, InitPOITypes.CRESTED_GECKO_POI));
        this.goalSelector.addGoal(4, new TameablePanicGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new TameablePanicGoal(this, 1.25D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.0D, 10));
    }

    public static boolean checkCrestedGeckoSpawnRules(EntityType<CrestedGeckoEntity> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos p_218245_, RandomSource p_218246_) {
        return checkAnimalSpawnRules(p_218242_,p_218243_,p_218244_,p_218245_,p_218246_) && ModCommonConfigs.CAN_SPAWN_CRESTED_GECKO.get();
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (isTameFood(itemstack) && !this.isTame()) {
            tameGAnimal(player, itemstack, 3);
            return InteractionResult.CONSUME;
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(Items.MELON);
    }

    @Override
    public boolean isTameFood(ItemStack itemStack) {
        return itemStack.is(ItemInit.FLYING_BEETLE_EGG_ITEM.get())||
                itemStack.is(ItemInit.GROUND_BEETLE_EGG_ITEM.get())||
                itemStack.is(ItemInit.GRASSHOPPER_EGG_ITEM.get());
    }

    @Override
    public EggBlockItem getEgg() {
        return (EggBlockItem) ItemInit.CRESTED_GECKO_EGG_ITEM.get();
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            this.setClimbing(this.horizontalCollision);
        }
    }

    @Override
    public boolean causeFallDamage(float p_147187_, float p_147188_, DamageSource p_147189_) {
        if(!this.isTame()) return super.causeFallDamage(p_147187_, p_147188_, p_147189_);
        return false;
    }

    @Override
    public boolean onClimbable() {
        return this.isClimbing();
    }

    public boolean isClimbing() {
        return (this.entityData.get(DATA_FLAGS_ID) & 128) != 0 ||
                (this.isControlledByLocalInstance() && this.horizontalCollision);
    }

    public void setClimbing(boolean climbing) {
        if (!this.isInSittingPose()) {
            byte b0 = this.entityData.get(DATA_FLAGS_ID);
            if (climbing) {
                b0 |= (byte) 128;
            } else {
                b0 &= (byte) ~128;
            }

            this.entityData.set(DATA_FLAGS_ID, b0);
        }
    }

    @Override
    public EntityDimensions getDimensions(Pose p_19975_) {
        if(isClimbing())
            return new EntityDimensions(0.9f,1.6f,true);
        return this.getType().getDimensions();
    }

    @Override
    protected void positionRider(Entity rider, MoveFunction moveFunction) {
        if (this.hasPassenger(rider)) {
            if(this.isClimbing())
                positionWithOffSets(rider, moveFunction, -0.5f, 0,0);
            else
                positionWithOffSets(rider, moveFunction, 0.5f, 0,0);
        }
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
    public Vec3 handleRelativeFrictionAndCalculateMovement(Vec3 p_21075_, float p_21076_) {
        this.moveRelative(this.getFrictionInfluencedSpeed(p_21076_), p_21075_);
        this.setDeltaMovement(this.handleOnClimbable(this.getDeltaMovement()));
        this.move(MoverType.SELF, this.getDeltaMovement());
        Vec3 vec3 = this.getDeltaMovement();
        if ((this.horizontalCollision || this.jumping) && (this.onClimbable() || this.getFeetBlockState().is(Blocks.POWDER_SNOW) && PowderSnowBlock.canEntityWalkOnPowderSnow(this))) {
            vec3 = new Vec3(vec3.x, 0.2D, vec3.z);
        }

        return vec3;
    }

    private float getFrictionInfluencedSpeed(float p_21331_) {
        return this.onGround() ? this.getSpeed() * (0.21600002F / (p_21331_ * p_21331_ * p_21331_)) : this.getFlyingSpeed();
    }

    private Vec3 handleOnClimbable(Vec3 p_21298_) {
        if (this.onClimbable()) {
            this.resetFallDistance();
            float f = 0.45F;
            double d0 = Mth.clamp(p_21298_.x, -f, f);
            double d1 = Mth.clamp(p_21298_.z, -f, f);
            double d2 = Math.max(p_21298_.y, -f);
            if (d2 < 0.0D && !this.getFeetBlockState().isScaffolding(this) && this.isSuppressingSlidingDownLadder()) {
                d2 = 0.0D;
            }

            p_21298_ = new Vec3(d0, d2, d1);
        }

        return p_21298_;
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
    protected SoundEvent getHurtSound(DamageSource p_21239_) {return SoundInit.CRESTED_GECKO_HURT.get();}

    @Override
    protected void playStepSound(BlockPos p_20135_, BlockState p_20136_) {
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
        control.add(groundController(this));
        super.registerControllers(control);
    }
}
