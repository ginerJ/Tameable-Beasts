package com.modderg.tameablebeasts.mixin;

import com.modderg.tameablebeasts.server.entity.abstracts.TBAnimal;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrushItem.class)
public abstract class BrushItemMixin {

    @Invoker("calculateHitResult")
    public abstract HitResult invokeCalculateHitResult(LivingEntity entity);

    @Inject(method = "onUseTick", at = @At("HEAD"), cancellable = true)
    public void onUseTick(Level level, LivingEntity p_273619_, ItemStack p_273316_, int p_273101_, CallbackInfo ci) {
        if (p_273101_ >= 0 && p_273619_ instanceof Player player) {
            HitResult hitresult = this.invokeCalculateHitResult(p_273619_);
            if (hitresult instanceof EntityHitResult result && result.getEntity() instanceof TBAnimal animal
                    && animal.getBrushDrops() != null) {

                Item[] brushDrops = animal.getBrushDrops();

                if (brushDrops.length > 0) {

                    if (animal.tickCount % 15 == 0){
                        level.playSound(player, animal.blockPosition(), SoundEvents.BRUSH_GENERIC, SoundSource.NEUTRAL);

                        if (animal.getRandom().nextInt(100) < 2 + 10 * animal.getHappiness()/100){
                            animal.spawnAtLocation(new ItemStack(brushDrops[animal.getRandom().nextInt(brushDrops.length)]));
                            animal.setHappiness(Math.max(0, animal.getHappiness() - 15));
                        }
                    }
                    ci.cancel();
                }
            }
        }
    }
}