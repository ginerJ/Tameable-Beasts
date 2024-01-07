package com.modderg.tameablebeasts.block.custom;

import com.modderg.tameablebeasts.block.BlockEntityInit;
import com.modderg.tameablebeasts.block.entity.EggBlockEntity;
import com.modderg.tameablebeasts.item.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class EggBlock extends BaseEntityBlock {

    private final String species;

    public String getSpecies(){
        return this.species;
    }

    private VoxelShape CUSTOM_SHAPE;

    public EggBlock(Properties p_49224_, String species, int width, int height){
        super(p_49224_);
        this.species = species;
        this.CUSTOM_SHAPE = Block.box((16-width)/2d,0,(16-width)/2d,
                (16-width)/2d + width, height, (16-width)/2d + width);
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

        return createTickerHelper(type, BlockEntityInit.EGG_BLOCK_ENTITY.get(),
                (plevel,pos,state,entity) -> entity.tick(plevel, pos, state));
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return CUSTOM_SHAPE;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState p_49849_, @Nullable LivingEntity player, ItemStack p_49851_) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof EggBlockEntity egg && !player.getUUID().toString().isEmpty()) {
            egg.setOwnerUUID(player.getUUID().toString());
        }
        super.setPlacedBy(level, pos, p_49849_, player, p_49851_);
    }

    @Override
    public void playerDestroy(Level level, Player p_49828_, BlockPos pos, BlockState p_49830_, @Nullable BlockEntity p_49831_, ItemStack p_49832_) {
        level.addFreshEntity(new ItemEntity(level,pos.getX(),pos.getY(),pos.getZ(),
                new ItemStack(ItemInit.EGG_RESTS.get())));
        super.playerDestroy(level, p_49828_, pos, p_49830_, p_49831_, p_49832_);
    }
}
