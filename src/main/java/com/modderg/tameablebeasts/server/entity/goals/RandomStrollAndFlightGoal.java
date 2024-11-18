package com.modderg.tameablebeasts.server.entity.goals;

import com.modderg.tameablebeasts.server.entity.FlyingTBAnimal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class RandomStrollAndFlightGoal extends WaterAvoidingRandomStrollGoal {

    FlyingTBAnimal flyingMob;

    public RandomStrollAndFlightGoal(FlyingTBAnimal p_25734_, double p_25735_) {
        super(p_25734_, p_25735_);
        this.flyingMob = p_25734_;
    }

    @Nullable
    @Override
    protected Vec3 getPosition() {
        if(!flyingMob.getIsFlying())
            return super.getPosition();
        Vec3 vec3 = this.mob.getViewVector(0.0F);
        Vec3 vec31 = HoverRandomPos.getPos(this.mob, 8, 7, vec3.x, vec3.z, ((float)Math.PI / 2F), 3, 1);
        return vec31 != null ? vec31 : AirAndWaterRandomPos.getPos(this.mob, 8, 4, -2, vec3.x, vec3.z, (float)Math.PI / 2F);
    }

}
