package com.modderg.tameablebeasts.server.enchantments;

import com.modderg.tameablebeasts.server.entity.FlyingBeetleEntity;
import com.modderg.tameablebeasts.registry.TBItemRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

import java.util.stream.IntStream;

public class SwarmEnchantment extends Enchantment {
    public SwarmEnchantment(Rarity p_44676_, EnchantmentCategory p_44677_, EquipmentSlot... p_44678_) {
        super(p_44676_, p_44677_, p_44678_);
    }

    @Override
    public void doPostAttack(@NotNull LivingEntity attacker, @NotNull Entity target, int level) {

        if (target instanceof LivingEntity livingTarget && attacker instanceof Player player)
            if (player.getAttackStrengthScale(0.5f) >= 1.0f)
                IntStream.range(0, level).forEach(i -> {
                    FlyingBeetleEntity.spawnDroneWithTarget(attacker, livingTarget);
                    FlyingBeetleEntity.spawnDroneWithTarget(attacker, livingTarget);
                });

        super.doPostAttack(attacker, target, level);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack item) {
         return item.is(TBItemRegistry.BUG_SWORD.get());
    }

    @Override
    public boolean canEnchant(@NotNull ItemStack item) {
        return item.is(TBItemRegistry.BUG_SWORD.get());
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
