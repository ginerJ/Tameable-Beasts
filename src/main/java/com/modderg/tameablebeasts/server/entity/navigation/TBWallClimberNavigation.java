package com.modderg.tameablebeasts.server.entity.navigation;

import com.modderg.tameablebeasts.server.entity.abstracts.TBAnimal;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.level.Level;


public class TBWallClimberNavigation extends WallClimberNavigation {
    public TBWallClimberNavigation(TBAnimal p_26580_, Level p_26581_) {
        super(p_26580_, p_26581_);
        animal = p_26580_;
    }

    TBAnimal animal;

    @Override
    public boolean moveTo(Entity p_26583_, double p_26584_) {
        if (animal.isOrderedToSit())
            return false;
        return super.moveTo(p_26583_, p_26584_);
    }

    @Override
    public void tick() {
        if(animal.isOrderedToSit())
            return;
        super.tick();
    }
}
