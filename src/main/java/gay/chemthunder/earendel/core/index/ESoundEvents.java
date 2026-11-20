package gay.chemthunder.earendel.core.index;

import gay.chemthunder.earendel.core.Earendel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import org.chemthunder.arboreal.api.core.task.RegisterTask;

/**
 * @author Chemthunder
 */
public interface ESoundEvents {
    RegisterTask<SoundEvent> task = Earendel.MAIN.createTask(RegisterTask::new, BuiltInRegistries.SOUND_EVENT);

    SoundEvent INTERCEPTOR_PING = task.register(
            "interceptor_ping",
            SoundEvent.createVariableRangeEvent(Earendel.id("block.interceptor_ping"))
    );
}
