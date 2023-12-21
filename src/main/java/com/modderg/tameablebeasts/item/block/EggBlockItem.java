package com.modderg.tameablebeasts.item.block;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
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
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    private AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }
}
