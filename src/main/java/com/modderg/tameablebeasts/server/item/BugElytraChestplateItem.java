package com.modderg.tameablebeasts.server.item;

import com.modderg.tameablebeasts.client.item.BugElytraChestplateRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.function.Consumer;

public class BugElytraChestplateItem extends ArmorItem implements GeoItem {

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    boolean isFlying = false;

    public BugElytraChestplateItem(ArmorMaterial p_40386_, Type p_266831_, Properties p_40388_) {
        super(p_40386_, p_266831_, p_40388_);
    }

    public static boolean isFlyEnabled(ItemStack p_41141_) {
        return p_41141_.getDamageValue() < p_41141_.getMaxDamage() - 1;
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        isFlying = player.isFallFlying();
    }


    @Override
    public boolean canElytraFly(ItemStack stack, net.minecraft.world.entity.LivingEntity entity) {
        return BugElytraChestplateItem.isFlyEnabled(stack);
    }

    @Override
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private BugElytraChestplateRenderer renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (renderer == null) {
                    renderer = new BugElytraChestplateRenderer();
                }

                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);

                return this.renderer;
            }
        });
    }

    @Override
    public boolean elytraFlightTick(ItemStack stack, net.minecraft.world.entity.LivingEntity entity, int flightTicks) {
        if (!entity.level().isClientSide) {
            int nextFlightTick = flightTicks + 1;
            if (nextFlightTick % 10 == 0) {
               if (nextFlightTick % 20 == 0)
                    stack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(net.minecraft.world.entity.EquipmentSlot.CHEST));

               entity.gameEvent(net.minecraft.world.level.gameevent.GameEvent.ELYTRA_GLIDE);
            }
        } else
            entity.level().addParticle(ParticleTypes.GLOW, entity.getRandomX(0.6D), entity.getRandomY(), entity.getRandomZ(0.6D), 0.0D, 0.0D, 0.0D);

        return true;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(elytraController(this));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {return cache;}

    @Override
    public double getTick(Object itemStack) {
        return GeoItem.super.getTick(itemStack);
    }

    public <T extends BugElytraChestplateItem & GeoEntity> AnimationController<BugElytraChestplateItem> elytraController(BugElytraChestplateItem item) {
        return new AnimationController<>(item, "movement", 15, event -> {

            if (item.isFlying)
                event.getController().setAnimation(RawAnimation.begin().then("flying", Animation.LoopType.LOOP));
            else
                event.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));

            return PlayState.CONTINUE;
        });
    }
}
