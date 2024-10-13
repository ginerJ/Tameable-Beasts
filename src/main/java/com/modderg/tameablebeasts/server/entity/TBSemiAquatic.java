package com.modderg.tameablebeasts.server.entity;

import com.modderg.tameablebeasts.server.entity.goals.TBFollowOwnerGoal;
import com.modderg.tameablebeasts.server.entity.navigation.TBGroundPathNavigation;
import com.modderg.tameablebeasts.server.entity.navigation.TBWaterMoveControl;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

import java.util.LinkedList;

public interface TBSemiAquatic {

    default boolean isAquatic(){
        return ((Mob) this).getMoveControl() instanceof SmoothSwimmingMoveControl;
    }

    default void switchNavigation(){

        if(((Mob)this).getMoveControl() instanceof SmoothSwimmingMoveControl){
            setMoveControl(new MoveControl((TBAnimal) this));
            setPathNavigation(new GroundPathNavigation((Mob) this, this.level()));
        } else {
            setMoveControl(new SmoothSwimmingMoveControl((Mob)this, 85, 10, 0.02F, 0.1F, false));
            setPathNavigation(new WaterBoundPathNavigation((Mob) this, this.level()));
        }

        getFollowOwnerGoal().refreshNavigatorPath();
    }

    default void initPathAndMoveControls(){
        setPathfindingMalus(BlockPathTypes.WATER, 0.0F);

        switchNavigation();
    }

    Level level();
    TBFollowOwnerGoal getFollowOwnerGoal();
    void setMoveControl(MoveControl control);
    void setPathNavigation(PathNavigation control);
    void setPathfindingMalus(BlockPathTypes p_21442_, float p_21443_);
}
