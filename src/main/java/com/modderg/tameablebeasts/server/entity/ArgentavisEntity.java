package com.modderg.tameablebeasts.server.entity;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.client.entity.CustomJumpMeter;
import com.modderg.tameablebeasts.client.gui.TBItemStackHandler;
import com.modderg.tameablebeasts.client.gui.TBMenu;
import com.modderg.tameablebeasts.client.gui.TBMenuJustSaddle;
import com.modderg.tameablebeasts.server.ModCommonConfigs;
import com.modderg.tameablebeasts.registry.TBPOITypesRegistry;
import com.modderg.tameablebeasts.server.entity.abstracts.FlyingRideableTBAnimal;
import com.modderg.tameablebeasts.server.entity.goals.*;
import com.modderg.tameablebeasts.registry.TBItemRegistry;
import com.modderg.tameablebeasts.server.item.block.EggBlockItem;
import com.modderg.tameablebeasts.client.sound.SoundInit;
import com.modderg.tameablebeasts.server.tags.TBTags;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animation.AnimatableManager;

public class ArgentavisEntity extends FlyingRideableTBAnimal implements CustomJumpMeter {

    public ArgentavisEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.textureIdSize = 7;
        this.healthFloor = 20;
        this.attackAnims.add("attack");
        this.consumeStaminaModule = 2;
        this.recoverStaminaModule = 10;
        this.downMovementAngle = 5F;

        this.inventory = new TBItemStackHandler(this, 1);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.FLYING_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.FOLLOW_RANGE, 48.0D)
                .add(Attributes.JUMP_STRENGTH);
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (!this.isTame() && isTameFood(itemstack)) {
            tameGAnimal(player, itemstack, 33);
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    public static boolean checkArgentavisSpawnRules(EntityType<ArgentavisEntity> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos blockpos, RandomSource p_218246_) {
        return checkAnimalSpawnRules(p_218242_,p_218243_,p_218244_,blockpos,p_218246_) &&
                ModCommonConfigs.CAN_SPAWN_ARGENTAVIS.get() && blockpos.getY() >= ModCommonConfigs.ARGENTAVIS_SPAWN_HEIGHT.get();
    }

    protected void registerGoals() {
        super.registerGoals();

        this.addGoals(
                new SitWhenOrderedToGoal(this),
                new TakeCareOfEggsGoal(this, 15, TBPOITypesRegistry.ARGENTAVIS_POI),
                new TameablePanicGoal(this, 1.25D),
                new NoFlyRandomStrollGoal(this,1.0D),
                new TemptGoal(this, 1.0D, Ingredient.of(TBTags.Items.ARGENTAVIS_FOOD), false),
                new FlyFromNowAndThenGoal(this),
                new TBFollowParentGoal(this, 1.0D),
                new BreedGoal(this, 1.0D),
                new WaterAvoidingRandomFlyingGoal(this, 1.0D),
                new LookAtPlayerGoal(this, Player.class, 6.0F),
                new FloatGoal(this)
        );

        this.addTargetGoals(
                new IncludesSitingRidingMeleeAttackGoal(this, 1.0D, true),
                new HurtByTargetGoal(this).setAlertOthers(),
                new OwnerHurtTargetGoal(this),
                new OwnerHurtByTargetGoal(this)
        );
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(TBTags.Items.ARGENTAVIS_FOOD);
    }

    @Override
    public boolean isTameFood(ItemStack itemStack) {
        return this.getHealth() < 5 && itemStack.is(TBTags.Items.ARGENTAVIS_TAME_FOOD);
    }

    @Override
    public float getRidingSpeedMultiplier() {
        if(this.isInWater())
            return 0.6f;
        if(this.getDeltaMovement().y < 0)
            return 1.5f;

        return 1f;
    }

    @Override
    public void setBaby(boolean p_146756_) {
        this.setAge(p_146756_ ? -48000 : 0);
    }

    @Override
    public EggBlockItem getEgg() {
        return (EggBlockItem) TBItemRegistry.ARGENTAVIS_EGG_ITEM.get();
    }

    //gui stuff

    @Override
    public ResourceLocation getStaminaSpriteLocation() {
        return new ResourceLocation(TameableBeasts.MOD_ID, "textures/gui/argentavis_stamina.png");}

    @Override
    public ResourceLocation getStaminaBackgroundLocation() {
        return new ResourceLocation(TameableBeasts.MOD_ID, "textures/gui/argentavis_stamina_back.png");}

    @Override
    public Vec2 getStaminaSpriteDimensions() {return new Vec2(47, 30);}

    @Override
    public float getStaminaHeight() {return (float) this.flyingStamina / this.maxFlyingStamina;}

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
        if(isFlying())
            return SoundInit.QUETZAL_FLY.get();

        return SoundInit.ARGENTAVIS_AMBIENT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundInit.ARGENTAVIS_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource p_21239_) {return SoundInit.ARGENTAVIS_HURT.get();}

    @Override
    protected void playStepSound(@NotNull BlockPos p_20135_, @NotNull BlockState p_20136_) {
        this.playSound(SoundInit.QUETZAL_STEPS.get(), 0.15F, 1.0F);
    }

    @Override
    public SoundEvent getTameSound(){
        return SoundInit.ARGENTAVIS_INTERACT.get();
    }

    @Override
    public SoundEvent getInteractSound(){
        return SoundInit.ARGENTAVIS_INTERACT.get();
    }

    //animation stuff

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar control) {
        control.add(addAnimationTriggers(glideFlyController(this)));
    }
}
