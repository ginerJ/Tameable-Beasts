package com.modderg.tameablebeasts.client.item;

import com.modderg.tameablebeasts.registry.TBItemRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class ItemHatRenderer extends ItemRenderer {
    public ItemHatRenderer(Minecraft p_266926_, TextureManager p_266774_, ModelManager p_266850_, ItemColors p_267016_, BlockEntityWithoutLevelRenderer p_267049_) {
        super(p_266926_, p_266774_, p_266850_, p_267016_, p_267049_);
    }

    public static final ModelResourceLocation BIKER_ON_HEAD = new ModelResourceLocation(
            new ResourceLocation("tameablebeasts", "models/block/biker_helmet.json"), "inventory");

    private static final ModelResourceLocation BIKER = new ModelResourceLocation(
            new ResourceLocation("tameablebeasts", "models/item/biker_helmet.json"), "inventory");


    public BakedModel getModel(@NotNull ItemStack p_174265_, @javax.annotation.Nullable Level p_174266_, @Nullable LivingEntity p_174267_, int p_174268_) {
        BakedModel bakedmodel = this.getItemModelShaper().getItemModel(p_174265_);;

        if (p_174265_.is(TBItemRegistry.BIKER_HELMET.get()))
            bakedmodel = this.getItemModelShaper().getModelManager().getModel(BIKER_ON_HEAD);

        ClientLevel clientlevel = p_174266_ instanceof ClientLevel ? (ClientLevel)p_174266_ : null;
        BakedModel bakedmodel1 = bakedmodel.getOverrides().resolve(bakedmodel, p_174265_, clientlevel, p_174267_, p_174268_);
        return bakedmodel1 == null ? this.getItemModelShaper().getModelManager().getMissingModel() : bakedmodel1;
    }

    public void render(ItemStack p_115144_, ItemDisplayContext p_270188_, boolean p_115146_, PoseStack p_115147_, MultiBufferSource p_115148_, int p_115149_, int p_115150_, BakedModel p_115151_) {
        if (!p_115144_.isEmpty()) {
            p_115147_.pushPose();
            boolean flag = p_270188_ == ItemDisplayContext.GUI || p_270188_ == ItemDisplayContext.GROUND || p_270188_ == ItemDisplayContext.FIXED;
            if (flag) {
                if (p_115144_.is(TBItemRegistry.BIKER_HELMET.get())) {
                    p_115151_ = this.getItemModelShaper().getModelManager().getModel(BIKER);
                }
            }

            p_115151_ = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(p_115147_, p_115151_, p_270188_, p_115146_);
            p_115147_.translate(-0.5F, -0.5F, -0.5F);
            if (!p_115151_.isCustomRenderer() && (!p_115144_.is(TBItemRegistry.BIKER_HELMET.get()) || flag)) {
                boolean flag1;
                if (p_270188_ != ItemDisplayContext.GUI && !p_270188_.firstPerson() && p_115144_.getItem() instanceof BlockItem) {
                    Block block = ((BlockItem)p_115144_.getItem()).getBlock();
                    flag1 = !(block instanceof HalfTransparentBlock) && !(block instanceof StainedGlassPaneBlock);
                } else {
                    flag1 = true;
                }
                for (var model : p_115151_.getRenderPasses(p_115144_, flag1)) {
                    for (var rendertype : model.getRenderTypes(p_115144_, flag1)) {
                        VertexConsumer vertexconsumer;
                        if (flag1) {
                            vertexconsumer = getFoilBufferDirect(p_115148_, rendertype, true, p_115144_.hasFoil());
                        } else {
                            vertexconsumer = getFoilBuffer(p_115148_, rendertype, true, p_115144_.hasFoil());
                        }

                        this.renderModelLists(model, p_115144_, p_115149_, p_115150_, p_115147_, vertexconsumer);
                    }
                }
            } else {
                net.minecraftforge.client.extensions.common.IClientItemExtensions.of(p_115144_).getCustomRenderer().renderByItem(p_115144_, p_270188_, p_115147_, p_115148_, p_115149_, p_115150_);
            }

            p_115147_.popPose();
        }
    }
}
