package com.modderg.tameablebeasts.entities.custom;

import com.modderg.tameablebeasts.config.ModCommonConfigs;
import com.modderg.tameablebeasts.entities.RideableTameableGAnimal;
import com.modderg.tameablebeasts.entities.goals.TameablePanicGoal;
import com.modderg.tameablebeasts.item.ItemInit;
import com.modderg.tameablebeasts.sound.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class GrasshopperEntity extends RideableTameableGAnimal implements PlayerRideableJumping {

    protected float playerJumpPendingScale = 0f;
    boolean allowStandSliding = true;
    protected int jumpCount = this.random.nextInt(100, 200);
    protected boolean isJumping = false;

    @Override
    protected boolean isSaddle(ItemStack itemStack) {
        return itemStack.is(ItemInit.GRASSHOPPER_SADDLE.get());
    }

    public GrasshopperEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.JUMP_STRENGTH, 2.5f);
    }

    protected float generateRandomMaxHealth(RandomSource p_218806_) {
        return 15.0F + (float)p_218806_.nextInt(8) + (float)p_218806_.nextInt(9);
    }

    public static boolean checkGrasshopperSpawnRules(EntityType<GrasshopperEntity> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos p_218245_, RandomSource p_218246_) {
        return checkAnimalSpawnRules(p_218242_,p_218243_,p_218244_,p_218245_,p_218246_) && ModCommonConfigs.CAN_SPAWN_GRASSHOPPER.get();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, Ingredient.of(ItemInit.LEAF.get()), false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new TameablePanicGoal(this, 1.25D));
        this.goalSelector.addGoal(6, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new FollowParentGoalIfNotSitting(this, 1.0D));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (isTameFood(itemstack) && !this.isTame()) {
            tameGAnimal(player, itemstack, 30);
            return InteractionResult.CONSUME;
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public boolean isFood(ItemStack item) {
        return item.is(ItemInit.LEAF.get());
    }

    @Override
    public boolean isTameFood(ItemStack item) {
        return item.is(Items.OAK_LEAVES);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_146746_, DifficultyInstance p_146747_, MobSpawnType p_146748_, @org.jetbrains.annotations.Nullable SpawnGroupData p_146749_, @org.jetbrains.annotations.Nullable CompoundTag p_146750_) {
        updateAttributes();
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.generateRandomMaxHealth(p_146746_.getRandom()));
        this.setTexture(this.random.nextInt(3));
        return super.finalizeSpawn(p_146746_, p_146747_, p_146748_, p_146749_, p_146750_);
    }

    @Override
    public void tick() {
        if(jumpCount >= 0) jumpCount --;
        super.tick();
    }

    @Override
    public boolean causeFallDamage(float p_147187_, float p_147188_, DamageSource p_147189_) {
        return false;
    }

    //sounds

    @Override
    public SoundEvent getAmbientSound() {
        return SoundInit.GRASSHOPPER_AMBIENT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundInit.GRASSHOPPER_DEATH.get();
    }

    @org.jetbrains.annotations.Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {return SoundInit.GRASSHOPPER_HURT.get();}

    @Override
    protected void playStepSound(BlockPos p_20135_, BlockState p_20136_) {
        this.playSound(SoundInit.GRASSHOPPER_STEPS.get(), 0.15F, 1.0F);
    }

    @Override
    public SoundEvent getTameSound(){
        return SoundInit.GRASSHOPPER_INTERACT.get();
    }

    @Override
    public SoundEvent getInteractSound(){
        return SoundInit.GRASSHOPPER_INTERACT.get();
    }


    //ride stuff

    @Override
    public void travel(Vec3 vec333) {
        if (this.isAlive()) {
            if (!this.isTame()) {
                if (jumpCount == 0 && this.isOnGround()) {
                    jumpFromGround();
                    this.playSound(SoundInit.GRASSHOPPER_JUMP.get(), 0.15F, 1.0F);
                    jumpCount = this.random.nextInt(20, 150);
                }
            }
            if (this.canBeControlledByRider()) {
                LivingEntity livingentity = (LivingEntity) this.getControllingPassenger();

                this.setYRot(livingentity.getYRot());
                this.yRotO = this.getYRot();
                this.setXRot(livingentity.getXRot() * 0.5F);
                this.setRot(this.getYRot(), this.getXRot());
                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.yBodyRot;
                float f = livingentity.xxa * 0.25f;
                float f1 = livingentity.zza/2f;
                if (f1 <= 0.0F) {
                    f1 *= 0.25F;
                }

                if (this.isOnGround() && this.playerJumpPendingScale == 0.0F && !this.allowStandSliding) {
                    f = 0.0F;
                    f1 = 0.0F;
                }

                if (this.playerJumpPendingScale > 0.0F && !this.isJumping() && this.isOnGround()) {
                    double d0 = this.getCustomJump() * (double) this.playerJumpPendingScale * (double) this.getBlockJumpFactor();
                    double d1 = d0 + this.getJumpPower();
                    Vec3 vec3 = this.getDeltaMovement();
                    this.setDeltaMovement(vec3.x, d1/2, vec3.z);
                    this.setIsJumping(true);
                    this.hasImpulse = true;
                    net.minecraftforge.common.ForgeHooks.onLivingJump(this);
                    if (f1 > 0.0F) {
                        float f2 = Mth.sin(this.getYRot() * ((float) Math.PI / 180F));
                        float f3 = Mth.cos(this.getYRot() * ((float) Math.PI / 180F));
                        this.setDeltaMovement(this.getDeltaMovement().add((-0.4F * f2 * this.playerJumpPendingScale), 0.0D, (double) (0.4F * f3 * this.playerJumpPendingScale)));
                    }

                    this.playerJumpPendingScale = 0.0F;
                }

                if (this.isControlledByLocalInstance()) {
                    this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    super.travel(new Vec3((double) f, vec333.y, (double) f1));
                } else if (livingentity instanceof Player) {
                    this.setDeltaMovement(Vec3.ZERO);
                }

                if (this.isOnGround()) {
                    this.playerJumpPendingScale = 0.0F;
                    this.setIsJumping(false);
                }

                this.tryCheckInsideBlocks();
            } else {
                super.travel(vec333);
            }
        }
    }

    public boolean isJumping(){
        return isJumping;
    }

    public void setIsJumping(boolean b){isJumping = b;
    }

    public double getCustomJump() {
        return this.getAttributeValue(Attributes.JUMP_STRENGTH);
    }

    @Override
    protected float getJumpPower() {
        if(jumpCount == 0){
            jumpCount = this.random.nextInt(100, 200);
            return 0.84F * this.getBlockJumpFactor();
        }
        return super.getJumpPower();
    }

    protected void updateAttributes(){
        if (this.isBaby()) {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.15D);
        } else {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        }
    }

    @Override
    public void onPlayerJump(int p_21696_) {
        if (this.getSaddle()) {
            if (p_21696_ < 0) {
                p_21696_ = 0;
            }else {
                this.allowStandSliding = true;
            }

            if (p_21696_ >= 90) {
                this.playerJumpPendingScale = 1.0F;
            } else {
                this.playerJumpPendingScale = 0.4F + 0.4F * (float)p_21696_ / 90.0F;
            }
        }
    }

    @Override
    public boolean canJump() {
        return this.getSaddle();
    }

    @Override
    public void handleStartJump(int p_21695_) {
        this.playSound(SoundInit.GRASSHOPPER_JUMP.get(), 0.15F, 1.0F);
    }

    @Override
    public void handleStopJump() {

    }


    //animation stuff

    protected AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);

    public static <T extends GrasshopperEntity & GeoEntity> AnimationController<T> flyController(T entity) {
        return new AnimationController<>(entity,"movement", 2, event ->{
            if(entity.isInSittingPose()){
                event.getController().setAnimation(RawAnimation.begin().then("sit", Animation.LoopType.LOOP));
            } else {
                if(!entity.isOnGround() && entity.getDeltaMovement().y > 0){
                    event.getController().setAnimation(RawAnimation.begin().then("jump", Animation.LoopType.LOOP));
                } else {
                    if(event.isMoving() && entity.isOnGround()){
                        event.getController().setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
                    } else {
                        event.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
                    }
                }
            }

            return PlayState.CONTINUE;
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(flyController(this));
        super.registerControllers(data);
    }
}