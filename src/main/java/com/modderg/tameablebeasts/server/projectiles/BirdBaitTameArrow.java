package com.modderg.tameablebeasts.server.projectiles;

import com.modderg.tameablebeasts.registry.TBEntityRegistry;
import com.modderg.tameablebeasts.registry.TBItemRegistry;
import com.modderg.tameablebeasts.registry.TBTagRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;

public class BirdBaitTameArrow extends AbstractTameArrow {

    public BirdBaitTameArrow(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
        super(p_36721_, p_36722_);
    }

    public BirdBaitTameArrow(Level p_36866_, LivingEntity p_36867_) {
        super(TBEntityRegistry.BIRD_BAIT_ARROW.get(), p_36867_, p_36866_);
    }

    @Override
    void init() {
        pickUp = TBItemRegistry.BIRD_BAIT_ARROW.get();
        particle = TBItemRegistry.BIG_BIRD_BAIT.get();
        chance = 5;
    }

    @Override
    protected boolean canTame(Entity entity){
        return entity.getType().is(TBTagRegistry.EntityTypes.TAMED_BY_BIRD_BAIT_ARROW);
    }
}
