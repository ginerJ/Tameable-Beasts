package com.modderg.tameablebeasts.server.entity.abstracts;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public interface TBRideable {

    default boolean hasSaddle(){
        return false;
    }

    default boolean hatBoostItem(Player player) {
        return false;
    }

    default  Item itemSaddle() {
        return Items.SADDLE;
    }
    default boolean isSaddle(ItemStack itemStack) {
        return itemStack.is(itemSaddle());
    }

    default float getRidingSpeedMultiplier(){
        LivingEntity passenger = getControllingPassenger();
        if (passenger instanceof Player p  && hatBoostItem(p))
            return 1.6f;

        else return 1f;
    }

    default void messageRiding(Player player){
        if(!(player instanceof LocalPlayer)) return;

        Minecraft.getInstance().gui.setOverlayMessage(Component.literal(getRidingMessage()), false);
    }

    default String getShiftKeyName(){
        KeyMapping key = Minecraft.getInstance().options.keyShift;
        return key.getTranslatedKeyMessage().getString();
    }

    default String getJumpKeyName(){
        KeyMapping key = Minecraft.getInstance().options.keyJump;
        return key.getTranslatedKeyMessage().getString();
    }

    default String getCrouchKeyName(){
        KeyMapping key = Minecraft.getInstance().options.keySprint;
        return key.getTranslatedKeyMessage().getString();
    }

    default String getRidingMessage(){
        return "Press " + getShiftKeyName() + " to Dismount";
    }

    default float applyPotionEffectsToSpeed(double speed) {
        double baseSpeed = speed;

        if (this.hasEffect(MobEffects.MOVEMENT_SPEED)) {
            int amplifier = this.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }

        if (this.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
            int amplifier = this.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getAmplifier();
            baseSpeed *= 1.0 - 0.15 * (amplifier + 1);
        }

        return (float) baseSpeed;
    }

     default void dropSaddle() {
        if(this.hasSaddle() && this.itemSaddle()!=null)
            spawnAtLocation(itemSaddle());
    }

     ItemEntity spawnAtLocation(ItemLike itemLike);
     boolean hasEffect(MobEffect p_21024_);
     SynchedEntityData getEntityData();
     MobEffectInstance getEffect(MobEffect p_21125_);
     LivingEntity getControllingPassenger();
     Level level();
     double getX();
     double getY();
     double getZ();
}
