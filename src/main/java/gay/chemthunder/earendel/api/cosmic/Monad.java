package gay.chemthunder.earendel.api.cosmic;

import gay.chemthunder.earendel.core.block.entity.TransmitterBlockEntity;
import gay.chemthunder.earendel.core.index.ERegistries;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chemthunder
 */
@SuppressWarnings("unused")
public interface Monad {
    default List<ItemStack> getDroppableItems(Level level, @Nullable LivingEntity target) {
        return new ArrayList<>();
    }

    default void createBlessing(Level level, @Nullable LivingEntity target) {}

    default Identifier getId() {
        return ERegistries.MONAD.getKey(this);
    }

    default boolean biomeEquals(TransmitterBlockEntity t, Level level, ResourceKey<Biome> biomeResourceKey) {
        Holder<Biome> biomeHolder = level.getBiome(t.getBlockPos());

        if (biomeHolder.unwrapKey().isPresent()) {
            ResourceKey<Biome> biomeKey = biomeHolder.unwrapKey().get();
            return biomeKey.equals(biomeResourceKey);
        }
        return false;
    }

    boolean canBeCalledUpon(Level level, @Nullable LivingEntity living, TransmitterBlockEntity transmitter);
}
