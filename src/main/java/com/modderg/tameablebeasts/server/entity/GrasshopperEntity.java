package com.modderg.tameablebeasts.server.entity;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.client.entity.CustomJumpMeter;
import com.modderg.tameablebeasts.client.gui.TBItemStackHandler;
import com.modderg.tameablebeasts.client.gui.TBMenu;
import com.modderg.tameablebeasts.client.gui.TBMenuGrasshopper;
import com.modderg.tameablebeasts.registry.TBTagRegistry;
import com.modderg.tameablebeasts.server.ModCommonConfigs;
import com.modderg.tameablebeasts.registry.TBPOITypesRegistry;
import com.modderg.tameablebeasts.server.entity.abstracts.RideableTBAnimal;

import com.modderg.tameablebeasts.server.entity.goals.*;
import com.modderg.tameablebeasts.registry.TBItemRegistry;
import com.modderg.tameablebeasts.server.item.block.EggBlockItem;
import com.modderg.tameablebeasts.registry.TBSoundRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import static com.modderg.tameablebeasts.client.entity.TBAnimControllers.groundState;

public class GrasshopperEntity extends RideableTBAnimal implements PlayerRideableJumping, CustomJumpMeter {

    protected float playerJumpPendingScale = 0f;
    boolean allowStandSliding = true;
    protected int jumpCount = this.random.nextInt(100, 200);
    protected boolean isJumping = false;
    private int jumpingTicks = 100;

    public GrasshopperEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        hasWarmthVariants();

        this.inventory = new TBItemStackHandler(this, 17);
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.15D)
                .add(Attributes.JUMP_STRENGTH, 2.5f);
    }

    @Override
    public void updateAttributes(){
        double maxHealth = 25D;
        if (this.isTame())
            maxHealth = 45D;

        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(maxHealth);
    }

    public static boolean checkGrasshopperSpawnRules(EntityType<GrasshopperEntity> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos p_218245_, RandomSource p_218246_) {
        return checkAnimalSpawnRules(p_218242_,p_218243_,p_218244_,p_218245_,p_218246_) && ModCommonConfigs.CAN_SPAWN_GRASSHOPPER.get();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.addGoals(
                new TameablePanicGoal(this, 1.2D),
                new TBFollowOwnerGoal(this, 1.0D, 10.0F, 6.0F),
                new FloatGoal(this),
                new SitWhenOrderedToGoal(this),
                new TakeCareOfEggsGoal(this, 15, TBPOITypesRegistry.GRASSHOPPER_POI),
                new TemptGoal(this, 1.1D, Ingredient.of(TBTagRegistry.Items.GRASSHOPPER_FOOD), false),
                new WaterAvoidingRandomStrollGoal(this, 1.0D),
                new TameablePanicGoal(this, 1.25D),
                new RandomSwimmingGoal(this, 1.0D, 10),
                new BreedGoal(this, 1.0D),
                new TBFollowParentGoal(this, 1.0D),
                new LookAtPlayerGoal(this, Player.class, 6.0F),
                new RandomLookAroundGoal(this)
        );
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (isTameFood(itemstack) && !this.isTame()) {
            tameTBAnimal(player, itemstack, 20);
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public Item[] getBrushDrops() {
        return new Item[]{TBItemRegistry.GRASSHOPPER_LEG.get()};
    }

    @Override
    public String getRidingMessage(){
        return getJumpKeyName() + " to Jump, " + getShiftKeyName() + " to Dismount";
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(TBTagRegistry.Items.GRASSHOPPER_FOOD);
    }

    @Override
    public boolean isTameFood(ItemStack itemStack) {
        
        return itemStack.is(TBTagRegistry.Items.GRASSHOPPER_TAME_FOOD);
    }

    @Override
    public void tick() {
        if(jumpCount >= 0) jumpCount --;
        jumpingTicks = Math.min(jumpingTicks +10, 100);
        super.tick();
    }

    @Override
    public boolean causeFallDamage(float p_147187_, float p_147188_, @NotNull DamageSource p_147189_) {
        return false;
    }

    @Override
    public EggBlockItem getEgg() {
        return (EggBlockItem) TBItemRegistry.GRASSHOPPER_EGG_ITEM.get();
    }

    @Override
    protected TBMenu createMenu(int containerId, Inventory playerInventory) {
        return new TBMenuGrasshopper(containerId, playerInventory, this);
    }

    @Override
    public boolean hasSaddle() {
        return !this.isBaby() && this.inventory.getStackInSlot(0).is(Items.SADDLE);
    }

    public boolean hasChest() {
        return !this.isBaby() && this.inventory.getStackInSlot(1).is(Items.CHEST);
    }

    //sounds

    @Override
    public SoundEvent getAmbientSound() {
        return TBSoundRegistry.GRASSHOPPER_AMBIENT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return TBSoundRegistry.GRASSHOPPER_DEATH.get();
    }

    @org.jetbrains.annotations.Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource p_21239_) {return TBSoundRegistry.GRASSHOPPER_HURT.get();}

    @Override
    protected void playStepSound(@NotNull BlockPos p_20135_, @NotNull BlockState p_20136_) {
        this.playSound(TBSoundRegistry.GRASSHOPPER_STEPS.get(), 0.15F, 1.0F);
    }

    @Override
    public SoundEvent getTameSound(){
        return TBSoundRegistry.GRASSHOPPER_INTERACT.get();
    }

    @Override
    public SoundEvent getInteractSound(){
        return TBSoundRegistry.GRASSHOPPER_INTERACT.get();
    }

    //ride stuff

    //max actual speed 0.27
    @Override
    public float getRidingSpeedMultiplier() {
        if(!this.onGround())
            return 2F;

        return 0.9F;
    }

    @Override
    public void travel(@NotNull Vec3 vec333) {
        if (this.isAlive()) {
            if (!this.isTame()) {
                if (jumpCount == 0 && this.onGround()) {
                    jumpFromGround();
                    this.playSound(TBSoundRegistry.GRASSHOPPER_JUMP.get(), 0.15F, 1.0F);
                    jumpCount = this.random.nextInt(20, 150);
                }
            }
            if (this.getControllingPassenger() instanceof Player passenger) {

                this.setYRot(passenger.getYRot());
                this.yRotO = this.getYRot();
                this.setXRot(passenger.getXRot() * 0.5F);
                this.setRot(this.getYRot(), this.getXRot());
                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.yBodyRot;
                float f = passenger.xxa * 0.25f;
                float f1 = passenger.zza/2f;
                if (f1 <= 0.0F) {
                    f1 *= 0.25F;
                }

                if (this.onGround() && this.playerJumpPendingScale == 0.0F && !this.allowStandSliding) {
                    f = 0.0F;
                    f1 = 0.0F;
                }

                if (this.playerJumpPendingScale > 0.0F && !this.isJumping() && this.onGround()) {
                    double d0 = this.getCustomJump() * (double) this.playerJumpPendingScale * (double) this.getBlockJumpFactor();
                    double d1 = d0 + this.getJumpPower();
                    Vec3 vec3 = this.getDeltaMovement();
                    this.setDeltaMovement(vec3.x, d1*0.7f, vec3.z);
                    this.setIsJumping(true);
                    this.hasImpulse = true;
                    net.minecraftforge.common.ForgeHooks.onLivingJump(this);
                    if (f1 > 0.0F) {
                        float f2 = Mth.sin(this.getYRot() * ((float) Math.PI / 180F));
                        float f3 = Mth.cos(this.getYRot() * ((float) Math.PI / 180F));
                        this.setDeltaMovement(this.getDeltaMovement().add((-1.5F * f2 * this.playerJumpPendingScale), 0.0D, (1.5F * f3 * this.playerJumpPendingScale)));
                    }

                    this.playerJumpPendingScale = 0.0F;
                }

                if (this.isControlledByLocalInstance()) {

                    this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    super.superTravel(new Vec3(f, vec333.y,f1));

                } else if (passenger instanceof Player) {
                    this.setDeltaMovement(Vec3.ZERO);
                }

                if (this.onGround()) {
                    this.playerJumpPendingScale = 0.0F;
                    this.setIsJumping(false);
                }

                this.tryCheckInsideBlocks();
            } else {
                super.superTravel(vec333);
            }
        }
    }

    public boolean isJumping(){
        return isJumping;
    }

    public void setIsJumping(boolean b){isJumping = b;}

    public double getCustomJump() {
        return this.getAttributeValue(Attributes.JUMP_STRENGTH);
    }

    @Override
    protected float getJumpPower() {
        if(jumpCount == 0) {
            jumpCount = this.random.nextInt(100, 200);
            return 0.84F * this.getBlockJumpFactor();
        }
        return super.getJumpPower();
    }

    @Override
    public void onPlayerJump(int p_21696_) {
        if (this.hasSaddle()) {
            if (p_21696_ < 0)
                p_21696_ = 0;
            else
                this.allowStandSliding = true;

            if (p_21696_ >= 90)
                this.playerJumpPendingScale = 1.0F;
            else
                this.playerJumpPendingScale = 0.4F + 0.4F * (float)p_21696_ / 90.0F;

            jumpingTicks = 0;
        }
    }

    @Override
    public boolean canJump() {
        return this.hasSaddle();
    }

    @Override
    public void handleStartJump(int p_21695_) {
        this.playSound(TBSoundRegistry.GRASSHOPPER_JUMP.get(), 0.15F, 1.0F);
    }

    @Override
    public void handleStopJump() {}

    @Override
    public ResourceLocation getStaminaSpriteLocation() {
        return new ResourceLocation(TameableBeasts.MOD_ID, "textures/gui/grasshopper_stamina.png");
    }

    @Override
    public ResourceLocation getStaminaBackgroundLocation() {
        return new ResourceLocation(TameableBeasts.MOD_ID, "textures/gui/grasshopper_stamina_back.png");
    }

    @Override
    public Vec2 getStaminaSpriteDimensions() {
        return new Vec2(40, 32);
    }

    @Override
    public float getStaminaHeight() {
        return (float) jumpingTicks /100;
    }

    //animation stuff

    public <T extends RideableTBAnimal & GeoEntity> AnimationController<T> jumpController(T entity) {
        return new AnimationController<>(entity,"movement", 2, event ->{
            if(!entity.onGround() && entity.getDeltaMovement().y > 0){
                event.getController().setAnimation(RawAnimation.begin().then("jump", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }
            return groundState(entity, event);
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar control) {
        control.add(addAnimationTriggers(jumpController(this)));
    }
}
