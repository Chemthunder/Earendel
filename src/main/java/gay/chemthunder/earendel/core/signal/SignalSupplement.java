package gay.chemthunder.earendel.core.signal;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

import java.util.List;

/**
 * @author Chemthunder
 */
public interface SignalSupplement {
    int getColor();

    List<ResourceKey<Biome>> getBiomeKeys();
}
