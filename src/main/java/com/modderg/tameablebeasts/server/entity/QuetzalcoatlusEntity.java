package com.modderg.tameablebeasts.server.entity;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.client.entity.CustomJumpMeter;
import com.modderg.tameablebeasts.client.gui.TBItemStackHandler;
import com.modderg.tameablebeasts.client.gui.TBMenu;
import com.modderg.tameablebeasts.client.gui.TBMenuQuetzal;
import com.modderg.tameablebeasts.server.ModCommonConfigs;
import com.modderg.tameablebeasts.registry.TBPOITypesRegistry;
import com.modderg.tameablebeasts.server.entity.abstracts.FlyingRideableTBAnimal;

import com.modderg.tameablebeasts.server.entity.goals.*;
import com.modderg.tameablebeasts.registry.TBItemRegistry;
import com.modderg.tameablebeasts.server.item.block.EggBlockItem;
import com.modderg.tameablebeasts.registry.TBSoundRegistry;
import com.modderg.tameablebeasts.registry.TBTagRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.ClientUtils;

import static com.modderg.tameablebeasts.client.entity.TBAnimControllers.flyGliderController;
import static com.modderg.tameablebeasts.server.ModCommonConfigs.QUETZAL_SPAWN_HEIGHT;

public class QuetzalcoatlusEntity extends FlyingRideableTBAnimal implements CustomJumpMeter {

    public QuetzalcoatlusEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.hasWarmthVariants();

        this.consumeStaminaModule = 14;
        this.recoverStaminaModule = 8;
        this.downMovementAngle = 8F;

        this.inventory = new TBItemStackHandler(this, 18);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FLYING_SPEED, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.FOLLOW_RANGE, 48.0D)
                .add(Attributes.JUMP_STRENGTH);
    }

    @Override
    public void updateAttributes(){

        double maxHealth = 40D;
        if (this.isTame())
            maxHealth = 70D;

        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(maxHealth);
    }

    public static boolean checkQuetzalSpawnRules(EntityType<QuetzalcoatlusEntity> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos pos, RandomSource p_218246_) {
        return checkAnimalSpawnRules(p_218242_,p_218243_,p_218244_,pos,p_218246_) && ModCommonConfigs.CAN_SPAWN_QUETZAL.get() && pos.getY() > QUETZAL_SPAWN_HEIGHT.get();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        addGoals(
                new SitWhenOrderedToGoal(this),
                new TakeCareOfEggsGoal(this, 15, TBPOITypesRegistry.QUETZAL_POI),
                new TameablePanicGoal(this, 1.25D),
                new NoFlyRandomStrollGoal(this, 1.0D),
                new TemptGoal(this, 1.0D, Ingredient.of(TBTagRegistry.Items.QUETZAL_FOOD), false),
                new TBFollowParentGoal(this, 1.0D),
                new BreedGoal(this, 1.0D),
                new TBWaterAvoidRandomFlyingGoal(this, 1.0D, 200),
                new LookAtPlayerGoal(this, Player.class, 6.0F),
                new FloatGoal(this),
                new RandomLookAroundGoal(this)
        );

        addTargetGoals(
                new OwnerHurtByTargetGoal(this),
                new IncludesSitingRidingMeleeAttackGoal(this, 1.0D, true),
                new HurtByTargetGoal(this).setAlertOthers(),
                new OwnerHurtTargetGoal(this)
        );
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (isTameFood(itemstack) && !this.isTame()) {
            tameTBAnimal(player, itemstack, 33);
            return InteractionResult.SUCCESS;
        }

        else if (this.isTame() && this.hasStand() && !this.isOwnedBy(player))
            if(!this.isInSittingPose()){
                player.startRiding(this);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }

        return super.mobInteract(player, hand);
    }


    @Override
    public boolean hatBoostItem(Player player) {
        return player.getInventory().getArmor(3).is(TBItemRegistry.FLYING_HELMET.get());
    }

    @Override
    public boolean isFood(ItemStack p_27600_) {
        boolean isFood = p_27600_.is(TBTagRegistry.Items.QUETZAL_FOOD);
        if(isFood)
            playBite = true;
        return isFood;
    }

    @Override
    public boolean isTameFood(ItemStack itemStack) {
        
        boolean isFood = this.getHealth() < 10 && itemStack.is(TBTagRegistry.Items.QUETZAL_TAME_FOOD);
        if(isFood)
            playBite = true;
        return isFood;
    }

    @Override
    public EggBlockItem getEgg() {
        return (EggBlockItem) TBItemRegistry.QUETZAL_EGG_ITEM.get();
    }

    @Override
    public void setBaby(boolean p_146756_) {
        this.setAge(p_146756_ ? -48000 : 0);
    }

    @Override
    protected void positionRider(@NotNull Entity rider, @NotNull MoveFunction p_19958_) {
        if(this.getPassengers().get(0).equals(rider))
            super.positionRider(rider, p_19958_);
        else
            if(this.getPassengers().get(1).equals(rider))
                positionWithOffSets(rider,0.9f,-0.7f,1.1f, 0.9f);
            else if (this.getPassengers().get(2).equals(rider))
                positionWithOffSets(rider,0.9f,0.7f,1.1f, 0.9f);
            else
                positionWithOffSets(rider,0.6f,0.0f,1.f, 0.9f);
    }

    public void positionWithOffSets(Entity rider, float xOffset, float zOffset, float yMovingOffSet, float yStillOffSet){

        double cos = Math.cos((this.getYRot()) * (Math.PI / 180F));
        double sin = Math.sin((this.getYRot()) * (Math.PI / 180F));

        double offsetX = sin * xOffset;
        double offsetZ = cos * -xOffset;

        double offsetX2 = cos * zOffset;
        double offsetZ2 = sin * zOffset;

        rider.setPos(this.getX() + offsetX + offsetX2,
                this.getY() + rider.getMyRidingOffset() + (this.isServerFlying() ? yMovingOffSet : yStillOffSet),
                this.getZ() + offsetZ + offsetZ2);
    }

    @Override
    protected boolean canAddPassenger(@NotNull Entity entity) {
        return (this.getPassengers().size() < 5 && this.hasStand() && !this.getPassengers().isEmpty())
                || (this.isOwnedBy((LivingEntity) entity) && super.canAddPassenger(entity));
    }

    @Override
    public float getRidingSpeedMultiplier() {
        if(this.isInWater())
            return 0.4f;

        LivingEntity passenger = getControllingPassenger();
        if (passenger instanceof Player p  && this.hatBoostItem(p)){
            return 1f;
        }
        return 0.6F;
    }

    @Override
    protected TBMenu createMenu(int containerId, Inventory playerInventory) {
        return new TBMenuQuetzal(containerId, playerInventory, this);
    }

    @Override
    public boolean hasSaddle() {
        return !this.isBaby() && this.inventory.getStackInSlot(0).is(Items.SADDLE);
    }

    public boolean hasChest() {
        return !this.isBaby() && this.inventory.getStackInSlot(2).is(Items.CHEST);
    }

    public boolean hasStand() {
        return !this.isBaby() && this.inventory.getStackInSlot(1).is(TBItemRegistry.QUETZAL_STAND.get());
    }

    //gui stuff

    @Override
    public ResourceLocation getStaminaSpriteLocation() {
        return new ResourceLocation(TameableBeasts.MOD_ID, "textures/gui/quetzal_stamina.png");}

    @Override
    public ResourceLocation getStaminaBackgroundLocation() {
        return new ResourceLocation(TameableBeasts.MOD_ID, "textures/gui/quetzal_stamina_back.png");}

    @Override
    public Vec2 getStaminaSpriteDimensions() {return new Vec2(53, 40);}

    @Override
    public float getStaminaHeight() {return (float) this.flyingStamina / this.maxFlyingStamina;}

    //sounds

    @Override
    public SoundEvent getAmbientSound() {
        playBite = true;
        return TBSoundRegistry.QUETZAL_AMBIENT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        playBite = true;
        return TBSoundRegistry.QUETZAL_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource p_21239_) {
        playBite = true;
        return TBSoundRegistry.QUETZAL_HURT.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos p_20135_, @NotNull BlockState p_20136_) {
        this.playSound(TBSoundRegistry.QUETZAL_STEPS.get(), 0.15F, 1.0F);
    }

    @Override
    public SoundEvent getTameSound(){
        playBite = true;
        return TBSoundRegistry.QUETZAL_INTERACT.get();
    }

    @Override
    public SoundEvent getInteractSound(){
        playBite = true;
        return TBSoundRegistry.QUETZAL_INTERACT.get();
    }

    //animation stuff

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar control) {
        control.add(addAnimationTriggers(flyGliderController(this))
                .setSoundKeyframeHandler(state -> {
                    Player player = ClientUtils.getClientPlayer();

                    if (player != null)
                        player.playSound(TBSoundRegistry.QUETZAL_FLY.get(), 0.5f, 0.5f);
                }));
        control.add(biteController(this));
    }

    public boolean playBite = false;

    public <T extends FlyingRideableTBAnimal & GeoEntity> AnimationController<T> biteController(T entity) {
        return new AnimationController<>(entity, "mouthController", 10, event -> {
            AnimationController<T> controller = event.getController();
            if (playBite) {
                controller.setAnimation(RawAnimation.begin().then("bite", Animation.LoopType.PLAY_ONCE));
                playBite = false;
                controller.forceAnimationReset();
            }
            return PlayState.CONTINUE;
        });
    }
}
