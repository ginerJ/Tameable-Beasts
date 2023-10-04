package com.modderg.tameablebeasts.core;

import com.modderg.tameablebeasts.init.SoundInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class TameableGAnimal extends TamableAnimal{

    protected int interact = 0;
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
        }

        if(interact <= 0 && !this.isInSittingPose()){
            this.playSound(this.getInteractSound(), 0.15F, 1.0F);
            interact = 20;
            return super.mobInteract(player, hand);
        }

        return  super.mobInteract(player, hand);
    }

    @Override
    public void tick() {
        if(interact >= 0){
            interact --;
        }
        oldpos = new Vec3(this.position().x, this.position().y,this.position().z);
        super.tick();
    }

    @Override
    public void setTarget(@Nullable LivingEntity entity) {
        if (entity instanceof TameableGAnimal animal && animal.m_269323_() != null && animal.isOwnedBy(this.m_269323_())){
            return;
        }
        super.setTarget(entity);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    @Override
    public void setOrderedToSit(boolean sit) {
        this.messageState(this.isInSittingPose() ? "following":"sitting");
        super.setOrderedToSit(sit);
    }

    public SoundEvent getTameSound(){
        return null;
    }
    public SoundEvent getInteractSound(){
        return null;
    }

    public void tameGAnimal(Player player, ItemStack itemStack, int chance){
        if (!player.getAbilities().instabuild) itemStack.shrink(1);
        if (this.random.nextInt(chance) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
            this.playSound(getTameSound(), 0.15F, 1.0F);
            this.setOwnerUUID(player.getUUID());
            this.setTame(true);
            this.getLevel().broadcastEntityEvent(this, (byte) 7);
        } else {
            this.getLevel().broadcastEntityEvent(this, (byte) 6);
        }
    }

    public void tameGAnimal(Player player, int chance){
        if (this.random.nextInt(chance) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
            this.playSound(getTameSound(), 0.15F, 1.0F);
            this.setOwnerUUID(player.getUUID());
            this.setTame(true);
            this.getLevel().broadcastEntityEvent(this, (byte) 7);
        } else {
            this.getLevel().broadcastEntityEvent(this, (byte) 6);
        }
    }

    public void messageState(String txt){
        if (this.m_269323_() != null && this.m_269323_().getLevel().isClientSide && this.m_269323_().getLevel() instanceof ClientLevel) {
            Minecraft.getInstance().gui.setOverlayMessage(Component.literal(txt), false);
        }
    }

    public boolean canBeControlledByRider() {
        if (!(this.getControllingPassenger() instanceof Player)) {
            return false;
        }
        else return this.isOwnedBy((LivingEntity) this.getControllingPassenger());
    }

    public class FollowParentGoalIfNotSitting extends FollowParentGoal{
        private final TameableGAnimal animal;

        public FollowParentGoalIfNotSitting(TameableGAnimal p_25319_, double p_25320_) {
            super((Animal)p_25319_, p_25320_);
            this.animal = p_25319_;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && !animal.isOrderedToSit();
        }

        @Override
        public boolean canContinueToUse(){
            return super.canContinueToUse() && !animal.isOrderedToSit();
        }
    }
}
