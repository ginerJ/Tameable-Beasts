package com.modderg.tameablebeasts.server.entity.custom;

import com.modderg.tameablebeasts.server.ModCommonConfigs;
import com.modderg.tameablebeasts.server.entity.RideableTBAnimal;
import com.modderg.tameablebeasts.server.entity.TBAnimal;
import com.modderg.tameablebeasts.server.entity.goals.*;
import com.modderg.tameablebeasts.server.item.ItemInit;
import com.modderg.tameablebeasts.server.item.block.EggBlockItem;
import com.modderg.tameablebeasts.client.sound.SoundInit;
import com.modderg.tameablebeasts.server.tags.TBTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.AnimationState;

public class ChikoteEntity extends RideableTBAnimal {

    @Override
    public Item itemSaddle() {
        return ItemInit.CHIKOTE_SADDLE.get();
    }

    public ChikoteEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.textureIdSize = 9;
        this.healthFloor = 18;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3D);
    }

    public static boolean checkChikoteSpawnRules(EntityType<ChikoteEntity> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos p_218245_, RandomSource p_218246_) {
        return checkAnimalSpawnRules(p_218242_,p_218243_,p_218244_,p_218245_,p_218246_) && ModCommonConfigs.CAN_SPAWN_CHIKOTE.get();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        addGoals(
                new TameablePanicGoal(this, 1.2D),
                new TBFollowOwnerGoal(this, 1.0D, 10.0F, 6.0F),
                new FloatGoal(this),
                new RaidCropsTameableGoal(this, 15),
                new SitWhenOrderedToGoal(this),
                new TakeCareOfEggsGoal(this, 15, InitPOITypes.CHIKOTE_POI),
                new TemptGoal(this, 1.1D, Ingredient.of(TBTags.Items.CHIKOTE_FOOD), false),
                new WaterAvoidingRandomStrollGoal(this, 1.0D),
                new AvoidEntityGoal<>(this, ScarecrowAllayEntity.class, 8.0F, 2.2D, 2.2D),
                new RandomSwimmingGoal(this, 1.0D, 10),
                new BreedGoal(this, 1.0D),
                new TBFollowParentGoal(this, 1.0D),
                new LookAtPlayerGoal(this, Player.class, 6.0F),
                new RandomStrollGoal(this, 1.0D, 10)
        );

    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (isTameFood(itemstack) && !this.isTame()) {
            tameGAnimal(player, itemstack, 10);
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(TBTags.Items.CHIKOTE_FOOD);
    }

    @Override
    public boolean isTameFood(ItemStack itemStack) {return itemStack.is(TBTags.Items.CHIKOTE_TAME_FOOD);}

    @Override
    public EggBlockItem getEgg() {
        return (EggBlockItem) ItemInit.CHIKOTE_EGG_ITEM.get();
    }

    @Override
    public void updateAttributes(){
        if (this.isBaby())
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2D);
        else
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
    }

    //SLOWFALLING STUFF

    @Override
    public void aiStep() {
        super.aiStep();
        Vec3 vec3 = this.getDeltaMovement();
        if (!this.onGround() && vec3.y < 0.0D)
            this.setDeltaMovement(vec3.multiply(1.0D, 0.8D, 1.0D));
    }

    @Override
    public boolean causeFallDamage(float p_147187_, float p_147188_, @NotNull DamageSource p_147189_) {
        return false;
    }

    //sounds

    @Override
    public SoundEvent getAmbientSound() {
        return SoundInit.CHIKOTE_AMBIENT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundInit.CHIKOTE_DEATH.get();
    }

    @org.jetbrains.annotations.Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource p_21239_) {return SoundInit.CHIKOTE_HURT.get();}

    @Override
    protected void playStepSound(@NotNull BlockPos p_20135_, @NotNull BlockState p_20136_) {
        this.playSound(SoundInit.CHIKOTE_STEPS.get(), 0.15F, 1.0F);
    }

    @Override
    public SoundEvent getTameSound(){
        return SoundInit.CHIKOTE_HAPPY.get();
    }

    @Override
    public SoundEvent getInteractSound(){
        return SoundInit.CHIKOTE_INTERACT.get();
    }

    //animation stuff

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar control) {
        control.add(addAnimationTriggers(groundController(this))
                .triggerableAnim("eat_crop", RawAnimation.begin().then("eat_crop", Animation.LoopType.PLAY_ONCE)));
    }

    @Override
    public <T extends TBAnimal & GeoEntity> PlayState groundState(T entity, AnimationState<T> event) {

        if(!entity.onGround() && entity.getDeltaMovement().y < 0.0D){
            event.getController().setAnimation(RawAnimation.begin().then("riding_up", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if(entity.isControlledByLocalInstance()) {
            if(event.isMoving())
                event.getController().setAnimation(RawAnimation.begin().then("riding_walk", Animation.LoopType.LOOP));
            else
                event.getController().setAnimation(RawAnimation.begin().then("idle_riding", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        else return super.groundState(entity, event);
    }
}
