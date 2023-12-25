package com.modderg.tameablebeasts.block.entity;

import com.modderg.tameablebeasts.block.BlockEntitiesInit;
import com.modderg.tameablebeasts.block.custom.EggBlock;
import com.modderg.tameablebeasts.entities.TameableGAnimal;
import com.modderg.tameablebeasts.entities.EntityIinit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;

import java.util.UUID;

public class EggBlockEntity extends BlockEntity implements GeoBlockEntity {

    //check gone bad and hatch timer
    public int goBadTimer = 500;
    public int hatchTimer = 600;

    public int textureID = 0;

    private String ownerUUID;

    public UUID getOwnerUUID() {
        return UUID.fromString(ownerUUID);
    }

    public void setOwnerUUID(String value) {
        this.ownerUUID = value;
        setChanged();
    }

    private final String species;

    public String getCleanSpecies(){
        return this.species.replace("tameable_","").replace("giant_","");
    }
    public String getSpecies(){
        return this.species;
    }

    public void  setTextureId(int id){
        this.textureID = id;
    }

    public boolean isWarm(){
        return this.level.getBlockState(this.getBlockPos().north()).getBlock() instanceof TorchBlock||
                this.level.getBlockState(this.getBlockPos().east()).getBlock() instanceof TorchBlock||
                this.level.getBlockState(this.getBlockPos().south()).getBlock() instanceof TorchBlock||
                this.level.getBlockState(this.getBlockPos().west()).getBlock() instanceof TorchBlock;
    }

    public EggBlockEntity(BlockPos p_155229_, BlockState block){
        super(BlockEntitiesInit.EGG_BLOCK_ENTITY.get(), p_155229_, block);
        this.species = ((EggBlock)block.getBlock()).getSpecies();
    }

    public EggBlockEntity(BlockPos p_155229_, BlockState p_155230_, String species){
        super(BlockEntitiesInit.EGG_BLOCK_ENTITY.get(), p_155229_, p_155230_);
        this.species = species;
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.putString("ownerUUUID",ownerUUID);
        compound.putInt("badTimer",goBadTimer);
        compound.putInt("hatchTimer",hatchTimer);
        compound.putInt("textureID",textureID);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.setOwnerUUID(compound.getString("ownerUUUID"));
        goBadTimer = compound.getInt("badTimer");
        goBadTimer = compound.getInt("hatchTimer");
        textureID = compound.getInt("textureID");
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (!level.isClientSide) {
            if ( goBadTimer-- >= 0) {
                if(isWarm())goBadTimer = 3000;
                hatchTimer--;
            }


            if(hatchTimer == 0){
                TameableGAnimal animal = (TameableGAnimal) EntityIinit.beastsMap.get(getSpecies().toLowerCase()).get().create(level);
                animal.setPos(this.getBlockPos().getCenter());
                animal.setBaby(true);
                animal.setTame(true);
                animal.setTextureId(textureID);
                animal.setOwnerUUID(this.getOwnerUUID());
                animal.updateAttributes();
                level.addFreshEntity(animal);
                level.destroyBlock(this.getBlockPos(), false);
                this.setRemoved();
            }
            setChanged();
            level.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = this.saveWithoutMetadata();
        tag.putInt("goBadTimer", this.goBadTimer);
        tag.putInt("hatchTimer", this.hatchTimer);
        tag.putInt("textureID", this.textureID);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        load(tag);
        this.goBadTimer = tag.getInt("goBadTimer");
        this.hatchTimer = tag.getInt("hatchTimer");
        this.textureID = tag.getInt("textureID");
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet) {
        var tag = packet.getTag();
        if (tag != null)
        {
            handleUpdateTag(tag);

            BlockState state = level.getBlockState(worldPosition);
            level.sendBlockUpdated(worldPosition, state, state, 3);
        }
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
