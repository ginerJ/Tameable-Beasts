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

public class EggBlockItem extends BlockItem implements GeoItem {

    private String species;

    public EggBlockItem(Block p_40565_, Properties p_40566_, String species) {
        super(p_40565_, p_40566_);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
        this.species = species;
    }

    public String getSpecies() {
        return species;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private EggBlockItemRenderer renderer;
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) {
                    renderer = new EggBlockItemRenderer();
                }
                return this.renderer;
            }
        });
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

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    private AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }
}
