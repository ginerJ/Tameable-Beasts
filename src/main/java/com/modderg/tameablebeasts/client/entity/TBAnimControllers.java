package com.modderg.tameablebeasts.client.entity;

import com.modderg.tameablebeasts.server.entity.abstracts.FlyingRideableTBAnimal;
import com.modderg.tameablebeasts.server.entity.abstracts.FlyingTBAnimal;
import com.modderg.tameablebeasts.server.entity.abstracts.RideableTBAnimal;
import com.modderg.tameablebeasts.server.entity.abstracts.TBAnimal;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public final class TBAnimControllers {

    public static void setLoopAnimation(AnimationState<?> event, String animationName){
        event.getController().setAnimation(RawAnimation.begin().then(animationName, Animation.LoopType.LOOP));
    }

    public static <T extends TBAnimal & GeoEntity> PlayState groundState(T entity, AnimationState<?> event) {
        if(event.isMoving() || entity.onClimbable())
            setLoopAnimation(event, entity.isRunning() ? "run" : "walk");
        else if(entity.isInSittingPose())
            setLoopAnimation(event, "sit");
        else
            setLoopAnimation(event, "idle");

        return PlayState.CONTINUE;
    }

    public static <T extends FlyingTBAnimal & GeoEntity> PlayState flyState(T entity, AnimationState<T> event) {
        if(entity.isStill())
            setLoopAnimation(event, "fly_idle");
        else
            setLoopAnimation(event, "fly");

        return PlayState.CONTINUE;
    }

    public static <T extends RideableTBAnimal & GeoEntity> PlayState vehicleState(T entity, AnimationState<T> event) {
        if(entity.isVehicle()){
            if(entity.moving)
                event.getController().setAnimation(RawAnimation.begin().then("run", Animation.LoopType.LOOP));
            else
                event.getController().setAnimation(RawAnimation.begin().then("sit", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        return groundState(entity, event);
    }

    public static <T extends RideableTBAnimal & GeoEntity> AnimationController<T> vehicleController(T entity) {
        return new AnimationController<>(entity,"movement", 2, event -> vehicleState(entity, event));
    }

    public static <T extends TBAnimal & GeoEntity> AnimationController<T> groundController(T entity) {
        return new AnimationController<>(entity,"movement", 5, event -> groundState(entity, event));
    }

    public static <T extends FlyingRideableTBAnimal & GeoEntity> AnimationController<T> flyGliderController(T entity) {
        return new AnimationController<>(entity,"movement", 5, event ->{

            if(entity.isFlying() && !entity.isInSittingPose()) {
                if(entity.isControlledByLocalInstance()) {
                    if (entity.downInput)
                        setLoopAnimation(event, "glide_down");
                    else if (entity.upInput)
                        setLoopAnimation(event, "fly");
                    else if (entity.isStill())
                        setLoopAnimation(event, "fly_idle");
                    else
                        setLoopAnimation(event, "glide");
                } else {
                    if (entity.isStill())
                        setLoopAnimation(event, "fly_idle");
                    else if (entity.getDeltaMovement().y < (entity.isControlledByLocalInstance() ? 0 : -0.2))
                        setLoopAnimation(event, "glide");
                    else
                        setLoopAnimation(event, "fly");
                }
                return PlayState.CONTINUE;

            }
            return groundState( entity, event);
        });
    }

    public static <T extends FlyingTBAnimal & GeoEntity> AnimationController<T> flyWalkingController(T entity) {
        return new AnimationController<>(entity,"movement", 5, event ->{
            if(entity.isFlying())
                return flyState(entity, event);

            return groundState( entity, event);
        });
    }

    public static <T extends FlyingTBAnimal & GeoEntity> AnimationController<T> flyOnlyController(T entity) {
        return new AnimationController<>(entity,"movement", 10, event ->{
            if(!entity.isInSittingPose() || !entity.isStill())
                return flyState(entity, event);

            setLoopAnimation(event, "sit");
            return PlayState.CONTINUE;
        });
    }
}
