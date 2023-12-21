package com.modderg.tameablebeasts.block.custom;

import com.modderg.tameablebeasts.block.BlockEntitiesInit;
import com.modderg.tameablebeasts.block.entity.EggBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class EggBlock extends BaseEntityBlock {

    private final String species;

    public String getSpecies(){
        return this.species;
    }

    public EggBlock(Properties p_49224_, String species){
        super(p_49224_);
        this.species = species;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new EggBlockEntity(p_153215_, p_153216_, species);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState p_153213_, BlockEntityType<T> type) {
        if(level.isClientSide){
            return null;
        }

        return createTickerHelper(type, BlockEntitiesInit.EGG_BLOCK_ENTITY.get(),
                (plevel,pos,state,entity) -> entity.tick(plevel, pos, state));
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState p_49849_, @Nullable LivingEntity player, ItemStack p_49851_) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof EggBlockEntity egg && !player.getUUID().toString().isEmpty()) {
            egg.setOwnerUUID(player.getUUID().toString());
        }
        super.setPlacedBy(level, pos, p_49849_, player, p_49851_);
    }
}
