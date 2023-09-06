package com.modderg.tameablebeasts.core;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class TameableGAnimal extends TamableAnimal{
    public Boolean isMoving() {
        return this.oldpos == this.position();
    }
    private Boolean isRunning = false;
    public Boolean getRunning(){
        return isRunning;
    }
    public void setRunning(boolean b){
        isRunning = b;
    }
    public Vec3 oldpos = new Vec3(0,0,0);

    protected TameableGAnimal(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if(isFood(player.getItemInHand(hand))){
            this.heal(1f);
            return super.mobInteract(player, hand);
        }
        return  super.mobInteract(player, hand);
    }

    @Override
    public void tick() {
        if(this.getTarget() != null && this.getTarget() instanceof TamableAnimal){
            TamableAnimal ta = (TamableAnimal) this.getTarget();
            if(Objects.equals(ta.getOwnerUUID(), this.getOwnerUUID())){
                this.setTarget(null);
            }
        }
        oldpos = new Vec3(this.position().x, this.position().y,this.position().z);
        super.tick();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    public boolean canBeControlledByRider() {
        if (!(this.getControllingPassenger() instanceof Player)) {
            return false;
        }
        else return this.isOwnedBy((LivingEntity) this.getControllingPassenger());
    }

    public class FollowParentGoalIfNotSitting extends FollowParentGoal{

        private final TameableGAnimal animal2;

        public FollowParentGoalIfNotSitting(TameableGAnimal p_25319_, double p_25320_) {
            super((Animal)p_25319_, p_25320_);
            this.animal2 = p_25319_;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && !animal2.isInSittingPose();
        }
    }
}
