package com.modderg.tameablebeasts.server.projectiles;

import com.modderg.tameablebeasts.server.entity.TBAnimal;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractTameArrow extends AbstractArrow {

    protected Item pickUp = null;
    protected Item particle = null;
    protected int chance = 0;

    protected AbstractTameArrow(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
        super(p_36721_, p_36722_);
        init();
        this.setBaseDamage(0);
    }

    protected AbstractTameArrow(EntityType<? extends AbstractArrow> p_36717_, LivingEntity p_36718_, Level p_36719_) {
        this(p_36717_, p_36718_.getX(), p_36718_.getEyeY() - (double)0.1F, p_36718_.getZ(), p_36719_);
        this.setOwner(p_36718_);
        if (p_36718_ instanceof Player) {
            this.pickup = AbstractArrow.Pickup.ALLOWED;
        }

    }

    protected AbstractTameArrow(EntityType<? extends AbstractArrow> p_36711_, double p_36712_, double p_36713_, double p_36714_, Level p_36715_) {
        this(p_36711_, p_36715_);
        this.setPos(p_36712_, p_36713_, p_36714_);
    }

    abstract void init();

    public void tick() {
        super.tick();
        if (this.level().isClientSide && !this.inGround)
            this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(this.particle)), this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
    }

    protected abstract boolean canTame(Entity entity);

    @Override
    protected void onHitEntity(@NotNull EntityHitResult p_36757_) {
        super.onHitEntity(p_36757_);

        if(this.getOwner() instanceof Player player && canTame(p_36757_.getEntity()) && p_36757_.getEntity() instanceof TBAnimal tameable && !tameable.isTame())
            tameable.tameGAnimal(player, null, chance);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(this.pickUp);
    }
}
