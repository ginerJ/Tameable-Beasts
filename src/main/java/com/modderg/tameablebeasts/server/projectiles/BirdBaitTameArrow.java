package com.modderg.tameablebeasts.server.projectiles;

import com.modderg.tameablebeasts.server.entity.EntityInit;
import com.modderg.tameablebeasts.server.entity.custom.ArgentavisEntity;
import com.modderg.tameablebeasts.server.entity.custom.ChikoteEntity;
import com.modderg.tameablebeasts.server.item.ItemInit;
import com.modderg.tameablebeasts.server.tags.TBTags;
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
        super(EntityInit.BIRD_BAIT_ARROW.get(), p_36867_, p_36866_);
    }

    @Override
    void init() {
        pickUp = ItemInit.BIRD_BAIT_ARROW.get();
        particle = ItemInit.BIG_BIRD_BAIT.get();
        chance = 5;
    }

    @Override
    protected boolean canTame(Entity entity){
        return entity.getType().is(TBTags.EntityTypes.TAMED_BY_BIRD_BAIT_ARROW);
    }
}
