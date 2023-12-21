package com.modderg.tameablebeasts.events;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.item.ItemInit;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TameableBeast.MOD_ID)
public class ServerEvents {

    @SubscribeEvent
    public static void onDeath(final LivingDeathEvent event){
        if(event.getEntity() instanceof AbstractIllager){
            if(event.getEntity().getRandom().nextInt(100) <= 5){
                Level level = event.getEntity().getLevel();
                Vec3 vec3 = event.getEntity().position();
                ItemEntity item = new ItemEntity(level, vec3.x, vec3.y, vec3.z, new ItemStack(ItemInit.PURPLE_ALLAY.get()));
                level.addFreshEntity(item);
            }
        }

    }
}
