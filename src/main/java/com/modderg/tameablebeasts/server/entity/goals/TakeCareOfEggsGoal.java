package com.modderg.tameablebeasts.server.entity.goals;

import com.modderg.tameablebeasts.server.block.EggBlockEntity;
import com.modderg.tameablebeasts.server.block.InitPOITypes;
import com.modderg.tameablebeasts.server.packet.InitPackets;
import com.modderg.tameablebeasts.server.packet.StoCLoveEggPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;

public class TakeCareOfEggsGoal extends Goal {

    Animal mob;

    List<RegistryObject<PoiType>> targetEggs = List.of(
            InitPOITypes.CHIKOTE_POI,
            InitPOITypes.ARGENTAVIS_POI,
            InitPOITypes.GRASSHOPPER_POI,
            InitPOITypes.CRESTED_GECKO_POI,
            InitPOITypes.PENGUIN_POI,
            InitPOITypes.GRAPTERANODON_POI,
            InitPOITypes.FLYING_BEETLE_POI,
            InitPOITypes.GROUND_BEETLE_POI,
            InitPOITypes.QUETZAL_POI,
            InitPOITypes.ROLY_POLY_POI
    );

    int radius;

    BlockPos targetPos;

    public TakeCareOfEggsGoal(Animal mob, int radius){
        this.mob = mob;
        this.radius = radius;
        doScan = mob.getRandom().nextInt(40);
    }

    public TakeCareOfEggsGoal(Animal mob, int radius, RegistryObject<PoiType> specificEgg){
        this(mob,radius);
        this.targetEggs = List.of(specificEgg);
    }

    int doScan;

    @Override
    public boolean canUse() {

        if(doScan-- <= 0){
            targetPos = hasEggToTakeCareOff();
            doScan = mob.getRandom().nextInt(80);
        }

        if(mob instanceof TamableAnimal tamable && tamable.isOrderedToSit())
            return false;

        return !mob.isAggressive() && targetPos != null;
    }

    @Override
    public boolean canContinueToUse() {
        return targetPos != null && mob.level().getBlockEntity(targetPos) instanceof EggBlockEntity && !(mob instanceof TamableAnimal tamable && tamable.isOrderedToSit());
    }

    @Override
    public void stop() {
        this.targetPos = null;
    }


    @Override
    public void tick() {

        mob.setMaxUpStep(1.5f);

        Level level = mob.level();

        if(mob.distanceToSqr(targetPos.getCenter()) < 4 && level.getBlockEntity(targetPos) instanceof EggBlockEntity egg){

            egg.goBadTimer = 3000;

            InitPackets.sendToAll(new StoCLoveEggPacket(targetPos));

            stop();
        } else {
            Vec3 center = targetPos.getCenter();

            double targetY = center.y + 1.5;

            this.mob.getNavigation().moveTo(center.x, targetY, center.z, 1.1f);
        }
    }

    private BlockPos hasEggToTakeCareOff() {
        Level level = mob.level();

        if (level instanceof ServerLevel serverLevel) {

            PoiManager poiManager = serverLevel.getPoiManager();

            for (RegistryObject<PoiType> poiT : targetEggs) {

                Optional<BlockPos> poiPos = poiManager.find(
                        poiType -> poiType.is(poiT.getId()),
                        pos -> {
                            BlockEntity blockEntity = level.getBlockEntity(pos);
                            return blockEntity instanceof EggBlockEntity egg && egg.goBadTimer < 2700 && egg.goBadTimer > 0;
                        },
                        mob.blockPosition(), 48, PoiManager.Occupancy.ANY
                );

                if (poiPos.isPresent())
                    return poiPos.get();
            }
        }

        return null;
    }
}
