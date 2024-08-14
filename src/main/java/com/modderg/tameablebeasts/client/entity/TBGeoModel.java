package com.modderg.tameablebeasts.client.entity;

import com.modderg.tameablebeasts.server.entity.FlyingTBAnimal;
import com.modderg.tameablebeasts.server.entity.TBAnimal;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public abstract class TBGeoModel<T extends GeoAnimatable> extends GeoModel<T> {

    protected void setBoneRot(CoreGeoBone bone, Vec3 rotation) {
        bone.setRotX((float) rotation.x);
        bone.setRotY((float) rotation.y);
        bone.setRotZ((float) rotation.z);
    }

    protected void setLookAngeRots(TBAnimal animatable, AnimationState<T> animationState) {
        setLookAngeRotsAdditionalZ(animatable, animationState, 0);
    }

    protected void setLookAngeRotsAdditionalZ(TBAnimal animatable, AnimationState<T> animationState, float additionalZ) {
        CoreGeoBone lookAngle = getAnimationProcessor().getBone("look_angle");

        if(lookAngle != null){
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            Vec3 headRot = new Vec3(entityData.headPitch() * Mth.DEG_TO_RAD,entityData.netHeadYaw() * Mth.DEG_TO_RAD, additionalZ);

            this.setBoneRot(lookAngle, headRot);
        }
    }

    protected void setHeadAndBodyRots(FlyingTBAnimal animatable, AnimationState<T> animationState) {
        CoreGeoBone bodyBone = getAnimationProcessor().getBone("fly_point");

        if (bodyBone != null && animatable.isFlying()) {
            //bod rot
            Vec3 movement = animatable.getDeltaMovement();

            float yaw = animatable.getYRot() * Mth.DEG_TO_RAD;

            double sidewaysMotion = Math.cos(yaw) * movement.x + Math.sin(yaw) * movement.z;

            sidewaysMotion = Mth.clamp(sidewaysMotion*5, -0.3, 0.3);

            double currRot = bodyBone.getRotZ();

            float actualTilt = (float) (currRot + (sidewaysMotion - currRot)*0.025);

            bodyBone.setRotY(actualTilt/3);
            bodyBone.setRotZ(actualTilt);

            //head rot
            setLookAngeRotsAdditionalZ(animatable, animationState, -actualTilt);
        } else
            setLookAngeRots(animatable, animationState);
    }
}
