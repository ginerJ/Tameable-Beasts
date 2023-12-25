package com.modderg.tameablebeasts.block;

import com.modderg.tameablebeasts.TameableBeast;
import com.modderg.tameablebeasts.block.custom.EggBlock;
import com.modderg.tameablebeasts.block.custom.ScarecrowBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;
import java.util.function.Supplier;

import static com.modderg.tameablebeasts.item.ItemInit.ITEMS;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TameableBeast.MOD_ID);

    public static final RegistryObject<Block> SCARECROW_BLOCK = register("scarecrow_block", () -> new ScarecrowBlock(BlockBehaviour.Properties.of()
            .strength(3.0F).sound(SoundType.WOOD).noOcclusion().dynamicShape()),
            object -> () -> new BlockItem(object.get(), new Item.Properties()));

    public static final RegistryObject<Block> FUR_BLOCK = register("racoon_fur_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BROWN_WOOL)),
            object -> () -> new BlockItem(object.get(), new Item.Properties()));

    public static final RegistryObject<Block> QUETZAL_EGG_BLOCK = BLOCKS.register("quetzalcoatlus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG),"Quetzalcoatlus"));

    public static final RegistryObject<Block> CHIKOTE_EGG_BLOCK = BLOCKS.register("chikote_egg",
            () -> new EggBlock(BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG),"tameable_Chikote"));

    public static final RegistryObject<Block> ROLY_POLY_EGG_BLOCK = BLOCKS.register("roly_poly_egg",
            () -> new EggBlock(BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG),"giant_Roly_Poly"));

    public static final RegistryObject<Block> FLYING_BEETLE_EGG = BLOCKS.register("beetle_egg",
            () -> new EggBlock(BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG),"tameable_Beetle"));

    private static <T extends Block> RegistryObject<T> registerBlock(final String name, final Supplier<? extends T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> register(final String name, final Supplier<? extends T> block, Function<RegistryObject<T>, Supplier<? extends Item>> item) {
        RegistryObject<T> obj = registerBlock(name, block);
        ITEMS.register(name, item.apply(obj));
        return obj;
    }
}
