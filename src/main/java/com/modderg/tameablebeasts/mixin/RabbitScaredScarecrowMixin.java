package com.modderg.tameablebeasts.mixin;

import com.modderg.tameablebeasts.server.entity.ScarecrowAllayEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.Rabbit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Rabbit.class)
public class RabbitScaredScarecrowMixin {

    @Inject(method = "registerGoals", at = @At("HEAD"))
    protected void registerGoals(CallbackInfo ci) {
        ((Mob) (Object) this).goalSelector.addGoal(4, new AvoidEntityGoal<>( (Rabbit) (Object) this, ScarecrowAllayEntity.class, 8.0F, 2.2D, 2.2D));
    }
}

