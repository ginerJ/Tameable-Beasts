package com.modderg.tameablebeasts.item;

import com.modderg.tameablebeasts.entities.QuetzalcoatlusEntity;
import com.modderg.tameablebeasts.init.ModEntityClass;
import com.modderg.tameablebeasts.particles.TameableParticles;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class QuetzalEggItem extends Item {
    public QuetzalEggItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        QuetzalcoatlusEntity quetzal = ModEntityClass.QUETZALCOATLUS.get().create(level);
        quetzal.setPos(new Vec3(context.getClickedPos().getX(), context.getClickedPos().getY() + 1, context.getClickedPos().getZ()));
        quetzal.setOwnerUUID(context.getPlayer().getUUID());
        quetzal.setTame(true);
        quetzal.setAge(-2400);
        level.addFreshEntity(quetzal);
        context.getItemInHand().shrink(1);
        level.addParticle(TameableParticles.CRACKED_EGG_PARTICLES.get(), quetzal.getRandomX(1.0D), quetzal.getRandomY() + 0.5D, quetzal.getRandomZ(1.0D),
                quetzal.getRandom().nextGaussian() * 0.02D, quetzal.getRandom().nextGaussian() * 0.02D, quetzal.getRandom().nextGaussian() * 0.02D);
        level.addParticle(TameableParticles.CRACKED_EGG_PARTICLES.get(), quetzal.getRandomX(1.0D), quetzal.getRandomY() + 0.5D, quetzal.getRandomZ(1.0D),
                quetzal.getRandom().nextGaussian() * 0.02D, quetzal.getRandom().nextGaussian() * 0.02D, quetzal.getRandom().nextGaussian() * 0.02D);
        level.addParticle(TameableParticles.CRACKED_EGG_PARTICLES.get(), quetzal.getRandomX(1.0D), quetzal.getRandomY() + 0.5D, quetzal.getRandomZ(1.0D),
                quetzal.getRandom().nextGaussian() * 0.02D, quetzal.getRandom().nextGaussian() * 0.02D, quetzal.getRandom().nextGaussian() * 0.02D);
        level.addParticle(TameableParticles.CRACKED_EGG_PARTICLES.get(), quetzal.getRandomX(1.0D), quetzal.getRandomY() + 0.5D, quetzal.getRandomZ(1.0D),
                quetzal.getRandom().nextGaussian() * 0.02D, quetzal.getRandom().nextGaussian() * 0.02D, quetzal.getRandom().nextGaussian() * 0.02D);
        level.addParticle(TameableParticles.CRACKED_EGG_PARTICLES.get(), quetzal.getRandomX(1.0D), quetzal.getRandomY() + 0.5D, quetzal.getRandomZ(1.0D),
                quetzal.getRandom().nextGaussian() * 0.02D, quetzal.getRandom().nextGaussian() * 0.02D, quetzal.getRandom().nextGaussian() * 0.02D);
        level.addParticle(TameableParticles.CRACKED_EGG_PARTICLES.get(), quetzal.getRandomX(1.0D), quetzal.getRandomY() + 0.5D, quetzal.getRandomZ(1.0D),
                quetzal.getRandom().nextGaussian() * 0.02D, quetzal.getRandom().nextGaussian() * 0.02D, quetzal.getRandom().nextGaussian() * 0.02D);
        level.addParticle(TameableParticles.CRACKED_EGG_PARTICLES.get(), quetzal.getRandomX(1.0D), quetzal.getRandomY() + 0.5D, quetzal.getRandomZ(1.0D),
                quetzal.getRandom().nextGaussian() * 0.02D, quetzal.getRandom().nextGaussian() * 0.02D, quetzal.getRandom().nextGaussian() * 0.02D);
        return super.useOn(context);
    }
}
