package com.modderg.tameablebeasts.server.item.block;

import com.modderg.tameablebeasts.server.block.EggBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

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
