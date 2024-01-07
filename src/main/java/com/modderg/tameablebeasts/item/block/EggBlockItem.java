package com.modderg.tameablebeasts.item.block;

import com.modderg.tameablebeasts.block.entity.EggBlockEntity;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;

import java.util.function.Consumer;

public class EggBlockItem extends BlockItem {

    public EggBlockItem(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }

    @Override
    public @Nullable CompoundTag getShareTag(ItemStack stack) {
        CompoundTag nbt = super.getShareTag(stack);
        if (nbt == null) {
            nbt = new CompoundTag();
        }
        nbt.putInt("textureId", 0);
        return nbt;
    }

    public void setTextureId(ItemStack stack, int value) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putInt("textureId", value);
    }

    public int getTextureId(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        return nbt.getInt("textureId");
    }

    @Override
    public InteractionResult place(BlockPlaceContext context) {

        InteractionResult interactionResult = super.place(context);

        BlockPos pos = context.getClickedPos();
        if(context.getLevel().getBlockEntity(pos) instanceof EggBlockEntity egg)
            egg.setTextureId(this.getTextureId(context.getItemInHand()));

        return interactionResult;
    }
}
