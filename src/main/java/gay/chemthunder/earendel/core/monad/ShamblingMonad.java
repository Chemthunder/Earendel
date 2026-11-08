package gay.chemthunder.earendel.core.monad;

import gay.chemthunder.earendel.api.cosmic.Monad;
import gay.chemthunder.earendel.core.block.entity.TransmitterBlockEntity;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.jetbrains.annotations.Nullable;

/**
 * @author Chemthunder
 */
public class ShamblingMonad implements Monad {
    public void createBlessing(Level level, @Nullable LivingEntity target) {
        if (target != null) {
            target.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 30 * 20));
        }
    }

    public boolean canBeCalledUpon(Level level, @Nullable LivingEntity living, TransmitterBlockEntity transmitter) {
        if (transmitter.getBlockPos().getY() <= 0) {
            Holder<Biome> biomeHolder = level.getBiome(transmitter.getBlockPos());

            if (biomeHolder.unwrapKey().isPresent()) {
                ResourceKey<Biome> biomeKey = biomeHolder.unwrapKey().get();
                return biomeKey.equals(Biomes.DEEP_DARK);
            }
        }
        return false;
    }
}
