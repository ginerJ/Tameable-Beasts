package com.modderg.tameablebeasts.server.entity.abstracts;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.client.packet.CtoSSyncRiderWantsFlying;
import com.modderg.tameablebeasts.registry.TBPacketRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import static net.minecraft.world.entity.ai.attributes.Attributes.FLYING_SPEED;

public class FlyingRideableTBAnimal extends FlyingTBAnimal implements TBRideable, PlayerRideableJumping {

    protected final int maxFlyingStamina = 50;
    protected int flyingStamina = maxFlyingStamina;
    private int travelCount = 0;

    protected int consumeStaminaModule = 0;
    protected int recoverStaminaModule = 0;
    protected float downMovementAngle = 0F;

    protected boolean riderWantsFlying = false;
    public void setRiderWantsFlying(boolean i) {this.riderWantsFlying = i;}
    public boolean getRiderWantFlying() {return !this.getPassengers().isEmpty() && this.riderWantsFlying;}

    protected boolean wasWalking = this.onGround();

    public boolean upInput = false;
    public boolean downInput = false;
    public boolean isJustRode = false;

    protected FlyingRideableTBAnimal(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);

        if(this.hasControllingPassenger())
            riderWantsFlying = this.onGround();
    }

    @Override
    public String getRidingMessage(){
//        return getJumpKeyName() + " to Ascend, " + getCrouchKeyName() + " to Plummet , " + getShiftKeyName() + " to Dismount";
        return String.format(Component.translatable("gui." + TameableBeasts.MOD_ID + ".flying_rideable.riding_message").getString(), getJumpKeyName(), getCrouchKeyName(), getShiftKeyName());
    }

    @NotNull
    public InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (isOwnedBy(player)){
            if (!player.isShiftKeyDown() && this.hasSaddle() && !isFood(itemstack) && !itemstack.is(Items.BRUSH)){
                if(!level().isClientSide())
                    player.startRiding(this);
//                messageRiding(player);
                isJustRode = true;

                return InteractionResult.SUCCESS;
            }
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public void travel(@NotNull Vec3 vec3) {
        if (this.isAlive()) {
            if (this.getControllingPassenger() instanceof Player passenger) {

                this.yRotO = getYRot();
                this.xRotO = getXRot();

                setRot(passenger.getYRot(), passenger.getXRot() * 0.5f);

                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.yBodyRot;

                float x = passenger.xxa * 0.5F, z = passenger.zza * 0.5F;

                if (z <= 0)
                    z *= 0.25f;

                this.setSpeed(0.1f);

                boolean tryFlyUp = upInput && flyingStamina > 0;

                if(this.onGround() && !wasWalking)
                    wasWalking = true;

                if(tryFlyUp)
                    wasWalking = false;

                if(level().isClientSide() && tickCount%5==0 && riderWantsFlying == wasWalking){
                    riderWantsFlying = !wasWalking;
                    TBPacketRegistry.sendToServer(new CtoSSyncRiderWantsFlying(this.getId(), !wasWalking));
                }

                if (wasWalking) {
                    super.travel(new Vec3(x, vec3.y, z));
                    travelCountProcess();

                } else {
                    double yMov = -(downInput ? 45.0F : downMovementAngle) * (Math.PI / 180);

                    if(tryFlyUp){
                        travelCount++;
                        yMov = 25.0F * (Math.PI / 180);

                        if(travelCount % consumeStaminaModule == 0)
                            flyingStamina --;
                    }

                    float speed = (float) getAttributeValue(FLYING_SPEED) * 0.2f;

                    speed *= this.getRidingSpeedMultiplier();

                    moveDist = moveDist > 0? moveDist : 0;

                    vec3 = new Vec3(x, yMov, z);

                    moveRelative(applyPotionEffectsToSpeed(speed), vec3);
                    move(MoverType.SELF, getDeltaMovement());

                    if (x == 0 && z == 0)
                        setDeltaMovement(getDeltaMovement().multiply(new Vec3(0.9, 1, 0.9)));

                    if (!upInput && getDeltaMovement().lengthSqr() < 0.1)
                        setDeltaMovement(getDeltaMovement().add(0, Math.sin(tickCount / 4f) * 0.02, 0));
                }

            } else {
                super.travel(vec3);
                travelCountProcess();
            }
        }
    }

    @Override
    protected Boolean shouldFly() {
        Entity owner = this.getOwner();

        return super.shouldFly() && (

                (this.getGoalsRequireFlying() && (
                        !this.isTame() ||
                       (!this.isOrderedToSit() &&  this.isWandering())
                        )
                ) ||

                (!hasControllingPassenger() && (
                        this.isAggressive() ||
                        (!this.onGround() && this.isOverFluidOrVoid())
                        )
                ) ||

                this.getRiderWantFlying() ||

                (owner != null && (
                        (this.distanceTo(owner) > 10 && !this.isWandering()) ||
                        (this.isServerFlying() && !owner.onGround() && !hasPassenger(owner))
                        )
                )
        );
    }

    private void travelCountProcess(){
            travelCount--;
            if(travelCount % recoverStaminaModule == 0 &&
                    flyingStamina < maxFlyingStamina)
                flyingStamina ++;
    }

    public float getStaminaScale() {
        return flyingStamina * 1F / maxFlyingStamina;
    }

    public boolean canJump() {
        return true;
    }
    public void handleStartJump(int o) {}
    public void handleStopJump() {}
    public void onPlayerJump(int o) {}
}
