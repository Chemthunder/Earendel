package gay.chemthunder.earendel.core.index;

import gay.chemthunder.earendel.core.Earendel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import org.chemthunder.arboreal.api.registration.Index;

/**
 * @author Chemthunder
 */
public interface ESoundEvents {
    Index<SoundEvent> index = Earendel.MAIN.createIndex(BuiltInRegistries.SOUND_EVENT);

    SoundEvent INTERCEPTOR_PING = index.register(
            "interceptor_ping",
            SoundEvent.createVariableRangeEvent(Earendel.id("block.interceptor_ping"))
    );

    static void init() {}
}
