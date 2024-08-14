package com.modderg.tameablebeasts.server.projectiles;

import com.modderg.tameablebeasts.server.entity.EntityIinit;
import com.modderg.tameablebeasts.server.entity.custom.ArgentavisEntity;
import com.modderg.tameablebeasts.server.entity.custom.ChikoteEntity;
import com.modderg.tameablebeasts.server.entity.custom.GrapteranodonEntity;
import com.modderg.tameablebeasts.server.entity.custom.QuetzalcoatlusEntity;
import com.modderg.tameablebeasts.server.item.ItemInit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;

public class PteraMealTameArrow extends AbstractTameArrow {

    public PteraMealTameArrow(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
        super(p_36721_, p_36722_);
    }

    public PteraMealTameArrow(Level p_36866_, LivingEntity p_36867_) {
        super(EntityIinit.PTERA_MEAL_ARROW.get(), p_36867_, p_36866_);
    }

    @Override
    void init() {
        pickUp = ItemInit.PTERA_MEAL_ARROW.get();
        particle = ItemInit.PTERANODON_MEAL.get();
        chance = 5;
    }

    @Override
    protected boolean canTame(Entity entity){
        return entity instanceof QuetzalcoatlusEntity || entity instanceof GrapteranodonEntity;
    }
}
