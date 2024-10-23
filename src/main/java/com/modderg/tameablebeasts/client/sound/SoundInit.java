package com.modderg.tameablebeasts.client.sound;

import com.modderg.tameablebeasts.TameableBeast;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundInit {
    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, TameableBeast.MOD_ID);

    public static final RegistryObject<SoundEvent> RACOON_HAPPY = SOUNDS.register("entity.tameable_racoon.racoon_happy",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.tameable_racoon.racoon_happy")));
    public static final RegistryObject<SoundEvent> RACOON_AMBIENT = SOUNDS.register("entity.tameable_racoon.racoon_ambient",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.tameable_racoon.racoon_ambient")));
    public static final RegistryObject<SoundEvent> RACOON_INTERACT = SOUNDS.register("entity.tameable_racoon.racoon_interact",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.tameable_racoon.racoon_interact")));
    public static final RegistryObject<SoundEvent> RACOON_DEATH = SOUNDS.register("entity.tameable_racoon.racoon_death",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.tameable_racoon.racoon_death")));
    public static final RegistryObject<SoundEvent> RACOON_HURT = SOUNDS.register("entity.tameable_racoon.racoon_hurt",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.tameable_racoon.racoon_hurt")));
    public static final RegistryObject<SoundEvent> RACOON_STEPS = SOUNDS.register("entity.tameable_racoon.racoon_steps",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.tameable_racoon.racoon_steps")));

    public static final RegistryObject<SoundEvent> PENGUIN_HAPPY = SOUNDS.register("entity.tameable_penguin.penguin_happy",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.tameable_penguin.penguin_happy")));
    public static final RegistryObject<SoundEvent> PENGUIN_AMBIENT = SOUNDS.register("entity.tameable_penguin.penguin_ambient",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.tameable_penguin.penguin_ambient")));
    public static final RegistryObject<SoundEvent> PENGUIN_INTERACT = SOUNDS.register("entity.tameable_penguin.penguin_interact",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.tameable_penguin.penguin_interact")));
    public static final RegistryObject<SoundEvent> PENGUIN_DEATH = SOUNDS.register("entity.tameable_penguin.penguin_death",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.tameable_penguin.penguin_death")));
    public static final RegistryObject<SoundEvent> PENGUIN_HURT = SOUNDS.register("entity.tameable_penguin.penguin_hurt",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.tameable_penguin.penguin_hurt")));
    public static final RegistryObject<SoundEvent> PENGUIN_STEPS = SOUNDS.register("entity.tameable_penguin.penguin_steps",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.tameable_penguin.penguin_steps")));

    public static final RegistryObject<SoundEvent> CHIKOTE_HAPPY = SOUNDS.register("entity.tameable_chikote.chikote_happy",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.tameable_chikote.chikote_happy")));
    public static final RegistryObject<SoundEvent> CHIKOTE_AMBIENT = SOUNDS.register("entity.tameable_chikote.chikote_ambient",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.tameable_chikote.chikote_ambient")));
    public static final RegistryObject<SoundEvent> CHIKOTE_INTERACT = SOUNDS.register("entity.tameable_chikote.chikote_interact",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.tameable_chikote.chikote_interact")));
    public static final RegistryObject<SoundEvent> CHIKOTE_DEATH = SOUNDS.register("entity.tameable_chikote.chikote_death",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.tameable_chikote.chikote_death")));
    public static final RegistryObject<SoundEvent> CHIKOTE_HURT = SOUNDS.register("entity.tameable_chikote.chikote_hurt",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.tameable_chikote.chikote_hurt")));
    public static final RegistryObject<SoundEvent> CHIKOTE_STEPS = SOUNDS.register("entity.tameable_chikote.chikote_steps",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.tameable_chikote.chikote_steps")));

    public static final RegistryObject<SoundEvent> QUETZAL_FLY = SOUNDS.register("entity.quetzalcoatlus.quetzal_fly",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.quetzalcoatlus.quetzal_fly")));
    public static final RegistryObject<SoundEvent> QUETZAL_AMBIENT = SOUNDS.register("entity.quetzalcoatlus.quetzal_ambient",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.quetzalcoatlus.quetzal_ambient")));
    public static final RegistryObject<SoundEvent> QUETZAL_INTERACT = SOUNDS.register("entity.quetzalcoatlus.quetzal_interact",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.quetzalcoatlus.quetzal_interact")));
    public static final RegistryObject<SoundEvent> QUETZAL_DEATH = SOUNDS.register("entity.quetzalcoatlus.quetzal_death",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.quetzalcoatlus.quetzal_death")));
    public static final RegistryObject<SoundEvent> QUETZAL_HURT = SOUNDS.register("entity.quetzalcoatlus.quetzal_hurt",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.quetzalcoatlus.quetzal_hurt")));
    public static final RegistryObject<SoundEvent> QUETZAL_STEPS = SOUNDS.register("entity.quetzalcoatlus.quetzal_step",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.quetzalcoatlus.quetzal_step")));

    public static final RegistryObject<SoundEvent> GRAPTERA_FLY = SOUNDS.register("entity.graptera.graptera_fly",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.graptera.graptera_fly")));
    public static final RegistryObject<SoundEvent> GRAPTERA_AMBIENT = SOUNDS.register("entity.graptera.graptera_ambient",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.graptera.graptera_ambient")));
    public static final RegistryObject<SoundEvent> GRAPTERA_INTERACT = SOUNDS.register("entity.graptera.graptera_interact",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.graptera.graptera_interact")));
    public static final RegistryObject<SoundEvent> GRAPTERA_DEATH = SOUNDS.register("entity.graptera.graptera_death",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.graptera.graptera_death")));
    public static final RegistryObject<SoundEvent> GRAPTERA_HURT = SOUNDS.register("entity.graptera.graptera_hurt",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.graptera.graptera_hurt")));

    public static final RegistryObject<SoundEvent> SCARECROW_FLY = SOUNDS.register("entity.scarecrow_allay.scarecrow_fly",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.scarecrow_allay.scarecrow_fly")));
    public static final RegistryObject<SoundEvent> SCARECROW_AMBIENT = SOUNDS.register("entity.scarecrow_allay.scarecrow_ambient",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.scarecrow_allay.scarecrow_ambient")));
    public static final RegistryObject<SoundEvent> SCARECROW_INTERACT = SOUNDS.register("entity.scarecrow_allay.scarecrow_interact",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.scarecrow_allay.scarecrow_interact")));
    public static final RegistryObject<SoundEvent> SCARECROW_DEATH = SOUNDS.register("entity.scarecrow_allay.scarecrow_death",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.scarecrow_allay.scarecrow_death")));
    public static final RegistryObject<SoundEvent> SCARECROW_HURT = SOUNDS.register("entity.scarecrow_allay.scarecrow_hurt",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.scarecrow_allay.scarecrow_hurt")));

    public static final RegistryObject<SoundEvent> BEETLE_FLY = SOUNDS.register("entity.tameable_beetle.beetle_fly",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.tameable_beetle.beetle_fly")));
    public static final RegistryObject<SoundEvent> BEETLE_AMBIENT = SOUNDS.register("entity.tameable_beetle.beetle_ambient",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.tameable_beetle.beetle_ambient")));
    public static final RegistryObject<SoundEvent> BEETLE_INTERACT = SOUNDS.register("entity.tameable_beetle.beetle_interact",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.tameable_beetle.beetle_interact")));
    public static final RegistryObject<SoundEvent> BEETLE_DEATH = SOUNDS.register("entity.tameable_beetle.beetle_death",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.tameable_beetle.beetle_death")));
    public static final RegistryObject<SoundEvent> BEETLE_HURT = SOUNDS.register("entity.tameable_beetle.beetle_hurt",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.tameable_beetle.beetle_hurt")));
    public static final RegistryObject<SoundEvent> BEETLE_STEPS = SOUNDS.register("entity.tameable_beetle.beetle_steps",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.tameable_beetle.beetle_steps")));

    public static final RegistryObject<SoundEvent> ROLYPOLY_ROLL = SOUNDS.register("entity.roly_poly.roly_poly_roll",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.roly_poly.roly_poly_roll")));
    public static final RegistryObject<SoundEvent> ROLYPOLY_AMBIENT = SOUNDS.register("entity.roly_poly.roly_poly_ambient",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.roly_poly.roly_poly_ambient")));
    public static final RegistryObject<SoundEvent> ROLYPOLY_INTERACT = SOUNDS.register("entity.roly_poly.roly_poly_interact",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.roly_poly.roly_poly_interact")));
    public static final RegistryObject<SoundEvent> ROLYPOLY_DEATH = SOUNDS.register("entity.roly_poly.roly_poly_death",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.roly_poly.roly_poly_death")));
    public static final RegistryObject<SoundEvent> ROLYPOLY_HURT = SOUNDS.register("entity.roly_poly.roly_poly_hurt",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.roly_poly.roly_poly_hurt")));
    public static final RegistryObject<SoundEvent> ROLYPOLY_STEPS = SOUNDS.register("entity.roly_poly.roly_poly_steps",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.roly_poly.roly_poly_steps")));

    public static final RegistryObject<SoundEvent> GRASSHOPPER_JUMP = SOUNDS.register("entity.giant_grasshopper.grasshopper_jump",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.giant_grasshopper.grasshopper_jump")));
    public static final RegistryObject<SoundEvent> GRASSHOPPER_AMBIENT = SOUNDS.register("entity.giant_grasshopper.grasshopper_ambient",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.giant_grasshopper.grasshopper_ambient")));
    public static final RegistryObject<SoundEvent> GRASSHOPPER_INTERACT = SOUNDS.register("entity.giant_grasshopper.grasshopper_interact",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.giant_grasshopper.grasshopper_interact")));
    public static final RegistryObject<SoundEvent> GRASSHOPPER_DEATH = SOUNDS.register("entity.giant_grasshopper.grasshopper_death",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.giant_grasshopper.grasshopper_death")));
    public static final RegistryObject<SoundEvent> GRASSHOPPER_HURT = SOUNDS.register("entity.giant_grasshopper.grasshopper_hurt",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.giant_grasshopper.grasshopper_hurt")));
    public static final RegistryObject<SoundEvent> GRASSHOPPER_STEPS = SOUNDS.register("entity.giant_grasshopper.grasshopper_steps",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.giant_grasshopper.grasshopper_steps")));

    public static final RegistryObject<SoundEvent> ARGENTAVIS_AMBIENT = SOUNDS.register("entity.argentavis.argentavis_ambient",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.argentavis.argentavis_ambient")));
    public static final RegistryObject<SoundEvent> ARGENTAVIS_INTERACT = SOUNDS.register("entity.argentavis.argentavis_interact",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.argentavis.argentavis_interact")));
    public static final RegistryObject<SoundEvent> ARGENTAVIS_DEATH = SOUNDS.register("entity.argentavis.argentavis_death",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.argentavis.argentavis_death")));
    public static final RegistryObject<SoundEvent> ARGENTAVIS_HURT = SOUNDS.register("entity.argentavis.argentavis_hurt",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.argentavis.argentavis_hurt")));

    public static final RegistryObject<SoundEvent> CRESTED_GECKO_AMBIENT = SOUNDS.register("entity.crested_gecko.crested_gecko_ambient",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.crested_gecko.crested_gecko_ambient")));
    public static final RegistryObject<SoundEvent> CRESTED_GECKO_INTERACT = SOUNDS.register("entity.crested_gecko.crested_gecko_interact",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.crested_gecko.crested_gecko_interact")));
    public static final RegistryObject<SoundEvent> CRESTED_GECKO_DEATH = SOUNDS.register("entity.crested_gecko.crested_gecko_death",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.crested_gecko.crested_gecko_death")));
    public static final RegistryObject<SoundEvent> CRESTED_GECKO_HURT = SOUNDS.register("entity.crested_gecko.crested_gecko_hurt",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TameableBeast.MOD_ID, "entity.crested_gecko.crested_gecko_hurt")));
}
