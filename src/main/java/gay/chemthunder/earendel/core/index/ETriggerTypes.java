package gay.chemthunder.earendel.core.index;

import gay.chemthunder.earendel.core.Earendel;
import net.minecraft.advancements.triggers.CriterionTrigger;
import net.minecraft.advancements.triggers.PlayerTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import org.chemthunder.arboreal.api.registration.Index;

/**
 * @author Chemthunder
 */
public interface ETriggerTypes {
    Index<CriterionTrigger<?>> index = Earendel.MAIN.createIndex(BuiltInRegistries.TRIGGER_TYPES);

    PlayerTrigger BROADCAST_SIGNAL = index.register("broadcast_signal", new PlayerTrigger());

    static void init() {}
}
