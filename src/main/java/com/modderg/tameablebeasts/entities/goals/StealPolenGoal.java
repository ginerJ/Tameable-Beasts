package com.modderg.tameablebeasts.entities.goals;

import com.modderg.tameablebeasts.entities.custom.RacoonEntity;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.EnumSet;
import java.util.List;

public class StealPolenGoal extends Goal {

    private static final TargetingConditions POLEN_TARGETING = TargetingConditions.forNonCombat().range(8.0D).ignoreLineOfSight();

    protected final RacoonEntity mob;
    protected final Level level;
    protected final double speedModifier;
    protected Bee bee;
    protected ItemStack item = new ItemStack(Items.HONEYCOMB);


    public StealPolenGoal(RacoonEntity racoon, double speedMod){
        this.mob = racoon;
        this.level = racoon.getLevel();
        this.speedModifier = speedMod;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return hasBeeWithPolenNearby() && !this.mob.hasPolen();
    }

    @Override
    public boolean canContinueToUse() {
        return bee != null && bee.hasNectar();
    }

    @Override
    public void stop() {
        super.stop();
        bee = null;
    }

    @Override
    public void tick() {
        this.mob.getLookControl().setLookAt(this.bee, 10.0F, (float)this.mob.getMaxHeadXRot());
        this.mob.getNavigation().moveTo(this.bee, this.speedModifier);

        if (this.mob.distanceToSqr(this.bee) < 2.0D) {
            this.bee.dropOffNectar();
            this.mob.setPolen(true);
            if (mob.getLevel() instanceof ServerLevel)
                mob.triggerAnim("InteractionController", "interact");

            mob.playSound(new ItemStack(Items.POTATO).getItem().getEatingSound(), 0.15F, 1.0F);
            mob.getLevel().addParticle(new ItemParticleOption(ParticleTypes.ITEM, item), mob.getX(), mob.getY(), mob.getZ(), 0, 0, 0);
        }
    }

    private boolean hasBeeWithPolenNearby(){
        List<? extends Animal> list = this.level.getNearbyEntities(Bee.class, POLEN_TARGETING, this.mob, this.mob.getBoundingBox().inflate(8.0D));
        for(Animal animal:list){
            if(animal instanceof Bee b && b.hasNectar()){
                bee = b;
                break;
            }
        }
        return this.bee != null;
    }
}
