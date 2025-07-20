package com.modderg.tameablebeasts.registry;

import com.modderg.tameablebeasts.TameableBeasts;
import com.modderg.tameablebeasts.server.block.EggBlock;
import com.modderg.tameablebeasts.server.block.ScarecrowBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirtPathBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;
import java.util.function.Supplier;

import static com.modderg.tameablebeasts.registry.TBEntityRegistry.*;
import static com.modderg.tameablebeasts.registry.TBItemRegistry.FUR;
import static com.modderg.tameablebeasts.registry.TBItemRegistry.TB_ITEMS;

public class TBBlockRegistry {
    public static final DeferredRegister<Block> TB_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TameableBeasts.MOD_ID);

    //blocks that have block item
    public static final RegistryObject<Block> SCARECROW_BLOCK = TB_BLOCKS.register("scarecrow_block",
            () -> new ScarecrowBlock(BlockBehaviour.Properties.of()
                    .strength(3.0F).sound(SoundType.WOOD).noOcclusion().dynamicShape()));

    public static final RegistryObject<Block> FUR_BLOCK = TB_BLOCKS.register("racoon_fur_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BROWN_WOOL)));

    public static final RegistryObject<Block> ASPHALTED_DIRT = TB_BLOCKS.register("asphalted_dirt",
            () -> new DirtPathBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DIRT).strength(0.65F).sound(SoundType.GRASS)
                    .isViewBlocking(TBBlockRegistry::always).isSuffocating(TBBlockRegistry::always)));

    public static final RegistryObject<Item> SCARECROW_BLOCK_ITEM = TB_ITEMS.register("scarecrow_block",
            () -> new BlockItem(SCARECROW_BLOCK.get(), new BlockItem.Properties()));

    public static final RegistryObject<Item> FUR_BLOCK_ITEM = TB_ITEMS.register("racoon_fur_block",
            () -> new BlockItem(FUR_BLOCK.get(), new BlockItem.Properties()));

    public static final RegistryObject<Item> ASPHALTED_DIRT_ITEM = TB_ITEMS.register("asphalted_dirt",
            () -> new BlockItem(ASPHALTED_DIRT.get(), new BlockItem.Properties()));

    //block without block items
    public static final RegistryObject<Block> QUETZAL_EGG_BLOCK = TB_BLOCKS.register("quetzalcoatlus_egg",
            () -> new EggBlock<>(BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG),"Quetzalcoatlus", QUETZALCOATLUS, 10,16));

    public static final RegistryObject<Block> CHIKOTE_EGG_BLOCK = TB_BLOCKS.register("chikote_egg",
            () -> new EggBlock<>(BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG),"tameable_Chikote", CHIKOTE, 10,11));

    public static final RegistryObject<Block> ROLY_POLY_EGG_BLOCK = TB_BLOCKS.register("roly_poly_egg",
            () -> new EggBlock<>(BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG),"giant_Roly_Poly", GIANT_ROLY_POLY,7, 12));

    public static final RegistryObject<Block> FLYING_BEETLE_EGG_BLOCK = TB_BLOCKS.register("beetle_egg",
            () -> new EggBlock<>(BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG),"tameable_Beetle", FLYING_BEETLE,7,12));

    public static final RegistryObject<Block> GROUND_BEETLE_EGG_BLOCK = TB_BLOCKS.register("ground_beetle_egg",
            () -> new EggBlock<>(BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG),"Ground_Beetle", GROUND_BEETLE, 7, 12));

    public static final RegistryObject<Block> GRASSHOPPER_EGG_BLOCK = TB_BLOCKS.register("grasshopper_egg",
            () -> new EggBlock<>(BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG),"giant_Grasshopper", GIANT_GRASSHOPPER,12, 4));

    public static final RegistryObject<Block> PENGUIN_EGG_BLOCK = TB_BLOCKS.register("penguin_egg",
            () -> new EggBlock<>(BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG),"tameable_Penguin", PENGUIN,5 ,6));

    public static final RegistryObject<Block> CRESTED_GECKO_EGG_BLOCK = TB_BLOCKS.register("crested_gecko_egg",
            () -> new EggBlock<>(BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG),"Crested_Gecko", CRESTED_GECKO, 8 ,13));

    public static final RegistryObject<Block> ARGENTAVIS_EGG_BLOCK = TB_BLOCKS.register("argentavis_egg",
            () -> new EggBlock<>(BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG),"Argentavis",ARGENTAVIS,10,16));

    public static final RegistryObject<Block> GRAPTERANODON_EGG_BLOCK = TB_BLOCKS.register("graptera_egg",
            () -> new EggBlock<>(BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG),"Grapteranodon",GRAPTERANODON, 7,13));

    private static boolean always(BlockState p_50775_, BlockGetter p_50776_, BlockPos p_50777_) {
        return true;
    }
}
