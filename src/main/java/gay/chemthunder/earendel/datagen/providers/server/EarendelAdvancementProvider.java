package gay.chemthunder.earendel.datagen.providers.server;

import gay.chemthunder.earendel.core.Earendel;
import gay.chemthunder.earendel.core.index.EItems;
import gay.chemthunder.earendel.core.index.ETriggerTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.triggers.PlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static gay.chemthunder.earendel.core.Earendel.id;

/**
 * @author Chemthunder
 */
public class EarendelAdvancementProvider extends FabricAdvancementProvider {
    public EarendelAdvancementProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }

    public void generateAdvancement(HolderLookup.Provider registryLookup, Consumer<AdvancementHolder> consumer) {
        AdvancementHolder root = Advancement.Builder.advancement()
                .rootDisplay(
                        Items.ENDER_EYE,
                        Component.literal("Earendel"),
                        Component.literal("Farther, farther than man has ever dreamt."),
                        Earendel.id("gui/advancements/backgrounds/earendel"),
                        AdvancementType.TASK,
                        false,
                        false,
                        false
                )
                .addCriterion("e", PlayerTrigger.TriggerInstance.tick().trigger().createCriterion(PlayerTrigger.TriggerInstance.tick().triggerInstance()))
                .requirements(AdvancementRequirements.allOf(Collections.singleton("e")))
                .build(id("root"));

        consumer.accept(root);

        AdvancementHolder etchSignal = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        EItems.DRIVE,
                        Component.literal("A Starry Night"),
                        Component.literal("Etch an intercepted Signal onto an empty Drive."),
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("e", ETriggerTypes.ETCH_SIGNAL.createCriterion(PlayerTrigger.TriggerInstance.tick().triggerInstance()))
                .requirements(AdvancementRequirements.allOf(Collections.singleton("e")))
                .build(id("etch_signal"));

        consumer.accept(etchSignal);

        AdvancementHolder broadcastSignal = Advancement.Builder.advancement()
                .parent(etchSignal)
                .display(
                        EItems.TRANSMITTER,
                        Component.literal("A Song to the Stars"),
                        Component.literal("Transmit an etched Signal back into space. Who knows what might hear you?"),
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("e", ETriggerTypes.BROADCAST_SIGNAL.createCriterion(PlayerTrigger.TriggerInstance.tick().triggerInstance()))
                .requirements(AdvancementRequirements.allOf(Collections.singleton("e")))
                .build(id("broadcast_signal"));

        consumer.accept(broadcastSignal);

        AdvancementHolder monad = Advancement.Builder.advancement()
                .parent(broadcastSignal)
                .display(
                        EItems.CRYING_RESOLVE,
                        Component.literal("Amaze Amaze Amaze!"),
                        Component.literal("Communicate with something beyond our solar system."),
                        AdvancementType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("e", ETriggerTypes.MONAD.createCriterion(PlayerTrigger.TriggerInstance.tick().triggerInstance()))
                .requirements(AdvancementRequirements.allOf(Collections.singleton("e")))
                .build(id("monad"));

        consumer.accept(monad);
    }
}
