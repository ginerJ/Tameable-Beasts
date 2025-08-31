package com.modderg.tameablebeasts.server.enchantment;

import com.modderg.tameablebeasts.registry.TBItemRegistry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

import static com.modderg.tameablebeasts.registry.TBTagRegistry.Items.ENCHANTABLE_STING;

public class StingEnchantment extends Enchantment {
    public StingEnchantment(Rarity p_44676_, EnchantmentCategory p_44677_, EquipmentSlot... p_44678_) {
        super(p_44676_, p_44677_, p_44678_);
    }

    @Override
    public void doPostAttack(@NotNull LivingEntity attacker, @NotNull Entity target, int level) {

        if (target instanceof LivingEntity livingTarget && attacker instanceof Player player){
            livingTarget.addEffect(new MobEffectInstance(MobEffects.POISON, (int) (80 * level * player.getAttackStrengthScale(0.5f))));
            livingTarget.playSound(SoundEvents.BEE_STING, 1.0F, 1.0F);
        }

        super.doPostAttack(attacker, target, level);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack item) {
         return item.is(ENCHANTABLE_STING);
    }

    @Override
    public boolean canEnchant(@NotNull ItemStack item) {
        return item.is(ENCHANTABLE_STING);
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }
}
