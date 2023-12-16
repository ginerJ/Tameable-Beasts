package com.modderg.tameablebeasts.events;

import com.modderg.tameablebeasts.entities.FurGolemEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEventBusEvents {

    static TargetingConditions LEASH_TARGETING = TargetingConditions.forNonCombat().range(8.0D).ignoreLineOfSight();

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.EntityInteractSpecific event) {
        Player player = event.getEntity();
        Level world = event.getEntity().getLevel();
        Mob target = (Mob) event.getTarget();

        if (!target.canBeLeashed(player)) {
            return;
        }

        for (Mob entity : world.getNearbyEntities(FurGolemEntity.class, LEASH_TARGETING, player, player.getBoundingBox().inflate(8.0D))) {
            if (!entity.is(target) && entity.isLeashed() && entity.getLeashHolder().is(player)) {
                entity.dropLeash(true, false);
                target.setLeashedTo(entity, true);
                break;
            }
        }
    }
}
