package com.modderg.tameablebeasts.server.entity.abstracts;

import com.modderg.tameablebeasts.client.gui.TBItemStackHandler;
import com.modderg.tameablebeasts.client.gui.TBMenu;
import com.modderg.tameablebeasts.registry.TBPacketRegistry;
import com.modderg.tameablebeasts.server.entity.navigation.TBGroundPathNavigation;
import com.modderg.tameablebeasts.server.item.block.EggBlockItem;
import com.modderg.tameablebeasts.server.packet.StoCEntityInvSyncPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.modderg.tameablebeasts.constants.TBConstants.*;

public class TBAnimal extends TamableAnimal implements GeoEntity, HasCustomInventoryScreen{

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final EntityDataAccessor<Integer> TEXTURE_ID = SynchedEntityData.defineId(TBAnimal.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> WANDERING = SynchedEntityData.defineId(TBAnimal.class, EntityDataSerializers.BOOLEAN);

    protected static final EntityDataAccessor<Integer> HAPPINESS = SynchedEntityData.defineId(TBAnimal.class, EntityDataSerializers.INT);

    protected static final EntityDataAccessor<Boolean> GOALS_WANT_RUNNING = SynchedEntityData.defineId(TBAnimal.class, EntityDataSerializers.BOOLEAN);

    protected ItemStackHandler inventory = new TBItemStackHandler(this, 0);

    protected final LazyOptional<ItemStackHandler> invCapability = LazyOptional.of(() -> inventory);

    protected int stillDuringInteractAnim = -1;

    protected List<String> attackAnims = new ArrayList<>();

    protected boolean hasWarmthVariants = false;

    protected void hasWarmthVariants(){
        this.hasWarmthVariants = true;
    }

    protected ParticleOptions extraTameParticles = ParticleTypes.CLOUD;

    protected int textureIdSize = 0;

    public float cachedHeadYaw = 0F;

    public float cachedHeadPitch = 0F;

    public TBAnimal(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.moveControl = createMoveControl();
    }

    public void updateAttributes(){}

    protected void addGoals(Goal... goals){
        int i = 0;
        for (Goal goal: goals){
            i++;
            this.goalSelector.addGoal(i, goal);
        }
    }

    protected void addTargetGoals(Goal... goals){
        int i = 0;
        for (Goal goal: goals){
            i++;
            this.targetSelector.addGoal(i, goal);
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TEXTURE_ID, 0);
        this.entityData.define(WANDERING, false);
        this.entityData.define(GOALS_WANT_RUNNING, false);
        this.entityData.define(HAPPINESS, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("TEXTURE_ID", this.getTextureID());
        compound.putBoolean("WANDERING", this.isWandering());
        compound.putBoolean("GOALS_WANT_RUNNING", this.isRunning());
        compound.putInt("HAPPINESS", this.getHappiness());
        compound.put("Inventory", inventory.serializeNBT());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        if (compound.contains("TEXTURE_ID"))
            this.setTextureId(compound.getInt("TEXTURE_ID"));

        if (compound.contains("WANDERING"))
            this.setWandering(compound.getBoolean("WANDERING"));

        if (compound.contains("GOALS_WANT_RUNNING"))
            this.setWandering(compound.getBoolean("GOALS_WANT_RUNNING"));

        if (compound.contains("HAPPINESS"))
            this.setHappiness(compound.getInt("HAPPINESS"));

        if (compound.contains("Inventory"))
            inventory.deserializeNBT(compound.getCompound("Inventory"));

        this.updateAttributes();
    }

    @Override
    protected void spawnTamingParticles(boolean p_21835_) {
        int pAmount = 18;
        ParticleOptions[] particles = new ParticleOptions[]{ParticleTypes.HEART, this.extraTameParticles};

        if (!p_21835_){
            particles = new ParticleOptions[]{ParticleTypes.SMOKE};
            pAmount = 7;
        }

        for(int i = 0; i < pAmount; ++i) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.level().addParticle(particles[this.getRandom().nextInt(particles.length)],
                    this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
        }
    }

    public void setTextureId(int i){
        if(i > 2 && this.hasWarmthVariants)
            i = this.random.nextInt(WARM_VARIANT+1);

        this.getEntityData().set(TEXTURE_ID, i);
    }

    public int getTextureID(){
        return this.getEntityData().get(TEXTURE_ID);
    }

    public void setWandering(boolean i){
        this.getEntityData().set(WANDERING, i);
    }

    public boolean isWandering(){
        return this.getEntityData().get(WANDERING);
    }

    public boolean isRunning(){
        return this.getEntityData().get(GOALS_WANT_RUNNING);
    }

    public void setRunning(boolean i){
        this.getEntityData().set(GOALS_WANT_RUNNING, i);

        updateAttributes();
    }

    public void setHappiness(int i){
        this.getEntityData().set(HAPPINESS, i);
    }

    public int getHappiness(){
        return this.getEntityData().get(HAPPINESS);
    }

    public Item[] getBrushDrops() {return null;}

    @Override
    public boolean canAttack(@NotNull LivingEntity target) {
        return !(target instanceof TBAnimal tg && (this.getOwner() instanceof Player p && tg.isOwnedBy(p))) && super.canAttack(target);
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor levelAccessor, @NotNull DifficultyInstance p_146747_, @NotNull MobSpawnType p_146748_, @Nullable SpawnGroupData p_146749_, @Nullable CompoundTag p_146750_) {

        if(hasWarmthVariants){
            Holder<Biome> biome = this.level().getBiome(this.blockPosition());

            float temperature = biome.value().getBaseTemperature();

            int variant = TEMPERATE_VARIANT;
            if (temperature < 0.3f)
                variant = COLD_VARIANT;
            else if (temperature > 0.9f)
                variant = WARM_VARIANT;

            this.setTextureId(variant);
        }
        else if(textureIdSize > 0)
            this.setTextureId(this.random.nextInt(textureIdSize));

        return super.finalizeSpawn(levelAccessor, p_146747_, p_146748_, p_146749_, p_146750_);
    }

    @Override
    public void aiStep() {

        if(this.isTame()){
            if (!this.level().isClientSide && this.isAlive() && (this.tickCount % (int) (Math.exp(-(this.getHappiness() - 165)*0.045)) == 0))
                this.heal(1.0F);

            if(this.isAlive() && this.tickCount % 25 == 0)
                this.setHappiness(Math.max(this.getHappiness() - 1, 0));
        }

        super.aiStep();

        if(this.stillDuringInteractAnim-- > 0){
            this.getNavigation().stop();
            this.setDeltaMovement(0, this.getDeltaMovement().y,0);
        }
    }

    //INVENTORY STUFF

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction facing) {
        if (capability == ForgeCapabilities.ITEM_HANDLER)
            return invCapability.cast();

        return super.getCapability(capability, facing);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        invCapability.invalidate();
    }

    @Override
    public void openCustomInventoryScreen(@NotNull Player player){
        if(player instanceof ServerPlayer sPlayer)
            NetworkHooks.openScreen(sPlayer, new SimpleMenuProvider(
                    (id, playerInventory, playerEntity) -> createMenu(id, playerInventory),
                    Component.literal("Tameable Beast Inventory")
            ), buffer -> buffer.writeInt(this.getId()));
    }

    protected TBMenu createMenu(int containerId, Inventory playerInventory){
        return new TBMenu(containerId, playerInventory, this);
    }

    @Override
    public void die(@NotNull DamageSource p_21809_) {
        if (this.isTame())
            IntStream.range(0,this.inventory.getSlots()).forEach(i -> {
                ItemStack stack = this.inventory.getStackInSlot(i);
                if (!stack.isEmpty())
                    this.spawnAtLocation(stack);
            });
        super.die(p_21809_);
    }

    @Override
    public void startSeenByPlayer(@NotNull ServerPlayer player) {
        super.startSeenByPlayer(player);

        if(!this.isTame()) return;

        player.server.execute(() -> {
            List<ItemStack> copy = new ArrayList<>();

            for (int i = 0; i < this.getInventory().getSlots(); i++) {
                ItemStack stack = this.getInventory().getStackInSlot(i);
                if (!stack.isEmpty())
                    copy.add(stack.copy());
            }

            TBPacketRegistry.sendToClient(new StoCEntityInvSyncPacket(this.getId(), copy), player);
        });
    }

    public ItemStackHandler getInventory(){
        return inventory;
    }

    //SITTING STUFF

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {

        boolean client = this.level().isClientSide;

        this.playSound(this.getInteractSound(), 0.45F, 1.0F);

        if (this.isOwnedBy(player)){
            ItemStack stack = player.getItemInHand(hand);

            if(this.getBrushDrops() != null && stack.is(Items.BRUSH)){
                player.startUsingItem(hand);
                return InteractionResult.SUCCESS;
            }

            if(isFood(player.getItemInHand(hand))){
                this.setHappiness(Math.min(this.getHappiness() + 35, 100));
                if(!canFallInLove()){
                    usePlayerItem(player, hand, stack);
                    return InteractionResult.SUCCESS;
                }
                return super.mobInteract(player, hand);
            }

            if (!player.isShiftKeyDown())
                this.openCustomInventoryScreen(player);
            else
                if(!isInSittingPose() && !isWandering()){
                    this.setWandering(true);

                    if(client)
                        this.messageState("wandering", player);

                    return InteractionResult.sidedSuccess(client);

                } else {
                    this.setWandering(false);

                    if(client)
                        this.messageState(this.isInSittingPose() ? "following":"sitting", player);

                    this.setInSittingPose(!this.isInSittingPose());
                    this.setOrderedToSit(!this.isOrderedToSit());
                    this.jumping = false;
                    this.navigation.stop();
                    this.setTarget(null);
                    this.setDeltaMovement(Vec3.ZERO);

                    if(this.isInSittingPose())
                        playStepSound(this.getOnPos(), this.level().getBlockState(this.getOnPos()));

                    return InteractionResult.sidedSuccess(client);
                }
        }

        return super.mobInteract(player, hand);
    }

    @Override public boolean isOrderedToSit() {
        return !this.isVehicle() && super.isOrderedToSit();
    }

    @Override public boolean isInSittingPose() {
        return !this.isVehicle() && super.isInSittingPose();
    }

    //TAMING STUFF

    public boolean isTameFood(ItemStack itemStack) {
        return false;
    }

    public void tameGAnimal(Player player, ItemStack itemStack, int chance){

        if (!player.getAbilities().instabuild && itemStack!= null) itemStack.shrink(1);

        if (this.random.nextInt(100) < chance && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
            this.setOwnerUUID(player.getUUID());
            this.setTame(true);
            this.level().broadcastEntityEvent(this, (byte) 7);
            this.playSound(getTameSound(), 0.15F, 1.0F);
        } else
            this.level().broadcastEntityEvent(this, (byte) 6);
    }

    @Override
    public void setTame(boolean flag) {
        super.setTame(flag);
        updateAttributes();
    }

    //BREEDING STUFF

    public EggBlockItem getEgg(){
        return null;
    }

    public boolean breedDropsEgg(){
        return this.getEgg() != null && this.isTame();
    }

    public int genChildTextId(AgeableMob parent){
        if(parent instanceof TBAnimal animal)
            return animal.getTextureID();

        return 0;
    }

    @Override
    public void spawnChildFromBreeding(@NotNull ServerLevel level, @NotNull Animal mob) {
        if(!breedDropsEgg()) {
            super.spawnChildFromBreeding(level, mob);
            return;
        }
        ItemStack item = new ItemStack(getEgg());
        spawnAtLocation(EggBlockItem.setTextureId(item, this.getTextureID()));
        super.finalizeSpawnChildFromBreeding(level, mob, null);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel p_146743_, @NotNull AgeableMob parent) {
        if(this.isTame()) return null;

        TBAnimal animal = (TBAnimal) this.getType().create(this.level());
        animal.setTextureId(this.genChildTextId(parent));
        return animal;
    }

    //RIDING STUFF

    @Override
    public LivingEntity getControllingPassenger() {
        if(!this.getPassengers().isEmpty() && this.getPassengers().get(0) instanceof LivingEntity passenger && this.isOwnedBy(passenger))
            return passenger;

        return null;
    }

    //NAVIGATION STUFF

    public @NotNull MoveControl createMoveControl() {
        return new MoveControl(this);
    }

    @Override
    public @NotNull PathNavigation createNavigation(@NotNull Level p_21480_) {
        return new TBGroundPathNavigation(this, this.level());
    }

    public void setMoveControl(MoveControl control){this.moveControl = control;}
    public void setPathNavigation(PathNavigation navigation){this.navigation = navigation;}

    //SOUND STUFF

    public SoundEvent getTameSound(){
        return null;
    }
    public SoundEvent getInteractSound(){
        return null;
    }

    //CLIENT EFFECTS

    public void messageState(String txt, Player player){
        if (player instanceof LocalPlayer)
            Minecraft.getInstance().gui.setOverlayMessage(Component.literal(txt), false);
    }

    public void spawnItemParticles(ItemStack p_21061_, int p_21062_, Entity animal) {
        for(int i = 0; i < p_21062_; ++i) {
            Vec3 vec3 = new Vec3(((double)random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
            vec3 = vec3.xRot(-animal.getXRot() * ((float)Math.PI / 180F));
            vec3 = vec3.yRot(-animal.getYRot() * ((float)Math.PI / 180F));
            double d0 = (double)(-random.nextFloat()) * 0.6D - 0.3D;
            Vec3 vec31 = new Vec3(((double)random.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
            vec31 = vec31.xRot(-animal.getXRot() * ((float)Math.PI / 180F));
            vec31 = vec31.yRot(-animal.getYRot() * ((float)Math.PI / 180F));
            vec31 = vec31.add(animal.getX(), animal.getEyeY(), animal.getZ());
            if (animal.level() instanceof ServerLevel)
                ((ServerLevel) animal.level()).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, p_21061_), vec31.x, vec31.y, vec31.z, 1, vec3.x, vec3.y + 0.05D, vec3.z, 0.0D);
            else
                animal.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, p_21061_), vec31.x, vec31.y, vec31.z, vec3.x, vec3.y + 0.05D, vec3.z);
        }
    }

    @Override
    public @NotNull Component getName() {
        if (!hasWarmthVariants)
            return super.getName();

        ResourceLocation key = ForgeRegistries.ENTITY_TYPES.getKey(this.getType());
        String base = "entity." + key.getNamespace() + "." + key.getPath();

        return switch (this.getTextureID()) {
            case COLD_VARIANT      -> Component.translatable(base + ".cold");
            case TEMPERATE_VARIANT      -> Component.translatable(base + ".temperate");
            default                       -> Component.translatable(base + ".warm");
        };
    }


    //ANIMATION STUFF

    @Override
    public boolean doHurtTarget(@NotNull Entity p_21372_) {
        if(!attackAnims.isEmpty())
            playAttackAnim();

        return super.doHurtTarget(p_21372_);
    }

    public void playAttackAnim(){
        triggerAnim("movement", "attack");
    }

    public AnimationController<?> addAnimationTriggers(AnimationController<?> controller){
        for (String attack: attackAnims)
            controller.triggerableAnim(attack, RawAnimation.begin().then(attack, Animation.LoopType.PLAY_ONCE));
        controller.triggerableAnim("interact", RawAnimation.begin().then("interact", Animation.LoopType.PLAY_ONCE));

        return controller;
    }

    @Override public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {}

    @Override public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public <T extends TBAnimal & GeoEntity> AnimationController<T> groundController(T entity) {
        return new AnimationController<>(entity,"movement", 5, event -> groundState(entity, event));
    }


    public <T extends TBAnimal & GeoEntity> PlayState groundState(T entity, software.bernie.geckolib.core.animation.AnimationState<T> event) {

        if(event.isMoving()||entity.onClimbable())
            event.getController().setAnimation(RawAnimation.begin().then(entity.isRunning() ? "run" : "walk", Animation.LoopType.LOOP));
        else if(entity.isInSittingPose())
            event.setAnimation(RawAnimation.begin().then("sit", Animation.LoopType.LOOP));
        else
            event.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));

        return PlayState.CONTINUE;
    }
}
