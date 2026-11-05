package gay.chemthunder.earendel.core.signal_supp;

import gay.chemthunder.earendel.core.signal.SignalSupplement;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.List;

/**
 * @author Chemthunder
 */
public class TestSupplement implements SignalSupplement {
    public int getColor() {
        return 0;
    }

    public List<ResourceKey<Biome>> getBiomeKeys() {
        return List.of(
                Biomes.JAGGED_PEAKS
        );
    }
}
