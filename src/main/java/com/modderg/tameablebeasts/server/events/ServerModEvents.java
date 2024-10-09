package com.modderg.tameablebeasts.server.events;

import com.modderg.tameablebeasts.server.entity.EntityIinit;
import com.modderg.tameablebeasts.server.entity.custom.*;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static net.minecraftforge.event.entity.SpawnPlacementRegisterEvent.Operation.OR;


public class ServerModEvents {

    @SubscribeEvent
    public static void onSpawnRegister(final SpawnPlacementRegisterEvent event){
    }
}
