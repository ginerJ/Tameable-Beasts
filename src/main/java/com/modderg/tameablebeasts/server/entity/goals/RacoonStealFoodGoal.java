package com.modderg.tameablebeasts.server.entity.goals;

import com.modderg.tameablebeasts.server.entity.custom.RacoonEntity;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.EnumSet;
import java.util.List;

public class RacoonStealFoodGoal extends Goal {

    private static final TargetingConditions POLEN_TARGETING = TargetingConditions.forNonCombat().range(8.0D).ignoreLineOfSight();

    protected final RacoonEntity mob;
    protected final Level level;
    protected final double speedModifier;
    protected Entity entity;
    protected ItemStack item = new ItemStack(Items.HONEYCOMB);


    public RacoonStealFoodGoal(RacoonEntity racoon, double speedMod){
        this.mob = racoon;
        this.level = racoon.level();
        this.speedModifier = speedMod;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return this.mob.tickCount % 20 == 0 && hasTargetBeeOrPlayerNearBy() && !this.mob.isBellyFull();
    }

    @Override
    public boolean canContinueToUse() {
        return this.mob.tickCount % 20 == 0 && entity != null && (
                (entity instanceof Bee bee && bee.hasNectar()) ||
                (entity instanceof Player player && player.getItemInHand(InteractionHand.MAIN_HAND).isEdible())
        );
    }

    @Override
    public void stop() {
        super.stop();
        entity = null;
    }

    @Override
    public void tick() {
        this.mob.getLookControl().setLookAt(this.entity, 10.0F, (float)this.mob.getMaxHeadXRot());
        this.mob.getNavigation().moveTo(this.entity, this.speedModifier);

        if (this.mob.distanceToSqr(this.entity) < 2.5D) {

            if(entity instanceof Bee b)
                b.dropOffNectar();
            else if(entity instanceof Player p && !mob.isOwnedBy(p))
                p.getItemInHand(InteractionHand.MAIN_HAND).shrink(1);

            this.mob.setBellyFull(true);
            if (mob.level() instanceof ServerLevel)
                mob.triggerAnim("movement", "interact");

            mob.playSound(new ItemStack(Items.POTATO).getItem().getEatingSound(), 0.15F, 1.0F);
            mob.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, item), mob.getX(), mob.getY(), mob.getZ(), 0, 0, 0);
        }
    }

    private boolean hasTargetBeeOrPlayerNearBy(){
        List<? extends LivingEntity> list = this.level.getNearbyEntities(LivingEntity.class, POLEN_TARGETING, this.mob, this.mob.getBoundingBox().inflate(8.0D));
        for(LivingEntity ent:list)
            if(ent instanceof Bee b && b.hasNectar()){
                entity = b;
                break;
            }
            else if(ent instanceof Player b && !mob.isOwnedBy(b) && b.getItemInHand(InteractionHand.MAIN_HAND).isEdible()){
                entity = b;
                break;
            }
        return this.entity != null;
    }
}
