package com.modderg.tameablebeasts.events;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.block.BlockInit;
import com.modderg.tameablebeasts.item.ItemInit;
import com.modderg.tameablebeasts.particles.TameableParticles;
import com.modderg.tameablebeasts.particles.custom.CitrineParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.IModBusEvent;

@Mod.EventBusSubscriber(modid = TameableBeast.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
}
