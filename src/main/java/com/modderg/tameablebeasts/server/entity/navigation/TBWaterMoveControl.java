package com.modderg.tameablebeasts.server.entity.navigation;

import com.modderg.tameablebeasts.server.entity.abstracts.TBAnimal;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;

public class TBWaterMoveControl extends MoveControl {
    private final TBAnimal tameable;

    public TBWaterMoveControl(TBAnimal p_27501_) {
        super(p_27501_);
        this.tameable = p_27501_;
    }

    public void tick() {
        if (this.tameable.isEyeInFluid(FluidTags.WATER))
            this.tameable.setDeltaMovement(this.tameable.getDeltaMovement().add(0.0D, 0.005D, 0.0D));

        if (this.operation == MoveControl.Operation.MOVE_TO && !this.tameable.getNavigation().isDone()) {
            float f = (float)(this.speedModifier * this.tameable.getAttributeValue(Attributes.MOVEMENT_SPEED));
            this.tameable.setSpeed(Mth.lerp(0.125F, this.tameable.getSpeed(), f));
            double d0 = this.wantedX - this.tameable.getX();
            double d1 = this.wantedY - this.tameable.getY();
            double d2 = this.wantedZ - this.tameable.getZ();
            if (d1 != 0.0D) {
                double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                this.tameable.setDeltaMovement(this.tameable.getDeltaMovement().add(0.0D, (double)this.tameable.getSpeed() * (d1 / d3) * 0.1D, 0.0D));
            }

            if (d0 != 0.0D || d2 != 0.0D) {
                float f1 = (float)(Mth.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                this.tameable.setYRot(this.rotlerp(this.tameable.getYRot(), f1, 90.0F));
                this.tameable.yBodyRot = this.tameable.getYRot();
            }

        } else
            this.tameable.setSpeed(0.0F);
    }
}
