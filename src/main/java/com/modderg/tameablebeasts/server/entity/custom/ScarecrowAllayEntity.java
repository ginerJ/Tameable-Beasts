package com.modderg.tameablebeasts.server.entity.custom;

import com.modderg.tameablebeasts.server.entity.FlyingTBAnimal;
import com.modderg.tameablebeasts.server.entity.TBAnimal;
import com.modderg.tameablebeasts.server.entity.goals.IncludesSitingRidingMeleeAttackGoal;
import com.modderg.tameablebeasts.server.item.ItemInit;
import com.modderg.tameablebeasts.client.sound.SoundInit;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animation.AnimatableManager;

public class ScarecrowAllayEntity extends FlyingTBAnimal implements GeoEntity {

    private static final EntityDataAccessor<Boolean> HASHOE = SynchedEntityData.defineId(TBAnimal.class, EntityDataSerializers.BOOLEAN);
    public void setHoe(boolean i){
        this.getEntityData().set(HASHOE, i);
    }
    public boolean hasHoe(){
        return this.getEntityData().get(HASHOE);
    }

    public ScarecrowAllayEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.textureIdSize = 3;
        this.attackAnims.add("attack");

        this.setPathfindingMalus(BlockPathTypes.WATER, -3.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 16.0F);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.FLYING_SPEED, 0.15F)
                .add(Attributes.MOVEMENT_SPEED, 0.25F)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(1, new IncludesSitingRidingMeleeAttackGoal(this, 2D, true));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new OwnerHurtByTargetGoal(this));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new FloatGoal(this));

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HASHOE, false);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("HASHOE"))
            this.setHoe(compound.getBoolean("HASHOE"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("HASHOE", this.hasHoe());
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if(this.isOwnedBy(player)){
            if(itemstack.is(ItemInit.IRON_BIG_HOE.get()) && !this.hasHoe()){
                this.setHoe(true);
                updateAttributes();
                itemstack.shrink(1);
                return InteractionResult.SUCCESS;
            }
            if(itemstack.is(Tags.Items.SHEARS)){
                this.setTextureId(this.random.nextInt(3));
                this.spawnItemParticles(new ItemStack(Items.PUMPKIN),16,this);
                return InteractionResult.SUCCESS;
            }
        } else if(!this.isTame())
            tameGAnimal(player,null,100);

        return super.mobInteract(player, hand);
    }

    @Override
    public boolean isFood(ItemStack itemstack) {
        return itemstack.is(Items.WHEAT);
    }

    public void updateAttributes(){
        if (this.hasHoe())
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(10.D);
        else
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    }

    @Override
    protected void dropAllDeathLoot(DamageSource p_21192_) {
        if(this.hasHoe())
            this.level().addFreshEntity(new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), new ItemStack(ItemInit.IRON_BIG_HOE.get())));

        super.dropAllDeathLoot(p_21192_);
    }

    //sounds

    @Override
    public SoundEvent getAmbientSound() {
        if(!isStill())
            return SoundInit.SCARECROW_AMBIENT.get();

        return SoundInit.SCARECROW_FLY.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundInit.SCARECROW_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {return SoundInit.SCARECROW_HURT.get();}

    @Override
    public SoundEvent getTameSound(){
        return SoundInit.SCARECROW_INTERACT.get();
    }

    @Override
    public SoundEvent getInteractSound(){
        return SoundInit.SCARECROW_INTERACT.get();
    }

    //animation stuff

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar control) {
        control.add(addAnimationTriggers(justFlyController(this)));
    }
}

