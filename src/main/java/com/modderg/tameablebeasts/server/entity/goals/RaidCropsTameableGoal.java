package com.modderg.tameablebeasts.server.entity.goals;

import com.modderg.tameablebeasts.server.block.InitPOITypes;
import com.modderg.tameablebeasts.server.entity.TBAnimal;
import com.modderg.tameablebeasts.server.entity.custom.ScarecrowAllayEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public class RaidCropsTameableGoal extends Goal {


    TBAnimal mob;
    int radius;

    BlockPos targetPos;

    public RaidCropsTameableGoal(TBAnimal mob, int radius){
        this.mob = mob;
        this.radius = radius;
        this.checkTimer = mob.getRandom().nextInt(40);
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    int checkTimer;

    @Override
    public boolean canUse() {
        if(checkTimer++ % 40 != 0)
            return false;

        if(mob.isTame())
            return false;

        if(!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(mob.level(), mob))
            return false;

        return !mob.isTame() &&
                hasCropsToRaid() &&
                areNotScarecrowBlocksNearBy() &&
                areNotScareCrowsNearby();
    }

    @Override
    public boolean canContinueToUse() {
        if(checkTimer++ % 40 != 0)
            return true;

        return targetPos != null &&
                mob.level().getBlockState(targetPos).getBlock() instanceof CropBlock
                && areNotScarecrowBlocksNearBy() && areNotScareCrowsNearby();
    }

    @Override
    public void stop() {
        this.targetPos = null;
    }

    int raidTimer = 60;

    @Override
    public void tick() {
        if(--raidTimer < 0)
            this.mob.getNavigation()
                    .moveTo(targetPos.getX(),targetPos.getY(),targetPos.getZ(),1f);

        if(targetPos != null && mob.distanceToSqr(targetPos.getCenter()) <= 2.5){
            mob.triggerAnim("movement", "eat_crop");
            mob.level().destroyBlock(targetPos,false);
            mob.playSound(SoundEvents.GENERIC_EAT);
            targetPos = null;
            raidTimer = 60;
        }
    }

    private static final TargetingConditions TARGETING_CONDITIONS = TargetingConditions.forNonCombat().range(8.0D).ignoreLineOfSight();

    private boolean areNotScareCrowsNearby(){
        List<? extends ScarecrowAllayEntity> list = mob.level().getNearbyEntities(ScarecrowAllayEntity.class, TARGETING_CONDITIONS, this.mob, this.mob.getBoundingBox().inflate(10.0D));
        return list.isEmpty();
    }

    private boolean areNotScarecrowBlocksNearBy(){

        Level level = mob.level();

        if (level instanceof ServerLevel serverLevel) {

            PoiManager poiManager = serverLevel.getPoiManager();

            Optional<BlockPos> poiPos = poiManager.find((p_217376_) ->
                    p_217376_.is(InitPOITypes.SCARECROW_POI.getId()),
                    pos -> true,
                    mob.blockPosition(), 48, PoiManager.Occupancy.ANY);
            return poiPos.isEmpty();
        }
        return false;
    }

    private boolean hasCropsToRaid(){
        BlockPos entityPos = mob.blockPosition();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {

                    BlockPos currentPos = entityPos.offset(x, y, z);

                    BlockState state = mob.level().getBlockState(currentPos);
                    Block currentBlock = state.getBlock();


                    if(currentBlock instanceof CropBlock crop && crop.isMaxAge(state)) {
                        targetPos = currentPos;
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
