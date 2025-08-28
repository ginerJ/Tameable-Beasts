package com.modderg.tameablebeasts.client.entity;

import com.modderg.tameablebeasts.server.entity.abstracts.TBAnimal;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.molang.MolangParser;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import static com.modderg.tameablebeasts.constants.TBConstants.HEAD_X_QUERY;
import static com.modderg.tameablebeasts.constants.TBConstants.HEAD_Y_QUERY;

public abstract class TBGeoModel<T extends TBAnimal> extends GeoModel<T> {

    @Override
    public void setCustomAnimations(T animatable, long instanceId, AnimationState<T> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        float pt = animationState.getPartialTick();

        float maxYaw = 45.0F;
        float initialYaw = animatable.getViewYRot(pt) - Mth.lerp(pt, animatable.yBodyRotO, animatable.yBodyRot);

        animatable.cachedHeadYaw = Mth.clamp(initialYaw, -maxYaw, maxYaw);
        animatable.cachedHeadPitch = animatable.getViewXRot(pt);
    }

    @Override
    public void applyMolangQueries(T animatable, double animTime) {
        super.applyMolangQueries(animatable, animTime);

        MolangParser parser = MolangParser.INSTANCE;

        parser.setValue(HEAD_Y_QUERY, () -> animatable.cachedHeadYaw);
        parser.setValue(HEAD_X_QUERY, () -> animatable.cachedHeadPitch);
    }
}
