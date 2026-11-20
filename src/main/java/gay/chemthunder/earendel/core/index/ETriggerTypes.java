package gay.chemthunder.earendel.core.index;

import gay.chemthunder.earendel.core.Earendel;
import net.minecraft.advancements.triggers.CriterionTrigger;
import net.minecraft.advancements.triggers.PlayerTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import org.chemthunder.arboreal.api.core.task.RegisterTask;

/**
 * @author Chemthunder
 */
public interface ETriggerTypes {
    RegisterTask<CriterionTrigger<?>> task = Earendel.MAIN.createTask(RegisterTask::new, BuiltInRegistries.TRIGGER_TYPES);

    PlayerTrigger ETCH_SIGNAL = task.register("etch_signal", new PlayerTrigger());
    PlayerTrigger BROADCAST_SIGNAL = task.register("broadcast_signal", new PlayerTrigger());
    PlayerTrigger MONAD = task.register("monad", new PlayerTrigger());
    PlayerTrigger LOCATE_CASTER = task.register("locate_caster", new PlayerTrigger());
}
