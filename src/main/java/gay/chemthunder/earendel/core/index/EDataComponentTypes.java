package gay.chemthunder.earendel.core.index;

import gay.chemthunder.earendel.api.cosmic.Signal;
import gay.chemthunder.earendel.core.Earendel;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
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

    DataComponentType<ItemStack> HELD_STACK = index.register("held_stack", DataComponentType.<ItemStack>builder()
            .persistent(ItemStack.CODEC)
            .networkSynchronized(ItemStack.STREAM_CODEC)
            .ignoreSwapAnimation()
            .build());

    static void init() {}
}
