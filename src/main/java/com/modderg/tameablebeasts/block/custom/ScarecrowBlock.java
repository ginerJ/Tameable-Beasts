package com.modderg.tameablebeasts.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class ScarecrowBlock extends HorizontalDirectionalBlock {
    public ScarecrowBlock(Properties p_54120_) {
        super(p_54120_);
    }

    private static final VoxelShape SHAPE = Stream.of(
            Block.box(2, 17, 2, 14, 29, 14),
            Block.box(6.5, 0, 6.5, 9.5, 17, 9.5),
            Block.box(4, 5, 4.5, 4, 17, 11.5),
            Block.box(12, 5, 4.5, 12, 17, 11.5),
            Block.box(4, 5, 4.5, 12, 17, 4.5),
            Block.box(7, 3, 4.5, 8, 5, 4.5),
            Block.box(5, 3, 4.5, 6, 5, 4.5),
            Block.box(12, 3, 5.375, 12, 5, 6.275),
            Block.box(12, 3, 7.1, 12, 5, 8),
            Block.box(12, 3, 8.875, 12, 5, 9.775),
            Block.box(12, 3, 10.6, 12, 5, 11.5),
            Block.box(4, 3, 4.475, 4, 5, 5.375),
            Block.box(4, 3, 7.975, 4, 5, 8.875),
            Block.box(4, 3, 6.25, 4, 5, 7.15),
            Block.box(4, 3, 9.7, 4, 5, 10.6),
            Block.box(9, 3, 4.5, 10, 5, 4.5),
            Block.box(4, 3, 11.5, 5, 5, 11.5),
            Block.box(6, 3, 11.5, 7, 5, 11.5),
            Block.box(10, 3, 11.5, 11, 5, 11.5),
            Block.box(8, 3, 11.5, 9, 5, 11.5),
            Block.box(4, 5, 11.5, 12, 17, 11.5),
            Block.box(3.8, 12.8, 4.3, 12.2, 14.2, 11.7),
            Block.box(3.8, 7.8, 4.3, 12.2, 9.2, 11.7)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }
}
