package com.modderg.tameablebeasts.server.entity;

import com.modderg.tameablebeasts.server.entity.goals.TBFollowOwnerGoal;
import com.modderg.tameablebeasts.server.entity.navigation.TBGroundPathNavigation;
import com.modderg.tameablebeasts.server.entity.navigation.TBWaterMoveControl;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

import java.util.LinkedList;

public interface TBSemiAquatic {

    LinkedList<MoveControl> moveControlRotation = new LinkedList<>();
    LinkedList<PathNavigation> pathNavigationRotation = new LinkedList<>();

    default boolean isPushedByFluid() {
        return false;
    }

    default PathNavigation createNavigation(Level p_21480_) {
        return new WaterBoundPathNavigation((Mob) this, level());
    }

    default boolean getSwimming() {
        return this.moveControlRotation.getFirst() instanceof TBWaterMoveControl;
    }

    default void switchNavigation(){

        moveControlRotation.addFirst(moveControlRotation.removeLast());
        pathNavigationRotation.addFirst(pathNavigationRotation.removeLast());

        setMoveControl(moveControlRotation.getFirst());
        setPathNavigation(pathNavigationRotation.getFirst());

        getFollowOwnerGoal().refreshNavigatorPath();
    }

    default void initPathAndMoveControls(){
        setPathfindingMalus(BlockPathTypes.WATER, 0.0F);

        moveControlRotation.add(new TBWaterMoveControl((TBAnimal) this));
        moveControlRotation.add(new MoveControl((Mob) this));

        pathNavigationRotation.add(new WaterBoundPathNavigation((Mob) this, this.level()));
        pathNavigationRotation.add(new TBGroundPathNavigation((Mob) this, this.level()));

        setMoveControl(moveControlRotation.getFirst());
    }

    Level level();
    TBFollowOwnerGoal getFollowOwnerGoal();
    void setMoveControl(MoveControl control);
    void setPathNavigation(PathNavigation control);
    void setPathfindingMalus(BlockPathTypes p_21442_, float p_21443_);
}
