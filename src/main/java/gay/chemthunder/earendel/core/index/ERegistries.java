package gay.chemthunder.earendel.core.index;

import gay.chemthunder.earendel.core.Earendel;
import gay.chemthunder.earendel.core.util.SignalFlag;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

/**
 * @author Chemthunder
 */
public interface ERegistries {
    ResourceKey<Registry<SignalFlag>> FLAG_KEY = ResourceKey.createRegistryKey(Earendel.id("signal_flag"));
    Registry<SignalFlag> FLAG = FabricRegistryBuilder.create(FLAG_KEY)
            .attribute(RegistryAttribute.MODDED)
            .buildAndRegister();

    static void init() {}
}
