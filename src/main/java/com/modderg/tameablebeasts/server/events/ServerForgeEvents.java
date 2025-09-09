package com.modderg.tameablebeasts.server.events;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.registry.*;
import com.modderg.tameablebeasts.server.ModCommonConfigs;
import com.modderg.tameablebeasts.server.entity.BeetleDrone;
import com.modderg.tameablebeasts.server.entity.ShinyBeetleEntity;
import com.modderg.tameablebeasts.server.entity.FurGolemEntity;
import com.modderg.tameablebeasts.server.entity.abstracts.TBAnimal;
import com.modderg.tameablebeasts.server.packet.StoCEntityInvSyncPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

import static com.modderg.tameablebeasts.registry.TBAdvancementRegistry.FUR_GOLEM;

@Mod.EventBusSubscriber(modid = TameableBeasts.MOD_ID)
public class ServerForgeEvents {

    @SubscribeEvent
    public static void onEntityJoin(final EntityJoinLevelEvent event){

        if(event.getEntity() instanceof TBAnimal tbAnimal && tbAnimal.isTame()){
            event.getLevel().getServer().execute(
                    () ->{
                        List<ItemStack> copy = new ArrayList<>();

                        for (int i = 0; i < tbAnimal.getInventory().getSlots(); i++) {
                            ItemStack stack = tbAnimal.getInventory().getStackInSlot(i);
                            if (!stack.isEmpty())
                                copy.add(stack.copy());
                        }

                        TBPacketRegistry.sendToAll(new StoCEntityInvSyncPacket(tbAnimal.getId(), copy));
                    }
            );
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(final LivingHurtEvent event){
        LivingEntity player = event.getEntity();

        if(player == null || !(event.getSource().getEntity() instanceof LivingEntity attacker))
            return;

        int enchantLevel = 0;

        for (ItemStack weapon : player.getHandSlots())
            if (weapon.getItem() instanceof ShieldItem shield && player.getUseItem().is(shield))
                enchantLevel += EnchantmentHelper.getTagEnchantmentLevel(TBEnchantmentRegistry.SWARM_ENCHANTMENT.get(), weapon);

        if (enchantLevel == 0)
            for (ItemStack armor : player.getArmorSlots())
                enchantLevel += EnchantmentHelper.getTagEnchantmentLevel(TBEnchantmentRegistry.SWARM_ENCHANTMENT.get(), armor);

        for (int i = 0; i < enchantLevel; i++)
            if (player.getRandom().nextInt(3) != 1){
                ShinyBeetleEntity.spawnDroneWithTarget(player, attacker);
                ShinyBeetleEntity.spawnDroneWithTarget(player, attacker);
            }
    }

    @SubscribeEvent
    public static void onDeath(final LivingDeathEvent event){
        if(event.getEntity() instanceof AbstractIllager illager)
            if(event.getEntity().getRandom().nextInt(100) <= 5)
                illager.spawnAtLocation(TBItemRegistry.PURPLE_ALLAY.get());

        if(event.getEntity().getMobType().equals(MobType.ARTHROPOD) && !(event.getEntity() instanceof BeetleDrone))
            if(event.getEntity().getRandom().nextInt(100) <= ModCommonConfigs.ARTHROPOD_TRIM_DROP_CHANCE.get())
                event.getEntity().spawnAtLocation(TBItemRegistry.ARTHROPOD_SMITHING_TEMPLATE.get());
    }

    @SubscribeEvent
    public static void onPlace(final BlockEvent.EntityPlaceEvent event){
        if(event.getEntity() == null)
            return;

        Level level = event.getEntity().level();
        BlockState blockState = event.getPlacedBlock();
        BlockPos pos = event.getPos();

        if(blockState.is(TBBlockRegistry.FUR_BLOCK.get())){
            if(level.getBlockState(pos.below()).is(TBBlockRegistry.FUR_BLOCK.get()) &&
                    level.getBlockState(pos.above()).is(Blocks.CARVED_PUMPKIN)){
                level.destroyBlock(pos, false);
                level.destroyBlock(pos.above(), false);
                level.destroyBlock(pos.below(), false);

                summonGolem(level, event.getPos().below(), event);
            }
            if(level.getBlockState(pos.above()).is(TBBlockRegistry.FUR_BLOCK.get()) &&
                    level.getBlockState(pos.above().above()).is(Blocks.CARVED_PUMPKIN)){
                level.destroyBlock(pos, false);
                level.destroyBlock(pos.above(2), false);
                level.destroyBlock(pos.above(), false);

                summonGolem(level, event.getPos(), event);
            }
        }
        if(blockState.is(Blocks.CARVED_PUMPKIN) &&
                level.getBlockState(pos.below()).is(TBBlockRegistry.FUR_BLOCK.get()) &&
                    level.getBlockState(pos.below().below()).is(TBBlockRegistry.FUR_BLOCK.get())){
            level.destroyBlock(pos, false);
            level.destroyBlock(pos.below(), false);
            level.destroyBlock(pos.below(2), false);

            summonGolem(level, event.getPos().below(2), event);
        }
    }

    public static void summonGolem(Level level, BlockPos pos, BlockEvent.EntityPlaceEvent event){

        if(event.getEntity() instanceof ServerPlayer player)
            TBAdvancementRegistry.grantAdvancement(player, FUR_GOLEM);

        FurGolemEntity golem = new FurGolemEntity(TBEntityRegistry.FUR_GOLEM.get(),level);
        golem.setPos(pos.getX(), pos.getY(), pos.getZ());
        level.addFreshEntity(golem);
    }
}
