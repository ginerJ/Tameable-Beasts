package com.modderg.tameablebeasts.server.block;

import com.modderg.tameablebeasts.registry.TBBlockEntityRegistry;
import com.modderg.tameablebeasts.server.entity.abstracts.TBAnimal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

import static com.modderg.tameablebeasts.server.ModCommonConfigs.GO_BAD_TIMER;
import static com.modderg.tameablebeasts.server.ModCommonConfigs.HATCH_TIMER;

public class EggBlockEntity<T extends TBAnimal> extends BlockEntity implements GeoBlockEntity {

    //check gone bad and hatch timer
    public int goBadTimer = 20 * GO_BAD_TIMER.get();
    public int hatchTimer = 20 * HATCH_TIMER.get();

    public int textureID = 0;

    private String ownerUUID = "";
    private RegistryObject<EntityType<T>> babyType;

    public UUID getOwnerUUID() {
        if(ownerUUID.isEmpty()) return UUID.randomUUID();

        return UUID.fromString(ownerUUID);
    }

    public void setOwnerUUID(String value) {
        this.ownerUUID = value;
        setChanged();
    }

    private final String species;

    public String getCleanSpecies(){
        return getSpecies().replace("tameable_","").replace("giant_","");
    }
    public String getSpecies(){
        return this.species;
    }

    public void  setTextureId(int id){
        this.textureID = id;
    }

    public boolean isWarm() {
        int blockLight = Objects.requireNonNull(this.getLevel())
                .getLightEngine()
                .getLayerListener(LightLayer.BLOCK)
                .getLightValue(this.getBlockPos());

        return blockLight > 11;
    }


    public EggBlockEntity(BlockPos p_155229_, BlockState block){
        super(TBBlockEntityRegistry.EGG_BLOCK_ENTITY.get(), p_155229_, block);
        this.species = ((EggBlock<?>)block.getBlock()).getSpecies();
    }

    public EggBlockEntity(BlockPos p_155229_, BlockState p_155230_, String species, RegistryObject<EntityType<T>> babyType){
        super(TBBlockEntityRegistry.EGG_BLOCK_ENTITY.get(), p_155229_, p_155230_);
        this.babyType = babyType;
        this.species = species;
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);

        compound.putString("ownerUUID",ownerUUID);
        compound.putInt("hatchTimer",hatchTimer);
        compound.putInt("textureID",textureID);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);

        this.textureID = compound.getInt("textureID");
        this.hatchTimer = compound.getInt("hatchTimer");
        this.ownerUUID = compound.getString("ownerUUID");
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = this.saveWithoutMetadata();

        tag.putInt("hatchTimer", this.hatchTimer);
        tag.putInt("textureID", this.textureID);
        tag.putString("ownerUUID", this.ownerUUID);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        load(tag);

        this.hatchTimer = tag.getInt("hatchTimer");
        this.textureID = tag.getInt("textureID");
        this.ownerUUID = tag.getString("ownerUUID");
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (!level.isClientSide) {
            if (goBadTimer-- >= 0)
                if(isWarm()) goBadTimer = 3000;

            if(hatchTimer-- <= 0 && babyType!= null){
                TBAnimal animal = babyType.get().create(level);
                animal.setPos(this.getBlockPos().getCenter());
                animal.setBaby(true);
                animal.setTame(true);
                animal.setTextureId(textureID);
                animal.setOwnerUUID(this.getOwnerUUID());
                animal.updateAttributes();
                animal.playSound(SoundEvents.TURTLE_EGG_HATCH);
                level.addFreshEntity(animal);
                level.destroyBlock(this.getBlockPos(), false);
                this.setRemoved();
            }
            setChanged();
            level.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
        }
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
