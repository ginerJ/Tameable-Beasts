package com.modderg.tameablebeasts.server.block;

import com.modderg.tameablebeasts.registry.TBBlockEntityRegistry;
import com.modderg.tameablebeasts.server.entity.abstracts.TBAnimal;
import com.modderg.tameablebeasts.server.entity.FurGolemEntity;
import com.modderg.tameablebeasts.registry.TBItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
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
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EggBlock<T extends TBAnimal> extends BaseEntityBlock {

    private final String species;
    private final RegistryObject<EntityType<T>> babyType;

    public String getSpecies(){
        return this.species;
    }

    private VoxelShape CUSTOM_SHAPE;

    public EggBlock(Properties p_49224_, String species, RegistryObject<EntityType<T>> babyType, double width, double height){
        super(p_49224_);
        this.babyType = babyType;
        this.species = species;
        this.CUSTOM_SHAPE = Block.box((16-width)/2d,0,(16-width)/2d,
                (16-width)/2d + width, height, (16-width)/2d + width);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new EggBlockEntity(p_153215_, p_153216_, species, babyType);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState p_153213_, BlockEntityType<T> type) {
        if(level.isClientSide)
            return null;

        return createTickerHelper(type, TBBlockEntityRegistry.EGG_BLOCK_ENTITY.get(),
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
        if (blockEntity instanceof EggBlockEntity egg && player!= null)
            egg.setOwnerUUID(player.getUUID().toString());

        super.setPlacedBy(level, pos, p_49849_, player, p_49851_);
    }

    @Override
    public void playerDestroy(Level level, @NotNull Player p_49828_, BlockPos pos, @NotNull BlockState p_49830_, @Nullable BlockEntity p_49831_, @NotNull ItemStack p_49832_) {
        level.addFreshEntity(new ItemEntity(level,pos.getX(),pos.getY(),pos.getZ(),
                new ItemStack(TBItemRegistry.EGG_RESTS.get())));
        super.playerDestroy(level, p_49828_, pos, p_49830_, p_49831_, p_49832_);
    }

    public void fallOn(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull Entity entity, float p_154849_) {
        if (this.canDestroyEgg(level, entity))
            if (!level.isClientSide && level.random.nextInt(3) == 0 && state.is(this)) {
                level.playSound(null, pos, SoundEvents.TURTLE_EGG_BREAK,
                        SoundSource.BLOCKS, 0.7F, 0.9F + level.random.nextFloat() * 0.2F);
                level.destroyBlock(pos, false);
            }

        super.fallOn(level, state, pos, entity, p_154849_);
    }

    private boolean canDestroyEgg(Level p_57768_, Entity p_57769_) {
        if (!(p_57769_ instanceof FurGolemEntity) && !(p_57769_ instanceof TBAnimal))
            if (!(p_57769_ instanceof LivingEntity))
                return false;
            else
                return p_57769_ instanceof Player || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(p_57768_, p_57769_);
        else return false;
    }
}
