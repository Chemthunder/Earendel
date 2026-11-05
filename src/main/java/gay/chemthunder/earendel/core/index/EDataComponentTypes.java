package gay.chemthunder.earendel.core.index;

import gay.chemthunder.earendel.core.Earendel;
import gay.chemthunder.earendel.core.signal.Signal;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import org.chemthunder.arboreal.api.registration.Index;

/**
 * @author Chemthunder
 */
public interface EDataComponentTypes {
    Index<DataComponentType<?>> index = Earendel.MAIN.createIndex(BuiltInRegistries.DATA_COMPONENT_TYPE);

    DataComponentType<Signal> SIGNAL = index.register("signal", DataComponentType.<Signal>builder()
            .persistent(Signal.CODEC)
            .networkSynchronized(Signal.STREAM_CODEC)
            .build()
    );

    static void init() {}
}
