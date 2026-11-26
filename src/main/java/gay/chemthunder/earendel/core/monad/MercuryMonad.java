package gay.chemthunder.earendel.core.monad;

import gay.chemthunder.earendel.api.cosmic.Monad;
import gay.chemthunder.earendel.core.block.entity.TransmitterBlockEntity;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.jetbrains.annotations.Nullable;

/**
 * @author Chemthunder
 */
public class MercuryMonad implements Monad {
    public void createBlessing(Level level, @Nullable LivingEntity target) {

    }

    public boolean canBeCalledUpon(Level level, @Nullable LivingEntity living, TransmitterBlockEntity transmitter) {
        if (living != null) {
            return biomeEquals(transmitter, level, Biomes.WINDSWEPT_HILLS) && transmitter.getBlockPos().getY() > 40;
        }
        return false;
    }
}
