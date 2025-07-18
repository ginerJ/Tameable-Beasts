package com.modderg.tameablebeasts.server.entity;

import net.minecraft.client.particle.HugeExplosionParticle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;

public class BeetleDrone extends Animal implements GeoEntity {

    public BeetleDrone(EntityType<? extends Animal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
        this.navigation = new FlyingPathNavigation(this, this.level());
        this.moveControl = new FlyingMoveControl(this, 20, false);
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.FLYING_SPEED, 0.3D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 1.5D)
                .add(Attributes.MAX_HEALTH, 1D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.5D, true));
        this.goalSelector.addGoal(0, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
    }

    int life = this.random.nextInt(1, 1000);

    @Override
    public void tick() {
        super.tick();
        if(life-- == 0)
            explodeDrone();
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity p_21372_) {
        boolean value = super.doHurtTarget(p_21372_);
        explodeDrone();
        return value;
    }

    private void explodeDrone(){
        if(this.level().isClientSide())
            return;

        int particleCount = 100;
        double radius = 1.0;
        double speed = 0.1D;

        for (int i = 0; i < particleCount; i++) {
            double tita = Math.random() * 2 * Math.PI;
            double fi = Math.random() * Math.PI;

            double xDir = Math.sin(fi) * Math.cos(tita);
            double yDir = Math.sin(fi) * Math.sin(tita);
            double zDir = Math.cos(fi);

            double xOffset = xDir * radius;
            double yOffset = yDir * radius;
            double zOffset = zDir * radius;

            ((ServerLevel) this.level()).sendParticles(ParticleTypes.GLOW,
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    1,
                    xOffset,
                    yOffset,
                    zOffset,
                    speed);
        }
        this.playSound(SoundEvents.SHULKER_BULLET_HIT, 1.0F, 1.0F);
        this.remove(RemovalReason.UNLOADED_TO_CHUNK);
    }

    @Override
    public boolean isNoGravity() {return true;}

    @Override
    public boolean causeFallDamage(float p_147187_, float p_147188_, @NotNull DamageSource p_147189_) {return false;}

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel p_146743_, @NotNull AgeableMob p_146744_) {return null;}

    public static final RawAnimation FLY = RawAnimation.begin().thenLoop("fly");
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this,"movement", 5, state -> state.setAndContinue(FLY)));
    }

    protected AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }
}
