package com.modderg.tameablebeasts.client.entity;

import com.modderg.tameablebeasts.server.entity.abstracts.TBAnimal;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
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

    protected void setLookAngle(TBAnimal animatable, AnimationState<T> animationState) {
        CoreGeoBone lookAngle = getAnimationProcessor().getBone("look_angle");

        if(lookAngle != null){
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            Vec3 headRot = new Vec3(entityData.headPitch() * Mth.DEG_TO_RAD,entityData.netHeadYaw() * Mth.DEG_TO_RAD, 0);

            this.setBoneRot(lookAngle, headRot);
        }
    }
}
