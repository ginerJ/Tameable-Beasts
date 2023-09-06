package com.modderg.tameablebeasts.block;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.item.CreativeTameableTab;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;
import java.util.function.Supplier;

import static com.modderg.tameablebeasts.item.ItemInit.ITEMS;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TameableBeast.MODID);

    public static final RegistryObject<Block> SCARECROW_BLOCK = register("scarecrow_block", () -> new ScarecrowBlock(BlockBehaviour.Properties.m_284310_()
            .strength(3.0F).sound(SoundType.WOOD)),
            object -> () -> new BlockItem(object.get(), new Item.Properties()));


    private static <T extends Block> RegistryObject<T> registerBlock(final String name,
                                                                     final Supplier<? extends T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> register(final String name, final Supplier<? extends T> block,
                                                                Function<RegistryObject<T>, Supplier<? extends Item>> item) {
        RegistryObject<T> obj = registerBlock(name, block);
        ITEMS.register(name, item.apply(obj));
        return obj;
    }
}
