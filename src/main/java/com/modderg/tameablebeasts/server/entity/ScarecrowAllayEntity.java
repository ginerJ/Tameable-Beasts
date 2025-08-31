package com.modderg.tameablebeasts.server.entity;

import com.modderg.tameablebeasts.client.gui.TBItemStackHandler;
import com.modderg.tameablebeasts.client.gui.TBMenu;
import com.modderg.tameablebeasts.client.gui.TBMenuScarecrow;
import com.modderg.tameablebeasts.registry.TBItemRegistry;
import com.modderg.tameablebeasts.registry.TBTagRegistry;
import com.modderg.tameablebeasts.server.entity.abstracts.FlyingTBAnimal;
import com.modderg.tameablebeasts.server.entity.goals.IncludesSitingRidingMeleeAttackGoal;
import com.modderg.tameablebeasts.client.sound.SoundInit;
import net.minecraft.sounds.SoundEvent;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animation.AnimatableManager;

public class ScarecrowAllayEntity extends FlyingTBAnimal implements GeoEntity {

    public ScarecrowAllayEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.textureIdSize = 3;
        this.attackAnims.add("attack");

        this.setPathfindingMalus(BlockPathTypes.WATER, -3.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 16.0F);

        this.inventory = new TBItemStackHandler(this, 1);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 45.0D)
                .add(Attributes.FLYING_SPEED, 0.15F)
                .add(Attributes.MOVEMENT_SPEED, 0.25F)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    public void updateAttributes(){

        double atkDamage = 3.0D;
        if (this.hasScythe())
            atkDamage = 12.0D;

        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(atkDamage);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.addGoals(
                new SitWhenOrderedToGoal(this),
                new IncludesSitingRidingMeleeAttackGoal(this, 2D, true),
                new LookAtPlayerGoal(this, Player.class, 6.0F),
                new RandomLookAroundGoal(this),
                new FloatGoal(this)
        );

        this.addTargetGoals(
                new OwnerHurtByTargetGoal(this),
                new OwnerHurtTargetGoal(this),
                new HurtByTargetGoal(this)
        );
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if(this.isOwnedBy(player)){
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
    public Item[] getBrushDrops() {
        return new Item[]{Items.WHEAT};
    }

    @Override
    public boolean isFood(ItemStack itemstack) {
        return itemstack.is(TBTagRegistry.Items.SCARECROW_FOOD);
    }

    public boolean hasScythe() {
        return this.inventory.getStackInSlot(0).is(TBItemRegistry.IRON_BIG_HOE.get());
    }

    @Override
    protected TBMenu createMenu(int containerId, Inventory playerInventory) {
        return new TBMenuScarecrow(containerId, playerInventory, this);
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

